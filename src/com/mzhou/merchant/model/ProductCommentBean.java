package com.mzhou.merchant.model;

import java.io.Serializable;
public class ProductCommentBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 */
	private String id;
	private String content;
	private String ctime;
	private String category;
	private String contact;
	private String nickname;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getCtime()
	{
		return ctime;
	}
	public void setCtime(String ctime)
	{
		this.ctime = ctime;
	}
	 
	public String getContact()
	{
		return contact;
	}
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	 
	 
	public ProductCommentBean(String id, String content, String ctime, String category, String contact, String nickname)
	{
		super();
		this.id = id;
		this.content = content;
		this.ctime = ctime;
		this.category = category;
		this.contact = contact;
		this.nickname = nickname;
	}
	@Override
	public String toString()
	{
		return "ProductCommentBean [id=" + id + ", content=" + content + ", ctime=" + ctime + ", category=" + category + ", contact=" + contact + ", nickname=" + nickname + "]";
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public ProductCommentBean()
	{
		super();
	}
	 
}
