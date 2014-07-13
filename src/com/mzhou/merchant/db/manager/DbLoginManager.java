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
	private String SELECT_BY_NEEDLOGIN = SELECT_ALL
			+ " where needlogin = ? ";
	private String SELECT_CURRENT_USER = SELECT_ALL
			+ " where status = ?";
	private String SELECT_CURRENT_USER_AND_USERTYPE= SELECT_ALL
			+ " where status = ? and usertype =?";
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
	 * 2.注册界面
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
	 * 1.登录界面，点击登录
	 * 2.注册界面
	 * @param bean
	 */
	private void insert(LoginUserBean bean) {
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
		if (bean.getLastlogin() != null) {
			values.put("lastlogin", bean.getLastlogin());
		}
		if (bean.getIsremeber() != null) {
			values.put("isremeber", bean.getIsremeber());
		}
		if (bean.getIsloginself() != null) {
			values.put("isloginself", bean.getIsloginself());
		}
		if (bean.getIsbinder() != null) {
			values.put("isbinder", bean.getIsbinder());
		}
		if (bean.getQq() != null) {
			values.put("qq", bean.getQq());
		}
		if (bean.getOpenid() != null) {
			values.put("openid", bean.getOpenid());
		}
		if (bean.getNeedlogin() != null) {
			values.put("needlogin", bean.getNeedlogin());
		}
		if (bean.getStatus() != null) {
			values.put("status", bean.getStatus());
		}
		
		st.insert(DATABASE_TABLENAME, values);
	}

	/**
	 * 根据用户名称跟用户类型来更新登录配置信息，需要将上次登录的改为1，登录状态改为1
	 * 主要用途
	 * 1.登录界面，再次的登录的时候,更新帐号信息
	 * 2.注册界面
	 * 3.用户详情界面
	 * @param bean
	 */
	public void updateByUserNameAndUserType(LoginUserBean bean) {
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
		if (bean.getLastlogin() != null) {
			values.put("lastlogin", bean.getLastlogin());
		}
		if (bean.getIsremeber() != null) {
			values.put("isremeber", bean.getIsremeber());
		}
		if (bean.getIsloginself() != null) {
			values.put("isloginself", bean.getIsloginself());
		}
		if (bean.getIsbinder() != null) {
			values.put("isbinder", bean.getIsbinder());
		}
		if (bean.getQq() != null) {
			values.put("qq", bean.getQq());
		}
		if (bean.getOpenid() != null) {
			values.put("openid", bean.getOpenid());
		}
		if (bean.getNeedlogin() != null) {
			values.put("needlogin", bean.getNeedlogin());
		}
		if (bean.getStatus() != null) {
			values.put("status", bean.getStatus());
		}
 
		st.update(DATABASE_TABLENAME, values, " username=? and  usertype = ? ",
				new String[] { bean.getUsername(), bean.getUsertype() });
	}
	/**
	 * 更新当前用户的信息
	 * 主要用途
	 * 1.qq登录绑定之后，默认qq绑定的帐号是qq，密码是123
	 * @param bean
	 */
	public void updateByStatus(LoginUserBean bean) {
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
		if (bean.getLastlogin() != null) {
			values.put("lastlogin", bean.getLastlogin());
		}
		if (bean.getIsremeber() != null) {
			values.put("isremeber", bean.getIsremeber());
		}
		if (bean.getIsloginself() != null) {
			values.put("isloginself", bean.getIsloginself());
		}
		if (bean.getIsbinder() != null) {
			values.put("isbinder", bean.getIsbinder());
		}
		if (bean.getQq() != null) {
			values.put("qq", bean.getQq());
		}
		if (bean.getOpenid() != null) {
			values.put("openid", bean.getOpenid());
		}
		if (bean.getNeedlogin() != null) {
			values.put("needlogin", bean.getNeedlogin());
		}
		if (bean.getStatus() != null) {
			values.put("status", bean.getStatus());
		}
		
		st.update(DATABASE_TABLENAME, values, " status=? ",
				new String[] {"1"});
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
		return st.isExistsBySQL(SELECT_CURRENT_USER, new String[] {
				"1" });
	}
	/**
	 * 获取当前登录的用户类型
	 * 1.在点击发布产品的时候进行判断，如果有登录的类型就进入到发布产品界面，否则进入登录界面
	 * @param usertype
	 * @return
	 */
	public boolean getLoginStatusByUsertype(String usertype) {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.isExistsBySQL(SELECT_CURRENT_USER_AND_USERTYPE, new String[] {
		"1" ,usertype});
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
	 * 判断是否需要自动登录
	 * 主要用途
	 * 1.软件启动的时候，如果需要自动登录就启动
	 * @return
	 */
	public boolean getNeedLoginStatus() {
		SQLiteTemplate st = SQLiteTemplate.getInstance(manager, false);
		return st.isExistsBySQL(SELECT_BY_NEEDLOGIN, new String[] {  "1" });
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
	 * 获取需要自动登录的帐号密码
	 * 主要用途
	 * 1.软件启动的时候查询是否需要自动登录
	 * @return
	 */
	public LoginUserBean getUserByNeedLogin() {
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
		}, SELECT_BY_NEEDLOGIN, new String[] {  "1" });
	}
	/**
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
