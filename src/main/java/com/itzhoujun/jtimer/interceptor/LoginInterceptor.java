package com.itzhoujun.jtimer.interceptor;

import com.itzhoujun.jtimer.exception.NotLoginException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Object obj = request.getSession().getAttribute("admin_user");
        if(obj != null) return true;
        String requestWith = request.getHeader("x-requested-with");
        if(requestWith != null && requestWith.equalsIgnoreCase("XMLHttpRequest")){
            //ajax请求
            throw new NotLoginException();
        }else{
            response.sendRedirect("/public/login");
        }
        return false;
    }
}
