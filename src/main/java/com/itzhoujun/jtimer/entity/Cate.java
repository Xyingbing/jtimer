package com.itzhoujun.jtimer.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import javax.validation.constraints.NotEmpty;

public class Cate {

    private Integer id;

    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @TableField(exist = false)
    private Integer taskCount;

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
