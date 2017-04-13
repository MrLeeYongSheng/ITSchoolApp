package com.lys.itschoolapp.courseService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.lys.itschoolapp.domain.Course;

public class CourseService {

	public static List<Course> getAllCourses(InputStream is) {

		final List<Course> courses = new ArrayList<Course>();

		try {
			Course course = null;
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if ("item".equals(parser.getName())) {
						course = new Course();
					} else if ("date".equals(parser.getName())) {
						course.setDate(parser.nextText());
					} else if ("when".equals(parser.getName())) {
						course.setCourse(parser.nextText());
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if ("item".equals(parser.getName())) {
						courses.add(course);
					}
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return courses;
	}
}
