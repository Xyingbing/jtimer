package com.itzhoujun.jtimer.utils;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

public class TableSet<T> extends Page {

    public TableSet(int current, int size) {
        super(current, size);
    }

    public TableSet setRecords(List records) {
        return (TableSet)super.setRecords(records);
    }

    public Response response(){
        Response r = new Response(0,this.getRecords(),"","");
        r.setCount((int)this.getTotal());
        return r;
    }

}
