package com.mzhou.merchant.db.manager;

import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.ProductsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbProductManager {

		private static DbProductManager instance = null;
		private String DATABASE_TABLENAME = "tbProduct";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_CATEGORY = SELECT_ALL + " where category = ?";
		private String SELECT_BY_ITEM = SELECT_ALL + " where category = ? and ctime = ? and brand = ? ";
		private static DBManager manager = null;

		private DbProductManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbProductManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbProductManager(context);
			}
			return instance;
		}
		
		public long insert(ProductsBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				values.put("id", bean.getId());
				values.put("ctime", bean.getCtime());
				values.put("classid", bean.getClassid());
				values.put("brand", bean.getBrand());
				values.put("is_en", bean.getIs_en());
				values.put("is_show", bean.getIs_show());
				values.put("pic", bean.getPic());
				values.put("order_sort", bean.getOrder_sort());
				values.put("category", bean.getCategory());
				return st.insert(DATABASE_TABLENAME, values);
		}

		public void delete(int _id)   {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "id=?", new String[]{String.valueOf(_id)});
			System.out.println("DELETE SUCCESSFULL !!!!");

		}
	 

		public void deleteByCategory(String category)  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "category=?", new String[]{category});
		}
		public void deleteByItem(String productid)  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "id = ? ", new String[]{productid});
		}
		public void deleteAll()  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.execSQL(DELETE_ALL );
			System.out.println("DELETE ALL SUCCESSFULL !!!!");
		}

	 
	
		public LinkedList<ProductsBean> getProductByCategory(String category) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<ProductsBean>() {

				@Override
				public ProductsBean mapRow(Cursor cursor, int index) {
					ProductsBean bean = new ProductsBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setClassid(cursor.getString(cursor.getColumnIndex("classid")));
					bean.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
					bean.setIs_en(cursor.getString(cursor.getColumnIndex("is_en")));
					bean.setIs_show(cursor.getString(cursor.getColumnIndex("is_show")));
					bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
					bean.setOrder_sort(cursor.getString(cursor.getColumnIndex("order_sort")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					return bean;
				}

			}, SELECT_BY_CATEGORY, new String[] {category });
		}
		public LinkedList<ProductsBean> getListAll() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<ProductsBean>() {
				
				@Override
				public ProductsBean mapRow(Cursor cursor, int index) {
					ProductsBean bean = new ProductsBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setClassid(cursor.getString(cursor.getColumnIndex("classid")));
					bean.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
					bean.setIs_en(cursor.getString(cursor.getColumnIndex("is_en")));
					bean.setIs_show(cursor.getString(cursor.getColumnIndex("is_show")));
					bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
					bean.setOrder_sort(cursor.getString(cursor.getColumnIndex("order_sort")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					return bean;
				}
				
			}, SELECT_ALL, null);
		}
		 
	}

