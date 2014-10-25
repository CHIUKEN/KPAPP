package com.app.kp.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.app.kp.AppController;
import com.app.kp.R;
import com.app.kp.model.Photo;
import com.app.kp.utils.TouchImageView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FullPhotoViewAdapter extends PagerAdapter {

	private Activity _activity;
	private ArrayList<Photo> _photoList;
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	// constructor
	public FullPhotoViewAdapter(Activity activity, ArrayList<Photo> photoList) {
		this._activity = activity;
		this._photoList = photoList;
	}

	@Override
	public int getCount() {
		return this._photoList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TouchImageView imgDisplay;
		Button btnClose;

		inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.fullphotoview_item, container, false);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
		btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

		imgDisplay.setScaleType(ImageView.ScaleType.CENTER_CROP);
		
		imgDisplay.setImageUrl(_photoList.get(position).getImages().get("large"), imageLoader);

		// close button click event
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_activity.finish();
			}
		});

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);

	}
}
