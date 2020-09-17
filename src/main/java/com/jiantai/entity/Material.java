package com.jiantai.entity;

import java.util.Date;

/**
 * 物料模型
 */
public class Material {
	private Integer id;
	private String name;
	private String unit;
	private String unitCn;
	private String remarks;
	private Date createTime;
	private Double used;//使用量，为了回显，这个不是表的字段

	//private String msds_filename;


	@Override
	public String toString() {
		return "Material{" +
				"id=" + id +
				", name='" + name + '\'' +
				", unit='" + unit + '\'' +
				", unitCn='" + unitCn + '\'' +
				", remarks='" + remarks + '\'' +
				", createTime=" + createTime +
				", used=" + used +
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

	public Double getUsed() {
		return used;
	}

	public void setUsed(Double used) {
		this.used = used;
	}
}
