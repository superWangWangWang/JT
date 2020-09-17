package com.jiantai.entity;

import java.util.Date;

/**
 * 用户表模型
 */
public class User {
    //id                    int(11)       主键id
    private Integer id;
    //user_name             varchar(32)   登录用户名
    private String userName;
    //password              varchar(32)   登录密码
    private String password;
    //number                varchar(32)   编号
    private String number;
    //company_short_name    varchar(32)   简称
    private String companyShortName;
    //company_name          varchar(128)  全称
    private String companyName;
    //legal_person          varchar(32)   法人代表
    private String legalPerson;
    //address               varchar(128)  联系地址
    private String address;
    //telephone             varchar(32)   公司固话
    private String telephone;
    //fax                   varchar(32)   传真
    private String fax;
    //contact               varchar(32)   联系人
    private String contacts;
    //contact_mobile_phone  varchar(32)   联系人手机号
    private String contactsMobilePhone;
    //social_credit_code    varchar(64)   统一社会信用代码
    private String socialCreditCode;
    //employees             int(11)       企业人数
    private Integer employees;
    //opening_time          varchar(32)   开业时间
    private String openingTime;
    //product_type          varchar(32)   产品类型
    private String productType;
    //location              varchar(16)   园区位置
    private String location;
    //update_time           timestamp     更新时间
    private Date updateTime;
    //plane_figure          varchar(64)   平面图
    private String planeFigure;
    //product_list          varchar(64)   产品名录
    private String productionEquipmentList;
    //modify                int(1)        跨月份修改/删除权限 (0无权限/1有权限)
    private Integer modify;
    //type                  int(1)        身份 0=普通公司 1 = 超级管理员 可以登录管理后台
    private Integer type;
    //state                 int(1)        1=启用，0=不允许登录
    private Integer state;
    //remarks               varchar(256)  备注
    private String remarks;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", companyShortName='" + companyShortName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", legalPerson='" + legalPerson + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsMobilePhone='" + contactsMobilePhone + '\'' +
                ", socialCreditCode='" + socialCreditCode + '\'' +
                ", employees=" + employees +
                ", openingTime='" + openingTime + '\'' +
                ", productType='" + productType + '\'' +
                ", location='" + location + '\'' +
                ", updateTime=" + updateTime +
                ", planeFigure='" + planeFigure + '\'' +
                ", productionEquipmentList='" + productionEquipmentList + '\'' +
                ", modify=" + modify +
                ", type=" + type +
                ", state=" + state +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsMobilePhone() {
        return contactsMobilePhone;
    }

    public void setContactsMobilePhone(String contactsMobilePhone) {
        this.contactsMobilePhone = contactsMobilePhone;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPlaneFigure() {
        return planeFigure;
    }

    public void setPlaneFigure(String planeFigure) {
        this.planeFigure = planeFigure;
    }

    public String getProductionEquipmentList() {
        return productionEquipmentList;
    }

    public void setProductionEquipmentList(String productionEquipmentList) {
        this.productionEquipmentList = productionEquipmentList;
    }

    public Integer getModify() {
        return modify;
    }

    public void setModify(Integer modify) {
        this.modify = modify;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
