package com.personal.oyl.agent.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author OuYang Liang
 * @since 2020-12-07
 */
public enum AgentHome {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(AgentHome.class);
    private static final String HOME_DIR_NAME = ".java_agent_home";
    private static final String JAR_FILE_EXTENSION = ".jar";
    private final Path home;

    AgentHome() {
        this.home = createHomeIfNecessary();
        createJarDirectories(home);
    }

    public Path getHome() {
        return home;
    }

    public URL[] listFileUrls(JarType jarType) {
        return Stream.of(listFiles(jarType))
                .map(File::toURI)
                .map(this::toURL)
                .toArray(URL[]::new);
    }

    private URL toURL(URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public File[] listFiles(JarType jarType) {
        File file = home.resolve(jarType.name().toLowerCase()).toFile();
        if (file.exists()) {
            File[] jars = file.listFiles(pathname -> pathname.getName().endsWith(JAR_FILE_EXTENSION));
            return jars == null ? new File[0] : jars;
        }

        return new File[0];
    }

    private Path createHomeIfNecessary() {
        Path path = Paths.get(System.getProperty("user.home"), HOME_DIR_NAME);
        File file = path.toFile();

        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (created) {
                log.info(String.format("创建重力家目录成功：%s", path));
            }
        }

        return path;
    }

    private void createJarDirectories(Path home) {
        for (JarType jarType : JarType.values()) {
            File file = home.resolve(jarType.name().toLowerCase()).toFile();

            if (!file.exists()) {
                boolean created = file.mkdirs();

                if (created) {
                    log.info(String.format("创建Jar包目录成功：%s", file));
                }
            }
        }
    }

}
