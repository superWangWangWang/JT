package com.jiantai.entity;

import java.util.Date;

/**
 * 佐证模型
 */
public class Evidence {
    //id             int(11)
    private Integer id;
    //c_id           int(11)        公司id
    private Integer cId;
    //src            varchar(64)    保存在磁盘上的位置
    private String src;
    //evidence_time  varchar(16)    佐证时间
    private String evidenceTime;
    //upload_time    timestamp
    private Date uploadTime;

    @Override
    public String toString() {
        return "Evidence{" +
                "id=" + id +
                ", cId=" + cId +
                ", src='" + src + '\'' +
                ", evidenceTime='" + evidenceTime + '\'' +
                ", uploadTime=" + uploadTime +
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
}
