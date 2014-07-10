package com.mzhou.merchant.activity;

import com.mzhou.merchant.utlis.MyConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserControlFaxActivity extends Activity {
	private ImageView title_bar_showleft;
	private EditText editText;
	private Button publish;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_manager_fax);
		Intent intent = getIntent();
		String string = intent.getStringExtra("fax");
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		editText = (EditText) findViewById(R.id.input);
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
				Intent intent = new Intent();
				intent.putExtra("fax", editText.getText().toString());
				setResult(MyConstants.REQUEST_FAX, intent);
				finish();
			}
		});
	}

}
