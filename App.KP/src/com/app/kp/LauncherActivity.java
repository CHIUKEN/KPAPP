package com.app.kp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import com.app.kp.model.ArticleCategory;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;

public class LauncherActivity extends Activity {
	// Log tag
	private static final String TAG = LauncherActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
		// Set screen name.
	    // Where path is a String representing the screen name.
	    t.setScreenName(LauncherActivity.class.getSimpleName());

	    // Send a screen view.
	    t.send(new HitBuilders.AppViewBuilder().build());
		 GoogleAnalytics.getInstance(this).reportActivityStart(this);

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.act_launcher);
		getActionBar().hide();
		ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = CM.getActiveNetworkInfo();
		if (info == null) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("通知");
			dialog.setMessage("網路讀取錯誤！");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			dialog.show();
			return;

		}
		String url = "http://api.kptaipei.tw/v1/category/";
		JsonObjectRequest articleReq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@SuppressLint("SimpleDateFormat")
					@Override
					public void onResponse(JSONObject response) {

						try {
							final ArrayList<ArticleCategory> articleCategories = new ArrayList<ArticleCategory>();
							JSONArray array = response.getJSONArray("data");
							for (int j = 0; j < array.length(); j++) {
								JSONObject arrayObject = array.getJSONObject(j);
								ArticleCategory articleCategory = new ArticleCategory();
								articleCategory.setId(arrayObject.getInt("id"));
								articleCategory.setName(arrayObject.getString("name"));
								articleCategories.add(articleCategory);
							}
							Thread.sleep(2000);
							Intent intent = new Intent(LauncherActivity.this, NavigationDrawerActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("articleCategories", articleCategories);
							intent.putExtras(bundle);
							startActivity(intent);
							finish();
						} catch (JSONException e) {
							// TODO 自動產生的 catch 區塊
							e.printStackTrace();

						} catch (InterruptedException e) {
							// TODO 自動產生的 catch 區塊
							e.printStackTrace();
						}
					}

					// }
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						VolleyLog.d(TAG, "Error: " + error.getMessage());
						// hidePDialog();

					}
				});
		AppController.getInstance().addToRequestQueue(articleReq);
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);

	}
}
