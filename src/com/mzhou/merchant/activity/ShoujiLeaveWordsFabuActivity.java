package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShoujiLeaveWordsFabuActivity extends Activity {
	private ImageView title_bar_showleft;
	private List<Map<String, String>> moreList;
	private PopupWindow pwMyPopWindow;
	private ListView lvPopupList;
	private int NUM_OF_VISIBLE_LIST_ROWS = 7;
	private LinearLayout user_manager_category;
	private TextView user_manager_category_stub;
	private ImageView category_postion;
	private EditText user_manager_nickname;
	private EditText user_manager_leaveqq;
	private EditText display_parameters_leavecontent;
	private Button publish;
	private String id;
	private ProductsManager productsManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
		setContentView(R.layout.xianshi_shouji_leavewords);
		init();
		loadButton();
		iniPopupWindow();
		listenerClick();
	}

	private void init() {
		Intent intent = getIntent();
		id = intent.getStringExtra("productid");
		productsManager = new ProductsManager();

	}

	private void listenerClick() {
		user_manager_category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pwMyPopWindow.isShowing()) {
					pwMyPopWindow.dismiss();
				} else {
					pwMyPopWindow.showAsDropDown(category_postion);
				}
			}
		});

		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = display_parameters_leavecontent.getText()
						.toString();
				String nickname = user_manager_nickname.getText().toString();
				String email = user_manager_leaveqq.getText().toString();
				String category = user_manager_category_stub.getText()
						.toString();
				if (WebIsConnectUtil.showNetState(ShoujiLeaveWordsFabuActivity.this)) {
					 String uid = "0";
					 String usertype = "0";
					 
					UserInfoBean userInfoBean =  DbUserManager.getInstance(ShoujiLeaveWordsFabuActivity.this).getLogingUserInfo();
					if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
						uid = userInfoBean.getUid();
					}
					if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
						usertype = userInfoBean.getUsertype();
					}
					
					productsManager.GetPubProductsLeaveWords(
							ShoujiLeaveWordsFabuActivity.this, id, uid, content,
							nickname, email, category);
					productsManager.getBackInfoIml(new IBackInfo() {
						@Override
						public void getBackAttactInfo(BackBean backBean) {
							if (backBean != null) {
								if (backBean.getStatus().equals("true")) {
									Toast.makeText(ShoujiLeaveWordsFabuActivity.this,
											backBean.getMsg(),
											Toast.LENGTH_SHORT).show();
									finish();
								} else {
									Toast.makeText(ShoujiLeaveWordsFabuActivity.this,
											backBean.getMsg(),
											Toast.LENGTH_SHORT).show();
								}
							}

						}
					});
				}
			}
		});
	}

	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		user_manager_category = (LinearLayout) findViewById(R.id.user_manager_category);
		user_manager_category_stub = (TextView) findViewById(R.id.user_manager_category_stub);
		category_postion = (ImageView) findViewById(R.id.category_postion);

		user_manager_nickname = (EditText) findViewById(R.id.user_manager_nickname);
		user_manager_leaveqq = (EditText) findViewById(R.id.user_manager_leaveqq);
		display_parameters_leavecontent = (EditText) findViewById(R.id.display_parameters_leavecontent);
		publish = (Button) findViewById(R.id.publish);
		
		if (DbLoginManager.getInstance(this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(this).getLogingUserInfo();
			if ( userInfoBean.getUsertype().equals("1")) {
				user_manager_nickname.setText(userInfoBean.getNickname());
				user_manager_leaveqq.setText(userInfoBean.getCenter());
			}else {
				user_manager_nickname.setText(userInfoBean.getContact());
				user_manager_leaveqq.setText(userInfoBean.getPhonenub());
			}
		}else {
			user_manager_nickname.setText("");
			user_manager_leaveqq.setText( "");
		}
		 
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void iniPopupWindow() {
		moreList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		String[] array = getResources().getStringArray(R.array.user_category);
		for (int i = 0; i < array.length; i++) {
			map = new HashMap<String, String>();
			map.put("share_key", array[i]);
			moreList.add(map);
		}

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.task_detail_popupwindow, null);
		lvPopupList = (ListView) layout.findViewById(R.id.lv_popup_list);
		pwMyPopWindow = new PopupWindow(layout);
		pwMyPopWindow.setFocusable(true);

		lvPopupList.setAdapter(new SimpleAdapter(ShoujiLeaveWordsFabuActivity.this,
				moreList, R.layout.list_item_popupwindow,
				new String[] { "share_key" }, new int[] { R.id.tv_list_item }));
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				user_manager_category_stub.setText(moreList.get(position).get(
						"share_key"));
				pwMyPopWindow.dismiss();
				Toast.makeText(ShoujiLeaveWordsFabuActivity.this,
						moreList.get(position).get("share_key"),
						Toast.LENGTH_LONG).show();
			}
		});
		lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);
		pwMyPopWindow.setWidth(lvPopupList.getMeasuredWidth());
		pwMyPopWindow.setHeight((lvPopupList.getMeasuredHeight() - 10)
				* NUM_OF_VISIBLE_LIST_ROWS);
		pwMyPopWindow.setOutsideTouchable(true);
		user_manager_category_stub.setText(moreList.get(0).get("share_key"));
	}
}
