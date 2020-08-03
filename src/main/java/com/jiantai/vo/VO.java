package com.jiantai.vo;

import com.jiantai.entity.JTProductRecord;

import java.util.List;

public class VO {

	private Integer code;
	private String msg;
	private Integer count;
	private List data;

	public VO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<JTProductRecord> getData() {
		return data;
	}

	public void setData(List<JTProductRecord> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ViewEntity [code=" + code + ", msg=" + msg + ", count=" + count + ", data=" + data + "]";
	}

}
