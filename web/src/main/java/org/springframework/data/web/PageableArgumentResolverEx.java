/**
 * hisuda copy right 1.0 
 */
package org.springframework.data.web;

import java.beans.PropertyEditorSupport;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequestEx;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageableEx;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 增加对可设定 offset的支持: page.offset。
 * 
 * @see PageableArgumentResolver
 * @author Ju
 */
public class PageableArgumentResolverEx implements WebArgumentResolver {

	private static final Pageable DEFAULT_PAGE_REQUEST = new PageRequestEx(0, 10);
	private static final String DEFAULT_PREFIX = "page";
	private static final String DEFAULT_SEPARATOR = ".";

	private Pageable fallbackPagable = DEFAULT_PAGE_REQUEST;
	private String prefix = DEFAULT_PREFIX;
	private String separator = DEFAULT_SEPARATOR;
	/** 是否解析客户端提交的page.size */
	private boolean parsePageSize;

	/**
	 * Setter to configure a fallback instance of {@link Pageable} that is being
	 * used to back missing parameters. Defaults to
	 * {@value #DEFAULT_PAGE_REQUEST}.
	 * 
	 * @param fallbackPagable
	 *            the fallbackPagable to set
	 */
	public void setFallbackPagable(Pageable fallbackPagable) {

		this.fallbackPagable = null == fallbackPagable ? DEFAULT_PAGE_REQUEST : fallbackPagable;
	}

	/**
	 * Setter to configure the prefix of request parameters to be used to
	 * retrieve paging information. Defaults to {@link #DEFAULT_PREFIX}.
	 * 
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {

		this.prefix = null == prefix ? DEFAULT_PREFIX : prefix;
	}

	/**
	 * Setter to configure the separator between prefix and actual property
	 * value. Defaults to {@link #DEFAULT_SEPARATOR}.
	 * 
	 * @param separator
	 *            the separator to set
	 */
	public void setSeparator(String separator) {

		this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.bind.support.WebArgumentResolver#resolveArgument
	 * (org.springframework.core.MethodParameter,
	 * org.springframework.web.context.request.NativeWebRequest)
	 */
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) {

		Class<?> paramType = methodParameter.getParameterType();
		if (PageableEx.class == paramType || Pageable.class == paramType) {

			assertPageableUniqueness(methodParameter);

			Pageable request = getDefaultFromAnnotationOrFallback(methodParameter);

			ServletRequest servletRequest = (ServletRequest) webRequest.getNativeRequest();

			MutablePropertyValues propertyValues = new ServletRequestParameterPropertyValues(servletRequest, getPrefix(methodParameter), separator);
			if (!parsePageSize) {
				// 忽略page.size这个参数，以防止攻击
				propertyValues.removePropertyValue("size");
			}

			DataBinder binder = new ServletRequestDataBinder(request);

			binder.initDirectFieldAccess();
			binder.registerCustomEditor(Sort.class, new SortPropertyEditor("sort.dir", propertyValues));
			binder.bind(propertyValues);

			if (request.getPageNumber() > 0) {

				request = new PageRequestEx(request.getPageNumber(), request);
			}

			return request;
		}

		return UNRESOLVED;
	}

	private Pageable getDefaultFromAnnotationOrFallback(MethodParameter methodParameter) {

		// search for PageableDefaults annotation
		for (Annotation annotation : methodParameter.getParameterAnnotations()) {
			if (annotation instanceof PageableDefaults) {
				PageableDefaults defaults = (PageableDefaults) annotation;
				return new PageRequestEx(defaults.pageNumber(), defaults.value());
			}
		}

		// Construct request with fallback request to ensure sensible
		// default values. Create fresh copy as Spring will manipulate the
		// instance under the covers
		return new PageRequestEx(fallbackPagable.getPageNumber(), fallbackPagable.getPageSize(), fallbackPagable.getSort());
	}

