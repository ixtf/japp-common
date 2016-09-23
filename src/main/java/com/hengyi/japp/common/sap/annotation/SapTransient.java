package com.hengyi.japp.common.sap.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SapTransient {
}
