package com.nitinag.connect.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.model.ConnectUser;
import com.nitinag.connect.utils.ActivityArrayAdapter;
import com.nitinag.connect.utils.ActivityUtil;
import com.nitinag.connect.utils.Constants;
import com.nitinag.connect.utils.EndlessScrollListener;
import com.nitinag.connect.utils.PopulateView;
import com.nitinag.connect.utils.UpdateActivityThread;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class ActivitiesFragment extends Fragment implements PopulateView{
	protected static final String TAG = "ActivitiesFragment";
	private ArrayList<ConnectActivity> activities;
	private Map<ConnectActivity, View> progressMap;
	protected ArrayAdapter<ConnectActivity> aActivities;
	protected PullToRefreshListView lvActivities;
	private Handler mHandler = new Handler();
	private String activityType;
	private boolean self;
	protected UpdateActivityThread updateProgress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activities = new ArrayList<ConnectActivity>();
		aActivities = new ActivityArrayAdapter(getActivity(), this, activities);
		progressMap = new HashMap<ConnectActivity, View>();
		Bundle args = getArguments();
		self = args.getBoolean("self");
		activityType = args.getString("activityType");
		updateProgress = new UpdateActivityThread(mHandler, progressMap);
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_activities, container, false);
		lvActivities = (PullToRefreshListView) v.findViewById(R.id.lvActivityList);
		lvActivities.setAdapter(aActivities);
		
		lvActivities.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call listView.onRefreshComplete() when
                // once the network request has completed successfully.
            	updateProgress.suspend();
            	aActivities.clear();
            	progressMap.clear();
            	
            	try {
					populateActivities();
				} catch (ParseException e) {
					Log.e(TAG, "Unable to populate activities", e);
				}
            	
            	
            }
        });
		
		
		lvActivities.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
				//customLoadMoreDataFromApi(); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
				
			}
		});
		
		lvActivities.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
			}
			
		});
		
		
		try {
			aActivities.clear();	
			progressMap.clear();
			populateActivities();
			new Thread(updateProgress).start();
		} catch (ParseException e) {
			Log.e(TAG, "Unable to populate activities", e);
		}
		return v;
	}

	private void populateActivities() throws ParseException {
		Log.d(TAG, "Fetching activity type " + activityType);
		ActivityUtil.getActivityList(ActivityUtil.getCurrentUser(), new FindCallback<ConnectActivity>() {
			
			@Override
			public void done(List<ConnectActivity> as, ParseException pe) {
				if(pe == null){
					Log.d(TAG, "Number of activities retrieved " + activities.size());
					for (ParseObject a : as) {
						activities.add((ConnectActivity) a);
					}
					aActivities.notifyDataSetChanged();
					lvActivities.onRefreshComplete();
					//if(!updateProgress.isStarted())
					//	new Thread(updateProgress).start();
					updateProgress.resume();
			    }
				else {
					Log.e(TAG, "Unable to retrieve friend activity list", pe);
				}
			}
		}, self, activityType);
	}


	@Override
	public View populate(LayoutInflater inflater, ConnectActivity activity) {
		throw new UnsupportedOperationException();
	}


	@Override
	public void populate(View v, final ConnectActivity connectActivity) {
		final ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		final TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		final TextView tvActivityBody = (TextView) v.findViewById(R.id.tvActivityBody);
		final TextView createdAt = (TextView) v.findViewById(R.id.created_at);
		final TextView tvProgress = (TextView) v.findViewById(R.id.tvRemaining);
		progressMap.put(connectActivity, tvProgress);
		
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		
		final ConnectUser createdBy = connectActivity.getUser();
		createdBy.fetchInBackground(new GetCallback<ConnectUser>() {

			@Override
			public void done(ConnectUser  user, ParseException arg1) {
				tvUserName.setText(user.getFirstName() + " " + user.getLastName());
				
				if(connectActivity.getType().equals(Constants.COFFEE))
					ivProfileImage.setImageDrawable(
							getResources().getDrawable(R.drawable.bb)
						);
				else if(connectActivity.getType().equals(Constants.RIDE))
					ivProfileImage.setImageDrawable(
							getResources().getDrawable(R.drawable.ic_car)
						);
				else if(connectActivity.getType().equals(Constants.LUNCH))
					ivProfileImage.setImageDrawable(
							getResources().getDrawable(R.drawable.ic_lunch)
						);
				else if(connectActivity.getType().equals(Constants.DINNER))
					ivProfileImage.setImageDrawable(
							getResources().getDrawable(R.drawable.ic_dinner)
						);
				
				tvActivityBody.setText(connectActivity.getMessage());
				createdAt.setText(connectActivity.getRelativeTimeAgo());
				
				
			}
			
		
		});
		
	}


	@Override
	public View populate(LayoutInflater inflator, String activityType) {
		throw new UnsupportedOperationException();
	}
	

}
