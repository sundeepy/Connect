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
import com.nitinag.connect.model.ConnectUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.GetCallback;
import com.parse.ParseException;

public class ActivityArrayAdapter extends ArrayAdapter<ConnectActivity> {

	public ActivityArrayAdapter(Context context, List<ConnectActivity> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ConnectActivity connectActivity = getItem(position);
		View v;
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.activity_item, parent, false);
		}
		else{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		final TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		final TextView tvActivityBody = (TextView) v.findViewById(R.id.tvActivityBody);
		final TextView createdAt = (TextView) v.findViewById(R.id.created_at);
		ivProfileImage.setImageResource(android.R.color.transparent);
		
		final ConnectUser createdBy = connectActivity.getUser();
		createdBy.fetchInBackground(new GetCallback<ConnectUser>() {

			@Override
			public void done(ConnectUser  user, ParseException arg1) {
				tvUserName.setText(user.getUserId());
				
				//ImageLoader imageLoader = ImageLoader.getInstance();
				/* Will set these items appropriately
				//imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
				*/
				
				
				tvActivityBody.setText(connectActivity.getType());
				createdAt.setText(connectActivity.getRelativeTimeAgo());
				
			}
			
		
		});
		
		
		return v;
	}

}
