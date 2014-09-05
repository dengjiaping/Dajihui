package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.mzhou.merchant.utlis.MyConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UserControlAleterPasswdActivity extends Activity {
	private ImageView title_bar_showleft;
	private EditText user_manager_alter_passwd2;
	private EditText user_manager_alter_passwd3;
	private EditText user_manager_alter_passwd4;
	private Button publish;
	private String instruction1;
	private String instruction2;
	private String instruction3;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	   
		setContentView(R.layout.user_manager_alter_passwd);
		String string = init();
		loadButton(string);
		listenerButton();
	}

	private void listenerButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����ť֮�󱣴�����
				if (user_manager_alter_passwd2
						.getText()
						.toString()
						.equals(user_manager_alter_passwd3.getText().toString())) {
					Toast.makeText(UserControlAleterPasswdActivity.this,
							instruction1, Toast.LENGTH_LONG).show();
				} else {
					if (user_manager_alter_passwd3
							.getText()
							.toString()
							.equals(user_manager_alter_passwd4.getText()
									.toString())) {
						Intent intent = new Intent();
						intent.putExtra("password", user_manager_alter_passwd4
								.getText().toString());
						setResult(MyConstants.REQUEST_PASSWORD, intent);
						finish();
						Toast.makeText(UserControlAleterPasswdActivity.this,
								instruction3, Toast.LENGTH_LONG).show();

					} else {
						Toast.makeText(UserControlAleterPasswdActivity.this,
								instruction2, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

	private void loadButton(String string) {
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		user_manager_alter_passwd2 = (EditText) findViewById(R.id.user_manager_alter_passwd2);
		user_manager_alter_passwd3 = (EditText) findViewById(R.id.user_manager_alter_passwd3);
		user_manager_alter_passwd4 = (EditText) findViewById(R.id.user_manager_alter_passwd4);
		instruction1 = getResources().getString(
				R.string.user_manager_alter_instruction1);
		instruction2 = getResources().getString(
				R.string.user_manager_alter_instruction2);
		instruction3 = getResources().getString(
				R.string.user_manager_alter_instruction3);
		user_manager_alter_passwd2.setText(string);
	}

	private String init() {
		Intent intent = getIntent();
		String string = intent.getStringExtra("password");
		return string;
	}

}
