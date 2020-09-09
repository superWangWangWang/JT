package com.jiantai.entity;

/**
 * 设备保养及运行记录
 */
public class EquipmentMaintenance {
    //id int(11) NOT NULL
    private Integer id;
    //c_id int(11) NULL公司id
    private Integer cId;
    //maintain_time varchar(10) NULL保养月份
    private String maintainTime;
    //src varchar(256) NULL存放地址
    private String src;
    //create_time timestamp NOT NULL
    //update_time timestamp NOT NULL

    @Override
    public String toString() {
        return "EquipmentMaintenance{" +
                "id=" + id +
                ", cId=" + cId +
                ", maintainTime='" + maintainTime + '\'' +
                ", src='" + src + '\'' +
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

    public String getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(String maintainTime) {
        this.maintainTime = maintainTime;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
