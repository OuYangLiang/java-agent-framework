package com.personal.oyl.plugin;

import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author OuYang Liang
 * @since 2020-11-21
 */
public class TimeConsumingIntercepter implements PluginIntercepter {

    @Override
    public Object intercept(Method method, Object[] param, Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();

        try {
            return callable.call();
        } finally {
            System.out.println(method + ": took " + (System.currentTimeMillis() - start) + " ms");
        }
    }

}
