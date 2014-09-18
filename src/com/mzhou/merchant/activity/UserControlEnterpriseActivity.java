package com.mzhou.merchant.activity;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mzhou.merchant.dao.IBack.IUploadBackInfo;
import com.mzhou.merchant.dao.IUser.IgetQQBinder;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.GroupUsers;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.User;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.HttpMultipartPost;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class UserControlEnterpriseActivity extends Activity {
	private ImageView title_bar_showleft;// 返回
	private ImageView user_manager_user_head;// 头像
	private TextView product_already_push;// 底部栏已发布
	private TextView product_new_push;// 底部栏新发布

	private LinearLayout user_manager_nickname_stub;// 昵称
	private LinearLayout user_manager_userhead_stub;// 更换头像
	private LinearLayout user_manager_alter_passwd;// 更换密码
	private LinearLayout user_manager_count;// 不可更改账号
	private LinearLayout user_manager_name1;// 联系人---增加
	private LinearLayout user_manager_name2;// 联系人---增加
	private LinearLayout user_manager_name3;// 联系人---增加
	private LinearLayout user_manager_name4;// 联系人---增加
	private LinearLayout user_manager_name5;// 联系人---增加
	private LinearLayout user_manager_name6;// 联系人---增加
	private LinearLayout user_manager_name7;// 联系人---增加
	private LinearLayout user_manager_name8;// 联系人---增加
	private LinearLayout user_manager_name9;// 联系人---增加
	private LinearLayout user_manager_name10;// 联系人---增加
	private LinearLayout company_center_enterprise;// 总机
	private LinearLayout user_manager_centerFax;// 传真
	private LinearLayout user_manager_company;// 公司名称
	private LinearLayout user_manager_address;// 公司地址
	private LinearLayout user_manager_net;// 公司网址

	private Button save;// 保存
	private Context context;
	private TextView nicknameTextView;// 昵称
	private TextView user_manager_alter_passwd_stub;// 密码
	private TextView user_manager_alter_count;// 账号
	private TextView user_manager_tv_name1;// 联系人：？？
	private TextView user_manager_tv_name2;// 联系人：？？
	private TextView user_manager_tv_name3;// 联系人：？？
	private TextView user_manager_tv_name4;// 联系人：？？
	private TextView user_manager_tv_name5;// 联系人：？？
	private TextView user_manager_tv_name6;// 联系人：？？
	private TextView user_manager_tv_name7;// 联系人：？？
	private TextView user_manager_tv_name8;// 联系人：？？
	private TextView user_manager_tv_name9;// 联系人：？？
	private TextView user_manager_tv_name10;// 联系人：？？
	private TextView user_manager_tv_nub1;// 联系人：？？
	private TextView user_manager_tv_nub2;// 联系人：？？
	private TextView user_manager_tv_nub3;// 联系人：？？
	private TextView user_manager_tv_nub4;// 联系人：？？
	private TextView user_manager_tv_nub5;// 联系人：？？
	private TextView user_manager_tv_nub6;// 联系人：？？
	private TextView user_manager_tv_nub7;// 联系人：？？
	private TextView user_manager_tv_nub8;// 联系人：？？
	private TextView user_manager_tv_nub9;// 联系人：？？
	private TextView user_manager_tv_nub10;// 联系人：？？
	private TextView user_manager_centerNub_stub;// 总机
	private TextView user_manager_centerFax_stub;// 传真
	private TextView user_manager_tv_company;// 公司名称
	private TextView user_manager_tv_address;// 公司地址
	private TextView user_manager_tv_net;// 公司网址

	public static UserManager userManager = null;
//	private String uid_enterprise;
//	private String headurl_enterprise;
	private LinkedList<String> mList;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	private static final int REQUEST_CODE = 1100;
	private static final int REQUEST_CODE1 = 1101;
	private Uri mImageUri;
	private File file;
//	private boolean isBinder_enterprise;
//	private String openid_enterprise;
	private boolean pwchange = false;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";
	private boolean fromqq;
	private DbLoginManager dbLoginManager;
	private DbUserManager dbUserManager;
	private boolean isSave= false;
	private boolean binder= false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.user_manager_enterprise);
		init();
		loadButton();
		setData();
		listenerButton();

	}

	private void init() {
		context = UserControlEnterpriseActivity.this;
		dbLoginManager = DbLoginManager.getInstance(context);
		dbUserManager = DbUserManager.getInstance(context);
//		uid_enterprise = sp.getString("uid_enterprise", "0");
//		headurl_enterprise = sp.getString("headurl_enterprise", "");
//		isBinder_enterprise = sp.getBoolean("isBinder_enterprise", false);
//		openid_enterprise = sp.getString("openid_enterprise", "");
		Intent intent = getIntent();
		fromqq = intent.getBooleanExtra("fromqq", false);
		binder = intent.getBooleanExtra("binder", false);
		userManager = new UserManager();
		imageLoader = ImageLoader.getInstance();
		 
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.head_default)
		.showImageForEmptyUri(R.drawable.head_default)
		.showImageOnFail(R.drawable.head_default)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		mList = new LinkedList<String>();

		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		file = new File(saveDir, "temp_pic.jpg");
	}

	/**
*/
	private void loadButton() {
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		user_manager_user_head = (ImageView) findViewById(R.id.user_manager_user_head);
		product_already_push = (TextView) findViewById(R.id.product_already_push);
		product_new_push = (TextView) findViewById(R.id.product_new_push);

		user_manager_nickname_stub = (LinearLayout) findViewById(R.id.user_manager_nickname_stub);
		user_manager_userhead_stub = (LinearLayout) findViewById(R.id.user_manager_userhead_stub);
		user_manager_alter_passwd = (LinearLayout) findViewById(R.id.user_manager_alter_passwd);
		user_manager_count = (LinearLayout) findViewById(R.id.user_manager_count);
		user_manager_name1 = (LinearLayout) findViewById(R.id.user_manager_name1);
		user_manager_name2 = (LinearLayout) findViewById(R.id.user_manager_name2);
		user_manager_name3 = (LinearLayout) findViewById(R.id.user_manager_name3);
		user_manager_name4 = (LinearLayout) findViewById(R.id.user_manager_name4);
		user_manager_name5 = (LinearLayout) findViewById(R.id.user_manager_name5);
		user_manager_name6 = (LinearLayout) findViewById(R.id.user_manager_name6);
		user_manager_name7 = (LinearLayout) findViewById(R.id.user_manager_name7);
		user_manager_name8 = (LinearLayout) findViewById(R.id.user_manager_name8);
		user_manager_name9 = (LinearLayout) findViewById(R.id.user_manager_name9);
		user_manager_name10 = (LinearLayout) findViewById(R.id.user_manager_name10);

		company_center_enterprise = (LinearLayout) findViewById(R.id.user_manager_centerNub);
		user_manager_centerFax = (LinearLayout) findViewById(R.id.user_manager_centerFax);
		user_manager_address = (LinearLayout) findViewById(R.id.user_manager_address);
		user_manager_company = (LinearLayout) findViewById(R.id.user_manager_company);
		user_manager_net = (LinearLayout) findViewById(R.id.user_manager_net);

		nicknameTextView = (TextView) findViewById(R.id.user_manager_tv_nickname);
		user_manager_alter_passwd_stub = (TextView) findViewById(R.id.user_manager_alter_passwd_stub);
		user_manager_alter_count = (TextView) findViewById(R.id.user_manager_alter_count);
		user_manager_tv_name1 = (TextView) findViewById(R.id.user_manager_tv_name1);
		user_manager_tv_name2 = (TextView) findViewById(R.id.user_manager_tv_name2);
		user_manager_tv_name3 = (TextView) findViewById(R.id.user_manager_tv_name3);
		user_manager_tv_name4 = (TextView) findViewById(R.id.user_manager_tv_name4);
		user_manager_tv_name5 = (TextView) findViewById(R.id.user_manager_tv_name5);
		user_manager_tv_name6 = (TextView) findViewById(R.id.user_manager_tv_name6);
		user_manager_tv_name7 = (TextView) findViewById(R.id.user_manager_tv_name7);
		user_manager_tv_name8 = (TextView) findViewById(R.id.user_manager_tv_name8);
		user_manager_tv_name9 = (TextView) findViewById(R.id.user_manager_tv_name9);
		user_manager_tv_name10 = (TextView) findViewById(R.id.user_manager_tv_name10);
		user_manager_tv_name5 = (TextView) findViewById(R.id.user_manager_tv_name5);
		user_manager_tv_nub1 = (TextView) findViewById(R.id.user_manager_tv_nub1);
		user_manager_tv_nub2 = (TextView) findViewById(R.id.user_manager_tv_nub2);
		user_manager_tv_nub3 = (TextView) findViewById(R.id.user_manager_tv_nub3);
		user_manager_tv_nub4 = (TextView) findViewById(R.id.user_manager_tv_nub4);
		user_manager_tv_nub5 = (TextView) findViewById(R.id.user_manager_tv_nub5);
		user_manager_tv_nub6 = (TextView) findViewById(R.id.user_manager_tv_nub6);
		user_manager_tv_nub7 = (TextView) findViewById(R.id.user_manager_tv_nub7);
		user_manager_tv_nub8 = (TextView) findViewById(R.id.user_manager_tv_nub8);
		user_manager_tv_nub9 = (TextView) findViewById(R.id.user_manager_tv_nub9);
		user_manager_tv_nub10 = (TextView) findViewById(R.id.user_manager_tv_nub10);
		user_manager_centerNub_stub = (TextView) findViewById(R.id.user_manager_centerNub_stub);
		user_manager_centerFax_stub = (TextView) findViewById(R.id.user_manager_centerFax_stub);
		user_manager_tv_company = (TextView) findViewById(R.id.user_manager_tv_company);
		user_manager_tv_address = (TextView) findViewById(R.id.user_manager_tv_address);
		user_manager_tv_net = (TextView) findViewById(R.id.user_manager_tv_net);
		save = (Button) findViewById(R.id.save);
	}

	/**
	 * 设置返回来的值，用sharedPreference来去出来
	 */
	private void setData() {
		UserInfoBean userInfoBean = dbUserManager.getLogingUserInfo();
		imageLoader.displayImage(userInfoBean.getHeadurl(), user_manager_user_head, options);
		nicknameTextView.setText(userInfoBean.getNickname());
		user_manager_centerNub_stub.setText(userInfoBean.getCenter());
		user_manager_centerFax_stub.setText(userInfoBean.getFax());
		user_manager_tv_company.setText(userInfoBean.getCompany());
		user_manager_tv_address.setText(userInfoBean.getAddress());
		user_manager_tv_net.setText(userInfoBean.getNet());
		user_manager_alter_passwd_stub.setText(userInfoBean.getPassword());
		user_manager_alter_count.setText(userInfoBean.getUsername());
		String json_name = userInfoBean.getContact();

		setName(json_name);
		
	}

	/**
	 * 设置联系人的值
	 * 
	 * @param json_name
	 */
	private String nameString(GroupUsers groupUsers, int position) {
		if (groupUsers != null) {
			if (groupUsers.getUsers().size() - position > 0) {
				return groupUsers.getUsers().get(position).getName().toString();
			} else {
				return "";
			}

		}
		return "";
	}

	private String numberString(GroupUsers groupUsers, int position) {
		if (groupUsers != null) {
			if (groupUsers.getUsers().size() - position > 0) {
				return groupUsers.getUsers().get(position).getNumber()
						.toString();
			} else {
				return "";
			}

		}
		return "";
	}

	private void setName(String json_name) {
		System.out.println("setName===="+json_name);
		GroupUsers groupUsers = parseJson(json_name);
		if (groupUsers != null) {
			user_manager_tv_name1.setText(nameString(groupUsers, 0));
			user_manager_tv_name2.setText(nameString(groupUsers, 1));
			user_manager_tv_name3.setText(nameString(groupUsers, 2));
			user_manager_tv_name4.setText(nameString(groupUsers, 3));
			user_manager_tv_name5.setText(nameString(groupUsers, 4));
			user_manager_tv_name6.setText(nameString(groupUsers, 5));
			user_manager_tv_name7.setText(nameString(groupUsers, 6));
			user_manager_tv_name8.setText(nameString(groupUsers, 7));
			user_manager_tv_name9.setText(nameString(groupUsers, 8));
			user_manager_tv_name10.setText(nameString(groupUsers, 9));
			user_manager_tv_nub1.setText(numberString(groupUsers, 0));
			user_manager_tv_nub2.setText(numberString(groupUsers, 1));
			user_manager_tv_nub3.setText(numberString(groupUsers, 2));
			user_manager_tv_nub4.setText(numberString(groupUsers, 3));
			user_manager_tv_nub5.setText(numberString(groupUsers, 4));
			user_manager_tv_nub6.setText(numberString(groupUsers, 5));
			user_manager_tv_nub7.setText(numberString(groupUsers, 6));
			user_manager_tv_nub8.setText(numberString(groupUsers, 7));
			user_manager_tv_nub9.setText(numberString(groupUsers, 8));
			user_manager_tv_nub10.setText(numberString(groupUsers, 9));
		}
	}

	@Override
	protected void onActivityResult(int arg1, int arg0, Intent arg2) {
		super.onActivityResult(arg1, arg0, arg2);

		if (arg1 == REQUEST_CODE && arg0 == RESULT_OK) {
			if (mList != null) {
				mList.clear();
			}
			mImageUri = arg2.getData();
			if (mImageUri != null) {
				String path = getImagePath(mImageUri);
				mList.add(path);
				imageLoader.displayImage(
						mList.get(0).replace("/mnt", "file:/"),
						user_manager_user_head, options);
			}

		} else if (arg1 == REQUEST_CODE1 && arg0 == Activity.RESULT_OK) {
			if (mList != null) {
				mList.clear();
			}
			if (file != null && file.exists()) {
				mList.add(file.getPath());
				imageLoader.displayImage(
						mList.get(0).replace("/mnt", "file:/"),
						user_manager_user_head, options);
			}

		}

		if (arg0 == MyConstants.REQUEST_NICKNAME) {
			nicknameTextView.setText(arg2.getExtras().getString("nickname"));
		}
		if (arg0 == MyConstants.REQUEST_PASSWORD) {
			if (arg2.getExtras().getString("password") != null) {
				user_manager_alter_passwd_stub.setText(arg2.getExtras()
						.getString("password"));
				pwchange = true;
			}

		}
		if (arg0 == MyConstants.REQUEST_NAME) {
			setName(arg2.getExtras().getString("name"));
		}
		if (arg0 == MyConstants.REQUEST_PHONENUB) {
			user_manager_centerNub_stub.setText(arg2.getExtras().getString(
					"phonenub"));
		}
		if (arg0 == MyConstants.REQUEST_FAX) {
			user_manager_centerFax_stub.setText(arg2.getExtras().getString(
					"fax"));
		}
		if (arg0 == MyConstants.REQUEST_ADDRESS) {
			user_manager_tv_address.setText(arg2.getExtras().getString(
					"address"));
		}
		if (arg0 == MyConstants.REQUEST_NET) {
			user_manager_tv_net.setText(arg2.getExtras().getString("net"));
		}

		if (arg0 == MyConstants.REQUEST_COMPANY) {
			user_manager_tv_company.setText(arg2.getExtras().getString(
					"company"));
		}

	}

	/**
	 * 获得照片的绝对路径
	 * 
	 * @param uri
	 *            拍照或者选取照片返回的数据
	 * @return 返回字符串
	 */
	private String getImagePath(final Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
				uri, projection);
		cursor.moveToFirst();
		String path = cursor.getString(cursor
				.getColumnIndex(MediaStore.Images.Media.DATA));
		cursor.close();
		return path;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (pwchange) {//如果修改了密码需要重新登录
				dbLoginManager.updateStauts();
				dbUserManager.updateStauts();
			}
			if (fromqq && !isSave) {
				if (!binder) {
					dbLoginManager.updateStauts();
					dbUserManager.updateStauts();
				}
			}
			Intent intent = new Intent();
			intent.setClass(UserControlEnterpriseActivity.this,
					ActivityIndex.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 加载完了所有的Button之后，监听所有的Button \n退出当前activity的时候将所有的数据都保存起来 \n并且用多线程处理提交上去
	 */
	private void listenerButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fromqq && !isSave) {
					if (!binder) {
						dbLoginManager.updateStauts();
						dbUserManager.updateStauts();
					}
				}
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						ActivityIndex.class);
				startActivity(intent);
				finish();
			}
		});
		product_already_push.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						MyProductActivity.class);
				intent.putExtra("authstr", false);
				startActivity(intent);

			}
		});
		user_manager_count.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(UserControlEnterpriseActivity.this,
						getResources().getString(R.string.cannotchange),
						Toast.LENGTH_SHORT).show();
			}
		});
		product_new_push.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (fromqq) {
					//查询当前帐号是否绑定了qq
					LoginUserBean loginUserBean = dbLoginManager.getCurrentBean();
					if (loginUserBean != null && loginUserBean.getIsbinder() != null && loginUserBean.getIsbinder().equals("1")) {//当前用户登录是否绑定了qq
						System.out.println("当前用户登录绑定了qq");
//						if (isSave) {
							Intent intent = new Intent();
							intent.setClass(context, FabuShoujiEnterpriseActivity.class);
							startActivity(intent);
//						}else {
//							//先保存用户信息
//							save2Server();
//						}
						
					} else {
						showdialog();
					}

				}else {
					Intent intent = new Intent();
					intent.setClass(context, FabuShoujiEnterpriseActivity.class);
					startActivity(intent);
				}
			}
		});
		user_manager_user_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((mList != null) && (!mList.toString().equals("[]"))) {
					String[] imageUrls = new String[mList.size()];

					for (int i = 0; i < mList.size(); i++) {
						imageUrls[i] = mList.get(i);
					}
					Intent intent = new Intent();
					intent.setClass(UserControlEnterpriseActivity.this,
							PicPagerActivity.class);
					intent.putExtra(MyConstants.Extra.IMAGES, imageUrls);
					intent.putExtra(MyConstants.Extra.IMAGE_POSITION, 0);
					startActivity(intent);
				} else {
					String[] imageUrls = new String[1];
					imageUrls[0] = dbUserManager.getLogingUserInfo().getHeadurl();
					if (imageUrls != null) {
						Intent intent = new Intent();
						intent.setClass(UserControlEnterpriseActivity.this,
								PicPagerActivity.class);
						intent.putExtra(MyConstants.Extra.IMAGES, imageUrls);
						intent.putExtra(MyConstants.Extra.IMAGE_POSITION, 0);
						startActivity(intent);
					}
				}

			}
		});

		user_manager_nickname_stub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlNicknameActivity.class);
				intent.putExtra("nickname", nicknameTextView.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_NICKNAME);
			}
		});
		user_manager_userhead_stub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						UserControlEnterpriseActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(UserControlEnterpriseActivity.this);
				View view1 = inflater.inflate(R.layout.dialog_menu_view, null);
				Button capture = (Button) view1.findViewById(R.id.capture_pic);
				Button select = (Button) view1.findViewById(R.id.select_pic);
				Button cancel = (Button) view1.findViewById(R.id.cancel);
				view1.setMinimumWidth(2000);

				builder.setView(view1);
				final AlertDialog dialog = builder.create();
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				WindowManager.LayoutParams wmlp = dialog.getWindow()
						.getAttributes();
				wmlp.gravity = Gravity.BOTTOM;
				dialog.show();
				capture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

						String state = Environment.getExternalStorageState();
						if (state.equals(Environment.MEDIA_MOUNTED)) {
							file = new File(saveDir, System.currentTimeMillis()
									+ ".jpg");
							file.delete();
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							startActivityForResult(intent, REQUEST_CODE1);
						} else {
							MyUtlis.toastInfo(getBaseContext(), getResources()
									.getString(R.string.nosdcard));
						}

					}
				});
				select.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_PICK);
						intent.setType("image/*");
						startActivityForResult(intent, REQUEST_CODE);
						dialog.dismiss();

					}
				});
				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

			}
		});
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		user_manager_alter_passwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlAleterPasswdActivity.class);
				intent.putExtra("password", user_manager_alter_passwd_stub
						.getText().toString());
				startActivityForResult(intent, MyConstants.REQUEST_PASSWORD);
			}
		});
		user_manager_name1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_name10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlEnterpriseNameActivity.class);
				intent.putExtra("name", getJsonName());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		company_center_enterprise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlPhonenubActivity.class);
				intent.putExtra("phonnumber", user_manager_centerNub_stub
						.getText().toString());
				startActivityForResult(intent, MyConstants.REQUEST_PHONENUB);
			}
		});
		user_manager_centerFax.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlFaxActivity.class);
				intent.putExtra("qq", user_manager_centerFax_stub.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_FAX);

			}
		});
		user_manager_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlCompanyActivity.class);
				intent.putExtra("company", user_manager_tv_company.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_COMPANY);
			}
		});
		user_manager_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControladressActivity.class);
				intent.putExtra("address", user_manager_tv_address.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_ADDRESS);
			}
		});
		user_manager_net.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserControlEnterpriseActivity.this,
						UserControlNetActivity.class);
				intent.putExtra("net", user_manager_tv_net.getText().toString());
				startActivityForResult(intent, MyConstants.REQUEST_NET);
			}
		});
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				if (fromqq) {
					//查询当前帐号是否绑定了qq
					LoginUserBean loginUserBean = dbLoginManager.getCurrentBean();
					if (loginUserBean != null && loginUserBean.getIsbinder() != null && loginUserBean.getIsbinder().equals("1")) {//当前用户登录是否绑定了qq
						System.out.println("保存操作，当前用户登录绑定了qq");
						save2Server();
					} else {
						System.out.println("保存操作，当前用户未绑定qq");
						showdialog();
					}

				}else {
					System.out.println("正常保存");
					save2Server();
				}

			}

		

		});
	}
	private void save2Server() {
		if (WebIsConnectUtil
				.showNetState(UserControlEnterpriseActivity.this)) {
			String nickname = nicknameTextView.getText().toString();
			String center = user_manager_centerNub_stub.getText()
					.toString();
			String fax = user_manager_centerFax_stub.getText()
					.toString();
			String company = user_manager_tv_company.getText()
					.toString();
			String address = user_manager_tv_address.getText()
					.toString();
			String net = user_manager_tv_net.getText().toString();
			final String pw = user_manager_alter_passwd_stub.getText()
					.toString();
			final String json_name = getJsonName();

			String[] array = (String[]) mList.toArray(new String[mList
					.size()]);
			Map<String, String> params = new HashMap<String, String>();
			params.put("subject", "edit");
			String uid =dbUserManager.getLogingUserInfo().getUid();
		 
			if (uid != null && !uid.equals("") && !uid.equals("null")) {
				params.put("uid", dbUserManager.getLogingUserInfo().getUid());
			}else {
				Toast.makeText(context, "保存用户失败", Toast.LENGTH_LONG).show();
				return ;
			}
			params.put("data[nickname]", nickname);
			params.put("data[contact]", json_name);
			if (pwchange) {
				params.put("data[pw]", pw);
			}
			params.put("data[center]", center);
			params.put("data[fax]", fax);
			params.put("data[company]", company);
			params.put("data[address]", address);
			params.put("data[net]", net);
			HttpMultipartPost task = new HttpMultipartPost(
					UserControlEnterpriseActivity.this,
					MyConstants.EN_LOGIN_URL, array, params);
			task.execute();
			task.getBackInfoIml(new IUploadBackInfo() {

				@Override
				public void getBackAttactInfo(String json) {
					AllBean userBean = JsonParse.parseUserJson(json);
					if (userBean != null) {
						if (userBean.getStatus().equals("true")) {
							
							System.out.println("修改用户信息成功");
							if (fromqq) {//如果是从qq那边过来的，将保存设置未已经保存
								isSave = true;
							}
							//更新登录信息
							LoginUserBean loginUserBean = new LoginUserBean();
							if (pwchange) {
								loginUserBean.setPassword(pw);
							}
							loginUserBean.setUsername(user_manager_alter_count.getText().toString());
							loginUserBean.setUsertype("1");
							loginUserBean.setLastlogin("1");
							loginUserBean.setStatus("1");
							 
							dbLoginManager.updateByUserNameAndUserType(loginUserBean);
							System.out.println("更新用户信息");
							//更新用户信息
							UserInfoBean userInfoBean = new UserInfoBean();
							userInfoBean.setNickname(userBean
									.getInfo().getNickname());
							userInfoBean.setUsername(user_manager_alter_count.getText().toString());
							userInfoBean.setPassword(pw);
							userInfoBean.setContact( userBean.getInfo()
									.getContact());
							userInfoBean.setPhonenub(userBean.getInfo().getPhonenub());
							userInfoBean.setEmail(userBean.getInfo().getEmail());
							userInfoBean.setCompany(userBean.getInfo().getCompany());
							userInfoBean.setAddress(userBean.getInfo().getAddress());
							userInfoBean.setNet(userBean.getInfo().getNet());
							userInfoBean.setCenter(userBean.getInfo().getCenter());
							userInfoBean.setFax(userBean.getInfo().getFax());
							userInfoBean.setHeadurl(MyConstants.PICTURE_URL
									+ userBean.getInfo()
									.getHeadurl());
							userInfoBean.setCategory(userBean.getInfo().getCategory());
							userInfoBean.setUsertype("1");
								userInfoBean.setStatus("1");
							dbUserManager.insertData(userInfoBean);
							System.out.println("更新登录信息");
							
						}
						MyUtlis.toastInfo(getBaseContext(),
								userBean.getMsg());
					}
				}
			});
		}
	}
	/**
	 * 删除目录
	 * 
	 * @param dir
	 * @return
	 */
	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	private void showdialog() {
		final Dialog dialog = new Dialog(context);
		dialog.setTitle("绑定指定账号");
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.activity_binderdialog_en);
		dialog.show();
		final EditText un = (EditText) dialog
				.findViewById(R.id.user_login_username_hint);
		final EditText pw = (EditText) dialog
				.findViewById(R.id.user_login_password_hint);
		Button binderbtn = (Button) dialog.findViewById(R.id.binder_ok);
		Button binder_cancel = (Button) dialog.findViewById(R.id.binder_cancel);

		binderbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (un.getText().toString().equals("")
						&& pw.getText().toString().equals("")) {
					MyUtlis.toastInfo(context, "账号或密码为空！");
				} else {
					if (dbLoginManager.getCurrentBean().getOpenid() == null || dbLoginManager.getCurrentBean().getOpenid().equals("null") || dbLoginManager.getCurrentBean().getOpenid().equals("")) {
						MyUtlis.toastInfo(context, "openid为空！");
						return ; 

					}
					userManager.Binder(context, MyConstants.EN_LOGIN_URL,
							dbLoginManager.getCurrentBean().getOpenid(), un.getText().toString(), pw
									.getText().toString());
					userManager.getQQBinder(new IgetQQBinder() {

						@Override
						public void getBinderInfo(AllBean userBean) {
							if (userBean != null) {
								MyUtlis.toastInfo(context, userBean.getMsg()
										.toString());
								if (userBean.getStatus().equals("true")) {
									binder = true;
									user_manager_alter_passwd_stub.setText(pw
											.getText().toString());
									user_manager_alter_count.setText(un
											.getText().toString());
									user_manager_centerNub_stub
											.setText(userBean.getInfo()
													.getCenter());
									user_manager_centerFax_stub
											.setText(userBean.getInfo()
													.getFax());
									user_manager_tv_company.setText(userBean
											.getInfo().getCompany());
									user_manager_tv_address.setText(userBean
											.getInfo().getAddress());
									user_manager_tv_net.setText(userBean
											.getInfo().getNet());
									user_manager_alter_passwd_stub.setText(pw
											.getText().toString());
									user_manager_alter_count.setText(pw
											.getText().toString());
									String json_name = userBean.getInfo()
											.getContact();
									setName(json_name);
									
									//更新登录信息
									LoginUserBean loginUserBean = new LoginUserBean();
									loginUserBean.setPassword(pw.getText()
											.toString());
									loginUserBean.setUsername(un.getText()
											.toString());
									loginUserBean.setUsertype("1");
 					 				loginUserBean.setIsbinder("1");
 					 				loginUserBean.setLastlogin("1");
 					 				loginUserBean.setStatus("1");
									dbLoginManager.updateByStatus(loginUserBean);
									
									System.out.println("更新用户信息");
									//更新用户信息
									UserInfoBean userInfoBean = new UserInfoBean();
									userInfoBean.setNickname(userBean
											.getInfo().getNickname());
									userInfoBean.setPassword(pw.getText()
											.toString());
									userInfoBean.setUsername(un.getText()
											.toString());
									userInfoBean.setContact( userBean.getInfo()
											.getContact());
									userInfoBean.setPhonenub(userBean.getInfo().getPhonenub());
									userInfoBean.setEmail(userBean.getInfo().getEmail());
									userInfoBean.setCompany(userBean.getInfo().getCompany());
									userInfoBean.setAddress(userBean.getInfo().getAddress());
									userInfoBean.setNet(userBean.getInfo().getNet());
									userInfoBean.setFax(userBean.getInfo().getFax());
									userInfoBean.setCenter(userBean.getInfo().getCenter());
									userInfoBean.setUid(userBean.getUid());
									userInfoBean.setHeadurl(MyConstants.PICTURE_URL
											+ userBean.getInfo()
											.getHeadurl());
									userInfoBean.setCategory(userBean.getInfo().getCategory());
									userInfoBean.setUsertype("1");
									userInfoBean.setStatus("1");
									dbUserManager.updateByStatus(userInfoBean);
									System.out.println("更新登录信息");
								} else {
									Toast.makeText(context, "绑定失败,请重试!", Toast.LENGTH_LONG).show();
									 
								}
							}
						}
					});
					dialog.dismiss();
				}
			}
		});
		binder_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();

			}
		});
	}

	@Override
	protected void onDestroy() {
		if (file != null && file.exists() ) {
			file.delete();
			deleteDir(file);
		}
		File savePath = new File(saveDir);
		if (savePath.exists() && savePath != null) {
			savePath.delete();
			deleteDir(savePath);
		}

		super.onDestroy();
	}

	/**
	 * 生成Json名称字符串，序列化
	 * 
	 * @return
	 */
	private String getJsonName() {
		GroupUsers groupUsers = new GroupUsers();
		User user1 = new User();
		user1.setId(1L);
		user1.setName(user_manager_tv_name1.getText().toString());
		user1.setNumber(user_manager_tv_nub1.getText().toString());
		User user2 = new User();
		user2.setId(2L);
		user2.setName(user_manager_tv_name2.getText().toString());
		user2.setNumber(user_manager_tv_nub2.getText().toString());
		User user3 = new User();
		user3.setId(3L);
		user3.setName(user_manager_tv_name3.getText().toString());
		user3.setNumber(user_manager_tv_nub3.getText().toString());
		User user4 = new User();
		user4.setId(4L);
		user4.setName(user_manager_tv_name4.getText().toString());
		user4.setNumber(user_manager_tv_nub4.getText().toString());
		User user5 = new User();
		user5.setId(5L);
		user5.setName(user_manager_tv_name5.getText().toString());
		user5.setNumber(user_manager_tv_nub5.getText().toString());
		User user6 = new User();
		user6.setId(6L);
		user6.setName(user_manager_tv_name6.getText().toString());
		user6.setNumber(user_manager_tv_nub6.getText().toString());
		User user7 = new User();
		user7.setId(7L);
		user7.setName(user_manager_tv_name7.getText().toString());
		user7.setNumber(user_manager_tv_nub7.getText().toString());
		User user8 = new User();
		user8.setId(8L);
		user8.setName(user_manager_tv_name8.getText().toString());
		user8.setNumber(user_manager_tv_nub8.getText().toString());
		User user9 = new User();
		user9.setId(9L);
		user9.setName(user_manager_tv_name9.getText().toString());
		user9.setNumber(user_manager_tv_nub9.getText().toString());
		User user10 = new User();
		user10.setId(10L);
		user10.setName(user_manager_tv_name10.getText().toString());
		user10.setNumber(user_manager_tv_nub10.getText().toString());

		groupUsers.getUsers().add(user1);
		groupUsers.getUsers().add(user2);
		groupUsers.getUsers().add(user3);
		groupUsers.getUsers().add(user4);
		groupUsers.getUsers().add(user5);
		groupUsers.getUsers().add(user6);
		groupUsers.getUsers().add(user7);
		groupUsers.getUsers().add(user8);
		groupUsers.getUsers().add(user9);
		groupUsers.getUsers().add(user10);
		String json_name = JSON.toJSONString(groupUsers,
				SerializerFeature.WriteNullStringAsEmpty);
		return json_name;
	}

	/**
	 * 解析联系人Json数据
	 * 
	 * @param string
	 * @return
	 */
	private GroupUsers parseJson(String string) {
		try {
			if (string != null && !string.equals("") && !string.equals("[]")) {
				GroupUsers groupUsers = JSON.parseObject(string, GroupUsers.class);
				return groupUsers;
			}
			
		} catch (Exception e) {
			return null;
		}
		return null;
	}
}