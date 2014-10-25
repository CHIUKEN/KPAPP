package com.app.kp.utils;

public class Configuration {

	public static String KP_API_SERVER = "http://api.kptaipei.tw/v1/";

	public static String THUMBNAILS_SMALL = "small";
	public static String THUMBNAILS_SMALL_SQUARE = "small_square";
	public static String THUMBNAILS_LARGE_SQUARE = "large_square";
	public static String THUMBNAILS_THUMBNAIL = "thumbnail";
	public static String THUMBNAILS_MEDIUM = "medium";
	public static String THUMBNAILS_LARGE = "large";
	public static String THUMBNAILS_ORIGINAL = "original";
	// Gridview image padding
	public static int GRID_PADDING = 4;// in dp
	// datetime
	public final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final int TYPE_HEADER = 0;
	/**
	 * ¤å³¹
	 */
	public static final int TYPE_ARTICLE = 1;
	/**
	 * ·Ó¤ù
	 */
	public static final int TYPE_PHOTO = 2;
}
