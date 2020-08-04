package com.jiantai.entity;

public class JTProductRecord {
	
	private Integer id;
	private String product;
	private String record_datetime;
	private String datetime;
	private String unit;
	private Double yield;
	private String company;

	@Override
	public String toString() {
		return "JTProductRecord{" +
				"id=" + id +
				", product='" + product + '\'' +
				", record_datetime='" + record_datetime + '\'' +
				", datetime='" + datetime + '\'' +
				", unit='" + unit + '\'' +
				", yield=" + yield +
				", company='" + company + '\'' +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getRecord_datetime() {
		return record_datetime;
	}

	public void setRecord_datetime(String record_datetime) {
		this.record_datetime = record_datetime;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getYield() {
		return yield;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
