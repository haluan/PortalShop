package com.portalshop.xmlparser;

import java.util.ArrayList;

public class ParsedSearchDataSet {
	private String nama;
	private ArrayList<String> urlList=null;
	private String extractedString = null;
    private int extractedInt = 0;
    public ParsedSearchDataSet(){
    	urlList=new ArrayList<String>();
    }
	public String getExtractedString() {
		return extractedString;
	}
	public void setExtractedString(String extractedString) {
		this.extractedString = extractedString;
	}
	public int getExtractedInt() {
		return extractedInt;
	}
	public void setExtractedInt(int extractedInt) {
		this.extractedInt = extractedInt;
	}
	public void adduNama(){
		urlList.add(nama);
	}
	public void setUrl(String nama){
		this.nama=nama;
	}
	public ArrayList<String> getList(){
		return urlList;
	}
}
