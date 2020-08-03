package com.jiantai.entity;

import java.util.Date;

public class JTLog {
    //idint(11) NOT NULL
    private Integer id;
    //cidint(11) NULL公司id
    private Integer cid;
    //create_timetimestamp NOT NULL记录时间，自动记录当前时间
    private Date createTime;
    //contentvarchar(255) NULL日志内容
    private String content;

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", cid=" + cid +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
