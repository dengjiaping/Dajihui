package com.mzhou.merchant.db.manager;

import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.model.NewsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbAttachManager {

		private static DbAttachManager instance = null;
		private String DATABASE_TABLENAME = "tbAttach";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_CATEGORY = SELECT_ALL + " where category = ?";
		private static DBManager manager = null;

		private DbAttachManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbAttachManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbAttachManager(context);
			}
			return instance;
		}

		public long insert(AttactBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				
				values.put("content", bean.getContent());
				values.put("email", bean.getEmail());
				values.put("ctime", bean.getCtime());
				values.put("contact", bean.getContact());
				values.put("category", bean.getCategory());
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

	 
	 
 
		public LinkedList<AttactBean> getAdByCategory(String  category) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<AttactBean>() {
				
				@Override
				public AttactBean mapRow(Cursor cursor, int index) {
					AttactBean bean = new AttactBean();
					bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
					bean.setEmail(cursor.getString(cursor.getColumnIndex("email")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					return bean;
				}
				
			}, SELECT_BY_CATEGORY, new String[] { category });
		}
		public LinkedList<AttactBean> getListAll() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<AttactBean>() {
				
				@Override
				public AttactBean mapRow(Cursor cursor, int index) {
					AttactBean bean = new AttactBean();
					bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
					bean.setEmail(cursor.getString(cursor.getColumnIndex("email")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					return bean;
				}
				
			}, SELECT_ALL, null);
		}
		 
	}

