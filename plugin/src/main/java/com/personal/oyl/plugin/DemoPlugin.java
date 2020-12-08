package com.personal.oyl.plugin;

import com.personal.oyl.agent.framework.plugin.api.Plugin;
import com.personal.oyl.agent.framework.plugin.api.PluginIntercepter;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Arrays;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-12-08
 */
public class DemoPlugin implements Plugin {
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
        return Arrays.asList(
                new TimesCountingIntercepter(),
                new NullParameterCheckIntercepter(),
                new TimeConsumingIntercepter()
        );
    }

    @Override
    public String name() {
        return "Demo Plugin";
    }
}
