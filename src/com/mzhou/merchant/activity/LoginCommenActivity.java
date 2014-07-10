package com.mzhou.merchant.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import com.mzhou.merchant.model.AllBean;
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
	public static SharedPreferences sp;
	private boolean remeberpassword;
	private boolean loginself;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login_common);
		init();
		loadButton();
		listenerButton();
	}

	private void init() {
		sp = getSharedPreferences("phonemerchant", 1);
		userManager = new UserManager();
		remeberpassword = sp.getBoolean("remeberpassword", false);
		loginself = sp.getBoolean("loginself", false);
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
							MyConstants.LOGIN_URL,map);// 登陆
					userManager
							.getUserInfoIml(new com.mzhou.merchant.dao.IUser.IgetUserInfo() {

								@Override
								public void getInfo(AllBean user) {
									if (user != null) {
										if (user.getStatus().equals("true")) {
											save2SharedPrefenrence(user);
											Intent intent = new Intent();
											intent.setClass(
													LoginCommenActivity.this,
													UserControlCommonActivity.class);
											startActivity(intent);
											Toast.makeText(
													LoginCommenActivity.this,
													user.getMsg(),
													Toast.LENGTH_SHORT).show();
											finish();
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
					}
				});
		user_checkbox2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						loginself = isChecked;

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
				finish();
			}
		});
	}
 
	/**
	 * 将用户信息储存到SharedPreference里面去
	 * 
	 * @param user
	 */
	private void save2SharedPrefenrence(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name", user.getInfo().getContact());// 联系人
		editor.putBoolean("loginself", loginself);
		editor.putBoolean("remeberpassword", remeberpassword);
		editor.putBoolean("isLogin", true);
		editor.putBoolean("isEnterprise", false);// 设置不是企业会员
		editor.putString("uid", user.getUid());
		editor.putString("nickname", user.getInfo().getNickname());// 昵称
		editor.putString("username", user.getInfo().getUsername());// 账号
		editor.putString("password", password);// 密码
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
		user_checkbox1.setChecked(loginself);
		user_checkbox2.setChecked(remeberpassword);
		user_login_username_hint.setText(sp.getString("username", ""));
		if (remeberpassword) {
			user_checkbox1.setChecked(remeberpassword);
			user_login_password_hint.setText(sp.getString("password", ""));
		} else {
			user_checkbox1.setChecked(remeberpassword);
			user_login_password_hint.setText("");
		}
		if (loginself) {
			user_checkbox2.setChecked(loginself);
		} else {
			user_checkbox2.setChecked(loginself);
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
