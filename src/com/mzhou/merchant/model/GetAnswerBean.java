package com.mzhou.merchant.model;

import java.io.Serializable;

public class GetAnswerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private String oldpw;
	private String msg;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOldpw() {
		return oldpw;
	}

	public void setOldpw(String oldpw) {
		this.oldpw = oldpw;
	}

	 
	public GetAnswerBean(String status, String oldpw, String msg) {
		super();
		this.status = status;
		this.oldpw = oldpw;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "GetAnswerBean [status=" + status + ", oldpw=" + oldpw + ", msg=" + msg + "]";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public GetAnswerBean() {
		super();
	}

}
