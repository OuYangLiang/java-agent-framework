package com.personal.oyl.plugin;

import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author OuYang Liang
 * @since 2020-12-07
 */
public class TimesCountingIntercepter implements PluginIntercepter {

    private static final Logger log = LoggerFactory.getLogger(TimesCountingIntercepter.class);
    private static final ConcurrentHashMap<Method, AtomicLong> count = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Method method, Object[] param, Callable<?> callable) throws Exception {
        count.putIfAbsent(method, new AtomicLong(0));

        long current = count.get(method).incrementAndGet();

        log.info(method.getName() + " called: " + current + " times.");

        return callable.call();
    }

}
