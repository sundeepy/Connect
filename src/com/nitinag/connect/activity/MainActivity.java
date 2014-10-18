package com.nitinag.connect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nitinag.connect.activity.ActivityDialogFragment.ActivityDialogListener;
import com.nitinag.connect.model.CoffeeActivity;
import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.model.ConnectUser;
import com.nitinag.connect.model.DinnerActivity;
import com.nitinag.connect.model.LunchActivity;
import com.nitinag.connect.model.RideActivity;
import com.nitinag.connect.utils.ActivityUtil;
import com.nitinag.connect.utils.Constants;
import com.nitinag.connect.utils.PopulateView;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class MainActivity extends FragmentActivity implements ActivityDialogListener, PopulateView{
	
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

	private boolean canCreateNew(ConnectUser user, Class activity){
		
		try {
			if(ActivityUtil.activityExists(user, activity))
				return false;
			
		} catch (ParseException e) {
			Log.e(TAG, "Error checking if activity type exists", e);
		}
		return true;
	}
	
	private void createActivityAndShow(ConnectUser me, String activityType, Class activityClass){
		if(canCreateNew(me, activityClass))
			showActivityCreateDialog(activityType);
		else{
           Intent i = new Intent(this, DashboardActivity.class);
           Bundle args = new Bundle();
           args.putString("activityType", activityType);
   		   i.putExtras(args);
           startActivity(i);
		}  	
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
				createActivityAndShow(me, Constants.COFFEE, CoffeeActivity.class);
                break;
			case R.id.btLunch:
				createActivityAndShow(me, Constants.LUNCH, LunchActivity.class);
                break;
			case R.id.btRide:
				createActivityAndShow(me, Constants.RIDE, RideActivity.class);
				break;
			case R.id.btDinner:
				createActivityAndShow(me, Constants.DINNER, DinnerActivity.class);
				break;
			default:
				break;
			}
	}

	private void showActivityCreateDialog(String activityType) {
		DialogFragment dialog = new ActivityDialogFragment(activityType);
		dialog.show(getSupportFragmentManager(), "createactivity");
		
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
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String activityType) {
		EditText tvActivityMessage = ((ActivityDialogFragment)dialog).getActivityMessage();
		ConnectActivity newActivity = null;
		if(activityType.equals(Constants.COFFEE)){
			newActivity = new CoffeeActivity();
		}
		else if(activityType.equals(Constants.RIDE)){
			newActivity = new RideActivity();
		}
		else if(activityType.equals(Constants.LUNCH)){
			newActivity = new LunchActivity();
		}
		else if(activityType.equals(Constants.DINNER)){
			newActivity = new DinnerActivity();
		}
		else 
			return;
		
		newActivity.setMessage(tvActivityMessage.getText().toString());
		addActivity(newActivity);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog, String activityType) {
		//Do nothing
		
	}

	@Override
	public View populate(LayoutInflater inflater, ConnectActivity activity) {
		String activityType = activity.getType();
		return populate(inflater,  activityType);
        
	}

	@Override
	public void populate(View v, ConnectActivity actvity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View populate(LayoutInflater inflater, String activityType) {
		View v = inflater.inflate(R.layout.create_activity_dialog, null);
        ImageView ivActivity = (ImageView) v.findViewById(R.id.ivActivity);
        if(activityType.equals(Constants.COFFEE))
        	ivActivity.setImageDrawable(getResources().getDrawable(R.drawable.ic_coffee));
        else if(activityType.equals(Constants.RIDE))
        	ivActivity.setImageDrawable(getResources().getDrawable(R.drawable.ic_car));
        else if(activityType.equals(Constants.LUNCH))
        	ivActivity.setImageDrawable(
					getResources().getDrawable(R.drawable.ic_lunch)
				);
		else if(activityType.equals(Constants.DINNER))
			ivActivity.setImageDrawable(
					getResources().getDrawable(R.drawable.ic_dinner)
				);
        return v;
	}
	
}
