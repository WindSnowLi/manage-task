package com.windsnowli.workserver.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.windsnowli.workserver.annotation.PassToken;
import com.windsnowli.workserver.annotation.UserLoginToken;
import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.services.impl.UserServiceImpl;
import com.windsnowli.workserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author windSnowLi
 */
@Component
public class PassTokenInterceptor implements HandlerInterceptor {

    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class) || method.getDeclaringClass().isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class) == null ? method.getDeclaringClass().getAnnotation(UserLoginToken.class) : null;
            if (userLoginToken == null) {
                return false;
            }
            if (userLoginToken.required()) {
                // 从 http 请求头中取出 token
                String token = request.getHeader("token");
                // 执行认证
                if (token == null) {
                    response.getWriter().println(Msg.getFailMsg());
                    return false;
                }
                // 获取 token 中的 user id
                int userId = JwtUtils.getTokenUserId(token);
                User user = userService.findIdUser(userId);
                if (user == null) {
                    response.getWriter().println(Msg.getFailMsg());
                    return false;
                }
                // 验证 token
                try {
                    JwtUtils.verifyToken(token);
                } catch (JWTVerificationException e) {
                    response.getWriter().println(Msg.getFailMsg());
                    return false;
                }
            }
        }
        return true;
    }
}