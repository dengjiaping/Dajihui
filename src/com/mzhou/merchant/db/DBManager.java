package com.mzhou.merchant.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLite���ݿ������
 * 
 * ��Ҫ�������ݿ���Դ�ĳ�ʼ��,����,�ر�,�Լ����DatabaseHelper���������
 * 
 * @author shimiso
 * 
 */
public class DBManager {
	private int version = 1;
 	private String databaseName="dajihui_new";

	// ����Context����
	private Context mContext = null;

	private static DBManager dBManager = null;

	/**
	 * ���캯��
	 * 
	 * @param mContext
	 */
	private DBManager(Context mContext) {
		super();
		this.mContext = mContext;

	}

	public synchronized static DBManager getInstance(Context mContext) {
		if (null == dBManager) {
			dBManager = new DBManager(mContext);
		}
		return dBManager;
	}

	/**
	 * �ر����ݿ� ע��:������ɹ�����һ���Բ������ʱ���ٹر�
	 */
	public synchronized void closeDatabase(SQLiteDatabase dataBase, Cursor cursor) {
		if (null != dataBase) {
			dataBase.close();
		}
		if (null != cursor) {
			cursor.close();
		}
	}

	/**
	 * �����ݿ� ע:SQLiteDatabase��Դһ�����ر�,�õײ�����²���һ���µ�SQLiteDatabase
	 */
	public synchronized SQLiteDatabase openDatabase() {
		return getDatabaseHelper().getWritableDatabase();
	}

	/**
	 * ��ȡDataBaseHelper
	 * 
	 * @return
	 */
	public synchronized CustomHelper getDatabaseHelper() {
		return new CustomHelper(mContext, this.databaseName, null,
				this.version);
	}

}
