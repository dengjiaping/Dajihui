package com.mzhou.merchant.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.mzhou.merchant.dao.IProduct.IgetEnterpriseProductInfoById;
import com.mzhou.merchant.dao.biz.ProductsManager;
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

public class EditShoujiEnterpriseActivity extends Activity {
	private ImageView showLeft;
	private Button publish;
	private Button preview;
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
	private TextView title;

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

	private String productid;
	private ProductsManager productsManager;
	private String picfromServer;
	private ImageView imageview_add;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fabu_shouji_enterprise);
		init();
		loadButton();
		try {
			getdataFromWeb();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 iniPopupWindow() ;
		selectPicture();
		listennerButton();
	}

	private void init() {
		context = EditShoujiEnterpriseActivity.this;
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
		productsManager = new ProductsManager();
		isLast = true;
		Intent intent = getIntent();
		productid = intent.getStringExtra("id");
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		file = new File(saveDir, "temp_pic.jpg");
	}

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
		title = (TextView) findViewById(R.id.title_bar_title);
		title.setText(getString(R.string.edit_product_info));// set the tilebar
		
		
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
		gridView = (MyGridView) findViewById(R.id.publish_product_gridview);
		
		imageview_add = (ImageView) findViewById(R.id.imageview_add);
	}

	private void getdataFromWeb() throws Exception{
		if (WebIsConnectUtil.showNetState(context)) {
			productsManager.ProductsEnterpriseByIdBean(context, productid);
			productsManager
					.getEnterpriseProductInfoByIdIml(new IgetEnterpriseProductInfoById() {

						@Override
						public void getProductInfoById(
								ProductsEnterpriseByIdBean productsByIdBean) {
							if (productsByIdBean != null) {
								pub_product_pingpai.setText(productsByIdBean.getBrand()); 
								pub_product_xinghao.setText(productsByIdBean.getType());	    
								pub_product_xinpian.setText(productsByIdBean.getChip()); 	
								pub_product_chicun.setText(productsByIdBean.getSize()); 		
								pub_product_fenbianlv.setText(productsByIdBean.getDistinguish());   
								pub_product_xitong.setText(productsByIdBean.getSystem());      
								pub_product_qianxiangsu.setText(productsByIdBean.getPrec_pixel()); 
								pub_product_houxiangsu.setText(productsByIdBean.getNext_pixel());  
								pub_product_jianjie.setText(productsByIdBean.getContent());     
								pub_product_rom.setText(productsByIdBean.getRom());      	
								pub_product_ah.setText(productsByIdBean.getAh()); 			
								user_manager_centerFox.setText(productsByIdBean.getFax());  
								user_manager_centerNub.setText(productsByIdBean.getCenter());  
								pub_product_companyName.setText(productsByIdBean.getCompany());
								pub_product_address.setText(productsByIdBean.getAddress());		  
								pub_product_net.setText(productsByIdBean.getNet()); 
								picfromServer = productsByIdBean.getPic();
								GroupUsers groupUsers = getNameJson(productsByIdBean.getContact());
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
								if (picfromServer != null) {
									StringTokenizer tokenizer = new StringTokenizer(
											picfromServer, getResources()
													.getString(R.string.spilt));
									while (tokenizer.hasMoreTokens()) {
										mList.addLast(MyConstants.PICTURE_URL
												+ tokenizer.nextToken());
									}
									adapter = new ImageAdapter(context);
									gridView.setAdapter(adapter);
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

	private void selectPicture() {
		
		gridView.setSelector(R.drawable.grid_item_background);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
						if (mList.size() != 0) {
							String[] imageUrls = new String[mList.size() ];
							Intent intent = new Intent();
							intent.setClass(EditShoujiEnterpriseActivity.this,
									PicPagerActivity.class);
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
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
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
		imageview_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(
						EditShoujiEnterpriseActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(EditShoujiEnterpriseActivity.this);
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
				if (WebIsConnectUtil
						.showNetState(EditShoujiEnterpriseActivity.this)) {
					getdata();

					if (!brand.toString().equals("")) {
						if (!content.toString().equals("")) {
							if ( mList.size() == 5) {
								String[] array = (String[]) mList
										.toArray(new String[mList.size()]);
								if (array.length > 0) {
									Map<String, String> params = new HashMap<String, String>();
									params.put("uid", uid_enterprise);
									params.put("data[id]", productid);
									params.put("subject", "edit");
									params.put("data[brand]", brand);
									params.put("data[classid]",classid);
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
								/*	if (picSize > 10 * 1024 * 1024) {
										MyUtlis.toastInfo(
												context,
												getResources().getString(
														R.string.picSize_big));
									} else {*/
										HttpMultipartPost task = new HttpMultipartPost(
												context,
												MyConstants.PRODUCT_URL, array,
												params);
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
//				}
			}

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

				String[] array = (String[]) mList.toArray(new String[mList
						.size()]);
				Intent intent = new Intent();
				intent.setClass(EditShoujiEnterpriseActivity.this,
						FabushoujiEnterprisePreViewActivity.class);
				intent.putExtra("productinfo", p);
				intent.putExtra("urls", array);
				startActivity(intent);
			}

		});

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

		lvPopupList.setAdapter(new SimpleAdapter(EditShoujiEnterpriseActivity.this,
				moreList, R.layout.list_item_popupwindow,
				new String[] { "share_key" }, new int[] { R.id.tv_list_item }));
		user_manager_category_stub.setText(moreList.get(0).get("share_key"));
		classid =   "8";
		lvPopupList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				user_manager_category_stub.setText(moreList.get(position).get("share_key"));
				if(position == 0){
					classid = "8";
				}else {
					classid="7";
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
	private String getdata() {

		brand = MyUtlis.getEditString(pub_product_pingpai);//
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
		company = MyUtlis.getEditString(pub_product_companyName);
		net = MyUtlis.getEditString(pub_product_net);
		fax = MyUtlis.getEditString(user_manager_centerFox);
		center = MyUtlis.getEditString(user_manager_centerNub); 
		name = getNameJsondata(); 
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

				/*FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					int fileLen = fis.available();
					if (fileLen > 2 * 1024 * 1024) {
						// pic size bigger the 2M
						MyUtlis.toastInfo(context,
								getResources()
										.getString(R.string.picSize_small));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				mList.addLast("file:/"+path);
				if (mList.size() == MAXSIZE) {
					isLast = true;
					imageview_add.setVisibility(View.GONE);
				}
				adapter.notifyDataSetChanged();
			}

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
			if (mList.size() != 5) {
				isLast = false;
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
			savePath.delete();
			deleteDir(savePath);
		}

		super.onDestroy();
	}

	private GroupUsers getNameJson(String string) {

		if (string != null && !string.equals("") && !string.equals("[]")) {
			GroupUsers groupUsers = JSON.parseObject(string, GroupUsers.class);
			return groupUsers;
		}
		return null;
	}

}
