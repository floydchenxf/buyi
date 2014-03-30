package org.springframework.velocity.tools;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.generic.SafeConfig;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.buyi.util.UrlUtil;
import com.buyi.webapp.controller.ViewController;

public class ControllerTool extends SafeConfig {

	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";

	private BeanFactory beanFactory;

	private VelocityEngine ve;

	private ViewController controller;

	private String templateName;

	private Map<String, Object> params = new HashMap<String, Object>();

	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory == null) {
			NullPointerException e = new NullPointerException("beanFactory should not be null");
			throw e;
		}

		this.beanFactory = beanFactory;

		VelocityConfigurer velocityConfigurer = (VelocityConfigurer) this.beanFactory.getBean(VelocityConfigurer.class);
		if (velocityConfigurer == null) {
			NullPointerException e = new NullPointerException("velocityConfigurer is null");
			throw e;
		}

		ve = velocityConfigurer.getVelocityEngine();
		if (ve == null) {
			NullPointerException e = new NullPointerException("velocityEngine is null");
			throw e;
		}
	}

	public ControllerTool setController(String controllerName) {
		if (controllerName == null || controllerName.trim().equals("")) {
			NullPointerException e = new NullPointerException("controller is empty");
			throw e;
		}

		controller = (ViewController) beanFactory.getBean(controllerName);
		if (controller == null) {
			NullPointerException e = new NullPointerException(controllerName + " is null");
			throw e;
		}

		return this;
	}

	public ControllerTool setTemplate(String templateName) {
		this.templateName = templateName;
		return this;
	}

	public ControllerTool setParameter(String key, Object value) {
		params.put(key, value);
		return this;
	}

	public String render(String encode) {
		if (this.controller == null && (this.templateName == null || this.templateName.trim().equals(""))) {
			NullPointerException e = new NullPointerException("controller is null");
			throw e;
		}

		Context context = null;
		String templateName = null;
		if (this.controller != null) {
			context = controller.fillData(params);
			templateName = controller.getTemplate();
		} else {
			context = new VelocityContext();
			if (params != null) {
				for (Map.Entry<String, Object> param : params.entrySet()) {
					context.put(param.getKey(), param.getValue());
				}
			}
		}
		
		if (templateName == null) {
			templateName = this.templateName;
		}
		
		initContextTool(context);

		StringWriter writer = new StringWriter();
		Template t = ve.getTemplate(templateName, encode);
		t.merge(context, writer, null);
		return writer.toString();
	}

	private void initContextTool(Context context) {
		context.put("urlUtil", new UrlUtil());
		context.put("controller", new ControllerTool());
	}

	public String toString() {
		return this.render(UTF_8);
	}

}
