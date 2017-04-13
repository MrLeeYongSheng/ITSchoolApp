package com.lys.itschoolapp.domain;

public class Course {

	private String date;
	private String course;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return date + " : " + course;
	}
	
	
}
