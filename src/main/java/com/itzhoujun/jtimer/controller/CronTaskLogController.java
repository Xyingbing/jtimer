package com.itzhoujun.jtimer.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import com.itzhoujun.jtimer.entity.Cate;
import com.itzhoujun.jtimer.entity.CronTask;
import com.itzhoujun.jtimer.entity.CronTaskLog;
import com.itzhoujun.jtimer.mapper.CateMapper;
import com.itzhoujun.jtimer.mapper.CronTaskLogMapper;
import com.itzhoujun.jtimer.mapper.CronTaskMapper;
import com.itzhoujun.jtimer.utils.TableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/CronTaskLog")
public class CronTaskLogController {


    @Autowired
    private CronTaskLogMapper cronTaskLogMapper;

    @Autowired
    private CronTaskMapper cronTaskMapper;

    @Autowired
    private CateMapper cateMapper;

    @GetMapping("/index")
    public String index(Integer ctId,ModelMap map){
        if(ctId != null && ctId > 0){
            map.addAttribute("ctId",ctId);
        }
        map.addAttribute("cateList",cateMapper.selectList(null));
        return "cron_task_log/index";
    }


    @GetMapping("/lists")
    @ResponseBody
    public Object lists(CronTaskLog taskLog,Integer page, Integer limit){
        TableSet<CronTaskLog> datasets = new TableSet<>(page,limit);
        List descs = new ArrayList<String>();
        descs.add("id");
        datasets.setDescs(descs);
        Condition condition = null;
        if(taskLog.getCmd() != null || taskLog.getCtId() !=null){
            condition = Condition.create();
            if(taskLog.getCmd() != null){
                condition = (Condition) condition.like("cmd",taskLog.getCmd());
            }
            if(taskLog.getCtId() != null){
                condition = (Condition) condition.eq("ct_id",taskLog.getCtId());
            }
        }

        List<CronTaskLog> list = cronTaskLogMapper.selectPage(datasets,condition);
        for (CronTaskLog log : list) {
            CronTask task = cronTaskMapper.selectById(log.getCtId());
            if(task != null){
                Cate cate = cateMapper.selectById(task.getCateId());
                if(cate != null){
                    log.setCateName(cate.getName());
                }
                log.setRemark(task.getRemark());
            }

        }
        return datasets.setRecords(list).response();
    }
}
