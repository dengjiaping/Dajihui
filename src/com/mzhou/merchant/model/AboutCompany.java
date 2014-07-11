package com.mzhou.merchant.model;

import java.io.Serializable;

public class AboutCompany implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cotitle;
	private String cocontent;
	private String copic; 

	public String getCotitle() {
		return cotitle;
	}

	public void setCotitle(String cotitle) {
		this.cotitle = cotitle;
	}

	public String getCocontent() {
		return cocontent;
	}

	public void setCocontent(String cocontent) {
		this.cocontent = cocontent;
	}

	public String getCopic() {
		return copic;
	}

	public void setCopic(String copic) {
		this.copic = copic;
	}

	@Override
	public String toString() {
		return "AboutCompany [cotitle=" + cotitle + ", cocontent=" + cocontent + ", copic=" + copic + "]";
	}

	public AboutCompany(String cotitle, String cocontent, String copic) {
		super();
		this.cotitle = cotitle;
		this.cocontent = cocontent;
		this.copic = copic;
	}

	public AboutCompany() {
		super();
	}

}
