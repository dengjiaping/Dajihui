package com.mzhou.merchant.model;

import java.io.Serializable;
public class NewsByIdBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String title; 
	private String ctime;  
	private String source;
	private String content;
	private String id;
	private String pic;
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getCtime()
	{
		return ctime;
	}
	public void setCtime(String ctime)
	{
		this.ctime = ctime;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPic()
	{
		return pic;
	}
	public void setPic(String pic)
	{
		this.pic = pic;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	@Override
	public String toString()
	{
		return "NewsByIdBean [title=" + title + ", ctime=" + ctime + ", source=" + source + ", content=" + content + ", id=" + id + ", pic=" + pic + "]";
	}
	public NewsByIdBean(String title, String ctime, String source, String content, String id, String pic)
	{
		super();
		this.title = title;
		this.ctime = ctime;
		this.source = source;
		this.content = content;
		this.id = id;
		this.pic = pic;
	}
	public NewsByIdBean()
	{
		super();
	}
	 
}
