package com.app.kp.fragments;

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

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.app.kp.AppController;
import com.app.kp.FullPhotoActivity;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.app.kp.adapter.AlbumAdapter;
import com.app.kp.model.Album;
import com.app.kp.utils.Configuration;
import com.app.kp.utils.Utils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.twotoasters.jazzylistview.JazzyGridView;

public class PhotosFragment extends Fragment {
	private static final String TAG = PhotosFragment.class.getSimpleName();
	private JazzyGridView gridView;
	private ProgressBar pbLoader;
	private String url = "http://api.kptaipei.tw/v1/albums/?accessToken=kp53f5989f258a84.59605552";
	private ArrayList<Album> albumsList = new ArrayList<Album>();
	private AlbumAdapter adapter;
	private int columnWidth;
	private View loadingview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		AppController con = (AppController) getActivity().getApplication();
		Tracker t = con.getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(PhotosFragment.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		super.onCreate(savedInstanceState);
		NavigationDrawerActivity.exitFlag = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
		// View rootView = mFadingHelper.createView(inflater);
		gridView = (JazzyGridView) rootView.findViewById(R.id.grid_view);
		gridView.setVisibility(View.GONE);
		pbLoader = (ProgressBar) rootView.findViewById(R.id.pbLoader);
		pbLoader.setVisibility(View.VISIBLE);
		loadingview = (View) rootView.findViewById(R.id.loadingview);
		loadingview.setVisibility(View.VISIBLE);
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
								Album album = new Album();
								album.setId(arrayObject.getString("id"));
								album.setPhotos(arrayObject.getInt("photos"));
								album.setVideos(arrayObject.getInt("videos"));
								album.setTitle(arrayObject.getString("title"));
								album.setDescription("description");
								album.setDate_create(arrayObject.getLong("date_create"));
								album.setDate_update(arrayObject.getLong("date_create"));
								album.setLink(arrayObject.getString("link"));
								JSONObject thumObject = arrayObject.getJSONObject("thumbnails");
								Map<String, String> item = new HashMap<String, String>();
								item.put("small", thumObject.getString("small"));
								item.put("small_square", thumObject.getString("small_square"));
								item.put("large_square", thumObject.getString("large_square"));
								item.put("thumbnail", thumObject.getString("thumbnail"));
								item.put("medium", thumObject.getString("medium"));
								item.put("large", thumObject.getString("large"));
								item.put("original", thumObject.getString("original"));
								album.setThumbnails(item);
								albumsList.add(album);
							}
							// looping through each photo and adding it to list
							// data set

							Log.d(TAG, "Photo: " + url);

							// Notify list adapter about dataset changes. So
							// that it renders grid again
							adapter.notifyDataSetChanged();

							// Hide the loader, make grid visible
							pbLoader.setVisibility(View.GONE);
							gridView.setVisibility(View.VISIBLE);
							loadingview.setVisibility(View.GONE);
							gridView.setTransitionEffect(6);

						} catch (JSONException e) {
							e.printStackTrace();

						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error: " + error.getMessage());
						Toast.makeText(getActivity(), "網路讀取錯誤", Toast.LENGTH_SHORT).show();

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
		adapter = new AlbumAdapter(getActivity(), albumsList, columnWidth);

		// setting grid view adapter
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO 自動產生的方法 Stub
				Album album = albumsList.get(position);
				Intent i = new Intent(getActivity(), FullPhotoActivity.class);
				i.putExtra("PhotoID", album.getId());
				i.putExtra("Title", album.getTitle());
				getActivity().startActivity(i);
			}

		});
		return rootView;
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Configuration.GRID_PADDING,
				r.getDisplayMetrics());

		// Column width 固定為3
		columnWidth = (int) ((Utils.getScreenWidth(getActivity()) - (3 + 1) * padding)) / 3;

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
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}
}
