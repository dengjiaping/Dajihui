package com.mzhou.merchant.db.manager;

import com.mzhou.merchant.db.DBManager;
import com.mzhou.merchant.db.SQLiteTemplate;
import com.mzhou.merchant.db.SQLiteTemplate.RowMapper;
import com.mzhou.merchant.model.LoginUserBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
public class DbLoginManager {
	private static DbLoginManager instance = null;
	private String DATABASE_TABLENAME = "tbLogin";
	private String DELETE_ALL = "delete from " + DATABASE_TABLENAME;
	private String SELECT_ALL = "select * from " + DATABASE_TABLENAME;
	private String SELECT_BY_USERTYPE = SELECT_ALL
			+ " where usertype = ? and lastlogin = ? ";
	private String SELECT_CURRENT_USER = SELECT_ALL
			+ " where status = ?";
	private String SELECT_BY_USERTYPE_AND_USERNAME = SELECT_ALL
			+ " where usertype = ? and username = ? ";
	private static DBManager manager = null;

	private DbLoginManager(Context context) {
		manager = DBManager.getInstance(context);
	}

	public static DbLoginManager getInstance(Context context) {
		if (instance == null) {
			instance = new DbLoginManager(context);
		}
		return instance;
	}

	/**
	 * 插入新的数据库，如果本地不存在就插入，否则就更新 主要用途 1.登录界面，点击登录
	 * 
	 * @param bean
	 * @return
	 */
	public void insertData(LoginUserBean bean) {
		updateStauts();//清除所有登录状态
		updateByUserType(bean.getUsertype());//清除当前登录状态
		if (isExist(bean.getUsertype(), bean.getUsername())) {
			updateByUserNameAndUserType(bean);
		} else {
			insert(bean);
		}
	}

	/**
	 * 插入新的一条数据
	 * 
	 * @param bean
	 */
	private void insert(LoginUserBean bean) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues values = new ContentValues();
		values.put("lastlogin", bean.getLastlogin());
		values.put("username", bean.getUsername());
		values.put("password", bean.getPassword());
		values.put("usertype", bean.getUsertype());
		values.put("isremeber", bean.getIsremeber());
		values.put("isloginself", bean.getIsloginself());
		values.put("isbinder", bean.getIsbinder());
		values.put("qq", bean.getQq());
		values.put("openid", bean.getOpenid());
		values.put("status", bean.getStatus());
		values.put("needlogin", bean.getNeedlogin());

