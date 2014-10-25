package com.app.kp;

import java.util.HashMap;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.app.kp.AppController.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


public class AppController extends Application {
	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	// GA V4 可讓開發者管理多個 Tracker
	
	private static AppController mInstance;
	// The following line should be changed to include the correct property id.
	private static final String PROPERTY_ID = "UA-55740021-1";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
						// roll-up tracking.
		ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
							// company.
	}
	
	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	public synchronized Tracker getTracker(TrackerName trackerId) {
	    if (!mTrackers.containsKey(trackerId)) {

	        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
	        Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
	                : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(
	                        R.xml.global_tracker)
	                        : analytics.newTracker(R.xml.ecommerce_tracker);
	        mTrackers.put(trackerId, t);

	    }
	    return mTrackers.get(trackerId);
	}
	public AppController()
	{
		super();
//		Tracker tracker =
//				 GoogleAnalytics.getInstance(this).getTracker(R.xml.global_tracker);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

//	synchronized Tracker getTracker(TrackerName trackerId) {
//	    if (!mTrackers.containsKey(trackerId)) {
//
//	        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//	        Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
//	                : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(
//	                        R.xml.global_tracker)
//	                        : analytics.newTracker(R.xml.ecommerce_tracker);
//	        mTrackers.put(trackerId, t);
//
//	    }
//	    return mTrackers.get(trackerId);
//	}

	public static synchronized AppController getInstance() {

		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	
}
