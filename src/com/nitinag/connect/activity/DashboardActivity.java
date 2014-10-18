package com.nitinag.connect.activity;



import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nitinag.connect.utils.FragmentTabListener;
import com.parse.ParseUser;

public class DashboardActivity extends FragmentActivity{

	private String activityType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		Bundle args = getIntent().getExtras();
		activityType = args.getString("activityType");
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		
		return true;
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Bundle b1 = new Bundle();
		b1.putBoolean("self", false);
		b1.putString("activityType", activityType);
		Tab tab1 = actionBar
			.newTab()
			.setText("Friend Activities")
			.setIcon(R.drawable.ic_connect)
			.setTag("Friend Activities")
			.setTabListener(
				new FragmentTabListener<ActivitiesFragment>(R.id.flContainer, this, "FriendActivitiesView",
						ActivitiesFragment.class, b1));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);
		
		Bundle b2 = new Bundle();
		b2.putBoolean("self", true);
		b2.putString("activityType", activityType);
		Tab tab2 = actionBar
			.newTab()
			.setText("My Activities")
			.setIcon(R.drawable.ic_connect)
			.setTag("My Activities")
			.setTabListener(
			    new FragmentTabListener<ActivitiesFragment>(R.id.flContainer, this, "MyActivitiesView",
			    		ActivitiesFragment.class, b2));

		actionBar.addTab(tab2);
	}

	
	public void onSignOut(MenuItem item){
		ParseUser.logOut();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
