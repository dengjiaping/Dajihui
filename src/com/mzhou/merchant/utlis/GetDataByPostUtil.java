package com.mzhou.merchant.utlis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.mzhou.merchant.model.ActivityBean;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class GetDataByPostUtil extends Activity {
	/**
	 * 通过post 与网络连接 得到返回状态码 200 成功连接，否则为连接失败 通过响应类别实体得到返回数据
	 * 
	 * @param httppost
	 * @return 得到网络数据为字符串，默认为空
	 */
	public static String queryStringForPost(HttpPost httppost, Context context) {
		String result = null;
		try {
			HttpResponse response = new DefaultHttpClient().execute(httppost);
//			Log.i("print", response.getStatusLine().getStatusCode() + "");
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 登录
	 * 
	 * @param url
	 *            登录地址
	 * @param subject
	 *            登录参数
	 * @param un
	 *            用户名
	 * @param pw
	 *            密码
	 * @return json数据
	 */
/*	public static String login(Context context, String url,
			final String subject, final String un, final String pw) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("data[un]", URLEncoder.encode(
					un, "utf-8")));
			params1.add(new BasicNameValuePair("data[pw]", pw));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "login--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}*/
	 

 

	/**
	 * 用户注册
	 * 
	 * @param url
	 *            注册地址
	 * @param subject
	 *            参数
	 * @param un
	 *            用户名
	 * @param pw
	 *            用户密码
	 * @return 返回用户注册Json数据
	 */
	public static String check(Context context, String url,
			final String openid ) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", "checkbinder"));
			params1.add(new BasicNameValuePair("data[openid]", openid));
//			  Log.i("print", "check -->"+params1.toString());
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
//			  Log.i("print", "check--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String binder(Context context, String url,
			final String openid,String username,String passwd ) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", "binderuser"));
			params1.add(new BasicNameValuePair("data[openid]", openid));
			params1.add(new BasicNameValuePair("data[un]", username));
			params1.add(new BasicNameValuePair("data[pw]", passwd));
//			 Log.i("print", " --binder-- -->"+params1.toString());
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
//			  Log.i("print", " --binder-- -->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String register(Context context, String url,
			final String subject, final String un, final String pw,
			final String nickname, final String category, String pw_pro,
			String pw_pro_an) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("data[question]", pw_pro));
			params1.add(new BasicNameValuePair("data[answer]", pw_pro_an));
			params1.add(new BasicNameValuePair("data[category]", URLEncoder
					.encode(category, "utf-8")));
			params1.add(new BasicNameValuePair("data[nickname]", URLEncoder
					.encode(nickname, "utf-8")));
			params1.add(new BasicNameValuePair("data[un]", URLEncoder.encode(
					un, "utf-8")));
			params1.add(new BasicNameValuePair("data[pw]", pw));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "register--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 找回问题
	 * 
	 * @param context
	 * @param url
	 * @param un
	 * @return
	 */
	public static String getQuestion(Context context, String url,
			final String un) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", "find"));
			params1.add(new BasicNameValuePair("data[un]", URLEncoder.encode(
					un, "utf-8")));
			// Log.i("print", "getQuestion-params1->"+params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "getQuestion-result->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 验证问题答案
	 * 
	 * @param context
	 * @param url
	 * @param un
	 * @param answer
	 * @param uid
	 * @return
	 */
	public static String verify(Context context, String url, String un,
			String answer, String uid) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", "valid"));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("data[question]", URLEncoder
					.encode(un, "utf-8")));
			params1.add(new BasicNameValuePair("data[answer]", URLEncoder
					.encode(answer, "utf-8")));
			// Log.i("print", "verify-params1->"+params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "verify-result->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 重置密码
	 * 
	 * @param context
	 * @param url
	 * @param pw
	 * @param oldpw
	 * @param uid
	 * @return
	 */
	public static String resetPw(Context context, String url, String pw,
			String oldpw, String uid) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", "repwd"));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("data[pw]", URLEncoder.encode(
					pw, "utf-8")));
			params1.add(new BasicNameValuePair("data[oldpw]", URLEncoder
					.encode(oldpw, "utf-8")));
			// Log.i("print", "resetPw-params1->"+params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "resetPw--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 意见反馈
	 * 
	 * @param context
	 * @param url
	 * @param content
	 * @param contact
	 * @return
	 */
	public static String feedback(Context context, String url, String content,
			String contact) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("subject", "add"));
			params1.add(new BasicNameValuePair("data[content]", URLEncoder
					.encode(content, "utf-8")));
			params1.add(new BasicNameValuePair("data[contact]", URLEncoder
					.encode(contact, "utf-8")));
			// Log.i("print", "feedback--params1--params1-->"+params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "feedback--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 发布求购信息
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param page
	 * @return
	 */
	public static String PubAttactInfo(Context context, String is_en,
			String subject, String uid, String content, String contact,
			String email, String url) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("data[contact]", URLEncoder
					.encode(contact, "utf-8")));
			params1.add(new BasicNameValuePair("data[content]", URLEncoder
					.encode(content, "utf-8")));
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("data[email]", email));
			params1.add(new BasicNameValuePair("data[is_en]", is_en));
			params1.add(new BasicNameValuePair("uid", uid));
			// Log.i("print", "params--->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取求购信息
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param page
	 * @return
	 */
	public static String GetAttactInfo(Context context, String url,
			String subject, String uid, int page, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("time", uptime));
			// Log.i("print", "GetAttactInfo--params1--params1-->"+params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取新闻信息
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param page
	 * @return
	 */
	public static String GetNewsInfo(Context context, String url,
			String subject, int page, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("time", uptime));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取单条新闻信息
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param page
	 * @return
	 */
	public static String GetNewsInfoById(Context context, String url,
			String subject, String id) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetNewsInfoById--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取单条新闻的评论
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param page
	 * @return
	 */
	public static String GetNewsCommentById(Context context, String url,
			String subject, int page, String id, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
//			Log.i("print", "GetNewsCommentById--params1--params1-->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
//			Log.i("print", "GetNewsCommentById--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 发布新闻的评论
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param page
	 * @return
	 */
	public static String PubNewsCommentById(Context context, String is_en,
			String url, String subject, String content, String uid, String id,
			String commenter) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("data[content]", URLEncoder
					.encode(content, "utf-8")));
			params1.add(new BasicNameValuePair("data[commenter]", URLEncoder
					.encode(commenter, "utf-8")));
			params1.add(new BasicNameValuePair("data[is_en]", is_en));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	public static String getProductCategoryInfo(Context context, String url,
			String subject) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 根据类别id来获取产品信息
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param classid
	 * @param page
	 * @return
	 */
	public static String getProductInfo(Context context, String url,
			String subject,   int classid, int page, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("classid", classid + ""));
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
 			  Log.i("print", "getProductInfo--params1---->" + params1);
			String data = queryStringForPost(httppost, context);
 			  Log.i("print", "getProductInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	/**
	 * 获取已经发布的产品
	 * @param context
	 * @param url
	 * @param subject
	 * @param page
	 * @param uid
	 * @param is_en
	 * @param uptime
	 * @param is_show
	 * @return
	 */
	public static String getMyProductInfo(Context context, String url,
			String subject,    int page, String uid,String is_en,String uptime,String is_show) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("uid", uid ));
			params1.add(new BasicNameValuePair("is_en", is_en ));
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("is_show", is_show));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			Log.i("print", "getMyProductInfo--params1---->" + params1);
			String data = queryStringForPost(httppost, context);
			Log.i("print", "getMyProductInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String getPhoneNumberInfo(Context context, String url,
			String subject, String uid,String is_en,String phone) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("data[is_en]", is_en));
			params1.add(new BasicNameValuePair("data[phone]", phone));
			params1.add(new BasicNameValuePair("is_en", is_en));
			params1.add(new BasicNameValuePair("phone", phone));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			Log.i("print", "getPhoneNumberInfo--params1---->" + params1);
			String data = queryStringForPost(httppost, context);
 			Log.i("print", "getPhoneNumberInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * logo列表
	 * 
	 * @param context
	 * @param url
	 * @param subject
	 * @param uid
	 * @param classid
	 * @param page
	 * @param uptime
	 * @param is_en
	 * @param is_show
	 * @return
	 */
	public static String getLogoItemInfo(Context context, String url,
			String subject, int classid, int page, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("classid", classid + ""));
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			// Log.i("print", "getProductInfo--params1---->" + params1);
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "getProductInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 品牌列表信息
	 * 
	 * @param context
	 * @param url
	 * @param subject
	 * @param uid
	 * @param classid
	 * @param page
	 * @param uptime
	 * @param is_en
	 * @param is_show
	 * @return
	 */
	public static String getLogoInfo(Context context, String url,
			String subject, String id, int page, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
//			  Log.i("print", "getLogoInfo--params1---->" + params1);
			String data = queryStringForPost(httppost, context);
//			 Log.i("print", "getLogoInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 根据产品id来获取单条产品信息
	 * 
	 * @param url
	 * @param subject
	 * @param id
	 * @return
	 */
	public static String getProductInfoById(Context context, String url,
			String subject, String id) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	public static String getProductMsgInfo(Context context, String url,
			String subject, int page, int id) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("id", id + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 刷新产品信息
	 * 
	 * @param context
	 * @param url
	 * @param uptime
	 * @param subject
	 * @param uid
	 * @param id
	 * @return
	 */
 	public static String FreshProductInfo(Context context, String url,
		 String subject, String uid, String id) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("data[id]", id));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("subject", subject));
			 Log.i("print", "FreshProductInfo--params1---->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
 			  Log.i("print", "FreshProductInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	} 

	/**
	 * 删除单跳产品信息
	 * 
	 * @param context
	 * @param url
	 * @param is_en
	 * @param subject
	 * @param uid
	 * @param id
	 * @return
	 */
	public static String DeleteProductInfo(Context context, String url,
			String is_en, String subject, String uid, String id) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("is_en", is_en));
			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("subject", subject));
			Log.i("print", "params1--return--result-->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			  Log.i("print", "PubProductInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 发布留言信息
	 * 
	 * @param url
	 * @param subject
	 * @param uid
	 * @param id
	 * @param content
	 * @return
	 */

	public static String PubProductLeaveMsgInfo(Context context, String url,
			String subject, String uid, String id, String content,
			String nickname, String contact, String category) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("data[content]", URLEncoder
					.encode(content, "utf-8")));
			params1.add(new BasicNameValuePair("data[nickname]", URLEncoder
					.encode(nickname, "utf-8")));
			params1.add(new BasicNameValuePair("data[contact]", URLEncoder
					.encode(contact, "utf-8")));
			params1.add(new BasicNameValuePair("data[category]", URLEncoder
					.encode(category, "utf-8")));
			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	public static String GetProductLeaveMsgInfo(Context context, String url,
			String subject, int page, String id, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取招聘信息
	 * 
	 * @param url
	 * @param subject
	 * @param page
	 * @param uid
	 * @return
	 */
	public static String GetJobInfo(Context context, String url,
			String subject, int page, String uid, String uptime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("time", uptime));
			params1.add(new BasicNameValuePair("subject", subject));
//			 Log.e("print", "GetJobInfo--return--result-->"+params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
//			 Log.e("print", "GetJobInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}


	public static String GetJobByIdInfo(Context context, String url,
			String subject, String id) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "[]";
		}
	}
	
	public static String PubJobInfo(Context context, String url,
			String subject, String uid, String position, String content,
			String contact, String phone, String email, String company,
			String address, String lasttime) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("data[position]", URLEncoder
					.encode(position, "utf-8")));
			params1.add(new BasicNameValuePair("data[content]", URLEncoder
					.encode(content, "utf-8")));
			params1.add(new BasicNameValuePair("data[contact]", URLEncoder
					.encode(contact, "utf-8")));
			params1.add(new BasicNameValuePair("data[phone]", phone));
			params1.add(new BasicNameValuePair("data[email]", email));
			params1.add(new BasicNameValuePair("data[company]", URLEncoder
					.encode(company, "utf-8")));
			params1.add(new BasicNameValuePair("data[address]", URLEncoder
					.encode(address, "utf-8")));
			params1.add(new BasicNameValuePair("data[lasttime]", lasttime));
			// Log.i("print", "params1-- ->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "PubJobInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	public static String GetSearchInfo(Context context, String url,
			String subject, String keyword, int page) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("keyword", URLEncoder.encode(
					keyword, "utf-8")));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
 			  Log.i("print", "params1--return--result-->" + params1);
			String data = queryStringForPost(httppost, context);
 			  Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	/**
	 * 获取广告
	 */
	public static String GetAdInfo(Context context, String url, String subject,
			String position, int channel) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("position", position));
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("channel", channel + ""));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			// Log.i("print", "GetAdInfo--return--params1-->" + params1);
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAdInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}

	public static String GetAboutCompanyInfo(Context context, String url,
			String subject, String name) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("name", URLEncoder.encode(name,
					"utf-8")));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			// Log.i("print", "GetAboutCompanyInfo--return--params1-->" +
			// params1);
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAboutCompanyInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String GetActivityInfo(Context context, String url,
			String subject, int page, String time) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("page", page + ""));
			params1.add(new BasicNameValuePair("time", time));
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String GetActivityDetailInfo(Context context, String url,
			String subject,  String  id ) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
		 
			params1.add(new BasicNameValuePair(" id",  id));
			 
			params1.add(new BasicNameValuePair("subject", subject));
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			// Log.i("print", "GetAttactInfo--return--result-->"+data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String PubActivityInfo(Context context, String url,
			String subject, String uid,String is_en,ActivityBean activityBean) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("uid", uid));
			params1.add(new BasicNameValuePair("data[is_en]", is_en));
			params1.add(new BasicNameValuePair("data[title]", URLEncoder
					.encode(activityBean.getTitle(), "utf-8")));
			params1.add(new BasicNameValuePair("data[content]", URLEncoder
					.encode(activityBean.getContent(), "utf-8")));
			params1.add(new BasicNameValuePair("data[address]", URLEncoder
					.encode(activityBean.getAddress(), "utf-8")));
			params1.add(new BasicNameValuePair("data[contact]", URLEncoder
					.encode(activityBean.getContact(), "utf-8")));
			params1.add(new BasicNameValuePair("data[phone]", activityBean.getPhone()+""));
			params1.add(new BasicNameValuePair("data[applytime]", URLEncoder
					.encode(activityBean.getApplytime(), "utf-8")));
			
			params1.add(new BasicNameValuePair("data[lasttime]", URLEncoder
					.encode(activityBean.getLasttime(), "utf-8")));
			
			  Log.i("print", "params1-- ->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			 Log.i("print", "PubJobInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	public static String JoinActivityInfo(Context context, String url,
			String subject, String uid,String name,String number) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("id", uid));
			params1.add(new BasicNameValuePair("uid", "0"));
			params1.add(new BasicNameValuePair("data[phone]", number));
			params1.add(new BasicNameValuePair("data[contact]", URLEncoder
					.encode(name, "utf-8")));
			
//			  Log.e("print", "params1-- ->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
//			  Log.e("print", "PubJobInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	}
 	public static String ActivityMemberInfo(Context context, String url,
			String subject, String id,int page) {
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("subject", subject));
			params1.add(new BasicNameValuePair("id", id));
			params1.add(new BasicNameValuePair("page", String.valueOf(page)));
			
			  Log.e("print", "params1-- ->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			  Log.e("print", "PubJobInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "[]";
	} 
	/**
	 * check version in the index activity
	 * @param context
	 * @param url
	 * @param subject
	 * @return
	 */
	public static String CheckVersion(Context context, String url,
			String subject ) { 
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("subject", subject));
			
			Log.e("print", "params1-- ->" + params1);
			httppost.setEntity(new UrlEncodedFormEntity(params1));
			String data = queryStringForPost(httppost, context);
			Log.e("print", "PubJobInfo--return--result-->" + data);
			return data;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "[]";
		} 
	}
}
