package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.mzhou.merchant.adapter.MyGridProductAdapter4;
import com.mzhou.merchant.adapter.MyListNewsAdapter;
import com.mzhou.merchant.dao.INews.IgetNewsInfo;
import com.mzhou.merchant.dao.IProduct.IgetProductInfo;
import com.mzhou.merchant.dao.biz.SearchManager;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.myview.MyListView;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ActivitySS extends Activity {

	private ImageView showLeft;
	private Button sousuo;
	private EditText sousuoContent;
	private RadioGroup group;
	private RadioButton rb_chanpin;
	private SearchManager searchManager;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private MyGridView mGridView;
 	private int page_news;
 	private int page_product;
	private boolean isNews = false;
	private String cate;
	private String keyword;
	private MyGridProductAdapter4 mproductAdapter;
	private LinkedList<ProductsBean> mproductList;

	private LinkedList<NewsBean> mnewsList;
	private MyListNewsAdapter mnewsAdapter;
	private MyListView mListView;
	private Context context;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_sousuo);
		init();
		loadButton();
		clickButton();
		refreshData();
		clickItemFromData();
	}

	/**
	 * ��ʼ�����
	 */
	private void init() {
		page_news = 2;
		page_product = 2;
		context = ActivitySS.this;
		cate = new String("product");
		mproductList = new LinkedList<ProductsBean>();
		mnewsList = new LinkedList<NewsBean>();
		mnewsAdapter = new MyListNewsAdapter(context, mnewsList);
		imageLoader = ImageLoader.getInstance();
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
		 
		 mproductAdapter = new MyGridProductAdapter4(context, mproductList,imageLoader,options);
	}

	private void clickItemFromData() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mproductAdapter.setSeclection(position);
				mproductAdapter.notifyDataSetChanged();

				if (mproductList.get(position).getIs_en().equals("1")) {
					Intent intent = new Intent();
					intent.setClass(context, ShoujiEnterpriseActivity.class);
					intent.putExtra("id", mproductList.get(position).getId());
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(context, ShoujiCommenActivity.class);
					intent.putExtra("id", mproductList.get(position).getId());
					startActivity(intent);
				}

			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mnewsAdapter.setSeclection(position);
				mnewsAdapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.setClass(context, XinwenContentActivity.class);
				intent.putExtra("id", mnewsList.get(position).getId());
				startActivity(intent);
			}
		});
	}

	private void refreshData() {
		mPullRefreshScrollView.setMode(Mode.DISABLED);
		mPullRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						if (WebIsConnectUtil.showNetState(context)) {
							if (isNews) {

								searchManager.GetAsynSearchInfo(context, cate,
										page_news, keyword);
								searchManager
										.getSearchNewsInfoIml(new IgetNewsInfo() {
											@Override
											public void getNewsInfo(
													List<NewsBean> newsBean) {
												if (newsBean != null) {
													page_news ++;
													for (NewsBean newsBean2 : newsBean) {
														mnewsList
																.addLast(newsBean2);
													}
													mnewsAdapter
															.notifyDataSetChanged();
													mPullRefreshScrollView
															.onRefreshComplete();
												} else {
													mnewsAdapter
															.notifyDataSetChanged();
													mPullRefreshScrollView
															.onRefreshComplete();
												}
											}
										});
							} else {

								searchManager.GetAsynSearchInfo(context, cate,
										page_product, keyword);
								searchManager
										.getSearchProductInfoIml(new IgetProductInfo() {
											@Override
											public void getProductInfo(
													List<ProductsBean> productsBeans) {
												if (productsBeans != null) {
													page_product ++;
													for (ProductsBean productsBean : productsBeans) {
														mproductList
																.addLast(productsBean);
													}
													mproductAdapter
															.notifyDataSetChanged();
													mPullRefreshScrollView
															.onRefreshComplete();

												} else {
													mproductAdapter
															.notifyDataSetChanged();
													mPullRefreshScrollView
															.onRefreshComplete();
												}
											}
										});

							}
						}
					}

				});
	}

	/**
	 * ���� findviewbyid
	 * 
	 * @param mView
	 */
	private void loadButton() {
		mGridView = (MyGridView) findViewById(R.id.gridview);
		mListView = (MyListView) findViewById(R.id.list);
		searchManager = new SearchManager();
		showLeft = (ImageView) findViewById(R.id.title_bar_showleft);
		sousuo = (Button) findViewById(R.id.btn_sousuo);
		group = (RadioGroup) findViewById(R.id.radioGroup);
		sousuoContent = (EditText) findViewById(R.id.edt_sousuo);
		rb_chanpin = (RadioButton) findViewById(R.id.rb_sousuo_chanpin);
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		rb_chanpin.setChecked(true);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		Intent intent = new Intent();
		intent.setClass(ActivitySS.this,
				ActivityIndex.class);
		startActivity(intent);
		finish();
	}
		return true;
	}
	private void clickButton() {
		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ActivitySS.this,
						ActivityIndex.class);
				startActivity(intent);
				finish();
			}
		});

		sousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				keyword = sousuoContent.getText().toString();
				if (!keyword.equals("") && !keyword.equals(" ")) {
					if (WebIsConnectUtil.showNetState(context)) {
						mPullRefreshScrollView.setMode(Mode.PULL_FROM_END);
						if (isNews) {
							mListView.setVisibility(View.VISIBLE);
							mGridView.setVisibility(View.GONE);
							searchManager.GetAsynSearchInfo(context, cate,
									1, keyword);
							searchManager
									.getSearchNewsInfoIml(new IgetNewsInfo() {
										@Override
										public void getNewsInfo(
												List<NewsBean> newsBean) {
											if (newsBean != null) {
												mnewsList.clear();
												mnewsList.addAll(newsBean);
												MyUtlis.sortListNewsBeanOrder(mnewsList);
												mListView
														.setAdapter(mnewsAdapter);
											} else {

												MyUtlis.toastInfo(
														context,
														getResources()
																.getString(
																		R.string.result_null));

											}

										}
									});
						} else {
							mGridView.setVisibility(View.VISIBLE);
							mListView.setVisibility(View.GONE);
							searchManager.GetAsynSearchInfo(context, cate,
									1, keyword);
							searchManager
									.getSearchProductInfoIml(new IgetProductInfo() {

										@Override
										public void getProductInfo(
												List<ProductsBean> productsBeans) {
											if (productsBeans != null) {
												mproductList.clear();
												mproductList
														.addAll(productsBeans);
												MyUtlis.sortListOrder(mproductList);
												mGridView
														.setAdapter(mproductAdapter);

											} else {
												MyUtlis.toastInfo(
														context,
														getResources()
																.getString(
																		R.string.result_null));
											}
										}
									});
						}
					}
				} else {
					MyUtlis.toastInfo(context,
							getResources().getString(R.string.search_null));

				}
			}
		});
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				String category = rb.getText().toString();
				if (category.equals(getResources().getString(R.string.news))) {
					isNews = true;
					cate = new String("news");
				} else {
					isNews = false;
					cate = new String("product");
				}
			}
		});
	}
	
}
