package org.vaadin.mockapp.ui.views;

import java.lang.annotation.*;

/**
 * @author petter@vaadin.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewDefinition {

    /**
     *
     * @return
     */
    String name();

    /**
     *
     * @return
     */
    String caption() default "";

    /**
     *
     * @return
     */
    String iconThemeResource() default "";

    /**
     *
     * @return
     */
    int order() default 0;

    /**
     *
     * @return
     */
    boolean cache() default false;

    /**
     *
     * @return
     */
    String[] allowedRoles() default {};
}
