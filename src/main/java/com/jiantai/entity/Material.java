package com.jiantai.entity;

import java.util.Date;

public class Material {
	private String name;
	private String unit;
	private String unitCn;
	private String remarks;
	private Date createTime;
	private Double used;//使用量，为了回显，这个不是表的字段


	private String 物料名称;
	private String 主要成分;
	private String 物料类别;
	private String 含量;
	private String 单位;
	private String 使用工序;
	private String 备注;
	private String msds_filename;

	
	public String getMsds_filename() {
		return msds_filename;
	}

	public void setMsds_filename(String msds_filename) {
		this.msds_filename = msds_filename;
	}

	public Material() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String get含量() {
		return 含量;
	}

	public void set含量(String 含量) {
		this.含量 = 含量;
	}

	public String get物料名称() {
		return 物料名称;
	}
	public void set物料名称(String 物料名称) {
		this.物料名称 = 物料名称;
	}
	public String get主要成分() {
		return 主要成分;
	}
	public void set主要成分(String 主要成分) {
		this.主要成分 = 主要成分;
	}
	public String get物料类别() {
		return 物料类别;
	}
	public void set物料类别(String 物料类别) {
		this.物料类别 = 物料类别;
	}
	public String get单位() {
		return 单位;
	}
	public void set单位(String 单位) {
		this.单位 = 单位;
	}
	public String get使用工序() {
		return 使用工序;
	}
	public void set使用工序(String 使用工序) {
		this.使用工序 = 使用工序;
	}
	public String get备注() {
		return 备注;
	}
	public void set备注(String 备注) {
		this.备注 = 备注;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUnitCn() {
		return unitCn;
	}

	public void setUnitCn(String unitCn) {
		this.unitCn = unitCn;
	}

	public Double getUsed() {
		return used;
	}

	public void setUsed(Double used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "Material{" +
				"name='" + name + '\'' +
				", unit='" + unit + '\'' +
				", unitCn='" + unitCn + '\'' +
				", remarks='" + remarks + '\'' +
				", createTime=" + createTime +
				", used=" + used +
				", 物料名称='" + 物料名称 + '\'' +
				", 主要成分='" + 主要成分 + '\'' +
				", 物料类别='" + 物料类别 + '\'' +
				", 含量='" + 含量 + '\'' +
				", 单位='" + 单位 + '\'' +
				", 使用工序='" + 使用工序 + '\'' +
				", 备注='" + 备注 + '\'' +
				", msds_filename='" + msds_filename + '\'' +
				'}';
	}
}
