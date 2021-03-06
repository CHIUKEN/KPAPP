package com.app.kp.model;

import java.io.Serializable;
import java.util.Map;

public class Video implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2926460517997553163L;
	private String id;
	private String sys_id;
	private String title;
	private String description;
	private String publishedAt;
	private String formatpublishedAt;
	private String link;
	private Map<String, Thumbnail> thumbnails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getFormatPublisheAt() {
		return formatpublishedAt;
	}

	public void setFormatPublisheAt(String Format) {
		this.formatpublishedAt = Format;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Map<String, Thumbnail> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(Map<String, Thumbnail> thumbnails) {
		this.thumbnails = thumbnails;
	}

}
