package com.mzhou.merchant.model;

import java.io.Serializable;
public class ProductsEnterpriseByIdBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id; //锟斤拷品id
	private String ctime;  //锟斤拷锟斤拷锟斤拷品锟斤拷时锟斤拷
	private String content;  //锟斤拷品锟斤拷锟�	
	private String classid;//锟斤拷锟絠d
	private String is_en;
	private String is_show;
	private String brand;//品锟斤拷
	private String pic;//图片锟斤拷址 
	private String type;//锟酵猴拷
	private String chip;//芯片
	private String size;//锟竭达拷
	private String system;//系统
	private String uid;//锟矫伙拷id
	private String company;//锟斤拷司锟斤拷锟�	
	private String address;//锟斤拷司锟斤拷址
	private String net;//锟斤拷司锟斤拷址
	private String prec_pixel;//前锟斤拷锟斤拷
	private String next_pixel;//锟斤拷锟斤拷锟斤拷
	private String distinguish;//锟街憋拷锟斤拷
	private String contact;//锟斤拷系锟斤拷
	private String fax;//锟斤拷锟斤拷
	private String center;//锟杰伙拷
	private String rom; //锟斤拷锟斤拷锟节达拷
	private String ah;//锟斤拷锟斤拷锟斤拷锟�
	private String videopic;
	 
	 
 
	public ProductsEnterpriseByIdBean(String id, String ctime, String content,
			String classid, String is_en, String is_show, String brand,
			String pic, String type, String chip, String size, String system,
			String uid, String company, String address, String net,
			String prec_pixel, String next_pixel, String distinguish,
			String contact, String fax, String center, String rom, String ah,
			String videopic) {
		super();
		this.id = id;
		this.ctime = ctime;
		this.content = content;
		this.classid = classid;
		this.is_en = is_en;
		this.is_show = is_show;
		this.brand = brand;
		this.pic = pic;
		this.type = type;
		this.chip = chip;
		this.size = size;
		this.system = system;
		this.uid = uid;
		this.company = company;
		this.address = address;
		this.net = net;
		this.prec_pixel = prec_pixel;
		this.next_pixel = next_pixel;
		this.distinguish = distinguish;
		this.contact = contact;
		this.fax = fax;
		this.center = center;
		this.rom = rom;
		this.ah = ah;
		this.videopic = videopic;
	}

	@Override
	public String toString() {
		return "ProductsEnterpriseByIdBean [id=" + id + ", ctime=" + ctime
				+ ", content=" + content + ", classid=" + classid + ", is_en="
				+ is_en + ", is_show=" + is_show + ", brand=" + brand
				+ ", pic=" + pic + ", type=" + type + ", chip=" + chip
				+ ", size=" + size + ", system=" + system + ", uid=" + uid
				+ ", company=" + company + ", address=" + address + ", net="
				+ net + ", prec_pixel=" + prec_pixel + ", next_pixel="
				+ next_pixel + ", distinguish=" + distinguish + ", contact="
				+ contact + ", fax=" + fax + ", center=" + center + ", rom="
				+ rom + ", ah=" + ah + ", videopic=" + videopic + "]";
	}

	public String getVideopic() {
		return videopic;
	}

	public void setVideopic(String videopic) {
		this.videopic = videopic;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public ProductsEnterpriseByIdBean() {
		super();
	}
	 
}
