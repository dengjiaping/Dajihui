package com.mzhou.merchant.model;

import java.io.Serializable;
public class NewsBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String title; 
	private String pic;  
	private String ctime;  
	private String source;
	private String id;
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getPic()
	{
		return pic;
	}
	public void setPic(String pic)
	{
		this.pic = pic;
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
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	@Override
	public String toString()
	{
		return "NewsBean [title=" + title + ", pic=" + pic + ", ctime=" + ctime + ", source=" + source + ", id=" + id + "]";
	}
	public NewsBean(String title, String pic, String ctime, String source, String id)
	{
		super();
		this.title = title;
		this.pic = pic;
		this.ctime = ctime;
		this.source = source;
		this.id = id;
	}
	public NewsBean()
	{
		super();
	}
	 
	
}
