package com.mzhou.merchant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class CustomHelper extends SDCardSQLiteOpenHelper {

	public CustomHelper(Context context, String dbName,CursorFactory factory,int dbVersion) {
		super(context, dbName, factory, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//ad table
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbAd ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "id VARCHAR(100),category VARCHAR(100),pic TEXT,url TEXT,type VARCHAR(10),order_sort VARCHAR(10),position VARCHAR(100),name VARCHAR(250))");
	 
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbNews ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "title VARCHAR(100),ctime VARCHAR(100),pic TEXT,url TEXT,source VARCHAR(100),id VARCHAR(100))");
		 //qiugou zhangshang table
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbAttach ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "content TEXT,email VARCHAR(100),contact VARCHAR(100),ctime VARCHAR(100),category VARCHAR(100))");
 
		//job table
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbJob ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "id VARCHAR(100),position VARCHAR(100),content TEXT,contact VARCHAR(100),phone VARCHAR(100),ctime VARCHAR(100))");
		//activity table

		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbActivity ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "id VARCHAR(100),title VARCHAR(250),content TEXT,address VARCHAR(250),lasttime VARCHAR(100),ctime VARCHAR(100)," +
				"applytime VARCHAR(100),contact VARCHAR(100),phone VARCHAR(100),uid VARCHAR(100),is_en VARCHAR(100))");
		//product table
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbProduct ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "category VARCHAR(100),id VARCHAR(100),ctime VARCHAR(250),pic TEXT,classid VARCHAR(100),brand VARCHAR(100),is_en VARCHAR(10),is_show VARCHAR(10),order_sort VARCHAR(4))");
		//login table
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbLogin ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"status varchar(1),needlogin  varchar(1),lastlogin varchar(1),username varchar(250),password varchar(250), usertype varchar(1),isremeber varchar(1),isloginself varchar(1)," +
				"isbinder varchar(1),qq varchar(250))");
		
		db.execSQL("CREATE TABLE  IF NOT EXISTS  tbUserInfo ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
				"");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

}
