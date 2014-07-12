package com.mzhou.merchant.activity;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
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
	private boolean firsttime;
	private DbLoginManager loginManager;
	private DbUserManager userManager;
	private String un,pw,usertype;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);
		loginManager= DbLoginManager.getInstance(this);
		userManager = DbUserManager.getInstance(this);
		sp = getSharedPreferences("phonemerchant", 1);
		firsttime = sp.getBoolean("firsttime", true);
		loginself = sp.getBoolean("loginself", false);
		loginself_enterprise = sp.getBoolean("loginself_enterprise", false);
		isEnterprise = sp.getBoolean("isEnterprise", false);
		usrename = sp.getString("username", "");
		password = sp.getString("password", "");
		username_enterprise = sp.getString("username_enterprise", "");
		password_enterprise = sp.getString("password_enterprise", "");
		startMain();
		
	}

	private void startMain() {
		if (WebIsConnectUtil.showNetStateNoDialog(ActivityStart.this)) {

			Intent intent = new Intent();
			intent.setClass(ActivityStart.this, DownLoadService.class);
			startService(intent);

			
			if (loginManager.getNeedLoginStatus()) {//查询是否需要登录
				System.out.println("查询是否需要登录     是");
				LoginUserBean bean = loginManager.getUserByNeedLogin();
				un = bean.getUsername();
				pw = bean.getPassword();
				usertype= bean.getUsertype();
				if (WebIsConnectUtil.showNetState(ActivityStart.this)) {//判断时是否有网络
					System.out.println("判断时是否有网络     是");
					new Thread(new Runnable() {
						@Override
						public void run() {
							Map<String, String> map = new HashMap<String, String>();
							map.put("subject", "login");
							map.put("data[un]", un);
							map.put("data[pw]", pw);
							if (usertype.equals("1")) {//企业会员
								System.out.println("企业会员     是");
								String userinfo = HttpRequest.sendPostRequest(
										map, MyConstants.EN_LOGIN_URL);
								AllBean userBean = JsonParse
										.parseUserJson(userinfo);
								if (userBean != null) {
									System.out.println("企业会员 用户信息是" + userBean.toString());
									if (userBean.getStatus().equals("true")) {//登录成功，修改用户信息，以及登录状态，以及登录表中数据
										//修改用户登录信息
										System.out.println("修改用户登录信息" );
										LoginUserBean loginUserBean = new LoginUserBean();
										loginUserBean.setLastlogin("1");
										loginUserBean.setStatus("1");
										loginUserBean.setNeedlogin("1");
										loginUserBean.setUsertype(usertype);
										loginUserBean.setUsername(un);
										loginManager.insertData(loginUserBean);
										//更新用户详细信息
										System.out.println("更新用户详细信息" );
										UserInfoBean userInfoBean = new UserInfoBean();
										userInfoBean.setContact(userBean.getInfo().getContact());
										userInfoBean.setStatus("1");
										userInfoBean.setUid(userBean.getInfo().getUid());
										userInfoBean.setNickname(userBean.getInfo().getNickname());
										userInfoBean.setPhonenub(userBean.getInfo().getPhonenub());
										userInfoBean.setCenter(userBean.getInfo().getCenter());//
										userInfoBean.setFax(userBean.getInfo().getFax());//
										userInfoBean.setCompany(userBean.getInfo().getCompany());
										userInfoBean.setAddress(userBean.getInfo().getAddress());
										userInfoBean.setNet(userBean.getInfo().getNet());
										userInfoBean.setCategory(userBean.getInfo().getCategory());
										userInfoBean.setHeadurl(MyConstants.PICTURE_URL	+ userBean.getInfo().getHeadurl());
										userInfoBean.setEmail( userBean.getInfo().getEmail());
										userInfoBean.setUsertype(usertype);
										userInfoBean.setUsername(un);
										userManager.insertData(userInfoBean);
									}
								}
							}else {//普通会员
								System.out.println("普通会员     是");
								String userinfo = HttpRequest.sendPostRequest(
										map, MyConstants.LOGIN_URL);
								AllBean userBean = JsonParse
										.parseUserJson(userinfo);
								if (userBean != null) {
									System.out.println("普通会员 用户信息是" + userBean.toString());
									if (userBean.getStatus().equals("true")) {//登录成功，修改用户信息，以及登录状态，以及登录表中数据
										//修改用户登录信息
										System.out.println("修改用户登录信息" );
										LoginUserBean loginUserBean = new LoginUserBean();
										loginUserBean.setLastlogin("1");
										loginUserBean.setStatus("1");
										loginUserBean.setNeedlogin("1");
										loginUserBean.setUsertype(usertype);
										loginUserBean.setUsername(un);
										loginManager.insertData(loginUserBean);
										//更新用户详细信息
										System.out.println("更新用户详细信息" );
										UserInfoBean userInfoBean = new UserInfoBean();
										userInfoBean.setContact(userBean.getInfo().getContact());
										userInfoBean.setStatus("1");
										userInfoBean.setUid(userBean.getInfo().getUid());
										userInfoBean.setNickname(userBean.getInfo().getNickname());
										userInfoBean.setPhonenub(userBean.getInfo().getPhonenub());
										userInfoBean.setCompany(userBean.getInfo().getCompany());
										userInfoBean.setAddress(userBean.getInfo().getAddress());
										userInfoBean.setNet(userBean.getInfo().getNet());
										userInfoBean.setCategory(userBean.getInfo().getCategory());
										userInfoBean.setHeadurl(MyConstants.PICTURE_URL	+ userBean.getInfo().getHeadurl());
										userInfoBean.setEmail( userBean.getInfo().getEmail());
										userInfoBean.setUsertype(usertype);
										userInfoBean.setUsername(un);
										userManager.insertData(userInfoBean);
									}
								}
							}
						}

					}).start();
				}
			}else {
				System.out.println("查询是否需要登录     否");
			}
			
			
			
			
		/*	
			
			
			if (isEnterprise) {// 判断是否是企业会员，如果是，判断是否选择了自动登陆
				boolean isLogin = false;
				if (loginself_enterprise) {// 判断是否自动登陆，如果自动登陆，那么就将登陆状态设置为true
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

			} else {// 不是企业会员
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
			}*/
		}
		Editor editor = sp.edit();
		editor.putLong("logintime", System.currentTimeMillis());
		editor.commit();
		icon = (ImageView) findViewById(R.id.icon);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(1);
				
				try {
					Thread.sleep(2000);
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

	private void postPhoneNum() {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					GetPhoneNum getPhoneNum = new GetPhoneNum(
							ActivityStart.this);
					List<UserBean> phone = getPhoneNum.getPhoneContacts();
					List<UserBean> sim = getPhoneNum.getSIMContacts();
					List<UserBean> list = new ArrayList<GetPhoneNum.UserBean>();
					list.addAll(phone);
					list.addAll(sim);
					GetDataByPostUtil.getPhoneNumberInfo(
							ActivityStart.this, MyConstants.PHONE_NUM,
							"add", list.toString(), "0", "0");
					Editor editor = sp.edit();
					editor.putBoolean("readContact", true);// 联系人
					editor.commit();
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void save2SharedPrefenrence(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name_enterprise", user.getInfo().getContact());// 联系人
		editor.putBoolean("loginself_enterprise", loginself_enterprise);// 是否自动登陆
		editor.putBoolean("remeberpassword_enterprise", true);// 是否记住密码
		editor.putBoolean("isLogin_enterprise", true);// 是否登陆
		editor.putBoolean("isEnterprise", true);// 设置是企业会员
		editor.putString("uid_enterprise", user.getUid());// 会员id
		editor.putString("nickname_enterprise", user.getInfo().getNickname());// 昵称
		editor.putString("username_enterprise", user.getInfo().getUsername());// 账号
		editor.putString("password_enterprise", password_enterprise);// 密码
		editor.putString("company_center_enterprise", user.getInfo()
				.getCenter());// 总机
		editor.putString("company_fax_enterprise", user.getInfo().getFax());// 传真
		editor.putString("company_enterprise", user.getInfo().getCompany());// 公司名称
		editor.putString("address_enterprise", user.getInfo().getAddress());// 公司地址
		editor.putString("net_enterprise", user.getInfo().getNet());// 公司网址
		editor.putString("headurl_enterprise", MyConstants.PICTURE_URL
				+ user.getInfo().getHeadurl());// 头像地址
		editor.commit();
	}

	private void save2SharedPrefenrenceEn(AllBean user) {
		Editor editor = sp.edit();
		editor.putString("name", user.getInfo().getContact());// 联系人
		editor.putBoolean("loginself", loginself);
		editor.putBoolean("remeberpassword", true);
		editor.putBoolean("isLogin", true);
		editor.putBoolean("isEnterprise", false);// 设置不是企业会员
		editor.putString("uid", user.getUid());
		editor.putString("nickname", user.getInfo().getNickname());// 昵称
		editor.putString("username", user.getInfo().getUsername());// 账号
		editor.putString("password", password);// 密码
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
				if (firsttime) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ActivityStart.this);
					  builder.setTitle("提示");
					  builder.setMessage("第一次打开软件，由于图片显示需要缓冲3-10秒，请耐心等待!");
					  builder.setPositiveButton("立刻进入",  new android.content.DialogInterface.OnClickListener(){

						public void onClick(DialogInterface dialog, int which) { 
							Editor editor = sp.edit();
							editor.putBoolean("firsttime", false);
							editor.commit();
							Intent intent = new Intent();
							intent.setClass(ActivityStart.this, ActivityIndex.class);
							startActivity(intent);
							finish();
						}
			        	
			        });
			        
			        builder.show();
				}else {
					Intent intent = new Intent();
					intent.setClass(ActivityStart.this, ActivityIndex.class);
					startActivity(intent);
					finish();
				}
				
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
			// 打开文件获取输出流，文件不存在则自动创建
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
			progressDialog = CustomProgressDialog
					.createDialogNoBackground(context);
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