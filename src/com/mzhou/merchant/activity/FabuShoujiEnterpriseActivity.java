package com.mzhou.merchant.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.provider.MediaStore.Video.VideoColumns;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mzhou.merchant.dao.IBack.IUploadBackInfo;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.GroupUsers;
import com.mzhou.merchant.model.ProductsEnterpriseByIdBean;
import com.mzhou.merchant.model.User;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.utlis.HttpMultipartPost;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 注释部分是：上传视频功能
 * @author user
 *
 */
public class FabuShoujiEnterpriseActivity extends Activity {
	private ImageView showLeft;
	private Button publish,preview;
//	private Button pub_product_pre_video, pub_product_record_video,pub_product_del_video;
//	private static final int RECORD_VIDEO = 10001;
//	private static final int PLAY_VIDEO = 10002;
//	private ImageView iv = null;
//	private Bitmap bitmap = null;
 	private LinearLayout user_manager_category;
 	private List<Map<String, String>> moreList;
	private PopupWindow pwMyPopWindow;
	private ListView lvPopupList;
	private TextView user_manager_category_stub;
	private int NUM_OF_VISIBLE_LIST_ROWS = 3;
 	private String classid;
	private ImageView category_postion;
	private EditText pub_product_pingpai;
	private EditText pub_product_xinghao;
	private EditText pub_product_xinpian;
	private EditText pub_product_chicun;
	private EditText pub_product_fenbianlv;
	private EditText pub_product_xitong;
	private EditText pub_product_qianxiangsu;
	private EditText pub_product_houxiangsu;
	private EditText pub_product_jianjie;
	private EditText pub_product_rom;
	private EditText pub_product_ah;
	 

	private EditText name1;
	private EditText name2;
	private EditText name3;
	private EditText name4;
	private EditText name5;
	private EditText name6;
	private EditText name7;
	private EditText name8;
	private EditText name9;
	private EditText name10;
	private EditText nub1;
	private EditText nub2;
	private EditText nub3;
	private EditText nub4;
	private EditText nub5;
	private EditText nub6;
	private EditText nub7;
	private EditText nub8;
	private EditText nub9;
	private EditText nub10;
	private EditText pub_product_companyName;
	private EditText pub_product_address;
	private EditText pub_product_net;
	private EditText user_manager_centerFox;
	private EditText user_manager_centerNub;

