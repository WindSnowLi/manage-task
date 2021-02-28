package com.windsnowli.workserver.interceptor;

import com.windsnowli.workserver.annotation.PowerLevel;
import com.windsnowli.workserver.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author windSnowLi
 */
@Component
public class PowerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PowerLevel.class)) {
            PowerLevel powerLevel = method.getAnnotation(PowerLevel.class);
            String token = request.getHeader("token");
            int power = JwtUtils.getTokenPower(token);
            return power >= powerLevel.value();
        }
        return true;
    }
}
