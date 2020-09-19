package com.jiantai.entity;

import java.util.Date;

/**
 * msds表模型
 */
public class Msds {
    private Integer id;
    //c_idint(11) NULL公司id
    private Integer cId;
    //namevarchar(64) NULLmsds对应的物料名称
    private String name;
    //srcvarchar(128) NULLpdf存放在磁盘的位置
    private String src;
    //create_timetimestamp NOT NULL
    private Date createTime;
    //update_timetimestamp NOT NULL
    private Date updateTime;
    //公司简称--用于管理员查询时
    private String companyShortName;

    @Override
    public String toString() {
        return "Msds{" +
                "id=" + id +
                ", cId=" + cId +
                ", name='" + name + '\'' +
                ", src='" + src + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", companyShortName='" + companyShortName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }
}
