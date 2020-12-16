package com.personal.oyl.plugin2;

import com.personal.oyl.agent.framework.plugin.api.Plugin;
import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;
import com.personal.oyl.agent.framework.plugin.api.PluginType;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-12-16
 */
public class DemoPlugin implements Plugin {

    @Override
    public PluginType type() {
        return PluginType.ADVICE;
    }

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return ElementMatchers.nameContains("DemoService");
    }

    @Override
    public ElementMatcher<MethodDescription> methodMatcher() {
        return ElementMatchers.isMethod();
    }

    @Override
    public List<PluginIntercepter> intercepters() {
        return null;
    }

    @Override
    public Class<?> advice() {
        return DemoAdvice.class;
    }

    @Override
    public String name() {
        return "advice plugin demo";
    }
}
