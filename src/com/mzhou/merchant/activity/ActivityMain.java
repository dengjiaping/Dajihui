package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mzhou.merchant.fragment.Left;
import com.mzhou.merchant.fragment.Right;
import com.mzhou.merchant.fragment.XianshiJinRiShoujiZhaoShang;
import com.mzhou.merchant.slidemenu.SlidingMenu;
import com.mzhou.merchant.utlis.MyUtlis;

public class ActivityMain extends FragmentActivity {
	public static SlidingMenu mSlidingMenu;
	private Context context;
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
		setContentView(R.layout.activity_main);
		 
		setView();
	}


	private void setView() {
		context = ActivityMain.this;
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		if (mSlidingMenu.getChildCount() != 0) {
			mSlidingMenu.removeAllViews();
		}
		mSlidingMenu.setLeftView(getLayoutInflater().inflate(
				R.layout.frame_left, null));
		mSlidingMenu.setRightView(getLayoutInflater().inflate(
				R.layout.frame_right, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.frame_center, null));
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		Left leftFragment = new Left();
		t.replace(R.id.left_frame, leftFragment);
		Right rightFragment = new Right(mSlidingMenu);
		t.replace(R.id.right_frame, rightFragment);
		ViewPageFragment viewPageFragment2 = new ViewPageFragment();
		t.replace(R.id.center_frame, viewPageFragment2);
		t.commit();
	}
 

	public static void showLeft() {
		mSlidingMenu.showLeftView();
	}

	public static void showRight() {
		mSlidingMenu.showRightView();
	}

	public static class ViewPageFragment extends Fragment {

		private MyAdapter mAdapter;
		private ViewPager mPager;
		private ArrayList<Fragment> pagerItemList;

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View mView = inflater.inflate(R.layout.view_pager, null);
			pagerItemList = new ArrayList<Fragment>();
			mPager = (ViewPager) mView.findViewById(R.id.pager0);
			pagerItemList.add(new XianshiJinRiShoujiZhaoShang());
			// pagerItemList.add(new XianshiBlank());
			// pagerItemList.add(page2);
			mAdapter = new MyAdapter(this.getFragmentManager());
			mPager.setAdapter(mAdapter);
			mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					if (arg1 < 0.5) {
						showRight();
					}
				}

				@Override
				public void onPageScrollStateChanged(int position) {
				}
			});

			return mView;
		}

		public class MyAdapter extends FragmentPagerAdapter {
			public MyAdapter(FragmentManager fm) {
				super(fm);
			}

			@Override
			public int getCount() {
				return pagerItemList.size();
			}

			@Override
			public Fragment getItem(int position) {

				Fragment fragment = null;
				if (position < pagerItemList.size())
					fragment = pagerItemList.get(position);
				else
					fragment = pagerItemList.get(0);

				return fragment;

			}
		}

	}

 
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				showLeft();
				MyUtlis.toastInfo(context, getResources().getString(R.string.exit_process));
				exitTime = System.currentTimeMillis();
			} else {
				 
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStart() {
		
		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		 
		super.onDestroy();
	}
 
}
