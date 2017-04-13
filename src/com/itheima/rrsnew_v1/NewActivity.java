package com.itheima.rrsnew_v1;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.rrsnew_v1.domain.New;
import com.itheima.rrsnew_v1.service.NewsService;
import com.loopj.android.image.SmartImageView;
import com.lys.itschoolapp.R;

public class NewActivity extends Activity {

	private ListView lv;
	private SmartImageView item_iv;
	private TextView item_tv_title;
	private TextView item_tv_desc;
	private TextView item_tv_type;
	
	private List<New> news;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);

		news = NewsService.getAllNews();
		lv = (ListView) findViewById(R.id.lv);
		if (adapter == null) {
			adapter = new MyAdapter();
			 lv.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		lv.setOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			New bean = news.get(position);
			Intent intent = new Intent(NewActivity.this, NewBody.class);
			intent.putExtra("title", bean.getTitle());
			intent.putExtra("image", bean.getImage());
			intent.putExtra("desception", bean.getDescription());
			startActivity(intent);
		}
	};
	
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return news.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			New newBean = news.get(position);
			
			View v;
			if(convertView == null) {
				v = View.inflate(NewActivity.this, R.layout.item, null);
			} else {
				v = convertView;
			}
			item_iv = (SmartImageView) v.findViewById(R.id.item_iv);
			item_tv_title = (TextView) v.findViewById(R.id.item_tv_title);
			item_tv_desc = (TextView) v.findViewById(R.id.item_tv_desc);
			item_tv_type = (TextView) v.findViewById(R.id.item_tv_type);
			
			item_tv_title.setText(newBean.getTitle());
			item_tv_desc.setText(newBean.getDescription());
			String type = newBean.getType();
			if("1".equals(type)) {
				item_tv_type.setText("评论:" + newBean.getComment());
			} else if("2".equals(type)) {
				item_tv_type.setText("视频");
			} else if("3".equals(type)){
				item_tv_type.setText("现场直播");
			}
			
			item_iv.setImageUrl(newBean.getImage());
			
			return v;
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
