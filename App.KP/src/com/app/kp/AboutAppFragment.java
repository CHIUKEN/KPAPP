package com.app.kp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutAppFragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �۰ʲ��ͪ���k Stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �۰ʲ��ͪ���k Stub
		View rootView = inflater.inflate(R.layout.fragment_aboutapp, container, false);
        
        return rootView;
	}

}
