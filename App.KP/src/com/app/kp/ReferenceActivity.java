package com.app.kp;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class ReferenceActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
	    t.setScreenName(ReferenceActivity.class.getSimpleName());
	    t.send(new HitBuilders.AppViewBuilder().build());
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_background));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.act_reference);
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
