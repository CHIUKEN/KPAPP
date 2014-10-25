package com.app.kp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.kp.AppController;
import com.app.kp.R;
import com.app.kp.model.Album;
import com.app.kp.model.Photo;

import android.app.Activity;
import android.content.Context;
import android.provider.CalendarContract.Instances;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AlbumAdapter extends BaseAdapter {

	private Activity activity;
	private List<Album> albumsList = new ArrayList<Album>();
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private int imageWidth;

	public AlbumAdapter(Activity activity, ArrayList<Album> albumsList, int columnWidth) {
		// TODO 自動產生的建構子 Stub
		this.activity = activity;
		this.albumsList = albumsList;
		this.imageWidth = columnWidth;
	}

	@Override
	public int getCount() {
		// TODO 自動產生的方法 Stub
		return albumsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自動產生的方法 Stub
		return albumsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動產生的方法 Stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO 自動產生的方法 Stub
		if (inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.grid_item_photo, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		// Grid thumbnail image view
		NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
		TextView txt_photo = (TextView) convertView.findViewById(R.id.txt_photo);

		Album p = albumsList.get(position);

		txt_photo.setText(p.getTitle());
		thumbNail.setScaleType(ImageView.ScaleType.CENTER_CROP);
		thumbNail.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, imageWidth));
		thumbNail.setImageUrl(p.getThumbnails().get("large_square"), imageLoader);

		return convertView;
	}

}
