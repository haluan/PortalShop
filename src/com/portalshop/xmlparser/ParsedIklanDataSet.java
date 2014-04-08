package com.portalshop.xmlparser;

import java.util.ArrayList;

import com.portalshop.model.Iklan;

public class ParsedIklanDataSet {
	private String idBarang;
	private String pict;
	private String status;
	private ArrayList<Iklan> list;
	public ParsedIklanDataSet(){
		list=new ArrayList<Iklan>();
	}
	public void addIklan(){
		Iklan i=new Iklan(idBarang, pict, status);
		list.add(i);
	}
	public String getIdBarang() {
		return idBarang;
	}
	public void setIdBarang(String idBarang) {
		this.idBarang = idBarang;
	}
	public String getPict() {
		return pict;
	}
	public void setPict(String pict) {
		this.pict = pict;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<Iklan> getList() {
		return list;
	}
	public void setList(ArrayList<Iklan> list) {
		this.list = list;
	}
}
