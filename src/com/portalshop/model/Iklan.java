package com.portalshop.model;

import com.portalshop.xmlparser.constant;

public class Iklan {
	private String id_barang;
	private String pict;
	private String status;
	public String getId_barang() {
		return id_barang;
	}
	public void setId_barang(String idBarang) {
		id_barang = idBarang;
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
	public Iklan(String idBarang, String pict, String status) {
		super();
		id_barang = idBarang;
		this.pict = constant.IMG_URL+pict;
		this.status = status;
	}
	
}
