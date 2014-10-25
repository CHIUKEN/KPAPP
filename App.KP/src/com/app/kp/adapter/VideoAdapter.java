package com.app.kp.adapter;


import java.util.List;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.kp.AppController;
import com.app.kp.R;
import com.app.kp.model.Video;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter {
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<Video> videoItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public VideoAdapter(Activity activity, List<Video> videoItems) {
		this.activity = activity;
		this.videoItems = videoItems;
	}

	@Override
	public int getCount() {
		// TODO 自動產生的方法 Stub
		return videoItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自動產生的方法 Stub
		return videoItems.get(position);
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
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.video_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		
		Video video=videoItems.get(position);
		
		TextView title=(TextView)convertView.findViewById(R.id.title);
		TextView desc=(TextView)convertView.findViewById(R.id.desc);
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
		
		title.setText(video.getTitle());
		desc.setText(video.getFormatPublisheAt());
		thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
		thumbNail.setImageUrl(video.getThumbnails().get("default").getUrl(), imageLoader);

		return convertView;
	}

}
