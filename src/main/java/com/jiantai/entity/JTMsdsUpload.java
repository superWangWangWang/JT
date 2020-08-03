package com.jiantai.entity;

public class JTMsdsUpload {
	private int id;
	private String updateDatetime;
	private String materiel;
	private String company;
	private String msdsFilename;

	public JTMsdsUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getMateriel() {
		return materiel;
	}

	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMsdsFilename() {
		return msdsFilename;
	}

	public void setMsdsFilename(String msdsFilename) {
		this.msdsFilename = msdsFilename;
	}

	@Override
	public String toString() {
		return "JTMsdsUpload [id=" + id + ", updateDatetime=" + updateDatetime + ", materiel=" + materiel + ", company="
				+ company + ", msdsFilename=" + msdsFilename + "]";
	}

	

}
