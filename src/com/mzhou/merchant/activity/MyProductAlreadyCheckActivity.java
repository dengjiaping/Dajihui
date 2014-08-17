package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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

public class MyProductAlreadyCheckActivity extends Activity {

	private LinkedList<ProductsBean> mList;
	private ProductsManager productsManager;
	private MyGridProductAdapter2 mAdapter;
 	private Context context;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private MyGridView mGridView;
 	private int page;
	private String uid;
	private String uptime;
	private String uid_enterprise;
	private boolean isEnterprise;
	 private QQShare mQQShare = null;
	  private QzoneShare mQzoneShare = null;
	  private com.tencent.tauth.Tencent	mTencent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
	         
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) {
	            Log.e("@"+this.getClass().getName(), "Crash dump", ex);
	        }
	    });
		setContentView(R.layout.view_pager_yifabu);
		init();
		loadButton();
		getdata();
		listenerButton();
		refreshListener();
	}

	/**
	 * 初始化
	 * 
	 */
	private void init() {
	 	mTencent = Tencent.createInstance(MyConstants.APP_ID, MyProductAlreadyCheckActivity.this);
    	mQQShare = new QQShare(MyProductAlreadyCheckActivity.this, mTencent.getQQToken());
    	 mQzoneShare = new QzoneShare(MyProductAlreadyCheckActivity.this, mTencent.getQQToken());
		if (DbLoginManager.getInstance(MyProductAlreadyCheckActivity.this).getLoginStatus()) {
			UserInfoBean userInfoBean = DbUserManager.getInstance(MyProductAlreadyCheckActivity.this).getLogingUserInfo();
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
		context = MyProductAlreadyCheckActivity.this;
		mList = new LinkedList<ProductsBean>();
		mAdapter = new MyGridProductAdapter2(MyProductAlreadyCheckActivity.this, mList);
		productsManager = new ProductsManager();
		page = 2;
	}

	/**
	 * 加载需要的控件
	 * 
	 * @param mView
	 */
	private void loadButton() {
		mGridView = (MyGridView) findViewById(R.id.gridview);
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
						MyProductAlreadyCheckActivity.this);
				LayoutInflater inflater = LayoutInflater
						.from(MyProductAlreadyCheckActivity.this);
				View view1 = inflater.inflate(R.layout.dialog_menu_product,
						null);
				Button look = (Button) view1.findViewById(R.id.look);
				Button share = (Button) view1.findViewById(R.id.share);
				Button delete = (Button) view1.findViewById(R.id.delete);
				Button refresh = (Button) view1.findViewById(R.id.refresh);
				Button cancel = (Button) view1.findViewById(R.id.cancel);
				Button edit = (Button) view1.findViewById(R.id.edit);

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
	share.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						 //share_qq_zone  share_qq  share_qq_cancel
						AlertDialog.Builder builder = new AlertDialog.Builder(MyProductAlreadyCheckActivity.this);
						LayoutInflater inflater = LayoutInflater.from(MyProductAlreadyCheckActivity.this);
						View view = inflater.inflate(R.layout.share_to_qq, null);
						view.setMinimumWidth(2000);
						Button share_qq_cancel = (Button) view.findViewById(R.id.share_qq_cancel);
						TextView share_qq = (TextView) view.findViewById(R.id.share_qq);
						TextView share_qq_zone = (TextView) view.findViewById(R.id.share_qq_zone);
						builder.setView(view);
						final AlertDialog dialog = builder.create();
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						WindowManager.LayoutParams wmlp = dialog.getWindow()
								.getAttributes();
						wmlp.gravity = Gravity.BOTTOM;
						dialog.show();
						share_qq_cancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								 dialog.dismiss();
							}
						});
						share_qq.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								//share to qq
							 	final Bundle params = new Bundle();
								String title = "【大机汇】"+"分享产品";
								String summary = 	"分享了一个新产品【"+mList.get(arg2).getBrand()+"】，点击查看更多详细信息";
								String imageUrl = MyUtlis.getHeadPic(mList.get(arg2).getPic(), MyProductAlreadyCheckActivity.this);
								params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
								params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
								params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
								params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);
								params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "大机汇");
								 params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
						            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,0x00);  
						            	doShareToQQ(params);
							}
						});
						share_qq_zone.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								final Bundle params = new Bundle();
				                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
				            	String title = "【大机汇】"+"分享产品";
								String summary = 	"分享了一个新产品【"+mList.get(arg2).getBrand()+"】，点击查看更多详细信息";
				                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
				                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,summary);
				                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
				                String imageUrl = MyUtlis.getHeadPic(mList.get(arg2).getPic(), MyProductAlreadyCheckActivity.this);
				                // 支持传多个imageUrl
				                ArrayList<String> imageUrls = new ArrayList<String>();
				                imageUrls.add(imageUrl);
				                // params.putString(Tencent.SHARE_TO_QQ_IMAGE_URL, imageUrl);
				                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
				                doShareToQzone(params);
							}
						});
						
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
 
  					showDialog(MyProductAlreadyCheckActivity.this,arg2);
							
					}

					
				});
				refresh.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						showRefreshDialog(MyProductAlreadyCheckActivity.this,arg2);

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
     * 用异步方式启动分享
     * @param params
     */
  
   private void doShareToQQ(final Bundle params) {
    	System.out.println("传入的参数是------"+params.toString());
        final Activity activity = MyProductAlreadyCheckActivity.this;
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                mQQShare.shareToQQ(activity, params, new IUiListener() {

                    @Override
                    public void onCancel() {
                    	System.out.println("qq share ---onCancel-----");
                    }

                    @Override
                    public void onComplete(Object response) {
                    	System.out.println("qq share --------");
                    	System.out.println(response.toString());
                    	System.out.println("qq share --------"); 
                    	MyUtlis.toastInfo(MyProductAlreadyCheckActivity.this, "分享成功!");
                    }

                    @Override
                    public void onError(UiError e) {
                    	System.out.println("qq share --------"+e.errorMessage);
                    }

                });
            }
        }).start();
    } 
	private void showRefreshDialog(  Context context, final int arg2) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage("你确定要刷新吗？");
		builder.setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){
			
			public void onClick(DialogInterface dialog, int which) {
				productsManager.AsynRefreshProduct(MyProductAlreadyCheckActivity.this, uid, mList.get(arg2).getId());
				productsManager.deleteProduct(new IDeleteProductInfo() {
					
					@Override
					public void deletProDuct(PublishProductBean productBean) {
						if ( "true".equals(productBean.getStatus())) {
							MyUtlis.toastInfo(MyProductAlreadyCheckActivity.this, getResources()
									.getString(R.string.refresh_ok));
							mList.remove(arg2);
							MyUtlis.sortListOrder(mList);
							mAdapter.notifyDataSetChanged();
							mAdapter.notifyDataSetChanged();
						} else {
							MyUtlis.toastInfo(MyProductAlreadyCheckActivity.this, getResources()
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
		/**
		 * 提示删除产品
		 * @param context
		 * @param arg2
		 */
	    private void showDialog(Context context,final int arg2){
	    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        builder.setTitle("提示");
	        builder.setMessage("你确定要删除吗？");
	        builder.setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					productsManager.AsynDelProduct(MyProductAlreadyCheckActivity.this, mList.get(arg2).getIs_en(), uid, mList
							.get(arg2).getId());
				 productsManager.deleteProduct(new IDeleteProductInfo() {
					
					@Override
					public void deletProDuct(PublishProductBean productBean) {
						// TODO Auto-generated method stub
						if ( "true".equals(productBean.getStatus())) {
							MyUtlis.toastInfo(MyProductAlreadyCheckActivity.this, productBean.getMsg());
							mList.remove(arg2);
							MyUtlis.sortListOrder(mList);
							mAdapter.notifyDataSetChanged();
						} else {
							MyUtlis.toastInfo(MyProductAlreadyCheckActivity.this, productBean.getMsg());
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
 
	private void getdata() {
		if (isEnterprise) {
//			Context context, String uid,
//			 int page, String uptime, String is_en, String is_show)
			productsManager.AsynGetMyProducts(
					MyProductAlreadyCheckActivity.this, uid_enterprise,  1, "0",
					"1", "1");
			productsManager.getProductInfoIml(new IgetProductInfo() {
				@Override
				public void getProductInfo(List<ProductsBean> productsBeans) {
					if (productsBeans != null) {
						for (ProductsBean productsBean : productsBeans) {
							mList.addLast(productsBean);
						}
						MyUtlis.sortListOrder(mList);
						uptime = mList.get(1).getCtime();
						mGridView.setAdapter(mAdapter);
					}

				}
			});

		} else {

			productsManager.AsynGetMyProducts(
					MyProductAlreadyCheckActivity.this, uid,  1, "0", "0", "1");
			productsManager.getProductInfoIml(new IgetProductInfo() {
				@Override
				public void getProductInfo(List<ProductsBean> productsBeans) {
					if (productsBeans != null) {
						for (ProductsBean productsBean : productsBeans) {
							mList.addLast(productsBean);
						}
						MyUtlis.sortListOrder(mList);
						uptime = mList.get(1).getCtime();
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
									.showNetState(MyProductAlreadyCheckActivity.this)) {

								productsManager.AsynGetMyProducts(
										MyProductAlreadyCheckActivity.this,
										uid_enterprise, 1, uptime,
										"1", "1");
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
									.showNetState(MyProductAlreadyCheckActivity.this)) {

								productsManager.AsynGetMyProducts(
										MyProductAlreadyCheckActivity.this,
										uid_enterprise, page, "0",
										"1", "1");
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
									.showNetState(MyProductAlreadyCheckActivity.this)) {
								productsManager.AsynGetMyProducts(
										MyProductAlreadyCheckActivity.this, uid, 
										1, uptime, "0", "1");
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
									.showNetState(MyProductAlreadyCheckActivity.this)) {
								productsManager.AsynGetMyProducts(
										MyProductAlreadyCheckActivity.this, uid, 
										page, uptime, "0", "1");
								productsManager
										.getProductInfoIml(new IgetProductInfo() {
											@Override
											public void getProductInfo(
													List<ProductsBean> productsBeans) {
												if ((productsBeans != null)
														&& (productsBeans
																.size() != 0)) {
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
	   @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        if (mQzoneShare != null) {
	            mQzoneShare.releaseResource();
	            mQzoneShare = null;
	        }
	        if (mQQShare != null) {
	            mQQShare.releaseResource();
	            mQQShare = null;
	        }
	        if (mTencent != null) {
	        	mTencent = null;
			}
	    }
	   
	   /**
	     * 用异步方式启动分享
	     * @param params
	     */
	    private void doShareToQzone(final Bundle params) {
	        final Activity activity = MyProductAlreadyCheckActivity.this;
	        new Thread(new Runnable() {
	            
	            @Override
	            public void run() {
	                // TODO Auto-generated method stub
	            	mQzoneShare.shareToQzone(activity, params, new IUiListener() {

	                    @Override
	                    public void onCancel() {
	                      System.out.println("oncancel  ");
	                    }

	                    @Override
	                    public void onError(UiError e) {
	                        // TODO Auto-generated method stub
	                        System.out.println("----erro-----"+e.errorMessage);
	                    }

						@Override
						public void onComplete(Object response) {
							// TODO Auto-generated method stub
							 System.out.println("onComplete: " + response.toString());
						}

	                });
	            }
	        }).start();}
}
