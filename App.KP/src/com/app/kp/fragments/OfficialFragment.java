package com.app.kp.fragments;

import com.app.kp.AppController;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class OfficialFragment extends Fragment {
	private WebView wv;
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		AppController con = (AppController) getActivity().getApplication();
		Tracker t = con.getTracker(AppController.TrackerName.APP_TRACKER);
		t.setScreenName(OfficialFragment.class.getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
		super.onCreate(savedInstanceState);
		NavigationDrawerActivity.exitFlag = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		View rootView = inflater.inflate(R.layout.fragment_official, container, false);
		// View rootView = mFadingHelper.createView(inflater);
		TypedValue tv = new TypedValue();
		int actionBarHeight = 0;
		if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
		}
	

		wv = (WebView) rootView.findViewById(R.id.wv_official);

		dialog = ProgressDialog.show(getActivity(), "", "loading");
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// getActivity().setTitle("Loading...");

				getActivity().setProgress(progress * 100);
				if (progress == 100)
					dialog.dismiss();
			}
		});

		wv.loadUrl("http://unlimited.kptaipei.tw/");
		return rootView;
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}
}
