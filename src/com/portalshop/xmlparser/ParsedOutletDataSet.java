package com.portalshop.xmlparser;

import java.util.ArrayList;

import com.portalshop.model.Kategori;

public class ParsedOutletDataSet {
	private String username;
	private ArrayList<String> list;
	public ParsedOutletDataSet() {
		super();
		list=new ArrayList<String>();
	}
	public void addString(){
		list.add(username);
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public ArrayList<String> getList(){
		return list;
	}
}
