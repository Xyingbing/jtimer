package com.itzhoujun.jtimer.controller;

import com.itzhoujun.jtimer.entity.User;
import com.itzhoujun.jtimer.mapper.UserMapper;
import com.itzhoujun.jtimer.utils.CommonUtil;
import com.itzhoujun.jtimer.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/alterPwd")
    public String alterPwd(){
        return "user/alter_pwd";
    }
    @PostMapping("/doAlterPwd")
    @ResponseBody
    public Object doAlterPwd(String password, String confirmPassword, String newPassword, HttpSession session) throws NoSuchAlgorithmException {

        User user = (User)session.getAttribute("admin_user");

        if(user == null){
            return Response.error("请先登录");
        }

        if(!confirmPassword.equals(newPassword)){
            return Response.error("两次输入密码不一致");
        }

        if(newPassword.length() < 5){
            return Response.error("密码长度至少6位");
        }

        String encryPwd = CommonUtil.encryPwd(password,user.getSalt());
        if(!encryPwd.equals(user.getPassword())){
            return Response.error("旧密码错误");
        }

        User updateUser = new User();
        String newEncryPwd = CommonUtil.encryPwd(newPassword,user.getSalt());
        updateUser.setId(user.getId());
        updateUser.setPassword(newEncryPwd);
        int num = userMapper.updateById(updateUser);
        if(num > 0){
            user.setPassword(newEncryPwd);
            session.setAttribute("admin_user",user);
            return Response.success();
        }else{
            return Response.error();
        }
    }

}