		st.insert(DATABASE_TABLENAME, values);
	}

	/**
	 * 根据用户名称跟用户类型来更新登录配置信息，需要将上次登录的改为1，登录状态改为1
	 * 主要用途
	 * 1.登录界面，再次的登录的时候,更新帐号信息
	 * 
	 * @param bean
	 */
	public void updateByUserNameAndUserType(LoginUserBean bean) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues values = new ContentValues();
		values.put("lastlogin", bean.getLastlogin());
		values.put("username", bean.getUsername());
		values.put("password", bean.getPassword());
		values.put("isremeber", bean.getIsremeber());
		values.put("isloginself", bean.getIsloginself());
		values.put("status", bean.getStatus());
		values.put("needlogin", bean.getNeedlogin());
		st.update(DATABASE_TABLENAME, values, " username=? and  usertype = ? ",
				new String[] { bean.getUsername(), bean.getUsertype() });
	}
	/**
	 * 根据类型来将用户上次登录状态全部清除
	 * @param bean
	 */
	public void updateByUserType(String usertype) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues values = new ContentValues();
		values.put("lastlogin", "0");
		values.put("needlogin", "0");
		st.update(DATABASE_TABLENAME, values, "usertype = ? ",
				new String[] { usertype});
	}
	/**
	 * 清除所有登录状态
	 * 1.退出登录的时候
	 * 2.登录成功，修改状态之前
	 */
	public void updateStauts() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		ContentValues values = new ContentValues();
		values.put("status", "0");
		st.update(DATABASE_TABLENAME, values, null,
				null);
	}

	/**
	 * 根据id来删除某条登录信息
	 * 
	 * @param _id
	 */
	public void delete(int _id) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		st.deleteByCondition(DATABASE_TABLENAME, "_id=?",
				new String[] { String.valueOf(_id) });

	}

	/**
	 * 清除所有登录数据
	 */
	public void deleteAll() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		st.execSQL(DELETE_ALL);
	}

	/**
	 * 判断当前数据库里面有没有帐号
	 * 
	 * @param usertype
	 * @param username
	 * @return
	 */
	public boolean isExist(String usertype, String username) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.isExistsBySQL(SELECT_BY_USERTYPE_AND_USERNAME, new String[] {
				usertype, username });
	}

	/**
	 * 获取登录状态，如果有就说明当前有登录的帐号，否者说名没有登录的状态，需要重新登录 1.主要用途，左侧头像 如果返回未false就进入相应的登录界面
	 * 2.右侧会员中心 如果返回为false 就进入相应的登录界面 3.点击发布产品的时候，如果返回未false就进入相应的登录界面
	 * 
	 * @return
	 */
	public boolean getLoginStatus() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.isExistsByField(DATABASE_TABLENAME, "status", "1");
	}
	/**
	 * 判断是否需要自动登录
	 * 主要用途
	 * 1.软件启动的时候，如果需要自动登录就启动
	 * @return
	 */
	public boolean getNeedLoginStatus() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.isExistsByField(DATABASE_TABLENAME, "needlogin", "1");
	}

	/**
	 * 根据用户类型来查询上一次登录的帐号，如普通会员用 usertype=0，企业会员usertype=1,默认的lastlogin=1 主要用途
	 * 1.显示在会员登录界面，根据类型不同显示的帐号密码不同
	 * 2.在发布产品界面查询出用户的详细信息，如，发布普通会员产品就显示普通会员上次登录的详细信息并且填充到对应的输入框 企业会员也是类似
	 * 
	 * @param usertype
	 * @return
	 */
	public LoginUserBean getLastLoginByUserType(String usertype) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.queryForObject(new RowMapper<LoginUserBean>() {

			@Override
			public LoginUserBean mapRow(Cursor cursor, int index) {
				LoginUserBean bean = new LoginUserBean();
				bean.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
				bean.setLastlogin(cursor.getString(cursor
						.getColumnIndex("lastlogin")));
				bean.setUsername(cursor.getString(cursor
						.getColumnIndex("username")));
				bean.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
				bean.setUsertype(cursor.getString(cursor
						.getColumnIndex("usertype")));
				bean.setIsremeber(cursor.getString(cursor
						.getColumnIndex("isremeber")));
				bean.setIsloginself(cursor.getString(cursor
						.getColumnIndex("isloginself")));
				bean.setIsbinder(cursor.getString(cursor
						.getColumnIndex("isbinder")));
				bean.setQq(cursor.getString(cursor.getColumnIndex("qq")));
				bean.setOpenid(cursor.getString(cursor.getColumnIndex("openid")));
				bean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				bean.setNeedlogin(cursor.getString(cursor.getColumnIndex("needlogin")));
				return bean;
			}
		}, SELECT_BY_USERTYPE, new String[] { usertype, "1" });
	}
	/**
	 * 貌似不需要
	 * 查询出当前的帐号
	 * 主要用途
	 * 1.发布产品界面或者发布其他信息界面，获取当前用户的数据
	 * 2.查询出当前用户的帐号和用户类型去用户详细信息里面去查询用户的头像
	 * @return
	 */
	public LoginUserBean getCurrentBean() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.queryForObject(new RowMapper<LoginUserBean>() {
			
			@Override
			public LoginUserBean mapRow(Cursor cursor, int index) {
				LoginUserBean bean = new LoginUserBean();
				bean.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
				bean.setLastlogin(cursor.getString(cursor
						.getColumnIndex("lastlogin")));
				bean.setUsername(cursor.getString(cursor
						.getColumnIndex("username")));
				bean.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
				bean.setUsertype(cursor.getString(cursor
						.getColumnIndex("usertype")));
				bean.setIsremeber(cursor.getString(cursor
						.getColumnIndex("isremeber")));
				bean.setIsloginself(cursor.getString(cursor
						.getColumnIndex("isloginself")));
				bean.setIsbinder(cursor.getString(cursor
						.getColumnIndex("isbinder")));
				bean.setQq(cursor.getString(cursor.getColumnIndex("qq")));
				bean.setOpenid(cursor.getString(cursor.getColumnIndex("openid")));
				bean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
				bean.setNeedlogin(cursor.getString(cursor.getColumnIndex("needlogin")));
				return bean;
			}
		}, SELECT_CURRENT_USER, new String[] { "1" });
	}

}
