package com.portalshop.model;

public class Kategori {
	private String idKategori,namaKategori;

	public Kategori(String idKategori, String namaKategori) {
		super();
		this.idKategori = idKategori;
		this.namaKategori = namaKategori;
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
	
}
