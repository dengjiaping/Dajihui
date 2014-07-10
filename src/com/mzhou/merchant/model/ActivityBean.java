package com.mzhou.merchant.model;

import java.io.Serializable;
public class ActivityBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String title;  
	private String content;  
	private String address;
	private String ctime;
	private String lasttime;
	private String applytime;
	private String contact;
	private String phone;
	private String uid;
	private String is_en;
	 
	public ActivityBean(String id, String title, String content,
			String address, String ctime, String lasttime, String applytime,
			String contact, String phone, String uid, String is_en) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.address = address;
		this.ctime = ctime;
		this.lasttime = lasttime;
		this.applytime = applytime;
		this.contact = contact;
		this.phone = phone;
		this.uid = uid;
		this.is_en = is_en;
	}

	@Override
	public String toString() {
		return "ActivityBean [id=" + id + ", title=" + title + ", content="
				+ content + ", address=" + address + ", ctime=" + ctime
				+ ", lasttime=" + lasttime + ", applytime=" + applytime
				+ ", contact=" + contact + ", phone=" + phone + ", uid=" + uid
				+ ", is_en=" + is_en + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getApplytime() {
		return applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIs_en() {
		return is_en;
	}

	public void setIs_en(String is_en) {
		this.is_en = is_en;
	}

	public ActivityBean() {
		super();
	}
	 
	
}
