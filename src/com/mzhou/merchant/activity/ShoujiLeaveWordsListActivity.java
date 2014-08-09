package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.dao.IProduct.IGetCommentInfo;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.model.ProductCommentBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShoujiLeaveWordsListActivity extends Activity {
	private ImageView title_bar_leavewords;
	private Context context;
	private ImageView title_bar_showleft;
	private String productid;
	private ProductsManager productsManager;
	private ZSAdapter mAdapter;
	private LinkedList<ProductCommentBean> mLinkedList;
	private PullToRefreshListView mPullRefreshListView;
	private String uptime;
	private int page_up;
	private int page_down;
	private TextView no_leavewords;

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
		setContentView(R.layout.xianshi_shouji_leavewords_list);
		init();
		loadButton();
		listenerClick();
		getdata();
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
		productsManager = new ProductsManager();
		context = ShoujiLeaveWordsListActivity.this;
		mAdapter = new ZSAdapter(context);
		Intent intent = getIntent();
		productid = intent.getStringExtra("id");
		page_down = 1;
		page_up = 2;
		uptime = String.valueOf(System.currentTimeMillis() / 1000);
		mLinkedList = new LinkedList<ProductCommentBean>();
	}

	private void listenerClick() {

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShoujiLeaveWordsListActivity.this.finish();
			}
		});
		title_bar_leavewords.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ShoujiLeaveWordsListActivity.this,
						ShoujiLeaveWordsFabuActivity.class);
				intent.putExtra("productid", productid);
				startActivity(intent);
			}
		});
	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		title_bar_leavewords = (ImageView) findViewById(R.id.title_bar_leavewords);
		no_leavewords = (TextView) findViewById(R.id.no_leavewords);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
	}

	private void getdata() {
		if (WebIsConnectUtil.showNetState(context)) {
			productsManager.GetProductsLeaveWords(
					ShoujiLeaveWordsListActivity.this, productid, page_down, "0");
			productsManager.getCommentInfoIml(new IGetCommentInfo() {
				@Override
				public void getCommentInfo(
						List<ProductCommentBean> productCommentBeans) {
					ListView actualListView = mPullRefreshListView
							.getRefreshableView();
					if (productCommentBeans != null) {
						mLinkedList.addAll(productCommentBeans);
						no_leavewords.setVisibility(View.GONE);

						MyUtlis.sortListProductCommentBeanOrder(mLinkedList);
						uptime = mLinkedList.get(0).getCtime();
						actualListView.setAdapter(mAdapter);
					} else {
						uptime = new String("11");
						actualListView.setAdapter(mAdapter);
					}
				}
			});

		}
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (WebIsConnectUtil.showNetState(context)) {
							productsManager.GetProductsLeaveWords(
									ShoujiLeaveWordsListActivity.this, productid,
									1, uptime);
							productsManager
									.getCommentInfoIml(new IGetCommentInfo() {
										@Override
										public void getCommentInfo(
												List<ProductCommentBean> productCommentBeans) {
											if (productCommentBeans != null) {
												for (ProductCommentBean productCommentBean : productCommentBeans) {
													mLinkedList
															.addFirst(productCommentBean);
												}
												MyUtlis.sortListProductCommentBeanOrder(mLinkedList);
												uptime = mLinkedList.get(0)
														.getCtime();
												mAdapter.notifyDataSetChanged();
												mPullRefreshListView
														.onRefreshComplete();
												no_leavewords
														.setVisibility(View.GONE);
											} else {
												mPullRefreshListView
														.onRefreshComplete();
											}
										}
									});

						} else {
							mPullRefreshListView.onRefreshComplete();
						}

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getBaseContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(context)) {
							productsManager.GetProductsLeaveWords(
									ShoujiLeaveWordsListActivity.this, productid,
									page_up, "0");
							productsManager
									.getCommentInfoIml(new IGetCommentInfo() {
										@Override
										public void getCommentInfo(
												List<ProductCommentBean> productCommentBeans) {
											if (productCommentBeans != null) {
												for (ProductCommentBean productCommentBean : productCommentBeans) {
													mLinkedList
															.addLast(productCommentBean);
												}
												MyUtlis.sortListProductCommentBeanOrder(mLinkedList);
												page_up++;
												mAdapter.notifyDataSetChanged();
												mPullRefreshListView
														.onRefreshComplete();
											} else {
												mPullRefreshListView
														.onRefreshComplete();
											}
										}
									});

						} else {
							mPullRefreshListView.onRefreshComplete();
						}

					}
				});
	}

	public class ZSAdapter extends BaseAdapter {
		private class ViewHolder {
			TextView content;
			TextView date;
			TextView name;
			TextView category;
			TextView number;

		}

		private Context mContext;

		public ZSAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return mLinkedList.size();
		}

		@Override
		public Object getItem(int position) {
			return mLinkedList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.xianshi_shouji_leavewords_item, null);
				holder = new ViewHolder();
				holder.content = (TextView) view
						.findViewById(R.id.pinglun_content);
				holder.date = (TextView) view.findViewById(R.id.pinglun_date);
				holder.name = (TextView) view.findViewById(R.id.pinglun_name);
				holder.category = (TextView) view
						.findViewById(R.id.pinglun_category);
				holder.number = (TextView) view.findViewById(R.id.pinglun_nub);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.content.setText(mLinkedList.get(position).getContent());
			holder.date.setText(MyUtlis.TimeStamp2DateList(
					mLinkedList.get(position).getCtime(), context));
			holder.name.setText(getResources().getString(R.string.contact_name)
					+ mLinkedList.get(position).getNickname());
			holder.category.setText(mLinkedList.get(position).getCategory());
			holder.number.setText(getResources().getString(R.string.contact)
					+ mLinkedList.get(position).getContact());

			return view;
		}
	}

}
