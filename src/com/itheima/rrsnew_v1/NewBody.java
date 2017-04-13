package com.itheima.rrsnew_v1;

import com.loopj.android.image.SmartImageView;
import com.lys.itschoolapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewBody extends Activity {

	private SmartImageView new_iv;
	private TextView new_tv_title;
	private TextView new_tv_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_body);

		new_tv_title = (TextView) findViewById(R.id.new_tv_title);
		new_iv = (SmartImageView) findViewById(R.id.new_iv);
		new_tv_desc = (TextView) findViewById(R.id.new_tv_desc);
		
		Intent intent = getIntent();
		
		new_tv_title.setText(intent.getStringExtra("title"));
		new_iv.setImageUrl(intent.getStringExtra("image"));
		new_tv_desc.setText(intent.getStringExtra("desception"));
	}
}
