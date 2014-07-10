package com.mzhou.merchant.activity;

import java.util.ArrayList;
import java.util.List;

import com.mzhou.merchant.adapter.MyPagerAdapter;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ActivityLogin extends Activity {
	private ViewPager mPager;
	private List<View> listViews;
	private TextView t1, t2;
	private ImageView showLeft;

	private LocalActivityManager manager = null;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login);
		init(savedInstanceState);
		InitTextView();
		InitViewPager();
	}

	private void init(Bundle savedInstanceState) {
		context = getBaseContext();
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {

		t2 = (TextView) findViewById(R.id.enterprise_member);
		t1 = (TextView) findViewById(R.id.common_member);
		showLeft = (ImageView) findViewById(R.id.title_bar_showleft);
		t2.setOnClickListener(new MyOnClickListener(1));
		t1.setOnClickListener(new MyOnClickListener(0));
		showLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ActivityLogin.this, ActivityIndex.class);
				startActivity(intent);
				finish();
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(ActivityLogin.this, ActivityIndex.class);
			startActivity(intent);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		Intent intent = new Intent(context, LoginCommenActivity.class);
		listViews.add(getView("Black", intent));
		Intent intent2 = new Intent(context, LoginEnterpriseActivity.class);
		listViews.add(getView("Gray", intent2));
		MyPagerAdapter mpAdapter = new MyPagerAdapter(listViews);
		mPager.setAdapter(mpAdapter);

		boolean isEnterprise = getIntent().getBooleanExtra("isEnterprise",
				false);
		if (isEnterprise) {
			t2.setSelected(true);
			t1.setSelected(false);
			mPager.setCurrentItem(1);
		} else {
			t2.setSelected(false);
			t1.setSelected(true);
			mPager.setCurrentItem(0);
		}
		 
	 
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
			if (i == 0) {
				t1.setSelected(true);
				t2.setSelected(false);
			} else if (i == 1) {
				t1.setSelected(false);
				t2.setSelected(true);
			}
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				t1.setSelected(true);
				t2.setSelected(false);
				break;
			case 1:
				t1.setSelected(false);
				t2.setSelected(true);
				break;

			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
}
