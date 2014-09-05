package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

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
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginEnterpriseActivity extends Activity {

	private EditText user_login_username_hint;
	private EditText user_login_password_hint;
	private CheckBox user_checkbox1;
	private CheckBox user_checkbox2;
	private TextView user_forgetpasswd;
	private Button user_button_login;
	private TextView user_button_register;
	  private LinearLayout qq_login;
	private String username_enterprise;
	private String password_enterprise;
 	public static UserManager userManager = null;
	private boolean remeberpassword_enterprise = false;
	private boolean loginself_enterprise = false;

	
	private DbLoginManager dbLoginManager;
	private DbUserManager dbUserManager;
	
	private Tencent mTencent = null;
	private UserInfo mInfo;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	     
		setContentView(R.layout.user_login_enterprise);
		init();
		loadButton();
		setData();
		listenerButton();
	}

	private void init() {
		userManager = new UserManager();
		dbLoginManager = DbLoginManager.getInstance(this);
		dbUserManager = DbUserManager.getInstance(this);
		mTencent = Tencent.createInstance(MyConstants.APP_ID,
				LoginEnterpriseActivity.this);
	}

	/**
	 * 监听所有的事件
	 */
	private void listenerButton() {
		user_button_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				username_enterprise = user_login_username_hint.getText()
						.toString();
				password_enterprise = user_login_password_hint.getText()
						.toString();
				if (WebIsConnectUtil.showNetState(LoginEnterpriseActivity.this)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("subject", "login");
					map.put("data[un]", username_enterprise);
					map.put("data[pw]", password_enterprise);
					userManager.Login(LoginEnterpriseActivity.this,
							MyConstants.EN_LOGIN_URL,map);// 登陆
					userManager
							.getUserInfoIml(new com.mzhou.merchant.dao.IUser.IgetUserInfo() {

								@Override
								public void getInfo(AllBean user) {
									if (user != null) {
										if (user.getStatus().equals("true")) {
											System.out.println("企业会员登录成功");
											saveLoginInfo();
											saveUserInfo(user);
											toUserControlCenter(user);
										} else {
											Toast.makeText(
													LoginEnterpriseActivity.this,
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
				intent.setClass(LoginEnterpriseActivity.this,
						RegisterEnterpriseActivity.class);
				startActivity(intent);
				finish();
			}
		});
		user_forgetpasswd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginEnterpriseActivity.this,
						ForgetPassWordActivity.class);
				startActivity(intent);
			}
		});
		user_checkbox1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						remeberpassword_enterprise = isChecked;
						if (!isChecked) {//如果没有选中记住密码
							user_checkbox2.setChecked(false);
							loginself_enterprise = false;
						}
					}
				});
		user_checkbox2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						loginself_enterprise = isChecked;
						if (isChecked) {//如果选中来自动登录
							user_checkbox1.setChecked(true);
							remeberpassword_enterprise = true;
						}

					}
				});
		qq_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*Intent intent = new Intent();
				intent.setClass(LoginEnterpriseActivity.this,
						ActivityBinderQQ.class);
				intent.putExtra("common", false);
				startActivity(intent);finish();*/
				QQLogin();
			}
		});
	}
	private void setData() {
		LoginUserBean loginUserBean = dbLoginManager.getLastLoginByUserType("1");//查询企业会员上次登录的数据
		if (loginUserBean != null) {//上次有登录的帐号
			System.out.println("查询企业会员上次登录的数据"+loginUserBean.toString());
			
			if (loginUserBean.getUsername() != null && !loginUserBean.getUsername().equals("null")) {
				System.out.println("存在用户名 - " + loginUserBean.getUsername());
				user_login_username_hint.setText(loginUserBean.getUsername());//用户名
			}else {
				System.out.println("不存在用户名 - ");
				user_login_username_hint.setText("");//用户名
			}
			
			if (loginUserBean.getIsremeber() != null && loginUserBean.getIsremeber().equals("1")) {//是记住密码
				user_checkbox1.setChecked(true);//密码
				remeberpassword_enterprise = true;
				System.out.println("是否记住密码"+loginUserBean.getIsremeber().equals("1"));
				user_login_password_hint.setText(loginUserBean.getPassword());
				
				if (loginUserBean.getIsloginself().equals("1")) {//是自动登录
					loginself_enterprise = true;
					user_checkbox2.setChecked(true); 
					System.out.println("是自动登录");
				}else {//不是自动登录
					user_checkbox2.setChecked(false); 
					loginself_enterprise = false;
					System.out.println("不是自动登录");
				}
				
			}else {
				user_checkbox1.setChecked(false);
				user_checkbox2.setChecked(false); 
				remeberpassword_enterprise = false;
				loginself_enterprise = false;
				user_login_password_hint.setText("");
				System.out.println("是否记住密码 false");
			}
			
		}else {//上次没有登录的帐号
			System.out.println("上次没有登录的帐号");
			user_login_username_hint.setText("");//用户名
			user_login_password_hint.setText("");//密码
			user_checkbox1.setChecked(false);
			user_checkbox2.setChecked(false); 
			remeberpassword_enterprise = false;
			loginself_enterprise = false;
		}
 
	}
	
	private void toUserControlCenter(AllBean user) {
		Intent intent = new Intent();
		intent.setClass(LoginEnterpriseActivity.this,
				UserControlEnterpriseActivity.class);
		startActivity(intent);
		Toast.makeText(LoginEnterpriseActivity.this, user.getMsg(),
				Toast.LENGTH_SHORT).show();
		finish();
	}

	private void saveLoginInfo() {
		// 保存登录数据
		LoginUserBean loginUserBean = new LoginUserBean();
		loginUserBean.setLastlogin("1");
		loginUserBean.setUsername(username_enterprise);
		loginUserBean.setPassword(password_enterprise);
		loginUserBean.setUsertype("1");
		if (remeberpassword_enterprise) {
			loginUserBean.setIsremeber("1");
		} else {
			loginUserBean.setIsremeber("0");
		}
		if (loginself_enterprise) {
			loginUserBean.setIsloginself("1");
		} else {
			loginUserBean.setIsloginself("0");
		}
		loginUserBean.setStatus("1");
		if (remeberpassword_enterprise && loginself_enterprise) {
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
		userInfoBean.setCenter(user.getInfo().getCenter());
		userInfoBean.setFax(user.getInfo().getFax());
		userInfoBean.setCategory(user.getInfo().getCategory());
		userInfoBean.setHeadurl(MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());
		userInfoBean.setEmail(user.getInfo().getEmail());
		userInfoBean.setUsertype("1");//企业会员
		userInfoBean.setUsername(username_enterprise);
		userInfoBean.setPassword(password_enterprise);
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
		user_checkbox1.setChecked(loginself_enterprise);
		user_checkbox2.setChecked(remeberpassword_enterprise);
	}
	
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(LoginEnterpriseActivity.this, ActivityIndex.class);
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
					MyUtlis.toastInfo(LoginEnterpriseActivity.this, "正在检测数据...");
					updateUserInfo();
				}
			};
			mTencent.login(this, "all", listener);
			Log.d("SDKQQAgentPref",
					"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
			mTencent.logout(this);
			MyUtlis.toastInfo(LoginEnterpriseActivity.this, "正在检测数据...");
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
						nickName ="大机汇(企业版)";
					}
					if (figureurl == null) {
						figureurl="http://dd";
					}
					MyUtlis.toastInfo(LoginEnterpriseActivity.this, "正在加载...");
					checkAPPID(nickName,figureurl);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	};
	private void checkAPPID(final String nickName ,final String headUrl){ 
		 System.out.println("是企业用户，开始检查用户是否在服务器上绑定过");
		userManager.Check(LoginEnterpriseActivity.this, MyConstants.EN_LOGIN_URL, mTencent.getOpenId());
		userManager.getQQCheck(new IgetQQCheck() {
			@Override
			public void getCheckInfo(AllBean user) {
				if (user != null) {
					if (user.getStatus().equals("true")) {
						System.out.println("企业用户已经绑定过");
						saveLoginInfo(user,"1");
				 		saveUserInfo(user, "1");
						Intent intent = new Intent();
						intent.setClass(
								LoginEnterpriseActivity.this,
								UserControlEnterpriseActivity.class);
						intent.putExtra("fromqq", true);
						intent.putExtra("binder", true);
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
							loginUserBean.setOpenid(mTencent.getOpenId());
							dbLoginManager.insertData(loginUserBean);
							
							UserInfoBean userInfoBean = new UserInfoBean();
							System.out.println("更新用户详细信息");
							userInfoBean.setStatus("1");
							userInfoBean.setNickname(nickName);
							userInfoBean.setHeadurl(headUrl);
							userInfoBean.setUsertype("1"); 
							userInfoBean.setUsername("qq");
							userInfoBean.setPassword("123");
							dbUserManager.insertData(userInfoBean);
							Intent intent = new Intent();
							intent.setClass(LoginEnterpriseActivity.this,
									UserControlEnterpriseActivity.class);
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
