package com.mzhou.merchant.model;

import java.io.Serializable;
public class GetNewPwBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String status; 
	private String msg;
	private String info;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "GetNewPwBean [status=" + status + ", msg=" + msg + ", info=" + info + "]";
	}
	public GetNewPwBean(String status, String msg, String info) {
		super();
		this.status = status;
		this.msg = msg;
		this.info = info;
	}
	public GetNewPwBean() {
		super();
	}
 
  
	 
 
}
