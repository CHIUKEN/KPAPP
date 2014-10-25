package com.app.kp.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.app.kp.AppController;
import com.app.kp.R;
import com.app.kp.model.ArticleCategory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArtcleAdapter extends BaseAdapter {

	private Activity _activity;
	private List<ArticleCategory> _articleCategories;
	private LayoutInflater inflater;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public ArtcleAdapter(Activity activity, List<ArticleCategory> articleCategories) {
		// TODO �۰ʲ��ͪ��غc�l Stub
		this._activity = activity;
		this._articleCategories = articleCategories;
	}

	@Override
	public int getCount() {
		// TODO �۰ʲ��ͪ���k Stub
		return _articleCategories.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO �۰ʲ��ͪ���k Stub
		return _articleCategories.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO �۰ʲ��ͪ���k Stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO �۰ʲ��ͪ���k Stub
		if (inflater == null)
			inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.articlecategory_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView txt_title = (TextView) convertView.findViewById(R.id.txt_artcle_title);
		
		txt_title.setText(_articleCategories.get(position).getName());
		
		return convertView;
	}

}