	/**
	 * Resolves the prefix to use to bind properties from. Will prepend a
	 * possible {@link Qualifier} if available or return the configured prefix
	 * otherwise.
	 * 
	 * @param parameter
	 * @return
	 */
	private String getPrefix(MethodParameter parameter) {

		for (Annotation annotation : parameter.getParameterAnnotations()) {
			if (annotation instanceof Qualifier) {
				return new StringBuilder(((Qualifier) annotation).value()).append("_").append(prefix).toString();
			}
		}

		return prefix;
	}

	public boolean isParsePageSize() {
		return parsePageSize;
	}

	public void setParsePageSize(boolean parsePageSize) {
		this.parsePageSize = parsePageSize;
	}

	/**
	 * Asserts uniqueness of all {@link Pageable} parameters of the method of
	 * the given {@link MethodParameter}.
	 * 
	 * @param parameter
	 */
	private void assertPageableUniqueness(MethodParameter parameter) {

		Method method = parameter.getMethod();

		if (containsMoreThanOnePageableParameter(method)) {
			Annotation[][] annotations = method.getParameterAnnotations();
			assertQualifiersFor(method.getParameterTypes(), annotations);
		}
	}

	/**
	 * Returns whether the given {@link Method} has more than one
	 * {@link Pageable} parameter.
	 * 
	 * @param method
	 * @return
	 */
	private boolean containsMoreThanOnePageableParameter(Method method) {

		boolean pageableFound = false;

		for (Class<?> type : method.getParameterTypes()) {

			if (pageableFound && type.equals(Pageable.class)) {
				return true;
			}

			if (type.equals(Pageable.class)) {
				pageableFound = true;
			}
		}

		return false;
	}

	/**
	 * Asserts that every {@link Pageable} parameter of the given parameters
	 * carries an {@link Qualifier} annotation to distinguish them from each
	 * other.
	 * 
	 * @param parameterTypes
	 * @param annotations
	 */
	private void assertQualifiersFor(Class<?>[] parameterTypes, Annotation[][] annotations) {

		Set<String> values = new HashSet<String>();

		for (int i = 0; i < annotations.length; i++) {

			if (Pageable.class.equals(parameterTypes[i])) {

				Qualifier qualifier = findAnnotation(annotations[i]);

				if (null == qualifier) {
					throw new IllegalStateException("Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
				}

				if (values.contains(qualifier.value())) {
					throw new IllegalStateException("Values of the user Qualifiers must be unique!");
				}

				values.add(qualifier.value());
			}
		}
	}

	/**
	 * Returns a {@link Qualifier} annotation from the given array of
	 * {@link Annotation}s. Returns {@literal null} if the array does not
	 * contain a {@link Qualifier} annotation.
	 * 
	 * @param annotations
	 * @return
	 */
	private Qualifier findAnnotation(Annotation[] annotations) {

		for (Annotation annotation : annotations) {
			if (annotation instanceof Qualifier) {
				return (Qualifier) annotation;
			}
		}

		return null;
	}

	/**
	 * {@link java.beans.PropertyEditor} to create {@link Sort} instances from
	 * textual representations. The implementation interprets the string as a
	 * comma separated list where the first entry is the sort direction (
	 * {@code asc}, {@code desc}) followed by the properties to sort by.
	 * 
	 * @author Oliver Gierke
	 */
	private static class SortPropertyEditor extends PropertyEditorSupport {

		private final String orderProperty;
		private final PropertyValues values;

		/**
		 * Creates a new {@link SortPropertyEditor}.
		 * 
		 * @param orderProperty
		 * @param values
		 */
		public SortPropertyEditor(String orderProperty, PropertyValues values) {

			this.orderProperty = orderProperty;
			this.values = values;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
		 */
		@Override
		public void setAsText(String text) throws IllegalArgumentException {

			PropertyValue rawOrder = values.getPropertyValue(orderProperty);
			Direction order = null == rawOrder ? Direction.ASC : Direction.fromString(rawOrder.getValue().toString());

			setValue(new Sort(order, text));
		}
	}
}
