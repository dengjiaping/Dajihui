package com.mzhou.merchant.activity;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

@SuppressWarnings("deprecation")
public class FabushoujiCommenPreViewActivity extends Activity {

	private Gallery gallery;
	private ImageAdapter imageAdapter;

	private ImageItemAdapter imageitemAdapter;
	private Button imageView_call;
	private Context context;
	private String number;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	private ImageView title_bar_showleft;
	private TextView pub_product_pinpai;
	private TextView pub_product_xinghao;
	private TextView pub_product_xinpian;
	private TextView pub_product_chicun;
	private TextView pub_product_fenbianlv;
	private TextView pub_product_xitong;
	private TextView pub_product_qianxiangsu;
	private TextView pub_product_houxiangsu;
	private TextView display_parameters_instruction;
	private TextView user_manager_name;
	private TextView user_manager_qq;
	private TextView user_manager_phonnumber;
	private TextView user_manager_company;
	private TextView user_manager_address;
	private TextView user_manager_net;
	private TextView pub_product_jishenneicun;
	private TextView pub_product_dianchirongliang;

	private String[] array;
	private String[] array1;
	private ProductsByIdBean p;

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
		setContentView(R.layout.fabu_shouji_preview);
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

		p = (ProductsByIdBean) bundle.getSerializable("productinfo");
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
		 
		imageLoader = ImageLoader.getInstance();
		imageAdapter = new ImageAdapter();
		imageitemAdapter = new ImageItemAdapter();
		LinkedList<String> linkedList = new LinkedList<String>();

		for (int i = 0; i < array1.length; i++) {
			linkedList.addLast(array1[i].replace("/mnt", "file:/"));
			Log.i("print", array1[i]);
		}
		String string = "drawable://" + R.drawable.roominfo_add_btn_normal;
		linkedList.remove(string);
		array = (String[]) linkedList.toArray(new String[0]);

	}

 
	private void listenerClick() {
		imageView_call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.DIAL");
				intent.setData(Uri.parse("tel:" + number));
				startActivity(intent);
			}
		});
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
				imageLoader.clearMemoryCache();
				finish();
			}
		});

	}
 
	private void loadButton() {
		gallery = (Gallery) findViewById(R.id.xianshi_shouji_gallery);
		imageView_call = (Button) findViewById(R.id.xianshi_shouji_call);
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
		user_manager_name = (TextView) findViewById(R.id.user_manager_name);
		user_manager_qq = (TextView) findViewById(R.id.user_manager_qq);
		user_manager_phonnumber = (TextView) findViewById(R.id.user_manager_phonnumber);
		user_manager_company = (TextView) findViewById(R.id.user_manager_company);
		user_manager_address = (TextView) findViewById(R.id.user_manager_address);
		user_manager_net = (TextView) findViewById(R.id.user_manager_net);
		pub_product_jishenneicun = (TextView) findViewById(R.id.pub_product_jishenneicun);
		pub_product_dianchirongliang = (TextView) findViewById(R.id.pub_product_dianchirongliang);

	}

	private void getdata() {

		user_manager_name.setText(p.getContact());
		user_manager_qq.setText(p.getQq());
		user_manager_phonnumber.setText(p.getPhone());
		number = p.getPhone();
		user_manager_company.setText(p.getCompany());
		user_manager_address.setText(p.getAddress());
		user_manager_net.setText(p.getNet());
		pub_product_dianchirongliang.setText(p.getAh());
		pub_product_jishenneicun.setText(p.getRom());
		pub_product_xinpian.setText(p.getChip());
		pub_product_chicun.setText(p.getSize());
		pub_product_fenbianlv.setText(p.getDistinguish());
		pub_product_xitong.setText(p.getSystem());
		pub_product_qianxiangsu.setText(p.getPrec_pixel());
		pub_product_houxiangsu.setText(p.getNext_pixel());
		display_parameters_instruction.setText(p.getContent());
		pub_product_pinpai.setText(p.getBrand());
		pub_product_xinghao.setText(p.getType());
		if (array.length > 0) {
			gallery.setAdapter(imageAdapter);
			if (array.length > 1) {
				gallery.setSelection(1);
			}

		}

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
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
