package com.bazzi.analysis.util;

import com.bazzi.analysis.bean.StrategyConfig;
import com.bazzi.analysis.model.Project;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class StrategyCache {
    private static volatile ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static volatile ConcurrentHashMap<String, List<StrategyConfig>> monitorMap = new ConcurrentHashMap<>();

    private static volatile ConcurrentHashMap<String, Project> projectMap = new ConcurrentHashMap<>();

    /**
     * 监控策略map中是否存在对应key
     *
     * @param key key
     * @return 结果
     */
    public static boolean containsKey(String key) {
        if (key == null || "".equals(key))
            return false;
        try {
            readWriteLock.readLock().lock();
            return monitorMap.containsKey(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 从监控策略map中获取key对应的策略集合
     *
     * @param key key
     * @return 策略集合
     */
    public static List<StrategyConfig> get(String key) {
        if (key == null || "".equals(key))
            return null;
        try {
            readWriteLock.readLock().lock();
            return monitorMap.get(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 根据key获取对应项目id
     *
     * @param key key
     * @return 项目id
     */
    public static Integer getProjectId(String key) {
        if (key == null || "".equals(key))
            return -1;
        try {
            readWriteLock.readLock().lock();
            Project p = projectMap.get(key);
            return p == null ? Integer.valueOf(-1) : p.getId();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 把策略集合、项目放到对应的map中
     *
     * @param key     key
     * @param value   策略集合
     * @param project 项目
     */
    public static void put(String key, List<StrategyConfig> value, Project project) {
        if (key == null || "".equals(key) || value == null)
            return;
        try {
            readWriteLock.writeLock().lock();
            monitorMap.put(key, value);
            projectMap.put(key, project);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 从监控策略map中移除key
     *
     * @param key key
     */
    public static void remove(String key) {
        if (key == null || "".equals(key))
            return;
        try {
            readWriteLock.writeLock().lock();
            monitorMap.remove(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 项目map中是否存在对应key
     *
     * @param key key
     * @return 结果
     */
    public static boolean containsProject(String key) {
        if (key == null || "".equals(key))
            return false;
        try {
            readWriteLock.readLock().lock();
            return projectMap.containsKey(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 从项目map中移除key
     *
     * @param key key
     */
    public static void removeProject(String key) {
        if (key == null || "".equals(key))
            return;
        try {
            readWriteLock.writeLock().lock();
            projectMap.remove(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
