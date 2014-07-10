package com.mzhou.merchant.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import com.mzhou.merchant.dao.IUser.IgetQQCheck;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class ActivityBinderQQ extends Activity implements OnClickListener {
	private Tencent mTencent;
	public static String APP_ID = "1101307194";//1101307194  100523165
	private String openid;
	private Boolean common;
	private static SharedPreferences sp;
	private UserManager manager;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_binderqq);
		context = ActivityBinderQQ.this;
		sp = getSharedPreferences("phonemerchant", 1);
		manager = new UserManager();
		findViewById(R.id.title_bar_showleft).setOnClickListener(this);
		findViewById(R.id.binder_again).setOnClickListener(this);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		common = bundle.getBoolean("common");
		mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
		login();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_showleft:
			Intent intent = new Intent();
			intent.setClass(context, ActivityLogin.class);
			startActivity(intent);
			finish();
			break;
		case R.id.binder_again:
			login();
			break;
		default:
			break;
		}
	}

	public void getInfo() {
		boolean ready = mTencent.isSessionValid()
				&& mTencent.getOpenId() != null;
		if (ready) {
			mTencent.requestAsync(Constants.GRAPH_USER_INFO, null,
					Constants.HTTP_GET, new BaseApiListener("get_user_info",
							false), null);
		}
	}
	private void save2Enterprise(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name_enterprise", user.getInfo().getContact());// 联系人
		editor.putBoolean("isLogin_enterprise", true);// 是否登陆
		editor.putBoolean("isEnterprise", true);// 设置是企业会员
		editor.putString("openid_enterprise", openid);
		editor.putBoolean("isBinder_enterprise", true);// 设置是否绑定
		editor.putString("uid_enterprise", user.getUid());// 会员id
		editor.putString("nickname_enterprise", user.getInfo().getNickname());// 昵称
		editor.putString("username_enterprise", user.getInfo().getUsername());// 账号
		editor.putString("password_enterprise", user.getInfo().getPassword());// 密码
		editor.putString("company_center_enterprise", user.getInfo()
				.getCenter());// 总机
		editor.putString("company_fax_enterprise", user.getInfo().getFax());// 传真
		editor.putString("company_enterprise", user.getInfo().getCompany());// 公司名称
		editor.putString("address_enterprise", user.getInfo().getAddress());// 公司地址
		editor.putString("net_enterprise", user.getInfo().getNet());// 公司网址
		editor.putString("headurl_enterprise", MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());// 头像地址
		editor.commit();
	}
	private void save2SharedPrefenrence(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name", user.getInfo().getContact());// 联系人
		editor.putBoolean("isLogin", true);
		editor.putBoolean("isEnterprise", false);// 设置不是企业会员
		editor.putBoolean("isBinder", true);// 设置是否绑定
		editor.putString("uid", user.getUid());
		editor.putString("openid", openid);
		editor.putString("nickname", user.getInfo().getNickname());// 昵称
		editor.putString("username", user.getInfo().getUsername());// 账号
		editor.putString("password", user.getInfo().getPassword());// 密码
		editor.putString("phonenub", user.getInfo().getPhonenub());
		editor.putString("company", user.getInfo().getCompany());
		editor.putString("address", user.getInfo().getAddress());
		editor.putString("net", user.getInfo().getNet());
		editor.putString("category", user.getInfo().getCategory());
		editor.putString("headurl", MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());
		editor.putString("email", user.getInfo().getEmail());
		editor.commit();
	}
	private class BaseApiListener implements IRequestListener {
		private String mScope = "all";
		private Boolean mNeedReAuth = false;

		public BaseApiListener(String scope, boolean needReAuth) {
			mScope = scope;
			mNeedReAuth = needReAuth;
		}

		@Override
		public void onComplete(final JSONObject response, Object state) {

			doComplete(response, state);
//			showResult("print", response.toString());
					 if (common) {
						manager.Check(ActivityBinderQQ.this, MyConstants.LOGIN_URL, openid);
						manager.getQQCheck(new IgetQQCheck() {
							@Override
							public void getCheckInfo(AllBean user) {
								if (user != null) {
								 	if (user.getStatus().equals("true")) {
										save2SharedPrefenrence(user);
										Intent intent = new Intent();
										intent.setClass(
												ActivityBinderQQ.this,
												UserControlCommonActivity.class);
										intent.putExtra("fromqq", true);
										startActivity(intent);
										finish();
									} else {
										try {
											Editor editor = sp.edit();
											editor.putString("headurl",
													response.getString("figureurl_qq_2"));
											editor.putString("nickname",
													response.getString("nickname"));
											editor.putBoolean("isBinder", false);// 设置是否绑定
											editor.putBoolean("isEnterprise", false);
											editor.putString("openid", openid);
											editor.putBoolean("isLogin", true);
											editor.commit();
											Intent intent = new Intent();
											intent.setClass(ActivityBinderQQ.this,
													UserControlCommonActivity.class);
											intent.putExtra("fromqq", true);
											startActivity(intent);
											finish();
										} catch (JSONException e) {
											e.printStackTrace();
										}
									} 
								} 
							}
						});
					} else { 
						manager.Check(context, MyConstants.EN_LOGIN_URL, openid);
						manager.getQQCheck(new IgetQQCheck() {
							@Override
							public void getCheckInfo(AllBean user) {
								if (user != null) {
									if (user.getStatus().equals("true")) {
										save2Enterprise(user);
										Intent intent = new Intent();
										intent.setClass(
												ActivityBinderQQ.this,
												UserControlEnterpriseActivity.class);
										intent.putExtra("fromqq", true);
										startActivity(intent);
										finish();
									} else {
										try {
											Editor editor = sp.edit();
											editor.putString("headurl_enterprise",
													response.getString("figureurl_qq_2"));
											editor.putString("nickname_enterprise",
													response.getString("nickname"));
													editor.putString("openid_enterprise", openid);
											editor.putBoolean("isEnterprise", true);
											editor.putBoolean("isBinder_enterprise", false);// 设置是否绑定
											editor.putBoolean("isLogin", true);
											editor.commit();
											Intent intent = new Intent();
											intent.setClass(ActivityBinderQQ.this,
													UserControlEnterpriseActivity.class);
											intent.putExtra("fromqq", true);
											startActivity(intent);
											finish();
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								} 
							}
						});
					 
					 } 
			 
		}

		protected void doComplete(JSONObject response, Object state) {
			try {
				int ret = response.getInt("ret");
				if (ret == 100030) {
					if (mNeedReAuth) {
						Runnable r = new Runnable() {
							public void run() {
								mTencent.reAuth(ActivityBinderQQ.this, mScope,
										new BaseUiListener());
							}
						};
						ActivityBinderQQ.this.runOnUiThread(r);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onIOException(final IOException e, Object state) {
			showResult("IRequestListener.onIOException:", e.getMessage());
		}

		@Override
		public void onMalformedURLException(final MalformedURLException e,
				Object state) {
			showResult("IRequestListener.onMalformedURLException", e.toString());
		}

		@Override
		public void onJSONException(final JSONException e, Object state) {
			showResult("IRequestListener.onJSONException:", e.getMessage());
		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException arg0,
				Object arg1) {
			showResult("IRequestListener.onConnectTimeoutException:",
					arg0.getMessage());
		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException arg0,
				Object arg1) {
			showResult("IRequestListener.SocketTimeoutException:",
					arg0.getMessage());
		}

		@Override
		public void onUnknowException(Exception arg0, Object arg1) {
			showResult("IRequestListener.onUnknowException:", arg0.getMessage());
 
		}

		@Override
		public void onHttpStatusException(HttpStatusException arg0, Object arg1) {
			showResult("IRequestListener.HttpStatusException:",
					arg0.getMessage());
		}

		@Override
		public void onNetworkUnavailableException(
				NetworkUnavailableException arg0, Object arg1) {
			showResult("IRequestListener.onNetworkUnavailableException:",
					arg0.getMessage());
		}
	}

	private void showResult(final String base, final String msg) {
//		Log.i("print", msg.toString());
		/*mHandler.post(new Runnable() {
			@Override
			public void run() {
				Log.i("print", msg.toString());
			}
		});*/
	}


	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {

			doComplete(response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			showResult("onError:", "code:" + e.errorCode + ", msg:"
					+ e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showResult("onCancel", "");
		}
	}

	public void login() {
		
		
		mTencent.login(this, "all", new IUiListener() {
			@Override
			public void onError(UiError arg0) {
			}

			@Override
			public void onComplete(JSONObject arg0) {

				try {
					openid = arg0.getString("openid");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getInfo();
			}

			@Override
			public void onCancel() {
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mTencent.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(context, ActivityLogin.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
