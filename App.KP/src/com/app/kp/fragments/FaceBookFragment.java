package com.app.kp.fragments;

import com.app.kp.AppController;
import com.app.kp.NavigationDrawerActivity;
import com.app.kp.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FaceBookFragment extends Fragment{
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自動產生的方法 Stub
		
		View rootView = inflater.inflate(R.layout.fragment_facebook, container, false);
        WebView wv=(WebView) rootView.findViewById(R.id.wv_fb);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("https://m.facebook.com/DoctorKoWJ");
        return rootView;
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}
}
