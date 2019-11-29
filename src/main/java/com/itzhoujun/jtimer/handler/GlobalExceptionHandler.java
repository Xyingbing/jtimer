package com.itzhoujun.jtimer.handler;

import com.itzhoujun.jtimer.exception.NotLoginException;
import com.itzhoujun.jtimer.utils.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    private Object notLoginHandler(HttpServletRequest request,Exception e){
        return new Response(-1,null,"请登录","");
    }
}
