package com.mzhou.merchant.activity;

import com.mzhou.merchant.dao.IUser.IgetUserInfo;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RegisterEnterpriseActivity extends Activity {
	private UserManager userManager = null;
	private Button button;
	private ImageView title_bar_showleft;
	private TextView reg_pro_en;
	private EditText user_register_passwd_pro_an;
	private EditText user_register_count_hint;
	private EditText user_register_passwd_hint;
	private EditText user_register_passwd_confirm_hint;
	private SharedPreferences sPreferences;
	private CheckBox cb_agree;
	private boolean selcect = true;
	private TextView tv_agree;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_register_enterprise);
		sPreferences = getSharedPreferences("phonemerchant", 1);
		userManager = new UserManager();
		loadButton();
		listernerButton();
	}

	private void loadButton() {
		cb_agree = (CheckBox) findViewById(R.id.cb_agree);
		tv_agree = (TextView) findViewById(R.id.tv_agree);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		button = (Button) findViewById(R.id.user_register_ok);
		user_register_count_hint = (EditText) findViewById(R.id.user_register_count_hint);
		reg_pro_en = (TextView) findViewById(R.id.reg_pro_en);
		user_register_passwd_pro_an = (EditText) findViewById(R.id.user_register_passwd_an);
		user_register_passwd_hint = (EditText) findViewById(R.id.user_register_passwd_hint);
		user_register_passwd_confirm_hint = (EditText) findViewById(R.id.user_register_passwd_confirm_hint);

	}

	private void listernerButton() {
		tv_agree.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(RegisterEnterpriseActivity.this, ActivityUserAgree.class);
				startActivity(intent);
				
			}
		});
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				 Intent intent = new Intent();
					intent.setClass(RegisterEnterpriseActivity.this, ActivityLogin.class);
					intent.putExtra("isEnterprise", true);
					startActivity(intent);
					finish(); 
			
			 
			}
		});
		cb_agree .setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					selcect = true;
				}else {
					selcect = false;
				}

			}
		});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user_register_count_hint.getText().toString().trim().equals("")) {
					MyUtlis.toastInfo(RegisterEnterpriseActivity.this, getString(R.string.user_register_username_is_null));
					return ;
				}
				if (user_register_passwd_hint.getText().toString().trim().equals("")) {
					MyUtlis.toastInfo(RegisterEnterpriseActivity.this, getString(R.string.user_register_password_is_null));
					return ;
				}
				if (!selcect) {
					MyUtlis.toastInfo(RegisterEnterpriseActivity.this, getString(R.string.user_register_no_agree));
					return;
				}
				
				if (WebIsConnectUtil
						.showNetState(RegisterEnterpriseActivity.this)) {if (selcect) {


							if (getEditText(user_register_passwd_confirm_hint).equals(
									getEditText(user_register_passwd_hint))) {
								final String username = getEditText(user_register_count_hint);
								final String password = getEditText(user_register_passwd_confirm_hint);
								final String pw_pro = reg_pro_en.getText().toString();
								final String pw_pro_an = getEditText(user_register_passwd_pro_an);
								userManager.Register(RegisterEnterpriseActivity.this,
										MyConstants.EN_LOGIN_URL, username, password,
										username, "", pw_pro, pw_pro_an);
								userManager.getUserInfoIml(new IgetUserInfo() {
									@Override
									public void getInfo(AllBean user) {
										if (user != null) {
											if (user.getStatus().equals("true")) {
												
												LoginUserBean loginUserBean = new LoginUserBean();
												loginUserBean.setUsername(username);
												loginUserBean.setPassword(password);
												loginUserBean.setUsertype("1");
												loginUserBean.setStatus("0");
												loginUserBean.setLastlogin("1");
												DbLoginManager.getInstance(RegisterEnterpriseActivity.this).insertData(loginUserBean);
												System.out.println("保存登录信息");
												UserInfoBean userInfoBean = new UserInfoBean();
												userInfoBean.setUsername(username);
												userInfoBean.setPassword(password);
												userInfoBean.setStatus("0");
												userInfoBean.setUsertype("1");
												DbUserManager.getInstance(RegisterEnterpriseActivity.this).insertData(userInfoBean);
												System.out.println("保存用户信息");
												
//												
//												Editor editor = sPreferences.edit();
//												editor.putString("username_enterprise",
//														username);
//												editor.putString("password_enterprise",
//														password);
//												editor.commit();

												Toast.makeText(
														RegisterEnterpriseActivity.this,
														user.getMsg(),
														Toast.LENGTH_SHORT).show();
												Intent intent = new Intent();
												intent.setClass(
														RegisterEnterpriseActivity.this,
														ActivityLogin.class);
												intent.putExtra("isEnterprise", true);
												startActivity(intent);
												finish();
											} else {
												Toast.makeText(
														RegisterEnterpriseActivity.this,
														user.getMsg(),
														Toast.LENGTH_SHORT).show();
												return;
											}
										}
									}

								});
							} else {
								Toast.makeText(
										RegisterEnterpriseActivity.this,
										getResources().getString(
												R.string.user_register_error),
										Toast.LENGTH_SHORT).show();
								return;
							}
						
						}else {
							MyUtlis.toastInfo(
									RegisterEnterpriseActivity.this,
									getResources().getString(
											R.string.user_register_no_agree));
						}}
			}
		});

	}

	private String getEditText(EditText editText) {
		return editText.getText().toString();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			 Intent intent = new Intent();
				intent.setClass(RegisterEnterpriseActivity.this, ActivityLogin.class);
				intent.putExtra("isEnterprise", true);
				startActivity(intent);
				finish(); 
		}
		return true;
	}
}
