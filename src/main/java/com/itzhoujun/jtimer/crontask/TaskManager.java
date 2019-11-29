package com.itzhoujun.jtimer.crontask;

import com.baomidou.mybatisplus.mapper.Condition;
import com.itzhoujun.jtimer.entity.CronTask;
import com.itzhoujun.jtimer.entity.CronTaskLog;
import com.itzhoujun.jtimer.entity.Setting;
import com.itzhoujun.jtimer.mapper.CronTaskLogMapper;
import com.itzhoujun.jtimer.mapper.CronTaskMapper;
import com.itzhoujun.jtimer.mapper.SettingMapper;
import com.itzhoujun.jtimer.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

@Component
public class TaskManager {

    private static Map<Integer,CronTask> taskMap = new HashMap<>();

    private static Map<Integer,Future<?>> futureMap = new HashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private CronTaskMapper cronTaskMapper;

    @Autowired
    private CronTaskLogMapper cronTaskLogMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    @Scheduled(fixedRate = 84600000)
    private void deleteLogs(){
        Setting setting = new Setting();
        setting.setName("cron_task_log_save_day");
        setting = settingMapper.selectOne(setting);
        Integer day = Integer.valueOf(setting.getValue());
        if(day > 0){
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -day);
            cronTaskLogMapper.delete(Condition.create().
                    lt("create_time",CommonUtil.formatDate(calendar.getTime())));
        }

    }

    @Scheduled(fixedRate = 5000)
    private void loadTask(){
        List<CronTask> list = cronTaskMapper.selectList(null);
        Set<Integer> idSet = new HashSet<>();
        for (CronTask task : list){
            Integer id = task.getId();
            idSet.add(id);
            boolean needStart = false;
            if(taskMap.containsKey(id)){
                //比较任务是否有改变
                if(!task.equals(taskMap.get(id))){
                    needStart = true;
                    stop(id);
                }
            }else{
                needStart = true;
            }
            if(needStart && task.getStatus().equals(1)){
                start(task);
            }
        }
        //是否有任务删除
        Iterator<Map.Entry<Integer, CronTask>> entries = taskMap.entrySet().iterator();
        while (entries.hasNext()){
            Integer key = entries.next().getKey();
            if(!idSet.contains(key)){
                //该任务被删除
                stop(key);
            }
        }
    }
    private void start(CronTask task){

        Runnable runner = new TaskThread(task);
        CronTrigger trigger = new CronTrigger(task.getCronExpression());

        Future<?> future = threadPoolTaskScheduler.schedule(runner,trigger);

        futureMap.put(task.getId(),future);
        taskMap.put(task.getId(),task);
    }

    private void stop(Integer id){

        taskMap.remove(id);
        Future f = futureMap.get(id);
        if(f != null){
            f.cancel(false);
            futureMap.remove(id);
        }
    }

    private class TaskThread implements Runnable{

        private CronTask task;

        public TaskThread(CronTask task){
            this.task = task;
        }

        @Override
        public void run() {

            Date createTime = new Date();
            long begin = System.currentTimeMillis();
            try {
                CronTaskLog log = new CronTaskLog();
                log.setCreateTime(createTime);
                log.setCmd(task.getCmd());
                log.setCtId(task.getId());

                new TaskExecutor(log,begin).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class TaskExecutor extends Thread{

        private CronTaskLog log;
        private long begin;

        public TaskExecutor(CronTaskLog log, long begin){
            this.log = log;
            this.begin = begin;
        }

        public void run(){
            String[] cmds = new String[]{"cmd","/c",log.getCmd()};

            try {
                Process process = Runtime.getRuntime().exec(cmds);
                Float spendTime = new Float((System.currentTimeMillis() - begin) / 1000.0);
                log.setSpendTime(spendTime);
                cronTaskLogMapper.insert(log);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}


