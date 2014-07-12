package com.mzhou.merchant.db.manager;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.UserInfoBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbUserManager {
	
		private static DbUserManager instance = null;
		private String DATABASE_TABLENAME = "tbUserInfo";
		private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
		private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
		private String SELECT_BY_USERTYPE_AND_USERNAME = SELECT_ALL
				+ " where usertype = ? and username = ? ";
		private String SELECT_BY_STATUS = SELECT_ALL
				+ " where status = ? ";
		private static DBManager manager = null;

		private DbUserManager(Context context) {
			manager = DBManager.getInstance(context);
		}

		public static DbUserManager getInstance(Context context) {
			if (instance == null) {
				instance = new DbUserManager(context);
			}
			return instance;
		}
		/**
		 * 插入数据，登录的时候用
		 * 插入的时候需要将登录状态设置为1，其他的都清除掉
		 * @param bean
		 */
		public void insertData(UserInfoBean bean) {
			updateStauts();//更新用户登录状态
		if (isExist(bean)) {//判断是否存在此帐号，如果存在需要更新此帐号数据
			updateByUserTypeAndUserName(bean);
		}else {//如果不存在就插入最新数据
			insert(bean);
		}
		}
		/**
		 * 插入一条数据
		 * 
		 * @param bean
		 * @return
		 */
		private long insert(UserInfoBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
				ContentValues values = new ContentValues();
				if (bean.getUsername() != null) {
					values.put("username", bean.getUsername());
				}
				if (bean.getPassword() != null) {
					values.put("password", bean.getPassword());
				}
				if (bean.getUsertype() != null) {
					values.put("usertype", bean.getUsertype());
				}
				if (bean.getUid() != null) {
					values.put("uid", bean.getUid());
				}
				if (bean.getCenter() != null) {
					values.put("center", bean.getCenter());
				}
				if (bean.getCompany() != null) {
					values.put("company", bean.getCompany());
				}
				if (bean.getCategory() != null) {
					values.put("category", bean.getCategory());
				}
				if (bean.getFax() != null) {
					values.put("fax", bean.getFax());
				}
				if (bean.getNet() != null) {
					values.put("net", bean.getNet());
				}
				if (bean.getNickname() != null) {
					values.put("nickname", bean.getNickname());
				}
				if (bean.getContact() != null) {
					values.put("contact", bean.getContact());
				}
				if (bean.getPhonenub() != null) {
					values.put("phonenub", bean.getPhonenub());
				}
				if (bean.getEmail() != null) {
					values.put("email", bean.getEmail());
				}
				if (bean.getHeadurl() != null) {
					values.put("headurl", bean.getHeadurl());
				}
				if (bean.getAddress() != null) {
					values.put("address", bean.getAddress());
				}
				if (bean.getStatus() != null) {
					values.put("status", bean.getStatus());
				}
				return st.insert(DATABASE_TABLENAME, values);
		}
		/**
		 * 更新一条数据
		 * @param bean
		 * @return
		 */
		public long updateByUserTypeAndUserName(UserInfoBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			ContentValues values = new ContentValues();
			if (bean.getUsername() != null) {
				values.put("username", bean.getUsername());
			}
			if (bean.getPassword() != null) {
				values.put("password", bean.getPassword());
			}
			if (bean.getUsertype() != null) {
				values.put("usertype", bean.getUsertype());
			}
			if (bean.getUid() != null) {
				values.put("uid", bean.getUid());
			}
			if (bean.getCenter() != null) {
				values.put("center", bean.getCenter());
			}
			if (bean.getCompany() != null) {
				values.put("company", bean.getCompany());
			}
			if (bean.getCategory() != null) {
				values.put("category", bean.getCategory());
			}
			if (bean.getFax() != null) {
				values.put("fax", bean.getFax());
			}
			if (bean.getNet() != null) {
				values.put("net", bean.getNet());
			}
			if (bean.getNickname() != null) {
				values.put("nickname", bean.getNickname());
			}
			if (bean.getContact() != null) {
				values.put("contact", bean.getContact());
			}
			if (bean.getPhonenub() != null) {
				values.put("phonenub", bean.getPhonenub());
			}
			if (bean.getEmail() != null) {
				values.put("email", bean.getEmail());
			}
			if (bean.getHeadurl() != null) {
				values.put("headurl", bean.getHeadurl());
			}
			if (bean.getAddress() != null) {
				values.put("address", bean.getAddress());
			}
			if (bean.getStatus() != null) {
				values.put("status", bean.getStatus());
			}
			
			st.update(DATABASE_TABLENAME, values, "usertype =? and username = ?", new String[]{bean.getUsertype(),bean.getUsername()});
			return st.insert(DATABASE_TABLENAME, values);
		}
		/**
		 * 跟据当前状态来更新数据
		 * 1.在会员中心绑定qq号时用此方法
		 * @param bean
		 * @return
		 */
		public long updateByStatus(UserInfoBean bean) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			ContentValues values = new ContentValues();
			if (bean.getUsername() != null) {
				values.put("username", bean.getUsername());
			}
			if (bean.getPassword() != null) {
				values.put("password", bean.getPassword());
			}
			if (bean.getUsertype() != null) {
				values.put("usertype", bean.getUsertype());
			}
			if (bean.getUid() != null) {
				values.put("uid", bean.getUid());
			}
			if (bean.getCenter() != null) {
				values.put("center", bean.getCenter());
			}
			if (bean.getCompany() != null) {
				values.put("company", bean.getCompany());
			}
			if (bean.getCategory() != null) {
				values.put("category", bean.getCategory());
			}
			if (bean.getFax() != null) {
				values.put("fax", bean.getFax());
			}
			if (bean.getNet() != null) {
				values.put("net", bean.getNet());
			}
			if (bean.getNickname() != null) {
				values.put("nickname", bean.getNickname());
			}
			if (bean.getContact() != null) {
				values.put("contact", bean.getContact());
			}
			if (bean.getPhonenub() != null) {
				values.put("phonenub", bean.getPhonenub());
			}
			if (bean.getEmail() != null) {
				values.put("email", bean.getEmail());
			}
			if (bean.getHeadurl() != null) {
				values.put("headurl", bean.getHeadurl());
			}
			if (bean.getAddress() != null) {
				values.put("address", bean.getAddress());
			}
			if (bean.getStatus() != null) {
				values.put("status", bean.getStatus());
			}
			
			st.update(DATABASE_TABLENAME, values, " status =? ", new String[]{"1"});
			return st.insert(DATABASE_TABLENAME, values);
		}
		/**
		 * 更新用户登录状态
		 */
		public void updateStauts() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			ContentValues values = new ContentValues();
			values.put("status", "0");
			st.update(DATABASE_TABLENAME, values, null,
					null);
		}
		/**
		 * 退出登录状态,大于0表示更新成功
		 * @return
		 */
		public int updateLoginStatus() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			ContentValues values = new ContentValues();
				values.put("status", "0");
			return st.update(DATABASE_TABLENAME, values, null, null);
		}
		/**
		 * 删除一条数据
		 * @param _id
		 */
		public void delete(int _id)   {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.deleteByCondition(DATABASE_TABLENAME, "_id=?", new String[]{String.valueOf(_id)});

		}
	 /**
	  * 删除全部数据
	  */
		public void deleteAll()  {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			st.execSQL(DELETE_ALL );
		}
		/**
		 * 判断当前帐号存不存在
		 * 主要用途
		 * 1.登录的时候，插入数据
		 * @param username
		 * @param usertype
		 */
		public boolean isExist(UserInfoBean bean){
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.isExistsBySQL(SELECT_BY_USERTYPE_AND_USERNAME, new String[]{bean.getUsertype(),bean.getUsername()});
		}
	 
	/**
	 * 根据用户名称和用户类型查询用户的详细信息
	 * @param usertype
	 * @param username
	 * @return
	 */
		public UserInfoBean getUserInfoByUserNameAndUserType(String usertype,String username) {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForObject(new RowMapper<UserInfoBean>() {
				@Override
				public UserInfoBean mapRow(Cursor cursor, int index) {
					UserInfoBean bean = new UserInfoBean();
					bean.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					bean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
					bean.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
					bean.setPhonenub(cursor.getString(cursor.getColumnIndex("phonenub")));
					bean.setEmail(cursor.getString(cursor.getColumnIndex("email")));
					bean.setHeadurl(cursor.getString(cursor.getColumnIndex("headurl")));
					bean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
					bean.setNet(cursor.getString(cursor.getColumnIndex("net")));
					bean.setFax(cursor.getString(cursor.getColumnIndex("fax")));
					bean.setCenter(cursor.getString(cursor.getColumnIndex("center")));
					bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
					bean.setUsertype(cursor.getString(cursor.getColumnIndex("usertype")));
					bean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
					return bean;
				}
			}, SELECT_BY_USERTYPE_AND_USERNAME, new String[] {usertype,username });
		}
		/**
		 * 查询出当前登录帐号的详细信息
		 * 主要用途
		 * 1.左侧详细信息
		 * 2.用户中心详细信息
		 * @return
		 */
		public UserInfoBean getLogingUserInfo() {
			SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
			return st.queryForObject(new RowMapper<UserInfoBean>() {
				@Override
				public UserInfoBean mapRow(Cursor cursor, int index) {
					UserInfoBean bean = new UserInfoBean();
					bean.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
					bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
					bean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
					bean.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
					bean.setContact(cursor.getString(cursor.getColumnIndex("contact")));
					bean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
					bean.setPhonenub(cursor.getString(cursor.getColumnIndex("phonenub")));
					bean.setEmail(cursor.getString(cursor.getColumnIndex("email")));
					bean.setHeadurl(cursor.getString(cursor.getColumnIndex("headurl")));
					bean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
					bean.setNet(cursor.getString(cursor.getColumnIndex("net")));
					bean.setFax(cursor.getString(cursor.getColumnIndex("fax")));
					bean.setCenter(cursor.getString(cursor.getColumnIndex("center")));
					bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
					bean.setUsertype(cursor.getString(cursor.getColumnIndex("usertype")));
					bean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
					return bean;
				}
			}, SELECT_BY_STATUS, new String[] {"1" });
		}
		 
	}

