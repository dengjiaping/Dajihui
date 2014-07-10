package com.mzhou.merchant.model;

import java.io.Serializable;
public class NewsCommentBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String content; 
	private String ctime;  
	private String news_id;  
 	private String commenter;
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
	public String getNews_id()
	{
		return news_id;
	}
	public void setNews_id(String news_id)
	{
		this.news_id = news_id;
	}
	public String getCommenter()
	{
		return commenter;
	}
	public void setCommenter(String commenter)
	{
		this.commenter = commenter;
	}
	@Override
	public String toString()
	{
		return "NewsCommentBean [content=" + content + ", ctime=" + ctime + ", news_id=" + news_id + ", commenter=" + commenter + "]";
	}
	public NewsCommentBean(String content, String ctime, String news_id, String commenter)
	{
		super();
		this.content = content;
		this.ctime = ctime;
		this.news_id = news_id;
		this.commenter = commenter;
	}
	public NewsCommentBean()
	{
		super();
	}  
	 
}
