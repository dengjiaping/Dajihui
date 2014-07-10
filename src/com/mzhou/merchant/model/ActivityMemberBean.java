package com.mzhou.merchant.model;

import java.io.Serializable;

public class ActivityMemberBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String contact;
	private String phone;
	private String is_en;
	private String uid;
	private String initiator;
	private String ctime;
	private String activity_id;

 
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

 

	public ActivityMemberBean(String id, String contact, String phone,
			String is_en, String uid, String initiator, String ctime,
			String activity_id) {
		super();
		this.id = id;
		this.contact = contact;
		this.phone = phone;
		this.is_en = is_en;
		this.uid = uid;
		this.initiator = initiator;
		this.ctime = ctime;
		this.activity_id = activity_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIs_en() {
		return is_en;
	}

	public void setIs_en(String is_en) {
		this.is_en = is_en;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	@Override
	public String toString() {
		return "ActivityMemberBean [id=" + id + ", contact=" + contact
				+ ", phone=" + phone + ", is_en=" + is_en + ", uid=" + uid
				+ ", initiator=" + initiator + ", ctime=" + ctime
				+ ", activity_id=" + activity_id + "]";
	}

	public ActivityMemberBean() {
		super();
	}

}
