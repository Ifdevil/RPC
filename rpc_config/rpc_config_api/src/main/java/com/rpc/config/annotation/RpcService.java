package com.rpc.config.annotation;

import java.lang.annotation.*;

/**
 * RPC服务注解
 * @author ifdevil
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RpcService {

    Class<?> interfaceClass() default void.class;
}
