package com.mzhou.merchant.model;

import java.io.Serializable;
public class ProductsByIdBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; 
	private String content;  
	private String ctime;  
	private String classid;
	private String uid;
	private String brand;
	private String type;
	private String chip;
	private String size;
	private String system;
	private String pic;
	private String contact;
	private String phone;
	private String qq;
	private String company;
	private String address;
	private String net;
	private String prec_pixel;
	private String next_pixel;
	private String distinguish;
	private String rom;
	private String ah;
	 
	public ProductsByIdBean(String id, String content, String ctime, String classid, String uid, String brand, String type,
			String chip, String size, String system, String pic, String contact, String phone, String qq, String company,
			String address, String net, String prec_pixel, String next_pixel, String distinguish, String rom, String ah) {
		super();
		this.id = id;
		this.content = content;
		this.ctime = ctime;
		this.classid = classid;
		this.uid = uid;
		this.brand = brand;
		this.type = type;
		this.chip = chip;
		this.size = size;
		this.system = system;
		this.pic = pic;
		this.contact = contact;
		this.phone = phone;
		this.qq = qq;
		this.company = company;
		this.address = address;
		this.net = net;
		this.prec_pixel = prec_pixel;
		this.next_pixel = next_pixel;
		this.distinguish = distinguish;
		this.rom = rom;
		this.ah = ah;
	}

	@Override
	public String toString() {
		return "ProductsByIdBean [id=" + id + ", content=" + content + ", ctime=" + ctime + ", classid=" + classid + ", uid="
				+ uid + ", brand=" + brand + ", type=" + type + ", chip=" + chip + ", size=" + size + ", system=" + system
				+ ", pic=" + pic + ", contact=" + contact + ", phone=" + phone + ", qq=" + qq + ", company=" + company
				+ ", address=" + address + ", net=" + net + ", prec_pixel=" + prec_pixel + ", next_pixel=" + next_pixel
				+ ", distinguish=" + distinguish + ", rom=" + rom + ", ah=" + ah + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChip() {
		return chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
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

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getPrec_pixel() {
		return prec_pixel;
	}

	public void setPrec_pixel(String prec_pixel) {
		this.prec_pixel = prec_pixel;
	}

	public String getNext_pixel() {
		return next_pixel;
	}

	public void setNext_pixel(String next_pixel) {
		this.next_pixel = next_pixel;
	}

	public String getDistinguish() {
		return distinguish;
	}

	public void setDistinguish(String distinguish) {
		this.distinguish = distinguish;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public ProductsByIdBean()
	{
		super();
	}
}