	private static final int REQUEST_CODE = 1;
	private static final int REQUEST_CODE1 = 2;
	private MyGridView gridView;
 	private LinkedList<String> mList;
	boolean isLast = false;
	private int MAXSIZE = 5;
	private Uri mImageUri;
	private ImageAdapter adapter;
	private SharedPreferences sp;
	private String uid_enterprise;
	private String brand;
	private String type;
	private String chip;
	private String size;
	private String distinguish;
	private String system;
	private String prec_pixel;
	private String next_pixel;
	private String content;
	private String rom;
	private String ah;
	private String address;
	private String company;
	private String net;
	private String fax;
	private String center;
	private String name;
	private Context context;
	private File file;
	private int REQUEST = 1234;
	public static int RESULT = 4321;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_pic";
//	private boolean isRecord = false;//if video  has recording
//	private int MAX_VIDEO = 1024 * 1024 * 10 ; //the max size of video
//	private String videopath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/recordvideo.3gp";// the video path
//	private TextView upload_video_tv;
	private ImageView imageview_add;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_shouji_enterprise);
		init();
		loadButton();
		iniPopupWindow();
		selectPicture();
		setData();
		listennerButton();
	}

	private void init() {
		context = FabuShoujiEnterpriseActivity.this;
		mList = new LinkedList<String>();
		sp = getSharedPreferences("phonemerchant", 1);
		uid_enterprise = sp.getString("uid_enterprise", "0");

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ad_loading)
				.showImageForEmptyUri(R.drawable.ad_loading)
				.showImageOnFail(R.drawable.ad_loading).cacheInMemory()
				.cacheOnDisc().delayBeforeLoading(0)
				.displayer(new RoundedBitmapDisplayer(4))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		file = new File(saveDir, "temp_pic.jpg");
	}

	/**
	 * 鍔犺浇鎺т欢鎸夐挳
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
		pub_product_rom = (EditText) findViewById(R.id.pub_product_rom);
		pub_product_ah = (EditText) findViewById(R.id.pub_product_ah);
		user_manager_centerFox = (EditText) findViewById(R.id.user_manager_centerFox);
		user_manager_centerNub = (EditText) findViewById(R.id.user_manager_centerNub);
		
		category_postion = (ImageView) findViewById(R.id.category_postion);
		user_manager_category_stub = (TextView) findViewById(R.id.user_manager_category_stub);
		user_manager_category = (LinearLayout) findViewById(R.id.user_manager_category);
		
		name1 = (EditText) findViewById(R.id.name1);
		name2 = (EditText) findViewById(R.id.name2);
		name3 = (EditText) findViewById(R.id.name3);
		name4 = (EditText) findViewById(R.id.name4);
		name5 = (EditText) findViewById(R.id.name5);
		name6 = (EditText) findViewById(R.id.name6);
		name7 = (EditText) findViewById(R.id.name7);
		name8 = (EditText) findViewById(R.id.name8);
		name9 = (EditText) findViewById(R.id.name9);
		name10 = (EditText) findViewById(R.id.name10);
		nub1 = (EditText) findViewById(R.id.nub1);
		nub2 = (EditText) findViewById(R.id.nub2);
		nub3 = (EditText) findViewById(R.id.nub3);
		nub4 = (EditText) findViewById(R.id.nub4);
		nub5 = (EditText) findViewById(R.id.nub5);
		nub6 = (EditText) findViewById(R.id.nub6);
		nub7 = (EditText) findViewById(R.id.nub7);
		nub8 = (EditText) findViewById(R.id.nub8);
		nub9 = (EditText) findViewById(R.id.nub9);
		nub10 = (EditText) findViewById(R.id.nub10);

		pub_product_companyName = (EditText) findViewById(R.id.pub_product_companyName);
		pub_product_address = (EditText) findViewById(R.id.pub_product_address);
		pub_product_net = (EditText) findViewById(R.id.pub_product_net);
		publish = (Button) findViewById(R.id.publish);
		preview = (Button) findViewById(R.id.preview);
//		iv = (ImageView) findViewById(R.id.upload_video);
//		pub_product_pre_video = (Button) findViewById(R.id.pub_product_pre_video);
//		pub_product_record_video = (Button) findViewById(R.id.pub_product_record_video);
		gridView = (MyGridView) findViewById(R.id.publish_product_gridview);
//		upload_video_tv = (TextView) findViewById(R.id.upload_video_tv);
//		pub_product_del_video = (Button) findViewById(R.id.pub_product_del_video);
		imageview_add = (ImageView) findViewById(R.id.imageview_add);
	}

	private void selectPicture() {
		adapter = new ImageAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setSelector(R.drawable.grid_item_background);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
						if (mList.size() != 0) {
							String[] imageUrls = new String[mList.size()];
							Intent intent = new Intent();
							intent.setClass(FabuShoujiEnterpriseActivity.this,
									PicPagerActivity.class);
							for (int i = 0; i < mList.size() ; i++) {
								imageUrls[i] = mList.get(i);
							}
							intent.putExtra(MyConstants.Extra.IMAGES, imageUrls);
							intent.putExtra(MyConstants.Extra.IMAGE_POSITION,
									position);
							intent.putExtra("pub", true);
							startActivityForResult(intent, REQUEST);
						}
			}
		});
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
	}

	/**
	 */
	private void setData() {

		String name_enterprise = sp.getString("name_enterprise", "");
		uid_enterprise = sp.getString("uid_enterprise", "0");
		String address_enterprise = sp.getString("address_enterprise", "");
		String company_enterprise = sp.getString("company_enterprise", "");
		String net_enterprise = sp.getString("net_enterprise", "");
		String company_center_enterprise = sp.getString(
				"company_center_enterprise", "");
		String company_fax_enterprise = sp.getString("company_fax_enterprise",
				"");

		pub_product_companyName.setText(company_enterprise);
		pub_product_address.setText(address_enterprise);
		pub_product_net.setText(net_enterprise);
		user_manager_centerFox.setText(company_fax_enterprise);
		user_manager_centerNub.setText(company_center_enterprise);

		GroupUsers groupUsers = getNameJson(name_enterprise);
		if (groupUsers != null) {
			name1.setText(groupUsers.getUsers().get(0).getName());
			name2.setText(groupUsers.getUsers().get(1).getName());
			name3.setText(groupUsers.getUsers().get(2).getName());
			name4.setText(groupUsers.getUsers().get(3).getName());
			name5.setText(groupUsers.getUsers().get(4).getName());
			name6.setText(groupUsers.getUsers().get(5).getName());
			name7.setText(groupUsers.getUsers().get(6).getName());
			name8.setText(groupUsers.getUsers().get(7).getName());
			name9.setText(groupUsers.getUsers().get(8).getName());
			name10.setText(groupUsers.getUsers().get(9).getName());
			nub1.setText(groupUsers.getUsers().get(0).getNumber());
			nub2.setText(groupUsers.getUsers().get(1).getNumber());
			nub3.setText(groupUsers.getUsers().get(2).getNumber());
			nub4.setText(groupUsers.getUsers().get(3).getNumber());
			nub5.setText(groupUsers.getUsers().get(4).getNumber());
			nub6.setText(groupUsers.getUsers().get(5).getNumber());
			nub7.setText(groupUsers.getUsers().get(6).getNumber());
			nub8.setText(groupUsers.getUsers().get(7).getNumber());
			nub9.setText(groupUsers.getUsers().get(8).getNumber());
			nub10.setText(groupUsers.getUsers().get(9).getNumber());
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
		
		
//		pub_product_pre_video.setEnabled(isRecord);
	}

	private void listennerButton() {

		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
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
//		iv.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				playvideo(v);
//			}
//		});
		imageview_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(
						FabuShoujiEnterpriseActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(FabuShoujiEnterpriseActivity.this);
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

						String state = Environment
								.getExternalStorageState();
						if (state.equals(Environment.MEDIA_MOUNTED)) {
							file = new File(saveDir, System
									.currentTimeMillis() + ".jpg");
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
							startActivityForResult(intent,
									REQUEST_CODE1);
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
//			}

		
		});
		preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getdata();
				ProductsEnterpriseByIdBean p = new ProductsEnterpriseByIdBean();
				p.setBrand(brand);
				p.setType(type);
				p.setChip(chip);
				p.setSize(size);
				p.setDistinguish(distinguish);
				p.setSystem(system);
				p.setPrec_pixel(prec_pixel);
				p.setNext_pixel(next_pixel);
				p.setContent(content);
				p.setRom(rom);
				p.setAh(ah);

				p.setAddress(address);
				p.setNet(net);
				p.setCompany(company);
				p.setCenter(center);
				p.setFax(fax);
				p.setContact(name);
//				if (isRecord) {
//					p.setVideopic(videopath);
//				}

				String[] array = (String[]) mList.toArray(new String[mList
						.size()]);
				Intent intent = new Intent();
				intent.setClass(FabuShoujiEnterpriseActivity.this,
						FabushoujiEnterprisePreViewActivity.class);
				intent.putExtra("productinfo", p);
				intent.putExtra("urls", array);
				startActivity(intent);
			}

		});
