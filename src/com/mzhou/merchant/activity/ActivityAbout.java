package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAbout extends Activity {

	private ImageView title_bar_showleft;
	private ImageView about_image;
	private TextView aboutus_string1;
	private TextView aboutus_string2;
	private TextView aboutus_string3;
	private TextView aboutus_string4;
	private TextView title_string;
	private TextView title_bar_title;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_guanyu);
		loadButton();
		setData();
		clickButton();
	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		about_image = (ImageView) findViewById(R.id.about_image);
		title_string = (TextView) findViewById(R.id.about_tiltle);
		aboutus_string1 = (TextView) findViewById(R.id.aboutus_string1);
		aboutus_string2 = (TextView) findViewById(R.id.aboutus_string2);
		aboutus_string3 = (TextView) findViewById(R.id.aboutus_string3);
		aboutus_string4 = (TextView) findViewById(R.id.aboutus_string4);
		title_bar_title = (TextView) findViewById(R.id.title_bar_title);
	}

	private void setData() {
		about_image.setImageResource(R.drawable.tubiao);
		title_bar_title.setText(getResources().getString(
				R.string.title_bar_about));
		title_string.setText(getResources().getString(R.string.title_string));
		aboutus_string1.setText(getResources().getString(
				R.string.aboutus_string1));
		aboutus_string2.setText(getResources().getString(
				R.string.aboutus_string2));
		aboutus_string4.setText(getResources().getString(
				R.string.contact_string));
		aboutus_string3.setText(setKeyColor(
				getResources().getString(R.string.change_string),
				getResources().getString(R.string.aboutus_string3), "blue"));
	}

	private void clickButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private Spanned setKeyColor(String key, String s, String color) {

		if (s == null)
			return null;
		if (key == null)
			return Html.fromHtml(s);
		String changeLine = s.replace("\t\t", "\n\t\t");
		String colorKey = "<font color=\"" + color + "\">" + key + "</font>";

		return Html.fromHtml(changeLine.replace(key, colorKey));
	}
}
