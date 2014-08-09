package com.mzhou.merchant.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.mzhou.merchant.dao.IBack.IUploadBackInfo;
import com.mzhou.merchant.dao.IProduct.IgetProductInfoById;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.utlis.HttpMultipartPost;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class EditShoujiCommenActivity extends Activity {
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

	private static final int REQUEST_CODE = 1;
	private static final int REQUEST_CODE1 = 2;
	private MyGridView gridView;
	private LinkedList<String> mList;

	boolean isLast = false;
	private int MAXSIZE = 5;
	private Uri mImageUri;
	private ImageAdapter adapter;
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

	private File file;
	private int REQUEST = 1234;
	public static int RESULT = 4321;
	protected ImageLoader imageLoader;
	private DisplayImageOptions options;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/temp_pic";
	private Context context;
	private TextView title_bar_title;
	private String productid;
	private ProductsManager productsManager;
	private String picfromServer;
	private ImageView imageview_add;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		setContentView(R.layout.fabu_shouji);
		init();
		loadButton();
		getdataFromWeb();
		iniPopupWindow();
		selectPicture();
		listennerButton();
	}

	/**
	 * 初始化
	 */
	private void init() {
		mList = new LinkedList<String>();
		context = EditShoujiCommenActivity.this;
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
		 
		productsManager = new ProductsManager();
		Intent intent = getIntent();
		productid = intent.getStringExtra("id");
		isLast = true;
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		file = new File(saveDir, "temp_pic.jpg");
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
		title_bar_title = (TextView) findViewById(R.id.title_bar_title);
		title_bar_title.setText(getResources().getString(
				R.string.edit_product_info));
		
		imageview_add = (ImageView) findViewById(R.id.imageview_add);

	}

	private void getdataFromWeb() {

		if (WebIsConnectUtil.showNetState(context)) {
			productsManager.GetProductInfoById(context, productid);
			productsManager.getProductInfoByIdIml(new IgetProductInfoById() {
				@Override
				public void getProductInfoById(ProductsByIdBean productsByIdBean) {
					if (productsByIdBean != null) {
						pub_product_pingpai.setText(productsByIdBean.getBrand());
						pub_product_xinghao.setText(productsByIdBean.getType());
						pub_product_xinpian.setText(productsByIdBean.getChip());
						pub_product_chicun.setText(productsByIdBean.getSize());
						pub_product_fenbianlv.setText(productsByIdBean
								.getDistinguish());
						pub_product_xitong.setText(productsByIdBean.getSystem());
						pub_product_qianxiangsu.setText(productsByIdBean
								.getPrec_pixel());
						pub_product_houxiangsu.setText(productsByIdBean
								.getNext_pixel());
						pub_product_jianjie.setText(productsByIdBean
								.getContent());
						pub_product_name.setText(productsByIdBean.getContact());
						pub_product_number.setText(productsByIdBean.getPhone());
						pub_product_email.setText(productsByIdBean.getQq());
						pub_product_rom.setText(productsByIdBean.getRom());
						pub_product_ah.setText(productsByIdBean.getAh());
						pub_product_companyName.setText(productsByIdBean
								.getCompany());
						pub_product_address.setText(productsByIdBean
								.getAddress());
						pub_product_net.setText(productsByIdBean.getNet());
						String[] array = getResources().getStringArray(
								R.array.category2);
						int category = Integer.valueOf(productsByIdBean
								.getClassid());
						user_manager_category_stub.setText(array[category - 1]);
						picfromServer = productsByIdBean.getPic();// get the
																	// picture
						if (picfromServer != null) {
							StringTokenizer tokenizer = new StringTokenizer(
									picfromServer, getResources().getString(
											R.string.spilt));
							while (tokenizer.hasMoreTokens()) {
								mList.addLast(MyConstants.PICTURE_URL
										+ tokenizer.nextToken());
							}

						}
					} else {
						finish();
					}
				}
			});

		} else {
			finish();
		}

	}

	/**
	 * 从本地选取照片或者拍照获得照片
	 */

	private void selectPicture() {
		adapter = new ImageAdapter(context, mList);
		gridView.setAdapter(adapter);
		gridView.setSelector(R.drawable.grid_item_background);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

						if (mList.size() != 0) {
							String[] imageUrls = new String[mList.size()];
							Intent intent = new Intent();
							intent.setClass(context, PicPagerActivity.class);
							for (int i = 0; i < mList.size(); i++) {
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
		imageview_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				LayoutInflater inflater = LayoutInflater.from(context);
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
		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WebIsConnectUtil.showNetState(context)) {
					getdata();

					String string = "drawable://"
							+ R.drawable.roominfo_add_btn_normal;
					if (!brand.toString().equals("")) {
						if (!content.toString().equals("")) {
							if (!mList.contains(string) && mList.size() == 5) {
								String[] array = (String[]) mList
										.toArray(new String[0]);
								if (array.length > 0) {
									Map<String, String> param1 = new HashMap<String, String>();
									param1.put("subject", "edit");
									 String uid = "0";
									 String usertype = "0";
									 
									UserInfoBean userInfoBean =  DbUserManager.getInstance(context).getLogingUserInfo();
									if (userInfoBean != null && !userInfoBean.getUid().equals("null")&& !userInfoBean.getUid().equals("")) {
										uid = userInfoBean.getUid();
									}
									if (userInfoBean != null && !userInfoBean.getUsertype().equals("null")&& !userInfoBean.getUsertype().equals("")) {
										usertype = userInfoBean.getUsertype();
									}
									
									param1.put("uid", uid);
									param1.put("data[id]", productid);
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

									long picSize = 0;
									for (int i = 0; i < array.length; i++) {
										FileInputStream fis;
										try {
											File file = new File(array[i]);
											fis = new FileInputStream(file);
											int fileLen = fis.available();
											picSize = picSize + fileLen;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
//									if (picSize < 10 * 1024 * 1024) {
										HttpMultipartPost task = new HttpMultipartPost(
												EditShoujiCommenActivity.this,
												MyConstants.PRODUCT_URL, array,
												param1);
										task.execute();
										task.getBackInfoIml(new IUploadBackInfo() {

											@Override
											public void getBackAttactInfo(
													String json) {
												BackBean backBean = JsonParse
														.parsePubAttactJson(json);

												if (backBean != null) {
													if (backBean.getStatus()
															.equals("true")) {
														Intent intent = new Intent();
														intent.setClass(
																context,
																MyProductActivity.class);
														intent.putExtra(
																"authstr", true);
														startActivity(intent);
														finish();
													}
													MyUtlis.toastInfo(context,
															backBean.getMsg()
																	.toString());
												}

											}
										});
									/*} else {
										MyUtlis.toastInfo(
												context,
												getResources().getString(
														R.string.picSize_big));
									}*/

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
				String[] array = (String[]) mList.toArray(new String[mList
						.size()]);
				Intent intent = new Intent();
				intent.setClass(context, FabushoujiCommenPreViewActivity.class);
				intent.putExtra("productinfo", p);
				intent.putExtra("urls", array);
				startActivity(intent);

			}

		});

	}

	/**
	 * getdata 获取EditText的值
	 * 
	 * @return
	 */
	private String getdata() {
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
		private LinkedList<String> mList;

		public ImageAdapter(Context c, LinkedList<String> mList) {
			mContext = c;
			this.mList = mList;
		}

		// 获取图片的个数
		public int getCount() {
			return mList.size();
		}

		// 获取图片在库中的位置
		public Object getItem(int position) {
			return mList.get(position);
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
					mList.get(position), image,
					options);
			return convertView;
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
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			mImageUri = data.getData();
			if (mImageUri != null) {
				String path = getImagePath(mImageUri);
				File file = new File(path);

				/*FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					int fileLen = fis.available();
					if (fileLen > 2 * 1024 * 1024) {
						// pic size bigger than 2M
						MyUtlis.toastInfo(context,
								getResources()
										.getString(R.string.picSize_small));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}*/

				mList.addLast("file:/"+ file.getAbsolutePath());
				if (mList.size() == MAXSIZE) {
					imageview_add.setVisibility(View.GONE);
					isLast = true;
				}
				adapter.notifyDataSetChanged();
			}

		} else if (requestCode == REQUEST_CODE1
				&& resultCode == Activity.RESULT_OK) {
			if (file != null && file.exists()) {
				// BitmapFactory.Options option = new BitmapFactory.Options();
				// option.inSampleSize = 2;
				String path = file.getAbsolutePath();
				/*FileInputStream fis;
				try {
					File file = new File(path);
					fis = new FileInputStream(file);
					int fileLen = fis.available();
					if (fileLen > 2 * 1024 * 1024) {
						// pic size bigger the 2M
						MyUtlis.toastInfo(context,
								getResources()
										.getString(R.string.picSize_small));

					}
				} catch (Exception e) {
					e.printStackTrace();
				}*/

				mList.addLast("file:/"+ path);
				if (mList.size() == MAXSIZE) {
					isLast = true;
					imageview_add.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
			}

		} else {
		}
		if (requestCode == REQUEST && resultCode == RESULT) {
			if (mList != null) {
				mList.clear();
			}
			String[] arry = data.getExtras().getStringArray(
					MyConstants.Extra.IMAGES);

			for (int i = 0; i < arry.length; i++) {
				mList.add(arry[i]);
			}
			if (mList != null && mList.size() != 5) {
					imageview_add.setVisibility(View.VISIBLE);
					isLast = false;
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

	@Override
	protected void onDestroy() {
		if ( file != null && file.exists()  ) {
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

		lvPopupList.setAdapter(new SimpleAdapter(EditShoujiCommenActivity.this,
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
