package com.mzhou.merchant.fragment;

import java.lang.Thread.UncaughtExceptionHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mzhou.merchant.activity.ActivityAbout;
import com.mzhou.merchant.activity.ActivityFankui;
import com.mzhou.merchant.activity.ActivityHuoDong;
import com.mzhou.merchant.activity.ActivityIndex;
import com.mzhou.merchant.activity.ActivityLogin;
import com.mzhou.merchant.activity.ActivityManual;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.UserControlCommonActivity;
import com.mzhou.merchant.activity.UserControlEnterpriseActivity;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.slidemenu.SlidingMenu;

@SuppressLint("ValidFragment")
public class Right extends Fragment {

	private TextView title_bar_user_center;
	private TextView title_bar_abount_use;
	private TextView title_bar_about;
	private TextView title_bar_activity;
	private SlidingMenu mSlidingMenu;
	private TextView title_bar_fankui;
	private TextView title_bar_exit;
	private Context context;
	
	private DbLoginManager loginManager;
	private DbUserManager userManager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
	         
		    
		View view = inflater.inflate(R.layout.fragment_right, null);
		init();
		loadButton(view);

		return view;
	}

	private void init() {
		context = getActivity();
		loginManager = DbLoginManager.getInstance(getActivity());
		userManager = DbUserManager.getInstance(getActivity());
	}

	private void loadButton(View view) {
		title_bar_user_center = (TextView) view
				.findViewById(R.id.title_bar_user_center);
		title_bar_abount_use = (TextView) view
				.findViewById(R.id.title_bar_abount_use);
		title_bar_about = (TextView) view.findViewById(R.id.title_bar_about);
		title_bar_fankui = (TextView) view.findViewById(R.id.title_bar_fankui);
		title_bar_exit = (TextView) view.findViewById(R.id.title_bar_exit);
		title_bar_activity = (TextView) view.findViewById(R.id.title_bar_activity);

	}

	public Right(SlidingMenu mSlidingMenu) {
		this.mSlidingMenu = mSlidingMenu;
	}

	public Right() {

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		title_bar_user_center.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("点击右侧 会员中心");
				
				if (loginManager.getLoginStatus()) {//有登录状态
					System.out.println("有登录状态");
					UserInfoBean bean = userManager.getLogingUserInfo();
					if (bean != null) {
						if (bean.getUsertype() != null && bean.getUsertype().equals("1")) {//企业会员
							System.out.println("企业会员");
							Intent intent = new Intent();
							intent.setClass(getActivity(),
									UserControlEnterpriseActivity.class);
							startActivity(intent);
						}else {//普通会员
							System.out.println("普通会员");
							Intent intent = new Intent();
							intent.setClass(getActivity(),
									UserControlCommonActivity.class);
							startActivity(intent);
						}
					}
				}else {//当前没有登录状态
					System.out.println("当前没有登录状态");
					Intent intent = new Intent();
					intent.setClass(getActivity(), ActivityLogin.class);
					startActivity(intent);
				}

				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});

		title_bar_abount_use.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityManual.class);
				startActivity(intent);
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_activity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityHuoDong.class);
				startActivity(intent);
				mSlidingMenu.showLeftView();
			}
		});

		title_bar_about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityAbout.class);
				startActivity(intent);
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_fankui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityFankui.class);
				startActivity(intent);
				mSlidingMenu.showLeftView();
			}
		});

		title_bar_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loginManager.getLoginStatus()) {//有登录状态
						System.out.println("有登录状态");
						loginManager.updateLoginStatus();
						userManager.updateLoginStatus();
						Intent intent = new Intent();
						intent.setClass(getActivity(), ActivityIndex.class);
						startActivity(intent);
						mSlidingMenu.showLeftView();
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.exitok), Toast.LENGTH_SHORT)
								.show();
						getActivity().finish();
				}else {
					Toast.makeText(
							getActivity(),
							getActivity().getResources().getString(
									R.string.exitfail), Toast.LENGTH_SHORT)
							.show();
				
				}
				
				
			}
		});

	}

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getActivity().getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getActivity()
				.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}
}
