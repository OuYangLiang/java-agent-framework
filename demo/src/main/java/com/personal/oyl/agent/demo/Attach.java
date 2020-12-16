package com.personal.oyl.agent.demo;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-12-15
 */
public class Attach {
    public static void main(String[] args) throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("App2")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/john/agent-core-1.0.0.jar", "file:/Users/john/Documents/git_repos/java-agent-framework/plugin2/target/plugin2-1.0.0.jar");
                System.out.println("ok");
                virtualMachine.detach();
            }
        }
    }
}
