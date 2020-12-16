package com.personal.oyl.plugin2;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Method;

/**
 * @author OuYang Liang
 * @since 2020-12-16
 */
public class DemoAdvice {

    @Advice.OnMethodEnter
    public static long onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
        if (null != arguments) {
            for (Object obj : arguments) {
                if (null == obj) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return System.currentTimeMillis();
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin String method, @Advice.AllArguments Object[] arguments, @Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object ret, @Advice.Enter long time) {

        if (ret instanceof String) {
            String strVal = ret.toString();
            System.out.println("Str Val = " + strVal);
        }

        ret = ret + "xxx";

        System.out.println(method + ": took " + (System.currentTimeMillis() - time) + "ms");
    }
}
