package com.mzhou.merchant.service;

import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mzhou.merchant.db.manager.DbActivityManager;
import com.mzhou.merchant.db.manager.DbAdManager;
import com.mzhou.merchant.db.manager.DbAttachManager;
import com.mzhou.merchant.db.manager.DbJobManager;
import com.mzhou.merchant.db.manager.DbNewsManager;
import com.mzhou.merchant.db.manager.DbProductManager;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.GetPhoneNum;
import com.mzhou.merchant.utlis.GetPhoneNum.UserBean;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.SIMCarInfo;

public class DownLoadService extends Service {
//	private SharedPreferences sp;
@Override
	public void onCreate() {
	
  
		super.onCreate();
		new Thread(new downloadThread()).start();
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		sp = getSharedPreferences("phonemerchant", 1);
//		new Thread(new indexThread()).start();
		
		return START_NOT_STICKY;
	}

 
	/**
	 * save the ad data
	 * @param category 
	 * @param json
	 */
	private void saveAdData(String category, String json) {
		DbAdManager adManager = DbAdManager.getInstance(getBaseContext());

		LinkedList<AdBean> adBeans = JsonParse.parseAdJson(json);
		if (adBeans == null) {
			return;
		}
		if (adBeans.size() != 0) {
			if (adManager.isExist(category)) {
				adManager.deleteByCategory(category);
			}
		}
		for (int j = 0; j < adBeans.size(); j++) {
			AdBean adBean = adBeans.get(j);
			adBean.setCategory(category);
			long id = adManager.insert(adBean);
			if (id != -1) {
			}
		}
	}
	/**
	 * save the qiugou and zhaoshang data
	 * @param category
	 * @param json
	 */
	private void saveAttachData(String category, String json) {
		DbAttachManager attachManager = DbAttachManager.getInstance(getBaseContext());
		LinkedList<AttactBean> beans = JsonParse.parseAttactJson(json);
		if (beans == null) {
			return;
		}
		if (beans.size() != 0) {
			if(attachManager.isExist(category)){
				attachManager.deleteByCategory(category);
			}
		}
		for (int j = 0; j < beans.size(); j++) {
			AttactBean attactBean = beans.get(j);
			attactBean.setCategory(category);
			long id = attachManager.insert(attactBean);
			if (id != -1) {
			}
		}
	}
	/**
	 * save the news data
	 * @param json
	 */
	private void saveNewsData(String json) {
		DbNewsManager manager = DbNewsManager.getInstance(getBaseContext());
		List<NewsBean> beans = JsonParse.parseNewsJson(json);
		if (beans == null) {
			return;
		}
		if (beans.size() != 0) {
			manager.deleteAll();
		}
		for (int j = 0; j < beans.size(); j++) {
			NewsBean attactBean = beans.get(j);
			long id = manager.insert(attactBean);
			if (id != -1) {
			}
		}
	}
	/**
	 * save activity data
	 * @param json
	 */
	private void saveActivityData(String json) {
		DbActivityManager manager = DbActivityManager.getInstance(getBaseContext());
		List<ActivityBean> beans = JsonParse.parseActivityJson(json);
		if (beans == null) {
			return;
		}
		if (beans.size() != 0) {
			manager.deleteAll();
		}
		for (int j = 0; j < beans.size(); j++) {
			ActivityBean attactBean = beans.get(j);
			long id = manager.insert(attactBean);
			if (id != -1) {
			}
		}
	}
	/**
	 * save job data
	 * @param json
	 */
	private void saveJobyData(String json) {
		DbJobManager manager = DbJobManager.getInstance(getBaseContext());
		List<JobBean> beans = JsonParse.parseJobJson(json);
		if (beans == null) {
			return;
		}
		if (beans.size() != 0) {
			manager.deleteAll();
		}
		for (int j = 0; j < beans.size(); j++) {
			JobBean attactBean = beans.get(j);
			long id = manager.insert(attactBean);
			if (id != -1) {
			}
		}
	}
	/**
	 * save product data
	 * @param category
	 * @param json
	 */
	private void saveProductData(String category,String json) {
		DbProductManager manager = DbProductManager.getInstance(getBaseContext());
		List<ProductsBean> beans = JsonParse.parseProductsJson(json);
		if (beans == null) {
			return;
		}
		if (beans.size() != 0) {
			if (manager.isExist(category)) {
				manager.deleteByCategory(category);
			}
		}
		for (int j = 0; j < beans.size(); j++) {
			ProductsBean attactBean = beans.get(j);
			attactBean.setCategory(category);
			long id = manager.insert(attactBean);
			if (id != -1) {
			}
		}
	}

// 	class indexThread implements Runnable {
//
//		@Override
//		public void run() {
//			//首页，classid = 0
//			String index = GetDataByPostUtil.getProductInfo(getBaseContext(),
//					MyConstants.PRODUCT_URL, "prolist",  0, 1, "0");
//
//			if (!isEmpity(index)) {
////				writeFiles(index, MyConstants.PRODUCT_INDEX);
//				saveProductData("index",index);
//			}
//		}
//
//	}
// 
	class downloadThread implements Runnable {

