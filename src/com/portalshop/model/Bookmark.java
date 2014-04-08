package com.portalshop.model;

public class Bookmark {
	
	//private variables
	String _id_barang;
	String _nama_barang;
	String _outlet;
	
	// Empty constructor
	public Bookmark(){
		
	}
	// constructor
	public Bookmark(String _id_barang, String _nama_barang, String _outlet){
		this._id_barang = _id_barang;
		this._nama_barang = _nama_barang;
		this._outlet = _outlet;
	}
	
	// constructor
	public Bookmark(String _nama_barang, String _outlet){
		this._nama_barang = _nama_barang;
		this._outlet = _outlet;
	}
	// getting _id_barang
	public String get_id_barang(){
		return this._id_barang;
	}
	
	// setting _id_barang
	public void set_id_barang(String _id_barang){
		this._id_barang = _id_barang;
	}
	
	// getting _nama_barang
	public String get_nama_barang(){
		return this._nama_barang;
	}
	
	// setting _nama_barang
	public void set_nama_barang(String _nama_barang){
		this._nama_barang = _nama_barang;
	}
	
	// getting phone number
	public String getOutlet(){
		return this._outlet;
	}
	
	// setting phone number
	public void setOutlet(String phone_number){
		this._outlet = phone_number;
	}
}
