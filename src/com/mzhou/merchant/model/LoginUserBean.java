package com.mzhou.merchant.model;

/**
 * 登录帐号本地保存的数据
 * @author Mzhou
 *
 */
public class LoginUserBean {
	private String lastlogin;//上一次登录 1表示是上次登录，0表示不是上次登录
	private String username;//帐号 默认是邮箱
	private String	password;//密码
	private String usertype;//是否是企业会员 0为普通会员，1为企业会员
	private String isremeber;//是否选择记住密码
	private String isloginself;//是否选择自动登录
	private String isbinder;//是否绑定
	private String qq;//绑定的qq号 
	private String openid;//绑定的openid
	private String status;//登录状态 0表示未登录，1表示已登录，
	private String needlogin;//需要自动登录只会存在 一次 0表示不需要自动登录，1表示需要自动登录
	private int _id;
	
	
	public LoginUserBean() {
		super();
	}
 
 

	public String getNeedlogin() {
		return needlogin;
	}



	public void setNeedlogin(String needlogin) {
		this.needlogin = needlogin;
	}



	public String getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getIsremeber() {
		return isremeber;
	}
	public void setIsremeber(String isremeber) {
		this.isremeber = isremeber;
	}
	public String getIsloginself() {
		return isloginself;
	}
	public void setIsloginself(String isloginself) {
		this.isloginself = isloginself;
	}
	public String getIsbinder() {
		return isbinder;
	}
	public void setIsbinder(String isbinder) {
		this.isbinder = isbinder;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return "LoginUserBean [lastlogin=" + lastlogin + ", username="
				+ username + ", password=" + password + ", usertype="
				+ usertype + ", isremeber=" + isremeber + ", isloginself="
				+ isloginself + ", isbinder=" + isbinder + ", qq=" + qq
				+ ", openid=" + openid + ", status=" + status + ", needlogin="
				+ needlogin + ", _id=" + _id + "]";
	}

 
 
	
}
