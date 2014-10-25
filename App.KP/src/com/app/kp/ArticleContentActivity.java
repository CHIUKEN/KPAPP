package com.app.kp;

import com.app.kp.model.Article;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class ArticleContentActivity extends Activity {
	private ActionBar actionBar;
	private TextView txt_content_title;
	private TextView txt_content_desc;
	private Article article;
	private ShareActionProvider mShareActionProvider;
	private Intent mShareIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(ArticleContentActivity.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_articlecontent);
		article = (Article) getIntent().getSerializableExtra("article");
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_background));
		actionBar.setTitle("文章內容");
		actionBar.setDisplayHomeAsUpEnabled(true);
		txt_content_title = (TextView) findViewById(R.id.txt_content_title);
		txt_content_desc = (TextView) findViewById(R.id.txt_content_desc);
		txt_content_title.setText(article.getTitle());
		txt_content_desc.setText(article.getPlain_content());
		mShareIntent = new Intent(Intent.ACTION_SEND);
		mShareIntent.setType("text/plain");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		getMenuInflater().inflate(R.menu.menu_share, menu);

		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);

		// Fetch and store ShareActionProvider
		mShareActionProvider = (ShareActionProvider) item.getActionProvider();
		 // Connect the dots: give the ShareActionProvider its Share Intent
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(mShareIntent);
	    }
		// Return true to display menu
		return true;
	}

	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);

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
}
