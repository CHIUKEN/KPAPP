package com.app.kp.adapter;

import java.util.ArrayList;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.kp.AppController;
import com.app.kp.R;

import android.app.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends RecyclingPagerAdapter {
	public Activity activity;
	public ArrayList<String> imageList;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public ImageAdapter(Activity activity, ArrayList<String> imageList) {
		this.activity = activity;
		this.imageList = imageList;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		// TODO 自動產生的方法 Stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = holder.imageView = new NetworkImageView(activity);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		holder.imageView.setImageResource(R.drawable.img_bg);
		holder.imageView.setImageUrl(imageList.get(position), imageLoader);
		return convertView;

	}

	@Override
	public int getCount() {
		// TODO 自動產生的方法 Stub
		return imageList.size();
	}

	private static class ViewHolder {
		public NetworkImageView imageView;
	}
}
