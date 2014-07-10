package com.mzhou.merchant.db.manager;

import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.NewsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbNewsManager {

		private static DbNewsManager instance = null;
		private String DATABASE_TABLENAME = "tbNews";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_ID = SELECT_ALL + " where id = ?";
		private static DBManager manager = null;

		private DbNewsManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbNewsManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbNewsManager(context);
			}
			return instance;
		}

		public long insert(NewsBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				values.put("id", bean.getId());
				values.put("title", bean.getTitle());
				values.put("ctime", bean.getCtime());
				values.put("source", bean.getSource());
				values.put("pic", bean.getPic());
				return st.insert(DATABASE_TABLENAME, values);
		}

		public void delete(int _id)   {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "id=?", new String[]{String.valueOf(_id)});
			System.out.println("DELETE SUCCESSFULL !!!!");

		}
	 

		public void deleteAll()  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.execSQL(DELETE_ALL );
			System.out.println("DELETE ALL SUCCESSFULL !!!!");
		}

	 
	 
		public LinkedList<NewsBean> getAdByID(String id) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<NewsBean>() {

				@Override
				public NewsBean mapRow(Cursor cursor, int index) {
					NewsBean bean = new NewsBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setSource(cursor.getString(cursor.getColumnIndex("source")));
					bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
					return bean;
				}

			}, SELECT_BY_ID, new String[] { String.valueOf(id) });
		}
		public LinkedList<NewsBean> getListAll() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<NewsBean>() {
				
				@Override
				public NewsBean mapRow(Cursor cursor, int index) {
					NewsBean bean = new NewsBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setSource(cursor.getString(cursor.getColumnIndex("source")));
					bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
					return bean;
				}
				
			}, SELECT_ALL, null);
		}
		 
	}

