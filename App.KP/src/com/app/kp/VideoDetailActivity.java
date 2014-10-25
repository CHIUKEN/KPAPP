package com.app.kp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.kp.adapter.VideoDetailAdapter;
import com.app.kp.model.Video;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.twotoasters.jazzylistview.JazzyListView;

public class VideoDetailActivity extends Activity {
	private ActionBar actionBar;
	private String mTitle;
	private ArrayList<Video> videoList = new ArrayList<Video>();
	private JazzyListView lv_videoitem;

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
	    t.setScreenName(VideoDetailActivity.class.getSimpleName());
	    t.send(new HitBuilders.AppViewBuilder().build());
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_videodetail);

		lv_videoitem = (JazzyListView) findViewById(R.id.lv_videoitem);

		lv_videoitem.setTransitionEffect(1);
		videoList = (ArrayList<Video>) getIntent().getSerializableExtra("video");
		mTitle = getIntent().getStringExtra("actionTitle");

		actionBar = getActionBar();
		actionBar.setTitle(mTitle);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();

		VideoDetailAdapter adapter = new VideoDetailAdapter(this, videoList);
		lv_videoitem.setAdapter(adapter);

		lv_videoitem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				String id = videoList.get(position).getId();
				// TODO 自動產生的方法 Stub
				Intent intent = new Intent(VideoDetailActivity.this, VideoPlayActivity.class);
				intent.putExtra("id", id);

				startActivityForResult(intent, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自動產生的方法 Stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
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