//		pub_product_pre_video.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				playvideo(arg0);
//			}
//		});
//		pub_product_record_video.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				startrecord(arg0);
//			}
//		});
//		pub_product_del_video.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				 
//				if (!isRecord) {
//					pub_product_record_video.setText(getString(R.string.pub_product_record_video));
//					return;
//				}
//				pub_product_record_video.setText(getString(R.string.pub_product_re_video));
//				isRecord = false;
//				iv.setVisibility(View.GONE);
//				upload_video_tv.setVisibility(View.GONE);
//				File file2 = new File(videopath);
//				if (file2.exists()) {
//					file2.delete();
//				}
//				
//			}
//		});
	}
	private void postData() throws Exception{
		if (WebIsConnectUtil
				.showNetState(FabuShoujiEnterpriseActivity.this)) {
			getdata();

			if (!brand.toString().equals("")) {
				if (!content.toString().equals("")) {
					if (mList.size() == 5) {
						String[] array = (String[]) mList
								.toArray(new String[mList.size()]);
						if (array.length > 0) {
							Map<String, String> params = new HashMap<String, String>();
							params.put("uid", uid_enterprise);
							params.put("subject", "add");
							params.put("data[brand]", brand);
							params.put("data[classid]", classid);//此处存放classid
							params.put("data[is_en]", "1");
							params.put("data[type]", type);
							params.put("data[chip]", chip);
							params.put("data[size]", size);
							params.put("data[distinguish]", distinguish);
							params.put("data[system]", system);
							params.put("data[prec_pixel]", prec_pixel);
							params.put("data[next_pixel]", next_pixel);
							params.put("data[content]", content);
							params.put("data[contact]", name);
							params.put("data[address]", address);
							params.put("data[rom]", rom);
							params.put("data[ah]", ah);
							params.put("data[company]", company);
							params.put("data[center]", center);
							params.put("data[fax]", fax);
							params.put("data[net]", net);
								HttpMultipartPost task = new HttpMultipartPost(
										context,
										MyConstants.PRODUCT_URL, array,
										params);
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
												imageLoader.clearMemoryCache();
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
							}

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

	/**
	 * 
	 * @return
	 */
	private String getdata() {

		brand = MyUtlis.getEditString(pub_product_pingpai); 
		type = MyUtlis.getEditString(pub_product_xinghao);//  
		chip = MyUtlis.getEditString(pub_product_xinpian);// 
		size = MyUtlis.getEditString(pub_product_chicun);//  
		distinguish = MyUtlis.getEditString(pub_product_fenbianlv);//  
		system = MyUtlis.getEditString(pub_product_xitong);//  
		prec_pixel = MyUtlis.getEditString(pub_product_qianxiangsu);//  
		next_pixel = MyUtlis.getEditString(pub_product_houxiangsu);//  
		content = MyUtlis.getEditString(pub_product_jianjie);//  
		rom = MyUtlis.getEditString(pub_product_rom);
		ah = MyUtlis.getEditString(pub_product_ah);
		address = MyUtlis.getEditString(pub_product_address);//  
		company = MyUtlis.getEditString(pub_product_companyName);//  
		net = MyUtlis.getEditString(pub_product_net);//  
		fax = MyUtlis.getEditString(user_manager_centerFox);// 
		center = MyUtlis.getEditString(user_manager_centerNub);//  
		name = getNameJsondata();//  
		String uptime = System.currentTimeMillis() + "";
		return uptime;
	}

	private String getNameJsondata() {
		GroupUsers groupUsers = new GroupUsers();
		User user1 = new User();
		user1.setId(1L);
		user1.setName(name1.getText().toString());
		user1.setNumber(nub1.getText().toString());
		User user2 = new User();
		user2.setId(2L);
		user2.setName(name2.getText().toString());
		user2.setNumber(nub2.getText().toString());
		User user3 = new User();
		user3.setId(3L);
		user3.setName(name3.getText().toString());
		user3.setNumber(nub3.getText().toString());
		User user4 = new User();
		user4.setId(4L);
		user4.setName(name4.getText().toString());
		user4.setNumber(nub4.getText().toString());
		User user5 = new User();
		user5.setId(5L);
		user5.setName(name5.getText().toString());
		user5.setNumber(nub5.getText().toString());
		User user6 = new User();
		user6.setId(6L);
		user6.setName(name6.getText().toString());
		user6.setNumber(nub6.getText().toString());
		User user7 = new User();
		user7.setId(7L);
		user7.setName(name7.getText().toString());
		user7.setNumber(nub7.getText().toString());
		User user8 = new User();
		user8.setId(8L);
		user8.setName(name8.getText().toString());
		user8.setNumber(nub8.getText().toString());
		User user9 = new User();
		user9.setId(9L);
		user9.setName(name9.getText().toString());
		user9.setNumber(nub9.getText().toString());
		User user10 = new User();
		user10.setId(10L);
		user10.setName(name10.getText().toString());
		user10.setNumber(nub10.getText().toString());

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
	 * 
	 * @author user
	 */
	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(int position) {
			return mList.get(position);
		}

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
					mList.get(position), image,
					options);
			return convertView;
		}
	}

	/**
	 * 
	 * @param uri
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			mImageUri = data.getData();
			if (mImageUri != null) {
				String path = getImagePath(mImageUri);
				File file = new File(path);
				mList.addLast("file:/"+file.getAbsolutePath());
				if (mList.size() == MAXSIZE) {
					isLast = true;
					imageview_add.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
			}

		} else if (requestCode == REQUEST_CODE1
				&& resultCode == Activity.RESULT_OK) {

			if (file != null && file.exists()) {
				// BitmapFactory.Options option = new BitmapFactory.Options();
				// option.inSampleSize = 2;
				String path = file.getAbsolutePath();
				mList.addLast("file:/"+path);
				if (mList.size() == MAXSIZE) {
					isLast = true;
					imageview_add.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
			}

		}
		else if (requestCode == REQUEST && resultCode == RESULT) {
				mList.clear();
			String[] arry = data.getExtras().getStringArray(
					MyConstants.Extra.IMAGES);
			for (int i = 0; i < arry.length; i++) {
				mList.addLast(arry[i]);
			}
			if (mList.size() != 5) {
				isLast = false;
				imageview_add.setVisibility(View.VISIBLE);
			}
			adapter.notifyDataSetChanged();

		}  else if (requestCode == REQUEST && resultCode == 0) {
			if (mList != null) {
				mList.clear();
				imageview_add.setVisibility(View.VISIBLE);
			}
			adapter.notifyDataSetChanged();
		} 

	}

	/**
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

	@Override
	protected void onDestroy() {
		if (file.exists() && file != null) {
			file.delete();
			deleteDir(file);
		}
		File savePath = new File(saveDir);
		if (savePath.exists() && savePath != null) {
			try {
				savePath.delete();
				 
			} catch (Exception e) {
				 
			}finally{
				deleteDir(savePath);
			}
		}
		super.onDestroy();
	}
	private void iniPopupWindow() {
		moreList = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		String[] array = getResources().getStringArray(R.array.category_en);
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

		lvPopupList.setAdapter(new SimpleAdapter(FabuShoujiEnterpriseActivity.this,
				moreList, R.layout.list_item_popupwindow,
				new String[] { "share_key" }, new int[] { R.id.tv_list_item }));
		user_manager_category_stub.setText(moreList.get(0).get("share_key"));
		classid = 8 + "";
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				user_manager_category_stub.setText(moreList.get(position).get(
						"share_key"));
				if (position==0) {
					classid ="8";
				}else {
					classid ="7";
				}
				 
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
	private GroupUsers getNameJson(String string) {

		if (string != null && !string.equals("") && !string.equals("[]")) {
			GroupUsers groupUsers = JSON.parseObject(string, GroupUsers.class);
			return groupUsers;
		}
		return null;
	}

	/** 录制视频 **/
//	public void startrecord(View view) {
//		try {
//			File file = new File(fileName);
//			if (file.exists()) {
//				file.delete();
//				Log.d("delete", "删除成功");
//			}
//			File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recordvideo.3gp");
//			if (file2.exists()) {
//				file2.delete();
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//		mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);
//		startActivityForResult(mIntent, RECORD_VIDEO);
//	}

	/** 播放视频 **/
//	public void playvideo(View view) {
//		if (!isRecord) {
//			return;
//		}
//		Intent intent2 = new Intent();
//		intent2.setClass(context, ActivityVideoPlayer.class);
//		intent2.putExtra("path", videopath);
//		startActivity(intent2);
//	}

}
