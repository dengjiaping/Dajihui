package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.mzhou.merchant.dao.IUser.Iforgetquestion;
import com.mzhou.merchant.model.GetQuestionBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ForgetPassWordActivity extends Activity {

	private RadioGroup group;
	private RadioButton rb_chanpin;
	private ImageView backImageView;
	private Button next;
	private EditText user_register_forget_count;
	private boolean isEnterprise = false;
	private CustomProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	 
		setContentView(R.layout.user_login_forgetpassword);
		next = (Button) findViewById(R.id.user_login_forgetpassword_button);
		user_register_forget_count = (EditText) findViewById(R.id.user_register_forget_count);
		group = (RadioGroup) findViewById(R.id.radioGroup);
		rb_chanpin = (RadioButton) findViewById(R.id.rb_sousuo_chanpin);
		rb_chanpin.setChecked(true);
		backImageView = (ImageView) findViewById(R.id.title_bar_showleft);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				String category = rb.getText().toString();
				if (category.equals(getResources().getString(
						R.string.enterprise_member))) {
					isEnterprise = true;

				} else {
					isEnterprise = false;
				}
			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WebIsConnectUtil.showNetState(ForgetPassWordActivity.this)) {
					String count = user_register_forget_count.getText()
							.toString();
					final String url;
					if (isEnterprise) {
						url = MyConstants.EN_LOGIN_URL;
					} else {
						url = MyConstants.LOGIN_URL;
					}

					AsyngetQuestion getQuestion = new AsyngetQuestion(
							ForgetPassWordActivity.this, url, count);
					getQuestion.execute();
					getQestion(new Iforgetquestion() {

						@Override
						public void getInfo(GetQuestionBean getQuestionBean) {
							if (getQuestionBean != null) {
								if (getQuestionBean.getStatus().equals("true")) {
									Intent intent = new Intent();
									intent.setClass(
											ForgetPassWordActivity.this,
											ForgetPassWordAnActivity.class);
									intent.putExtra("getQuestionBean",
											getQuestionBean);
									intent.putExtra("isEnterprise", isEnterprise);
									intent.putExtra("url", url);
									startActivity(intent);
									finish();
								} else {
									MyUtlis.toastInfo(
											ForgetPassWordActivity.this,
											getQuestionBean.getMsg());
								}

							}
						}
					});

				}
			}
		});
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

	}

	public class AsyngetQuestion extends AsyncTask<Void, Void, String> {
		private String un;
		private String url;
		private Context context;

		public AsyngetQuestion(Context context, String url, String un) {
			this.un = un;
			this.url = url;
			this.context = context;
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
			String userinfo = GetDataByPostUtil.getQuestion(context, url, un);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {
			GetQuestionBean getQuestionBean = parseUserJson(result);
			forgetqestion.getInfo(getQuestionBean);
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

	public GetQuestionBean parseUserJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			GetQuestionBean json = com.alibaba.fastjson.JSON.parseObject(
					result, GetQuestionBean.class);
			return json;
		}
		return null;
	}

	private Iforgetquestion forgetqestion;

	public void getQestion(Iforgetquestion iforgetquestion) {
		forgetqestion = iforgetquestion;
	}

}
