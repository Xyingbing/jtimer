package com.itzhoujun.jtimer.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.itzhoujun.jtimer.entity.Setting;
import com.itzhoujun.jtimer.mapper.SettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/Setting")
public class SettingController {

    @Autowired
    private SettingMapper settingMapper;

    @GetMapping("/index")
    public String index(ModelMap map){
        List<Setting> list = settingMapper.selectList(Condition.create()
                .orderBy("`group`",true)
                .orderBy("sort",true));

        List<Setting> sysSettingList = new ArrayList<>();
        List<Setting> userSettingList = new ArrayList<>();

        for (Setting setting : list){
            if(setting.getType().equals(3)){
                //下拉框
                String[] options = setting.getOptions().split("\n");
                if(options.length == 2){
                    for (String option : options){
                        String[] optionMap = option.split(":");
                        if(optionMap.length == 2){
                            Map<String,String> om = new HashMap<>();
                            om.put("name",optionMap[1]);
                            om.put("value",optionMap[0]);
                            setting.getOptionList().add(om);
                        }
                    }
                }
            }
            if(setting.getGroup().equals(1)){
                sysSettingList.add(setting);
            }else{
                userSettingList.add(setting);
            }
        }
        map.addAttribute("sysSettings",sysSettingList);
        map.addAttribute("userSettings",userSettingList);
        return "setting/index";
    }
    @PostMapping("/postSetting")
    public String postSetting(HttpServletRequest request){
        List<Setting> list = settingMapper.selectList(null);
        for (Setting setting : list){
            String value = request.getParameter(setting.getName());
            if(value != null){
                Setting newSetting = new Setting();
                newSetting.setId(setting.getId());
                newSetting.setValue(value);
                settingMapper.updateValueById(newSetting);
            }
        }
        return "redirect:index";
    }
}
