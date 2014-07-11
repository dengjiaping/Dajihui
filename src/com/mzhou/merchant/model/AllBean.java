package com.mzhou.merchant.model;

import java.io.Serializable;
public class AllBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String status;
	private String msg;
	private String uid; 
	private info info;

	public info getInfo()
	{
		return info;
	}
	public void setInfo(info info)
	{
		this.info = info;
	}
	public AllBean(String status, String msg, String uid, com.mzhou.merchant.model.info info)
	{
		super();
		this.status = status;
		this.msg = msg;
		this.uid = uid;
		this.info = info;
	}
	@Override
	public String toString()
	{
		return "AllBean [status=" + status + ", msg=" + msg + ", uid=" + uid + ", info=" + info + "]";
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public String getUid()
	{
		return uid;
	}
	public void setUid(String uid)
	{
		this.uid = uid;
	}
 
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	 
	public AllBean()
	{
		super();
	}
	
}
