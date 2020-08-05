package com.jiantai.vo;

import com.jiantai.entity.JTProductRecord;

import java.util.List;

public class VO<T> {

	private Integer code;
	private String msg;
	private Integer count;
	private T data;

	@Override
	public String toString() {
		return "VO{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", count=" + count +
				", data=" + data +
				'}';
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
