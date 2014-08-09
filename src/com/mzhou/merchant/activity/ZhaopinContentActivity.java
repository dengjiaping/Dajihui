package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.mzhou.merchant.dao.biz.JobManager;
import com.mzhou.merchant.model.JobItemBean;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ZhaopinContentActivity extends Activity {
	private ImageView title_bar_showleft;
	private TextView display_job_station;
	private TextView display_job_require;
	private TextView pub_qiugou_name;
	private TextView display_job_contact;
	private TextView user_manager_qq;
	private TextView pub_qiugou_address;
	private TextView display_job_timestop;
	private TextView pub_qiugou_company;
	private String productId;

	private String postion;
	private String content;
	private String contact;
	private String email;
	private String stopTime;
	private String address;
	private String company;
	private String phoneNub;

	private JobManager jobManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		setContentView(R.layout.xianshi_zhaoping);
		init();
		loadButton();

		if (productId != null) {
			if (WebIsConnectUtil.showNetState(ZhaopinContentActivity.this)) {
				AsynJobByInfo asynJobInfo = new AsynJobByInfo("info", productId);
				asynJobInfo.execute();

			}
		}

	}

	private void init() {
		jobManager = new JobManager();
		Intent intent = getIntent();
		productId = intent.getStringExtra("id");
	}

	public class AsynJobByInfo extends AsyncTask<Void, Void, String> {
		private String id;
		private String subject;

		public AsynJobByInfo(String subject, String id) {
			this.subject = subject;
			this.id = id;
		}

		@Override
		protected String doInBackground(Void... params) {
			String jobInfo = GetDataByPostUtil.GetJobByIdInfo(
					ZhaopinContentActivity.this, MyConstants.JOB_URL, subject,
					id);
			if (jobInfo != null) {
				return jobInfo;
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			jobManager.startProgressDialog(ZhaopinContentActivity.this,
					getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			jobManager.stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null && !result.trim().equals("") && !"[{}]".equals(result.trim().toString())) {
				JobItemBean attactBeans = JsonParse.parseJobJsonById(result);
				jobManager.stopProgressDialog();
				if (attactBeans == null) {
					return;
				}
				postion = attactBeans.getPosition();
				content = attactBeans.getContent();
				contact = attactBeans.getContact();
				company = attactBeans.getCompany();
				phoneNub = attactBeans.getPhone();
				address = attactBeans.getAddress();
				email = attactBeans.getEmail();
				stopTime = attactBeans.getLasttime();


				display_job_station.setText(postion);
				display_job_require.setText(content);
				pub_qiugou_name.setText(contact);
				display_job_contact.setText(phoneNub);
				user_manager_qq.setText(email);
				pub_qiugou_address.setText(address);
				pub_qiugou_company.setText(company);
				display_job_timestop.setText(MyUtlis.TimeStamp2Date(stopTime));
			}else {
				Toast.makeText(ZhaopinContentActivity.this, getString(R.string.ad_toast), Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		display_job_station = (TextView) findViewById(R.id.display_job_station);
		display_job_require = (TextView) findViewById(R.id.display_job_require);
		pub_qiugou_name = (TextView) findViewById(R.id.pub_qiugou_name);
		display_job_contact = (TextView) findViewById(R.id.display_job_contact);
		user_manager_qq = (TextView) findViewById(R.id.user_manager_qq);
		pub_qiugou_address = (TextView) findViewById(R.id.pub_qiugou_address);
		display_job_timestop = (TextView) findViewById(R.id.display_job_timestop);
		pub_qiugou_company = (TextView) findViewById(R.id.pub_qiugou_company);
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZhaopinContentActivity.this.finish();
			}
		});
	}
}
