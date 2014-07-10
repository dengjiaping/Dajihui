package com.mzhou.merchant.dao.biz;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IActivity.IgetActivityDetailInfo;
import com.mzhou.merchant.dao.IActivity.IgetActivityInfo;
import com.mzhou.merchant.dao.IActivity.IgetActivityMemberInfo;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.IJob.IgetJobInfo;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.ActivityMemberBean;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;

public class ActivityManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	/**
	 * 发布活动 获取活动列表
	 */
	public ActivityManager() {
	}

	public void GetActivityInfo(Context context, int page, String uptime) {
		AsynActivityInfo login = new AsynActivityInfo(context, page, uptime);
		login.execute();
	}

	public void GetActivityDetailInfo(Context context, String id) {
		AsynActivityDetailInfo login = new AsynActivityDetailInfo(context, id);
		login.execute();
	}

	public void PubActivityInfo(Context context, String uid, String is_en,
			ActivityBean activityBean) {
		AsynPubActivityInfo login = new AsynPubActivityInfo(context, uid,
				is_en, activityBean);
		login.execute();
	}

	public void JoinActivityInfo(Context context, String uid, String name,
			String number) {
		AsynJoinActivityInfo login = new AsynJoinActivityInfo(context, uid,
				name, number);
		login.execute();
	}

	public void ActivityMemberInfo(Context context, String id,int page)   {
		AsynMemberActivityInfo login = new AsynMemberActivityInfo(context, id,page);
		login.execute();
	}

	/**
	 * 获取活动所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynActivityInfo extends AsyncTask<Void, Void, String> {
		private int page;
		private Context context;
		private String uptime;

		public AsynActivityInfo(Context context, int page, String uptime) {
			this.page = page;
			this.context = context;
			this.uptime = uptime;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String activityInfo = GetDataByPostUtil.GetActivityInfo(context,
					MyConstants.ACTIVITY_URL, "list", page, uptime);
			return activityInfo;
		}

		@Override
		protected void onPostExecute(String result) {

			List<ActivityBean> attactBeans = JsonParse
					.parseActivityJson(result);
			getActivityInfo.getActivityInfo(attactBeans);
			super.onPostExecute(result);
		}
	}

	/**
	 * 获取活动详细的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynActivityDetailInfo extends AsyncTask<Void, Void, String> {
		private String id;
		private Context context;

		public AsynActivityDetailInfo(Context context, String id) {

			this.id = id;
			this.context = context;

		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context, "");
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String activityInfo = GetDataByPostUtil.GetActivityDetailInfo(
					context, MyConstants.ACTIVITY_URL, "info", id);
			return activityInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			ActivityBean attactBeans = JsonParse
					.parseActivityDetailJson(result);
			getActivityDetailInfo.getActivityDetailInfo(attactBeans);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	/**
	 * 发布活动信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynPubActivityInfo extends AsyncTask<Void, Void, String> {

		private String uid;
		private String is_en;
		private ActivityBean activityBean;
		private Context context;

		public AsynPubActivityInfo(Context context, String uid, String is_en,
				ActivityBean activityBean) {
			this.uid = uid;
			this.context = context;
			this.is_en = is_en;
			this.activityBean = activityBean;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.publishing_huodong));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String jobInfo = GetDataByPostUtil.PubActivityInfo(context,
					MyConstants.ACTIVITY_URL, "add", uid, is_en, activityBean);
			return jobInfo;
		}

		@Override
		protected void onPostExecute(String result) {

			BackBean backBean = JsonParse.parsePubAttactJson(result);
			stopProgressDialog();
			backInfo.getBackAttactInfo(backBean);

			super.onPostExecute(result);
		}

	}

	/**
	 * 参加活动
	 * 
	 * @author user
	 * 
	 */
	public class AsynJoinActivityInfo extends AsyncTask<Void, Void, String> {

		private String uid;
		private Context context;
		private String name;
		private String phonenum;

		public AsynJoinActivityInfo(Context context, String uid, String name,
				String phonenum) {
			this.uid = uid;
			this.context = context;
			this.name = name;
			this.phonenum = phonenum;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.join_huodong));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String jobInfo = GetDataByPostUtil.JoinActivityInfo(context,
					MyConstants.ACTIVITY_URL, "join", uid, name, phonenum);
			return jobInfo;
		}

		@Override
		protected void onPostExecute(String result) {

			BackBean backBean = JsonParse.parsePubAttactJson(result);
			stopProgressDialog();
			backInfo.getBackAttactInfo(backBean);

			super.onPostExecute(result);
		}

	}

	/**
	 * 参加活动人员列表
	 * 
	 * @author user
	 * 
	 */
	public class AsynMemberActivityInfo extends AsyncTask<Void, Void, String>{

		private String id;
		private Context context;
		private int page;

		public AsynMemberActivityInfo(Context context, String id,int page) {
			this.id = id;
			this.context = context;
			this.page = page;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String jobInfo = GetDataByPostUtil.ActivityMemberInfo(context,
					MyConstants.ACTIVITY_URL, "user", id,page);
			return jobInfo;
		}

		@Override
		protected void onPostExecute(String result) {
				getActivityMemberInfo.getActivityMemberInfo(result);
			
			super.onPostExecute(result);
		}

	}

	// ********************************************

	private IgetActivityDetailInfo getActivityDetailInfo;
	private IgetActivityInfo getActivityInfo;
	private IBackInfo backInfo;
	private IgetActivityMemberInfo getActivityMemberInfo;

	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getActivityInfoIml(IgetActivityInfo igetActivityInfo) {
		getActivityInfo = igetActivityInfo;
	}

	public void getActivityDetailInfoIml(
			IgetActivityDetailInfo igetActivityDetailInfo) {
		getActivityDetailInfo = igetActivityDetailInfo;
	}

	public void getBackInfoIml(IBackInfo info) {
		backInfo = info;
	}

	public void getMemberInfoIml(IgetActivityMemberInfo igetActivityMemberInfo) {
		getActivityMemberInfo = igetActivityMemberInfo;
	}

	// ********************************************
	public void startProgressDialog(Context context, String msg) {

		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
			progressDialog.setMessage(msg);
		}
		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
