package com.mzhou.merchant.fragment;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mzhou.merchant.activity.ActivityFourPhone;
import com.mzhou.merchant.activity.ActivityGX;
import com.mzhou.merchant.activity.ActivityIndex;
import com.mzhou.merchant.activity.ActivityLogin;
import com.mzhou.merchant.activity.ActivityLogo;
import com.mzhou.merchant.activity.ActivityLogoProduct;
import com.mzhou.merchant.activity.ActivityMain;
import com.mzhou.merchant.activity.ActivityPP;
import com.mzhou.merchant.activity.ActivityQG;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.ActivitySM;
import com.mzhou.merchant.activity.ActivitySS;
import com.mzhou.merchant.activity.ActivityTD;
import com.mzhou.merchant.activity.UserControlCommonActivity;
import com.mzhou.merchant.activity.UserControlEnterpriseActivity;
import com.mzhou.merchant.activity.ActivityWD;
import com.mzhou.merchant.activity.ActivityWM;
import com.mzhou.merchant.activity.ActivityXinWen;
import com.mzhou.merchant.activity.ActivityZS;
import com.mzhou.merchant.activity.ActivityZP;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.slidemenu.SlidingMenu;
import com.mzhou.merchant.utlis.MyConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class Left extends Fragment {

	private SlidingMenu mSlidingMenu;
	private TextView title_bar_user_login;
	private TextView title_bar_user_name;
	private TextView title_bar_main;
	private TextView title_bar_zhaoshang;
	private TextView title_bar_qiugou;
	private TextView title_bar_pinpai;
	private TextView title_bar_wuma;
	private TextView title_bar_sanma;
	private TextView title_bar_waidan;
	private TextView title_bar_td;
	private TextView title_bar_logo;
	private TextView title_bar_news;
	private LinearLayout title_bar_serch;
	private TextView title_bar_zhaopin;
	private TextView title_bar_index;
	private TextView title_bar_gexing;
	private TextView title_bar_logo_product;
	private TextView title_bar_four_phone;
	private LinearLayout loginLinearLayout;
	private ImageView userhead;


	protected ImageLoader imageLoader;
	private DisplayImageOptions options;

	private DbLoginManager loginManager;
	private DbUserManager userManager;
	public Left() {
		this.mSlidingMenu = ActivityIndex.mSlidingMenu;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		View view = inflater.inflate(R.layout.fragment_left, null);
		init();
		loadButton(view);
		return view;
	}

	private void init() {
		loginManager = DbLoginManager.getInstance(getActivity());
		userManager = DbUserManager.getInstance(getActivity());
	 
		imageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_stub)
		.showImageOnFail(R.drawable.ic_stub)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();

	}

	private void loadButton(View view) {
		title_bar_user_login = (TextView) view
				.findViewById(R.id.title_bar_user_login);
		title_bar_user_name = (TextView) view
				.findViewById(R.id.title_bar_user_name);
		loginLinearLayout = (LinearLayout) view
				.findViewById(R.id.login_linearlayout);
		userhead = (ImageView) view.findViewById(R.id.user_login_image);
		title_bar_main = (TextView) view.findViewById(R.id.title_bar_main);
		title_bar_zhaoshang = (TextView) view
				.findViewById(R.id.title_bar_zhaoshang);
		title_bar_qiugou = (TextView) view.findViewById(R.id.title_bar_qiugou);
		title_bar_pinpai = (TextView) view.findViewById(R.id.title_bar_pinpai);
		title_bar_wuma = (TextView) view.findViewById(R.id.title_bar_wuma);
		title_bar_sanma = (TextView) view.findViewById(R.id.title_bar_sanma);
		title_bar_waidan = (TextView) view.findViewById(R.id.title_bar_waidan);
		title_bar_td = (TextView) view.findViewById(R.id.title_bar_td);
		title_bar_logo = (TextView) view.findViewById(R.id.title_bar_logo);
		title_bar_gexing = (TextView) view.findViewById(R.id.title_bar_gexing);
		title_bar_logo_product = (TextView) view.findViewById(R.id.title_bar_logo_product);
		title_bar_four_phone = (TextView) view.findViewById(R.id.title_bar_four_phone);
		title_bar_news = (TextView) view.findViewById(R.id.title_bar_news);
		title_bar_serch = (LinearLayout) view
				.findViewById(R.id.title_bar_serch);
		title_bar_zhaopin = (TextView) view
				.findViewById(R.id.title_bar_zhaopin);
		title_bar_index = (TextView) view.findViewById(R.id.title_bar_ind);
		
		
		
		if (loginManager.getLoginStatus()) {//有登录状态
			UserInfoBean bean = userManager.getLogingUserInfo();
			if (bean != null) {
				title_bar_user_name.setText(bean.getNickname()+"");//设置昵称
				imageLoader.displayImage(bean.getHeadurl()+"", userhead, options);
				title_bar_user_login.setText(getResources().getString(
						R.string.isloginstatus));
			}else {
				imageLoader.displayImage(MyConstants.DRAWABLE_DEFOULT,
						userhead, options);
				title_bar_user_name.setText(getResources().getString(
						R.string.nickname));
				title_bar_user_login.setText(getResources().getString(
						R.string.nologinstatus));
			
			}
		}else {
			imageLoader.displayImage(MyConstants.DRAWABLE_DEFOULT,
					userhead, options);
			title_bar_user_name.setText(getResources().getString(
					R.string.nickname));
			title_bar_user_login.setText(getResources().getString(
					R.string.nologinstatus));
		
		}
		
	 
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		loginLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("点击左侧头像");
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
		title_bar_index.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityIndex.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		title_bar_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityMain.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		title_bar_gexing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityGX.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		title_bar_logo_product.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityLogoProduct.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		title_bar_four_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityFourPhone.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_zhaoshang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityZS.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_qiugou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityQG.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});

		title_bar_pinpai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityPP.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_wuma.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityWM.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_sanma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivitySM.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_waidan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityWD.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_td.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityTD.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_logo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityLogo.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityXinWen.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});
		title_bar_serch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivitySS.class);
				startActivity(intent);

			}
		});
		title_bar_zhaopin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ActivityZP.class);
				startActivity(intent);
				getActivity().finish();
				mSlidingMenu.showLeftView();
			}
		});

	}

 
}
