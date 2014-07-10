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
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.activity.FabuQiugouActivity;
import com.mzhou.merchant.activity.ActivityQG;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.adapter.MyAttachAdapter;
import com.mzhou.merchant.dao.biz.AttactManager;
import com.mzhou.merchant.db.manager.DbAttachManager;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

public class XianshiQiuGou extends Fragment {
	private ImageView title_bar_showleft;
	private ImageView title_bar_publish;
	private LinkedList<AttactBean> mList;
	private PullToRefreshListView mPullRefreshListView;
	private MyAttachAdapter mAdapter;
	private AttactManager attactManager;
	private int page = 2;
	private String uid;
	private Context context;
	private String uptime;
	private boolean flag = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.xianshi_qiugou_list, null);
		init();
		loadButton(view);
		lisentnerButton();
		getdata();
		return view;
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

						String label = DateUtils.formatDateTime(getActivity()
								.getBaseContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(getActivity())) {
							attactManager.GetAttactInfo(getActivity(), 1, uid,
									uptime, MyConstants.PURCHASE_URL);
							attactManager
									.getAttactInfoIml(new com.mzhou.merchant.dao.IAttact.IgetAttactInfo() {

										@Override
										public void getAttactInfo(
												List<AttactBean> attactBeans) {
											if (attactBeans != null) {
												for (AttactBean attactBean : attactBeans) {
													mList.addFirst(attactBean);
													flag = true;
												}
												MyUtlis.sortListAttactBeanOrder(mList);
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
						String label = DateUtils.formatDateTime(getActivity()
								.getBaseContext(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (WebIsConnectUtil.showNetState(getActivity())) {
							attactManager.GetAttactInfo(getActivity(), page,
									uid, "0", MyConstants.PURCHASE_URL);
							attactManager
									.getAttactInfoIml(new com.mzhou.merchant.dao.IAttact.IgetAttactInfo() {

										@Override
										public void getAttactInfo(
												List<AttactBean> attactBeans) {
											if (attactBeans != null) {
												for (AttactBean attactBean : attactBeans) {
													mList.addLast(attactBean);
													
												}
												MyUtlis.sortListAttactBeanOrder(mList);
												mAdapter.notifyDataSetChanged();
												mPullRefreshListView
														.onRefreshComplete();
											} else {
												mPullRefreshListView
														.onRefreshComplete();
											}
											page++;
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
		title_bar_publish = (ImageView) view
				.findViewById(R.id.title_bar_publish);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshListView.scrollTo(0, 0);
	}

	private void init() {
		uid = new String("0");
		uptime = new String("0");
		context = getActivity().getBaseContext();
		mList = new LinkedList<AttactBean>();
		attactManager = new AttactManager();
		mAdapter = new MyAttachAdapter(context, mList);
	}

	public class AsynAttact extends AsyncTask<Void, Void, List<AttactBean> > {

		public AsynAttact() {

		}

		@Override
		protected LinkedList<AttactBean> doInBackground(Void... params) {
			return DbAttachManager.getInstance(context).getAdByCategory("qiugou");
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
		protected void onPostExecute(List<AttactBean> productsBeans) {
				if (productsBeans != null) {
					ListView actualListView = mPullRefreshListView
							.getRefreshableView();
					mList.addAll(productsBeans);
					MyUtlis.sortListAttactBeanOrder(mList);
					uptime = mList.get(0).getCtime();
					actualListView.setAdapter(mAdapter);
			}

			super.onPostExecute(productsBeans);
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ActivityQG.showLeft();
			}
		});
		title_bar_publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), FabuQiugouActivity.class);
				startActivity(intent);
			}
		});

	}
 
	@Override
	public void onDestroy() {
		super.onDestroy();
		 if (flag) {//save the data to file
			//to json
				DbAttachManager.getInstance(context).deleteByCategory("qiugou");
			 for (int i = 0; i < mList.size(); i++) {
				if (i <= 20) {
					AttactBean bean = mList.get(i);
					bean.setCategory("qiugou");
					DbAttachManager.getInstance(context).insert(bean);
				}
			}
		} 
		
	}
	 
}
