package com.jiantai.entity;

import java.util.Date;

public class JTMsdsUpload {
    private int id;
    private Date updateDatetime;
    private String material;
    private String company;
    private String msdsFilename;

    public JTMsdsUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMsdsFilename() {
        return msdsFilename;
    }

    public void setMsdsFilename(String msdsFilename) {
        this.msdsFilename = msdsFilename;
    }

    @Override
    public String toString() {
        return "JTMsdsUpload{" +
                "id=" + id +
                ", updateDatetime=" + updateDatetime +
                ", material='" + material + '\'' +
                ", company='" + company + '\'' +
                ", msdsFilename='" + msdsFilename + '\'' +
                '}';
    }
}
