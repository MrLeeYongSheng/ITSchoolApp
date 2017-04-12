package com.lys.itschoolapp;

import com.lys.itschoolapp.dao.UserDao;
import com.lys.itschoolapp.domain.User;
import com.lys.itschoolapp.utils.MD5Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText et_account;
	private EditText et_password;
	private Button btn_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		initViewComponent();

		btn_register.setOnClickListener(mOnClickListener);
	}
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String account = et_account.getText().toString().trim();
			String password = et_password.getText().toString().trim();
			if(TextUtils.isEmpty(account) && TextUtils.isEmpty(password)) {
				Toast.makeText(RegisterActivity.this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
			} else {
				User user = new User();
				user.setAccount(account);
				user.setPassword(MD5Utils.md5(password));
				UserDao udao = new UserDao(RegisterActivity.this);
				boolean existUser = udao.findByAccount(account);
				if(!existUser) {
					udao.add(user);
					
					Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(RegisterActivity.this, "账号已经存在,请使用其他账号..", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	private void initViewComponent() {
		et_account = (EditText) findViewById(R.id.register_et_account);
		et_password = (EditText) findViewById(R.id.register_et_password);
		btn_register = (Button) findViewById(R.id.register_btn_reg);
	}
}
