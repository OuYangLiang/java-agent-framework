package com.personal.oyl.agent.framework.plugin.api;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface PluginIntercepter {

    Object intercept(Method method, Object[] param, Callable<?> callable) throws Exception;

}
