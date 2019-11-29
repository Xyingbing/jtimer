package com.itzhoujun.jtimer.controller;

import com.itzhoujun.jtimer.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(HttpSession session, ModelMap map){
        User user = (User)session.getAttribute("admin_user");
        if(user == null){
            return "redirect:public/login";
        }
        map.addAttribute("adminUserName",user.getUsername());
        return "index/index";
    }
    @GetMapping("/console")
    public String console(ModelMap map) throws Exception {
        String[] cmds = {"cmd","/c","tasklist"};
        Process process = Runtime.getRuntime().exec(cmds);
        InputStream in = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line + "<br />");
        }
        map.addAttribute("status",sb);
        return "index/console";
    }
}
