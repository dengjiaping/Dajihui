package com.mzhou.merchant.model;

import java.io.Serializable;
public class BackBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String status; //×´Ì¬
	private String msg; //ÏûÏ¢
	private String uid; //id

 
	public BackBean(String status, String msg, String uid)
	{
		super();
		this.status = status;
		this.msg = msg;
		this.uid = uid;
	}
	@Override
	public String toString()
	{
		return "AllBean [status=" + status + ", msg=" + msg + ", uid=" + uid + "]";
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
	 
	public BackBean()
	{
		super();
	}
	
}
