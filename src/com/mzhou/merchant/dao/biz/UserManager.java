package com.mzhou.merchant.dao.biz;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IUser.IgetQQBinder;
import com.mzhou.merchant.dao.IUser.IgetQQCheck;
import com.mzhou.merchant.dao.IUser.IgetUserInfo;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.HttpRequest;
import com.mzhou.merchant.utlis.JsonParse;

public class UserManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	public UserManager() {
	}

	/**
	 * 登录
	 * 
	 * @param userId
	 * @param passwd
	 * @return 返回的是服务器端的所有的用户信息
	 */

	public void Login(Context context, String url,Map<String, String> map) {
		AsynLogin login = new AsynLogin(context, url, map);
		login.execute();
	}

	/**
	 * 异步登录,同时获取用户所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynLogin extends AsyncTask<Void, Void, String> {
	 
		private String url;
		private Context context;
		private Map<String, String> map;

		public AsynLogin(Context context, String url,Map<String, String> map) {
			this.url = url;
			this.context = context;
			this.map = map;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.logining));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String userinfo = HttpRequest.sendPostRequest(map, url);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {

			AllBean userBean = JsonParse.parseUserJson(result);
			get.getInfo(userBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	/**
	 * 注册成功
	 * 
	 * @param user
	 * @return
	 */
	public void Register(Context context, String url, String username,
			String passWord, String nickName, String category, String pw_pro,
			String pw_pro_an) {
		AsynRegister login = new AsynRegister(context, url, "register",
				username, passWord, nickName, category, pw_pro, pw_pro_an);
		login.execute();
	}
	public void Binder(Context context, String url, String openid,String username,String passwd) {
		AsynBinder login = new AsynBinder(context, url, openid, username, passwd);
		login.execute();
	}
	public void Check(Context context, String url, String openid) {
		AsynCheck login = new AsynCheck(context, url,  openid);
		login.execute();
	}

	public class AsynCheck extends AsyncTask<Void, Void, String> {
		private String url;
		private Context context;
		private String openid;
		
		public AsynCheck(Context context, String url,   String openid) {
			this.url = url;
			this.openid = openid;
			this.context = context;
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
			String userinfo = GetDataByPostUtil.check(context, url, openid);
			return userinfo;
		}
		
		@Override
		protected void onPostExecute(String result) {
			AllBean userBean = JsonParse.parseUserJson(result);
			check.getCheckInfo(userBean);
			super.onPostExecute(result);
		}
	}
	public class AsynBinder extends AsyncTask<Void, Void, String> {
		private String username;
		private String passwd;
		private String url;
		private Context context;
		private String openid;
		
		public AsynBinder(Context context, String url,   String openid,String username,String passwd) {
			this.url = url;
			this.username = username;
			this.passwd = passwd;
			this.openid = openid;
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.bindering));
			super.onPreExecute();
		}
		
		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			String userinfo = GetDataByPostUtil.binder(context, url, openid, username, passwd);
			return userinfo;
		}
		
		@Override
		protected void onPostExecute(String result) {
			AllBean userBean = JsonParse.parseUserJson(result);
			binder.getBinderInfo(userBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}
	public class AsynRegister extends AsyncTask<Void, Void, String> {
		private String un;
		private String pw;
		private String subject;
		private String nickname;
		private String category;
		private String url;
		private Context context;
		private String pw_pro;
		private String pw_pro_an;

		public AsynRegister(Context context, String url, String subject,
				String un, String pw, String nickname, String category,
				String pw_pro, String pw_pro_an) {
			this.un = un;
			this.subject = subject;
			this.pw = pw;
			this.nickname = nickname;
			this.category = category;
			this.context = context;
			this.url = url;
			this.pw_pro = pw_pro;
			this.pw_pro_an = pw_pro_an;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.registering));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String userinfo = GetDataByPostUtil.register(context, url, subject,
					un, pw, nickname, category, pw_pro, pw_pro_an);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {
			AllBean userBean = JsonParse.parseUserJson(result);
			get.getInfo(userBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	// ********************************************

	private IgetUserInfo get;
	private IgetQQCheck check;
	private IgetQQBinder binder;

	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getQQCheck(IgetQQCheck igetUserInfo) {
		check = igetUserInfo;
	}
	public void getQQBinder(IgetQQBinder igetUserInfo) {
		binder = igetUserInfo;
	}
	public void getUserInfoIml(IgetUserInfo igetUserInfo) {
		get = igetUserInfo;
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
