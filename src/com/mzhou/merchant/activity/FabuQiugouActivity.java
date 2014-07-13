package com.mzhou.merchant.activity;

import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.AttactManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FabuQiugouActivity extends Activity {
	private ImageView title_bar_showleft;
	private Button fabuqiugou;
	private EditText content;
	private EditText phonenumber;
	private EditText name;

	private String contentStr;
	private String phonenumberStr;
	private String nameStr;

	private AttactManager attactManager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_qiugou);
		init();
		loadbutton();
		getdata();
		listenerbutton();

	}

	private void listenerbutton() {
		fabuqiugou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contentStr = MyUtlis.getEditString(content);
				phonenumberStr = MyUtlis.getEditString(phonenumber);
				nameStr = MyUtlis.getEditString(name);
				if (WebIsConnectUtil.showNetState(FabuQiugouActivity.this)) {
					 String uid = "0";
					 String usertype = "0";
					 
					UserInfoBean userInfoBean =  DbUserManager.getInstance(FabuQiugouActivity.this).getLogingUserInfo();
					if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
						uid = userInfoBean.getUid();
					}
					if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
						usertype = userInfoBean.getUsertype();
					}
						attactManager.PubAttactInfo(FabuQiugouActivity.this,
								usertype, contentStr, nameStr, phonenumberStr,  uid,
								MyConstants.PURCHASE_URL);
						 
					attactManager.getBackInfoIml(new IBackInfo() {

						@Override
						public void getBackAttactInfo(BackBean backBean) {
							if (backBean != null) {
								if (backBean.getStatus().equals("true")) {
									Toast.makeText(FabuQiugouActivity.this,
											backBean.getMsg(),
											Toast.LENGTH_SHORT).show();
									finish();
								} else {
									Toast.makeText(FabuQiugouActivity.this,
											backBean.getMsg(),
											Toast.LENGTH_SHORT).show();
								}

							}
						}
					});
				}
			}
		});
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void getdata() {
		if (DbLoginManager.getInstance(this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(this).getLogingUserInfo();
			if (userInfoBean.getUsertype().equals("1")) {
				name.setText(userInfoBean.getNickname());
				phonenumber.setText(userInfoBean.getCenter());
			}else {
				name.setText(userInfoBean.getContact());
				phonenumber.setText(userInfoBean.getPhonenub());
			}}else {
				phonenumber.setText("");
				name.setText( "");
			}
	}

	private void loadbutton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		content = (EditText) findViewById(R.id.pub_qiugou_content);
		phonenumber = (EditText) findViewById(R.id.pub_qiugou_number);
		name = (EditText) findViewById(R.id.pub_qiugou_name);
		fabuqiugou = (Button) findViewById(R.id.publish);
	}

	private void init() {
		attactManager = new AttactManager();
	}

}
