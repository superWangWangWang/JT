package com.jiantai.vo;

import java.util.List;

public class ResultVO {
    //前端需要的json数据模型
    private Integer code;//错误码  layui的数据表格返回0为正常 1 为不正常 ，我自己定义的接口一般是0异常1正常
    private String msg;//信息
    private Integer count;//总条数
    private List data;//集合数据

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
