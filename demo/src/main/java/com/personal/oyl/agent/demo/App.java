package com.personal.oyl.agent.demo;

/**
 * Hello world!
 *
 * -javaagent:/Users/john/agent-core-1.0.0.jar=file:/Users/john/Documents/git_repos/java-agent-framework/plugin2/target/plugin2-1.0.0.jar
 */
public class App {
    public static void main(String[] args) {
        try {
            System.out.println(new DemoService().hello("欧阳"));
            System.out.println(new DemoService().hello("小朋友"));
            System.out.println(new DemoService().hello(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
