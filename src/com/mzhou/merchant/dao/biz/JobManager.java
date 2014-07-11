package com.mzhou.merchant.dao.biz;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.IJob.IgetJobInfo;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;

public class JobManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	public JobManager() {
	}

	public void GetJobInfo(Context context, int page, String uid, String uptime) {
		AsynJobInfo login = new AsynJobInfo(context, "list", page, uid, uptime);
		login.execute();
	}

	public void PubJobInfo(Context context, String uid, String position,
			String content, String contact, String phone, String email,
			String company, String address, String lasttime) {
		AsynPubJobInfo login = new AsynPubJobInfo(context, "add", uid,
				position, content, contact, phone, email, company, address,
				lasttime);
		login.execute();
	}

	/**
	 * 获取招聘所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynJobInfo extends AsyncTask<Void, Void, String> {
		private int page;
		private String uid;
		private String subject;
		private Context context;
		private String uptime;

		public AsynJobInfo(Context context, String subject, int page,
				String uid, String uptime) {
			this.page = page;
			this.subject = subject;
			this.uid = uid;
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
			String jobInfo = GetDataByPostUtil.GetJobInfo(context,
					MyConstants.JOB_URL, subject, page, uid, uptime);
			return jobInfo;
		}

		@Override
		protected void onPostExecute(String result) {

			List<JobBean> attactBeans = JsonParse.parseJobJson(result);
			getJobInfo.getJobInfo(attactBeans);
			super.onPostExecute(result);
		}
	}

	public class AsynPubJobInfo extends AsyncTask<Void, Void, String> {

		private String uid;
		private String subject;
		private String position;
		private String content;
		private String contact;
		private String phone;
		private String email;
		private String company;
		private String address;
		private String lasttime;
		private Context context;

		public AsynPubJobInfo(Context context, String subject, String uid,
				String position, String content, String contact, String phone,
				String email, String company, String address, String lasttime) {
			this.subject = subject;
			this.uid = uid;
			this.position = position;
			this.content = content;
			this.contact = contact;
			this.phone = phone;
			this.email = email;
			this.company = company;
			this.address = address;
			this.lasttime = lasttime;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.publishing_job));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String jobInfo = GetDataByPostUtil.PubJobInfo(context,
					MyConstants.JOB_URL, subject, uid, position, content,
					contact, phone, email, company, address, lasttime);
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

	// ********************************************

	private IgetJobInfo getJobInfo;
	private IBackInfo backInfo;

	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getJobInfoIml(IgetJobInfo igetJobInfo) {
		getJobInfo = igetJobInfo;
	}

	public void getBackInfoIml(IBackInfo info) {
		backInfo = info;
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
