package com.jiantai.entity;

import java.util.Date;

/**
 * 物料用量表模型
 */
public class MaterialsUsed {
    //id
    private Integer id;
    //c_id           公司id
    private String cName;//公司名

    private Integer cId;
    //name           物料名
    private String name;
    //used           使用用量
    private Double used;
    //used_time      物料使用的年月
    private String usedTime;
    private String unitCn;
    //create_time
    private Date createTime;
    //update_time
    private Date updateTime;

    @Override
    public String toString() {
        return "MaterialsUsed{" +
                "id=" + id +
                ", cName='" + cName + '\'' +
                ", cId=" + cId +
                ", name='" + name + '\'' +
                ", used=" + used +
                ", usedTime='" + usedTime + '\'' +
                ", unitCn='" + unitCn + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
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

    public Double getUsed() {
        return used;
    }

    public void setUsed(Double used) {
        this.used = used;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getUnitCn() {
        return unitCn;
    }

    public void setUnitCn(String unitCn) {
        this.unitCn = unitCn;
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
}
