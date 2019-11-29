package com.itzhoujun.jtimer.controller;

import com.itzhoujun.jtimer.entity.User;
import com.itzhoujun.jtimer.mapper.UserMapper;
import com.itzhoujun.jtimer.utils.CommonUtil;
import com.itzhoujun.jtimer.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin_user");
        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(HttpSession session){
        User user = (User)session.getAttribute("admin_user");
        if(user == null){
            return "public/login";
        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(@Valid User user, BindingResult bindResult, HttpSession session) throws NoSuchAlgorithmException {

        if(bindResult.hasErrors()){
            return Response.error(bindResult.getFieldError().getDefaultMessage());
        }
        if(user.getUsername() == "" || user.getPassword() == ""){
            return Response.error("用户或密码有误");
        }

        String password = user.getPassword();
        user.setPassword(null);

        User existUser = userMapper.selectOne(user);
        if(null == existUser){
            return Response.error("用户或密码有误");
        }else{
            String encryPwd = CommonUtil.encryPwd(password,existUser.getSalt());
            if(!existUser.getPassword().equals(encryPwd)){
                return Response.error("用户或密码有误");
            }
            session.setAttribute("admin_user",existUser);
            return Response.successAndJump("/");
        }
    }
}
