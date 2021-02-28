package com.windsnowli.workserver.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，允许跨域
 *
 * @author windSnowLi
 */
@Component
public class CrosInterceptor implements HandlerInterceptor {
    final public static String OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {


        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization,token,Accept,X-Requested-With");

        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        String method = httpServletRequest.getMethod();

        if (OPTIONS.equals(method)) {
            httpServletResponse.setStatus(200);
            return false;
        }

        return true;
    }

}