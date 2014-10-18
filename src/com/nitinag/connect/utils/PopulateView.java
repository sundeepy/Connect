package com.nitinag.connect.utils;

import com.nitinag.connect.model.ConnectActivity;

import android.view.LayoutInflater;
import android.view.View;

public interface PopulateView {
	public View populate(LayoutInflater inflator, ConnectActivity activity);
	public View populate(LayoutInflater inflator, String activityType);
	public void populate(View v, ConnectActivity activity);
	
}
