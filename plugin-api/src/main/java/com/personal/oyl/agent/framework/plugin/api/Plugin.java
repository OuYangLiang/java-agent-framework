package com.personal.oyl.agent.framework.plugin.api;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-12-07
 */
public interface Plugin extends Named {

    ElementMatcher<TypeDescription> typeMatcher();

    ElementMatcher<MethodDescription> methodMatcher();

    List<PluginIntercepter> intercepters();

}
