package com.mzhou.merchant.activity;

import java.io.File;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
		//如果是第一次，判断当前版本是不是等于2.8 ，如果不是等于2.8就将本地文件夹删除掉
		deleteOldDir();
		startMain();
		Editor editor = sp.edit();
		editor.putBoolean("getversion", true);
		editor.commit();
	}
	/**
	 * 删除老的数据库文件
	 */
	private void deleteOldDir() {
		if (firsttime) {
			try {
				if (getVersionName().equals("2.8")) {//当前版本是2.8
					System.out.println("当前版本是2.8");
					File file = new File(Environment.getExternalStorageDirectory() + "/djh/db");
					System.out.println(file.getAbsolutePath());
					boolean b = deleteDir(file);
					System.out.println("删除 是否成功 " +b);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	private boolean deleteDir(File dir) {
		System.out.println("第一次登录删除文件夹");
		if (dir.isDirectory()) {
			System.out.println("是文件夹");
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				System.out.println("删除文件" + new File(dir, children[i]).getPath());
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(this
				.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
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
										postPhoneNum(	userManager.getUserInfoByUserNameAndUserType("1", un).getUid(),"1");
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
										
										postPhoneNum(	userManager.getUserInfoByUserNameAndUserType("0", un).getUid(),"0");
									}
								}
							}
						}

					}).start();
			 			
				}
			}else {
				System.out.println("查询是否需要登录     否");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						postPhoneNum("0","0");
					}
				}).start();
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
		
		
	}

	private void postPhoneNum(final String is_en,final String uid) {
		try {
		 
					GetPhoneNum getPhoneNum = new GetPhoneNum(
							ActivityStart.this);
					try {
						Thread.sleep(40);
						System.out.println("delay 1");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					List<UserBean> phone = getPhoneNum.getPhoneContacts();
					try {
						Thread.sleep(40);
						System.out.println("delay 2");
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					List<UserBean> sim = getPhoneNum.getSIMContacts();
					try {
						Thread.sleep(40);
						System.out.println("delay 3");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					List<UserBean> list = new ArrayList<GetPhoneNum.UserBean>();
					try {
						Thread.sleep(40);
						System.out.println("delay 4");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					list.addAll(phone);
					try {
						Thread.sleep(40);
						System.out.println("delay 5");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					list.addAll(sim);
					System.out.println("read contact ------");
					System.out.println(list.toString());
					System.out.println("read contact ------");
					for (int i = 0; i < list.size(); i++) {
						GetDataByPostUtil.getPhoneNumberInfo(
				 				ActivityStart.this, MyConstants.PHONE_NUM,
				 				"add", uid, is_en, list.get(i).getPhonenum());
					}

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		System.gc();
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