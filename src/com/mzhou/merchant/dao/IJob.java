package com.mzhou.merchant.dao;

import java.util.List;

import com.mzhou.merchant.model.JobBean;

public class IJob {
	/**
	 * ����ӿ�
	 */
	public interface IgetJobInfo {
		public void getJobInfo(List<JobBean> jobBeans);
	}

	public interface IgetJobItemInfo {
		public void getJobItemInfo(JobBean jobBean);
	}
}
