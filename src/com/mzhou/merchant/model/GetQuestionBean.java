package com.mzhou.merchant.model;

import java.io.Serializable;
public class GetQuestionBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String uid; 
	private String username;  
	private String question;
	private String msg;
	private String status;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	 
	 
	public GetQuestionBean(String uid, String username, String question, String msg, String status) {
		super();
		this.uid = uid;
		this.username = username;
		this.question = question;
		this.msg = msg;
		this.status = status;
	}
	@Override
	public String toString() {
		return "GetQuestionBean [uid=" + uid + ", username=" + username + ", question=" + question + ", msg=" + msg + ", status="
				+ status + "]";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public GetQuestionBean() {
		super();
	} 
	 
 
}
