package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.dao.INews.IgetNewsCommentInfo;
import com.mzhou.merchant.dao.biz.NewsManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.NewsCommentBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

public class XinwenCommentListActivity extends Activity {
	private ImageView backButton;
	private String product_id;
	private TextView no_comment;
	private NewsManager newsManager;
	private PullToRefreshListView mPullRefreshListView;
	private LinkedList<NewsCommentBean> mLinkedList;
	private ZSAdapter listAdapter;
	private ImageView comment;
	private int page_up;
	private int page_down;
	private String uptime;
	private ListView actualListView;
	private Context context;

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
		setContentView(R.layout.xianshi_xinwen_comment_list);
		init();
		loadButton();
		getdata();
		listenerButton();

	}

	private void init() {
		newsManager = new NewsManager();
		page_down = 1;
		page_up = 2;
		uptime = new String("0");
		mLinkedList = new LinkedList<NewsCommentBean>();
		Intent intent = getIntent();
		product_id = intent.getStringExtra("newsId");
		mLinkedList = new LinkedList<NewsCommentBean>();
		listAdapter = new ZSAdapter(this);
		context = XinwenCommentListActivity.this;
	}

	private void listenerButton() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 String uid = "0";
				 String commenter = "";
				 
				UserInfoBean userInfoBean =  DbUserManager.getInstance(XinwenCommentListActivity.this).getLogingUserInfo();
				if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
					uid = userInfoBean.getUid();
				}
				if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
					if (userInfoBean.getUsertype().equals("1")) {
						commenter = userInfoBean.getNickname();
					}else {
						commenter = userInfoBean.getContact();
					}
				}
				 
				Intent intent = new Intent();
				intent.setClass(XinwenCommentListActivity.this,
						XinwenCommentFabuActivity.class);
				intent.putExtra("uid", uid);
				intent.putExtra("commenter", commenter);
				intent.putExtra("product_id", product_id);
				startActivity(intent);
			}
		});
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getBaseContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						if (WebIsConnectUtil
								.showNetState(XinwenCommentListActivity.this)) {
							newsManager.GetNewsCommentInfoById(
									XinwenCommentListActivity.this, page_down,
									product_id, uptime);
							newsManager
									.getNewsCommentInfo(new IgetNewsCommentInfo() {
										@Override
										public void getNewsCommentInfo(
												List<NewsCommentBean> newsBean) {

											if (newsBean != null) {
												for (NewsCommentBean newsCommentBean : newsBean) {
													mLinkedList
															.addFirst(newsCommentBean);
												}
												// Log.i("print", "------>" +
												// mLinkedList.toString());
												MyUtlis.sortListNewsCommentBeanOrder(mLinkedList);
												no_comment
														.setVisibility(View.GONE);
												uptime = mLinkedList.get(0)
														.getCtime();
												listAdapter
														.notifyDataSetChanged();
												mPullRefreshListView
														.onRefreshComplete();
											} else {
												// uptime = new String("11");
												listAdapter
														.notifyDataSetChanged();
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

						if (WebIsConnectUtil
								.showNetState(XinwenCommentListActivity.this)) {
							newsManager.GetNewsCommentInfoById(
									XinwenCommentListActivity.this, page_up,
									product_id, "0");
							newsManager
									.getNewsCommentInfo(new IgetNewsCommentInfo() {
										@Override
										public void getNewsCommentInfo(
												List<NewsCommentBean> newsBean) {
											if (newsBean != null) {
												for (NewsCommentBean newsCommentBean : newsBean) {
													mLinkedList
															.addLast(newsCommentBean);
												}
												page_up++;
												MyUtlis.sortListNewsCommentBeanOrder(mLinkedList);
												listAdapter
														.notifyDataSetChanged();
												mPullRefreshListView
														.onRefreshComplete();
											} else {
												listAdapter
														.notifyDataSetChanged();
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

	private void loadButton() {
		backButton = (ImageView) findViewById(R.id.title_bar_showleft);
		comment = (ImageView) findViewById(R.id.title_bar_comment);
		no_comment = (TextView) findViewById(R.id.no_comment);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
		actualListView = mPullRefreshListView.getRefreshableView();
	}

	private void getdata() {
		if (WebIsConnectUtil.showNetState(XinwenCommentListActivity.this)) {
			newsManager.GetNewsCommentInfoById(XinwenCommentListActivity.this,
					page_down, product_id, "0");
			newsManager.getNewsCommentInfo(new IgetNewsCommentInfo() {
				@Override
				public void getNewsCommentInfo(List<NewsCommentBean> newsBean) {
					if (newsBean != null) {
						mLinkedList.addAll(newsBean);
						MyUtlis.sortListNewsCommentBeanOrder(mLinkedList);
						no_comment.setVisibility(View.GONE);
						MyUtlis.sortListNewsCommentBeanOrder(mLinkedList);
						uptime = mLinkedList.get(0).getCtime();
						actualListView.setAdapter(listAdapter);

					} else {
						uptime = new String("11");
						// no_comment.setVisibility(View.VISIBLE);
						actualListView.setAdapter(listAdapter);
					}
				}
			});

		}
	}

	public class ZSAdapter extends BaseAdapter {
		private class ViewHolder {
			TextView content;
			TextView date;
			TextView name;
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
						R.layout.xianshi_xinwen_pinglun_item, null);
				holder = new ViewHolder();
				holder.content = (TextView) view
						.findViewById(R.id.pinglun_content);
				holder.date = (TextView) view.findViewById(R.id.pinglun_date);
				holder.name = (TextView) view.findViewById(R.id.pinglun_name);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.content.setText(mLinkedList.get(position).getContent());
			holder.date.setText(MyUtlis.TimeStamp2DateList(
					mLinkedList.get(position).getCtime(), context));
			holder.name.setText(mLinkedList.get(position).getCommenter());

			return view;
		}
	}

}
