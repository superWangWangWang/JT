package com.jiantai.entity;

/**
 * 产品产量表模型
 */
public class Product {
    //id       int(11)      (NULL)              NO      PRI     (NULL)   auto_increment  select,insert,update,references
    private Integer id;
    //name     varchar(64)  utf8mb4_general_ci  YES             (NULL)                   select,insert,update,references  产品类型
    private String name;
    //unit     varchar(16)  utf8mb4_general_ci  YES             (NULL)                   select,insert,update,references  单位
    private String unit;
    //unit_cn  varchar(16)  utf8mb4_general_ci  YES             (NULL)                   select,insert,update,references  中文单位
    private String unitCn;
    private Double output;
    private String outputTime;
    private String companyShortName;
    private String second;
    private String first;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", unitCn='" + unitCn + '\'' +
                ", output=" + output +
                ", outputTime='" + outputTime + '\'' +
                ", companyShortName='" + companyShortName + '\'' +
                ", second='" + second + '\'' +
                ", first='" + first + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitCn() {
        return unitCn;
    }

    public void setUnitCn(String unitCn) {
        this.unitCn = unitCn;
    }

    public Double getOutput() {
        return output;
    }

    public void setOutput(Double output) {
        this.output = output;
    }

    public String getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(String outputTime) {
        this.outputTime = outputTime;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }
}
