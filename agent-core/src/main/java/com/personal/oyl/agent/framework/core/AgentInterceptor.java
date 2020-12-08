package com.personal.oyl.agent.framework.core;

import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author OuYang Liang
 * @since 2020-12-08
 */
public class AgentInterceptor {

    private List<PluginIntercepter> intercepters;

    public AgentInterceptor(List<PluginIntercepter> intercepters) {
        this.intercepters = intercepters;
    }

    @RuntimeType
    public Object intercept(@Origin Method method, @AllArguments Object[] param, @SuperCall Callable<?> callable) throws Exception {

        Callable<?> runner = callable;
        for (PluginIntercepter intercepter : intercepters) {
            Object rlt = intercepter.intercept(method, param, runner);
            runner = () ->  rlt;
        }

        return runner.call();
    }

}
