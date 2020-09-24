package com.jiantai.entity;

import java.util.Date;

public class MaterielEvidence {
    //idint(11) NOT NULL
    private Integer id;
    //c_idint(11) NULL公司id
    private Integer cId;
    //srcvarchar(256) NULL保存在磁盘上的位置
    private String src;
    //evidence_timevarchar(16) NULL佐证时间
    private String evidenceTime;
    //upload_timetimestamp NOT NULL
    private Date uploadTime;
    private String companyShortName;

    @Override
    public String toString() {
        return "MaterielEvidence{" +
                "id=" + id +
                ", cId=" + cId +
                ", src='" + src + '\'' +
                ", evidenceTime='" + evidenceTime + '\'' +
                ", uploadTime=" + uploadTime +
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getEvidenceTime() {
        return evidenceTime;
    }

    public void setEvidenceTime(String evidenceTime) {
        this.evidenceTime = evidenceTime;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }
}
