package com.mzhou.merchant.activity;

import com.mzhou.merchant.dao.IUser.IgetUserInfo;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RegisterCommonActivity extends Activity {
	private UserManager userManager = null;
	private Button button;
	private ImageView title_bar_showleft;

	private EditText user_register_count_hint;
	private EditText user_register_passwd_hint;
	private EditText user_register_passwd_confirm_hint;
	private TextView reg_pro_com;
	
	private EditText user_register_passwd_an;
	private CheckBox cb_agree;
	private boolean selcect = true;
	private TextView tv_agree;
	private SharedPreferences sPreferences;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_register_common);
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
		reg_pro_com = (TextView) findViewById(R.id.reg_pro_com);
		user_register_passwd_an = (EditText) findViewById(R.id.user_register_passwd_an);
		user_register_passwd_hint = (EditText) findViewById(R.id.user_register_passwd_hint);
		user_register_passwd_confirm_hint = (EditText) findViewById(R.id.user_register_passwd_confirm_hint);
	}

	private void listernerButton() {
		cb_agree.setSelected(selcect);
		tv_agree.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(RegisterCommonActivity.this, ActivityUserAgree.class);
				startActivity(intent);
			}
		});
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		cb_agree .setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				selcect = isChecked;

			}
		});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WebIsConnectUtil.showNetState(RegisterCommonActivity.this)) {
					if (selcect) {
						if (getEditText(user_register_passwd_confirm_hint).equals(
								getEditText(user_register_passwd_hint))) {
							final String username = getEditText(user_register_count_hint);
							final String password = getEditText(user_register_passwd_confirm_hint);
							final String pw_pro = reg_pro_com.getText().toString();
							final String pw_pro_an = getEditText(user_register_passwd_an);
							userManager.Register(RegisterCommonActivity.this,
									MyConstants.LOGIN_URL, username, password,
									username, "Àà±ð", pw_pro, pw_pro_an);
							userManager.getUserInfoIml(new IgetUserInfo() {

								@Override
								public void getInfo(AllBean user) {
									if (user != null) {
										if (user.getStatus().equals("true")) {
											Editor editor = sPreferences.edit();
											editor.putString("username", username);
											editor.putString("password", password);
											editor.commit();
											MyUtlis.toastInfo(
													RegisterCommonActivity.this,
													user.getMsg()
															+ getResources()
																	.getString(
																			R.string.to_login));

											Intent intent = new Intent();
											intent.setClass(
													RegisterCommonActivity.this,
													ActivityLogin.class);
											startActivity(intent);
											finish();
										} else {
											MyUtlis.toastInfo(
													RegisterCommonActivity.this,
													user.getMsg());
											return;
										}
									}
								}

							});
						} else {
							MyUtlis.toastInfo(
									RegisterCommonActivity.this,
									getResources().getString(
											R.string.user_register_error));

							return;
						}
					
						
					}else {
						MyUtlis.toastInfo(
								RegisterCommonActivity.this,
								getResources().getString(
										R.string.user_register_no_agree));
					}
				}
			}
		});

	}

	private String getEditText(EditText editText) {
		return editText.getText().toString();
	}
}
