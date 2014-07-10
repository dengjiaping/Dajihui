package com.mzhou.merchant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class ActivityUserAgree extends Activity {

	private ImageView title_bar_showleft;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_agree);
		loadButton();

		clickButton();
	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);

	}

	private void clickButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

}
