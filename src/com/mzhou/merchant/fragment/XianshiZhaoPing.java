package com.mzhou.merchant.fragment;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.activity.FabuZhaopinActivity;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.ActivityZP;
import com.mzhou.merchant.activity.ZhaopinContentActivity;
import com.mzhou.merchant.adapter.MyJobAdapter;
import com.mzhou.merchant.dao.IJob.IgetJobInfo;
import com.mzhou.merchant.dao.biz.JobManager;
import com.mzhou.merchant.db.manager.DbJobManager;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

public class XianshiZhaoPing extends Fragment {
	private ImageView title_bar_showleft;
	private ImageView title_bar_publish;
	private LinkedList<JobBean> mList;
	private Context context;
	private PullToRefreshListView mPullRefreshListView;
	private MyJobAdapter mAdapter;
	private JobManager jobManager;
	private int page;
	private String uid;
	private String uptime;
 	private boolean flag = false;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.xianshi_zhaoping_list, null);
		init();
		loadButton(view);
		getdata();
		lisentnerButton();
		return view;
	}

	private void getdata() {
		AsynJobInfo asynJobInfo = new AsynJobInfo();
		asynJobInfo.execute();
	}

	private void lisentnerButton() {
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
							jobManager
									.GetJobInfo(getActivity(), 1, uid, uptime);
							jobManager.getJobInfoIml(new IgetJobInfo() {

								@Override
								public void getJobInfo(List<JobBean> jobBeans) {
									if (jobBeans != null) {
										for (JobBean jobBean : jobBeans) {
											mList.addFirst(jobBean);
											flag = true;
										}
										MyUtlis.sortListJobBeanOrder(mList);
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
							jobManager
									.GetJobInfo(getActivity(), page, uid, "0");
							page++;
							jobManager.getJobInfoIml(new IgetJobInfo() {

								@Override
								public void getJobInfo(List<JobBean> jobBeans) {
									if (jobBeans != null) {
										for (JobBean jobBean : jobBeans) {
											mList.addLast(jobBean);
											
										}
										MyUtlis.sortListJobBeanOrder(mList);
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

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.setSeclection(position - 1);
				mAdapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.setClass(getActivity(), ZhaopinContentActivity.class);
				intent.putExtra("id", mList.get(position - 1).getId());
				startActivity(intent);

			}
		});
	}

	private void loadButton(View view) {
		title_bar_showleft = (ImageView) view
				.findViewById(R.id.title_bar_showleft);
		title_bar_publish = (ImageView) view
				.findViewById(R.id.title_bar_publish);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
	}

	private void init() {
		context = getActivity().getBaseContext();
		jobManager = new JobManager();
		mList = new LinkedList<JobBean>();
		uid = new String("0");
		uptime = new String("0");
		mAdapter = new MyJobAdapter(context, mList);
		page = 2;
	}

	public class AsynJobInfo extends AsyncTask<Void, Void, List<JobBean>> {

		public AsynJobInfo() {
		}

		@Override
		protected List<JobBean> doInBackground(Void... params) {
			return DbJobManager.getInstance(context).getListAll();
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
		protected void onPostExecute(List<JobBean> result) {
			if (result != null) {
				mList.addAll(result);
				MyUtlis.sortListJobBeanOrder(mList);
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

				ActivityZP.showLeft();
			}
		});
		title_bar_publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), FabuZhaopinActivity.class);
				startActivity(intent);
			}
		});

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		 if (flag) {//save the data to file
			//to json
				DbJobManager.getInstance(context).deleteAll();
			 for (int i = 0; i < mList.size(); i++) {
				if (i <= 20) {
					JobBean bean = mList.get(i);
					DbJobManager.getInstance(context).insert(bean);
				}
			}
		} 
		
	}
}
