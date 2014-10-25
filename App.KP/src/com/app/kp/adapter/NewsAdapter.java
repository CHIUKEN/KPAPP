package com.app.kp.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.kp.AppController;
import com.app.kp.R;
import com.app.kp.model.News;
import com.app.kp.utils.Configuration;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private ArrayList<News> _newsList;
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public NewsAdapter(Activity activity, ArrayList<News> newsList) {

		this._newsList = newsList;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		// TODO 自動產生的方法 Stub
		return _newsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO 自動產生的方法 Stub
		return _newsList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動產生的方法 Stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		int type = super.getItemViewType(position);
		try {
			type = _newsList.get(position).getType();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return type;
	}

	@Override
	public int getViewTypeCount() {

		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動產生的方法 Stub
		News news = _newsList.get(position);
		int type = getItemViewType(position);
		
		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();
			switch (type) {
			case Configuration.TYPE_HEADER:
				convertView = inflater.inflate(R.layout.news_header, null);
				holder.txt = (TextView) convertView.findViewById(R.id.txt_news_title);

				break;
			case Configuration.TYPE_ARTICLE:
			case Configuration.TYPE_PHOTO:
				convertView = inflater.inflate(R.layout.news_content, null);
				holder.txt = (TextView) convertView.findViewById(R.id.txt_news_contenttitle);
				holder.thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
				holder.txt_subtitle = (TextView) convertView.findViewById(R.id.txt_subtitle);
				break;

			}
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt.setText(news.getTitle());
		if (type != Configuration.TYPE_HEADER) {
			if (news.getThumbnails() == null) {
				holder.txt_subtitle.setText("- " + news.getparent());
				holder.txt_subtitle.setVisibility(View.VISIBLE);
				holder.thumbNail.setVisibility(View.GONE);
			} else {
				holder.thumbNail.setVisibility(View.VISIBLE);
				holder.txt_subtitle.setVisibility(View.GONE);
				holder.thumbNail.setImageUrl(news.getThumbnails().get("large_square"), imageLoader);
			}
		}
		return convertView;
	}

	static class ViewHolder {
		public TextView txt;
		public NetworkImageView thumbNail;
		public TextView txt_subtitle;
	}
}
