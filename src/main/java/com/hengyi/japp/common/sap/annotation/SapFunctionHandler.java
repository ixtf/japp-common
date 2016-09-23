package com.hengyi.japp.common.sap.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SapFunctionHandler {

    String functionName();
}
