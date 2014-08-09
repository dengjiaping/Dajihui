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

public class UserControlNameActivity extends Activity {
	private ImageView title_bar_showleft;
	private EditText editText;
	private Button publish;

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
		setContentView(R.layout.user_manager_name);
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
				Intent intent = new Intent();
				intent.putExtra("name", editText.getText().toString());
				setResult(MyConstants.REQUEST_NAME, intent);
				finish();
			}
		});
	}

	private void loadButton(String string) {
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		editText = (EditText) findViewById(R.id.input);
		editText.setText(string);
	}

	private String init() {
		Intent intent = getIntent();
		String string = intent.getStringExtra("name");
		return string;
	}

}
