package com.nitinag.connect.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitinag.connect.activity.R;
import com.nitinag.connect.model.ConnectActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ActivityArrayAdapter extends ArrayAdapter<ConnectActivity> {

	public ActivityArrayAdapter(Context context, List<ConnectActivity> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ConnectActivity tweet = getItem(position);
		View v;
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.activity_item, parent, false);
		}
		else{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvActivityBody = (TextView) v.findViewById(R.id.tvActivityBody);
		TextView created_at = (TextView) v.findViewById(R.id.created_at);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		/* Will set these items appropriately
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvActivityBody.setText(tweet.getBody());
		created_at.setText(tweet.getRelativeTimeAgo());
		tvTwitterHandle.setText("@" + tweet.getUser().getScreenName());
		*/
		return v;
	}

}
