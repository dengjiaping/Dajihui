package com.mzhou.merchant.model;

import java.io.Serializable;
public class JobBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String position;  
	private String content;  
	private String contact;
	private String phone;
	private String ctime;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPosition()
	{
		return position;
	}
	public void setPosition(String position)
	{
		this.position = position;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getCtime()
	{
		return ctime;
	}
	public void setCtime(String ctime)
	{
		this.ctime = ctime;
	}
	@Override
	public String toString()
	{
		return "JobBean [id=" + id + ", position=" + position + ", content=" + content + ", contact=" + contact + ", phone=" + phone + ", ctime=" + ctime + "]";
	}
	public JobBean(String id, String position, String content, String contact, String phone, String ctime)
	{
		super();
		this.id = id;
		this.position = position;
		this.content = content;
		this.contact = contact;
		this.phone = phone;
		this.ctime = ctime;
	}
	public JobBean()
	{
		super();
	}
	 
	
}
