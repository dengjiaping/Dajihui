package com.mzhou.merchant.activity;

import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

public class ActivityFankui extends Activity {

	private ImageView title_bar_showleft;
	private EditText content, number;
	private Button submmit;
	private CustomProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_fankui);
		loadButton();
		clickButton();
	}

	private void loadButton() {
		ScrollView scrollViewExtend = (ScrollView) findViewById(R.id.scroll);
		scrollViewExtend.smoothScrollTo(0, 0);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		content = (EditText) findViewById(R.id.pub_feedback_instruction);
		number = (EditText) findViewById(R.id.pub_feedback_contact);
		submmit = (Button) findViewById(R.id.publish);
	}

	private void clickButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		submmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WebIsConnectUtil.showNetState(ActivityFankui.this)) {

					AsyngetBack asyngetBack = new AsyngetBack(
							ActivityFankui.this, content.getText().toString(),
							number.getText().toString());
					asyngetBack.execute();
					getback(new IBackInfo() {

						@Override
						public void getBackAttactInfo(BackBean backBean) {
							if (backBean != null) {
								if (backBean.getStatus().equals("true")) {
									finish();

								}
								MyUtlis.toastInfo(ActivityFankui.this,
										backBean.getMsg());
							}

						}
					});

				}
			}
		});
	}

	public class AsyngetBack extends AsyncTask<Void, Void, String> {
		private String content;
		private Context context;
		private String contact;

		public AsyngetBack(Context context, String contact, String content) {
			this.contact = contact;
			this.context = context;
			this.content = content;
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
			String userinfo = GetDataByPostUtil.feedback(context,
					MyConstants.FEEDBACK_URL, content, contact);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {
			BackBean backBean = parseUserJson(result);
			backInfo.getBackAttactInfo(backBean);
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

	public BackBean parseUserJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			BackBean json = com.alibaba.fastjson.JSON.parseObject(result,
					BackBean.class);
			return json;
		}
		return null;
	}

	private IBackInfo backInfo;

	public void getback(IBackInfo ibaBackInfo) {
		backInfo = ibaBackInfo;
	}

}
