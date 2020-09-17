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
    private String companyShortName;

    public JTLog() {//无参构造
    }

    public JTLog(Integer cid ,String content) {//有参构造
        this.cid = cid;
        this.content = content;
    }

    @Override
    public String toString() {
        return "JTLog{" +
                "id=" + id +
                ", cid=" + cid +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", companyShortName='" + companyShortName + '\'' +
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

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }
}
