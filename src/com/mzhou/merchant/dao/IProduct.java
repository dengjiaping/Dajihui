package com.mzhou.merchant.dao;

import java.util.List;

import com.mzhou.merchant.model.AboutCompany;
import com.mzhou.merchant.model.CategoryBean;
import com.mzhou.merchant.model.ProductCommentBean;
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.model.ProductsByIdBean;
import com.mzhou.merchant.model.ProductsEnterpriseByIdBean;
import com.mzhou.merchant.model.PublishProductBean;

public class IProduct {
	/**
	 * 获取产品信息接口
	 */
	public interface IgetProductInfo {
		public void getProductInfo(List<ProductsBean> productsBeans);
	}

	/**
	 * 获取产品类别接口
	 * 
	 * @author user
	 * 
	 */
	public interface IgetProductCategory {
		public void getProductCategory(List<CategoryBean> categoryBeans);
	}

	/**
	 * 根据产品id获取产品信息
	 * 
	 * @author user
	 * 
	 */
	public interface IgetProductInfoById {
		public void getProductInfoById(ProductsByIdBean productsByIdBean);
	}

	/**
	 * 根据产品id获取企业产品
	 * 
	 * @author user
	 * 
	 */
	public interface IgetEnterpriseProductInfoById {
		public void getProductInfoById(
				ProductsEnterpriseByIdBean productsByIdBean);
	}

	/**
	 * 发布产品信息
	 * 
	 * @author user
	 * 
	 */
	public interface IPubProductInfo {
		public void getPubProductInfo(PublishProductBean publishProductBean);
	}

	/**
	 * 获取评论
	 * 
	 * @author user
	 * 
	 */
	public interface IGetCommentInfo {
		public void getCommentInfo(List<ProductCommentBean> productCommentBeans);
	}

	/**
	 * 获取公司简介
	 * 
	 * @author user
	 * 
	 */
	public interface IGetCompanyInfo {
		public void getCompanyInfo(AboutCompany aboutCompany);
	}
	/**
	 * 删除产品
	 * @author Mzhou
	 *
	 */
	public interface IDeleteProductInfo {
		public void deletProDuct(PublishProductBean productBean);
	}
}