package com.mzhou.merchant.dao.biz;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IAttact.IgetAttactInfo;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;

public class AttactManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	public AttactManager() {
	}

	public void GetAttactInfo(Context context, int page, String uid,
			String uptime, String url) {
		AsynAttact login = new AsynAttact(context, "list", page, uid, uptime,
				url);
		login.execute();
	}

	public void PubAttactInfo(Context context, String is_en, String content,
			String contact, String email, String uid, String url) {
		AsynAttactPublish asynAttactPublish = new AsynAttactPublish(context,
				is_en, "add", content, uid, contact, email, url);
		asynAttactPublish.execute();
	}

	/**
	 * 获取招商所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynAttact extends AsyncTask<Void, Void, String> {
		private int page;
		private String uid;
		private String subject;
		private Context context;
		private String uptime;
		private String url;

		public AsynAttact(Context context, String subject, int page,
				String uid, String uptime, String url) {
			this.page = page;
			this.subject = subject;
			this.uid = uid;
			this.context = context;
			this.uptime = uptime;
			this.url = url;
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
			String userinfo = GetDataByPostUtil.GetAttactInfo(context, url,
					subject, uid, page, uptime);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {
			List<AttactBean> attactBeans = JsonParse.parseAttactJson(result);
			// Log.i("print", "attactBeans--->" + result);
			getAttactInfo.getAttactInfo(attactBeans);
			super.onPostExecute(result);
		}
	}

	/**
	 * 发布招商所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynAttactPublish extends AsyncTask<Void, Void, String> {
		private String content;
		private String uid;
		private String contact;
		private String subject;
		private String email;
		private Context context;
		private String is_en;
		private String url;

		public AsynAttactPublish(Context context, String is_en, String subject,
				String content, String uid, String contact, String email,
				String url) {
			this.content = content;
			this.subject = subject;
			this.uid = uid;
			this.contact = contact;
			this.email = email;
			this.context = context;
			this.is_en = is_en;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.publishing));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String userinfo = GetDataByPostUtil.PubAttactInfo(context, is_en,
					subject, uid, content, contact, email, url);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {

			// Log.i("print", "result--->" + result);
			BackBean backBean = JsonParse.parsePubAttactJson(result);
			// Log.i("print", "attactBeans--->" + result);
			stopProgressDialog();
			backInfo.getBackAttactInfo(backBean);
			super.onPostExecute(result);
		}
	}

	// ********************************************

	private IgetAttactInfo getAttactInfo;
	private IBackInfo backInfo;

	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getAttactInfoIml(IgetAttactInfo igetAttactInfo) {
		getAttactInfo = igetAttactInfo;
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
