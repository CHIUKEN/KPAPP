package com.app.kp.model;

import java.io.Serializable;

public class Article implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6752582962271153519L;
	private int id;
	private String author;
	private String title;
	private String post_date;
	private String last_modify;
	private String url;
	private String content;
	private String plain_content;
	private String thumbnail;
	private String format_Last_modify;
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public String getLast_modify() {
		return last_modify;
	}

	public void setLast_modify(String last_modify) {
		this.last_modify = last_modify;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlain_content() {
		return plain_content;
	}

	public void setPlain_content(String plain_content) {
		this.plain_content = plain_content;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setFormatPublisheAt(String format_Last_modify) {
		this.format_Last_modify = format_Last_modify;
	}

	public String getFormatPublisheAt() {
		// TODO 自動產生的方法 Stub
		return format_Last_modify;
	}
}
