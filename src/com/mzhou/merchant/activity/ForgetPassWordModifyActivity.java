package com.mzhou.merchant.activity;

import com.mzhou.merchant.dao.IUser.Iforgetpw;
import com.mzhou.merchant.model.GetNewPwBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ForgetPassWordModifyActivity extends Activity {
	private ImageView title_bar_showleft;
	private EditText user_manager_alter_passwd3;
	private EditText user_manager_alter_passwd4;
	private Button publish;
	private String instruction2;
	private CustomProgressDialog progressDialog = null;
	private String uid;
	private String url;
	private String oldpw;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login_forgetpassword2);
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url");
		uid = bundle.getString("uid");
		oldpw = bundle.getString("oldpw");
		loadButton();
		listenerButton();
	}

	private void listenerButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击按钮之后保存起来
				if (WebIsConnectUtil
						.showNetState(ForgetPassWordModifyActivity.this)) {
					if (user_manager_alter_passwd3
							.getText()
							.toString()
							.equals(user_manager_alter_passwd4.getText()
									.toString())) {
						AsyngetResetPw asyngetResetPw = new AsyngetResetPw(
								ForgetPassWordModifyActivity.this,
								url,
								user_manager_alter_passwd3.getText().toString(),
								oldpw, uid);
						asyngetResetPw.execute();
						getResetPw(new Iforgetpw() {

							@Override
							public void getInfo(GetNewPwBean getNewPwBean) {
								if (getNewPwBean != null) {
									if (getNewPwBean.getStatus().equals("true")) {
										finish();
									}
									MyUtlis.toastInfo(
											ForgetPassWordModifyActivity.this,
											getNewPwBean.getMsg());
								}

							}
						});

					} else {
						Toast.makeText(ForgetPassWordModifyActivity.this,
								instruction2, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

	private void loadButton() {
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		user_manager_alter_passwd3 = (EditText) findViewById(R.id.user_manager_alter_passwd3);
		user_manager_alter_passwd4 = (EditText) findViewById(R.id.user_manager_alter_passwd4);
		instruction2 = getResources().getString(
				R.string.user_manager_alter_instruction2);
		 
	}

	public class AsyngetResetPw extends AsyncTask<Void, Void, String> {
		private String pw;
		private String oldpw;
		private String url;
		private Context context;
		private String uid;

		public AsyngetResetPw(Context context, String url, String pw,
				String oldpw, String uid) {
			this.pw = pw;
			this.url = url;
			this.context = context;
			this.oldpw = oldpw;
			this.uid = uid;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String userinfo = GetDataByPostUtil.resetPw(context, url, pw,
					oldpw, uid);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {
			GetNewPwBean getQuestionBean = parseUserJson(result);
			forgetpw.getInfo(getQuestionBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

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

	public GetNewPwBean parseUserJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			GetNewPwBean json = com.alibaba.fastjson.JSON.parseObject(result,
					GetNewPwBean.class);
			return json;
		}
		return null;
	}

	private Iforgetpw forgetpw;

	public void getResetPw(Iforgetpw iforgetquestion) {
		forgetpw = iforgetquestion;
	}

}
