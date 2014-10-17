package com.nitinag.connect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.nitinag.connect.model.CoffeeActivity;
import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.model.ConnectUser;
import com.nitinag.connect.model.DinnerActivity;
import com.nitinag.connect.model.LunchActivity;
import com.nitinag.connect.model.RideActivity;
import com.nitinag.connect.utils.ActivityUtil;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//ActivityUtil.addMockFriends();
		
		return true;
	}

	private boolean createNew(ConnectUser user){
		
		try {
			return ActivityUtil.activityExists(user);
		} catch (ParseException e) {
			Log.e(TAG, "Error checking if activity type exists", e);
		}
		return false;
	}
	
	
	public void onClick(View v){
		ConnectUser me = null;
		try {
			me = ActivityUtil.getCurrentUser();
		} catch (ParseException e) {
			Log.e(TAG, "Unable to get connectuser for the parse user", e);
			return;
		}
		
		
		switch (v.getId()) {
		case R.id.btCoffee:
			ConnectActivity newActivity = new CoffeeActivity();
			if(createNew(me))
				addActivity(newActivity);
			else{
				Intent i = new Intent(this, DashboardActivity.class);
				startActivity(i);
			}
			
			break;
		case R.id.btLunch:
			addActivity(new LunchActivity());
			break;
		case R.id.btRide:
			addActivity(new RideActivity());
			break;
		case R.id.btDinner:
			addActivity(new DinnerActivity());
			break;
		default:
			break;
		}
	}

	private void addActivity(final ConnectActivity connectActivity)  {
		try{
			ConnectUser me = ActivityUtil.getCurrentUser();	
			connectActivity.setUser(me);
			
			SaveCallback callback = new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					if(e != null)
						Log.e(TAG, e.toString(), e);
					else {
						Toast.makeText(MainActivity.this, "A " + connectActivity.getType() + " actvity got created" , Toast.LENGTH_SHORT).show();
					}
				}
			};
			
			ActivityUtil.saveActivity(ActivityUtil.getCurrentUser(), connectActivity, callback);
	
		}catch(ParseException e){
			
		}
	}
	
}
