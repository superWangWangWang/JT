package com.jiantai.vo;

/**
 * 统计的模型
 */
public class StatisticVO {
    //公司简称
    private String name;
    //物料申报
    private int materielState;
    //凭证
    private int evidenceState;
    //产品产量
    private int productState;
    //保养记录
    private int maintainState;
    //msds
    private int MSDSState;
    //生产设备
    private int equipmentState;

    public StatisticVO() {
    }

    public StatisticVO(String name, Integer materielState, Integer evidenceState, Integer productState, Integer maintainState, Integer MSDSState, Integer equipmentState) {
        this.name = name;
        this.materielState = materielState;
        this.evidenceState = evidenceState;
        this.productState = productState;
        this.maintainState = maintainState;
        this.MSDSState = MSDSState;
        this.equipmentState = equipmentState;
    }

    @Override
    public String toString() {
        return "StatisticVO{" +
                "name='" + name + '\'' +
                ", materielState=" + materielState +
                ", evidenceState=" + evidenceState +
                ", productState=" + productState +
                ", maintainState=" + maintainState +
                ", MSDSState=" + MSDSState +
                ", equipmentState=" + equipmentState +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaterielState() {
        return materielState;
    }

    public void setMaterielState(Integer materielState) {
        this.materielState = materielState;
    }

    public Integer getEvidenceState() {
        return evidenceState;
    }

    public void setEvidenceState(Integer evidenceState) {
        this.evidenceState = evidenceState;
    }

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    public Integer getMaintainState() {
        return maintainState;
    }

    public void setMaintainState(Integer maintainState) {
        this.maintainState = maintainState;
    }

    public Integer getMSDSState() {
        return MSDSState;
    }

    public void setMSDSState(Integer MSDSState) {
        this.MSDSState = MSDSState;
    }

    public Integer getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(Integer equipmentState) {
        this.equipmentState = equipmentState;
    }
}
