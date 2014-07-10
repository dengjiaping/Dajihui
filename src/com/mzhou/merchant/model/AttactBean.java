package com.mzhou.merchant.model;

import java.io.Serializable;
public class AttactBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String content;  
	private String email;
	private String contact;  
	private String ctime;
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	public String getCtime()
	{
		return ctime;
	}
	public void setCtime(String ctime)
	{
		this.ctime = ctime;
	}
	 
	public AttactBean()
	{
		super();
	}
	 
	 
}
