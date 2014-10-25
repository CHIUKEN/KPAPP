package com.app.kp.model;

import java.util.Map;

public class News {
	private String id;
	private String title;
	private int type;
	private Map<String, String> thumbnails;
	private String parent;
	private String setPlain_content;

	public void setparent(String parent) {
		this.parent = parent;
	}

	public String getparent() {
		return parent;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getid() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public Map<String, String> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(Map<String, String> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public void setPlain_content(String setPlain_content) {
		// TODO 自動產生的方法 Stub
		this.setPlain_content = setPlain_content;
	}
	public String getPlain_content()
	{
		return setPlain_content;
	}
}
