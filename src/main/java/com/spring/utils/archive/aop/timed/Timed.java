package com.spring.utils.archive.aop.timed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @apiNote Custom annotation
 *          - Measures the execution time of the method.
 *
 * @author  Michelnajeho
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Timed {

    public String name() default "Unnamed";
}
