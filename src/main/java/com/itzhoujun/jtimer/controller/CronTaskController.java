package com.itzhoujun.jtimer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.itzhoujun.jtimer.entity.CronTask;
import com.itzhoujun.jtimer.mapper.CateMapper;
import com.itzhoujun.jtimer.mapper.CronTaskMapper;
import com.itzhoujun.jtimer.utils.Response;
import com.itzhoujun.jtimer.utils.TableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/CronTask")
public class CronTaskController {

    @Autowired
    private CronTaskMapper cronTaskMapper;

    @Autowired
    private CateMapper cateMapper;

    @GetMapping("/index")
    public String index(){
        return "cron_task/index";
    }


    @PostMapping("/doUpdate")
    @ResponseBody
    public Object doUpdate(@Valid CronTask task, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Response.error(bindingResult.getFieldError().getDefaultMessage());
        }
        Integer id = task.getId();
        try{
            new CronTrigger(task.getCronExpression());
        }catch (Exception e){
            return Response.error("cron表达式有误：<br>"+e.getMessage());
        }
        int num;
        if(id != null && id > 0){
            task.setUpdateTime(new Date());
            num = cronTaskMapper.updateById(task);
        }else{
            task.setCreateTime(new Date());
            num = cronTaskMapper.insert(task);
        }
        if(num > 0){
            return Response.successAndJump("/CronTask/index");
        }else{
            return Response.error("操作失败");
        }

    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public Object changeStatus(CronTask task){
        Integer id = task.getId();
        int num = 0;
        if(id != null && id > 0){
            task.setUpdateTime(new Date());
            num = cronTaskMapper.updateById(task);
        }
        if(num > 0){
            return Response.success();
        }else{
            return Response.error();
        }

    }

    @GetMapping(value = {"/edit","/add"})
    public String edit(Integer id,ModelMap map){
        if(null != id && id > 0){
            map.addAttribute("vo",cronTaskMapper.selectById(id));
        }
        map.addAttribute("cateList",cateMapper.selectList(null));
        return "cron_task/edit";
    }

    @GetMapping("/help")
    public String help(){
        return "cron_task/help";
    }

    @GetMapping("/lists")
    @ResponseBody
    public Object lists(Integer page, Integer limit){
        TableSet<CronTask> datasets = new TableSet<>(page,limit);
        List<CronTask> list = cronTaskMapper.selectTaskPage(datasets);
        return datasets.setRecords(list).response();
    }

    @GetMapping("doDel")
    @ResponseBody
    public Object doDel(Integer id){
        int num = 0;
        if(null != id && id > 0){
            num = cronTaskMapper.deleteById(id);
        }
        if(num > 0){
            return Response.success();
        }else{
            return Response.error();
        }
    }

}
