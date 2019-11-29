package com.itzhoujun.jtimer.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


public class CronTask {

    private Integer id;
    @Min(value = 1, message = "请选择分类")
    private Integer cateId;
    @NotEmpty(message = "请输入cron表达式")
    private String cronExpression;
    @NotEmpty(message = "请输入命令")
    private String cmd;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    @TableField(exist = false)
    private String cateName;

    public CronTask(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public boolean equals(Object obj) {

        if(null == obj) return false;

        if(obj instanceof CronTask){
            CronTask task = (CronTask)obj;
            return this.getCmd().equals(task.getCmd())
                && this.getCronExpression().equals(task.getCronExpression())
                && this.getStatus().equals(task.getStatus());
        }else{
            return false;
        }
    }
}
