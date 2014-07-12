package com.mzhou.merchant.utlis;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

public class GetPhoneNum {
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID, Phone.CONTACT_ID };
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 联系人显示名称 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 电话号码 **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	/** 头像ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	private Context context;
	
	public GetPhoneNum(Context context){
		this.context = context;
	}
	/**
	 * 得到手机SIM卡联系人人信息
	 * 
	 * @return
	 **/
	public List<UserBean> getSIMContacts() {
		List<UserBean> addressRelateLists = new ArrayList<UserBean>();
		String phoneNumber;// 联系人手机号码
		String contactName;// 联系人名称
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://icc/adn");// 获取Sims卡联系人
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);// 得到手机号码
				if (TextUtils.isEmpty(phoneNumber))// 当手机号码为空的或者为空字段 跳过当前循环
					continue;
				contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);// 得到联系人名称
				UserBean userBean = new UserBean(contactName, phoneNumber);
				addressRelateLists.add(userBean);
			}
			phoneCursor.close();
		}
		return addressRelateLists;
	}

	/** 得到手机通讯录联系人信息 **/
	public List<UserBean> getPhoneContacts() {
		List<UserBean> addressRelateLists = new ArrayList<UserBean>();
		ContentResolver resolver = context.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);// 获取手机联系人
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber_ph = phoneCursor.getString(PHONES_NUMBER_INDEX);// 得到手机号码
				if (TextUtils.isEmpty(phoneNumber_ph))// 当手机号码为空的或者为空字段 跳过当前循环
					continue;
				String contactName_ph = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);// 得到联系人名称
				Long contactid_ph = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);// 得到联系人ID
				Long photoid_ph = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);// 得到联系人头像ID
				// Bitmap contactPhoto = null;//得到联系人头像Bitamp
				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid_ph > 0) {
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid_ph);
					ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
				} else {
				}
				addressRelateLists.add(new UserBean(contactName_ph, phoneNumber_ph));
			}
			phoneCursor.close();
		}
		return addressRelateLists;
	}
	public class UserBean{
		private String username;
		private String phonenum;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPhonenum() {
			return phonenum;
		}
		public void setPhonenum(String phonenum) {
			this.phonenum = phonenum;
		}
		@Override
		public String toString() {
			return "[username=" + username + ", phonenum=" + phonenum
					+ "]";
		}
		public UserBean(String username, String phonenum) {
			super();
			this.username = username;
			this.phonenum = phonenum;
		}
		public UserBean() {
			super();
		}
		
	}
}
