package com.lys.itschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private EditText login_account;
	private EditText login_password;
	private Button btn_login;
	private Button btn_exit;
	private CheckBox cb_remember;
	private TextView tv_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initViewComponent();
		
		tv_register.setOnClickListener(mRegisterListener);
		
	}
	
	private OnClickListener mRegisterListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(registerIntent);
		}
	};
	
	private void initViewComponent (){
		login_account = (EditText) findViewById(R.id.et_login_account);
		login_password = (EditText) findViewById(R.id.et_login_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		cb_remember = (CheckBox) findViewById(R.id.cb_remember);
		tv_register = (TextView) findViewById(R.id.tv_register);
	}
}
