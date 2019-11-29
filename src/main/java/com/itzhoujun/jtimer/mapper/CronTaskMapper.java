package com.itzhoujun.jtimer.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.itzhoujun.jtimer.entity.CronTask;

import java.util.List;

public interface CronTaskMapper extends BaseMapper<CronTask> {

    public List<CronTask> selectTaskPage(Page<CronTask> page);
}
