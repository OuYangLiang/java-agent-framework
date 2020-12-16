package com.personal.oyl.agent.framework.core;

import com.personal.oyl.agent.framework.plugin.api.Plugin;
import com.personal.oyl.agent.framework.plugin.api.PluginType;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

import static net.bytebuddy.matcher.ElementMatchers.none;

/**
 * @author OuYang Liang
 * @since 2020-12-07
 */
public class AgentCore {
    private static final Logger log = LoggerFactory.getLogger(AgentCore.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        go(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        go(agentArgs, inst);
    }

    private static void go(String agentArgs, Instrumentation inst) {
        PluginDownloader.INSTANCE.downloadPlugin(agentArgs, inst);

        AgentBuilder builder = new AgentBuilder.Default()//.ignore(none())
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION);

        ClassLoader cl = AgentCore.class.getClassLoader();
        List<Plugin> plugins = load(cl);

        for (Plugin plugin : plugins) {
            log.info( String.format("加载插件：%s", plugin.name()) );

            if (PluginType.INTERCEPTER.equals(plugin.type())) {
                builder = builder.type(plugin.typeMatcher()).transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                        return builder.method(plugin.methodMatcher()).intercept(MethodDelegation.to(new AgentInterceptor(plugin.intercepters())));
                    }
                });
            } else if (PluginType.ADVICE.equals(plugin.type())) {
                builder = builder.type(plugin.typeMatcher()).transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                        return builder.visit(Advice.to(plugin.advice()).on(plugin.methodMatcher()));
                    }
                });
            }

        }

        builder.installOn(inst);
    }

    private static  List<Plugin> load(ClassLoader cl) {
        ServiceLoader<Plugin> services = ServiceLoader.load(Plugin.class, cl);

        List<Plugin> list = new LinkedList<>();

        for (Plugin service : services) {
            list.add(service);
        }

        return list;
    }

}
