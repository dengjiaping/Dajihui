package com.mzhou.merchant.db.manager;

import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.AdBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbAdManager {

		private static DbAdManager instance = null;
		private String DATABASE_TABLENAME = "tbAd";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_ID = SELECT_ALL + " where id = ?";
		private String SELECT_BY_CATEGORY = SELECT_ALL + " where category = ?";
		private static DBManager manager = null;

		private DbAdManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbAdManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbAdManager(context);
			}
			return instance;
		}

		public long insert(AdBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				values.put("id", bean.getId());
				values.put("category", bean.getCategory());
				values.put("name", bean.getName());
				values.put("order_sort", bean.getOrder_sort());
				values.put("pic", bean.getPic());
				values.put("type", bean.getType());
				values.put("url", bean.getUrl());
				return st.insert(DATABASE_TABLENAME, values);
		}

		public void delete(int _id)   {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "id=?", new String[]{String.valueOf(_id)});
			System.out.println("DELETE SUCCESSFULL !!!!");

		}
		public void deleteByCategory(String category)   {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "category=?", new String[]{category});
			System.out.println("DELETE SUCCESSFULL !!!!");
			
		}

		public void deleteAll()  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.execSQL(DELETE_ALL );
			System.out.println("DELETE ALL SUCCESSFULL !!!!");
		}

	 
	 
		public LinkedList<AdBean> getAdByID(String id) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<AdBean>() {

				@Override
				public AdBean mapRow(Cursor cursor, int index) {
					AdBean bean = new AdBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					bean.setName(cursor.getString(cursor.getColumnIndex("name")));
					bean.setOrder_sort(cursor.getString(cursor.getColumnIndex("order_sort")));
					bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
					bean.setType(cursor.getString(cursor.getColumnIndex("type")));
					bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
					return bean;
				}

			}, SELECT_BY_ID, new String[] { String.valueOf(id) });
		}
		
		public  LinkedList<AdBean>  getAdByCategory(String categroy)  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<AdBean>() {
				
				@Override
				public AdBean mapRow(Cursor cursor, int index) {
					AdBean bean = new AdBean();
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setName(cursor.getString(cursor.getColumnIndex("name")));
					bean.setOrder_sort(cursor.getString(cursor.getColumnIndex("order_sort")));
					bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
					bean.setType(cursor.getString(cursor.getColumnIndex("type")));
					bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
					return bean;
				}
				
			}, SELECT_BY_CATEGORY, new String[] { String.valueOf(categroy) });
		}

		 
	}

