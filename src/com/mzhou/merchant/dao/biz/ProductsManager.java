package com.mzhou.merchant.dao.biz;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.IProduct.IDeleteProductInfo;
import com.mzhou.merchant.dao.IProduct.IGetCommentInfo;
import com.mzhou.merchant.dao.IProduct.IGetCompanyInfo;
import com.mzhou.merchant.dao.IProduct.IgetEnterpriseProductInfoById;
import com.mzhou.merchant.dao.IProduct.IgetProductInfo;
import com.mzhou.merchant.dao.IProduct.IgetProductInfoById;
import com.mzhou.merchant.model.AboutCompany;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.ProductCommentBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.model.PublishProductBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;

public class ProductsManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	public ProductsManager() {
	}
	/**
	 * 获取公司
	 * @param context
	 * @param name
	 */
	public void AsynGetCompany(Context context, String name) {
		AsynGetCompany asynGetProductsInfo = new AsynGetCompany(context,
				"company", name);
		asynGetProductsInfo.execute();
	}
	/**
	 *删除产品
	 * @param context
	 * @param is_en
	 * @param uid
	 * @param id
	 */
	public void AsynDelProduct(Context context,  String is_en,String uid,String id,String youkuid) {
		AsynDeleteProduct asynGetProductsInfo = new AsynDeleteProduct( context,  "drop",  is_en, uid, id,youkuid);
		asynGetProductsInfo.execute();
	}
	
	/**
	 * 刷新产品
	 * @param context
	 * @param uid
	 * @param id
	 */
	public void AsynRefreshProduct(Context context, String uid,String id) {
		AsynRefreshProduct asynGetProductsInfo = new AsynRefreshProduct( context,  "refresh",  uid, id);
		asynGetProductsInfo.execute();
	}
	/**
	 * 获取单个品牌厂家
	 * @param context
	 * @param classid
	 * @param page
	 * @param uptime
	 */
	public void AsynGetLogoItemHasDialog(Context context, int classid,
			int page, String uptime) {
		AsynGetLogoItemInfoHasDialog asynGetProductsInfo = new AsynGetLogoItemInfoHasDialog(
				context, "info", classid, page, uptime);
		asynGetProductsInfo.execute();
	}
	/**
	 * 获取品牌厂家列表
	 * @param context
	 * @param id
	 * @param page
	 * @param uptime
	 */
 	public void AsynGetLogoHasDialog(Context context, String id, int page,
			String uptime) {
		AsynGetLogoHasDialog asynGetProductsInfo = new AsynGetLogoHasDialog(
				context, "list", id, page, uptime);
		asynGetProductsInfo.execute();
	} 
	/**
	 * 获取已经发布的产品
	 * @param context
	 * @param uid
	 * @param classid
	 * @param page
	 * @param uptime
	 * @param is_en
	 * @param is_show
	 * @param isday
	 */
 	public void AsynGetMyProducts(Activity context, String uid,
			 int page, String uptime, String is_en, String is_show) {
 		MyproductList asynGetProductsInfo = new MyproductList(
 				context, "userlist", uid,  page, uptime, is_en, is_show);
 		asynGetProductsInfo.execute();
	}
 	/**
 	 * 获取产品列表
 	 * @param context
 	 * @param classid
 	 * @param page
 	 * @param uptime
 	 */
 
	public void GetProductInfo(Context context,int classid,
			int page, String uptime) {
		AsynGetProductsInfo asynGetProductsInfo = new AsynGetProductsInfo(
				context, "prolist",  classid, page, uptime);
		asynGetProductsInfo.execute();
	}
	/**
	 * 获取今日手机招商产品列表
	 * @param context
	 * @param uid
	 * @param classid
	 * @param page
	 * @param uptime
	 * @param is_en
	 * @param is_show
	 * @param isday
	 */
	public void GetProductJinRiShouJiZhaoShangInfo(Context context, String uid, int classid,
			int page, String uptime, String is_en, String is_show,String isday) {
		AsynGetProductsInfo asynGetProductsInfo = new AsynGetProductsInfo(
				context, "daylist",  classid, page, uptime );
		asynGetProductsInfo.execute();
	}

	public void GetProductInfoById(Context context, String productId) {
		AsynGetProductsByIdInfo asynGetProductsByIdInfo = new AsynGetProductsByIdInfo(
				context, "info", productId);
		asynGetProductsByIdInfo.execute();
	}

	public void ProductsEnterpriseByIdBean(Context context, String productId) throws Exception{
		AsynGetEnterpriseProductsByIdInfo asynGetProductsByIdInfo = new AsynGetEnterpriseProductsByIdInfo(
				context, "info", productId);
		asynGetProductsByIdInfo.execute();
	}

	public void GetPubProductsLeaveWords(Context context, String id,
			String uid, String content, String nickname, String contact,
			String category) {
		AsynPubProductsLeaveWords asynGetProductsByIdInfo = new AsynPubProductsLeaveWords(
				context, "addmessage", id, uid, content, nickname, contact,
				category);
		asynGetProductsByIdInfo.execute();
	}
 
	public void GetProductsLeaveWords(Context context, String id, int page,
			String uptime) {
		AsynGetProductsLeaveWords asynGetProductsByIdInfo = new AsynGetProductsLeaveWords(
				context, "getmessage", id, page, uptime);
		asynGetProductsByIdInfo.execute();
	} 

	/**
	 * 异步读取产品信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynGetProductsInfo extends AsyncTask<Void, Void, String> {
		private int classid;
		private int page;
		private String subject;
		private Context context;
		private String uptime;

		public AsynGetProductsInfo(Context context, String subject, 
				int classid, int page, String uptime) {
			this.subject = subject;
			this.classid = classid;
			this.page = page;
			this.context = context;
			this.uptime = uptime;
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
		protected String doInBackground(Void... params) {
			String producsInfo = GetDataByPostUtil.getProductInfo(context,
					MyConstants.PRODUCT_URL, subject,  classid, page,
					uptime);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {

			List<ProductsBean> productsBeans = JsonParse
					.parseProductsJson(result);
			getProductInfo.getProductInfo(productsBeans);
			super.onPostExecute(result);
		}
	}
	/**
	 * 已经发布的产品列表
	 * @author Mzhou
	 *
	 */
 	public class MyproductList extends
			AsyncTask<Void, Void, String> {
		private int page;
		private String subject;
		private String uid;
		private Activity context;
		private String uptime;
		private String is_en;
		private String is_show;

		public MyproductList(Activity context, String subject,
				String uid,  int page, String uptime, String is_en,
				String is_show) {
			this.uid = uid;
			this.subject = subject;
			this.page = page;
			this.context = context;
			this.uptime = uptime;
			this.is_en = is_en;
			this.is_show = is_show;
		}

		@Override
		protected void onPreExecute() {
//			startProgressDialog(context,
//					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String producsInfo = GetDataByPostUtil.getMyProductInfo(context,
					MyConstants.PRODUCT_URL, subject,page, uid,is_en,  
					uptime,  is_show);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
//			stopProgressDialog();
			List<ProductsBean> productsBeans = JsonParse
					.parseProductsJson(result);
			getProductInfo.getProductInfo(productsBeans);

			super.onPostExecute(result);
		}
	} 

	/**
	 * logo列表
	 * 
	 * @author user
	 * 
	 */
	public class AsynGetLogoItemInfoHasDialog extends
			AsyncTask<Void, Void, String> {
		private int classid;
		private int page;
		private String subject;
		private Context context;
		private String uptime;

		public AsynGetLogoItemInfoHasDialog(Context context, String subject,
				int classid, int page, String uptime) {
			this.subject = subject;
			this.classid = classid;
			this.page = page;
			this.context = context;
			this.uptime = uptime;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String producsInfo = GetDataByPostUtil.getLogoItemInfo(context,
					MyConstants.BRAND_URL, subject, classid, page, uptime);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			stopProgressDialog();
			List<ProductsBean> productsBeans = JsonParse
					.parseProductsJson(result);
			getProductInfo.getProductInfo(productsBeans);

			super.onPostExecute(result);
		}
	}

	/**
	 * 获取logo数据
	 * 
	 * @author user
	 * 
	 */
	public class AsynGetLogoHasDialog extends AsyncTask<Void, Void, String> {
		private int page;
		private String subject;
		private String id;
		private Context context;
		private String uptime;

		public AsynGetLogoHasDialog(Context context, String subject, String id,
				int page, String uptime) {
			this.id = id;
			this.subject = subject;
			this.page = page;
			this.context = context;
			this.uptime = uptime;
		}

		@Override
		protected void onPreExecute() {
			  startProgressDialog(context,
		  context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			 stopProgressDialog();
		}

		@Override
		protected String doInBackground(Void... params) {
			String producsInfo = GetDataByPostUtil.getLogoInfo(context,
					MyConstants.BRAND_URL, subject, id, page, uptime);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
		  stopProgressDialog();
			List<ProductsBean> productsBeans = JsonParse
					.parseProductsJson(result);
			getProductInfo.getProductInfo(productsBeans);

			super.onPostExecute(result);
		}
	}

	public class AsynGetProductsByIdInfo extends AsyncTask<Void, Void, String> {
		private String subject;
		private String productId;
		private Context context;

		public AsynGetProductsByIdInfo(Context context, String subject,
				String productId) {
			this.subject = subject;
			this.productId = productId;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {

			String producsInfo = GetDataByPostUtil.getProductInfoById(context,
					MyConstants.PRODUCT_URL, subject, productId);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			// *************************
			// *************************
			// *************************
			// *************************
			// *************************
			ProductsByIdBean productsBeans = JsonParse
					.parseProductsByIdJson(result);
			getProductInfoById.getProductInfoById(productsBeans);
			stopProgressDialog();

			super.onPostExecute(result);
		}
	}

	public class AsynGetEnterpriseProductsByIdInfo extends
			AsyncTask<Void, Void, String> {
		private String subject;
		private String productId;
		private Context context;

		public AsynGetEnterpriseProductsByIdInfo(Context context,
				String subject, String productId) {
			this.subject = subject;
			this.productId = productId;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {

			String producsInfo = GetDataByPostUtil.getProductInfoById(context,
					MyConstants.PRODUCT_URL, subject, productId);
			// Log.i("print", "doInBackground---->"+producsInfo);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			com.mzhou.merchant.model.ProductsEnterpriseByIdBean productsBeans;
			try {
				productsBeans = JsonParse
						.parseEnterpriseProductsByIdJson(result);
				getEnterpriseProductInfoById.getProductInfoById(productsBeans);
			} catch (Exception e) {
				getEnterpriseProductInfoById.getProductInfoById(null);
				e.printStackTrace();
			}
			// Log.i("print", "productsBeans---->"+productsBeans.toString());
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	public class AsynPubProductsLeaveWords extends
			AsyncTask<Void, Void, String> {
		private String subject;
		private String id;
		private String uid;
		private String content;
		private String nickname;
		private String contact;
		private String category;
		private Context context;

		public AsynPubProductsLeaveWords(Context context, String subject,
				String id, String uid, String content, String nickname,
				String contact, String category) {
			this.subject = subject;
			this.id = id;
			this.uid = uid;
			this.content = content;
			this.nickname = nickname;
			this.contact = contact;
			this.category = category;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(
					context,
					context.getResources().getString(
							R.string.publishing_leavewords));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String producsInfo = GetDataByPostUtil.PubProductLeaveMsgInfo(
					context, MyConstants.PRODUCT_URL, subject, uid, id,
					content, nickname, contact, category);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			BackBean backBean = JsonParse.parsePubAttactJson(result);
			backInfo.getBackAttactInfo(backBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	public class AsynGetProductsLeaveWords extends
			AsyncTask<Void, Void, String> {
		private String subject;
		private String id;
		private int page;
		private Context context;
		private String uptime;

		public AsynGetProductsLeaveWords(Context context, String subject,
				String id, int page, String uptime) {
			this.subject = subject;
			this.id = id;
			this.page = page;
			this.context = context;
			this.uptime = uptime;
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
		protected String doInBackground(Void... params) {
			String producsInfo = GetDataByPostUtil
					.GetProductLeaveMsgInfo(context, MyConstants.PRODUCT_URL,
							subject, page, id, uptime);
			return producsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			List<ProductCommentBean> productCommentBean = JsonParse
					.parseCommentProductBean(result);
			getCommentInfo.getCommentInfo(productCommentBean);
			super.onPostExecute(result);
		}
	}

/**
	 * 获取公司简介数据
	 * 
	 * @author user
	 * 
	 */
	public class AsynGetCompany extends AsyncTask<Void, Void, String> {
		private String subject;
		private Context context;
		private String name;

		public AsynGetCompany(Context context, String subject, String name) {
			this.subject = subject;
			this.name = name;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String jsonString = GetDataByPostUtil.GetAboutCompanyInfo(context,
					MyConstants.BRAND_URL, subject, name);
			if (!jsonString.equals("false")) {
				return jsonString;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			AboutCompany aboutCompany = JsonParse.parseAboutCompanyJson(result);
			getCompanyInfo.getCompanyInfo(aboutCompany);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}
	public class AsynDeleteProduct extends AsyncTask<Void, Void, String> {
		private String subject;
		private Context context;
		private String is_en;
		private String uid;
		private String id;
		private String youkuid;
		
		public AsynDeleteProduct(Context context, String subject, String is_en,String uid,String id,String youkuid) {
			this.subject = subject;
			this.is_en = is_en;
			this.context = context;
			this.uid = uid;
			this.id = id;
			this.youkuid = youkuid;
		}
		
		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}
		
		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			String jsonString = GetDataByPostUtil.DeleteProductInfo(
					getBaseContext(), MyConstants.PRODUCT_URL,
					is_en, subject, uid, id,youkuid);
				return jsonString;
		}
		
		@Override
		protected void onPostExecute(String result) {
			PublishProductBean databack = JsonParse
					.parseProductBackFormServer(result);
			deleteProduct.deletProDuct(databack);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}
	public class AsynRefreshProduct extends AsyncTask<Void, Void, String> {
		private String subject;
		private Context context;
		private String uid;
		private String id;
		public AsynRefreshProduct(Context context, String subject, String uid,String id) {
			this.subject = subject;
			this.context = context;
			this.uid = uid;
			this.id = id;
		}
		
		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.loading));
			super.onPreExecute();
		}
		
		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			String jsonString = GetDataByPostUtil.FreshProductInfo(
					getBaseContext(), MyConstants.PRODUCT_URL,
					 subject, uid, id);
			return jsonString;
		}
		
		@Override
		protected void onPostExecute(String result) {
			PublishProductBean databack = JsonParse
					.parseProductBackFormServer(result);
			deleteProduct.deletProDuct(databack);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	private IgetProductInfo getProductInfo;
	private IgetProductInfoById getProductInfoById;
	private IgetEnterpriseProductInfoById getEnterpriseProductInfoById;
	private IGetCompanyInfo getCompanyInfo;
	private IGetCommentInfo getCommentInfo;
	private IBackInfo backInfo;
	private IDeleteProductInfo deleteProduct;
	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getProductInfoIml(IgetProductInfo igetProductInfo) {
		getProductInfo = igetProductInfo;
	}

	public void getProductInfoByIdIml(IgetProductInfoById igetProductInfoById) {
		getProductInfoById = igetProductInfoById;
	}

	public void getEnterpriseProductInfoByIdIml(
			IgetEnterpriseProductInfoById igetProductInfoById) {
		getEnterpriseProductInfoById = igetProductInfoById;
	}

	public void getCompanyInfoIml(IGetCompanyInfo iGetCompanyInfo) {
		getCompanyInfo = iGetCompanyInfo;
	}

	public void getBackInfoIml(IBackInfo info) {
		backInfo = info;
	}

	public void getCommentInfoIml(IGetCommentInfo info) {
		getCommentInfo = info;
	}
	public void deleteProduct(IDeleteProductInfo delet){
		deleteProduct = delet;
	}

	// ********************************************
	public void startProgressDialog(Context context, String msg) {

		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
			progressDialog.setMessage(msg);
		}
		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
