package com.mzhou.merchant.db.manager;

import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.model.NewsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbJobManager {

		private static DbJobManager instance = null;
		private String DATABASE_TABLENAME = "tbJob";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_ID = SELECT_ALL + " where id = ?";
		private static DBManager manager = null;

		private DbJobManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbJobManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbJobManager(context);
			}
			return instance;
		}
	
		public long insert(JobBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				values.put("id", bean.getId());
				values.put("position", bean.getPosition());
				values.put("content", bean.getContent());
				values.put("contact", bean.getContact());
				values.put("phone", bean.getPhone());
				values.put("ctime", bean.getCtime());
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

 
		public LinkedList<JobBean> getAdByID(String id) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<JobBean>() {

				@Override
				public JobBean mapRow(Cursor cursor, int index) {
					JobBean bean = new JobBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setPosition(cursor.getString(cursor.getColumnIndex("position")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
					return bean;
				}

			}, SELECT_BY_ID, new String[] { String.valueOf(id) });
		}
		public LinkedList<JobBean> getListAll() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<JobBean>() {
				
				@Override
				public JobBean mapRow(Cursor cursor, int index) {
					JobBean bean = new JobBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setPosition(cursor.getString(cursor.getColumnIndex("position")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
					return bean;
				}
				
			}, SELECT_ALL, null);
		}
		 
	}

