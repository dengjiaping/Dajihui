package com.mzhou.merchant.activity;

import java.util.HashMap;
import java.util.Map;

import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
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
	private boolean remeberpassword_enterprise = false;
	private boolean loginself_enterprise = false;

	
	private DbLoginManager dbLoginManager;
	private DbUserManager dbUserManager;

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
		sp = getSharedPreferences("phonemerchant", 1);
		userManager = new UserManager();
//		remeberpassword_enterprise = sp.getBoolean(
//				"remeberpassword_enterprise", false);
//		loginself_enterprise = sp.getBoolean("loginself_enterprise", false);
		dbLoginManager = DbLoginManager.getInstance(this);
		dbUserManager = DbUserManager.getInstance(this);
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
											
//											save2SharedPrefenrence(user);
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
				Intent intent = new Intent();
				intent.setClass(LoginEnterpriseActivity.this,
						ActivityBinderQQ.class);
				intent.putExtra("common", false);
				startActivity(intent);finish();
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
			
			if (loginUserBean.getIsremeber().equals("1")) {//是记住密码
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
				System.out.println("是否记住密码"+loginUserBean.getIsremeber().equals("1"));
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
		
		/*
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
*/
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
