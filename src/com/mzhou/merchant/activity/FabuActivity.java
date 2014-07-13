package com.mzhou.merchant.activity;

import java.util.HashMap;
import java.util.Map;


import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.ActivityManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.ToolsUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FabuActivity extends Activity {
 
	private EditText activity_topic;
	private EditText activity_address;
	private EditText activity_introduce;
	private EditText activity_name;
	private EditText activity_contact;
	private TextView activity_stime;
	private TextView activity_etime;
	private Button publish;
	private Button activity_stime_btn;
	private Button activity_etime_btn;
	private ImageView title_bar_showleft;
	private ActivityManager manager;
	private Context context;
 	private Time sTime;
 	private Time eTime;
 	private boolean isEnd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_huodong);
		init();
		loadButton();
		setData();
		listenerButton();
	}


	private void init() {
		manager = new ActivityManager();
		context = FabuActivity.this;
		sTime = new Time();
		eTime = new Time();
		long st = System.currentTimeMillis();
		long et = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L);
		sTime.set(st);
		eTime.set(et);
		isEnd = true;
	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		activity_topic = (EditText) findViewById(R.id.activity_topic);
		activity_address = (EditText) findViewById(R.id.activity_address);
		activity_introduce = (EditText) findViewById(R.id.activity_introduce);
		activity_name = (EditText) findViewById(R.id.activity_name);
		activity_contact = (EditText) findViewById(R.id.activity_contact);
		activity_stime = (TextView) findViewById(R.id.activity_stime);
		activity_etime = (TextView) findViewById(R.id.activity_etime);
		publish = (Button) findViewById(R.id.publish);
		activity_stime_btn = (Button) findViewById(R.id.activity_stime_btn);
		activity_etime_btn = (Button) findViewById(R.id.activity_etime_btn);
		ToolsUtils.updateDateDisplay(activity_stime, sTime);
		ToolsUtils.updateDateDisplay(activity_etime, eTime);
	}
	private void setData() {
		if (DbLoginManager.getInstance(this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(this).getLogingUserInfo();
			if ( userInfoBean.getUsertype().equals("1")) {
				activity_name.setText(userInfoBean.getNickname());
				activity_contact.setText(userInfoBean.getCenter());
			}else {
				activity_name.setText(userInfoBean.getContact());
				activity_contact.setText(userInfoBean.getPhonenub());
			}
		}else {
			activity_name.setText("");
			activity_contact.setText( "");
		}
	
	}

	private void listenerButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		activity_stime_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isEnd = false;
				new DatePickerDialog(context, mDateSetListener2, sTime.year, sTime.month, sTime.monthDay).show();
			}
		});
		activity_etime_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isEnd = true;
				new DatePickerDialog(context, mDateSetListener2, eTime.year, eTime.month, eTime.monthDay).show();
			}
		});
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (activity_topic.getText().toString().equals("")) {
					MyUtlis.toastInfo(FabuActivity.this, getString(R.string.activity_topic_null));
					return;
				}
				if (activity_stime.getText().toString().equals("")) {
					MyUtlis.toastInfo(FabuActivity.this, getString(R.string.activity_stime_null));
					return;
				}
				 ActivityBean activityBean = new ActivityBean();
				 activityBean.setTitle(activity_topic.getText().toString());
				 activityBean.setAddress(activity_address.getText().toString());
				 activityBean.setContent(activity_introduce.getText().toString());
				 activityBean.setPhone(activity_contact.getText().toString());
				 activityBean.setContact(activity_name.getText().toString());
				 activityBean.setLasttime(activity_stime.getText().toString());
				 activityBean.setApplytime(activity_etime.getText().toString());
				 
				 String uid = "0";
				 String usertype = "0";
				 
				UserInfoBean userInfoBean =  DbUserManager.getInstance(FabuActivity.this).getLogingUserInfo();
				if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
					uid = userInfoBean.getUid();
				}
				if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
					usertype = userInfoBean.getUsertype();
				}
				 manager.PubActivityInfo(context, uid, usertype, activityBean);
			 
				manager.getBackInfoIml(new IBackInfo() {
					@Override
					public void getBackAttactInfo(BackBean backBean) {
						 if (backBean != null) {
							MyUtlis.toastInfo(FabuActivity.this, backBean.getMsg());
							finish();
						}
					}
				});
			}
		});
	}
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			if (isEnd) {
				eTime.hour = hourOfDay;
				eTime.minute = minute;
				if (eTime.after(sTime)) {
					ToolsUtils.updateDateDisplay(activity_stime, sTime);
					ToolsUtils.updateDateDisplay(activity_etime, eTime);
					
				} else {
					ToolsUtils.updateDateDisplay(activity_etime, eTime, Color.RED);
					Toast.makeText(FabuActivity.this, getString(R.string.fabu_activity_small), Toast.LENGTH_LONG).show();
				}
			}else {
				sTime.hour = hourOfDay;
				sTime.minute = minute;
				if (sTime.before(eTime)) {
					ToolsUtils.updateDateDisplay(activity_stime, sTime);
					ToolsUtils.updateDateDisplay(activity_etime, eTime);
				} else {
					ToolsUtils.updateDateDisplay(activity_stime, sTime, Color.RED);
					Toast.makeText(FabuActivity.this, getString(R.string.fabu_activity_bigger), Toast.LENGTH_LONG).show();
				}
			}
		}
	};
	private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			if (isEnd) {
				eTime.year = year;
				eTime.month = monthOfYear;
				eTime.monthDay = dayOfMonth;
				new TimePickerDialog(context, mTimeSetListener, eTime.hour, eTime.minute, true).show();
			} else {
				sTime.year = year;
				sTime.month = monthOfYear;
				sTime.monthDay = dayOfMonth;
				new TimePickerDialog(context, mTimeSetListener, sTime.hour, sTime.minute, true).show();
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.ACTION_DOWN) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
