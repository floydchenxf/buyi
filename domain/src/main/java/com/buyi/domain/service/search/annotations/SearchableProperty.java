/**
 * hisuda copy right 1.0 
 */
package com.buyi.domain.service.search.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * SearchableProperty.java
 * 
 * @author cxf128
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchableProperty {

    String name() default "";

    float boost() default 1.0f;

    Store store() default Store.NO;

    Index index() default Index.NO;

    String analyzer() default "default";

    String converter() default "";

    String format() default "";

    String nullValue() default "";

}
