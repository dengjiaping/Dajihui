package com.mzhou.merchant.activity;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mzhou.merchant.dao.IBack.IUploadBackInfo;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.utlis.HttpMultipartPost;
import com.mzhou.merchant.utlis.ImageUtils;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class FabuShoujiCommenActivity extends Activity {
	private ImageView showLeft;
	private Button publish;
	private Button preview;
	 private ImageView category_postion; 
 	private LinearLayout user_manager_category;
	 private List<Map<String, String>> moreList;
	private PopupWindow pwMyPopWindow;
	private ListView lvPopupList;
	private TextView user_manager_category_stub;
	private int NUM_OF_VISIBLE_LIST_ROWS = 8; 
	private EditText pub_product_pingpai;
	private EditText pub_product_xinghao;
	private EditText pub_product_xinpian;
	private EditText pub_product_chicun;
	private EditText pub_product_fenbianlv;
	private EditText pub_product_xitong;
	private EditText pub_product_qianxiangsu;
	private EditText pub_product_houxiangsu;
	private EditText pub_product_jianjie;
	private EditText pub_product_name;
	private EditText pub_product_number;
	private EditText pub_product_companyName;
	private EditText pub_product_address;
	private EditText pub_product_net;
	private EditText pub_product_email;
	private EditText pub_product_rom;
	private EditText pub_product_ah;
 	private String classid;

	private static final int CHOOSE_PIC = 34;
	private static final int TAKE_PIC = 43;
	private MyGridView gridView;
   	private List<String> mList;

//	boolean isLast = false;
	private int MAXSIZE = 5;
 	private ImageAdapter adapter;
	private String uid;
	private String rom;
	private String ah;
	private String brand;
	private String type;
	private String chip;
	private String size;
	private String distinguish;
	private String system;
	private String prec_pixel;
	private String next_pixel;
	private String content;
	private String email;
	private String phonenub;
	private String address;
	private String company;
	private String net;
	private String name;

//	private File file;
	private int REQUEST = 1234;
	public static int RESULT = 4321;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	 
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() +File.separator+ "djh"+File.separator+"pic"+File.separator;
	private Context context;
private ImageView imageview_add;
private final String TAG="FabuShoujiCommenActivity";
//private String takePicName ="";
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_shouji);
		Log.i(TAG, "oncreate");
		init();
		loadButton();
		selectPicture();
		iniPopupWindow();
		setData();
		listennerButton();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		mList = new ArrayList<String>();
		context = getBaseContext();
