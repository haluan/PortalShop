package com.portalshop.model;

public class Rate {
	private String id,username,titile,comment,date;
	private float rate;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public Rate(String id, String username, String rate, String titile,
			String comment, String date) {
		super();
		this.id = id;
		this.username = username;
		this.rate = Float.parseFloat(rate);
		this.titile = titile;
		this.comment = comment;
		this.date = date;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getTitile() {
		return titile;
	}

	public void setTitile(String titile) {
		this.titile = titile;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
