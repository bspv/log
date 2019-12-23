package com.bazzi.manager.util;

import com.bazzi.manager.model.User;
import com.bazzi.manager.vo.response.UserResponseVO;

import java.util.Map;

public final class ThreadLocalUtil {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<UserResponseVO> userInfoThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Object>> parameterThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Object> resultThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void setUserInfo(UserResponseVO userResponseVO) {
        userInfoThreadLocal.set(userResponseVO);
    }

    public static UserResponseVO getUserInfo() {
        return userInfoThreadLocal.get();
    }

    public static void setParameter(Map<String, Object> parameter) {
        parameterThreadLocal.set(parameter);
    }

    public static Map<String, Object> getParameter() {
        return parameterThreadLocal.get();
    }

    public static void setResult(Object result) {
        resultThreadLocal.set(result);
    }

    public static Object getResult() {
        return resultThreadLocal.get();
    }

    public static void clearThreadLocal() {
        setUser(null);
        setUserInfo(null);
        setParameter(null);
        setResult(null);
    }

}