//		file = new File(saveDir, "temp_pic.jpg");
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ad_loading)
		.showImageForEmptyUri(R.drawable.ad_loading)
		.showImageOnFail(R.drawable.ad_loading)
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		configFile();
	}

	private void configFile() {
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		Log.i(TAG, saveDir);
		
		File file = new File(saveDir+"temp_pic.jpg");
		if (!file.exists()) {
			Log.i(TAG, "file is not exists!!");
			try {
				file.createNewFile();
				Log.i(TAG, "createNewFile  SUCCEED!!");
			} catch (IOException e) {
				Log.i(TAG, "createNewFile  FAILED!!");
				e.printStackTrace();
			}
		}
		Log.i(TAG, "file is  exists!!");
	}

	/**
	 * 加载控件按钮
	 */

	private void loadButton() {
		showLeft = (ImageView) findViewById(R.id.title_bar_showleft);
		pub_product_pingpai = (EditText) findViewById(R.id.pub_product_pingpai);
		pub_product_xinghao = (EditText) findViewById(R.id.pub_product_xinghao);
		pub_product_xinpian = (EditText) findViewById(R.id.pub_product_xinpian);
		pub_product_chicun = (EditText) findViewById(R.id.pub_product_chicun);
		pub_product_fenbianlv = (EditText) findViewById(R.id.pub_product_fenbianlv);
		pub_product_xitong = (EditText) findViewById(R.id.pub_product_xitong);
		pub_product_qianxiangsu = (EditText) findViewById(R.id.pub_product_qianxiangsu);
		pub_product_houxiangsu = (EditText) findViewById(R.id.pub_product_houxiangsu);
		pub_product_jianjie = (EditText) findViewById(R.id.pub_product_jianjie);
		pub_product_name = (EditText) findViewById(R.id.pub_product_name);
		pub_product_number = (EditText) findViewById(R.id.pub_product_number);
		pub_product_email = (EditText) findViewById(R.id.pub_product_email);
		pub_product_rom = (EditText) findViewById(R.id.pub_product_rom);
		pub_product_ah = (EditText) findViewById(R.id.pub_product_ah);

		pub_product_companyName = (EditText) findViewById(R.id.pub_product_companyName);
		pub_product_address = (EditText) findViewById(R.id.pub_product_address);
		pub_product_net = (EditText) findViewById(R.id.pub_product_net);
		publish = (Button) findViewById(R.id.publish);
		preview = (Button) findViewById(R.id.preview);
		gridView = (MyGridView) findViewById(R.id.publish_product_gridview);
		category_postion = (ImageView) findViewById(R.id.category_postion);
		user_manager_category_stub = (TextView) findViewById(R.id.user_manager_category_stub);
		user_manager_category = (LinearLayout) findViewById(R.id.user_manager_category);
		imageview_add = (ImageView) findViewById(R.id.imageview_add);
	}

	/**
	 * 从本地选取照片或者拍照获得照片
	 */

	private void selectPicture() {
		adapter = new ImageAdapter(this,mList);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
							String[] imageUrls = new String[mList.size()];
							Intent intent = new Intent();
							intent.setClass(FabuShoujiCommenActivity.this,
									PicPagerActivity.class);
							  mList.toArray(imageUrls);
							intent.putExtra(MyConstants.Extra.IMAGES, imageUrls);
							intent.putExtra(MyConstants.Extra.IMAGE_POSITION,
									position);
							intent.putExtra("pub", true);
							startActivityForResult(intent, REQUEST);
			}

		});
		
	}
	/**
	 * 照相
	 */
	private void takePhoto() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Uri imageUri = Uri.fromFile(new File(saveDir+ "temp_pic.jpg"));
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(openCameraIntent, TAKE_PIC);
		} else {
			MyUtlis.toastInfo(context, getResources()
					.getString(R.string.no_sdcard));
		}
	}

	/**
	 * 选择图片
	 */
	private void choosePicture() {
		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
		openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(openAlbumIntent, CHOOSE_PIC);
	}
	/**
	 * 设置用户信息
	 */
	private void setData() {

		// 判断是否填写了会员的信息如果填了就直接设置
		uid = "0";
		
		if (DbLoginManager.getInstance(this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(this).getLogingUserInfo();
			
			if (userInfoBean.getUsertype() != null && !userInfoBean.getUsertype().equals("null") && !userInfoBean.getUsertype().equals("")) {
				if (userInfoBean.getUsertype().equals("1")) {
				}else {
					pub_product_name.setText(userInfoBean.getContact());
					pub_product_number.setText(userInfoBean.getPassword());
					pub_product_email.setText(userInfoBean.getEmail());
					pub_product_companyName.setText(userInfoBean.getCompany());
					pub_product_address.setText(userInfoBean.getAddress());
					pub_product_net.setText(userInfoBean.getNet());
					uid = userInfoBean.getUid();
				}
			}
			}else {
				pub_product_name.setText("");
				pub_product_number.setText("");
				pub_product_email.setText("");
				pub_product_companyName.setText("");
				pub_product_address.setText("");
				pub_product_net.setText("");
			}
	 
 
		pub_product_pingpai.setText("");
		pub_product_xinghao.setText("");
		pub_product_xinpian.setText("");
		pub_product_chicun.setText("");
		pub_product_fenbianlv.setText("");
		pub_product_xitong.setText("");
		pub_product_qianxiangsu.setText("");
		pub_product_houxiangsu.setText("");
		pub_product_jianjie.setText("");
		pub_product_rom.setText("");
		pub_product_ah.setText("");

	}

	private void listennerButton() {
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

		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		imageview_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						FabuShoujiCommenActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(FabuShoujiCommenActivity.this);
				View view1 = inflater.inflate(
						R.layout.dialog_menu_view, null);
				Button capture = (Button) view1
						.findViewById(R.id.capture_pic);
				Button select = (Button) view1
						.findViewById(R.id.select_pic);
				Button cancel = (Button) view1
						.findViewById(R.id.cancel);
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
							takePhoto();
					}
				});
				select.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						choosePicture();
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

		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					postData();
				} catch (Exception e) {
					MyUtlis.toastInfo(context,
							"上传失败，请重试！");
					e.printStackTrace();
					return;
				}
			}

	

		});
		preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getdata();
				ProductsByIdBean p = new ProductsByIdBean();
				p.setBrand(brand);
				p.setType(type);
				p.setChip(chip);
				p.setSize(size);
				p.setDistinguish(distinguish);
				p.setSystem(system);
				p.setPrec_pixel(prec_pixel);
				p.setNext_pixel(next_pixel);
				p.setContent(content);
				p.setQq(email);
				p.setContact(name);
				p.setPhone(phonenub);
				p.setCompany(company);
				p.setAddress(address);
				p.setRom(rom);
				p.setAh(ah);
				p.setNet(net);
				String[] array = new String[mList.size()] ;
				mList.toArray(array);
				Intent intent = new Intent();
				intent.setClass(FabuShoujiCommenActivity.this,
						FabushoujiCommenPreViewActivity.class);
				intent.putExtra("productinfo", p);
				intent.putExtra("urls", array);
				startActivity(intent);

			}

		});

	}
	private void postData() throws Exception{
		if (WebIsConnectUtil
				.showNetState(FabuShoujiCommenActivity.this)) {
			getdata();
			if (!brand.toString().equals("")) {
				if (!content.toString().equals("")) {
					if ( mList.size() == 5) {
						String[] array = (String[]) mList
								.toArray(new String[0]);
						if (array.length > 0) {
							Map<String, String> param1 = new HashMap<String, String>();
							param1.put("subject", "add");
							param1.put("uid", uid);
							param1.put("data[brand]", brand);
							param1.put("data[classid]", classid);
							param1.put("data[type]", type);
							param1.put("data[chip]", chip);
							param1.put("data[size]", size);
							param1.put("data[distinguish]", distinguish);
							param1.put("data[system]", system);
							param1.put("data[prec_pixel]", prec_pixel);
							param1.put("data[next_pixel]", next_pixel);
							param1.put("data[content]", content);
							param1.put("data[phone]", phonenub);
							param1.put("data[contact]", name);
							param1.put("data[address]", address);
							param1.put("data[qq]", email);
							param1.put("data[rom]", rom);
							param1.put("data[ah]", ah);
							param1.put("data[company]", company);
							param1.put("data[net]", net);
								HttpMultipartPost task = new HttpMultipartPost(
										FabuShoujiCommenActivity.this,
										MyConstants.PRODUCT_URL, array,
										param1);
								task.execute();
								task.getBackInfoIml(new IUploadBackInfo() {

									@Override
									public void getBackAttactInfo(
											String json) {
										if (!"[{}]".equals(json)) {
										BackBean backBean = JsonParse
												.parsePubAttactJson(json);

										if (backBean != null) {
											if ("true".equals(backBean.getStatus())) {
												Intent intent = new Intent();
												intent.setClass(
														context,
														MyProductActivity.class);
												intent.putExtra(
														"authstr", true);
												startActivity(intent);
												deleteDir( new File(saveDir));
												finish();
											}
											MyUtlis.toastInfo(context,
													backBean.getMsg()
															.toString());
										}else {
											MyUtlis.toastInfo(context,
													"上传失败，请重试！");
										}
										}else {
											MyUtlis.toastInfo(context,
													"上传失败，请重试！");
										}
									}
								});

						} else {
							MyUtlis.toastInfo(context, getResources()
									.getString(R.string.picSize_null));
						}
					} else {
						MyUtlis.toastInfo(context, getResources()
								.getString(R.string.picCount_low));

					}
				} else {
					MyUtlis.toastInfo(context, getResources()
							.getString(R.string.introduce_null));
				}
			} else {
				MyUtlis.toastInfo(context,
						getResources().getString(R.string.brand_null));
			}
		}
	}
	/**
	 * getdata 获取EditText的值
	 * 
	 * @return
	 */
	private String getdata()  {
		brand = MyUtlis.getEditString(pub_product_pingpai) + "";
		type = MyUtlis.getEditString(pub_product_xinghao) + "";
		chip = MyUtlis.getEditString(pub_product_xinpian) + "";
		size = MyUtlis.getEditString(pub_product_chicun) + "";
		distinguish = MyUtlis.getEditString(pub_product_fenbianlv) + "";
		system = MyUtlis.getEditString(pub_product_xitong) + "";
		prec_pixel = MyUtlis.getEditString(pub_product_qianxiangsu) + "";
		next_pixel = MyUtlis.getEditString(pub_product_houxiangsu) + "";
		content = MyUtlis.getEditString(pub_product_jianjie) + "";
		email = MyUtlis.getEditString(pub_product_email) + "";
		rom = MyUtlis.getEditString(pub_product_rom) + "";
		ah = MyUtlis.getEditString(pub_product_ah) + "";
		phonenub = MyUtlis.getEditString(pub_product_number) + "";
		address = MyUtlis.getEditString(pub_product_address) + "";
		company = MyUtlis.getEditString(pub_product_companyName) + "";
		net = MyUtlis.getEditString(pub_product_net) + "";
		name = MyUtlis.getEditString(pub_product_name) + "";
		String uptime = System.currentTimeMillis() + "";
		return uptime;
	}

	/**
	 * 图片适配器，用于适配拍照或本地选择图片展示
	 * 
	 * @author user
	 * 
	 */
	public class ImageAdapter extends BaseAdapter {
		// 定义Context
		private Context mContext;
		private List<String> listStr ;
		public ImageAdapter(Context c,List<String> list) {
			this.listStr = list;
			mContext = c;
		}

		// 获取图片的个数
		public int getCount() {
			return listStr.size();
		}

		// 获取图片在库中的位置
		public Object getItem(int position) {
			return listStr.get(position);
		}

		// 获取图片ID
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.fabu_shouji_grid_item, null);
			}
			ImageView image = (ImageView) convertView
					.findViewById(R.id.image_view);
			imageLoader.displayImage(
					listStr.get(position), image,
					options);
 		return convertView;
		}
		 
	}

 

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, "[onActivityResult]    requestCode="+requestCode+",resultCode="+resultCode+",mList="+mList.toString());
		if (requestCode == CHOOSE_PIC && resultCode == RESULT_OK) {//choose picture 
			if (mList.size() < MAXSIZE) {
				ContentResolver resolver = getContentResolver();
				Uri originalUri = data.getData();
				try {
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						String choose_pic_path = ImageUtils.savePhotoToSDCard(photo, saveDir,
								String.valueOf(System.currentTimeMillis()));
						if (choose_pic_path.length() > 0) {
							Log.i(TAG, "[onActivityResult]    choose_pic_path="+choose_pic_path);
								mList.add("file:/"+choose_pic_path);
								if (mList.size() == MAXSIZE) {
									imageview_add.setVisibility(View.GONE);
								}
							adapter.notifyDataSetChanged();
						} 
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				MyUtlis.toastInfo(context, "图片最多只能显示5张!");
			}
		
		 
		} else if (requestCode == TAKE_PIC
				&& resultCode ==   RESULT_OK) {//take picture
			if (mList.size() < MAXSIZE) {
				Bitmap newBitmap = ImageUtils.getimage(saveDir+ "temp_pic.jpg");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA);
				String newName = dateFormat.format(new Date(System.currentTimeMillis())) ;
				String pic_path = ImageUtils.savePhotoToSDCard(newBitmap, saveDir,newName);
				Log.i(TAG, "[onActivityResult]    pic_path="+pic_path);
				mList.add("file:/"+pic_path);
				if (mList.size() == MAXSIZE) {
					imageview_add.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
				newBitmap.recycle();
			}
		}
		else if (requestCode == REQUEST && resultCode == RESULT) {//preview back
			mList.clear();
			String[] arry = data.getExtras().getStringArray(
					MyConstants.Extra.IMAGES);
			mList = Arrays.asList(arry);
			if (mList.size() < MAXSIZE) {
				imageview_add.setVisibility(View.VISIBLE);
			}
			adapter.notifyDataSetChanged();

		} else if (requestCode == REQUEST && resultCode == 0) {
			if (mList != null) {
				mList.clear();
				imageview_add.setVisibility(View.VISIBLE);
			}
		 
			adapter.notifyDataSetChanged();
		}
	}
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "onPause");
		super.onStop();
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "onStart");
		super.onStart();
	}
	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}
	/**
	 * 删除文件夹目录和文件夹里面的文件
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



	private void iniPopupWindow() {
		moreList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		String[] array = getResources().getStringArray(R.array.category2);
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

		lvPopupList.setAdapter(new SimpleAdapter(FabuShoujiCommenActivity.this,
				moreList, R.layout.list_item_popupwindow,
				new String[] { "share_key" }, new int[] { R.id.tv_list_item }));
		user_manager_category_stub.setText(moreList.get(0).get("share_key"));
		classid = 1 + "";
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				user_manager_category_stub.setText(moreList.get(position).get(
						"share_key"));
				int class_id = position + 1;
				classid = class_id + "";
				pwMyPopWindow.dismiss();
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
