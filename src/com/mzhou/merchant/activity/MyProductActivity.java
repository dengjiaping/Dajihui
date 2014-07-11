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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MyProductActivity extends Activity {
	private ViewPager mPager;
	private List<View> listViews;
	private TextView t1, t2;
	private ImageView showLeft;

	private LocalActivityManager manager = null;
	private Context context;
	private boolean authstr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_manager_myproduct);
		init(savedInstanceState);
		InitTextView();
		InitViewPager();
	}

	private void init(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		authstr = bundle.getBoolean("authstr");
		context = getBaseContext();
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {

		t2 = (TextView) findViewById(R.id.text2);
		t1 = (TextView) findViewById(R.id.text1);
		showLeft = (ImageView) findViewById(R.id.title_bar_showleft);
		t2.setOnClickListener(new MyOnClickListener(1));
		t1.setOnClickListener(new MyOnClickListener(0));
		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		Intent intent = new Intent(context, MyProductAlreadyCheckActivity.class);
		listViews.add(getView("Black", intent));
		Intent intent2 = new Intent(context, MyProductNoCheckActivity.class);
		listViews.add(getView("Gray", intent2));
		MyPagerAdapter mpAdapter = new MyPagerAdapter(listViews);
		mPager.setAdapter(mpAdapter);
		if (authstr) {
			mPager.setCurrentItem(1);
			t2.setSelected(true);
			t1.setSelected(false);
		} else {
			mPager.setCurrentItem(0);
			t2.setSelected(false);
			t1.setSelected(true);
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
