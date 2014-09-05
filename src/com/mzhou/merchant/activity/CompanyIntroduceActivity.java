package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzhou.merchant.dao.IProduct.IGetCompanyInfo;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.model.AboutCompany;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class CompanyIntroduceActivity extends Activity {

	private ImageView title_bar_showleft;
	private ImageView about_image;
	private TextView aboutus_string1;
	private TextView aboutus_string2;
	private TextView aboutus_string3;
	private TextView aboutus_string4;
	private TextView title_string;
	private TextView title_bar_title;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	private Context context;
	private String brand;
	private ProductsManager productsManager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_guanyu);
		init();
		loadButton();
		getData();

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void getData() {
		if (WebIsConnectUtil.showNetState(context)) {
			productsManager.AsynGetCompany(context, brand);
			productsManager.getCompanyInfoIml(new IGetCompanyInfo() {
				@Override
				public void getCompanyInfo(AboutCompany aboutCompany) {
					if (aboutCompany != null) {
						if (aboutCompany.getCocontent() != null) {

							title_string.setText(aboutCompany.getCotitle());
							aboutus_string1.setText(aboutCompany.getCocontent());
							imageLoader.displayImage(MyConstants.PICTURE_URL
									+ aboutCompany.getCopic(), about_image,
									options);
							aboutus_string2.setText("");
							aboutus_string4.setText("");
							aboutus_string3.setText("");
						} else {

							title_string.setText(getResources().getString(
									R.string.about_company_no_intro));
							aboutus_string1.setText(getResources().getString(
									R.string.contact_string));
							imageLoader.displayImage("drawable://"
									+ R.drawable.ic_launcher, about_image,
									options);
						}
					} else {
						title_string.setText(getResources().getString(
								R.string.about_company_no_intro));
						aboutus_string1.setText(getResources().getString(
								R.string.contact_string));
						imageLoader.displayImage("drawable://"
								+ R.drawable.ic_launcher, about_image, options);
					}

				}
			});

		}

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
		title_bar_title.setText(getResources().getString(
				R.string.introduce_company));
	}

	private void init() {
		context = CompanyIntroduceActivity.this;
		productsManager = new ProductsManager();
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ad_loading)
		.showImageForEmptyUri(R.drawable.ad_loading)
		.showImageOnFail(R.drawable.ad_loading)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		 
		Intent intent = getIntent();
		brand = intent.getStringExtra("brand");
		Log.i("print", brand);
		// classid = "51";
	}

}
