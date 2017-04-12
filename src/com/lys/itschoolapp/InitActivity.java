package com.lys.itschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		
		new Thread(){
			public void run() {
				try {
					sleep(2000);
					Intent intent = new Intent(InitActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
