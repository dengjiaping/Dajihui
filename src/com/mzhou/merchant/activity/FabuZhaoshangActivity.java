package com.mzhou.merchant.activity;

import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.AttactManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.utlis.MyConstants;
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

public class FabuZhaoshangActivity extends Activity {
	private ImageView title_bar_showleft;
	private Button fabuqiugou;
	private EditText content;
	private EditText phonenumber;
	private EditText name;
	private String contentStr;
	private String phonenumberStr;
	private String nameStr;
	private SharedPreferences sp;
	private AttactManager attactManager;
	private boolean isEnterprise;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_zhaoshang);
		init();
		loadbutton();
		setdata();
		listenerbutton();

	}

	private void listenerbutton() {
		fabuqiugou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				contentStr = content.getText().toString();
				phonenumberStr = phonenumber.getText().toString();
				nameStr = name.getText().toString();
				if (WebIsConnectUtil.showNetState(FabuZhaoshangActivity.this)) {
					if (isEnterprise) {
						attactManager.PubAttactInfo(FabuZhaoshangActivity.this,
								"1", contentStr, nameStr, phonenumberStr, sp.getString("uid_enterprise", "0"),
								MyConstants.ATTRACT_URL);
					} else {
						attactManager.PubAttactInfo(FabuZhaoshangActivity.this,
								"0", contentStr, nameStr, phonenumberStr, sp.getString("uid", "0"),
								MyConstants.ATTRACT_URL);
					}
					attactManager.getBackInfoIml(new IBackInfo() {

						@Override
						public void getBackAttactInfo(BackBean backBean) {

							if (backBean != null) {
								if (backBean.getStatus().equals("true")) {
									finish();
									Toast.makeText(FabuZhaoshangActivity.this,
											backBean.getMsg(),
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(FabuZhaoshangActivity.this,
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

	private void setdata() {
		if (isEnterprise) {
			name.setText(sp.getString("nickname", ""));
			phonenumber.setText(sp.getString("company_center_enterprise", ""));
		} else {
			name.setText(sp.getString("name", ""));
			phonenumber.setText(sp.getString("phonenub", ""));
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
		sp = getSharedPreferences("phonemerchant", 1);
		isEnterprise = sp.getBoolean("isEnterprise", false);
	}
}