		@Override
		public void run() {

//			首页，classid = 0
			String index = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist",  0, 1, "0");

			if (!isEmpity(index)) {
//				writeFiles(index, MyConstants.PRODUCT_INDEX);
				saveProductData("index",index);
			}
		
			
			

			//首页
			String ad_index = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "ind", 0);
			if (!isEmpity(ad_index)) {
				saveAdData("ad_index", ad_index);
			}
			//今日手机招商
			String ad_main = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "mer", 0);
			if (!isEmpity(ad_main)) {
				saveAdData("ad_main", ad_main);
				
			}
			//品牌国内
			String ad_pinpai = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.PINGPAI);
			if (!isEmpity(ad_pinpai)) {
				saveAdData("ad_pinpai", ad_pinpai);
				
			}
			//五码
			String ad_wuma = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.WUMA);
			if (!isEmpity(ad_wuma)) {
				
				saveAdData("ad_wuma", ad_wuma);
			}
			//三马
			String ad_sanma = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.SANMA);
			if (!isEmpity(ad_sanma)) {
				saveAdData("ad_sanma", ad_sanma);
				
			}
			//外单
			String ad_waidan = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.WAIDAN);
			if (!isEmpity(ad_waidan)) {
				
				saveAdData("ad_waidan", ad_waidan);
			}
			//手表手机
			String ad_td = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.TD);
			if (!isEmpity(ad_td)) {
				
				saveAdData("ad_td", ad_td);
			}
			//品牌厂家
			String ad_brand = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "bra", 0);
			if (!isEmpity(ad_brand)) {
				
				saveAdData("ad_brand", ad_brand);
			}
			//个性手机
			String ad_gx = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.GX);
			if (!isEmpity(ad_gx)) {
				saveAdData("ad_gx", ad_gx);
				
			}
			//4G手机
			String ad_fourg = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", MyConstants.FOURG);
			if (!isEmpity(ad_fourg)) {
				saveAdData("ad_fourg", ad_fourg);
				
			}
			//2g3g  品牌产品
			String ad_2g3g = GetDataByPostUtil.GetAdInfo(getBaseContext(),
					MyConstants.AD_URL, "list", "pro", 8);
			if (!isEmpity(ad_2g3g)) {
				saveAdData("ad_2g3g", ad_2g3g);
				
			}

			
			
			
			
			
			
			
			
			
			
			
			String zhaoshang = GetDataByPostUtil.GetAttactInfo(
					getBaseContext(), MyConstants.ATTRACT_URL, "list", "0", 1,
					"0");
			if (!isEmpity(zhaoshang)) {
				saveAttachData("zhaoshang", zhaoshang);
			}
			
			String qiugou = GetDataByPostUtil.GetAttactInfo(getBaseContext(),
					MyConstants.PURCHASE_URL, "list", "0", 1, "0");
			if (!isEmpity(qiugou)) {
				saveAttachData("qiugou", qiugou);
			}
			//
			String xinwen = GetDataByPostUtil.GetNewsInfo(getBaseContext(),
					MyConstants.NEWS_URL, "list", 1, "0");
			if (!isEmpity(xinwen)) {
				saveNewsData(xinwen);
				
			}
			
			String jobInfo = GetDataByPostUtil.GetJobInfo(getBaseContext(),
					MyConstants.JOB_URL, "list", 1, "0", "0");
			
			if (!isEmpity(jobInfo)) {
				
				saveJobyData(jobInfo);
			}
			String activity = GetDataByPostUtil.GetActivityInfo(
					getBaseContext(), MyConstants.ACTIVITY_URL, "list", 1, "0");
			if (!isEmpity(activity)) {
				
				saveActivityData(activity);
			}
			//品牌厂家
			String logo = GetDataByPostUtil.getLogoInfo(getBaseContext(),
					MyConstants.BRAND_URL, "list", "0", 1, "0");
			if (!isEmpity(logo)) {
 				saveProductData("logo",logo);
			}
			
			
			
			//今日手机招商
			String main = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "daylist",0, 1, "0");
			
			//品牌国内
			String pingpai = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist",  1, 1, "0");
			//五码
			String wuma = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist",  2, 1, "0");
			//三码
			String sanma = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist", 3, 1, "0");
			//外单手机
			String waidan = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist",  4, 1, "0");
			//td，手表手机
			String td = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist", 5, 1, "0");
			//个性手机
			String gx = GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist", 6, 1, "0");
			//4g手机
			String fourg= GetDataByPostUtil.getProductInfo(getBaseContext(),
					MyConstants.PRODUCT_URL, "prolist",  7, 1, "0");
			//品牌产品，也就是2g3g
			String logo_product = GetDataByPostUtil.getProductInfo(getBaseContext(), 
					MyConstants.PRODUCT_URL, "prolist", 8,1, "0");
			if (!isEmpity(main)) {
// 				writeFiles(main, MyConstants.PRODUCT_MAIN);
 				 	saveProductData("main",main);
			}
			if (!isEmpity(logo_product)) {
			//		writeFiles(logo_product, MyConstants.PRODUCT_LOGO_PRODUCT);
				saveProductData("logo_product",logo_product);
			}

			if (!isEmpity(pingpai)) {
			//	writeFiles(pingpai, MyConstants.PRODUCT_PINGPAI);
				 			saveProductData("pingpai",pingpai);
			}
			if (!isEmpity(wuma)) {
			//		writeFiles(wuma, MyConstants.PRODUCT_WUMA);
				 	saveProductData("wuma",wuma);
			}
			if (!isEmpity(sanma)) {
			//	writeFiles(sanma, MyConstants.PRODUCT_SANMA);
				 	saveProductData("sanma",sanma);
			}
			if (!isEmpity(waidan)) {
			//	writeFiles(waidan, MyConstants.PRODUCT_WAIDAN);
 				 	saveProductData("waidan",waidan);
			}
			if (!isEmpity(td)) {
			//	writeFiles(td, MyConstants.PRODUCT_TD);
				 		saveProductData("td",td);
			}
			if (!isEmpity(gx)) {
			//	writeFiles(gx, MyConstants.PRODUCT_GX);
				 		saveProductData("gx",gx);
			}
			if (!isEmpity(fourg)) {
			//	writeFiles(fourg, MyConstants.PRODUCT_FOURG);
 				saveProductData("fourg",fourg);
			}
			
		}

	}

	/**
	 * 判断是否为空，如果为空那么返回true
	 * 
	 * @param string
	 * @return
	 */
	private boolean isEmpity(String string) {
		if ((string != null) && (!string.equals("[]"))) {
			return false;
		}
		return true;

	}

	/**
	 * 保存文件工具类
	 * 
	 */
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

}
