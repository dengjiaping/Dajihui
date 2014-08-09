package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.adapter.MyActivityAdapter;
import com.mzhou.merchant.dao.IActivity.IgetActivityInfo;
import com.mzhou.merchant.dao.biz.ActivityManager;
import com.mzhou.merchant.db.manager.DbActivityManager;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

public class ActivityHuoDong extends Activity {
	private ImageView title_bar_showleft;
	private ImageView title_bar_publish;
	private LinkedList<ActivityBean> mList;
	private PullToRefreshListView mPullRefreshListView;
	private MyActivityAdapter mAdapter;
	private ActivityManager activityManager;
	private int page = 2;
	private Context context;
	private String uptime;
	private boolean flag= false;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_huodong);
		init();
		loadButton();
		lisentnerButton();
		getdata();
	}
	private void getdata() {
		AsynAttact asynAttact = new AsynAttact();
		asynAttact.execute();
	}

	private void lisentnerButton() {
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {

						String label = DateUtils.formatDateTime(context,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(context)) {
							activityManager.GetActivityInfo(context, 1, uptime);
							activityManager
									.getActivityInfoIml(new IgetActivityInfo() {
										@Override
										public void getActivityInfo(
												List<ActivityBean> activityBeans) {
											if (activityBeans != null) {
												for (ActivityBean activityBean : activityBeans) {
													mList.addFirst(activityBean);
													flag = true;
												}
												MyUtlis.sortListActivityBeanOrder(mList);
												uptime = mList.get(0)
														.getCtime();
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

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(context,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(context)) {
							activityManager.GetActivityInfo(context, page, "0");
							activityManager
									.getActivityInfoIml(new IgetActivityInfo() {
										@Override
										public void getActivityInfo(
												List<ActivityBean> activityBeans) {
											if (activityBeans != null) {
												for (ActivityBean activityBean : activityBeans) {
													mList.addLast(activityBean);
												}
												MyUtlis.sortListActivityBeanOrder(mList);
												uptime = mList.get(0)
														.getCtime();
												mAdapter.notifyDataSetChanged();
												mPullRefreshListView
														.onRefreshComplete();
												page++;
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
		// 查看活动详细信息，并且加入活动
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent intent = new Intent(context, ActivityHuoDongDetail.class);
				intent.putExtra("id", mList.get(arg2 - 1).getId());
				startActivity(intent); 
			}
		});
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title_bar_publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, FabuActivity.class);
				startActivity(intent);
			}
		});

	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		title_bar_publish = (ImageView) findViewById(R.id.title_bar_publish);
		mPullRefreshListView = (PullToRefreshListView)  findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshListView.scrollTo(0, 0);
	}

	private void init() {
		uptime = new String("0");
		context = ActivityHuoDong.this;
		mList = new LinkedList<ActivityBean>();
		activityManager = new ActivityManager();
		mAdapter = new MyActivityAdapter(context, mList);
	}

	public class AsynAttact extends AsyncTask<Void, Void, LinkedList<ActivityBean>> {

		public AsynAttact() {

		}

		@Override
		protected LinkedList<ActivityBean> doInBackground(Void... params) {
//			String result = MyUtlis.readFiles(MyConstants.ACTIVITY, context);
			
			return DbActivityManager.getInstance(context).getListAll();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(LinkedList<ActivityBean> productsBeans) {
/*
			if ((result != null) && (!result.equals(""))
					&& (WebIsConnectUtil.showNetState(context))) {
				List<ActivityBean> productsBeans = JsonParse
						.parseActivityJson(result);*/
				if (productsBeans != null) {
					ListView actualListView = mPullRefreshListView
							.getRefreshableView();
					mList.addAll(productsBeans);
					MyUtlis.sortListActivityBeanOrder(mList);
					uptime = mList.get(0).getCtime();
					actualListView.setAdapter(mAdapter);
//				}
			}
			super.onPostExecute(productsBeans);
		}

	}
	public void onDestroy() {
		super.onDestroy();
		 if (flag) {//save the data to file
			//to json
				DbActivityManager.getInstance(context).deleteAll();
			 for (int i = 0; i < mList.size(); i++) {
				if (i <= 20) {
					ActivityBean bean = mList.get(i);
					DbActivityManager.getInstance(context).insert(bean);
				}
			}
		} 
		
	}
}
