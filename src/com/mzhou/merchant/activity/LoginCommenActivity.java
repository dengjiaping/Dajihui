package com.mzhou.merchant.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

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

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login_common);
		init();
		loadButton();
		setData();
		listenerButton();
	}

	private void init() {
		dbLoginManager = DbLoginManager.getInstance(this);
		dbUserManager = DbUserManager.getInstance(this);
		
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
						if (isChecked) {//选中记住密码
							
						}else {//取消记住密码
							user_checkbox2.setChecked(false);
							loginself= false;
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
				 Intent intent = new Intent();
				intent.setClass(LoginCommenActivity.this,
						ActivityBinderQQ.class);
				intent.putExtra("common", true);
				startActivity(intent);
				finish() ;
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
		 
			if (loginUserBean.getIsremeber() != null && loginUserBean.getIsremeber().equals("1")) {// 是记住密码
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
	 
}
