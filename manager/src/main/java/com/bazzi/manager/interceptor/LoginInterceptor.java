package com.bazzi.manager.interceptor;

import com.bazzi.common.annotation.AllowAccess;
import com.bazzi.common.util.JsonUtil;
import com.bazzi.manager.service.UserService;
import com.bazzi.manager.util.Constant;
import com.bazzi.manager.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private NamedThreadLocal<Long> timeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");

    @Resource
    private UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        timeThreadLocal.set(System.currentTimeMillis());
        userService.putUserIfExists(request.getHeader(Constant.TOKEN_HEADER));
        if (handler instanceof HandlerMethod) {
            boolean allowAccess = ((HandlerMethod) handler).hasMethodAnnotation(AllowAccess.class);
            if (!allowAccess && ThreadLocalUtil.getUser() == null) {
                try {
                    request.setAttribute("originalUri", request.getRequestURI());
                    request.setAttribute("originalMethod", request.getMethod());
                    String param = getBodyByInputStream(request.getInputStream());
                    request.setAttribute("originalParam", "".equals(param) ? getQueryJson(request) : param);
                    request.setAttribute(Constant.TOKEN_HEADER, request.getHeader(Constant.TOKEN_HEADER));
                    request.getRequestDispatcher("/u/toLogin").forward(request, response);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return false;
            }
        }
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String actualURI = (String) request.getAttribute("originalUri");
        String actualMethod = (String) request.getAttribute("originalMethod");
        String actualParam = (String) request.getAttribute("originalParam");
        log.info("Completed--->URI:{}, Method:{},TokenHeader:{}, Parameter:{}, Result:{}, Time:{}ms",
                actualURI != null && !"".equals(actualURI) ? actualURI : request.getRequestURI(),
                actualMethod != null && !"".equals(actualMethod) ? actualMethod : request.getMethod(),
                request.getHeader(Constant.TOKEN_HEADER),
                actualURI != null && !"".equals(actualURI) ? actualParam : JsonUtil.toJsonString(ThreadLocalUtil.getParameter()),
                JsonUtil.toJsonString(ThreadLocalUtil.getResult()),
                System.currentTimeMillis() - timeThreadLocal.get());
        ThreadLocalUtil.clearThreadLocal();
    }

    private String getBodyByInputStream(ServletInputStream ris) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] byteArr = new byte[1024];
            int len;
            while ((len = ris.read(byteArr)) > 0) {
                sb.append(new String(byteArr, 0, len, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    private String getQueryJson(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterVal = request.getParameter(parameterName);
            paramMap.put(parameterName, parameterVal);
        }
        return paramMap.size() > 0 ? JsonUtil.toJsonString(paramMap) : null;
    }

}
