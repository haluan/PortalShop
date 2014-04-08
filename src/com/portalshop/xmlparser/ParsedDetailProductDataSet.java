package com.portalshop.xmlparser;

import java.util.ArrayList;

public class ParsedDetailProductDataSet {
	private String url;
	private ArrayList<String> urlList=null;
	private String extractedString = null;
    private int extractedInt = 0;
    public ParsedDetailProductDataSet(){
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
	public void adduUrl(){
		urlList.add(url);
	}
	public void setUrl(String url){
		this.url=constant.IMG_URL+url;
	}
	public ArrayList<String> getList(){
		return urlList;
	}
}
