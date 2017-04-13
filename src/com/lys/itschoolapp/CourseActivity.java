package com.lys.itschoolapp;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lys.itschoolapp.courseService.CourseService;
import com.lys.itschoolapp.domain.Course;

public class CourseActivity extends Activity {

	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		
		
		lv = (ListView) findViewById(R.id.lv_course);


		 AsyncHttpClient client = new AsyncHttpClient();
		 client.get("http://192.168.15.1:8080/course.xml", new AsyncHttpResponseHandler() {
		     @Override
		     public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		          if(statusCode == 200) {
		        	  List<Course> courses = CourseService.getAllCourses(new ByteArrayInputStream(responseBody));
		        	  lv.setAdapter( new ArrayAdapter<Course>(CourseActivity.this, R.layout.item_course, courses));
		          }
		     }
		     @Override
		     public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
		 error)
		 {
		          error.printStackTrace(System.out);
		     }
		 });

	}
}
