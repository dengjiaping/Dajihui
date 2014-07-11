package com.mzhou.merchant.dao;

import java.util.List;

import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.ActivityMemberBean;
import com.mzhou.merchant.model.JobBean;

public class IActivity {
	/**
	 * 定义接口
	 */
	public interface IgetActivityInfo {
		public void getActivityInfo(List<ActivityBean> activityBeans);

	}
	public interface IgetActivityDetailInfo {
		public void getActivityDetailInfo( ActivityBean  activityBean );
	}
 
	public interface IgetActivityMemberInfo {
		public void getActivityMemberInfo(String  activityMemberBean);
	}
}
