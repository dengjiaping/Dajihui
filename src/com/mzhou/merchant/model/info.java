package com.mzhou.merchant.model;

import java.io.Serializable;
public class info implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String category; 
	private String username;  
	private String nickname;  
	private String contact;  
	private String password; 
	private String phonenub; 
	private String email; 
	private String company; 
	private String headurl; 
	private String address; 
	private String net; 
	private String fax;  
	private String center; 
	private String uid;
	 
	public info(String category, String username, String nickname, String contact, String password, String phonenub,
			String email, String company, String headurl, String address, String net, String fax, String center, String uid) {
		super();
		this.category = category;
		this.username = username;
		this.nickname = nickname;
		this.contact = contact;
		this.password = password;
		this.phonenub = phonenub;
		this.email = email;
		this.company = company;
		this.headurl = headurl;
		this.address = address;
		this.net = net;
		this.fax = fax;
		this.center = center;
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "info [category=" + category + ", username=" + username + ", nickname=" + nickname + ", contact=" + contact
				+ ", password=" + password + ", phonenub=" + phonenub + ", email=" + email + ", company=" + company
				+ ", headurl=" + headurl + ", address=" + address + ", net=" + net + ", fax=" + fax + ", center=" + center
				+ ", uid=" + uid + "]";
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenub() {
		return phonenub;
	}

	public void setPhonenub(String phonenub) {
		this.phonenub = phonenub;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public info() {
		super();
	}
	 

 
}
