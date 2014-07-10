package com.mzhou.merchant.model;

import java.io.Serializable;
public class AdBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String pic;  
	private String url; 
	private String type; 
	private String order_sort;
	private String position;
	private String name;
	private String category;
	 
 
	public AdBean(String id, String pic, String url, String type,
			String order_sort, String position, String name, String category) {
		super();
		this.id = id;
		this.pic = pic;
		this.url = url;
		this.type = type;
		this.order_sort = order_sort;
		this.position = position;
		this.name = name;
		this.category = category;
	}

	public AdBean(String id, String pic, String url, String type, String order_sort, String position, String name) {
		super();
		this.id = id;
		this.pic = pic;
		this.url = url;
		this.type = type;
		this.order_sort = order_sort;
		this.position = position;
		this.name = name;
	}
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "AdBean [id=" + id + ", pic=" + pic + ", url=" + url + ", type="
				+ type + ", order_sort=" + order_sort + ", position="
				+ position + ", name=" + name + ", category=" + category + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder_sort() {
		return order_sort;
	}

	public void setOrder_sort(String order_sort) {
		this.order_sort = order_sort;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public AdBean() {
		super();
	} 
	 
	 
 
}
