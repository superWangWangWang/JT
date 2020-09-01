package com.jiantai.entity;

/**
 * 设备参数模型
 */
public class Equipment {
    //id                       int(11)
    private Integer id;
    //type                     int(2)          类型(后期再加) 1=空压机，2=风机，3=电机
    private Integer type;
    //kinds                    varchar(64)     型号(自己填)-NIASD
    private String kinds;
    //total                    int(11)         总台数
    private Integer total;
    //load_pressure            decimal(11,2)   加载压力-MPA
    private Double loadPressure;
    //unload_pressure          decimal(11,2)   卸载压力-MPA
    private Double unloadPressure;
    //power                    decimal(11,2)   功率-KW
    private Double power;
    //exhaust_temperature      decimal(11,2)   排气温度-℃
    private Double exhaustTemperature;
    //load_rate                decimal(11,2)   负载率-%
    private Double loadRate;
    //lubricating_oil_used     decimal(11,2)   润滑油使用量-L
    private Double lubricatingOilUsed;
    //lubricating_oil_replace  int(3)          润滑油更换周期-月/次
    private Integer lubricatingOilReplace;
    //voltage                  decimal(11,2)   电压-V
    private Double voltage;
    //electric_current         decimal(11,2)   电流-A
    private Double electricCurrent;
    //speed                    int(11)         转速-r/min
    private Integer speed;

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", type=" + type +
                ", kinds='" + kinds + '\'' +
                ", total=" + total +
                ", loadPressure=" + loadPressure +
                ", unloadPressure=" + unloadPressure +
                ", power=" + power +
                ", exhaustTemperature=" + exhaustTemperature +
                ", loadRate=" + loadRate +
                ", lubricatingOilUsed=" + lubricatingOilUsed +
                ", lubricatingOilReplace=" + lubricatingOilReplace +
                ", voltage=" + voltage +
                ", electricCurrent=" + electricCurrent +
                ", speed=" + speed +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getLoadPressure() {
        return loadPressure;
    }

    public void setLoadPressure(Double loadPressure) {
        this.loadPressure = loadPressure;
    }

    public Double getUnloadPressure() {
        return unloadPressure;
    }

    public void setUnloadPressure(Double unloadPressure) {
        this.unloadPressure = unloadPressure;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getExhaustTemperature() {
        return exhaustTemperature;
    }

    public void setExhaustTemperature(Double exhaustTemperature) {
        this.exhaustTemperature = exhaustTemperature;
    }

    public Double getLoadRate() {
        return loadRate;
    }

    public void setLoadRate(Double loadRate) {
        this.loadRate = loadRate;
    }

    public Double getLubricatingOilUsed() {
        return lubricatingOilUsed;
    }

    public void setLubricatingOilUsed(Double lubricatingOilUsed) {
        this.lubricatingOilUsed = lubricatingOilUsed;
    }

    public Integer getLubricatingOilReplace() {
        return lubricatingOilReplace;
    }

    public void setLubricatingOilReplace(Integer lubricatingOilReplace) {
        this.lubricatingOilReplace = lubricatingOilReplace;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getElectricCurrent() {
        return electricCurrent;
    }

    public void setElectricCurrent(Double electricCurrent) {
        this.electricCurrent = electricCurrent;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
