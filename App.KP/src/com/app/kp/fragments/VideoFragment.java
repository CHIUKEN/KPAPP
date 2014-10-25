package com.app.kp.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.kp.AppController;
import com.app.kp.FullPhotoActivity;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.app.kp.VideoDetailActivity;
import com.app.kp.adapter.ImageAdapter;
import com.app.kp.adapter.VideoAdapter;
import com.app.kp.model.Thumbnail;
import com.app.kp.model.Video;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoFragment extends Fragment {
	private static final String TAG = VideoFragment.class.getSimpleName();
	private final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private ListView lv_video;
	private String url = "http://api.kptaipei.tw/v1/videos/?accessToken=kp53f5989f258a84.59605552";
	private VideoAdapter adapter;

	private String[] thumArray = new String[] { "default", "medium", "high", "standard" };
	private ProgressDialog pDialog;
	private FadingActionBarHelper mFadingHelper;

	private AutoScrollViewPager viewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		AppController con = (AppController) getActivity().getApplication();
		Tracker t = con.getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(VideoFragment.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		super.onCreate(savedInstanceState);
		NavigationDrawerActivity.exitFlag = false;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		int actionBarBg = R.drawable.ab_background;

		mFadingHelper = new FadingActionBarHelper().actionBarBackground(actionBarBg).headerLayout(R.layout.home_header)
				.contentLayout(R.layout.fragment_video).lightActionBar(true);
		mFadingHelper.initActionBar(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		View rootView = mFadingHelper.createView(inflater);

		viewPager = (AutoScrollViewPager) rootView.findViewById(R.id.view_pager);
		ArrayList<String> imageList = new ArrayList<String>();
		imageList.add("https://farm4.staticflickr.com/3842/15398765092_d9d1da1e94_b.jpg");
		imageList.add("https://farm3.staticflickr.com/2941/15201547327_758d4409ea_b.jpg");
		imageList.add("https://farm4.staticflickr.com/3863/15170070878_effaa1458e_b.jpg");
		imageList.add("https://farm3.staticflickr.com/2942/15152291240_d91467cfe8_b.jpg");
		imageList.add("https://farm6.staticflickr.com/5579/15119499687_6d9e0e6896_b.jpg");
		imageList.add("https://farm6.staticflickr.com/5556/15305301232_6b694b5a6b_b.jpg");
		viewPager.setAdapter(new ImageAdapter(getActivity(), imageList));
		viewPager.setInterval(5000);// 設定滾動時間
		viewPager.setBorderAnimation(false);// 從最後一張到第一張是否要有動畫
		viewPager.startAutoScroll();// 啟動自動滾動
		viewPager.setSlideBorderMode(2);

		pDialog = new ProgressDialog(getActivity());
		final ArrayList<Video> videoList = new ArrayList<Video>();

		lv_video = (ListView) rootView.findViewById(android.R.id.list);
		pDialog = new ProgressDialog(getActivity());
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@SuppressLint("SimpleDateFormat")
					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "List of photos json reponse: " + response.toString());
						try {
							// Parsing the json response

							JSONArray array = response.getJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								JSONObject arrayObject = array.getJSONObject(i);
								Video video = new Video();
								video.setId(arrayObject.getString("id"));
								video.setTitle(arrayObject.getString("title"));
								video.setDescription("description");
								video.setLink(arrayObject.getString("link"));
								video.setPublishedAt(arrayObject.getString("publishedAt"));
								// 將json字串格式化
								SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
								Date d = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.CHINESE).parse(arrayObject
										.getString("publishedAt"));
								String CurrentDate = df1.format(d.getTime());
								video.setFormatPublisheAt(CurrentDate);

								JSONObject thumObject = arrayObject.getJSONObject("thumbnails");
								Map<String, Thumbnail> thumMap = new HashMap<String, Thumbnail>();
								for (int j = 0; j < thumArray.length; j++) {

									JSONObject def = thumObject.getJSONObject(thumArray[j]);
									Thumbnail thumbnail = new Thumbnail();
									thumbnail.setUrl(def.getString("url"));
									thumbnail.setHeight(def.getInt("height"));
									thumbnail.setWidth(def.getInt("width"));
									if (!def.has(thumArray[j])) {
										thumMap.put(thumArray[j], thumbnail);
									}
								}
								video.setThumbnails(thumMap);
								videoList.add(video);
							}
							// looping through each photo and adding it to list
							// data set

							Log.d(TAG, "Photo: " + url);
							pDialog.dismiss();
							// Notify list adapter about dataset changes. So
							// that it renders grid again
							adapter.notifyDataSetChanged();

						} catch (JSONException e) {
							e.printStackTrace();

						} catch (ParseException e) {
							// TODO 自動產生的 catch 區塊
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error: " + error.getMessage());
						// unable to fetch wallpapers
						// either google username is wrong or
						// devices doesn't have internet connection
						pDialog.dismiss();
						Toast.makeText(getActivity(), "網路讀取錯誤", Toast.LENGTH_SHORT).show();
					}
				});
		adapter = new VideoAdapter(getActivity(), videoList);
		lv_video.setAdapter(adapter);
		// Remove the url from cache
		AppController.getInstance().getRequestQueue().getCache().remove(url);
		// Disable the cache for this url, so that it always fetches updated
		// json
		jsonObjReq.setShouldCache(false);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
		lv_video.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, final int position, long arg3) {
				pDialog.setMessage("Loading");
				pDialog.show();
				final ArrayList<Video> videodetail = new ArrayList<Video>();
				url = "http://api.kptaipei.tw/v1/videos/" + videoList.get(position - 1).getId();

				// TODO 自動產生的方法 Stub
				JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url, null,
						new Response.Listener<JSONObject>() {
							@SuppressLint("SimpleDateFormat")
							@Override
							public void onResponse(JSONObject response) {
								Log.d(TAG, "List of photos json reponse: " + response.toString());
								try {
									JSONArray array = response.getJSONArray("data");
									for (int i = 0; i < array.length(); i++) {
										JSONObject arrayObject = array.getJSONObject(i);
										Video video = new Video();
										video.setId(arrayObject.getString("id"));
										video.setTitle(arrayObject.getString("title"));
										video.setDescription(arrayObject.getString("description"));
										video.setLink(arrayObject.getString("link"));
										// 將json字串格式化
										SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
										Date d = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.CHINESE)
												.parse(arrayObject.getString("publishedAt"));
										String CurrentDate = df1.format(d.getTime());
										video.setFormatPublisheAt(CurrentDate);

										JSONObject thumObject = arrayObject.getJSONObject("thumbnails");
										Map<String, Thumbnail> thumMap = new HashMap<String, Thumbnail>();
										for (int j = 0; j < thumArray.length; j++) {

											JSONObject def = thumObject.getJSONObject(thumArray[j]);
											Thumbnail thumbnail = new Thumbnail();
											thumbnail.setUrl(def.getString("url"));
											thumbnail.setHeight(def.getInt("height"));
											thumbnail.setWidth(def.getInt("width"));
											thumMap.put(thumArray[j], thumbnail);
										}
										video.setThumbnails(thumMap);
										videodetail.add(video);
									}
									hidePDialog();
									Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
									Bundle bundle = new Bundle();
									bundle.putSerializable("video", videodetail);
									intent.putExtra("actionTitle", videoList.get(position - 1).getTitle());
									intent.putExtras(bundle);
									startActivity(intent);
								} catch (JSONException e) {
									hidePDialog();
									e.printStackTrace();
								} catch (ParseException e) {
									hidePDialog();
									// TODO 自動產生的 catch 區塊
									e.printStackTrace();
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								Log.e(TAG, "Error: " + error.getMessage());
								hidePDialog();

							}
						});
				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(jsonObjReq);

			}
		});

		return rootView;
	}

	@Override
	public void onPause() {
		super.onPause();
		// stop auto scroll when onPause
		viewPager.stopAutoScroll();// 停止自動滾動
	}

	@Override
	public void onResume() {
		super.onResume();
		// start auto scroll when onResume
		viewPager.startAutoScroll();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();

		}
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}
}
