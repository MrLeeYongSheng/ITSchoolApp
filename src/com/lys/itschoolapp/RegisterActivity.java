package com.lys.itschoolapp;

import com.lys.itschoolapp.dao.MySQLiteOpenHelper;

import android.app.Activity;
import android.os.Bundle;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
		helper.getReadableDatabase();
	}
}
