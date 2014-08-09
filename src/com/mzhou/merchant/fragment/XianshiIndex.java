package com.mzhou.merchant.fragment;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.mzhou.merchant.activity.ActivityIndex;
import com.mzhou.merchant.activity.LogoItemListActivity;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.ShoujiCommenActivity;
import com.mzhou.merchant.activity.ShoujiEnterpriseActivity;
import com.mzhou.merchant.adapter.MyGridProductAdapter4;
import com.mzhou.merchant.dao.IProduct.IgetProductInfo;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbAdManager;
import com.mzhou.merchant.db.manager.DbProductManager;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

@SuppressLint("HandlerLeak")
public class XianshiIndex extends Fragment {
	private static boolean isRun = true;
	private ImageView showLeft, fabu;
	private ImageView guanggao1, guanggao2, guanggao3, guanggao4, guanggao5;
	private View view;
	private ViewFlipper viewFlipper;
	private boolean showNext = true;
	private int currentPage = 0;
	private final int SHOW_NEXT = 0011;
	private Context context;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	private LinkedList<ProductsBean> mList;
	private ProductsManager productsManager;
	private MyGridProductAdapter4 mAdapter;

	private PullToRefreshScrollView mPullRefreshScrollView;
 	private MyGridView mGridView;
	private int page_up;
	private int page_down;
	private String uptime;
	private int classid;
	private LinkedList<AdBean> adBeans;
 	private boolean flag = false;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		View mView = inflater.inflate(R.layout.view_pager_index, null);
		view = mView;
		init();
		loadButton(mView);
		refreshListener();
		getdata();
		thread.start();
		listenerButton();
		setAd();
		if (getActivity().getSharedPreferences("phonemerchant", 1).getBoolean("getversion", false)) {
			checkVersion();
		}
		
