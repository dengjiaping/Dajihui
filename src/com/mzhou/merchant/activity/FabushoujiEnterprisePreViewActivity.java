package com.mzhou.merchant.activity;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;



import com.mzhou.merchant.model.ProductsEnterpriseByIdBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 注释部分是：上传视频功能
 * @author user
 *
 */
@SuppressWarnings("deprecation")
public class FabushoujiEnterprisePreViewActivity extends Activity {

	private Gallery gallery;
	private ImageAdapter imageAdapter;

	private ImageItemAdapter imageitemAdapter;
	private Context context;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	private ImageView title_bar_showleft;
//	private ImageView video_pic;
	private TextView pub_product_pinpai;
	private TextView pub_product_xinghao;
	private TextView pub_product_xinpian;
	private TextView pub_product_chicun;
	private TextView pub_product_fenbianlv;
	private TextView pub_product_xitong;
	private TextView pub_product_qianxiangsu;
	private TextView pub_product_houxiangsu;
	private TextView display_parameters_instruction;
	private TextView about_company;
	private TextView contact_company;
	private TextView rom;
	private TextView ah;
	private String[] array;
	private String[] array1;
	private ProductsEnterpriseByIdBean p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		setContentView(R.layout.fabu_shouji_preview_enterprise);
		init();
		loadButton();
		listenerClick();
		getdata();
	}

	private void init() {
		context = getBaseContext();
		Bundle bundle = getIntent().getExtras();
		array1 = bundle.getStringArray("urls");
		array = new String[array1.length];

		p = (ProductsEnterpriseByIdBean) bundle.getSerializable("productinfo");
		 
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_stub)
		.showImageOnFail(R.drawable.ic_stub)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		imageLoader = ImageLoader.getInstance();
		imageAdapter = new ImageAdapter();
		imageitemAdapter = new ImageItemAdapter();
		LinkedList<String> linkedList = new LinkedList<String>();

		for (int i = 0; i < array1.length; i++) {
			linkedList.addLast(array1[i].replace("/mnt", "file:/"));
		}
		String string = "drawable://" + R.drawable.roominfo_add_btn_normal;
		linkedList.remove(string);
		Log.i("print", linkedList.toString());
		array = (String[]) linkedList.toArray(new String[0]);

	}

	private void listenerClick() {
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(context, PicPagerActivity.class);
				imageitemAdapter.notifyDataSetChanged();
				intent.putExtra(
						MyConstants.Extra.IMAGES,
						array);
				intent.putExtra(
						MyConstants.Extra.IMAGE_POSITION,
						arg2);
				startActivity(intent);

			}
		});

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		about_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(context, ActivityAbout.class);
				startActivity(intent);

			}
		});
		contact_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent = new Intent();
				intent.setClass(context, CompanyContactUsActivity.class);
				intent.putExtra("productinfo", p);
				startActivity(intent); 
			}
		});
//		video_pic.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(FabushoujiEnterprisePreViewActivity.this, ActivityVideoPlayer.class);
//				intent.putExtra("path", p.getVideopic());
//				startActivity(intent);
//			}
//		});
		
	}

	private void loadButton() {
		gallery = (Gallery) findViewById(R.id.xianshi_shouji_gallery);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		pub_product_pinpai = (TextView) findViewById(R.id.pub_product_pinpai);
		pub_product_xinghao = (TextView) findViewById(R.id.pub_product_xinghao);
		pub_product_xinpian = (TextView) findViewById(R.id.pub_product_xinpian);
		pub_product_chicun = (TextView) findViewById(R.id.pub_product_chicun);
		pub_product_fenbianlv = (TextView) findViewById(R.id.pub_product_fenbianlv);
		pub_product_xitong = (TextView) findViewById(R.id.pub_product_xitong);
		pub_product_qianxiangsu = (TextView) findViewById(R.id.pub_product_qianxiangsu);
		pub_product_houxiangsu = (TextView) findViewById(R.id.pub_product_houxiangsu);
		display_parameters_instruction = (TextView) findViewById(R.id.display_parameters_instruction);
		about_company = (TextView) findViewById(R.id.about_company);
		contact_company = (TextView) findViewById(R.id.contact_company);
		ah = (TextView) findViewById(R.id.pub_product_dianchirongliang);
		rom = (TextView) findViewById(R.id.pub_product_jishenneicun);
//		video_pic = (ImageView) findViewById(R.id.video_pic);

	}

	private void getdata() {
		pub_product_xinpian.setText(p.getChip());
		pub_product_chicun.setText(p.getSize());
		pub_product_fenbianlv.setText(p.getDistinguish());
		pub_product_xitong.setText(p.getSystem());
		pub_product_qianxiangsu.setText(p.getPrec_pixel());
		pub_product_houxiangsu.setText(p.getNext_pixel());
		display_parameters_instruction.setText(p.getContent());
		pub_product_pinpai.setText(p.getBrand());
		pub_product_xinghao.setText(p.getType());
		rom.setText(p.getRom());
		ah.setText(p.getAh());
		if (array.length > 0) {
			gallery.setAdapter(imageAdapter);
			if (array.length > 1) {
				gallery.setSelection(1);
			}

		}
//		if (p.getVideopic() != null) {
//			Bitmap bitmap = 	ThumbnailUtils.createVideoThumbnail(p.getVideopic(), Thumbnails.MICRO_KIND);
//			video_pic.setImageBitmap(bitmap);
//			video_pic.setVisibility(View.VISIBLE);
//		}
		
	}

	public class ImageAdapter extends BaseAdapter {

		public ImageAdapter() {
		}

		@Override
		public int getCount() {
			return array.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(
						R.layout.item_gallery_image, parent, false);
			}
			imageView.setAdjustViewBounds(true);
			imageLoader.displayImage(array[position], imageView, options);
			return imageView;

		}
	}

	public class ImageItemAdapter extends BaseAdapter {

		public ImageItemAdapter() {
		}

		@Override
		public int getCount() {
			return array.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView t = new TextView(context);
			t.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			t.setText(position + File.separator + array.length);
			t.setTextColor(Color.BLACK);
			return t;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			imageLoader.clearMemoryCache();
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
