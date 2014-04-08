package com.portalshop.xmlparser;

import java.util.ArrayList;

import com.portalshop.model.Kategori;

public class ParsedKategoriDataSet {
	private String idKategori,namaKategori;
	private ArrayList<Kategori> list;
	public ParsedKategoriDataSet() {
		super();
		list=new ArrayList<Kategori>();
	}
	public String getIdKategori() {
		return idKategori;
	}
	public void setIdKategori(String idKategori) {
		this.idKategori = idKategori;
	}
	public String getNamaKategori() {
		return namaKategori;
	}
	public void setNamaKategori(String namaKategori) {
		this.namaKategori = namaKategori;
	}
	public ArrayList<Kategori> getList() {
		return list;
	}
	public void setList(ArrayList<Kategori> list) {
		this.list = list;
	}
	public void addKategori(){
		list.add(new Kategori(idKategori, namaKategori));
	}
}
