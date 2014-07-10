package com.mzhou.merchant.dao.biz;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.INews.IgetNewsByIdInfo;
import com.mzhou.merchant.dao.INews.IgetNewsCommentInfo;
import com.mzhou.merchant.dao.INews.IgetNewsInfo;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.NewsByIdBean;
import com.mzhou.merchant.model.NewsCommentBean;
import com.mzhou.merchant.utlis.CustomProgressDialog;
import com.mzhou.merchant.utlis.GetDataByPostUtil;
import com.mzhou.merchant.utlis.JsonParse;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;

public class NewsManager extends Activity {
	private CustomProgressDialog progressDialog = null;

	public NewsManager() {
	}

	public void GetNewsInfo(Context context, int page, String uptime) {
		AsynNews login = new AsynNews(context, "list", page, uptime);
		login.execute();
	}

	/**
	 * 根据id来获取新闻信息
	 * 
	 * @param page
	 * @param id
	 */
	public void GetNewsInfoById(Context context, String id) {
		AsynNewsById newsInfoById = new AsynNewsById(context, "info", id);
		newsInfoById.execute();
	}

	/**
	 * 根据id来获取新闻评论信息
	 * 
	 * @param page
	 * @param id
	 */
	public void GetNewsCommentInfoById(Context context, int page, String id,
			String uptime) {
		AsynNewsCommentById newsInfoById = new AsynNewsCommentById(context,
				"getcomment", page, id, uptime);
		newsInfoById.execute();
	}

	/**
	 * 发布新闻信息
	 * 
	 * @param content
	 * @param contact
	 * @param uid
	 * @param id
	 */
	public void PublishNewsComment(Context context, String is_en,
			String content, String uid, String id, String commenter) {
		AsynNewsCommentPublish newsInfoById = new AsynNewsCommentPublish(
				context, is_en, "addcomment", content, uid, commenter, id);
		newsInfoById.execute();
	}

	/**
	 * 获取新闻所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynNews extends AsyncTask<Void, Void, String> {
		private int page;
		private String subject;
		private Context context;
		private String uptime;

		public AsynNews(Context context, String subject, int page, String uptime) {
			this.page = page;
			this.subject = subject;
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
			String newsInfo = GetDataByPostUtil.GetNewsInfo(context,
					MyConstants.NEWS_URL, subject, page, uptime);
			if (!MyUtlis.isEmpity(newsInfo)) {
				return newsInfo;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// Log.i("print", "newsmanager--" + result);

			List<NewsBean> newsBean = JsonParse.parseNewsJson(result);
			getNewsInfo.getNewsInfo(newsBean);

			super.onPostExecute(result);
		}
	}

	/**
	 * 获取单条新闻信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynNewsById extends AsyncTask<Void, Void, String> {
		private String id;
		private String subject;
		private Context context;

		public AsynNewsById(Context context, String subject, String id) {
			this.id = id;
			this.subject = subject;
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
			String newsInfo = GetDataByPostUtil.GetNewsInfoById(context,
					MyConstants.NEWS_URL, subject, id);
			return newsInfo;
		}

		@Override
		protected void onPostExecute(String result) {

			// Log.i("print", "result--->" + result);
			NewsByIdBean newsBean = JsonParse.parseNewsByIdJson(result);
			// Log.i("print", "attactBeans--->" + result);
			getNewsByIdInfo.getNewsByIdInfo(newsBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	/**
	 * 获取新闻评论所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynNewsCommentById extends AsyncTask<Void, Void, String> {
		private String id;
		private int page;
		private String subject;
		private Context context;
		private String uptime;

		public AsynNewsCommentById(Context context, String subject, int page,
				String id, String uptime) {
			this.id = id;
			this.page = page;
			this.subject = subject;
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
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String newsInfo = GetDataByPostUtil.GetNewsCommentById(context,
					MyConstants.NEWS_URL, subject, page, id, uptime);
			return newsInfo;
		}

		@Override
		protected void onPostExecute(String result) {
			// Log.i("print", "result--->" + result);
			List<NewsCommentBean> newsBean = JsonParse
					.parseNewsCommentBeanJson(result);
			// Log.i("print", "attactBeans--->" + result);
			getNewsCommentInfo.getNewsCommentInfo(newsBean);
			stopProgressDialog();
			super.onPostExecute(result);
		}
	}

	/**
	 * 发布招商所有的信息
	 * 
	 * @author user
	 * 
	 */
	public class AsynNewsCommentPublish extends AsyncTask<Void, Void, String> {
		private String content;
		private String uid;
		private String id;
		private String subject;
		private String commenter;
		private Context context;
		private String is_en;

		public AsynNewsCommentPublish(Context context, String is_en,
				String subject, String content, String uid, String commenter,
				String id) {
			this.content = content;
			this.subject = subject;
			this.uid = uid;
			this.id = id;
			this.commenter = commenter;
			this.context = context;
			this.is_en = is_en;
		}

		@Override
		protected void onPreExecute() {
			startProgressDialog(
					context,
					context.getResources().getString(
							R.string.publishing_comment));
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected String doInBackground(Void... params) {
			String userinfo = GetDataByPostUtil.PubNewsCommentById(context,
					is_en, MyConstants.NEWS_URL, subject, content, uid, id,
					commenter);
			return userinfo;
		}

		@Override
		protected void onPostExecute(String result) {

			// Log.i("print", "result--->" + result);
			BackBean backBean = JsonParse.parsePubAttactJson(result);
			stopProgressDialog();
			backInfo.getBackAttactInfo(backBean);

			super.onPostExecute(result);
		}
	}

	// ********************************************

	private IgetNewsInfo getNewsInfo;
	private IgetNewsByIdInfo getNewsByIdInfo;
	private IgetNewsCommentInfo getNewsCommentInfo;
	private IBackInfo backInfo;

	/**
	 * 接口的实现方法，将接口传递出来，然后在需要实现的地方去实现
	 * 
	 * @param igetUserInfo
	 *            需要实现的接口 获取用户信息的回调方法
	 * 
	 */
	public void getNewsInfoIml(IgetNewsInfo igetNewsInfo) {
		getNewsInfo = igetNewsInfo;
	}

	public void getNewsByIdInfoIml(IgetNewsByIdInfo idInfo) {
		getNewsByIdInfo = idInfo;
	}

	public void getNewsCommentInfo(IgetNewsCommentInfo igetNewsCommentInfo) {
		getNewsCommentInfo = igetNewsCommentInfo;
	}

	public void getBackInfo(IBackInfo info) {
		backInfo = info;
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
