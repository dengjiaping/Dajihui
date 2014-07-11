package com.mzhou.merchant.activity;

import java.io.File;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.mzhou.merchant.dao.IProduct.IgetEnterpriseProductInfoById;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbProductManager;
import com.mzhou.merchant.model.ProductsEnterpriseByIdBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ShoujiEnterpriseActivity extends Activity {
	private Button getMoreComment;
	private Gallery gallery;
	private ImageAdapter imageAdapter;
	private ImageItemAdapter imageitemAdapter;
	private Context context;
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
	private TextView pub_product_jishenneicun;
	private TextView pub_product_dianchirongliang;
	private TextView about_company;
	private TextView contact_company;
	private String productid;
	private ProductsManager productsManager;
	private String picfromServer;
	private ProductsEnterpriseByIdBean p;
	private LinkedList<String> list;
	private String[] listStr;

	
	private LinearLayout linear;
	private LinearLayout bottom_linearlayout;
	private ScrollView scroll_linear;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_shouji_enterprise);
		init();
		loadButton();
		listenerClick();
		try {
			bottom_linearlayout.setVisibility(View.VISIBLE);
			scroll_linear.setVisibility(View.VISIBLE);
			linear.setVisibility(View.GONE);
			getdata();
		} catch (Exception e) {
			DbProductManager.getInstance(context).deleteByItem(productid);
			bottom_linearlayout.setVisibility(View.GONE);
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
		super.onPause();
	}

	private void init() {
		
		list = new LinkedList<String>();
		context = getBaseContext();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ad_loading)
				.showImageForEmptyUri(R.drawable.ad_loading)
				.showImageOnFail(R.drawable.ad_loading).cacheInMemory()
				.cacheOnDisc().delayBeforeLoading(0)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader = ImageLoader.getInstance();
		productsManager = new ProductsManager();
		Intent intent = getIntent();
		productid = intent.getStringExtra("id");
		imageAdapter = new ImageAdapter();
		imageitemAdapter = new ImageItemAdapter();
	}

	private void listenerClick() {
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(ShoujiEnterpriseActivity.this,
						PicPagerActivity.class);
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
				ShoujiEnterpriseActivity.this.finish();
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShoujiEnterpriseActivity.this.finish();
			}
		});
		getMoreComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ShoujiEnterpriseActivity.this,
						ShoujiLeaveWordsListActivity.class);
				intent.putExtra("id", productid);
				startActivity(intent);
			}
		});
		about_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (WebIsConnectUtil.showNetState(context)) {
					Intent intent = new Intent();
					intent.setClass(context, CompanyIntroduceActivity.class);
					intent.putExtra("brand", p.getBrand());
					// Log.i("print", p.toString());
					startActivity(intent);
				}

			}
		});
		contact_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WebIsConnectUtil.showNetState(context)) {
					Intent intent = new Intent();
					intent.setClass(context, CompanyContactUsActivity.class);
					intent.putExtra("productinfo", p);
					startActivity(intent);
				}
			}
		});

	}

	private void loadButton() {
		gallery = (Gallery) findViewById(R.id.xianshi_shouji_gallery);
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
		about_company = (TextView) findViewById(R.id.about_company);
		contact_company = (TextView) findViewById(R.id.contact_company);

		pub_product_jishenneicun = (TextView) findViewById(R.id.pub_product_jishenneicun);
		pub_product_dianchirongliang = (TextView) findViewById(R.id.pub_product_dianchirongliang);
		display_parameters_instruction = (TextView) findViewById(R.id.display_parameters_instruction);
		
		linear = (LinearLayout) findViewById(R.id.linear);
		scroll_linear = (ScrollView) findViewById(R.id.scroll_linear);
		btn_back = (Button) findViewById(R.id.btn_back);
		bottom_linearlayout = (LinearLayout) findViewById(R.id.bottom_linearlayout);
	}

	private void getdata() throws Exception{
		if (WebIsConnectUtil.showNetState(context)) {
			productsManager.ProductsEnterpriseByIdBean(
					ShoujiEnterpriseActivity.this, productid);
			productsManager
					.getEnterpriseProductInfoByIdIml(new IgetEnterpriseProductInfoById() {

						@Override
						public void getProductInfoById(
								ProductsEnterpriseByIdBean productsByIdBean) {
							if (productsByIdBean != null) {
								p = productsByIdBean;
								picfromServer = productsByIdBean.getPic();
								pub_product_xinpian.setText(productsByIdBean
										.getChip());
								pub_product_chicun.setText(productsByIdBean
										.getSize());
								pub_product_fenbianlv.setText(productsByIdBean
										.getDistinguish());
								pub_product_xitong.setText(productsByIdBean
										.getSystem());
								pub_product_qianxiangsu
										.setText(productsByIdBean
												.getPrec_pixel());
								pub_product_houxiangsu.setText(productsByIdBean
										.getNext_pixel());
								pub_product_jishenneicun
										.setText(productsByIdBean.getRom());
								pub_product_dianchirongliang
										.setText(productsByIdBean.getAh());
								display_parameters_instruction
										.setText(productsByIdBean.getContent());
								pub_product_pinpai.setText(productsByIdBean
										.getBrand());
								pub_product_xinghao.setText(productsByIdBean
										.getType());
								if (picfromServer != null) {
									StringTokenizer tokenizer = new StringTokenizer(
											picfromServer, getResources()
													.getString(R.string.spilt));
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
								bottom_linearlayout.setVisibility(View.GONE);
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
			TextView t = new TextView(ShoujiEnterpriseActivity.this);
			t.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			t.setText(position + File.separator + list.size());
			t.setTextColor(Color.BLACK);
			return t;
		}
	}

}
