package com.mzhou.merchant.activity;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mzhou.merchant.dao.IProduct.IgetProductInfoById;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbProductManager;
import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

@SuppressWarnings("deprecation")
public class ShoujiCommenActivity extends Activity {
	private Button getMoreComment;
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
	private String productid;
	private ProductsManager productsManager;
	private String picfromServer;

	private LinkedList<String> list;
	private String[] listStr;
	private LinearLayout linear;
	private ScrollView scroll_linear;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	    
		setContentView(R.layout.xianshi_shouji_common);
		init();
		loadButton();
		listenerClick();
		try {
			scroll_linear.setVisibility(View.VISIBLE);
			linear.setVisibility(View.GONE);
			getdata();
		} catch (Exception e) {
			DbProductManager.getInstance(context).deleteByItem(productid);
			scroll_linear.setVisibility(View.GONE);
			linear.setVisibility(View.VISIBLE);
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void init() {
		
		list = new LinkedList<String>();
		context = getBaseContext();
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
		productsManager = new ProductsManager();
		Intent intent = getIntent();
		productid = intent.getStringExtra("id");
		imageAdapter = new ImageAdapter();
		imageitemAdapter = new ImageItemAdapter();
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
				intent.setClass(ShoujiCommenActivity.this, PicPagerActivity.class);
				for (int i = 0; i < list.size(); i++) {
					listStr[i] = list.get(i);
				}
				imageitemAdapter.notifyDataSetChanged();
				intent.putExtra(
						MyConstants.Extra.IMAGES,
						listStr);
				intent.putExtra(
						MyConstants.Extra.IMAGE_POSITION,
						arg2);
				startActivity(intent);

			}
		});

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShoujiCommenActivity.this.finish();
			}
		});
	btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShoujiCommenActivity.this.finish();
			}
		});
		getMoreComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ShoujiCommenActivity.this,
						ShoujiLeaveWordsListActivity.class);
				intent.putExtra("id", productid);
				startActivity(intent);
			}
		});
	}

	private void loadButton() {
		gallery = (Gallery) findViewById(R.id.xianshi_shouji_gallery);
		imageView_call = (Button) findViewById(R.id.xianshi_shouji_call);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		getMoreComment = (Button) findViewById(R.id.getMoreComment);
		pub_product_pinpai = (TextView) findViewById(R.id.pub_product_pinpai);
		pub_product_xinghao = (TextView) findViewById(R.id.pub_product_xinghao);
		pub_product_xinpian = (TextView) findViewById(R.id.pub_product_xinpian);
		pub_product_chicun = (TextView) findViewById(R.id.pub_product_chicun);
		pub_product_fenbianlv = (TextView) findViewById(R.id.pub_product_fenbianlv);
		pub_product_xitong = (TextView) findViewById(R.id.pub_product_xitong);
		pub_product_qianxiangsu = (TextView) findViewById(R.id.pub_product_qianxiangsu);
		pub_product_houxiangsu = (TextView) findViewById(R.id.pub_product_houxiangsu);

		pub_product_jishenneicun = (TextView) findViewById(R.id.pub_product_jishenneicun);
		pub_product_dianchirongliang = (TextView) findViewById(R.id.pub_product_dianchirongliang);
		display_parameters_instruction = (TextView) findViewById(R.id.display_parameters_instruction);
		user_manager_name = (TextView) findViewById(R.id.user_manager_name);
		user_manager_qq = (TextView) findViewById(R.id.user_manager_qq);
		user_manager_phonnumber = (TextView) findViewById(R.id.user_manager_phonnumber);
		user_manager_company = (TextView) findViewById(R.id.user_manager_company);
		user_manager_address = (TextView) findViewById(R.id.user_manager_address);
		user_manager_net = (TextView) findViewById(R.id.user_manager_net);

		linear = (LinearLayout) findViewById(R.id.linear);
		scroll_linear = (ScrollView) findViewById(R.id.scroll_linear);
		btn_back = (Button) findViewById(R.id.btn_back);
	}

	private void getdata() throws Exception{
		if (WebIsConnectUtil.showNetState(context)) {
			productsManager.GetProductInfoById(ShoujiCommenActivity.this, productid);
			productsManager.getProductInfoByIdIml(new IgetProductInfoById() {
				@Override
				public void getProductInfoById(ProductsByIdBean productsByIdBean) {
					if (productsByIdBean != null) {
						 
						user_manager_name.setText(productsByIdBean.getContact());
						user_manager_qq.setText(productsByIdBean.getQq());
						user_manager_phonnumber.setText(productsByIdBean
								.getPhone());
						number = productsByIdBean.getPhone();
						user_manager_company.setText(productsByIdBean
								.getCompany());
						user_manager_address.setText(productsByIdBean
								.getAddress());
						user_manager_net.setText(productsByIdBean.getNet());
						picfromServer = productsByIdBean.getPic();
						pub_product_xinpian.setText(productsByIdBean.getChip());
						pub_product_chicun.setText(productsByIdBean.getSize());
						pub_product_fenbianlv.setText(productsByIdBean
								.getDistinguish());
						pub_product_xitong.setText(productsByIdBean.getSystem());
						pub_product_qianxiangsu.setText(productsByIdBean
								.getPrec_pixel());
						pub_product_houxiangsu.setText(productsByIdBean
								.getNext_pixel());
						pub_product_jishenneicun.setText(productsByIdBean
								.getRom());
						pub_product_dianchirongliang.setText(productsByIdBean
								.getAh());
						display_parameters_instruction.setText(productsByIdBean
								.getContent());
						pub_product_pinpai.setText(productsByIdBean.getBrand());
						pub_product_xinghao.setText(productsByIdBean.getType());
						if (picfromServer != null) {
							StringTokenizer tokenizer = new StringTokenizer(
									picfromServer, getResources().getString(
											R.string.spilt));
							while (tokenizer.hasMoreTokens()) {
								list.add(MyConstants.PICTURE_URL
										+ tokenizer.nextToken());
							}
							listStr = new String[list.size()];
							for (int i = 0; i < list.size(); i++) {
								listStr[i] = list.get(i);
							}
							gallery.setAdapter(imageAdapter);
							if (list.size() > 1) {
								gallery.setSelection(1);
							}
						}
					} else {
						DbProductManager.getInstance(context).deleteByItem(productid);
						scroll_linear.setVisibility(View.GONE);
						linear.setVisibility(View.VISIBLE);
					}
				}
			});

		} else {
			Toast.makeText(context, "网络未连接", Toast.LENGTH_LONG).show();
			finish();
		}

	}

	public class ImageAdapter extends BaseAdapter {

		public ImageAdapter() {
		}

		@Override
		public int getCount() {
			return list.size();
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
			imageLoader.displayImage(list.get(position), imageView, options);
			return imageView;

		}
	}

	public class ImageItemAdapter extends BaseAdapter {

		public ImageItemAdapter() {
		}

		@Override
		public int getCount() {
			return list.size();
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
			TextView t = new TextView(ShoujiCommenActivity.this);
			t.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			t.setText(position + File.separator + list.size());
			t.setTextColor(Color.BLACK);
			return t;

		}

	}
 
}
