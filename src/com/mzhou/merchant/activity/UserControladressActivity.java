package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

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

import com.mzhou.merchant.utlis.MyConstants;

public class UserControladressActivity extends Activity {
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
		setContentView(R.layout.user_manager_address);
		Intent intent = getIntent();
		String string = intent.getStringExtra("address");
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		editText = (EditText) findViewById(R.id.inputs);
		editText.setText(string);
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editText.getText().toString() != null) {
					Intent intent = new Intent();
					intent.putExtra("address", editText.getText().toString());
					setResult(MyConstants.REQUEST_ADDRESS, intent);
					finish();
				}
			}
		});
	}

}
