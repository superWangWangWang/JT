package com.jiantai.entity;

public class CompanyInfo {
	private int id;
	private String user;
	private String password;
	private String 企业编号;
	private String 企业简称;
	private String 企业全称;
	private String 法人代表;
	private String 联系地址;
	private String 公司固话;
	private String 公司传真;
	private String 联系人;
	private String 联系人手机号;
	private String 统一社会信用代码;
	private String 企业人数;
	private String 开业时间;
	private String 产品类型;
	private String 园区位置;
	private int 是否启用;
	private String planName;
	private String productName;
	private String 备注;
	private Integer right;// 修改跨月份物料数据的权利 0代表无，1代表有
	private Integer type;

	@Override
	public String toString() {
		return "CompanyInfo{" +
				"id=" + id +
				", user='" + user + '\'' +
				", password='" + password + '\'' +
				", 企业编号='" + 企业编号 + '\'' +
				", 企业简称='" + 企业简称 + '\'' +
				", 企业全称='" + 企业全称 + '\'' +
				", 法人代表='" + 法人代表 + '\'' +
				", 联系地址='" + 联系地址 + '\'' +
				", 公司固话='" + 公司固话 + '\'' +
				", 公司传真='" + 公司传真 + '\'' +
				", 联系人='" + 联系人 + '\'' +
				", 联系人手机号='" + 联系人手机号 + '\'' +
				", 统一社会信用代码='" + 统一社会信用代码 + '\'' +
				", 企业人数='" + 企业人数 + '\'' +
				", 开业时间='" + 开业时间 + '\'' +
				", 产品类型='" + 产品类型 + '\'' +
				", 园区位置='" + 园区位置 + '\'' +
				", 是否启用=" + 是否启用 +
				", planName='" + planName + '\'' +
				", productName='" + productName + '\'' +
				", 备注='" + 备注 + '\'' +
				", right=" + right +
				", type=" + type +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String get企业编号() {
		return 企业编号;
	}

	public void set企业编号(String 企业编号) {
		this.企业编号 = 企业编号;
	}

	public String get企业简称() {
		return 企业简称;
	}

	public void set企业简称(String 企业简称) {
		this.企业简称 = 企业简称;
	}

	public String get企业全称() {
		return 企业全称;
	}

	public void set企业全称(String 企业全称) {
		this.企业全称 = 企业全称;
	}

	public String get法人代表() {
		return 法人代表;
	}

	public void set法人代表(String 法人代表) {
		this.法人代表 = 法人代表;
	}

	public String get联系地址() {
		return 联系地址;
	}

	public void set联系地址(String 联系地址) {
		this.联系地址 = 联系地址;
	}

	public String get公司固话() {
		return 公司固话;
	}

	public void set公司固话(String 公司固话) {
		this.公司固话 = 公司固话;
	}

	public String get公司传真() {
		return 公司传真;
	}

	public void set公司传真(String 公司传真) {
		this.公司传真 = 公司传真;
	}

	public String get联系人() {
		return 联系人;
	}

	public void set联系人(String 联系人) {
		this.联系人 = 联系人;
	}

	public String get联系人手机号() {
		return 联系人手机号;
	}

	public void set联系人手机号(String 联系人手机号) {
		this.联系人手机号 = 联系人手机号;
	}

	public String get统一社会信用代码() {
		return 统一社会信用代码;
	}

	public void set统一社会信用代码(String 统一社会信用代码) {
		this.统一社会信用代码 = 统一社会信用代码;
	}

	public String get企业人数() {
		return 企业人数;
	}

	public void set企业人数(String 企业人数) {
		this.企业人数 = 企业人数;
	}

	public String get开业时间() {
		return 开业时间;
	}

	public void set开业时间(String 开业时间) {
		this.开业时间 = 开业时间;
	}

	public String get产品类型() {
		return 产品类型;
	}

	public void set产品类型(String 产品类型) {
		this.产品类型 = 产品类型;
	}

	public String get园区位置() {
		return 园区位置;
	}

	public void set园区位置(String 园区位置) {
		this.园区位置 = 园区位置;
	}

	public int get是否启用() {
		return 是否启用;
	}

	public void set是否启用(int 是否启用) {
		this.是否启用 = 是否启用;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String get备注() {
		return 备注;
	}

	public void set备注(String 备注) {
		this.备注 = 备注;
	}

	public Integer getRight() {
		return right;
	}

	public void setRight(Integer right) {
		this.right = right;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
