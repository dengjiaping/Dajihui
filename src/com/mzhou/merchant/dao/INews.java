package com.mzhou.merchant.dao;

import java.util.List;

import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.NewsByIdBean;
import com.mzhou.merchant.model.NewsCommentBean;

public class INews {
	/**
	 * ����ӿ�NewsBean
	 */
	public interface IgetNewsInfo {
		public void getNewsInfo(List<NewsBean> newsBean);
	}

	/**
	 * ����ӿ�NewsByIdBean
	 */
	public interface IgetNewsByIdInfo {
		public void getNewsByIdInfo(NewsByIdBean newsBean);
	}

	/**
	 * ����ӿ�NewsCommentBean
	 */
	public interface IgetNewsCommentInfo {
		public void getNewsCommentInfo(List<NewsCommentBean> newsBean);
	}

}
