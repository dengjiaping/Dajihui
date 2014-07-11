package com.mzhou.merchant.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.mzhou.merchant.slidemenu.SlidingMenu;
import com.mzhou.merchant.utlis.MyConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

 	private boolean isLogin;
	private boolean isLogin_enterprise;
	private boolean isEnterprise;
	private String nickname;
	private String nickname_enterprise;

	protected ImageLoader imageLoader;
	private DisplayImageOptions options;

	private static SharedPreferences sp;
	private String headurl;
	private String headurl_enterprise;

	public Left() {
		this.mSlidingMenu = ActivityIndex.mSlidingMenu;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_left, null);
		init();
		loadButton(view);
		return view;
	}

	private void init() {
		sp = getActivity().getSharedPreferences("phonemerchant", 1);
		
		isLogin = sp.getBoolean("isLogin", false);
		isEnterprise = sp.getBoolean("isEnterprise", false);
		isLogin_enterprise = sp.getBoolean("isLogin_enterprise", false);
		headurl = sp.getString("headurl", "");
		headurl_enterprise = sp.getString("headurl_enterprise", "");
		nickname = sp.getString("nickname", "");
		nickname_enterprise = sp.getString("nickname_enterprise", "");
		imageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.head_default)
				.showImageForEmptyUri(R.drawable.head_default)
				.showImageOnFail(R.drawable.head_default)
				.cacheOnDisc().delayBeforeLoading(0)
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
		if (isEnterprise) {// 判断是否是企业会员登陆，如果是企业会员，设置企业会员的信息，否则，普通会员
			if (isLogin_enterprise) {
				title_bar_user_name.setText(nickname_enterprise);
				title_bar_user_login.setText(getResources().getString(
						R.string.isloginstatus));
				imageLoader.displayImage(headurl_enterprise, userhead, options);
			} else {
				imageLoader.displayImage(MyConstants.DRAWABLE_DEFOULT,
						userhead, options);
				title_bar_user_name.setText(getResources().getString(
						R.string.nickname));
				title_bar_user_login.setText(getResources().getString(
						R.string.nologinstatus));
			}
		} else {
			if (isLogin) {
				title_bar_user_name.setText(nickname);
				title_bar_user_login.setText(getResources().getString(
						R.string.isloginstatus));
				imageLoader.displayImage(headurl, userhead, options);
			} else {
				imageLoader.displayImage(MyConstants.DRAWABLE_DEFOULT,
						userhead, options);
				title_bar_user_name.setText(getResources().getString(
						R.string.nickname));
				title_bar_user_login.setText(getResources().getString(
						R.string.nologinstatus));
			}
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		loginLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isEnterprise) {// 判断是否是企业会员
					if (isLogin_enterprise) {// 判断企业会员是否已经登陆
						
						Intent intent = new Intent();
						intent.setClass(getActivity(),
								UserControlEnterpriseActivity.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent();
						intent.setClass(getActivity(), ActivityLogin.class);
						startActivity(intent);
					}
				} else {
					if (isLogin) {
						Intent intent = new Intent();
						intent.setClass(getActivity(),
								UserControlCommonActivity.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent();
						intent.setClass(getActivity(), ActivityLogin.class);
						startActivity(intent);
					}
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
