package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
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
import com.mzhou.merchant.fragment.XianshiLogoChangjia;
import com.mzhou.merchant.slidemenu.SlidingMenu;
import com.mzhou.merchant.utlis.MyUtlis;

public class ActivityLogo extends FragmentActivity {
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
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		System.out.println("onActivityResult");
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("onNewIntent");
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		System.out.println("onPause");
		super.onPause();
	}

	@Override
	protected void onPostResume() {
		System.out.println("onPostResume");
		super.onPostResume();
			 
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		System.out.println("onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}




  
	@Override
	protected void onRestart() {
		System.out.println("onRestart");
		super.onRestart();
		
		 
	}
	 
	@Override
	protected void onStart() {
		System.out.println("onStart");
	
		super.onStart();
	}
  
	private void setView() {
		context = ActivityLogo.this;
		
		
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
		 
		  Left leftFragment = new Left();
		  ViewPageFragment viewPageFragment2 = new ViewPageFragment();
		  Right rightFragment = new Right(mSlidingMenu);
		  FragmentTransaction   t = this.getSupportFragmentManager()
					.beginTransaction();
		t.replace(R.id.left_frame, leftFragment);
		t.replace(R.id.right_frame, rightFragment);
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
			pagerItemList.add(new XianshiLogoChangjia());
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

}
