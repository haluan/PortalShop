package com.portalshop.xmlparser;

import java.util.ArrayList;

import com.portalshop.model.Barang;


public class ParsedListProductDataSet {
        private String extractedString = null;
        private int extractedInt = 0;
        private ArrayList<Barang> list=null;
        public String id,nama,date,diskon,deskripsi,stok,pict,outlet,ketgori,namaOutlet,lantai,lokasi,email,telp;
        public double harga;
    	public float jml_rate;
    	public float rater;
    	
        public void setRater(String rater){
        	this.rater=Float.parseFloat(rater);
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
		public ArrayList<Barang> getList() {
			if(list.size()==0){
				return null;
			}
			return list;
		}
		public void setList(ArrayList<Barang> list) {
			this.list = list;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNama() {
			return nama;
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
		public void setOutlet(String outlet) {
			this.outlet = outlet;
		}
		public String getKetgori() {
			return ketgori;
		}
		public void setKetgori(String ketgori) {
			this.ketgori = ketgori;
		}
		public ParsedListProductDataSet(){
        	list=new ArrayList<Barang>();
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
		
        public void setNamaOutlet(String namaOutlet) {
			this.namaOutlet = namaOutlet;
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
		public void addBook(){
        	Barang barang=new Barang(id, nama, date, diskon, deskripsi, stok, pict, outlet, ketgori,harga, jml_rate,rater,namaOutlet,lantai,lokasi,email,telp);
        	list.add(barang);
        }
        
}