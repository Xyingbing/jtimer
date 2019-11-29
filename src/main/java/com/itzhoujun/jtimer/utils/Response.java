package com.itzhoujun.jtimer.utils;


public class Response{

    private Integer code;
    private Object  data;
    private String  msg;
    private String url;
    private Integer count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Response(Integer code, Object data, String msg, String url) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.url = url;
    }

    public static Response dataset(Object data){
        return new Response(1,data,"操作成功","");
    }

    public static Response success(){
        return Response.success("操作成功");
    }

    public static Response success(String msg){
        return Response.success(msg,null);
    }

    public static Response success(String msg,Object data){
        return new Response(1,data,msg,"");
    }

    public static Response successAndJump(String url,String msg){
        return new Response(1,null,msg,url);
    }

    public static Response successAndJump(String url){
        return Response.successAndJump(url,"操作成功");
    }

    public static Response error(String msg){
        return new Response(0,null,msg,"");
    }

    public static Response error(){
        return Response.error("操作失败");
    }
}
