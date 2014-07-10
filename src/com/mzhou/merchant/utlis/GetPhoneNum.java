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
	/** ��ϵ����ʾ���� **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** �绰���� **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	/** ͷ��ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	private Context context;
	
	public GetPhoneNum(Context context){
		this.context = context;
	}
	/**
	 * �õ��ֻ�SIM����ϵ������Ϣ
	 * 
	 * @return
	 **/
	public List<UserBean> getSIMContacts() {
		List<UserBean> addressRelateLists = new ArrayList<UserBean>();
		String phoneNumber;// ��ϵ���ֻ�����
		String contactName;// ��ϵ������
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.parse("content://icc/adn");// ��ȡSims����ϵ��
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);// �õ��ֻ�����
				if (TextUtils.isEmpty(phoneNumber))// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
					continue;
				contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);// �õ���ϵ������
				UserBean userBean = new UserBean(contactName, phoneNumber);
				addressRelateLists.add(userBean);
			}
			phoneCursor.close();
		}
		return addressRelateLists;
	}

	/** �õ��ֻ�ͨѶ¼��ϵ����Ϣ **/
	public List<UserBean> getPhoneContacts() {
		List<UserBean> addressRelateLists = new ArrayList<UserBean>();
		ContentResolver resolver = context.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);// ��ȡ�ֻ���ϵ��
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber_ph = phoneCursor.getString(PHONES_NUMBER_INDEX);// �õ��ֻ�����
				if (TextUtils.isEmpty(phoneNumber_ph))// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
					continue;
				String contactName_ph = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);// �õ���ϵ������
				Long contactid_ph = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);// �õ���ϵ��ID
				Long photoid_ph = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);// �õ���ϵ��ͷ��ID
				// Bitmap contactPhoto = null;//�õ���ϵ��ͷ��Bitamp
				// photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�
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
			return "UserBean [username=" + username + ", phonenum=" + phonenum
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
