package com.itheima.rrsnew_v1.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.itheima.rrsnew_v1.domain.New;
import com.itheima.rrsnew_v1.service.utils.StreamUtils;

public class NewsService {

	private static String path = "http://192.168.254.1:8080/news.xml";

	public static List<New> getAllNews(){

		final List<New> news = new ArrayList<New>();
		
		new Thread(){
			public void run() {
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					int code = conn.getResponseCode();
					if(code != 200) {
						System.err.println("×´Ì¬Âë·Ç200");
						return ;
					}
					InputStream is = conn.getInputStream();
					/*String data = StreamUtils.decode2String(is);
					System.out.println(data);*/
					New newBean = null;
					XmlPullParser parser = Xml.newPullParser();
					parser.setInput(is, "UTF-8");
					int eventType = parser.getEventType();
					while(eventType != XmlPullParser.END_DOCUMENT) {
						if(eventType == XmlPullParser.START_TAG) {
							if("item".equals(parser.getName())) {
								newBean = new New();
							} else if("title".equals(parser.getName())) {
								newBean.setTitle(parser.nextText());
							} else if("description".equals(parser.getName())) {
								newBean.setDescription(parser.nextText());
							} else if("image".equals(parser.getName())) {
								newBean.setImage(parser.nextText());
							} else if("type".equals(parser.getName())) {
								newBean.setType(parser.nextText());
							} else if("comment".equals(parser.getName())) {
								newBean.setComment(parser.nextText());
							}
						} else if(eventType == XmlPullParser.END_TAG) {
							if("item".equals(parser.getName())) {
								news.add(newBean);
							}
						}
						eventType = parser.next();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

		return news;
	}
}
