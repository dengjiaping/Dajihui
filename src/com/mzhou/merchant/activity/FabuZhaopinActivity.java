package com.mzhou.merchant.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.JobManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FabuZhaopinActivity extends Activity {
	private ImageView showLeft;
	private Button publish;

	private EditText display_job_station;
	private EditText display_job_require;
	private EditText pub_qiugou_name;
	private EditText display_job_contact;
	private EditText user_manager_qq;
	private EditText pub_qiugou_address;
	private EditText pub_qiugou_company;
	private TextView user_manager_category_stub;

	private ImageView category_postion;
	private LinearLayout user_manager_category;
	private List<Map<String, String>> moreList;
	private PopupWindow pwMyPopWindow;
	private ListView lvPopupList;
	private int NUM_OF_VISIBLE_LIST_ROWS = 9;
	private int postion = 0;
	private JobManager jobManager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_zhaopin);
		init();
		loadButton();
		listennerButton();
		iniPopupWindow();
		setdata();
	}

	private void setdata() {
		user_manager_category_stub.setText(moreList.get(0).get("share_key"));
		if (DbLoginManager.getInstance(this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(this).getLogingUserInfo();
			if (userInfoBean.getUsertype().equals("1")) {
				pub_qiugou_name.setText(userInfoBean.getNickname());
				user_manager_qq.setText(userInfoBean.getFax());
				display_job_contact.setText(userInfoBean.getCenter());
				pub_qiugou_address.setText(userInfoBean.getAddress());
				pub_qiugou_company.setText(userInfoBean.getCompany());
			}else {
				pub_qiugou_name.setText(userInfoBean.getContact());
				user_manager_qq.setText(userInfoBean.getEmail());
				display_job_contact
						.setText(userInfoBean.getPhonenub());
				pub_qiugou_address.setText(userInfoBean.getAddress());
				pub_qiugou_company.setText(userInfoBean.getCompany());
			}
		}else {
			pub_qiugou_name.setText("");
			user_manager_qq.setText("");
			display_job_contact
					.setText("");
			pub_qiugou_address.setText("");
			pub_qiugou_company.setText("");
		}
 
	}

	private void init() {
		jobManager = new JobManager();
		 
	}

	private void listennerButton() {
		user_manager_category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pwMyPopWindow.isShowing()) {
					pwMyPopWindow.dismiss();// 关闭
				} else {
					pwMyPopWindow.showAsDropDown(category_postion);
				}
			}
		});
		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		publish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				long mString = 0;
				if (postion == 0) {
					mString = MyUtlis.getdays(7);
				} else if (postion == 1) {
					mString = MyUtlis.getdays(14);
				} else if (postion == 2) {
					mString = MyUtlis.getdays(21);
				} else if (postion == 3) {
					mString = MyUtlis.getdays(30);
				} else if (postion == 4) {
					mString = MyUtlis.getdays(90);
				} else if (postion == 5) {
					mString = MyUtlis.getdays(180);
				} else if (postion == 6) {
					mString = MyUtlis.getdays(365);
				}
				String lasttime = String.valueOf(mString);
				String jobStation = MyUtlis.getEditString(display_job_station);
				String content = MyUtlis.getEditString(display_job_require);
				String contact = MyUtlis.getEditString(pub_qiugou_name);
				String email = MyUtlis.getEditString(user_manager_qq);
				String address = MyUtlis.getEditString(pub_qiugou_address);
				String phone = MyUtlis.getEditString(display_job_contact);
				String company = MyUtlis.getEditString(pub_qiugou_company);
				if (WebIsConnectUtil.showNetState(FabuZhaopinActivity.this)) {

					 String uid = "0";
					 String usertype = "0";
					 
					UserInfoBean userInfoBean =  DbUserManager.getInstance(FabuZhaopinActivity.this).getLogingUserInfo();
					if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
						uid = userInfoBean.getUid();
					}
					if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
						usertype = userInfoBean.getUsertype();
					}
					
					jobManager.PubJobInfo(FabuZhaopinActivity.this,uid,
							jobStation, content, contact, phone, email,
							company, address, lasttime);
					jobManager.getBackInfoIml(new IBackInfo() {
						@Override
						public void getBackAttactInfo(BackBean backBean) {
							if (backBean != null) {
								if (backBean.getStatus().equals("true")) {
									finish();
									Toast.makeText(FabuZhaopinActivity.this,
											backBean.getMsg(),
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(FabuZhaopinActivity.this,
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
		showLeft = (ImageView) findViewById(R.id.title_bar_showleft);

		publish = (Button) findViewById(R.id.publish);
		category_postion = (ImageView) findViewById(R.id.category_postion);
		user_manager_category_stub = (TextView) findViewById(R.id.user_manager_category_stub);
		user_manager_category = (LinearLayout) findViewById(R.id.user_manager_category);
		display_job_station = (EditText) findViewById(R.id.display_job_station);
		display_job_require = (EditText) findViewById(R.id.display_job_require);
		pub_qiugou_name = (EditText) findViewById(R.id.pub_qiugou_name);
		user_manager_qq = (EditText) findViewById(R.id.user_manager_qq);
		display_job_contact = (EditText) findViewById(R.id.display_job_contact);
		pub_qiugou_address = (EditText) findViewById(R.id.pub_qiugou_address);
		pub_qiugou_company = (EditText) findViewById(R.id.pub_qiugou_company);

	}

	private void iniPopupWindow() {
		moreList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		String[] array = getResources().getStringArray(R.array.limitdays);
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

		lvPopupList.setAdapter(new SimpleAdapter(FabuZhaopinActivity.this,
				moreList, R.layout.list_item_popupwindow,
				new String[] { "share_key" }, new int[] { R.id.tv_list_item }));
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				user_manager_category_stub.setText(moreList.get(position).get(
						"share_key"));
				postion = position;
				pwMyPopWindow.dismiss();
				Toast.makeText(FabuZhaopinActivity.this,
						moreList.get(position).get("share_key"),
						Toast.LENGTH_LONG).show();
			}
		});

		lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);
		pwMyPopWindow.setWidth(lvPopupList.getMeasuredWidth());
		pwMyPopWindow.setHeight((lvPopupList.getMeasuredHeight() - 10)
				* NUM_OF_VISIBLE_LIST_ROWS);
		pwMyPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.color.transparent));
		pwMyPopWindow.setOutsideTouchable(true);
	}

}
