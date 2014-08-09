package com.mzhou.merchant.activity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mzhou.merchant.dao.IUser.IgetQQCheck;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginCommenActivity extends Activity {
	private EditText user_login_username_hint;
	private EditText user_login_password_hint;
	private CheckBox user_checkbox1;
	private CheckBox user_checkbox2;
	private TextView user_forgetpasswd;
	private Button user_button_login;
	private TextView user_button_register;
	private String username;
	private String password;
	private LinearLayout qq_login;
	public static UserManager userManager = null;
	private boolean remeberpassword = false;
	private boolean loginself = false;

	private DbLoginManager dbLoginManager;
	private DbUserManager dbUserManager;
	private Tencent mTencent = null;
	private UserInfo mInfo;

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
		setContentView(R.layout.user_login_common);
		init();
		loadButton();
		setData();
		listenerButton();
	}

	private void init() {
		dbLoginManager = DbLoginManager.getInstance(this);
		dbUserManager = DbUserManager.getInstance(this);
		mTencent = Tencent.createInstance(MyConstants.APP_ID,
				LoginCommenActivity.this);
		userManager = new UserManager();
	}

	/**
	 * 监听所有的事件
	 */

	private void listenerButton() {
		user_button_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				username = user_login_username_hint.getText().toString();
				password = user_login_password_hint.getText().toString();
				Map<String, String> map = new HashMap<String, String>();
				map.put("subject", "login");
				map.put("data[un]", username);
				map.put("data[pw]", password);
				if (WebIsConnectUtil.showNetState(LoginCommenActivity.this)) {
					userManager.Login(LoginCommenActivity.this,
							MyConstants.LOGIN_URL, map);// 登陆
					userManager
							.getUserInfoIml(new com.mzhou.merchant.dao.IUser.IgetUserInfo() {

								@Override
								public void getInfo(AllBean user) {
									if (user != null) {
										if (user.getStatus().equals("true")) {
											System.out.println("普通会员登录成功");
											saveLoginInfo();
											saveUserInfo(user);
											toUserControlCenter(user);
										} else {
											Toast.makeText(
													LoginCommenActivity.this,
													user.getMsg(),
													Toast.LENGTH_SHORT).show();
											return;
										}
									}
								}

							});
				}
			}
		});

		user_button_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginCommenActivity.this,
						RegisterCommonActivity.class);
				startActivity(intent);
			}
		});
		user_forgetpasswd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginCommenActivity.this,
						ForgetPassWordActivity.class);
				startActivity(intent);
			}
		});
		user_checkbox1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						remeberpassword = isChecked;
						if (isChecked) {// 选中记住密码

						} else {// 取消记住密码
							user_checkbox2.setChecked(false);
							loginself = false;
						}
					}
				});
		user_checkbox2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						loginself = isChecked;
						if (isChecked) {
							user_checkbox1.setChecked(true);
							remeberpassword = true;
						}

					}
				});
		qq_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				QQLogin();
			}
		});
	}

	private void toUserControlCenter(AllBean user) {
		Intent intent = new Intent();
		intent.setClass(LoginCommenActivity.this,
				UserControlCommonActivity.class);
		startActivity(intent);
		Toast.makeText(LoginCommenActivity.this, user.getMsg(),
				Toast.LENGTH_SHORT).show();
		finish();
	}

	private void saveLoginInfo() {
		// 保存登录数据
		LoginUserBean loginUserBean = new LoginUserBean();
		loginUserBean.setLastlogin("1");
		loginUserBean.setUsername(username);
		loginUserBean.setPassword(password);
		loginUserBean.setUsertype("0");
		if (remeberpassword) {
			loginUserBean.setIsremeber("1");
		} else {
			loginUserBean.setIsremeber("0");
		}
		if (loginself) {
			loginUserBean.setIsloginself("1");
		} else {
			loginUserBean.setIsloginself("0");
		}
		loginUserBean.setStatus("1");
		if (remeberpassword && loginself) {
			loginUserBean.setNeedlogin("1");
		} else {
			loginUserBean.setNeedlogin("0");
		}
		dbLoginManager.insertData(loginUserBean);
		System.out.println("保存登录数据");
	}

	private void saveUserInfo(AllBean user) {

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
		userInfoBean.setCategory(user.getInfo().getCategory());
		userInfoBean.setHeadurl(MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());
		userInfoBean.setEmail(user.getInfo().getEmail());
		userInfoBean.setUsertype("0");
		userInfoBean.setUsername(username);
		userInfoBean.setPassword(password);
		dbUserManager.insertData(userInfoBean);
		System.out.println("保存用户信息");
	}

	/**
	 * 加载所有的控件
	 */
	private void loadButton() {
		user_login_username_hint = (EditText) findViewById(R.id.user_login_username_hint);
		user_login_password_hint = (EditText) findViewById(R.id.user_login_password_hint);
		qq_login = (LinearLayout) findViewById(R.id.qq_login);
		user_forgetpasswd = (TextView) findViewById(R.id.user_forgetpasswd);
		user_checkbox1 = (CheckBox) findViewById(R.id.user_checkbox1);
		user_checkbox2 = (CheckBox) findViewById(R.id.user_checkbox2);
		user_button_login = (Button) findViewById(R.id.user_button_login);
		user_button_register = (TextView) findViewById(R.id.user_button_register);

	}

	/**
	 * 配置显示信息
	 */
	private void setData() {
		LoginUserBean loginUserBean = dbLoginManager
				.getLastLoginByUserType("0");// 查询普通会员上次登录的数据
		if (loginUserBean != null) {// 上次有登录的帐号
			System.out.println("查询普通会员上次登录的数据" + loginUserBean.toString());

			if (loginUserBean.getUsername() != null
					&& !loginUserBean.getUsername().equals("null")) {
				System.out.println("存在用户名 - " + loginUserBean.getUsername());
				user_login_username_hint.setText(loginUserBean.getUsername());// 用户名
			} else {
				System.out.println("不存在用户名 - ");
				user_login_username_hint.setText("");// 用户名
			}

			if (loginUserBean.getIsremeber() != null
					&& loginUserBean.getIsremeber().equals("1")) {// 是记住密码
				user_checkbox1.setChecked(true);// 密码
				remeberpassword = true;

				System.out.println("是否记住密码"
						+ loginUserBean.getIsremeber().equals("1"));
				user_login_password_hint.setText(loginUserBean.getPassword());

				if (loginUserBean.getIsloginself().equals("1")) {// 是自动登录
					user_checkbox2.setChecked(true);
					loginself = true;
					System.out.println("是自动登录");
				} else {// 不是自动登录
					user_checkbox2.setChecked(false);
					loginself = false;
					System.out.println("不是自动登录");
				}

			} else {
				loginself = false;
				remeberpassword = false;
				user_checkbox1.setChecked(false);
				user_checkbox2.setChecked(false);
				user_login_password_hint.setText("");
				System.out.println("是否记住密码  false");
			}

		} else {// 上次没有登录的帐号
			System.out.println("上次没有登录的帐号");
			user_login_username_hint.setText("");// 用户名
			user_login_password_hint.setText("");// 密码
			user_checkbox1.setChecked(false);
			user_checkbox2.setChecked(false);
			loginself = false;
			remeberpassword = false;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(LoginCommenActivity.this, ActivityIndex.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// ***************************************************QQLogin*************************************************

	public void QQLogin() {
		if (!mTencent.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					Log.d("SDKQQAgentPref",
							"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
					System.out.println("登录成功---values---" + values.toString());
					System.out
							.println("获取用户openid-----" + mTencent.getOpenId());
					MyUtlis.toastInfo(LoginCommenActivity.this, "正在检测数据...");
					updateUserInfo();
				}
			};
			mTencent.login(this, "all", listener);
			Log.d("SDKQQAgentPref",
					"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
			mTencent.logout(this);
			MyUtlis.toastInfo(LoginCommenActivity.this, "正在检测数据...");
			updateUserInfo();

		}

	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {
		}

		@Override
		public void onError(UiError e) {
		}

		@Override
		public void onCancel() {
		}
	}

	private void updateUserInfo() {
		System.out.println("获取用户信息");
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					System.out.println("用户信息--response----》"
							+ response.toString());
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);

				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);

		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				String nickName = null;
				String figureurl = null;
				try {
					JSONObject response = (JSONObject) msg.obj;
					if (response.has("nickname")) {
						nickName = response.getString("nickname");
					}
					if (response.has("figureurl")) {
						figureurl = response.getString("figureurl_qq_2");
					}
					if (nickName == null) {
						nickName ="大机汇(普通版)";
					}
					if (figureurl == null) {
						figureurl="http://dd";
					}
					MyUtlis.toastInfo(LoginCommenActivity.this, "正在加载...");
					checkAPPID(nickName,figureurl);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	};
	private void checkAPPID(final String nickName ,final String headUrl){
		userManager.Check(LoginCommenActivity.this, MyConstants.LOGIN_URL, mTencent.getOpenId());
		userManager.getQQCheck(new IgetQQCheck() {
			@Override
			public void getCheckInfo(AllBean user) {
				if (user != null) {
				 	if (user.getStatus().equals("true")) {//已经绑定了qq，请求服务器的数据
				 		 System.out.println("普通用户，已经绑定过");
//						save2SharedPrefenrence(user);
				 		saveLoginInfo(user,"0");
				 		saveUserInfo(user, "0");
						Intent intent = new Intent();
						intent.setClass(
								LoginCommenActivity.this,
								UserControlCommonActivity.class);
						intent.putExtra("fromqq", true);
						intent.putExtra("binder", true);
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
							loginUserBean.setOpenid(mTencent.getOpenId());
							dbLoginManager.insertData(loginUserBean);
							
							UserInfoBean userInfoBean = new UserInfoBean();
							System.out.println("更新用户详细信息");
							userInfoBean.setStatus("1");
							userInfoBean.setNickname(nickName);
							userInfoBean.setHeadurl(headUrl);
							userInfoBean.setUsertype("0"); 
							userInfoBean.setUsername("qq");
							userInfoBean.setPassword("123");
							dbUserManager.insertData(userInfoBean);
							Intent intent = new Intent();
							intent.setClass(LoginCommenActivity.this,
									UserControlCommonActivity.class);
							intent.putExtra("fromqq", true);
							intent.putExtra("binder", false);
							startActivity(intent);
							finish();
							
					} 
				} 
			}
		});
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
		loginUserBean.setIsloginself("0");
		loginUserBean.setIsremeber("0");
		loginUserBean.setOpenid(mTencent.getOpenId());
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
	
	 @Override
	 protected void onDestroy() {
	 	// TODO Auto-generated method stub
	 	super.onDestroy();
	 	mTencent = null;
	 }
}
