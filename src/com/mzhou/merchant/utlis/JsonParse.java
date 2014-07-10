package com.mzhou.merchant.utlis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.model.AboutCompany;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.ActivityMemberBean;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.AllBean;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.model.CategoryBean;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.model.JobItemBean;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.NewsByIdBean;
import com.mzhou.merchant.model.NewsCommentBean;
import com.mzhou.merchant.model.ProductCommentBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.model.ProductsEnterpriseByIdBean;
import com.mzhou.merchant.model.PublishProductBean;

public class JsonParse { 

	 /**
	 * 解析用户数据得到所有的用户信息
	 * 
	 * @param result
	 * @return user对象
	 */
	public static AllBean parseUserJson(String result) {
		// Log.i("print", " result  result---->"+result);
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			AllBean json = com.alibaba.fastjson.JSON.parseObject(result,
					AllBean.class);
			// Log.i("print", " AllBean  Json---->"+json.toString());
			return json;
		}
		return null;
	}

	public static AboutCompany parseAboutCompanyJson(String result) {
		// Log.i("print", " result  result---->"+result);
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			AboutCompany json = com.alibaba.fastjson.JSON.parseObject(result,
					AboutCompany.class);
			// Log.i("print", " AllBean  Json---->"+json.toString());
			return json;
		}
		return null;
	}

	/**
	 * 解析广告数据
	 * 
	 * @param result
	 * @return
	 */
	public static LinkedList<AdBean> parseAdJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			LinkedList<AdBean> attactBeans = new LinkedList<AdBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				AdBean attactBean = com.alibaba.fastjson.JSON.parseObject(
						object.get(string).toString(), AdBean.class);
				attactBeans.add(attactBean);
			}

			return attactBeans;
		}
		return null;
	}

	/**
	 * 解析求购数据
	 * 
	 * @param result
	 * @return
	 */
	public static LinkedList<AttactBean> parseAttactJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			LinkedList<AttactBean> attactBeans = new LinkedList<AttactBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				AttactBean attactBean = com.alibaba.fastjson.JSON.parseObject(
						object.get(string).toString(), AttactBean.class);
				attactBeans.add(attactBean);
			}

			return attactBeans;
		}
		return null;
	}
	public static List<ActivityBean> parseActivityJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<ActivityBean> attactBeans = new ArrayList<ActivityBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				ActivityBean attactBean = com.alibaba.fastjson.JSON.parseObject(
						object.get(string).toString(), ActivityBean.class);
				attactBeans.add(attactBean);
			}
			
			return attactBeans;
		}
		return null;
	}
	public static ActivityBean  parseActivityDetailJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			ActivityBean attactBean = com.alibaba.fastjson.JSON.parseObject(
					result, ActivityBean.class);
			return attactBean;
		}
		return null;
	}

	/**
	 * 解析返回数据数据
	 * 
	 * @param result
	 * @return
	 */
	public static BackBean parsePubAttactJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			BackBean json = com.alibaba.fastjson.JSON.parseObject(result,
					BackBean.class);
			return json;
		}
		return null;
	}

	/**
	 * 解析新闻数据
	 * 
	 * @param result
	 * @return
	 */
	public static List<NewsBean> parseNewsJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<NewsBean> attactBeans = new ArrayList<NewsBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				NewsBean attactBean = com.alibaba.fastjson.JSON.parseObject(
						object.get(string).toString(), NewsBean.class);
				attactBeans.add(attactBean);
			}

			return attactBeans;
		}
		return null;
	}
	public static List<ActivityMemberBean> parseActivityMemberJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<ActivityMemberBean> attactBeans = new ArrayList<ActivityMemberBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				ActivityMemberBean attactBean = com.alibaba.fastjson.JSON.parseObject(
						object.get(string).toString(), ActivityMemberBean.class);
				attactBeans.add(attactBean);
			}
			
			return attactBeans;
		}
		return null;
	}

	/**
	 * 解析评论数据
	 * 
	 * @param result
	 * @return
	 */
	public static List<NewsCommentBean> parseNewsCommentBeanJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<NewsCommentBean> attactBeans = new ArrayList<NewsCommentBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				NewsCommentBean attactBean = com.alibaba.fastjson.JSON
						.parseObject(object.get(string).toString(),
								NewsCommentBean.class);
				attactBeans.add(attactBean);
			}

			return attactBeans;
		}
		return null;
	}

	/**
	 * 根据每条id来解析新闻数据
	 * 
	 * @param result
	 * @return
	 */
	public static NewsByIdBean parseNewsByIdJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			NewsByIdBean attactBean = com.alibaba.fastjson.JSON.parseObject(
					result, NewsByIdBean.class);

			return attactBean;
		}
		return null;
	}

	/**
	 * product category
	 * 
	 * @param result
	 * @return
	 */
	public static List<CategoryBean> parseProductsCategoryJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<CategoryBean> productsBeans = new ArrayList<CategoryBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				CategoryBean productsBean = com.alibaba.fastjson.JSON
						.parseObject(object.get(string).toString(),
								CategoryBean.class);
				productsBeans.add(productsBean);
			}
			return productsBeans;
		}
		return null;
	}

	public static List<ProductsBean> parseProductsJson(String result) {
		// Log.i("print", "parseProductsJson---> " + result) ;
		if ((result != null) && (!result.trim().toString().equals("[]"))
				&& (!result.trim().toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<ProductsBean> productsBeans = new ArrayList<ProductsBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				ProductsBean productsBean = com.alibaba.fastjson.JSON
						.parseObject(object.get(string).toString(),
								ProductsBean.class);
				productsBeans.add(productsBean);
			}
			return productsBeans;
		}
		return null;
	}

	/**
	 * 根据id来解析产品信息
	 * 
	 * @param result
	 * @return
	 */
	public static ProductsByIdBean parseProductsByIdJson(String result) {
		// Log.i("print", "ProductsByIdBean---> " + result);
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			ProductsByIdBean productsBean = com.alibaba.fastjson.JSON
					.parseObject(result, ProductsByIdBean.class);
			return productsBean;
		}
		return null;
	}

	public static ProductsEnterpriseByIdBean parseEnterpriseProductsByIdJson(
			String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			ProductsEnterpriseByIdBean productsBean = com.alibaba.fastjson.JSON
					.parseObject(result, ProductsEnterpriseByIdBean.class);
			return productsBean;
		}
		return null;
	}

	/**
	 * 解析发布产品返回信息
	 * 
	 * @param result
	 * @return
	 */
	public static PublishProductBean parseProductBackFormServer(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			PublishProductBean publishProductBean = com.alibaba.fastjson.JSON
					.parseObject(result, PublishProductBean.class);
			return publishProductBean;
		}
		return null;
	}

	/**
	 * 解析评论
	 * 
	 * @param result
	 * @return
	 */
	public static List<ProductCommentBean> parseCommentProductBean(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<ProductCommentBean> publishProductBeans = new ArrayList<ProductCommentBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				ProductCommentBean publishProductBean = com.alibaba.fastjson.JSON
						.parseObject(object.get(string).toString(),
								ProductCommentBean.class);
				publishProductBeans.add(publishProductBean);
			}
			return publishProductBeans;

		}
		return null;
	}

	/**
	 * 解析招聘信息
	 * 
	 * @param result
	 * @return
	 */
	public static List<JobBean> parseJobJson(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSON
					.parseObject(result);
			List<JobBean> jobBeans = new ArrayList<JobBean>();
			Iterator<String> sIterator = object.keySet().iterator();
			while (sIterator.hasNext()) {
				String string = (String) sIterator.next();
				JobBean attactBean = com.alibaba.fastjson.JSON.parseObject(
						object.get(string).toString(), JobBean.class);
				jobBeans.add(attactBean);
			}

			return jobBeans;
		}
		return null;
	}

	public static JobItemBean parseJobJsonById(String result) {
		if ((result != null) && (!result.toString().equals("[]"))
				&& (!result.toString().equals(""))) {
			JobItemBean jobBeans = com.alibaba.fastjson.JSON.parseObject(
					result, JobItemBean.class);

			return jobBeans;
		}
		return null;
	}
}
