package com.bazzi.analysis.util;

import com.bazzi.analysis.AnalysisProcessor;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.concurrent.locks.ReentrantLock;

public final class SpringContainer implements ApplicationContextAware {
    private static volatile ApplicationContext context = null;

    private static final ReentrantLock runLock = new ReentrantLock();

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public void setApplicationContext(
            @SuppressWarnings("NullableProblems") ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }

    public static boolean hasRun() {
        return context != null;
    }

    private SpringContainer() {
    }

    private static final SpringContainer container = new SpringContainer();

    public static void run(String[] args) {
        if (hasRun())
            return;
        runLock.lock();
        try {
            if (hasRun())
                return;
            //如果是windows或者MAC，则控制台输出日志，如果是Linux则不输出控制台日志，避免storm集群重复记录日志
            System.setProperty("stdout.level",
                    String.valueOf(File.separatorChar).equals("\\") || System.getProperty("os.name").contains("Mac") ? "DEBUG" : "OFF");
            //初始化容器
            ConfigurableApplicationContext context = SpringApplication.run(AnalysisProcessor.class, args);
            container.setApplicationContext(context);
        } finally {
            runLock.unlock();
        }
    }
}
