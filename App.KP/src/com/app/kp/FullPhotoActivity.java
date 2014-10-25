package com.app.kp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.kp.adapter.PhotoAdapter;
import com.app.kp.model.Photo;
import com.app.kp.utils.Configuration;
import com.app.kp.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.twotoasters.jazzylistview.JazzyGridView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

public class FullPhotoActivity extends Activity {
	private static final String TAG = FullPhotoActivity.class.getSimpleName();
	private JazzyGridView gridView;
	private ProgressBar pbLoader;
	private ArrayList<Photo> photoList = new ArrayList<Photo>();
	private PhotoAdapter adapter;

	private String url;
	private int columnWidth;
	private ActionBar actionbar;
	private View loadingview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
	    t.setScreenName(FullPhotoActivity.class.getSimpleName());
	    t.send(new HitBuilders.AppViewBuilder().build());
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_fullphoto);
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_background));

		String title = getIntent().getStringExtra("Title");
		String id = getIntent().getStringExtra("PhotoID");
		actionbar.setTitle(title);
		url = "http://api.kptaipei.tw/v1/albums/" + id;
		gridView = (JazzyGridView) findViewById(R.id.grid_view);
		gridView.setVisibility(View.GONE);
		pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
		pbLoader.setVisibility(View.VISIBLE);
		loadingview = (View) findViewById(R.id.loadingview);
		loadingview.setVisibility(View.VISIBLE);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "List of photos json reponse: " + response.toString());
						try {
							// Parsing the json response

							JSONObject dataObject = response.getJSONObject("data");
							JSONArray photoJsonArray = dataObject.getJSONArray("photos");
							for (int i = 0; i < photoJsonArray.length(); i++) {
								JSONObject arrayObject = photoJsonArray.optJSONObject(i);
								Photo photo = new Photo();
								photo.setId(Long.parseLong(arrayObject.getString("id")));
								photo.setTitle(arrayObject.getString("title"));
								photo.setIsPrimary(arrayObject.getString("isprimary"));
								photo.setLink(arrayObject.getString("link"));
								Map<String, String> imagesMap = new HashMap<String, String>();
								JSONObject imagesObject = arrayObject.getJSONObject("images");
								imagesMap.put(Configuration.THUMBNAILS_SMALL,
										imagesObject.getString(Configuration.THUMBNAILS_SMALL));
								imagesMap.put(Configuration.THUMBNAILS_SMALL_SQUARE,
										imagesObject.getString(Configuration.THUMBNAILS_SMALL_SQUARE));
								imagesMap.put(Configuration.THUMBNAILS_LARGE,
										imagesObject.getString(Configuration.THUMBNAILS_LARGE));
								imagesMap.put(Configuration.THUMBNAILS_LARGE_SQUARE,
										imagesObject.getString(Configuration.THUMBNAILS_LARGE_SQUARE));
								imagesMap.put(Configuration.THUMBNAILS_MEDIUM,
										imagesObject.getString(Configuration.THUMBNAILS_MEDIUM));
								imagesMap.put(Configuration.THUMBNAILS_ORIGINAL,
										imagesObject.getString(Configuration.THUMBNAILS_ORIGINAL));
								imagesMap.put(Configuration.THUMBNAILS_THUMBNAIL,
										imagesObject.getString(Configuration.THUMBNAILS_THUMBNAIL));
								photo.setImages(imagesMap);
								photoList.add(photo);
							}
							// looping through each photo and adding it to list
							// data set

							Log.d(TAG, "Photo: " + url);

							// Notify list adapter about dataset changes. So
							// that it renders grid again
							adapter.notifyDataSetChanged();

							// Hide the loader, make grid visible
							loadingview.setVisibility(View.GONE);
							pbLoader.setVisibility(View.GONE);
							gridView.setVisibility(View.VISIBLE);
							gridView.setTransitionEffect(6);

						} catch (JSONException e) {
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

					}
				});

		// Remove the url from cache
		AppController.getInstance().getRequestQueue().getCache().remove(url);
		// Disable the cache for this url, so that it always fetches updated
		// json
		jsonObjReq.setShouldCache(false);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);

		// Initilizing Grid View
		InitilizeGridLayout();

		// Gridview adapter
		adapter = new PhotoAdapter(this, photoList, columnWidth);
		// setting grid view adapter
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO 自動產生的方法 Stub
				Intent intent = new Intent(getApplicationContext(), FullPhotoViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("Position", position);
				bundle.putSerializable("PhotoList", photoList);
				intent.putExtras(bundle);
				intent.putExtra("position", position);
				startActivity(intent);

			}
		});
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Configuration.GRID_PADDING,
				r.getDisplayMetrics());

		// Column width 固定為3
		columnWidth = (int) ((Utils.getScreenWidth(this) - (3 + 1) * padding)) / 3;

		// Setting number of grid columns
		gridView.setNumColumns(3);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);

		// Setting horizontal and vertical padding
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動產生的方法 Stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);

	}
}
