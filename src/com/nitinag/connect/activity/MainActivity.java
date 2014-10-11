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
		
		ActivityUtil.getFriends(null);
		
		return true;
	}

	private boolean createNew(ConnectActivity connectActivity){
		return false;
	}
	
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btCoffee:
			ConnectActivity newActivity = new CoffeeActivity();
			if(createNew(newActivity))
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

	private void addActivity(final ConnectActivity connectActivity) {
		connectActivity.setUserId(ParseUser.getCurrentUser().getObjectId());
		
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
		
		ActivityUtil.saveActivity(ParseUser.getCurrentUser(), connectActivity, callback);
	}
	
}
