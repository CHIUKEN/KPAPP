package com.app.kp.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;

import com.android.volley.toolbox.JsonObjectRequest;

import com.app.kp.AppController;
import com.app.kp.ArticleContentActivity;
import com.app.kp.FullPhotoActivity;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.app.kp.TestActivity;
import com.app.kp.adapter.ImageAdapter;
import com.app.kp.adapter.NewsAdapter;
import com.app.kp.model.Article;
import com.app.kp.model.ArticleCategory;
import com.app.kp.model.News;
import com.app.kp.utils.Configuration;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.commonsware.cwac.merge.MergeAdapter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	private FadingActionBarHelper mFadingHelper;
	private Bundle mArguments;

	public static final String ARG_IMAGE_RES = "image_source";
	public static final String ARG_ACTION_BG_RES = "image_action_bs_res";
	// Log tag
	private static final String TAG = TestActivity.class.getSimpleName();
	private ListView listview;
	private NewsAdapter adapter;
	private ArrayList<News> newsList = new ArrayList<News>();
	private int Num = 0;
	private ArrayList<ArticleCategory> articleCategories;

	private AutoScrollViewPager viewPager;
	private MergeAdapter mAdapter;
	private Handler handler = new Handler();
	private TextView txt_dt;
	private int num = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		AppController con = (AppController) getActivity().getApplication();
		Tracker t = con.getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(HomeFragment.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		super.onCreate(savedInstanceState);
		NavigationDrawerActivity.exitFlag = false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Date now = new Date();
		Date enddate = new Date("2014/11/29 00:00:00");
		long diff = enddate.getTime() - now.getTime();
		if (diff > 0) {
			// 設定定時要執行的方法
			handler.removeCallbacks(updateTimer);
			// 設定Delay的時間
			handler.postDelayed(updateTimer, 1000);
			num++;
		}
		View view = mFadingHelper.createView(inflater);

		viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
		ArrayList<String> imageList = new ArrayList<String>();
		imageList.add("https://farm6.staticflickr.com/5552/15026606389_6a7229f03a_b.jpg");
		imageList.add("https://farm3.staticflickr.com/2946/15281107798_b2d05369f1_b.jpg");
		imageList.add("https://farm4.staticflickr.com/3928/15421620956_0bdb36bd2c_b.jpg");
		imageList.add("https://farm4.staticflickr.com/3903/15142521829_b1dc9531b0_b.jpg");
		imageList.add("https://farm3.staticflickr.com/2948/15436529822_8628a136a2_b.jpg");
		imageList.add("https://farm3.staticflickr.com/2945/15214695828_899c2de953_b.jpg");
		viewPager.setAdapter(new ImageAdapter(getActivity(), imageList));
		viewPager.setInterval(5000);// 設定滾動時間
		viewPager.setBorderAnimation(false);// 從最後一張到第一張是否要有動畫
		viewPager.startAutoScroll();// 啟動自動滾動
		viewPager.setSlideBorderMode(2);

		News newsTitle = new News();
		newsTitle.setTitle(getResources().getString(R.string.home_subtitle1));
		newsTitle.setType(Configuration.TYPE_HEADER);
		newsList.add(newsTitle);
		for (int i = 0; i < articleCategories.size(); i++) {
			getRequestforUrl(articleCategories.get(i).getId(), articleCategories.get(i).getName(),
					Configuration.TYPE_ARTICLE);
		}

		listview = (ListView) view.findViewById(android.R.id.list);

		mAdapter = new MergeAdapter();
		View dtview = LayoutInflater.from(getActivity()).inflate(R.layout.datetime_item, null);
		txt_dt = (TextView) dtview.findViewById(R.id.txt_datetime);
		if (num == 2) {
			mAdapter.addView(dtview);
		}
		adapter = new NewsAdapter(getActivity(), newsList);
		mAdapter.addAdapter(adapter);

		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO 自動產生的方法 Stub
				News news = newsList.get(position - num);
				if (news.getType() == Configuration.TYPE_HEADER) {
					return;
				}
				if (news.getType() == Configuration.TYPE_ARTICLE) {
					Article article = new Article();
					article.setPlain_content(news.getPlain_content());
					article.setTitle(news.getTitle());
					Intent intent = new Intent(getActivity(), ArticleContentActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("article", article);
					intent.putExtras(bundle);
					startActivity(intent);
					return;
				}
				if (news.getType() == Configuration.TYPE_PHOTO) {
					Intent i = new Intent(getActivity(), FullPhotoActivity.class);
					i.putExtra("PhotoID", news.getid());
					i.putExtra("Title", news.getTitle());
					startActivity(i);
					return;
				}
			}
		});

		return view;
	}

	// 固定要執行的方法
	private Runnable updateTimer = new Runnable() {
		@SuppressLint("SimpleDateFormat")
		@SuppressWarnings("deprecation")
		public void run() {

			SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
			Date now = new Date();
			Date nowday = null;
			Date end = null;
			Calendar today = Calendar.getInstance();
			String date = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINESE).format(today.getTime());

			try {
				end = format.parse("2014/11/29 00:00:00");
				nowday = format.parse(date + " 00:00:00");
			} catch (ParseException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}

			long diff = end.getTime() - nowday.getTime();
			long diffdays = (diff / (1000 * 60 * 60 * 24)) - 1;// 日差;
			long diffHours = 24 - now.getHours() - 1;
			long diffMinutes = 60 - now.getMinutes();
			long diffSeconds = 60 - now.getSeconds();
			txt_dt.setText(diffdays + "天" + diffHours + ":" + diffMinutes + ":" + diffSeconds);

			handler.postDelayed(this, 1000);
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mArguments = getArguments();
		int actionBarBg = mArguments != null ? mArguments.getInt(ARG_ACTION_BG_RES) : R.drawable.ab_background_light;
		articleCategories = (ArrayList<ArticleCategory>) mArguments.getSerializable("articleCategories");

		mFadingHelper = new FadingActionBarHelper().actionBarBackground(actionBarBg).headerLayout(R.layout.home_header)
				.contentLayout(R.layout.home_content).lightActionBar(true);
		mFadingHelper.initActionBar(activity);
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

	private void getRequestforUrl(int id, final String parent, final int type) {

		String articleUrl = "http://api.kptaipei.tw/v1/category/" + id + "?page_size=1&page=1";
		JsonObjectRequest articleDetailReq = new JsonObjectRequest(Method.GET, articleUrl, null,
				new Response.Listener<JSONObject>() {
					@SuppressLint("SimpleDateFormat")
					@Override
					public void onResponse(JSONObject response) {

						try {

							JSONArray array = response.getJSONArray("data");
							for (int j = 0; j < array.length(); j++) {
								JSONObject arrayObject = array.getJSONObject(j);
								News news = new News();
								news.setparent(parent);
								news.setTitle(arrayObject.getString("title"));
								news.setPlain_content(arrayObject.getString("plain_content"));
								news.setType(type);
								newsList.add(news);

							}
							adapter.notifyDataSetChanged();
							Num++;
							if (Num == articleCategories.size()) {
								News newsTitle2 = new News();
								newsTitle2.setTitle(getResources().getString(R.string.home_subtitle2));
								newsTitle2.setType(Configuration.TYPE_HEADER);
								newsList.add(newsTitle2);
								getPhotoforUrl();
							}
						} catch (JSONException e) {
							// TODO 自動產生的 catch 區塊
							e.printStackTrace();
						}

					}

					// }
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						VolleyLog.d(TAG, "Error: " + error.getMessage());

					}
				});
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(articleDetailReq);

	}

	private void getPhotoforUrl() {
		final String url = "http://api.kptaipei.tw/v1/albums/?page_size=2";
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "List of photos json reponse: " + response.toString());
						try {
							// Parsing the json response
							JSONArray array = response.getJSONArray("data");
							for (int i = 0; i < array.length(); i++) {
								JSONObject arrayObject = array.getJSONObject(i);
								News album = new News();
								album.setid(arrayObject.getString("id"));
								album.setTitle(arrayObject.getString("title"));
								JSONObject thumObject = arrayObject.getJSONObject("thumbnails");
								Map<String, String> item = new HashMap<String, String>();
								item.put("small", thumObject.getString("small"));
								item.put("small_square", thumObject.getString("small_square"));
								item.put("large_square", thumObject.getString("large_square"));
								item.put("thumbnail", thumObject.getString("thumbnail"));
								item.put("medium", thumObject.getString("medium"));
								item.put("large", thumObject.getString("large"));
								item.put("original", thumObject.getString("original"));
								album.setType(Configuration.TYPE_PHOTO);
								album.setThumbnails(item);
								newsList.add(album);
							}
							// looping through each photo and adding it to list
							// data set

							Log.d(TAG, "Photo: " + url);

							// Notify list adapter about dataset changes. So
							// that it renders grid again
							adapter.notifyDataSetChanged();

						} catch (JSONException e) {
							e.printStackTrace();

						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error: " + error.getMessage());
					}
				});

		// Remove the url from cache
		AppController.getInstance().getRequestQueue().getCache().remove(url);
		// Disable the cache for this url, so that it always fetches updated
		// json
		jsonObjReq.setShouldCache(false);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}
}
