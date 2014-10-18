package com.nitinag.connect.utils;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nitinag.connect.activity.R;
import com.nitinag.connect.model.ConnectActivity;

public class ActivityArrayAdapter extends ArrayAdapter<ConnectActivity> {

	
	
	private PopulateView mPopulate;

	public ActivityArrayAdapter(Context context, Fragment fragment, List<ConnectActivity> objects) {
		super(context, 0, objects);
		mPopulate = (PopulateView)fragment;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ConnectActivity connectActivity = getItem(position);
		View v;
		
		
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.activity_item, parent, false);
			mPopulate.populate(v, connectActivity);
		}
		else{
			v = convertView;
			mPopulate.populate(v, connectActivity);
		}
		return v;
	}

}
