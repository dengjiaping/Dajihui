package com.mzhou.merchant.model;

import java.io.Serializable;
public class JobItemBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String position;  
	private String content;  
	private String company;
	private String address;
	private String ctime;
	private String lasttime;
	private String email;
	private String contact;
	private String phone;
	private String uid;
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
	public String getCompany()
	{
		return company;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getCtime()
	{
		return ctime;
	}
	public void setCtime(String ctime)
	{
		this.ctime = ctime;
	}
	public String getLasttime()
	{
		return lasttime;
	}
	public void setLasttime(String lasttime)
	{
		this.lasttime = lasttime;
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
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getUid()
	{
		return uid;
	}
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	@Override
	public String toString()
	{
		return "JobItemBean [id=" + id + ", position=" + position + ", content=" + content + ", company=" + company + ", address=" + address + ", ctime=" + ctime + ", lasttime=" + lasttime + ", email=" + email + ", contact=" + contact + ", phone=" + phone + ", uid=" + uid + "]";
	}
	public JobItemBean(String id, String position, String content, String company, String address, String ctime, String lasttime, String email, String contact, String phone, String uid)
	{
		super();
		this.id = id;
		this.position = position;
		this.content = content;
		this.company = company;
		this.address = address;
		this.ctime = ctime;
		this.lasttime = lasttime;
		this.email = email;
		this.contact = contact;
		this.phone = phone;
		this.uid = uid;
	}
	public JobItemBean()
	{
		super();
	}
	 
}
