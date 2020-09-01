package com.jiantai.entity;

public class MaterialRemember {
    //idint(11) NOT NULL
    private Integer id;
    //c_idint(11) NULL公司id
    private Integer cId;
    //namevarchar(64) NULL物料名
    private String name;
    //stateint(1) NULL0=不显示，1=显示
    private Integer state;

    @Override
    public String toString() {
        return "MaterialsRemember{" +
                "id=" + id +
                ", cId=" + cId +
                ", name='" + name + '\'' +
                ", state=" + state +
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
