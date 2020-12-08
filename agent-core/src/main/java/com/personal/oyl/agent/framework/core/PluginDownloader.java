package com.personal.oyl.agent.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarFile;

/**
 * @author OuYang Liang
 * @since 2020-12-08
 */
public enum PluginDownloader {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(PluginDownloader.class);

    public void downloadPlugin(String agentArgs, Instrumentation inst) {
        String pluginFilename = agentArgs.substring(agentArgs.lastIndexOf("/") + 1);
        URL url;
        try {
            url = new URL(agentArgs);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        log.info(String.format("开始下载文件：%s", pluginFilename));
        try (InputStream is = url.openStream()) {
            Files.copy(is, AgentHome.INSTANCE.getSubDirectoriy(JarType.AGENT).resolve(pluginFilename), StandardCopyOption.REPLACE_EXISTING);
            log.info(String.format("下载 %s 下载成功", pluginFilename));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        for (File file : AgentHome.INSTANCE.listFiles(JarType.AGENT)) {
            try {
                inst.appendToSystemClassLoaderSearch(new JarFile(file));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

    }
}
