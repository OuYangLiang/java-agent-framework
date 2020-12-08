package com.personal.oyl.agent.framework.core;

import com.personal.oyl.agent.framework.plugin.api.Plugin;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.jar.JarFile;

/**
 * @author OuYang Liang
 * @since 2020-12-07
 */
public class AgentCore {
    private static final Logger log = LoggerFactory.getLogger(AgentCore.class);

    public static void premain(String agentArgs, Instrumentation inst) throws IOException {

        PluginDownloader.INSTANCE.downloadPlugin(agentArgs, inst);

        AgentBuilder builder = new AgentBuilder.Default();

        //AgentClassLoader cl = new AgentClassLoader();
        ClassLoader cl = AgentCore.class.getClassLoader();
        List<Plugin> plugins = load(cl);

        for (Plugin plugin : plugins) {
            log.info( String.format("加载插件：%s", plugin.name()) );
            builder = builder.type(plugin.typeMatcher()).transform(new AgentBuilder.Transformer() {
                @Override
                public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                    return builder.method(plugin.methodMatcher()).intercept(MethodDelegation.to(new AgentInterceptor(plugin.intercepters())));
                }
            });
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
