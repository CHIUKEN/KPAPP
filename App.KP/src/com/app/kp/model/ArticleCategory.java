package com.app.kp.model;

import java.io.Serializable;

public class ArticleCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3787928627307491029L;
	private String	name;
	private int		id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
