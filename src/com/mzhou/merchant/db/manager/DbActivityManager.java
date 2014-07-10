package com.mzhou.merchant.db.manager;

import java.util.LinkedList;
import java.util.List;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.NewsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbActivityManager {

		private static DbActivityManager instance = null;
		private String DATABASE_TABLENAME = "tbActivity";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_ID = SELECT_ALL + " where id = ?";
		private static DBManager manager = null;

		private DbActivityManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbActivityManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbActivityManager(context);
			}
			return instance;
		}
	
		public long insert(ActivityBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				values.put("id", bean.getId());
				values.put("title", bean.getTitle()+"");
				values.put("ctime", bean.getCtime()+"");
				values.put("content", bean.getContent()+"");
				values.put("address", bean.getAddress()+"");
				values.put("lasttime", bean.getLasttime()+"");
				values.put("applytime", bean.getApplytime()+"");
				values.put("contact", bean.getContact()+"");
				values.put("phone", bean.getPhone()+"");
				values.put("uid", bean.getUid()+"");
				values.put("is_en", bean.getIs_en()+"");
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
 
	 
		public LinkedList<ActivityBean> getAdByID(String id) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<ActivityBean>() {

				@Override
				public ActivityBean mapRow(Cursor cursor, int index) {
					ActivityBean bean = new ActivityBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
					bean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
					bean.setLasttime(cursor.getString(cursor.getColumnIndex("lasttime")));
					bean.setApplytime(cursor.getString(cursor.getColumnIndex("applytime")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
					bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
					bean.setIs_en(cursor.getString(cursor.getColumnIndex("is_en")));
					return bean;
				}

			}, SELECT_BY_ID, new String[] { String.valueOf(id) });
		}
		public LinkedList<ActivityBean> getListAll() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForList(new  RowMapper<ActivityBean>() {
				
				@Override
				public ActivityBean mapRow(Cursor cursor, int index) {
					ActivityBean bean = new ActivityBean();
					bean.setId(cursor.getString(cursor.getColumnIndex("id")));
					bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
					bean.setCtime(cursor.getString(cursor.getColumnIndex("ctime")));
					bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
					bean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
					bean.setLasttime(cursor.getString(cursor.getColumnIndex("lasttime")));
					bean.setApplytime(cursor.getString(cursor.getColumnIndex("applytime")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
					bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
					bean.setIs_en(cursor.getString(cursor.getColumnIndex("is_en")));
					return bean;
				}
				
			}, SELECT_ALL, null);
		}
		 
	}

