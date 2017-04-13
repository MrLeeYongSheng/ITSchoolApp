package com.lys.itschoolapp;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lys.itschoolapp.utils.StreamUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {

	private EditText et_city;
	private ListView lv;
	private static final String PATH = "http://wthrcdn.etouch.cn/weather_mini?city=";
	private static final int SUCCESS = 0;
	private static final int STATECODE_ERROR = 1;
	private static final int CONNECTION_ERROR = 2;
	private static final int INVALID_CITY = 3;
	private String urlPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		lv = (ListView) findViewById(R.id.lv_weather);
		et_city = (EditText) findViewById(R.id.et_city);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS: {
				JSONArray jsonArray = (JSONArray) msg.obj;
				listForecast(jsonArray);
			}
				break;

			case STATECODE_ERROR:
				Toast.makeText(WeatherActivity.this, "返回的状态码错误",
						Toast.LENGTH_SHORT).show();
				break;

			case CONNECTION_ERROR:
				Toast.makeText(WeatherActivity.this, "连接错误", Toast.LENGTH_SHORT)
						.show();
				break;

			case INVALID_CITY:
				Toast.makeText(WeatherActivity.this, "城市错误", Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				break;
			}
		}
	};

	public void gotoFind(View v) {
		String cityName = et_city.getText().toString().trim();
		if (TextUtils.isEmpty(cityName)) {
			Toast.makeText(WeatherActivity.this, "请输入正确的城市名",
					Toast.LENGTH_SHORT).show();
			return;
		}

		try {
			urlPath = PATH + URLEncoder.encode(cityName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				try {
					URL url = new URL(urlPath);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream is = conn.getInputStream();
						String data = StreamUtils.decode(is);
						JSONObject jsonObj = new JSONObject(data);
						String desc = jsonObj.getString("desc");
						if ("OK".equalsIgnoreCase(desc)) {
							JSONObject dataObj = jsonObj.getJSONObject("data");
							JSONArray jsonArray = dataObj
									.getJSONArray("forecast");
							Message msg = Message.obtain();
							msg.obj = jsonArray;
							msg.what = SUCCESS;
							handler.sendMessage(msg);
						} else {
							Message msg = Message.obtain();
							msg.what = INVALID_CITY;
							handler.sendMessage(msg);
						}
					} else {
						Message msg = Message.obtain();
						msg.what = STATECODE_ERROR;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = CONNECTION_ERROR;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	private void listForecast(JSONArray jsonArray) {
		lv.setAdapter(new MyAdapter(jsonArray));
	}

	private class MyAdapter extends BaseAdapter {

		private JSONArray jsonArray;

		public MyAdapter() {
			super();
		}

		public MyAdapter(JSONArray jsonArray) {
			this.jsonArray = jsonArray;
		}

		@Override
		public int getCount() {
			return jsonArray.length();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = View.inflate(WeatherActivity.this, R.layout.item_weather, null);
			TextView item_tv = (TextView) v.findViewById(R.id.item_weather_tv);
			String weatherMessage = null;
			String weatherData = null;
			try {
				weatherMessage = jsonArray.getString(position);
				JSONObject jsonObject = jsonArray.getJSONObject(position);
				String fengxiang = jsonObject.getString("fengxiang");
				String fengli = jsonObject.getString("fengli");
				String high = jsonObject.getString("high");
				String type = jsonObject.getString("type");
				String low = jsonObject.getString("low");
				String date = jsonObject.getString("date");
				
				weatherData = "日期:" + date + " 温度为:" + high + "~" + low + " 天气类型:" + type + " 风向:" + fengxiang + " 风力:" + fengli;
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(WeatherActivity.this, "解析显示的数据错误",
						Toast.LENGTH_SHORT).show();
			}
			item_tv.setText(weatherData);
			return item_tv;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
