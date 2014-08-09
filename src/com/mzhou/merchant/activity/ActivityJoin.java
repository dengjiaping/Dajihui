package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.adapter.MyActivityMemberAdapter;
import com.mzhou.merchant.dao.IActivity.IgetActivityInfo;
import com.mzhou.merchant.dao.IActivity.IgetActivityMemberInfo;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.ActivityManager;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.ActivityMemberBean;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class ActivityJoin extends Activity {
	private EditText title_bar_huodong_join_name;
	private EditText title_bar_huodong_join_number;
	private ImageView title_bar_showleft;
	private Button publish;
	private PullToRefreshListView pull_refresh_list;
	private Context context;
	private ActivityManager manager;
	private LinkedList<ActivityMemberBean> memberBeans;
	private MyActivityMemberAdapter adapter;
	private int page ;
	private String id;
	
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
		setContentView(R.layout.xianshi_huodong_join);
		init();
		loadButton();
		getData();
		listenerButton();
	}
	private void init(){
		context = ActivityJoin.this;
		manager = new ActivityManager();
		
		memberBeans = new LinkedList<ActivityMemberBean>();
		adapter = new MyActivityMemberAdapter(context, memberBeans);
		id = getIntent().getStringExtra("id");
		page = 2;
	}
	private void loadButton(){
		title_bar_huodong_join_name = (EditText) findViewById(R.id.title_bar_huodong_join_name);
		title_bar_huodong_join_number = (EditText) findViewById(R.id.title_bar_huodong_join_number);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		publish = (Button) findViewById(R.id.publish);
		pull_refresh_list = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pull_refresh_list.setMode(Mode.PULL_FROM_END);
		pull_refresh_list.setScrollingWhileRefreshingEnabled(true);
		pull_refresh_list.scrollTo(0, 0);
	}
	private void getData(){
		manager.ActivityMemberInfo(context, id,1);
		manager.getMemberInfoIml(new IgetActivityMemberInfo() {
			
			@Override
			public void getActivityMemberInfo(
					String activityMemberBean) {
				
				List<ActivityMemberBean> activityMember = JsonParse
						.parseActivityMemberJson(activityMemberBean);
				if (activityMember != null) {
					for (int i = 0; i < activityMember.size(); i++) {
						memberBeans.addLast(activityMember.get(i));
						MyUtlis.sortActivityMemberOrder(memberBeans);
					}
					pull_refresh_list.setAdapter(adapter);
				}
				
			}
		});
	}
	 
	private void listenerButton(){
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 if (title_bar_huodong_join_name.getText().toString().equals("")) {
					 MyUtlis.toastInfo(context, getString(R.string.activity_name_null));
					return;
				}
				 if (title_bar_huodong_join_number.getText().toString().equals("")) {
					 MyUtlis.toastInfo(context, getString(R.string.activity_contact_null));
					 return;
				 }
				 final ActivityMemberBean activityb = new ActivityMemberBean();
				 activityb.setContact(title_bar_huodong_join_name.getText().toString());
				 activityb.setPhone(title_bar_huodong_join_number.getText().toString());
				 manager.JoinActivityInfo(context, id, title_bar_huodong_join_name.getText().toString(), title_bar_huodong_join_number.getText().toString());
				 manager.getBackInfoIml(new IBackInfo() {
					@Override
					public void getBackAttactInfo(BackBean backBean) {
						if (backBean != null) {
							MyUtlis.toastInfo(context, backBean.getMsg());
							if (backBean.getStatus().toString().equals("true")) {
								 memberBeans.addLast(activityb);
								 MyUtlis.sortActivityMemberOrder(memberBeans);
								 adapter.notifyDataSetChanged();
								 finish();
							}
						}
					}
				});
			}
		});
		
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		pull_refresh_list
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

					manager.ActivityMemberInfo(context, id,1);
					manager.getMemberInfoIml(new IgetActivityMemberInfo() {
						
						@Override
						public void getActivityMemberInfo(
								String activityMemberBean) {
							List<ActivityMemberBean> activityMember = JsonParse
									.parseActivityMemberJson(activityMemberBean);
							
							if (activityMember != null) {
								for (int i = 0; i < activityMember.size(); i++) {
									memberBeans.addFirst(activityMember.get(i));
								}
								MyUtlis.sortActivityMemberOrder(memberBeans);
								adapter.notifyDataSetChanged();
								pull_refresh_list.onRefreshComplete();
							}else {
								pull_refresh_list.onRefreshComplete();
							}
							
						}
					});
				
				} else {
					pull_refresh_list.onRefreshComplete();
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
					manager.ActivityMemberInfo(context, id,page);
					manager.getMemberInfoIml(new IgetActivityMemberInfo() {
						
						@Override
						public void getActivityMemberInfo(
								String activityMemberBean) {
							List<ActivityMemberBean> activityMember = JsonParse
									.parseActivityMemberJson(activityMemberBean);
							if (activityMember != null) {
								for (int i = 0; i < activityMember.size(); i++) {
									memberBeans.addLast(activityMember.get(i));
								}
								MyUtlis.sortActivityMemberOrder(memberBeans);
								adapter.notifyDataSetChanged();
								page ++;
							}
							pull_refresh_list.onRefreshComplete();
						}
					});
				
				
				} else {
					pull_refresh_list.onRefreshComplete();
				}
			}
		});
	}
}
