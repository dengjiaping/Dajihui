package com.mzhou.merchant.activity;

import java.util.HashMap;
import java.util.Map;

import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
	public static SharedPreferences sp;
	private boolean remeberpassword_enterprise;
	private boolean loginself_enterprise;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login_enterprise);
		init();
		loadButton();
		listenerButton();
	}

	private void init() {
		sp = getSharedPreferences("phonemerchant", 1);
		userManager = new UserManager();
		remeberpassword_enterprise = sp.getBoolean(
				"remeberpassword_enterprise", false);
		loginself_enterprise = sp.getBoolean("loginself_enterprise", false);
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
											save2SharedPrefenrence(user);
											Intent intent = new Intent();
											intent.setClass(
													LoginEnterpriseActivity.this,
													UserControlEnterpriseActivity.class);
											startActivity(intent);
											Toast.makeText(
													LoginEnterpriseActivity.this,
													user.getMsg(),
													Toast.LENGTH_SHORT).show();
											finish();
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
					}
				});
		user_checkbox2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						loginself_enterprise = isChecked;

					}
				});
		qq_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LoginEnterpriseActivity.this,
						ActivityBinderQQ.class);
				intent.putExtra("common", false);
				startActivity(intent);finish();
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
		editor.putString("name_enterprise", user.getInfo().getContact());// 联系人
		editor.putBoolean("loginself_enterprise", loginself_enterprise);// 是否自动登陆
		editor.putBoolean("remeberpassword_enterprise",
				remeberpassword_enterprise);// 是否记住密码
		editor.putBoolean("isLogin_enterprise", true);// 是否登陆
		editor.putBoolean("isEnterprise", true);// 设置是企业会员
		editor.putString("uid_enterprise", user.getUid());// 会员id
		editor.putString("nickname_enterprise", user.getInfo().getNickname());// 昵称
		editor.putString("username_enterprise", user.getInfo().getUsername());// 账号
		editor.putString("password_enterprise", password_enterprise);// 密码
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
		user_login_username_hint.setText(sp
				.getString("username_enterprise", ""));

		if (remeberpassword_enterprise) {
			user_checkbox1.setChecked(remeberpassword_enterprise);
			user_login_password_hint.setText(sp.getString(
					"password_enterprise", ""));
		} else {
			user_checkbox1.setChecked(remeberpassword_enterprise);
			user_login_password_hint.setText("");
		}
		if (loginself_enterprise) {
			user_checkbox2.setChecked(loginself_enterprise);
		} else {
			user_checkbox2.setChecked(loginself_enterprise);
		}

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
}
