package com.nitinag.connect.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.utils.ActivityArrayAdapter;
import com.nitinag.connect.utils.ActivityUtil;
import com.nitinag.connect.utils.EndlessScrollListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class ActivitiesFragment extends Fragment {
	protected static final String TAG = "ActivitiesFragment";
	private ArrayList<ConnectActivity> activities;
	protected ArrayAdapter<ConnectActivity> aActivities;
	protected PullToRefreshListView lvActivities;
	private String activityType = "Coffee";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		activities = new ArrayList<ConnectActivity>();
		aActivities = new ActivityArrayAdapter(getActivity(), activities);
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
            	aActivities.clear();
            	populateActivities();
            	
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
		
		
		populateActivities();
		return v;
	}

	private void populateActivities() {
		ActivityUtil.getActivityList(ParseUser.getCurrentUser(), new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> activities, ParseException pe) {
				if(pe == null){
					Log.d(TAG, "Number of activities retrieved " + activities.size());
					for (ParseObject activity : activities) {
						activities.add(activity);
					}
					aActivities.notifyDataSetChanged();
					lvActivities.onRefreshComplete();
			    }
				else {
					Log.e(TAG, "Unable to retrieve friend activity list", pe);
				}
			}
		}, activityType);
	}
	

}