package com.lys.itschoolapp;

import com.itheima.rrsnew_v1.NewActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class MainActivity extends Activity {

	private ImageView iv_new;
	private ImageView iv_course;
	private ImageView iv_weather;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        iv_new = (ImageView) findViewById(R.id.iv_new_btn);
        iv_course = (ImageView) findViewById(R.id.iv_course_btn);
        iv_weather = (ImageView) findViewById(R.id.iv_weather_btn);
		iv_new.setOnClickListener(mNewOnClickListener);
		iv_course.setOnClickListener(mCourseOnClickListener);
		iv_weather.setOnClickListener(mWeatherOnClickListener);
    }

   private OnClickListener mWeatherOnClickListener = new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
		startActivity(intent);
	}
};
    
   private OnClickListener mCourseOnClickListener = new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, CourseActivity.class);
		startActivity(intent);
		
	}
};
   private OnClickListener mNewOnClickListener = new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(MainActivity.this, NewActivity.class);
		startActivity(intent);
	}
};
}
