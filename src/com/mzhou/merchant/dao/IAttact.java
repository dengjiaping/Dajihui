package com.mzhou.merchant.dao;

import java.util.List;

import com.mzhou.merchant.model.AttactBean;

public class IAttact {
	/**
	 * �������̽ӿ�
	 */
	public interface IgetAttactInfo {
		public void getAttactInfo(List<AttactBean> attactBeans);
	}
}
