package com.app.kp.adapter;

import java.util.ArrayList;

import com.app.kp.R;
import com.app.kp.model.Financial;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FinancialAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ArrayList<Financial> financialList;
	private final Resources res;

	public FinancialAdapter(Activity activity, ArrayList<Financial> financialList) {
		// TODO 自動產生的建構子 Stub
		inflater = LayoutInflater.from(activity);
		this.financialList = financialList;
		res = activity.getResources();
	}

	@Override
	public int getCount() {
		// TODO 自動產生的方法 Stub
		return financialList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自動產生的方法 Stub
		return financialList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自動產生的方法 Stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自動產生的方法 Stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.financial_item, null);
			holder.txt_account = (TextView) convertView.findViewById(R.id.txt_account);
			holder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
			holder.txt_label = (TextView) convertView.findViewById(R.id.txt_label);
			holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
			holder.txt_type = (TextView) convertView.findViewById(R.id.txt_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Financial financial = financialList.get(position);
		holder.txt_account.setText("【" + financial.getAccount() + "】");
		holder.txt_label.setText(financial.getLabel());
		holder.txt_date.setText(financial.getStart_date() + "~" + financial.getEnd_date());
		holder.txt_price.setText("$" + String.valueOf(financial.getPrice()));

		if (financial.getType().equals("income")) {
			holder.txt_type.setText("收入");
			holder.txt_type.setTextColor(res.getColor(R.color.olive));
		}
		if (financial.getType().equals("expense")) {
			holder.txt_type.setText("支出");
			holder.txt_type.setTextColor(Color.RED);
		}
		int colorResId = position % 2 == 0 ? R.color.even : R.color.odd;
		convertView.setBackgroundColor(res.getColor(colorResId));
		return convertView;
	}

	private static class ViewHolder {
		public TextView txt_account;
		public TextView txt_label;
		public TextView txt_type;
		public TextView txt_date;
		public TextView txt_price;
	}
}
