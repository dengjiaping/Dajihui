package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mzhou.merchant.model.GroupUsers;
import com.mzhou.merchant.model.ProductsEnterpriseByIdBean;

public class CompanyContactUsActivity extends Activity {

	private ImageView title_bar_showleft;
	private Button call1;
	private Button call2;
	private Button call3;
	private Button call4;
	private Button call5;
	private Button call6;
	private Button call7;
	private Button call8;
	private Button call9;
	private Button call10;

	private ProductsEnterpriseByIdBean p;
	private TextView user_manager_centerNub_stub;
	private TextView user_manager_centerFax_stub;
	private TextView user_manager_tv_company;
	private TextView user_manager_tv_address;
	private TextView user_manager_tv_net;
	private TextView user_manager_tv_name1;
	private TextView user_manager_tv_name2;
	private TextView user_manager_tv_name3;
	private TextView user_manager_tv_name4;
	private TextView user_manager_tv_name5;
	private TextView user_manager_tv_name6;
	private TextView user_manager_tv_name7;
	private TextView user_manager_tv_name8;
	private TextView user_manager_tv_name9;
	private TextView user_manager_tv_name10;
	private TextView user_manager_tv_nub1;
	private TextView user_manager_tv_nub2;
	private TextView user_manager_tv_nub3;
	private TextView user_manager_tv_nub4;
	private TextView user_manager_tv_nub5;
	private TextView user_manager_tv_nub6;
	private TextView user_manager_tv_nub7;
	private TextView user_manager_tv_nub8;
	private TextView user_manager_tv_nub9;
	private TextView user_manager_tv_nub10;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		setContentView(R.layout.contactus);
		init();
		loadButton();
		setdata();
		listenerButton();
	}

	private void setdata() {
		user_manager_centerNub_stub.setText(p.getCenter());
		user_manager_centerFax_stub.setText(p.getFax());
		user_manager_tv_company.setText(p.getCompany());
		user_manager_tv_address.setText(p.getAddress());
		user_manager_tv_net.setText(p.getNet());
		String string = p.getContact();
		if (string != null && !string.equals("") && !string.equals("[]")) {
			
				
			GroupUsers groupUsers = JSON.parseObject(string, GroupUsers.class);

			try {
				user_manager_tv_name1.setText(groupUsers.getUsers().get(0)
						.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				user_manager_tv_name2.setText(groupUsers.getUsers().get(1)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name3.setText(groupUsers.getUsers().get(2)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name4.setText(groupUsers.getUsers().get(3)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name5.setText(groupUsers.getUsers().get(4)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name6.setText(groupUsers.getUsers().get(5)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name7.setText(groupUsers.getUsers().get(6)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name8.setText(groupUsers.getUsers().get(7)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name9.setText(groupUsers.getUsers().get(8)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_name10.setText(groupUsers.getUsers().get(9)
						.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				user_manager_tv_nub1.setText(groupUsers.getUsers().get(0)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub2.setText(groupUsers.getUsers().get(1)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub3.setText(groupUsers.getUsers().get(2)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub4.setText(groupUsers.getUsers().get(3)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub5.setText(groupUsers.getUsers().get(4)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub6.setText(groupUsers.getUsers().get(5)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub7.setText(groupUsers.getUsers().get(6)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub8.setText(groupUsers.getUsers().get(7)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user_manager_tv_nub9.setText(groupUsers.getUsers().get(8)
						.getNumber());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}try {
			user_manager_tv_nub10.setText(groupUsers.getUsers().get(9)
					.getNumber());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		Intent intent = getIntent();
		p = (ProductsEnterpriseByIdBean) intent
				.getSerializableExtra("productinfo");
		Log.i("print", p.toString());

	}

	private void loadButton() {
		ScrollView scrollViewExtend = (ScrollView) findViewById(R.id.scroll);
		scrollViewExtend.smoothScrollTo(0, 0);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		user_manager_centerNub_stub = (TextView) findViewById(R.id.user_manager_centerNub_stub);
		user_manager_centerFax_stub = (TextView) findViewById(R.id.user_manager_centerFax_stub);
		user_manager_tv_company = (TextView) findViewById(R.id.user_manager_tv_company);
		user_manager_tv_address = (TextView) findViewById(R.id.user_manager_tv_address);
		user_manager_tv_net = (TextView) findViewById(R.id.user_manager_tv_net);
		user_manager_tv_name1 = (TextView) findViewById(R.id.user_manager_tv_name1);
		user_manager_tv_name2 = (TextView) findViewById(R.id.user_manager_tv_name2);
		user_manager_tv_name3 = (TextView) findViewById(R.id.user_manager_tv_name3);
		user_manager_tv_name4 = (TextView) findViewById(R.id.user_manager_tv_name4);
		user_manager_tv_name5 = (TextView) findViewById(R.id.user_manager_tv_name5);
		user_manager_tv_name6 = (TextView) findViewById(R.id.user_manager_tv_name6);
		user_manager_tv_name7 = (TextView) findViewById(R.id.user_manager_tv_name7);
		user_manager_tv_name8 = (TextView) findViewById(R.id.user_manager_tv_name8);
		user_manager_tv_name9 = (TextView) findViewById(R.id.user_manager_tv_name9);
		user_manager_tv_name10 = (TextView) findViewById(R.id.user_manager_tv_name10);
		user_manager_tv_name5 = (TextView) findViewById(R.id.user_manager_tv_name5);
		user_manager_tv_nub1 = (TextView) findViewById(R.id.user_manager_tv_nub1);
		user_manager_tv_nub2 = (TextView) findViewById(R.id.user_manager_tv_nub2);
		user_manager_tv_nub3 = (TextView) findViewById(R.id.user_manager_tv_nub3);
		user_manager_tv_nub4 = (TextView) findViewById(R.id.user_manager_tv_nub4);
		user_manager_tv_nub5 = (TextView) findViewById(R.id.user_manager_tv_nub5);
		user_manager_tv_nub6 = (TextView) findViewById(R.id.user_manager_tv_nub6);
		user_manager_tv_nub7 = (TextView) findViewById(R.id.user_manager_tv_nub7);
		user_manager_tv_nub8 = (TextView) findViewById(R.id.user_manager_tv_nub8);
		user_manager_tv_nub9 = (TextView) findViewById(R.id.user_manager_tv_nub9);
		user_manager_tv_nub10 = (TextView) findViewById(R.id.user_manager_tv_nub10);
		call1 = (Button) findViewById(R.id.call1);
		call2 = (Button) findViewById(R.id.call2);
		call3 = (Button) findViewById(R.id.call3);
		call4 = (Button) findViewById(R.id.call4);
		call5 = (Button) findViewById(R.id.call5);
		call6 = (Button) findViewById(R.id.call6);
		call7 = (Button) findViewById(R.id.call7);
		call8 = (Button) findViewById(R.id.call8);
		call9 = (Button) findViewById(R.id.call9);
		call10 = (Button) findViewById(R.id.call10);

	}

	private void listenerButton() {

		call1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub1.getText().toString()));
				startActivity(intent);
			}
		});
		call2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub2.getText().toString()));
				startActivity(intent);
			}
		});
		call3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub3.getText().toString()));
				startActivity(intent);
			}
		});
		call4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub4.getText().toString()));
				startActivity(intent);
			}
		});
		call5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub5.getText().toString()));
				startActivity(intent);
			}
		});
		call6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub6.getText().toString()));
				startActivity(intent);
			}
		});
		call7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub7.getText().toString()));
				startActivity(intent);
			}
		});
		call8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub8.getText().toString()));
				startActivity(intent);
			}
		});
		call9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub9.getText().toString()));
				startActivity(intent);
			}
		});
		call10.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:"
						+ user_manager_tv_nub10.getText().toString()));
				startActivity(intent);
			}
		});

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
