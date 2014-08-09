package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IActivity.IgetActivityDetailInfo;
import com.mzhou.merchant.dao.biz.ActivityManager;
import com.mzhou.merchant.model.ActivityBean;

public class ActivityHuoDongDetail extends Activity { 
	private Button publish;
	private ImageView title_bar_showleft;
	private Context context;
	private TextView activity_topic;
	private TextView activity_address;
	private TextView activity_introduce;
	private TextView activity_name;
	private TextView activity_contact;
	private TextView activity_stime;
	private TextView activity_etime;
	private ActivityManager activityManager;
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_huodong_one);
		init();
		loadButton();
		getData();
		listenerButton();
	}
	private void init(){
		context = ActivityHuoDongDetail.this;
		activityManager = new ActivityManager();
		Intent intent = getIntent();
 		id = intent.getStringExtra("id");
	}
	private void loadButton(){
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		activity_topic = (TextView) findViewById(R.id.activity_topic);
		activity_address = (TextView) findViewById(R.id.activity_address);
		activity_introduce = (TextView) findViewById(R.id.activity_introduce);
		activity_name = (TextView) findViewById(R.id.activity_name);
		activity_contact = (TextView) findViewById(R.id.activity_contact);
		activity_stime = (TextView) findViewById(R.id.activity_stime);
		activity_etime = (TextView) findViewById(R.id.activity_etime);
	}
	private void getData(){
		activityManager.GetActivityDetailInfo(context, id);
		activityManager.getActivityDetailInfoIml(new IgetActivityDetailInfo() {
			@Override
			public void getActivityDetailInfo(ActivityBean activityBean) {
				if (activityBean != null) {
					activity_topic.setText(activityBean.getTitle());
					activity_address.setText(activityBean.getAddress());
					activity_introduce.setText(activityBean.getContent());
					activity_name.setText(activityBean.getContact());
					activity_contact.setText(activityBean.getPhone());
					activity_etime.setText(activityBean.getApplytime());//is wrong in server
					activity_stime.setText(activityBean.getLasttime());
				}
			}
		});
		
	}
	 
	private void listenerButton(){
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 Intent intent = new Intent(context, ActivityJoin.class);
			 intent.putExtra("id", id);
			 startActivity(intent);
			}
		});
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
