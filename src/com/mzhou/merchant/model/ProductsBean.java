package com.mzhou.merchant.model;

import java.io.Serializable;
public class ProductsBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String ctime;  
	private String classid;
	private String brand;
	private String  is_en;
	private String  is_show;
	private String pic;
	private String order_sort;
	private String category;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getIs_en() {
		return is_en;
	}
	public void setIs_en(String is_en) {
		this.is_en = is_en;
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getOrder_sort() {
		return order_sort;
	}
	public void setOrder_sort(String order_sort) {
		this.order_sort = order_sort;
	}
 
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ProductsBean() {
		super();
	}
	@Override
	public String toString() {
		return "ProductsBean [id=" + id + ", ctime=" + ctime + ", classid="
				+ classid + ", brand=" + brand + ", is_en=" + is_en
				+ ", is_show=" + is_show + ", pic=" + pic + ", order_sort="
				+ order_sort + ", category=" + category + "]";
	}
	 
	 
	 
}



