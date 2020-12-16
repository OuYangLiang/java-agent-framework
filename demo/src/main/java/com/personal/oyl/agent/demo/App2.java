package com.personal.oyl.agent.demo;

import java.util.concurrent.TimeUnit;

/**
 * @author OuYang Liang
 * @since 2020-12-15
 */
public class App2 {
    public static void main(String[] args) {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println(new DemoService().hello("欧阳"));
                System.out.println(new DemoService().hello("欧阳亮"));
                System.out.println(new DemoService().hello(null));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
