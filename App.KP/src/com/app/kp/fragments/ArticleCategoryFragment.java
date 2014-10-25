package com.app.kp.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.app.kp.ArticleActivity;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.app.kp.adapter.ArtcleAdapter;
import com.app.kp.adapter.ImageAdapter;
import com.app.kp.model.Article;
import com.app.kp.model.ArticleCategory;
import com.app.kp.utils.Configuration;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ArticleCategoryFragment extends Fragment {
	// Log tag
	private static final String TAG = ArticleCategoryFragment.class.getSimpleName();

	private ListView lv_artcle;
	private ArtcleAdapter adapter;
	private List<ArticleCategory> articleCategories;
	private ProgressDialog pDialog;

	private FadingActionBarHelper mFadingHelper;

	private AutoScrollViewPager viewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		AppController con = (AppController) getActivity().getApplication();
		Tracker t = con.getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(ArticleCategoryFragment.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		super.onCreate(savedInstanceState);
		NavigationDrawerActivity.exitFlag = false;
		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		int actionBarBg = R.drawable.ab_background_light;

		mFadingHelper = new FadingActionBarHelper().actionBarBackground(actionBarBg).headerLayout(R.layout.home_header)
				.contentLayout(R.layout.fragment_articlecategory).lightActionBar(true);
		mFadingHelper.initActionBar(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		// View rootView = inflater.inflate(R.layout.fragment_articlecategory,
		// container, false);
		View rootView = mFadingHelper.createView(inflater);

		viewPager = (AutoScrollViewPager) rootView.findViewById(R.id.view_pager);
		ArrayList<String> imageList = new ArrayList<String>();
		imageList.add("https://farm4.staticflickr.com/3845/15110878868_e8c1ac54a3_b.jpg");
		imageList.add("https://farm4.staticflickr.com/3852/15109820819_6fe727385d_b.jpg");
		imageList.add("https://farm4.staticflickr.com/3905/15108663428_f7d477d5b9_b.jpg");
		imageList.add("https://farm4.staticflickr.com/3934/15462310021_df64d93f26_b.jpg");
		imageList.add("https://farm6.staticflickr.com/5591/15068201487_c5c193182f_b.jpg");
		imageList.add("https://farm6.staticflickr.com/5593/15026607189_1b92d810ee_b.jpg");
		viewPager.setAdapter(new ImageAdapter(getActivity(), imageList));
		viewPager.setInterval(5000);// 設定滾動時間
		viewPager.setBorderAnimation(false);// 從最後一張到第一張是否要有動畫
		viewPager.startAutoScroll();// 啟動自動滾動
		viewPager.setSlideBorderMode(2);

		String url = "http://api.kptaipei.tw/v1/category/?accessToken=kp53f5989f258a84.59605552";
		lv_artcle = (ListView) rootView.findViewById(android.R.id.list);
		pDialog = new ProgressDialog(getActivity());
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

		articleCategories = new ArrayList<ArticleCategory>();
		adapter = new ArtcleAdapter(getActivity(), articleCategories);
		JsonObjectRequest articleReq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						hidePDialog();
						try {

							JSONArray array = response.getJSONArray("data");
							for (int j = 0; j < array.length(); j++) {
								JSONObject arrayObject = array.getJSONObject(j);
								ArticleCategory articleCategory = new ArticleCategory();
								articleCategory.setId(arrayObject.getInt("id"));
								articleCategory.setName(arrayObject.getString("name"));
								articleCategories.add(articleCategory);
							}
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO 自動產生的 catch 區塊
							e.printStackTrace();
						}

					}

					// }
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						hidePDialog();
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						Toast.makeText(getActivity(), "網路讀取錯誤", Toast.LENGTH_SHORT).show();

					}
				});
		lv_artcle.setAdapter(adapter);
		lv_artcle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, final int position, long arg3) {
				// TODO 自動產生的方法 Stub
				pDialog.show();
				final ArrayList<Article> articleList = new ArrayList<Article>();
				String articleUrl = "http://api.kptaipei.tw/v1/category/" + articleCategories.get(position - 1).getId();
				JsonObjectRequest articleDetailReq = new JsonObjectRequest(Method.GET, articleUrl, null,
						new Response.Listener<JSONObject>() {
							@SuppressLint("SimpleDateFormat")
							@Override
							public void onResponse(JSONObject response) {
								hidePDialog();
								try {

									JSONArray array = response.getJSONArray("data");
									for (int j = 0; j < array.length(); j++) {
										JSONObject arrayObject = array.getJSONObject(j);
										Article articleCategory = new Article();
										articleCategory.setTitle(arrayObject.getString("title"));
										articleCategory.setLast_modify(arrayObject.getString("last_modify"));
										articleCategory.setContent(arrayObject.getString("content"));
										articleCategory.setPlain_content(arrayObject.getString("plain_content"));
										articleCategory.setPost_date(arrayObject.getString("post_date"));
										articleCategory.setUrl(arrayObject.getString("url"));
										articleCategory.setThumbnail(arrayObject.getString("thumbnail"));
										SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
										Date d;
										try {
											d = new SimpleDateFormat(Configuration.DATE_FORMAT_PATTERN, Locale.CHINESE)
													.parse(arrayObject.getString("last_modify"));
											if (d != null) {
												String CurrentDate = df1.format(d.getTime());
												articleCategory.setFormatPublisheAt(CurrentDate);
											}
										} catch (ParseException e) {
											// TODO 自動產生的 catch 區塊
											e.printStackTrace();
										}

										articleList.add(articleCategory);

									}
									Intent intent = new Intent(getActivity(), ArticleActivity.class);
									Bundle bundle = new Bundle();
									bundle.putSerializable("article", articleList);
									intent.putExtras(bundle);
									intent.putExtra("title", articleCategories.get(position - 1).getName());
									startActivity(intent);

								} catch (JSONException e) {
									// TODO 自動產生的 catch 區塊
									e.printStackTrace();
								}

							}

							// }
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								hidePDialog();
								VolleyLog.d(TAG, "Error: " + error.getMessage());
								Toast.makeText(getActivity(), "網路讀取錯誤", Toast.LENGTH_SHORT).show();

							}
						});
				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(articleDetailReq);
			}
		});
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(articleReq);
		return rootView;
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();

		}
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
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}
}
