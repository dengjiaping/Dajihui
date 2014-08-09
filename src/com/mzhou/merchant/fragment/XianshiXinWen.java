package com.mzhou.merchant.fragment;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.ActivityXinWen;
import com.mzhou.merchant.activity.XinwenContentActivity;
import com.mzhou.merchant.adapter.MyListNewsAdapter;
import com.mzhou.merchant.dao.INews.IgetNewsInfo;
import com.mzhou.merchant.dao.biz.NewsManager;
import com.mzhou.merchant.db.manager.DbNewsManager;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class XianshiXinWen extends Fragment {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageView title_bar_showleft;
	private ImageView showRgiht;
	// **************************
	private LinkedList<NewsBean> mList;
	private PullToRefreshListView mPullRefreshListView;
	private MyListNewsAdapter mAdapter;
	private NewsManager newsManager;
	private Context context;
	private int page = 2;
	private String uptime;
	private boolean flag = false;
	// **************************
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		View view = inflater.inflate(R.layout.xianshi_xinwen_list, null);
		init();
		loadButton(view);
		initdata();
		getdata();
		return view;
	}

	private void initdata() {
		AsynNews asynNews = new AsynNews();
		asynNews.execute();
	 }

	private void getdata() {
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.setSeclection(position - 1);
				mAdapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.setClass(getActivity(), XinwenContentActivity.class);
				intent.putExtra("id", mList.get(position - 1).getId());
				startActivity(intent);
			}
		});
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity()
								.getBaseContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(getActivity())) {
							newsManager.GetNewsInfo(getActivity(), 1, uptime);
							newsManager.getNewsInfoIml(new IgetNewsInfo() {
								@Override
								public void getNewsInfo(List<NewsBean> newsBean) {
									if ((newsBean != null) && ( newsBean.size() != 0)) {

										for (NewsBean attactBean : newsBean) {
											mList.addFirst(attactBean);
											flag = true;
										}
										MyUtlis.sortListNewsBeanOrder(mList);
										uptime = mList.get(0).getCtime();
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
						String label = DateUtils.formatDateTime(getActivity()
								.getBaseContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(getActivity())) {
							newsManager.GetNewsInfo(getActivity(), page, "0");
							page++;
							newsManager.getNewsInfoIml(new IgetNewsInfo() {
								@Override
								public void getNewsInfo(List<NewsBean> newsBean) {
									if ((newsBean != null) && ( newsBean.size() != 0)) {

										for (NewsBean attactBean : newsBean) {
											mList.addLast(attactBean);
										}
										MyUtlis.sortListNewsBeanOrder(mList);
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

	private void loadButton(View view) {
		title_bar_showleft = (ImageView) view
				.findViewById(R.id.title_bar_showleft);
		showRgiht = (ImageView) view.findViewById(R.id.title_bar_publish);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
	}

	private void init() {
		context = getActivity().getBaseContext();
		newsManager = new NewsManager();
		mList = new LinkedList<NewsBean>();
		uptime = new String("0");
		mAdapter = new MyListNewsAdapter(context, mList);
	}

	public class AsynNews extends AsyncTask<Void, Void, List<NewsBean>> {

		public AsynNews() {

		}

		@Override
		protected List<NewsBean> doInBackground(Void... params) {
			return  DbNewsManager.getInstance(context).getListAll();
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
		protected void onPostExecute(List<NewsBean> result) {
			if ((result != null) && ( result.size() != 0)) {
				mList.addAll(result);
				MyUtlis.sortListNewsBeanOrder(mList);
				uptime = mList.get(0).getCtime();
				mPullRefreshListView.setAdapter(mAdapter);
			} else {
				uptime = new String("11");
				mPullRefreshListView.setAdapter(mAdapter);

			}
			super.onPostExecute(result);
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ActivityXinWen.showLeft();
			}
		});
		showRgiht.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ActivityXinWen.showRight();
			}
		});
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		 if (flag) {//save the data to file
			//to json
				DbNewsManager.getInstance(context).deleteAll();
			 for (int i = 0; i < mList.size(); i++) {
				if (i <= 20) {
					NewsBean bean = mList.get(i);
					DbNewsManager.getInstance(context).insert(bean);
				}
			}
		} 
		
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.gc();
	}
}
