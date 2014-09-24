package com.mzhou.merchant.activity;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchSingleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.OnDrawableChangeListener;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyConstants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class PicPagerActivity extends Activity {
	public static int RESULT = 4321;
	private static final String STATE_POSITION = "STATE_POSITION";
	private DisplayImageOptions options;
	private ImageLoader imageLoader  ;
	private ViewPager pager;
	private Matrix imageMatrix;
	private ImageViewTouch mImage;
	private boolean pub;
	private TextView currentpage;
	private ImageView backImageView;
	private ImageView delete;
	private RelativeLayout layout;
	private ImagePagerAdapter adapter;
	private String[] imageUrls;
	private List<String> delUrls;
	private int currentposition;
	private String currentUrls ="";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		 
		setContentView(R.layout.ac_image_pager);
		int pagerPosition = init();
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		delUrls = new ArrayList<String>();
		loadbutton();
		setdata(pagerPosition);
		listenerbutton();

	}

	/**
	 * 初始化
	 * 
	 * @return
	 */
	private int init() {
		imageLoader = ImageLoader.getInstance();
		Bundle bundle = getIntent().getExtras();
		imageUrls = bundle.getStringArray(Extra.IMAGES);
		int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);
		pub = bundle.getBoolean("pub", false);
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
		return pagerPosition;
	}

	/**
	 * 加载按钮
	 */
	private void loadbutton() {
		currentpage = (TextView) findViewById(R.id.title_bar_title);
		backImageView = (ImageView) findViewById(R.id.title_bar_showleft);
		delete = (ImageView) findViewById(R.id.title_bar_delete);
		layout = (RelativeLayout) findViewById(R.id.pager_title);
		pager = (ViewPager) findViewById(R.id.pager);

		
	}

	/**
	 * 设置pageadapter 和加载 pager页面
	 * 
	 * @param pagerPosition
	 */
	private void setdata(int pagerPosition) {
		if (imageUrls != null &&  imageUrls.length != 0) {

			for (int i = 0; i < imageUrls.length; i++) {
				if (imageUrls[i].startsWith("/mnt")) {
					imageUrls[i] = imageUrls[i].replace("/mnt", "file:/");
				}
				
			}
			adapter = new ImagePagerAdapter();
			pager.setAdapter(adapter);
			pager.setCurrentItem(pagerPosition);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int arg0) {
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					layout.setVisibility(View.GONE);
					if (pub) {
						delete.setVisibility(View.GONE);
					}

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {

				}
			});
		}
	}

	/**
	 * 监听事件
	 */
	private void listenerbutton() {
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(
						MyConstants.Extra.IMAGES,
						imageUrls);
				String[] delArray = new String[delUrls.size()];
				for (int i = 0; i < delUrls.size(); i++) {
					delArray[i] = delUrls.get(i);
				}
				intent.putExtra(
						MyConstants.Extra.DEL_IMAGES,
						delArray);
				setResult(RESULT, intent);
				finish();

			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout.setVisibility(View.GONE);
			String[]	deleteUrls = deleteBitmap(imageUrls, currentUrls);
			
			imageUrls = deleteUrls;
				if (deleteUrls.length != 0 ) {
					pager.removeAllViews();
					adapter = new ImagePagerAdapter();
					pager.setAdapter(adapter);
					pager.setCurrentItem(currentposition - 1);

				} else {
					adapter.notifyDataSetChanged();
					finish();
				}
			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra(
					MyConstants.Extra.IMAGES,
					imageUrls);
			String[] delArray = new String[delUrls.size()];
			for (int i = 0; i < delUrls.size(); i++) {
				delArray[i] = delUrls.get(i);
			}
			intent.putExtra(
					MyConstants.Extra.DEL_IMAGES,
					delArray);
			setResult(RESULT, intent);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	/**
	 * pager 适配器
	 * 
	 * @author user
	 * 
	 */
	private class ImagePagerAdapter extends PagerAdapter {

		private LayoutInflater inflater;

		ImagePagerAdapter() {
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, final int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image,
					view, false);
			mImage = (ImageViewTouch) imageLayout
					.findViewById(R.id.imagezoom_image);
			mImage.setDisplayType(DisplayType.FIT_IF_BIGGER);
			displayBitmap(imageUrls[position]);
			mImage.setSingleTapListener(new OnImageViewTouchSingleTapListener() {

				@Override
				public void onSingleTapConfirmed() {
					layout.setVisibility(View.VISIBLE);
					currentUrls = imageUrls[position];
					int po = position + 1;
					currentposition = po;
					currentpage.setText(po + File.separator + imageUrls.length);
					if (pub) {
						delete.setVisibility(View.VISIBLE);
					}

				}
			});
			mImage.setOnDrawableChangedListener(new OnDrawableChangeListener() {

				@Override
				public void onDrawableChanged(Drawable drawable) {
				}
			});
			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}

	/**
	 * 根据url 加载图片，然后设置图片matrix ,图片为 ImageViewTouch
	 * @param url
	 */
	private void displayBitmap(String url) {
		/**
		 * 加载图片监听
		 */
		imageLoader.displayImage(url, mImage, options);
		if (null == imageMatrix) {
			imageMatrix = new Matrix();
		} else {
			imageMatrix = mImage.getDisplayMatrix();
		}
		mImage.setImageMatrix(imageMatrix.isIdentity() ? null : imageMatrix);
	}

	/**
	 * 在数组里面存储着所有的图片地址，根据position 位置来删除图片数组中的图片地址
	 * 
	 * @param images
	 * @param position
	 * @return
	 */
	private String[] deleteBitmap(String[] images, String position) {
		List<String> list  = new ArrayList<String>();
		for (int i = 0; i < images.length; i++) {
			if (!position.equals(images[i])) {
				list.add(images[i]);
			}else {
				if (images[i].contains("http")) {
					delUrls.add(images[i]);
				}
			}
		}
		String[] newStr = list.toArray(new String[list.size()]); // 返回一个包含所有对象的指定类型的数组
		return newStr;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}