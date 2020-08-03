package com.jiantai.entity;

public class JtMaterialEvidence {
	private int id;
	private String filename;
	private String datetime;
	private String company;
	public JtMaterialEvidence() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "JtMaterialEvidence [id=" + id + ", filename=" + filename + ", datetime=" + datetime + ", company="
				+ company + "]";
	}
	
	
}
