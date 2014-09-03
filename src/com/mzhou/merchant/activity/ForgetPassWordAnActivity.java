package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.mzhou.merchant.dao.IUser.Iforgetanswer;
import com.mzhou.merchant.model.GetAnswerBean;
import com.mzhou.merchant.model.GetQuestionBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
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
import android.widget.TextView;

public class ForgetPassWordAnActivity extends Activity {

	private ImageView backImageView;
	private TextView question;
	private Button next;
	private EditText answer;
	private CustomProgressDialog progressDialog = null;
	private String url;
	private String uid;
	private   GetQuestionBean que;
	private boolean isEnterprise;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	         
	     
		setContentView(R.layout.user_login_forgetpassword1);
		next = (Button) findViewById(R.id.user_login_forgetpassword_button);
		answer = (EditText) findViewById(R.id.answer);
		question = (TextView) findViewById(R.id.question);
		backImageView = (ImageView) findViewById(R.id.title_bar_showleft);

		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url");
		isEnterprise = bundle.getBoolean("isEnterprise");
		  que = (GetQuestionBean) bundle
				.getSerializable("getQuestionBean");
		uid = que.getUid();
		question.setText(que.getQuestion());

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WebIsConnectUtil
						.showNetState(ForgetPassWordAnActivity.this)) {
					String answe = answer.getText().toString();
					AsyngetAnswer getAnswer = new AsyngetAnswer(
							ForgetPassWordAnActivity.this, url, que
									.getQuestion(), uid, answe);
					getAnswer.execute();
					getAnswer(new Iforgetanswer() {
						@Override
						public void getInfo(GetAnswerBean getAnswerBean) {
							if (getAnswerBean != null) {
								if (getAnswerBean.getStatus().equals("true")) {
									Intent intent = new Intent();
									intent.setClass(
											ForgetPassWordAnActivity.this,
											ForgetPassWordModifyActivity.class);
									intent.putExtra("url", url);
									intent.putExtra("isEnterprise", isEnterprise);
									intent.putExtra("username", que.getUsername());
									intent.putExtra("oldpw",
											getAnswerBean.getOldpw());
									intent.putExtra("uid", uid);
									startActivity(intent);
									finish();
								} else {
									MyUtlis.toastInfo(
											ForgetPassWordAnActivity.this,
											getAnswerBean.getMsg());
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

	public class AsyngetAnswer extends AsyncTask<Void, Void, String> {
		private String qu;
		private String an;
		private String url;
		private Context context;
		private String uid;

		public AsyngetAnswer(Context context, String url, String qu,
				String uid, String an) {
			this.qu = qu;
			this.url = url;
			this.context = context;
			this.uid = uid;
			this.an = an;
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
			String userinfo = GetDataByPostUtil.verify(context, url, qu, an,
					uid);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {
			GetAnswerBean getQuestionBean = parseUserJson(result);
			forgetan.getInfo(getQuestionBean);
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

	public GetAnswerBean parseUserJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			GetAnswerBean json = com.alibaba.fastjson.JSON.parseObject(result,
					GetAnswerBean.class);
			return json;
		}
		return null;
	}

	private Iforgetanswer forgetan;

	public void getAnswer(Iforgetanswer iforgetquestion) {
		forgetan = iforgetquestion;
	}

}
