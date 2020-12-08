package com.personal.oyl.plugin;

import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author OuYang Liang
 * @since 2020-11-21
 */
public class TimeConsumingIntercepter implements PluginIntercepter {

    private static final Logger log = LoggerFactory.getLogger(TimeConsumingIntercepter.class);

    @Override
    public Object intercept(Method method, Object[] param, Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();

        try {
            return callable.call();
        } finally {
            log.info(method + ": took " + (System.currentTimeMillis() - start) + " ms");
        }
    }

}
