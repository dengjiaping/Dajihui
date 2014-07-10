package com.mzhou.merchant.activity;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchSingleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.OnDrawableChangeListener;

import java.io.File;
import java.util.ArrayList;
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
	private int currentposition;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ac_image_pager);
		int pagerPosition = init();
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		loadbutton();
		setdata(pagerPosition);
		listenerbutton();

	}

	/**
	 * ��ʼ��
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
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_stub)
		.showImageOnFail(R.drawable.ic_stub).cacheInMemory()
		.cacheOnDisc().delayBeforeLoading(0)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
		return pagerPosition;
	}

	/**
	 * ���ذ�ť
	 */
	private void loadbutton() {
		currentpage = (TextView) findViewById(R.id.title_bar_title);
		backImageView = (ImageView) findViewById(R.id.title_bar_showleft);
		delete = (ImageView) findViewById(R.id.title_bar_delete);
		layout = (RelativeLayout) findViewById(R.id.pager_title);
		pager = (ViewPager) findViewById(R.id.pager);

		
	}

	/**
	 * ����pageadapter �ͼ��� pagerҳ��
	 * 
	 * @param pagerPosition
	 */
	private void setdata(int pagerPosition) {
		if (imageUrls != null && !imageUrls.equals("")) {

			for (int i = 0; i < imageUrls.length; i++) {
				imageUrls[i] = imageUrls[i].replace("/mnt", "file:/");
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
	 * �����¼�
	 */
	private void listenerbutton() {
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(
						MyConstants.Extra.IMAGES,
						imageUrls);
				setResult(RESULT, intent);
				finish();

			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout.setVisibility(View.GONE);
			String[]	deleteUrls = deleteBitmap(imageUrls, currentposition);
			imageUrls = deleteUrls;
				if (deleteUrls.length != 0 && !deleteUrls.equals("[]")) {
					pager.removeAllViews();
					adapter.notifyDataSetChanged();
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
			setResult(RESULT, intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		imageLoader.clearMemoryCache();
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	/**
	 * pager ������
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
	 * ����url ����ͼƬ��Ȼ������ͼƬmatrix ,ͼƬΪ ImageViewTouch
	 * @param url
	 */
	private void displayBitmap(String url) {
		/**
		 * ����ͼƬ����
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
	 * ����������洢�����е�ͼƬ��ַ������position λ����ɾ��ͼƬ�����е�ͼƬ��ַ
	 * 
	 * @param images
	 * @param position
	 * @return
	 */
	private String[] deleteBitmap(String[] images, int position) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < images.length; i++) {
			list.add(images[i]);
		}
		list.remove(position - 1);
		String[] newStr = list.toArray(new String[list.size()]); // ����һ���������ж����ָ�����͵�����
		return newStr;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

}