package com.mzhou.merchant.dao;

import com.mzhou.merchant.model.BackBean;

public class IBack {
	/**
	 * �������ݽӿ�
	 */

	public interface IBackInfo {
		public void getBackAttactInfo(BackBean backBean);
	}

	/**
	 * �����ϴ����ݽӿ�
	 */
	public interface IUploadBackInfo {
		public void getBackAttactInfo(String json);
	}
}
