package com.mzhou.merchant.dao;

import com.mzhou.merchant.model.BackBean;

public class IBack {
	/**
	 * 返回数据接口
	 */

	public interface IBackInfo {
		public void getBackAttactInfo(BackBean backBean);
	}

	/**
	 * 返回上传数据接口
	 */
	public interface IUploadBackInfo {
		public void getBackAttactInfo(String json);
	}
}
