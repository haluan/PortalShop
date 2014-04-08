package com.portalshop.model;

import com.portalshop.xmlparser.constant;

public class Barang {
	private String id,nama,date,diskon,deskripsi="",stok,pict,outlet,ketgori,namaOutlet,lantai,lokasi,email,telp;
	private double harga;
	private float jml_rate;
	private float rater; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNamaOutlet() {
		return namaOutlet;
	}

	public void setNamaOutlet(String namaOutlet) {
		this.namaOutlet = namaOutlet;
	}

	public String getNama() {
		return nama;
	}

	public Barang(String id, String nama, String date, String diskon,
			String deskripsi, String stok, String pict, String outlet,
			String ketgori, double harga, float jmlRate, float rater,String namaOutlet,String lantai,String lokasi,String email,String telp) {
		super();
		this.id = id;
		this.nama = nama;
		this.date = date;
		this.diskon = diskon;
		this.deskripsi = deskripsi;
		this.stok = stok;
		this.pict = constant.IMG_URL+pict;
		this.outlet = outlet;
		this.ketgori = ketgori;
		this.harga = harga;
		jml_rate = jmlRate;
		this.rater = rater;
		this.namaOutlet=namaOutlet;
		this.lantai=lantai;
		this.lokasi=lokasi;
		this.email=email;
		this.telp=telp;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDiskon() {
		return diskon;
	}

	public void setDiskon(String diskon) {
		this.diskon = diskon;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public String getStok() {
		return stok;
	}

	public void setStok(String stok) {
		this.stok = stok;
	}

	public String getPict() {
		return pict;
	}

	public void setPict(String pict) {
		this.pict = pict;
	}

	public String getOutlet() {
		return outlet;
	}

	public double getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = Double.parseDouble(harga);
	}

	public float getJml_rate() {
		return jml_rate;
	}

	public void setJml_rate(String jmlRate) {
		jml_rate = Float.parseFloat(jmlRate);
	}

	public float getRater() {
		return rater;
	}

	public void setRater(String rater) {
		this.rater = Integer.parseInt(rater);
	}

	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	public String getKetgori() {
		return ketgori;
	}

	public void setKetgori(String ketgori) {
		this.ketgori = ketgori;
	}

	public String getLantai() {
		return lantai;
	}

	public void setLantai(String lantai) {
		this.lantai = lantai;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}
}
