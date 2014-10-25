package com.app.kp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.app.kp.AppController;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.app.kp.adapter.FinancialAdapter;
import com.app.kp.model.Financial;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.twotoasters.jazzylistview.JazzyListView;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FinancialFragment extends Fragment {
	private static final String TAG = FinancialFragment.class.getSimpleName();
	private FadingActionBarHelper mFadingHelper;

	private JazzyListView lv_fincial;
	
	private FinancialAdapter fadapter;
	private ArrayList<Financial> financialList;
	private ProgressDialog pDialog;
	private NetworkImageView Networkimage;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	@Override
	public void onAttach(Activity activity) {
		// TODO 自動產生的方法 Stub
		super.onAttach(activity);
		int actionBarBg = R.drawable.ab_background;
		mFadingHelper = new FadingActionBarHelper().actionBarBackground(actionBarBg)
				.headerLayout(R.layout.financial_header)// .header_light)
				.contentLayout(R.layout.fragment_financial).lightActionBar(true);
		mFadingHelper.initActionBar(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		AppController con = (AppController) getActivity().getApplication();
		Tracker t = con.getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(FinancialFragment.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		super.onCreate(savedInstanceState);
		NavigationDrawerActivity.exitFlag = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		View rootView = mFadingHelper.createView(inflater);
		Networkimage = (NetworkImageView) rootView.findViewById(R.id.image_header);
		Networkimage.setImageUrl("https://farm6.staticflickr.com/5569/15088624490_2ca1fc7212_b.jpg", imageLoader);
		String url = "http://api.kptaipei.tw/v1/financial/all";
		lv_fincial = (JazzyListView) rootView.findViewById(android.R.id.list);
		financialList = new ArrayList<Financial>();
		fadapter = new FinancialAdapter(getActivity(), financialList);
		pDialog = new ProgressDialog(getActivity());
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();
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
								Financial financial = new Financial();
								financial.setAccount(arrayObject.getString("account"));
								financial.sePrice(arrayObject.getInt("price"));
								financial.setType(arrayObject.getString("type"));
								financial.setLabel(arrayObject.getString("label"));
								financial.setStart_date(arrayObject.getString("start_date"));
								financial.setEnd_date(arrayObject.getString("end_date"));
								financial.setStart_timestamp(arrayObject.getInt("start_timestamp"));
								financial.setEnd_timestamp(arrayObject.getInt("end_timestamp"));
								financialList.add(financial);
							}
							hidePDialog();
							fadapter.notifyDataSetChanged();
							lv_fincial.setTransitionEffect(1);
						} catch (JSONException e) {
							e.printStackTrace();
							hidePDialog();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error: " + error.getMessage());
						hidePDialog();
						Toast.makeText(getActivity(), "網路讀取錯誤", Toast.LENGTH_SHORT).show();
					}
				});
		lv_fincial.setAdapter(fadapter);
		// Remove the url from cache
		AppController.getInstance().getRequestQueue().getCache().remove(url);
		// Disable the cache for this url, so that it always fetches updated
		// json
		jsonObjReq.setShouldCache(false);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);

		return rootView;
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
