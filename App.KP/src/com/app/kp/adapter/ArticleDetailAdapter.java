package com.app.kp.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.kp.AppController;
import com.app.kp.R;
import com.app.kp.model.Article;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleDetailAdapter extends BaseAdapter {
	private Activity _activity;
	private ArrayList<Article> _article;
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private final Resources res;
	public ArticleDetailAdapter(Activity articleActivity, ArrayList<Article> article) {
		// TODO 自動產生的建構子 Stub
		this._activity = articleActivity;
		this._article = article;
		res = articleActivity.getResources();
	}

	@Override
	public int getCount() {
		// TODO 自動產生的方法 Stub
		return _article.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自動產生的方法 Stub
		return _article.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動產生的方法 Stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動產生的方法 Stub
		if (inflater == null)
			inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.articlelist_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		Article article = _article.get(position);

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView desc = (TextView) convertView.findViewById(R.id.desc);
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);

		title.setText(article.getTitle());
		String sub = _activity.getResources().getString(R.string.article_subtitle);
		desc.setText(sub + article.getFormatPublisheAt());
		thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
		thumbNail.setImageUrl(article.getThumbnail(), imageLoader);
		int colorResId = position % 2 == 0 ? R.color.even : R.color.odd;
		convertView.setBackgroundColor(res.getColor(colorResId));
		return convertView;
	}

}
