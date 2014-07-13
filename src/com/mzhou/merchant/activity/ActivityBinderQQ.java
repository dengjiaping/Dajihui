package com.mzhou.merchant.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import com.mzhou.merchant.dao.IUser.IgetQQCheck;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class ActivityBinderQQ extends Activity implements OnClickListener {
	private Tencent mTencent;
	public static String APP_ID = "100523165";//1101307194  100523165
	private String openid;
	private Boolean common;
	private UserManager manager;
	private Context context;
	
	private DbLoginManager dbLoginManager;
	private DbUserManager dbUserManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_binderqq);
		context = ActivityBinderQQ.this;
		dbLoginManager = DbLoginManager.getInstance(context);
		dbUserManager = DbUserManager.getInstance(context);
		manager = new UserManager();
		findViewById(R.id.title_bar_showleft).setOnClickListener(this);
		findViewById(R.id.binder_again).setOnClickListener(this);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		common = bundle.getBoolean("common");
		mTencent = Tencent.createInstance(APP_ID, ActivityBinderQQ.this);
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
			System.out.println("开始异步获取用户信息");
			mTencent.requestAsync(Constants.GRAPH_USER_INFO, null,
					Constants.HTTP_GET, new BaseApiListener("get_user_info",
							false), null);
		}
	}
	private void saveLoginInfo(AllBean user,String usertype) {
		// 保存登录数据
		LoginUserBean loginUserBean = new LoginUserBean();
		loginUserBean.setLastlogin("1");
		loginUserBean.setUsername(user.getInfo().getUsername());
		loginUserBean.setPassword(user.getInfo().getPassword());
		loginUserBean.setUsertype(usertype);
		loginUserBean.setStatus("1");
		loginUserBean.setIsbinder("1");
		loginUserBean.setOpenid(openid);
		dbLoginManager.insertData(loginUserBean);
		System.out.println("保存登录数据");
	}

	private void saveUserInfo(AllBean user,String usertype) {
		// 保存用户信息
		UserInfoBean userInfoBean = new UserInfoBean();
		System.out.println("更新用户详细信息");
		userInfoBean.setContact(user.getInfo().getContact());
		userInfoBean.setStatus("1");
		userInfoBean.setUid(user.getInfo().getUid());
		userInfoBean.setNickname(user.getInfo().getNickname());
		userInfoBean.setPhonenub(user.getInfo().getPhonenub());
		userInfoBean.setCenter(user.getInfo().getCenter());//
		userInfoBean.setFax(user.getInfo().getFax());//
		userInfoBean.setCompany(user.getInfo().getCompany());
		userInfoBean.setAddress(user.getInfo().getAddress());
		userInfoBean.setNet(user.getInfo().getNet());
		userInfoBean.setCenter(user.getInfo().getCenter());
		userInfoBean.setFax(user.getInfo().getFax());
		userInfoBean.setCategory(user.getInfo().getCategory());
		userInfoBean.setHeadurl(MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());
		userInfoBean.setEmail(user.getInfo().getEmail());
		userInfoBean.setUsertype(usertype); 
		userInfoBean.setUsername(user.getInfo().getUsername());
		userInfoBean.setPassword(user.getInfo().getPassword());
		dbUserManager.insertData(userInfoBean);
		System.out.println("保存用户信息");
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
			System.out.println("获取信息成功"+response.toString());
			doComplete(response, state);
//			showResult("print", response.toString());
					 if (common) {
						 System.out.println("是普通用户，开始检查用户是否在服务器上绑定过");
						manager.Check(ActivityBinderQQ.this, MyConstants.LOGIN_URL, openid);
						manager.getQQCheck(new IgetQQCheck() {
							@Override
							public void getCheckInfo(AllBean user) {
								if (user != null) {
								 	if (user.getStatus().equals("true")) {//已经绑定了qq，请求服务器的数据
								 		 System.out.println("普通用户，已经绑定过");
//										save2SharedPrefenrence(user);
								 		saveLoginInfo(user,"0");
								 		saveUserInfo(user, "0");
										Intent intent = new Intent();
										intent.setClass(
												ActivityBinderQQ.this,
												UserControlCommonActivity.class);
										intent.putExtra("fromqq", true);
										startActivity(intent);
										finish();
									} else {
										 System.out.println("普通用户，未绑定");
										 LoginUserBean loginUserBean = new LoginUserBean();
											loginUserBean.setLastlogin("1");
											loginUserBean.setUsername("qq");
											loginUserBean.setPassword("123");
											loginUserBean.setUsertype("0");
											loginUserBean.setStatus("1");
											loginUserBean.setIsbinder("0");
											loginUserBean.setOpenid(openid);
											dbLoginManager.insertData(loginUserBean);
											
											UserInfoBean userInfoBean = new UserInfoBean();
											System.out.println("更新用户详细信息");
											userInfoBean.setStatus("1");
											try {
												userInfoBean.setNickname(response.getString("nickname"));
											} catch (JSONException e1) {
												System.out
														.println("昵称获取报错"+response.toString());
												userInfoBean.setNickname("qq昵称");
												e1.printStackTrace();
											}
											try {
												userInfoBean.setHeadurl(response.getString("figureurl_qq_2"));
											} catch (JSONException e1) {
												System.out
												.println("头像获取报错"+response.toString());
												userInfoBean.setHeadurl(MyConstants.PICTURE_URL);
												e1.printStackTrace();
											}
											userInfoBean.setUsertype("0"); 
											userInfoBean.setUsername("qq");
											userInfoBean.setPassword("123");
											dbUserManager.insertData(userInfoBean);
											Intent intent = new Intent();
											intent.setClass(ActivityBinderQQ.this,
													UserControlCommonActivity.class);
											intent.putExtra("fromqq", true);
											startActivity(intent);
											finish();
											
								/*		try {
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
										}*/
									} 
								} 
							}
						});
					} else { 
						 System.out.println("是企业用户，开始检查用户是否在服务器上绑定过");
						manager.Check(context, MyConstants.EN_LOGIN_URL, openid);
						manager.getQQCheck(new IgetQQCheck() {
							@Override
							public void getCheckInfo(AllBean user) {
								if (user != null) {
									if (user.getStatus().equals("true")) {
										System.out.println("企业用户已经绑定过");
//										save2Enterprise(user);
										saveLoginInfo(user,"1");
								 		saveUserInfo(user, "1");
										Intent intent = new Intent();
										intent.setClass(
												ActivityBinderQQ.this,
												UserControlEnterpriseActivity.class);
										intent.putExtra("fromqq", true);
										startActivity(intent);
										finish();
									} else {
										 System.out.println("企业用户，未绑定");
										 LoginUserBean loginUserBean = new LoginUserBean();
											loginUserBean.setLastlogin("1");
											loginUserBean.setUsername("qq");
											loginUserBean.setPassword("123");
											loginUserBean.setUsertype("1");
											loginUserBean.setStatus("1");
											loginUserBean.setIsbinder("0");
											loginUserBean.setOpenid(openid);
											dbLoginManager.insertData(loginUserBean);
											
											UserInfoBean userInfoBean = new UserInfoBean();
											System.out.println("更新用户详细信息");
											userInfoBean.setStatus("1");
											try {
												userInfoBean.setNickname(response.getString("nickname"));
											} catch (JSONException e1) {
												System.out
														.println("昵称获取报错"+response.toString());
												userInfoBean.setNickname("qq昵称");
												e1.printStackTrace();
											}
											try {
												userInfoBean.setHeadurl(response.getString("figureurl_qq_2"));
											} catch (JSONException e1) {
												System.out
												.println("头像获取报错"+response.toString());
												userInfoBean.setHeadurl(MyConstants.PICTURE_URL);
												e1.printStackTrace();
											}
											userInfoBean.setUsertype("1"); 
											userInfoBean.setUsername("qq");
											userInfoBean.setPassword("123");
											dbUserManager.insertData(userInfoBean);
											Intent intent = new Intent();
											intent.setClass(ActivityBinderQQ.this,
													UserControlCommonActivity.class);
											intent.putExtra("fromqq", true);
											startActivity(intent);
											finish();
								 
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
				System.out.println("qq登录失败");
			}

			@Override
			public void onComplete(JSONObject arg0) {
				System.out.println("qq登录成功");
				try {
					openid = arg0.getString("openid");
					System.out.println("openid---"+openid);
					mTencent.setOpenId(openid);
				} catch (JSONException e) {
					e.printStackTrace();
				}finally{
					System.out.println("获取用户信息--");
					getInfo();
				}
			}

			@Override
			public void onCancel() {
				System.out.println("qq登录取消");
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
