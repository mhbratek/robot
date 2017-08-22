package com.java.academy.logger;

import java.lang.annotation.*;

/**
 * @author bratek
 * @since 22.08.17
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Log {
}
