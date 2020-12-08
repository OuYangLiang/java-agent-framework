package com.personal.oyl.plugin;

import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author OuYang Liang
 * @since 2020-11-20
 */
public class NullParameterCheckIntercepter implements PluginIntercepter {

    @Override
    public Object intercept(Method method, Object[] param, Callable<?> callable) throws Exception {
        if (null != param) {
            for (Object obj : param) {
                if (null == obj) {
                    throw new IllegalArgumentException(method.getName());
                }
            }
        }

        return callable.call();
    }

}
