package com.app.kp.model;

import java.io.Serializable;

public class Thumbnail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2420658374287971076L;
	private String url;
	private int width;
	private int height;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
