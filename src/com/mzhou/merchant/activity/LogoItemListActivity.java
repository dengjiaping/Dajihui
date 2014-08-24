package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.mzhou.merchant.adapter.MyGridProductAdapter2;
import com.mzhou.merchant.dao.IProduct.IgetProductInfo;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

public class LogoItemListActivity extends Activity {
	private LinkedList<ProductsBean> mList;
	private ProductsManager productsManager;
	private ImageView showLeft;
	private MyGridProductAdapter2 mAdapter;
	private Context context;
 	private PullToRefreshScrollView mPullRefreshScrollView;
	private MyGridView mGridView;
	private TextView title_bar_title;
	private int page_up;
	private int page_down;
	private String uptime;
	private String title;
	private int id;
	private LinearLayout item_linear_toast;
	private Button im_register;
	 
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
		setContentView(R.layout.xianshi_logo_item);
		init();
		loadButton();
		refreshListener();
		listenerButton();
		getdata();
	}

	/**
	 * 初始化
	 * 
	 */
	private void init() {
		context = LogoItemListActivity.this;
		mList = new LinkedList<ProductsBean>();
		productsManager = new ProductsManager();
		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		id = Integer.parseInt(intent.getStringExtra("id"));
		mAdapter = new MyGridProductAdapter2(context, mList);
		page_up = 2;
		page_down = 1;
		uptime = new String("0");
	}

	/**
	 * 加载需要的控件
	 * 
	 * @param mView
	 */
	private void loadButton() {
		mGridView = (MyGridView) findViewById(R.id.gridview);
		title_bar_title = (TextView) findViewById(R.id.title_bar_title);
		showLeft = (ImageView) findViewById(R.id.title_bar_showleft);
		item_linear_toast = (LinearLayout) findViewById(R.id.item_linear_toast);
		im_register = (Button) findViewById(R.id.im_register);
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setMode(Mode.BOTH);
		mPullRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshScrollView.scrollTo(0, 0);
	}

	/**
	 * 监听广告和产品事件
	 */
	private void listenerButton() {
		title_bar_title.setText(title);
		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent();
//				intent.setClass(getBaseContext(),
//						ActivityLogo.class);
//				startActivity(intent);
				finish();

			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mAdapter.setSeclection(arg2);
				mAdapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.setClass(getBaseContext(),
						ShoujiEnterpriseActivity.class);
				intent.putExtra("id", mList.get(arg2).getId());
				startActivity(intent);
			}
		});
		im_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					if (DbLoginManager.getInstance(context).getLoginStatus()) {
						Intent intent = new Intent();
						intent.setClass(context,
								UserControlEnterpriseActivity.class);
						startActivity(intent);
						finish(); 
					}else {
						 Intent intent = new Intent();
							intent.setClass(context, ActivityLogin.class);
							intent.putExtra("isEnterprise", true);
							startActivity(intent);
							finish(); 
					}
				} catch (Exception e) {
					Intent intent = new Intent();
					intent.setClass(context,
							UserControlEnterpriseActivity.class);
					startActivity(intent);
					finish(); 
					e.printStackTrace();
				}
			}
		});

	}
	private void getdata() {
		 
		productsManager
				.AsynGetLogoItemHasDialog(context, id, page_down, uptime);
		productsManager.getProductInfoIml(new IgetProductInfo() {
			@Override
			public void getProductInfo(List<ProductsBean> productsBeans) {
				if (productsBeans != null) {
					item_linear_toast.setVisibility(View.GONE);
					for (ProductsBean productsBean : productsBeans) {
						mList.addLast(productsBean);
					}
					 
						MyUtlis.sortListOrder(mList);
						if (mList.size() != 0) {
							title_bar_title.setText(mList.get(0).getBrand());
							uptime = mList.get(0).getCtime();
						}else {
							uptime = new String("11");
						}
						mGridView.setAdapter(mAdapter);
				}else {
					item_linear_toast.setVisibility(View.VISIBLE);
				}

			}
		});

	}

	/**
	 * 刷新监听
	 */
	private void refreshListener() {
		mPullRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						if (WebIsConnectUtil
								.showNetState(LogoItemListActivity.this)) {
							productsManager.AsynGetLogoItemHasDialog(context,
									id, page_down, uptime);
							productsManager
									.getProductInfoIml(new IgetProductInfo() {
										@Override
										public void getProductInfo(
												List<ProductsBean> productsBeans) {
											if ((productsBeans != null)
													&& (productsBeans
															.size() !=0)) {
												for (ProductsBean productsBean : productsBeans) {
													mList.addLast(productsBean);
												}
												MyUtlis.sortListOrder(mList);
												if (mList.size() != 0) {
													uptime = mList.get(0).getCtime();
												}
												mAdapter.notifyDataSetChanged();
												mPullRefreshScrollView
														.onRefreshComplete();
											} else {
												mPullRefreshScrollView
														.onRefreshComplete();
											}

										}
									});
						} else {
							mPullRefreshScrollView.onRefreshComplete();
						}

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						if (WebIsConnectUtil
								.showNetState(LogoItemListActivity.this)) {
							productsManager.AsynGetLogoItemHasDialog(context,
									id, page_up, uptime);
							productsManager
									.getProductInfoIml(new IgetProductInfo() {
										@Override
										public void getProductInfo(
												List<ProductsBean> productsBeans) {
											if ((productsBeans != null)
													&& (productsBeans
															.size() !=0)) {
												for (ProductsBean productsBean : productsBeans) {
													mList.addLast(productsBean);

												}
												MyUtlis.sortListOrder(mList);
												page_up++;
												mAdapter.notifyDataSetChanged();
												mPullRefreshScrollView
														.onRefreshComplete();
											} else {
												mPullRefreshScrollView
														.onRefreshComplete();
											}

										}
									});
						} else {
							mPullRefreshScrollView.onRefreshComplete();
						}

					}

				});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent();
//			intent.setClass(getBaseContext(),
//					ActivityLogo.class);
//			startActivity(intent);
			finish();
		}
		return true;
	}
}
