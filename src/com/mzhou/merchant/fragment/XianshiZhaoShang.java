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
import com.mzhou.merchant.activity.FabuZhaoshangActivity;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.ActivityZS;
import com.mzhou.merchant.adapter.MyAttachAdapter;
import com.mzhou.merchant.dao.biz.AttactManager;
import com.mzhou.merchant.db.manager.DbAttachManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

public class XianshiZhaoShang extends Fragment {
	private ImageView title_bar_showleft;
	private ImageView title_bar_publish;
	// **************************
	private LinkedList<AttactBean> mList;

	private PullToRefreshListView mPullRefreshListView;
	private MyAttachAdapter mAdapter;
	private AttactManager attactManager;
	private int page = 2;
	private Context context;
	private String uptime;
	private boolean flag = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.xianshi_zhaoshang_list, null);
		init();
		loadButton(view);
		listenerbutton();
		getdata();
		return view;
	}

	private void getdata() {
		AsynAttact asynAttact = new AsynAttact();
		asynAttact.execute();
	 }

	private void listenerbutton() {
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (WebIsConnectUtil.showNetState(getActivity())) {
							String label = DateUtils.formatDateTime(
									getActivity().getBaseContext(),
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);
							 String uid = "0";
							 String usertype = "0";
							 
							UserInfoBean userInfoBean =  DbUserManager.getInstance(getActivity()).getLogingUserInfo();
							if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
								uid = userInfoBean.getUid();
							}
							if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
								usertype = userInfoBean.getUsertype();
							}
							attactManager.GetAttactInfo(getActivity(), 1, uid,
									uptime, MyConstants.ATTRACT_URL);
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
						if (WebIsConnectUtil.showNetState(getActivity())) {
							String label = DateUtils.formatDateTime(
									getActivity().getBaseContext(),
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);
							
							 String uid = "0";
							 String usertype = "0";
							 
							UserInfoBean userInfoBean =  DbUserManager.getInstance(getActivity()).getLogingUserInfo();
							if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
								uid = userInfoBean.getUid();
							}
							if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
								usertype = userInfoBean.getUsertype();
							}
							
							attactManager.GetAttactInfo(getActivity(), page,
									uid, "0", MyConstants.ATTRACT_URL);
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
		mPullRefreshListView.scrollTo(0, 0);
		mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
	}

	private void init() {
		attactManager = new AttactManager();
		mList = new LinkedList<AttactBean>();
		context = getActivity().getBaseContext();
		mAdapter = new MyAttachAdapter(context, mList);
	}

	public class AsynAttact extends AsyncTask<Void, Void, List<AttactBean>  >  {

		public AsynAttact() {

		}

		@Override
		protected LinkedList<AttactBean>  doInBackground(Void... params) {
			return DbAttachManager.getInstance(context).getAdByCategory("zhaoshang");
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
			ListView actualListView = mPullRefreshListView.getRefreshableView();
				if (productsBeans != null) {
					mList.addAll(productsBeans);
					MyUtlis.sortListAttactBeanOrder(mList);
					uptime = mList.get(0).getCtime();
					actualListView.setAdapter(mAdapter);
			} else {
				uptime = new String("11");
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
				ActivityZS.showLeft();
			}
		});
		title_bar_publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), FabuZhaoshangActivity.class);
				startActivity(intent);
			}
		});

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		 if (flag) {//save the data to file
			//to json
				DbAttachManager.getInstance(context).deleteByCategory("zhaoshang");
			 for (int i = 0; i < mList.size(); i++) {
				if (i <= 20) {
					AttactBean bean = mList.get(i);
					bean.setCategory("zhaoshang");
					DbAttachManager.getInstance(context).insert(bean);
				}
			}
		} 
		
	}
}
