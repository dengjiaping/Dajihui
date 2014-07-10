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
	 * ��ȡ��Ʒ��Ϣ�ӿ�
	 */
	public interface IgetProductInfo {
		public void getProductInfo(List<ProductsBean> productsBeans);
	}

	/**
	 * ��ȡ��Ʒ���ӿ�
	 * 
	 * @author user
	 * 
	 */
	public interface IgetProductCategory {
		public void getProductCategory(List<CategoryBean> categoryBeans);
	}

	/**
	 * ���ݲ�Ʒid��ȡ��Ʒ��Ϣ
	 * 
	 * @author user
	 * 
	 */
	public interface IgetProductInfoById {
		public void getProductInfoById(ProductsByIdBean productsByIdBean);
	}

	/**
	 * ���ݲ�Ʒid��ȡ��ҵ��Ʒ
	 * 
	 * @author user
	 * 
	 */
	public interface IgetEnterpriseProductInfoById {
		public void getProductInfoById(
				ProductsEnterpriseByIdBean productsByIdBean);
	}

	/**
	 * ������Ʒ��Ϣ
	 * 
	 * @author user
	 * 
	 */
	public interface IPubProductInfo {
		public void getPubProductInfo(PublishProductBean publishProductBean);
	}

	/**
	 * ��ȡ����
	 * 
	 * @author user
	 * 
	 */
	public interface IGetCommentInfo {
		public void getCommentInfo(List<ProductCommentBean> productCommentBeans);
	}

	/**
	 * ��ȡ��˾���
	 * 
	 * @author user
	 * 
	 */
	public interface IGetCompanyInfo {
		public void getCompanyInfo(AboutCompany aboutCompany);
	}
	/**
	 * ɾ����Ʒ
	 * @author Mzhou
	 *
	 */
	public interface IDeleteProductInfo {
		public void deletProDuct(PublishProductBean productBean);
	}
}