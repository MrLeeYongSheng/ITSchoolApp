package com.lys.itschoolapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lys.itschoolapp.domain.User;

public class UserDao {
	
	private Context context;
	
	public UserDao(Context context) {
		this.context = context;
		mHelper = new MySQLiteOpenHelper(context);
	}
	


	private MySQLiteOpenHelper mHelper;
	
	public void add(User user) {
	
		SQLiteDatabase database = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("account", user.getAccount());
		values.put("password", user.getPassword());
		database.insert("users", "account=?,password=?", values);
	}

	public boolean findByAccount(String account) {
		
		SQLiteDatabase database = mHelper.getReadableDatabase();
		Cursor cursor = database.query("users", new String[]{"account"}, "account=?", new String[]{account}, null, null, null);
		if(cursor.moveToNext()) {
			return true;
		}
		return false;
	}

	public boolean find(User user) {
		SQLiteDatabase database = mHelper.getReadableDatabase();
		Cursor cursor = database.query("users", new String[]{"account","password"}, "account=? and password=?", new String[]{user.getAccount(),user.getPassword()}, null, null, null);
		if(cursor.moveToNext()) {
			return true;
		}
		return false;
	}
}
