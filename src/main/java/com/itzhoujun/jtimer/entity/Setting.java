package com.itzhoujun.jtimer.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting {

    private Integer id;
    private String name;
    private String title;
    private String value;
    private Integer group;
    private Integer type;
    private String remark;
    private String options;
    private Integer sort;
    @TableField(exist = false)
    private List<Map<String,String>> optionList = new ArrayList<>();

    public List<Map<String, String>> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Map<String, String>> optionList) {
        this.optionList = optionList;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
