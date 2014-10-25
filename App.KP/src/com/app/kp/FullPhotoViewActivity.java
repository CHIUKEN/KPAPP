package com.app.kp;

import java.util.ArrayList;

import com.app.kp.adapter.FullPhotoViewAdapter;
import com.app.kp.model.Photo;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class FullPhotoViewActivity extends Activity {
	private ActionBar actionbar;
	private FullPhotoViewAdapter adapter;
	private ViewPager viewPager;
	private Intent intent;
	private int position;
	private ArrayList<Photo> photoList = new ArrayList<Photo>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
	    t.setScreenName(FullPhotoViewActivity.class.getSimpleName());
	    t.send(new HitBuilders.AppViewBuilder().build());
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_fullphotoview);

		intent = getIntent();
		photoList = (ArrayList<Photo>) getIntent().getSerializableExtra("PhotoList");
		position = intent.getExtras().getInt("Position");

		actionbar = getActionBar();
		actionbar.hide();

		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter = new FullPhotoViewAdapter(this, photoList);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
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
