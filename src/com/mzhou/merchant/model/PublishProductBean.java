package com.mzhou.merchant.model;

import java.io.Serializable;
public class PublishProductBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String status;  
	private String msg;
	public PublishProductBean()
	{
		super();
	}
	public PublishProductBean(String id, String status, String msg)
	{
		super();
		this.id = id;
		this.status = status;
		this.msg = msg;
	}
	@Override
	public String toString()
	{
		return "ProductsBean [id=" + id + ", status=" + status + ", msg=" + msg + "]";
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	 
}
