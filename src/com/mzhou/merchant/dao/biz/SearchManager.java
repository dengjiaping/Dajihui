package com.mzhou.merchant.dao.biz;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.INews.IgetNewsInfo;
import com.mzhou.merchant.dao.IProduct.IgetProductInfo;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;

public class SearchManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	public SearchManager() {
	}

	public void GetAsynSearchInfo(Context context, String subject, int page,
			String keyword) {
		AsynSearchInfo login = new AsynSearchInfo(context, subject, page,
				keyword);
		login.execute();
	}

	/**
	 * 获取招商所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynSearchInfo extends AsyncTask<Void, Void, String> {
		private int page;
		private String keyword;
		private String subject;
		private Context context;

		public AsynSearchInfo(Context context, String subject, int page,
				String keyword) {
			this.page = page;
			this.subject = subject;
			this.keyword = keyword;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(context,
					context.getResources().getString(R.string.searching));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String userinfo = GetDataByPostUtil.GetSearchInfo(context,
					MyConstants.SEARCH_URL, subject, keyword, page);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {

//			  Log.i("print", "搜索json数组----》"+result);
			if (subject.equals("news")) {
				List<NewsBean> newsBean = JsonParse.parseNewsJson(result);
//				  Log.i("print", "List<NewsBean> newsBean----》"+newsBean);
				searchNewsInfo.getNewsInfo(newsBean);

			} else if (subject.equals("product")) {
				List<ProductsBean> productsBeans = JsonParse
						.parseProductsJson(result);
//				 Log.i("print", "List<ProductsBean> newsBean----》"+productsBeans);
				searchProductInfo.getProductInfo(productsBeans);
			}
			stopProgressDialog();

			super.onPostExecute(result);
		}
	}

	// ********************************************
	private IgetProductInfo searchProductInfo;
	private IgetNewsInfo searchNewsInfo;

	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getSearchNewsInfoIml(IgetNewsInfo igetNewsInfo) {
		searchNewsInfo = igetNewsInfo;
	}

	public void getSearchProductInfoIml(IgetProductInfo igetProductInfo) {
		searchProductInfo = igetProductInfo;
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
