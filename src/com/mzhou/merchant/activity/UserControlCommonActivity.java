package com.mzhou.merchant.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mzhou.merchant.dao.IBack.IUploadBackInfo;
import com.mzhou.merchant.dao.IUser.IgetQQBinder;
import com.mzhou.merchant.dao.biz.UserManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.LoginUserBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.utlis.HttpMultipartPost;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
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
import android.view.Window;
import android.view.WindowManager;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class UserControlCommonActivity extends Activity {
	private ImageView title_bar_showleft;
	private ImageView user_manager_user_head;
	private ImageView category_postion;
	private TextView product_already_push;// 底部栏已发布
	private TextView product_new_push;// 底部栏新发布
	private boolean pwchange = false;
	private LinearLayout user_manager_category;
	private LinearLayout user_manager_nickname_stub;
	private LinearLayout user_manager_userhead_stub;
	private LinearLayout user_manager_alter_passwd;
	private LinearLayout user_manager_name;
	private LinearLayout user_manager_phonnumber;
	private LinearLayout user_manager_qq;
	private LinearLayout user_manager_company;
	private LinearLayout user_manager_address;
	private LinearLayout user_manager_net;
	private LinearLayout user_manager_count;
	private Button save;

	private TextView user_manager_category_stub;
	private TextView nicknameTextView;
	private TextView user_manager_tv_name;
	private TextView user_manager_tv_phonnumber;
	private TextView user_manager_tv_qq;
	private TextView user_manager_tv_company;
	private TextView user_manager_tv_address;
	private TextView user_manager_tv_net;
	private TextView user_manager_alter_passwd_stub;
	private TextView user_manager_alter_count;

	private List<Map<String, String>> moreList;
	private PopupWindow pwMyPopWindow;
	private ListView lvPopupList;
	private int NUM_OF_VISIBLE_LIST_ROWS = 10;

	public static UserManager userManager = null;
	private LinkedList<String> mList;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	private static final int REQUEST_CODE = 1100;
	private static final int REQUEST_CODE1 = 1101;
	private Uri mImageUri;
	private File file;
	private Context context;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_image";
	private boolean fromqq;
	private DbLoginManager dbLoginManager;
	private DbUserManager dbUserManager;
 	private boolean isSave= false;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_manager_common);
		init();
		loadButton();
		iniPopupWindow();
		setData();
		listenerButton();

	}

	private void init() {
		dbLoginManager = DbLoginManager.getInstance(context);
		dbUserManager = DbUserManager.getInstance(context);
		Intent intent1 = getIntent();
		fromqq = intent1.getBooleanExtra("fromqq", false);

		context = UserControlCommonActivity.this;
		userManager = new UserManager();
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.head_default)
				.showImageForEmptyUri(R.drawable.head_default)
				.showImageOnFail(R.drawable.head_default).cacheInMemory()
				.cacheOnDisc().delayBeforeLoading(0)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		mList = new LinkedList<String>();

		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		file = new File(saveDir, "temp_pic.jpg");
		/*if (!isLogin) {
			Intent intent = new Intent();
			intent.setClass(context, ActivityLogin.class);
			startActivity(intent);
			finish();
		}*/
	}

	/**
	 * 找到所有的id，加载所有的Button
	 */
	private void loadButton() {
		nicknameTextView = (TextView) findViewById(R.id.user_manager_tv_nickname);
		user_manager_tv_name = (TextView) findViewById(R.id.user_manager_tv_name);
		user_manager_tv_phonnumber = (TextView) findViewById(R.id.user_manager_tv_phonnumber);
		user_manager_tv_qq = (TextView) findViewById(R.id.user_manager_tv_qq);
		user_manager_tv_company = (TextView) findViewById(R.id.user_manager_tv_company);
		user_manager_tv_address = (TextView) findViewById(R.id.user_manager_tv_address);
		user_manager_tv_net = (TextView) findViewById(R.id.user_manager_tv_net);
		user_manager_alter_passwd_stub = (TextView) findViewById(R.id.user_manager_alter_passwd_stub);
		user_manager_alter_count = (TextView) findViewById(R.id.user_manager_alter_count);

		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		user_manager_user_head = (ImageView) findViewById(R.id.user_manager_user_head);
		product_new_push = (TextView) findViewById(R.id.product_new_push);
		user_manager_category = (LinearLayout) findViewById(R.id.user_manager_category);
		user_manager_nickname_stub = (LinearLayout) findViewById(R.id.user_manager_nickname_stub);
		user_manager_userhead_stub = (LinearLayout) findViewById(R.id.user_manager_userhead_stub);
		user_manager_alter_passwd = (LinearLayout) findViewById(R.id.user_manager_alter_passwd);
		user_manager_name = (LinearLayout) findViewById(R.id.user_manager_name);
		user_manager_phonnumber = (LinearLayout) findViewById(R.id.user_manager_phonnumber);
		user_manager_qq = (LinearLayout) findViewById(R.id.user_manager_qq);
		user_manager_address = (LinearLayout) findViewById(R.id.user_manager_address);
		user_manager_company = (LinearLayout) findViewById(R.id.user_manager_company);
		user_manager_net = (LinearLayout) findViewById(R.id.user_manager_net);
		product_already_push = (TextView) findViewById(R.id.product_already_push);
		user_manager_count = (LinearLayout) findViewById(R.id.user_manager_count);
		save = (Button) findViewById(R.id.save);

		category_postion = (ImageView) findViewById(R.id.category_postion);
		user_manager_category_stub = (TextView) findViewById(R.id.user_manager_category_stub);
	}

	/**
	 * 设置返回来的值，用sharedPreference来去出来
	 */
	private void setData() {
		UserInfoBean userInfoBean = dbUserManager.getLogingUserInfo();
		imageLoader.displayImage(userInfoBean.getHeadurl(), user_manager_user_head, options);
		nicknameTextView.setText(userInfoBean.getNickname());
		user_manager_tv_name.setText(userInfoBean.getContact());
		user_manager_tv_phonnumber.setText(userInfoBean.getPhonenub());
		user_manager_tv_qq.setText(userInfoBean.getEmail());
		user_manager_tv_company.setText(userInfoBean.getCompany());
		user_manager_tv_address.setText(userInfoBean.getAddress());
		user_manager_tv_net.setText(userInfoBean.getNet());
		user_manager_category_stub.setText(userInfoBean.getCategory());
		user_manager_alter_passwd_stub.setText(userInfoBean.getPassword());
		user_manager_alter_count.setText(userInfoBean.getUsername());
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
			user_manager_tv_name.setText(arg2.getExtras().getString("name"));
		}
		if (arg0 == MyConstants.REQUEST_PHONENUB) {
			user_manager_tv_phonnumber.setText(arg2.getExtras().getString(
					"phonenub"));
		}
		if (arg0 == MyConstants.REQUEST_EMAIL) {
			user_manager_tv_qq.setText(arg2.getExtras().getString("email"));
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
	/*		if (!isBinder) {
				Editor editor = sp.edit();
				editor.putBoolean("isLogin", false);
				editor.commit();
			}*/
				if (pwchange) {//如果修改了密码需要重新登录
					dbLoginManager.updateStauts();
					dbUserManager.updateStauts();
				}
				if (fromqq && !isSave) {
					dbLoginManager.updateStauts();
					dbUserManager.updateStauts();
				}
			Intent intent = new Intent();
			intent.setClass(context, ActivityIndex.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 加载完了所有的Button之后，监听所有的Button \n退出当前activity的时候将所有的数据都保存起来 \n并且用多线程处理提交上去
	 */
	private void listenerButton() {
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		title_bar_showleft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fromqq && !isSave) {
					dbLoginManager.updateStauts();
					dbUserManager.updateStauts();
				}
				Intent intent = new Intent();
				intent.setClass(context, ActivityIndex.class);
				startActivity(intent);
				finish();
			}
		});

		product_already_push.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, MyProductActivity.class);
				intent.putExtra("authstr", false);
				startActivity(intent);
			}
		});
		user_manager_count.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context,
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
						if (isSave) {
							Intent intent = new Intent();
							intent.setClass(context, FabuShoujiCommenActivity.class);
							startActivity(intent);
						}else {
							//先保存用户信息
							save2Server();
						}
						
					} else {
						showdialog();
					}

				}else {
					Intent intent = new Intent();
					intent.setClass(context, FabuShoujiCommenActivity.class);
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
					intent.setClass(context, PicPagerActivity.class);
					intent.putExtra(MyConstants.Extra.IMAGES, imageUrls);
					intent.putExtra(MyConstants.Extra.IMAGE_POSITION, 0);
					startActivity(intent);
				} else {
					String[] imageUrls = new String[1];
					imageUrls[0] = dbUserManager.getLogingUserInfo().getHeadurl();
					if (imageUrls != null) {
						Intent intent = new Intent();
						intent.setClass(context, PicPagerActivity.class);
						intent.putExtra(MyConstants.Extra.IMAGES, imageUrls);
						intent.putExtra(MyConstants.Extra.IMAGE_POSITION, 0);
						startActivity(intent);
					}
				}

			}
		});

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

		user_manager_nickname_stub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlNicknameActivity.class);
				intent.putExtra("nickname", nicknameTextView.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_NICKNAME);
			}
		});
		user_manager_userhead_stub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						UserControlCommonActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(UserControlCommonActivity.this);
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
							MyUtlis.toastInfo(context, getResources()
									.getString(R.string.no_sdcard));
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

		user_manager_alter_passwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlAleterPasswdActivity.class);
				intent.putExtra("password", user_manager_alter_passwd_stub
						.getText().toString());
				startActivityForResult(intent, MyConstants.REQUEST_PASSWORD);
			}
		});
		user_manager_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlNameActivity.class);
				intent.putExtra("name", user_manager_tv_name.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_NAME);
			}
		});
		user_manager_phonnumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlPhonenubActivity.class);
				intent.putExtra("phonnumber", user_manager_tv_phonnumber
						.getText().toString());
				startActivityForResult(intent, MyConstants.REQUEST_PHONENUB);
			}
		});
		user_manager_qq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlQQActivity.class);
				intent.putExtra("qq", user_manager_tv_qq.getText().toString());
				startActivityForResult(intent, MyConstants.REQUEST_EMAIL);
			}
		});
		user_manager_company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlCompanyActivity.class);
				intent.putExtra("company", user_manager_tv_company.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_COMPANY);
			}
		});
		user_manager_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControladressActivity.class);
				intent.putExtra("address", user_manager_tv_address.getText()
						.toString());
				startActivityForResult(intent, MyConstants.REQUEST_ADDRESS);
			}
		});
		user_manager_net.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, UserControlNetActivity.class);
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

	private void save2Server() {
		if (WebIsConnectUtil
				.showNetState(UserControlCommonActivity.this)) {

			String nickname = nicknameTextView.getText().toString();
			String phonenub = user_manager_tv_phonnumber.getText()
					.toString();
			String net = user_manager_tv_net.getText().toString();
			final String pw = user_manager_alter_passwd_stub.getText()
					.toString();
			String email = user_manager_tv_qq.getText().toString();
			String company = user_manager_tv_company.getText()
					.toString();
			String address = user_manager_tv_address.getText()
					.toString();
			String contact = user_manager_tv_name.getText().toString();
			String category = user_manager_category_stub.getText()
					.toString();

			String[] array = (String[]) mList.toArray(new String[mList
					.size()]);
			Map<String, String> params = new HashMap<String, String>();
			 
			String uid =dbUserManager.getLogingUserInfo().getUid();
			if (uid == null) {
				Toast.makeText(context, "保存用户失败", Toast.LENGTH_LONG).show();
				return ;
			}
			if (!uid.equals("") && !uid.equals("null")) {
				params.put("uid", dbUserManager.getLogingUserInfo().getUid());
			}else {
				Toast.makeText(context, "保存用户失败", Toast.LENGTH_LONG).show();
				return ;
			}
			params.put("subject", "edit");
			params.put("data[category]", category);
			params.put("data[nickname]", nickname);
			params.put("data[contact]", contact);
			params.put("data[pw]", pw);
			params.put("data[phonenub]", phonenub);
			params.put("data[email]", email);
			params.put("data[company]", company);
			params.put("data[address]", address);
			params.put("data[net]", net);
			HttpMultipartPost task = new HttpMultipartPost(context,
					MyConstants.LOGIN_URL, array, params);
			task.execute();
			task.getBackInfoIml(new IUploadBackInfo() {

				@Override
				public void getBackAttactInfo(String json) {

					AllBean userBean = JsonParse.parseUserJson(json);
						if (userBean != null && userBean.getStatus().equals("true")) {
							// Log.i("print", "userbing--------->" +
							// userBean.toString());
							System.out.println("修改用户信息成功");
							if (fromqq) {//如果是从qq那边过来的，将保存设置未已经保存
								isSave = true;
							}
							//更新登录信息
							LoginUserBean loginUserBean = new LoginUserBean();
							loginUserBean.setPassword(pw);
							loginUserBean.setUsername(user_manager_alter_count.getText().toString());
							loginUserBean.setUsertype("0");
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
							userInfoBean.setHeadurl(MyConstants.PICTURE_URL
									+ userBean.getInfo()
									.getHeadurl());
							userInfoBean.setCategory(userBean.getInfo().getCategory());
							userInfoBean.setUsertype("0");
							userInfoBean.setStatus("1");
						 
							dbUserManager.insertData(userInfoBean);
							System.out.println("更新登录信息");
						
						 
						}
						MyUtlis.toastInfo(getBaseContext(),
								userBean.getMsg());
					}
			});

		}
	}
	private void showdialog() {
		final Dialog dialog = new Dialog(UserControlCommonActivity.this);
		dialog.setTitle("绑定一个邮箱账号");
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.activity_binderdialog);
		dialog.show();
		final EditText un = (EditText) dialog
				.findViewById(R.id.user_login_username_hint);
		final EditText pw = (EditText) dialog
				.findViewById(R.id.user_login_password_hint);
		Button binder = (Button) dialog.findViewById(R.id.binder_ok);
		Button binder_cancel = (Button) dialog.findViewById(R.id.binder_cancel);
		binder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (un.getText().toString().equals("")
						|| pw.getText().toString().equals("")) {
					MyUtlis.toastInfo(context, "账号或密码为空！");
				} else {
					if (dbLoginManager.getCurrentBean().getOpenid() == null || dbLoginManager.getCurrentBean().getOpenid().equals("null") || dbLoginManager.getCurrentBean().getOpenid().equals("")) {
						MyUtlis.toastInfo(context, "openid为空！");
						return ;
					}
					userManager.Binder(context, MyConstants.LOGIN_URL, dbLoginManager.getCurrentBean().getOpenid(),
							un.getText().toString(), pw.getText().toString());
					userManager.getQQBinder(new IgetQQBinder() {
						@Override
						public void getBinderInfo(AllBean userBean) {
							if (userBean != null) {
								MyUtlis.toastInfo(context, userBean.getMsg()
										.toString());
								if (userBean.getStatus().equals("true")) {
									Log.i("print", userBean.toString());
									user_manager_alter_count.setText(un
											.getText().toString());
									user_manager_alter_passwd_stub.setText(pw
											.getText().toString());
									user_manager_tv_name.setText(userBean
											.getInfo().getContact());
									user_manager_tv_phonnumber.setText(userBean
											.getInfo().getPhonenub());
									user_manager_tv_qq.setText(userBean
											.getInfo().getEmail());
									user_manager_tv_company.setText(userBean
											.getInfo().getCompany());
									user_manager_tv_address.setText(userBean
											.getInfo().getAddress());
									user_manager_tv_net.setText(userBean
											.getInfo().getNet());
									user_manager_category_stub.setText(userBean
											.getInfo().getCenter());
									//更新登录信息
									LoginUserBean loginUserBean = new LoginUserBean();
									loginUserBean.setPassword(pw.getText()
											.toString());
									loginUserBean.setUsername(un.getText()
											.toString());
									loginUserBean.setUsertype("0");
 					 				loginUserBean.setIsbinder("1");
 					 				loginUserBean.setLastlogin("1");
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
									userInfoBean.setUid(userBean.getUid());
									userInfoBean.setHeadurl(MyConstants.PICTURE_URL
											+ userBean.getInfo()
											.getHeadurl());
									userInfoBean.setCategory(userBean.getInfo().getCategory());
									userInfoBean.setUsertype("0");
									dbUserManager.updateByStatus(userInfoBean);
									System.out.println("更新登录信息");
//									isBinder = true;
								} else {
									Toast.makeText(context, "绑定失败,请重试!", Toast.LENGTH_LONG).show();
//									Editor editor = sp.edit();
//									editor.putBoolean("isBinder", false);
//									editor.commit();
//									isBinder = false;
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
		if (file.exists() && file != null) {
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
	 * 用户保存头像
	 * 
	 * @author user
	 * 
	 */

	private void iniPopupWindow() {

		moreList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		String[] array = getResources().getStringArray(R.array.user_category);
		for (int i = 0; i < array.length; i++) {
			map = new HashMap<String, String>();
			map.put("share_key", array[i]);
			moreList.add(map);
		}

		LayoutInflater inflater = (LayoutInflater) getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.task_detail_popupwindow, null);

		lvPopupList = (ListView) layout.findViewById(R.id.lv_popup_list);
		pwMyPopWindow = new PopupWindow(layout);
		pwMyPopWindow.setFocusable(true);
		lvPopupList.setAdapter(new SimpleAdapter(this, moreList,
				R.layout.list_item_popupwindow, new String[] { "share_key" },
				new int[] { R.id.tv_list_item }));
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				user_manager_category_stub.setText(moreList.get(position).get(
						"share_key"));
				pwMyPopWindow.dismiss();
				Toast.makeText(UserControlCommonActivity.this,
						moreList.get(position).get("share_key"),
						Toast.LENGTH_SHORT).show();
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
