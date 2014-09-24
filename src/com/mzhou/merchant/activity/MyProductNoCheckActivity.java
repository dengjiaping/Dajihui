package com.mzhou.merchant.activity;

import java.util.LinkedList;
import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.activity.ShoujiCommenActivity;
import com.mzhou.merchant.adapter.MyGridProductAdapter2;
import com.mzhou.merchant.dao.IProduct.IDeleteProductInfo;
import com.mzhou.merchant.dao.IProduct.IgetProductInfo;
import com.mzhou.merchant.dao.biz.ProductsManager;
import com.mzhou.merchant.db.manager.DbLoginManager;
import com.mzhou.merchant.db.manager.DbUserManager;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.model.PublishProductBean;
import com.mzhou.merchant.model.UserInfoBean;
import com.mzhou.merchant.myview.MyGridView;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyProductNoCheckActivity extends Activity {
	private LinkedList<ProductsBean> mList;
	private ProductsManager productsManager;
	private MyGridProductAdapter2 mAdapter;
 	private Context context;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private MyGridView mGridView;
	private int page;
	private String uid;
	private String uid_enterprise;
	private boolean isEnterprise;
	private String uptime;
	private TextView tView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_pager_yifabu);
		init();
		loadButton();
		refreshListener();
		listenerButton();
		getdata();
	}
	/**
	 * 初始化
	 * 
	 */
	private void init() {
		if (DbLoginManager.getInstance(MyProductNoCheckActivity.this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(this).getLogingUserInfo();
			if ( userInfoBean.getUsertype().equals("1")) {
				uid_enterprise = userInfoBean.getUid();
				isEnterprise= true;
			}else {
				uid = userInfoBean.getUid();
				isEnterprise= false;
			}
		}else {
			uid_enterprise ="0";
			isEnterprise = false;
			uid ="0";
		}
		context = MyProductNoCheckActivity.this;
		mList = new LinkedList<ProductsBean>();
		mAdapter = new MyGridProductAdapter2(MyProductNoCheckActivity.this, mList);
		productsManager = new ProductsManager();
		page = 2;
		uptime = new String("0");
	}

	/**
	 * 加载需要的控件
	 * 
	 * @param mView
	 */
	private void loadButton() {
		mGridView = (MyGridView) findViewById(R.id.gridview);
		tView = (TextView) findViewById(R.id.tv_viewpager);
		tView.setVisibility(View.VISIBLE);
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setMode(Mode.BOTH);
		mPullRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshScrollView.scrollTo(0, 0);
	}

	/**
	 * 监听广告和产品事件
	 */
	private void listenerButton() {

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				mAdapter.setSeclection(arg2);
				mAdapter.notifyDataSetChanged();

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyProductNoCheckActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(MyProductNoCheckActivity.this);
				View view1 = inflater.inflate(R.layout.dialog_menu_product,
						null);
				Button look = (Button) view1.findViewById(R.id.look);
				Button share = (Button) view1.findViewById(R.id.share);
				share.setVisibility(View.GONE);
				Button delete = (Button) view1.findViewById(R.id.delete);
				Button refresh = (Button) view1.findViewById(R.id.refresh);
				Button edit = (Button) view1.findViewById(R.id.edit);
				Button cancel = (Button) view1.findViewById(R.id.cancel);
				view1.setMinimumWidth(2000);

				builder.setView(view1);
				final AlertDialog dialog = builder.create();
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				WindowManager.LayoutParams wmlp = dialog.getWindow()
						.getAttributes();
				wmlp.gravity = Gravity.BOTTOM;
				dialog.show();
				look.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						if (mList.get(arg2).getIs_en().equals("1")) {
							Intent intent = new Intent();
							intent.setClass(getBaseContext(),
									ShoujiEnterpriseActivity.class);
							intent.putExtra("id", mList.get(arg2).getId());
							startActivity(intent);
						} else {
							Intent intent = new Intent();
							intent.setClass(getBaseContext(),
									ShoujiCommenActivity.class);
							intent.putExtra("id", mList.get(arg2).getId());
							startActivity(intent);
						}
					}
				});
				 
				edit.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
						if (mList.get(arg2).getIs_en().equals("1")) {
							Intent intent = new Intent();
							intent.setClass(getBaseContext(),
									EditShoujiEnterpriseActivity.class);
							intent.putExtra("id", mList.get(arg2).getId());
							startActivity(intent);
						} else {
							Intent intent = new Intent();
							intent.setClass(getBaseContext(),
									EditShoujiCommenActivity.class);
							intent.putExtra("id", mList.get(arg2).getId());
							startActivity(intent);
						}
					}
				});
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						showDelDialog(MyProductNoCheckActivity.this, arg2);
					}

				});
				refresh.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						showRefreshDialog(MyProductNoCheckActivity.this,arg2);

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

	}
 
  
	/**
	 * 提示删除产品
	 * 
	 * @param context
	 * @param arg2
	 */
	private void showDelDialog(Context context, final int arg2) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("你确定要删除吗？");
        builder.setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				productsManager.AsynDelProduct(MyProductNoCheckActivity.this, mList.get(arg2).getIs_en(), uid, mList
						.get(arg2).getId());
			 productsManager.deleteProduct(new IDeleteProductInfo() {
				
				@Override
				public void deletProDuct(PublishProductBean productBean) {
					if ( "true".equals(productBean.getStatus())) {
						MyUtlis.toastInfo(MyProductNoCheckActivity.this, productBean.getMsg());
						mList.remove(arg2);
						MyUtlis.sortListOrder(mList);
						mAdapter.notifyDataSetChanged();
					} else {
						MyUtlis.toastInfo(MyProductNoCheckActivity.this, productBean.getMsg());
					}
				}
			});
			 
				
			
			 }
        	
        });
        
        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
			}
        	
        });
        
        builder.show();
    }
	private void showRefreshDialog(  Context context, final int arg2) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage("你确定要刷新吗？");
		builder.setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which) {
				productsManager.AsynRefreshProduct(MyProductNoCheckActivity.this, uid, mList.get(arg2).getId());
				productsManager.deleteProduct(new IDeleteProductInfo() {
					
					@Override
					public void deletProDuct(PublishProductBean productBean) {
						if ( "true".equals(productBean.getStatus())) {
							MyUtlis.toastInfo(MyProductNoCheckActivity.this, getResources()
									.getString(R.string.refresh_ok));
							mList.remove(arg2);
							MyUtlis.sortListOrder(mList);
							mAdapter.notifyDataSetChanged();
						} else {
							MyUtlis.toastInfo(MyProductNoCheckActivity.this, getResources()
									.getString(R.string.refresh_no));
						}
					}
				});
			}
			
		});
		
		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which) {
			}
			
		});
		
		builder.show();
	}
	 private Handler handler = new Handler(){public void handleMessage(android.os.Message msg) {
		   switch (msg.what) {
		case 1:
			
			break;
		case 2:
			PublishProductBean databack =(PublishProductBean) msg.obj;
			if ( "true".equals(databack.getStatus())) {
				MyUtlis.toastInfo(MyProductNoCheckActivity.this, databack.getMsg());
				mList.remove(msg.arg2);
				MyUtlis.sortListOrder(mList);
				mAdapter.notifyDataSetChanged();
			} else {
				MyUtlis.toastInfo(MyProductNoCheckActivity.this, databack.getMsg());
			}
			break;

		default:
			break;
		}
	   };};
	private void getdata() {
		if (isEnterprise) {
//			Context context, String uid,
//			 int page, String uptime, String is_en, String is_show)
			productsManager.AsynGetMyProducts(
					MyProductNoCheckActivity.this, uid_enterprise,  1,
					"0", "1", "0");
			productsManager.getProductInfoIml(new IgetProductInfo() {
				@Override
				public void getProductInfo(List<ProductsBean> productsBeans) {
					if (productsBeans != null) {
						for (ProductsBean productsBean : productsBeans) {
							mList.addLast(productsBean);
						}
						MyUtlis.sortListOrder(mList);
						if (mList.size() != 0) {
							uptime = mList.get(0).getCtime();
						}
			
						mGridView.setAdapter(mAdapter);
					}

				}
			});

		} else {
//			Context context, String uid,
//			 int page, String uptime, String is_en, String is_show)
			productsManager.AsynGetMyProducts(
					MyProductNoCheckActivity.this, uid,  1, "0", "0",
					"0");
			productsManager.getProductInfoIml(new IgetProductInfo() {
				@Override
				public void getProductInfo(List<ProductsBean> productsBeans) {
					if (productsBeans != null) {
						for (ProductsBean productsBean : productsBeans) {
							mList.addLast(productsBean);
						}
						MyUtlis.sortListOrder(mList);
						if (mList.size() != 0) {
							uptime = mList.get(0).getCtime();
						}
						mGridView.setAdapter(mAdapter);
					}

				}
			});

		}
	}

	/**
	 * 刷新监听
	 */
	private void refreshListener() {
		if (isEnterprise) {

			mPullRefreshScrollView
					.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
						@Override
						public void onPullDownToRefresh(
								PullToRefreshBase<ScrollView> refreshView) {
							if (WebIsConnectUtil
									.showNetState(MyProductNoCheckActivity.this)) {
//								Context context, String uid,
//								 int page, String uptime, String is_en, String is_show)
								productsManager.AsynGetMyProducts(
										MyProductNoCheckActivity.this,
										uid_enterprise,  1, uptime,
										"1", "0");
								productsManager
										.getProductInfoIml(new IgetProductInfo() {
											@Override
											public void getProductInfo(
													List<ProductsBean> productsBeans) {
												if ((productsBeans != null)
														&& (productsBeans
																.size() !=0)) {
													for (ProductsBean productsBean : productsBeans) {
														mList.addLast(productsBean);
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
							if (WebIsConnectUtil
									.showNetState(MyProductNoCheckActivity.this)) {
//								Context context, String uid,
//								 int page, String uptime, String is_en, String is_show)
								productsManager.AsynGetMyProducts(
										MyProductNoCheckActivity.this,
										uid_enterprise,  page, "0",
										"1", "0");
								productsManager
										.getProductInfoIml(new IgetProductInfo() {
											@Override
											public void getProductInfo(
													List<ProductsBean> productsBeans) {
												if ((productsBeans != null)
														&& (productsBeans
																.size() !=0)) {
													for (ProductsBean productsBean : productsBeans) {
														mList.addLast(productsBean);
													}
													MyUtlis.sortListOrder(mList);
													page++;
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

		} else {

			mPullRefreshScrollView
					.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
						@Override
						public void onPullDownToRefresh(
								PullToRefreshBase<ScrollView> refreshView) {
							if (WebIsConnectUtil
									.showNetState(MyProductNoCheckActivity.this)) {
//								Context context, String uid,
//								 int page, String uptime, String is_en, String is_show)
								productsManager.AsynGetMyProducts(
										MyProductNoCheckActivity.this, uid,
										 1, uptime, "0", "0");
								productsManager
										.getProductInfoIml(new IgetProductInfo() {
											@Override
											public void getProductInfo(
													List<ProductsBean> productsBeans) {
												if ((productsBeans != null)
														&& (productsBeans
																.size() !=0)) {
													for (ProductsBean productsBean : productsBeans) {
														mList.addLast(productsBean);
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
							if (WebIsConnectUtil
									.showNetState(MyProductNoCheckActivity.this)) {
//								Context context, String uid,
//								 int page, String uptime, String is_en, String is_show)
								productsManager.AsynGetMyProducts(
										MyProductNoCheckActivity.this, uid,
										 page, "0", "0", "0");
								productsManager
										.getProductInfoIml(new IgetProductInfo() {
											@Override
											public void getProductInfo(
													List<ProductsBean> productsBeans) {
												if ((productsBeans != null)
														&& (productsBeans
																.size() !=0)) {
													for (ProductsBean productsBean : productsBeans) {
														mList.addLast(productsBean);
													}
													MyUtlis.sortListOrder(mList);
													page++;
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
	}

	 
}
