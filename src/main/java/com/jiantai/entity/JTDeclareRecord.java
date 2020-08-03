package com.jiantai.entity;

public class JTDeclareRecord {
	private int id;
	private String recordDatetime;
	private String datetime;
	private String material;
	private String unit;
	private String element;
	private String content;
	private String dosage;
	private String company;

	public JTDeclareRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecordDatetime() {
		return recordDatetime;
	}

	public void setRecord_datetime(String recordDatetime) {
		this.recordDatetime = recordDatetime;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "JTDeclareRecord [id=" + id + ", record_datetime=" + recordDatetime + ", datetime=" + datetime
				+ ", material=" + material + ", unit=" + unit + ", element=" + element + ", content=" + content
				+ ", dosage=" + dosage + ", company=" + company + "]";
	}

}
