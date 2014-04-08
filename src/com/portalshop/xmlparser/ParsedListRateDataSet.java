package com.portalshop.xmlparser;

import java.util.ArrayList;

import com.portalshop.model.Rate;

public class ParsedListRateDataSet {
	String id,username,rate,titile,comment,date;
	Rate rating;
	ArrayList<Rate> list=new ArrayList<Rate>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<Rate> getList() {
		return list;
	}
	public void setList(ArrayList<Rate> list) {
		this.list = list;
	}
	public void addRate(){
		rating=new Rate(id, username, rate, titile, comment, date);
		list.add(rating);
	}
}