		return mView;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getdata();
	}
	/**
	 * check the version 
	 * this need server modify
	 * 1.url,2.subject 3.the data return
	 */
	private void checkVersion(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			String version =	GetDataByPostUtil.CheckVersion(context, MyConstants.CHECKVERSION_URL, "info");
			if (!version.trim().toString().equals("[]")) {
				try {
					JSONObject object = new JSONObject(version);
					String currentVersion = (String) object.get("version");
					String url = object.getString("url");
					try {
						System.out.println(getVersionName());
						if (!currentVersion.equals(getVersionName())) {//current version is not the newest
							//then update the version
						Message message = handler.obtainMessage();
						message.what = 1;
						message.obj = url;
						handler.sendMessage(message);
}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				
			 
			}
			
			}
		}).start();
	}

	private String getVersionName() throws Exception {
		PackageManager packageManager = getActivity().getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getActivity()
				.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}
	private Handler handler  = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//set Dialog show
 				final String url = (String) msg.obj;
 				getActivity().getSharedPreferences("phonemerchant", 1).edit().putBoolean("getversion", false).commit();
 				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
 				  builder.setTitle("新版本提示");
 				  builder.setMessage("有新版本发布，请更新使用!");
 				  builder.setPositiveButton("立即更新",  new android.content.DialogInterface.OnClickListener(){

 					public void onClick(DialogInterface dialog, int which) { 
 						Intent viewIntent = new Intent(
 								"android.intent.action.VIEW",
 								Uri.parse(MyConstants.PICTURE_URL + "/"+url));
 						startActivity(viewIntent);
 					}
 		        	
 		        });
 		        
 		        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener(){

 					public void onClick(DialogInterface dialog, int which) {
 					}
 		        	
 		        });
 		        
 		        builder.show();
				break;

			default:
				break;
			}
		};
	};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		showLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ActivityIndex.showLeft();
			}
		});

		fabu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityIndex.showRight();
			}
		});
		super.onActivityCreated(savedInstanceState);
	}

	private void init() {
		context = getActivity().getBaseContext();
		mList = new LinkedList<ProductsBean>();
		adBeans = new LinkedList<AdBean>();

		productsManager = new ProductsManager();
		
		 imageLoader = ImageLoader.getInstance();
		 options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_stub)
			.showImageOnFail(R.drawable.ic_stub)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		 mAdapter = new MyGridProductAdapter4(context, mList,imageLoader,options);

		page_up = 2;
		page_down = 1;
		uptime = new String("0");
		classid = MyConstants.MAIN;
	}

	/**
	 * 
	 * @param mView
	 */
	private void loadButton(View mView) {
		showLeft = (ImageView) mView.findViewById(R.id.title_bar_showleft);
		fabu = (ImageView) mView.findViewById(R.id.title_bar_publish);
		viewFlipper = (ViewFlipper) mView.findViewById(R.id.mViewFliper_vf);
		guanggao1 = (ImageView) mView.findViewById(R.id.ad_first);
		guanggao2 = (ImageView) mView.findViewById(R.id.ad_second);
		guanggao3 = (ImageView) mView.findViewById(R.id.ad_three);
		guanggao4 = (ImageView) mView.findViewById(R.id.ad_four);
		guanggao5 = (ImageView) mView.findViewById(R.id.ad_five);
		mGridView = (MyGridView) mView.findViewById(R.id.gridview);

		mPullRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setMode(Mode.BOTH);
		mPullRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshScrollView.scrollTo(0, 0);
	}

	/**
	 */
	private void listenerButton() {

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mAdapter.setSeclection(arg2);
				mAdapter.notifyDataSetChanged();
				if (mList.get(arg2).getIs_en().equals("1")) {

					Intent intent = new Intent();
					intent.setClass(getActivity(),
							ShoujiEnterpriseActivity.class);
					intent.putExtra("id", mList.get(arg2).getId());
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(), ShoujiCommenActivity.class);
					intent.putExtra("id", mList.get(arg2).getId());
					startActivity(intent);
				}
			}
		});

		/**
		 */

		viewFlipper.setOnClickListener(clickListener);
		displayRatio_selelct(currentPage);
	}

	private void getdata() {

		GetProducts getProducts = new GetProducts();
		getProducts.execute();
	}

	/**
	 */
	private void refreshListener() {
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {

						if (WebIsConnectUtil.showNetState(getActivity())) {

							productsManager.GetProductInfo(getActivity(),  
									classid, page_down, uptime );
							productsManager
									.getProductInfoIml(new IgetProductInfo() {
										@Override
										public void getProductInfo(
												List<ProductsBean> productsBeans) {
											if ((productsBeans != null) && (productsBeans.size() != 0)) {
												for (ProductsBean productsBean : productsBeans) {
													mList.addFirst(productsBean);
													flag = true;
												}
												MyUtlis.sortListOrder(mList);
												uptime = mList.get(0)
														.getCtime();
												mAdapter.notifyDataSetChanged();
												mPullRefreshScrollView
														.onRefreshComplete();
											} else {
												mPullRefreshScrollView
														.onRefreshComplete();
											}
										}
									});
						} else {
							mPullRefreshScrollView.onRefreshComplete();
						}

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {

						if (WebIsConnectUtil.showNetState(getActivity())) {

							productsManager.GetProductInfo(getActivity(),  
									classid, page_up, "0" );
							productsManager
									.getProductInfoIml(new IgetProductInfo() {
										@Override
										public void getProductInfo(
												List<ProductsBean> productsBeans) {
											if ((productsBeans != null) && (productsBeans.size() != 0)) {
												for (ProductsBean productsBean : productsBeans) {
													mList.addLast(productsBean);
												}
												MyUtlis.sortListOrder(mList);
												page_up++;
												mAdapter.notifyDataSetChanged();
												mPullRefreshScrollView
														.onRefreshComplete();
											} else {
												mPullRefreshScrollView
														.onRefreshComplete();
											}

										}
									});
						} else {
							mPullRefreshScrollView.onRefreshComplete();
						}

					}

				});

	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_NEXT:
				if (showNext) {
					showNextView();
				} else {
					showPreviousView();
				}
				break;

			default:
				break;
			}
		}

	};

	/**
	 * 
	 * @author user
	 * 
	 */
	public class GetProducts extends AsyncTask<Void, Void, LinkedList<ProductsBean>> {
		public GetProducts() {
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected LinkedList<ProductsBean> doInBackground(Void... params) {//shouye
//			String result = MyUtlis.readFiles(MyConstants.PRODUCT_INDEX,
//					getActivity());
			return DbProductManager.getInstance(context).getProductByCategory("index");
//			return result;
		}

		@Override
		protected void onPostExecute(LinkedList<ProductsBean> productsBeans) {
//			List<ProductsBean> productsBeans = JsonParse
//					.parseProductsJson(result);
			mList.clear();
			if (productsBeans != null) {
				mList.addAll(productsBeans);
				MyUtlis.sortListOrder(mList);
				uptime = mList.get(0).getCtime();
				mGridView.setAdapter(mAdapter);
			} else {
				uptime = new String("11");
				mGridView.setAdapter(mAdapter);
			}
			super.onPostExecute(productsBeans);
		}
	}

	Thread thread = new Thread() {
		@Override
		public void run() {
			while (isRun) {
				try {
					Thread.sleep(MyConstants.SLEEPTIME);
					Message msg = new Message();
					msg.what = SHOW_NEXT;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	};

	/**
	 */
	private void showNextView() {

		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_left_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_left_out));
		viewFlipper.showNext();
		currentPage++;
		if (currentPage == viewFlipper.getChildCount()) {
			displayRatio_normal(currentPage - 1);
			currentPage = 0;
			displayRatio_selelct(currentPage);
		} else {
			displayRatio_selelct(currentPage);
			displayRatio_normal(currentPage - 1);
		}

	}

	/**
	 */
	private void showPreviousView() {
		displayRatio_selelct(currentPage);
		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_right_in));
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context,
				R.anim.push_right_out));
		viewFlipper.showPrevious();
		currentPage--;
		if (currentPage == -1) {
			displayRatio_normal(currentPage + 1);
			currentPage = viewFlipper.getChildCount() - 1;
			displayRatio_selelct(currentPage);
		} else {
			displayRatio_selelct(currentPage);
			displayRatio_normal(currentPage + 1);
		}
	}

	/**
	 * 
	 * @param id
	 */
	private void displayRatio_selelct(int id) {
		int[] ratioId = { R.id.home_ratio_img_05, R.id.home_ratio_img_04,
				R.id.home_ratio_img_03, R.id.home_ratio_img_02,
				R.id.home_ratio_img_01 };
		ImageView img = (ImageView) view.findViewById(ratioId[id]);

		img.setSelected(true);
	}

	private void displayRatio_normal(int id) {
		int[] ratioId = { R.id.home_ratio_img_05, R.id.home_ratio_img_04,
				R.id.home_ratio_img_03, R.id.home_ratio_img_02,
				R.id.home_ratio_img_01 };
		ImageView img = (ImageView) view.findViewById(ratioId[id]);
		img.setSelected(false);
	}

	private LinkedList<AdBean> adSortList;

	private void setAd() {

		adSortList = new LinkedList<AdBean>();
		AdBean adBean = new AdBean("", MyConstants.PICTURE_URL, "", "", "", "",
				"");
//		String ad = sp.getString("ad_index", "");
//		adBeans = JsonParse.parseAdJson(ad);
		adBeans = DbAdManager.getInstance(context).getAdByCategory("ad_index");
		if (adBeans != null) {
//			MyUtlis.sortAdOrder(adBeans);
			 adSortList.add(0, adBean);
			adSortList.add(1, adBean);
			adSortList.add(2, adBean);
			adSortList.add(3, adBean);
			adSortList.add(4, adBean); 
			for (int i = 0; i < adBeans.size(); i++) {
				int order = Integer.valueOf(adBeans.get(i).getOrder_sort());
				switch (order) {
				case 1:
					adSortList.remove(0);
					adSortList.add(0, adBeans.get(i));
					break;
				case 2:
					adSortList.remove(1);
					adSortList.add(1, adBeans.get(i));
					break;
				case 3:
					adSortList.remove(2);
					adSortList.add(2, adBeans.get(i));
					break;
				case 4:
					adSortList.remove(3);
					adSortList.add(3, adBeans.get(i));
					break;
				case 5:
					adSortList.remove(4);
					adSortList.add(4, adBeans.get(i));
					break;
				default:
					break;
				}
			}
			imageLoader.displayImage(MyConstants.PICTURE_URL
					+ adSortList.get(0).getPic(), guanggao1, options);
			imageLoader.displayImage(MyConstants.PICTURE_URL
					+ adSortList.get(1).getPic(), guanggao2, options);
			imageLoader.displayImage(MyConstants.PICTURE_URL
					+ adSortList.get(2).getPic(), guanggao3, options);
			imageLoader.displayImage(MyConstants.PICTURE_URL
					+ adSortList.get(3).getPic(), guanggao4, options);
			imageLoader.displayImage(MyConstants.PICTURE_URL
					+ adSortList.get(4).getPic(), guanggao5, options);

		} else {
			imageLoader.displayImage(MyConstants.PICTURE_URL, guanggao1,
					options);
			imageLoader.displayImage(MyConstants.PICTURE_URL, guanggao2,
					options);
			imageLoader.displayImage(MyConstants.PICTURE_URL, guanggao3,
					options);
			imageLoader.displayImage(MyConstants.PICTURE_URL, guanggao4,
					options);
			imageLoader.displayImage(MyConstants.PICTURE_URL, guanggao5,
					options);
		}
		mPullRefreshScrollView.scrollTo(0, 0);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (adBeans != null) {
				click(currentPage);
			} else {
				MyUtlis.toastInfo(context,
						getResources().getString(R.string.ad_toast));
			}

		}
	};

	private void click(int i) {

		if (adSortList.get(i).getType().toString().equals("1")) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), ShoujiCommenActivity.class);
			intent.putExtra("id", adSortList.get(i).getUrl().toString());
			startActivity(intent);
		} else if (adSortList.get(i).getType().toString().equals("2")) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), LogoItemListActivity.class);
			intent.putExtra("id", adSortList.get(i).getUrl().toString());
			intent.putExtra("title", adSortList.get(i).getName() + "");
			startActivity(intent);
		} else if (adSortList.get(i).getType().toString().equals("3")) {
			Intent viewIntent = new Intent(
					"android.intent.action.VIEW",
					Uri.parse("http://" + adSortList.get(i).getUrl().toString()));
			startActivity(viewIntent);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		 if (flag) {//save the data to file
			//to json
				DbProductManager.getInstance(context).deleteByCategory("index");
 				MyUtlis.sortListOrder(mList);
			 for (int i = 0; i < mList.size(); i++) {
				if (i <= 20) {
					ProductsBean bean = mList.get(i);
					bean.setCategory("index");
					DbProductManager.getInstance(context).insert(bean);
				}
			}
		} 
		
	}
	@Override
	public void onStop() {
			thread.interrupt();
			System.gc();
		super.onStop();
	}
}
