package com.lys.itschoolapp;

import com.lys.itschoolapp.dao.UserDao;
import com.lys.itschoolapp.domain.User;
import com.lys.itschoolapp.utils.MD5Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText et_login_account;
	private EditText et_login_password;
	private Button btn_login;
	private Button btn_exit;
	private CheckBox cb_remember;
	private TextView tv_register;
	private SharedPreferences mSharedpre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initViewComponent();
		
		if(mSharedpre == null) {
			mSharedpre = getSharedPreferences("userData", Context.MODE_PRIVATE);
		}
		if(mSharedpre.getBoolean("checked", false)) {
			et_login_account.setText(mSharedpre.getString("account", ""));
			et_login_password.setText(mSharedpre.getString("password", ""));
			cb_remember.setChecked(true);
		}
		
		tv_register.setOnClickListener(mRegisterListener);
		btn_exit.setOnClickListener(mExitOnClickListener);
		btn_login.setOnClickListener(mLoginOnClickListener);
		
	}
	private OnClickListener mLoginOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			String account = et_login_account.getText().toString().trim();
			String password = et_login_password.getText().toString().trim();
			if(TextUtils.isEmpty(account) && TextUtils.isEmpty(password)) {
				Toast.makeText(LoginActivity.this, "账号或者密码不能为空..", Toast.LENGTH_SHORT).show();
			} else {
				User user = new User();
				user.setAccount(account);
				user.setPassword(MD5Utils.md5(password));
				UserDao udao = new UserDao(LoginActivity.this);
				boolean exist = udao.find(user);
				if(exist) {
					boolean checked = cb_remember.isChecked();
					if(mSharedpre == null) {
						mSharedpre = getSharedPreferences("userData", Context.MODE_PRIVATE);
					}
					Editor editor = mSharedpre.edit();
					if(checked) {
						editor.putString("account", account);
						editor.putString("password", password);
					}
					editor.putBoolean("checked", checked);
					editor.commit();
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(LoginActivity.this, "账号或者密码错误..", Toast.LENGTH_SHORT).show();
				}
			}
			
		}
	};
	
	private OnClickListener mExitOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			System.exit(0);
		}
	};
	
	private OnClickListener mRegisterListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(registerIntent);
		}
	};
	
	private void initViewComponent (){
		et_login_account = (EditText) findViewById(R.id.et_login_account);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		cb_remember = (CheckBox) findViewById(R.id.cb_remember);
		tv_register = (TextView) findViewById(R.id.tv_register);
	}
}
