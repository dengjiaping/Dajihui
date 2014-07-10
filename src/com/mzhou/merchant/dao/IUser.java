package com.mzhou.merchant.dao;

import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.GetQuestionBean;
import com.mzhou.merchant.model.GetAnswerBean;
import com.mzhou.merchant.model.GetNewPwBean;

public class IUser {
	/**
	 * ��ȡ�û���Ϣ
	 */
	public interface IgetUserInfo {
		public void getInfo(AllBean userBean);
	}
	public interface IgetQQCheck {
		public void getCheckInfo(AllBean userBean);
	}
	public interface IgetQQBinder {
		public void getBinderInfo(AllBean userBean);
	}
	/**
	 * ��������
	 */
	public interface Iforgetquestion {
		public void getInfo(GetQuestionBean getQuestionBean);
	}
	/**
	 * ��������,�����������
	 */
	public interface Iforgetanswer {
		public void getInfo(GetAnswerBean getAnswerBean);
	}
	/**
	 *  
	 */
	public interface Iforgetpw {
		public void getInfo(GetNewPwBean getNewPwBean);
	}

}
