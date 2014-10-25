package com.app.kp;

import java.util.ArrayList;

import com.app.kp.adapter.ArticleDetailAdapter;
import com.app.kp.model.Article;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.twotoasters.jazzylistview.JazzyListView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class ArticleActivity extends Activity {
	private ActionBar actionBar;
	private ArrayList<Article> articleList = new ArrayList<Article>();
	private JazzyListView lv_articlelist;
	private ArticleDetailAdapter adapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
	    t.setScreenName(ArticleActivity.class.getSimpleName());
	    t.send(new HitBuilders.AppViewBuilder().build());
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_article);

		articleList = (ArrayList<Article>) getIntent().getSerializableExtra("article");

		actionBar = getActionBar();
		actionBar.setTitle(getIntent().getStringExtra("title"));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_background));
		lv_articlelist = (JazzyListView) findViewById(R.id.lv_artcilelist);
		adapter = new ArticleDetailAdapter(this, articleList);
		lv_articlelist.setAdapter(adapter);
		lv_articlelist.setTransitionEffect(1);
		lv_articlelist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO 自動產生的方法 Stub
				Article article = articleList.get(position);
				Intent intent = new Intent(ArticleActivity.this, ArticleContentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("article", article);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
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
