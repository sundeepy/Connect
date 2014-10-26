package com.nitinag.connect.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.nitinag.connect.model.Assoc;
import com.nitinag.connect.model.CoffeeActivity;
import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.model.ConnectUser;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ActivityUtil { 
	
	private static final String TAG = "ActivityUtil";
	private static ConnectUser currUser = null;
	public final static void addActivity(ConnectActivity c, SaveCallback callback){
		c.saveInBackground(callback);
	}
	
	
	public final static ConnectUser getCurrentUser() throws ParseException{
		if (currUser == null){
			ParseUser me = ParseUser.getCurrentUser();
			if(me != null){
				ParseQuery<ConnectUser> query = ParseQuery.getQuery(ConnectUser.class);
				query.whereEqualTo("userId", me.getObjectId());
				List<ConnectUser> result = query.find();
				if(result.size() == 1){
					currUser = result.get(0);
				}
			}
		}
		
		return currUser;
	}
	
	public final static void addMockFriends() throws ParseException{
		ConnectUser me = getCurrentUser();
		
		
		List<ParseUser> friends = new ArrayList<ParseUser>();
		ParseUser nitin = new ParseUser();
		nitin.setUsername("nitin");
		nitin.setPassword("password");
		
		
		
		
		
		ParseUser vikram = new ParseUser();
		vikram.setUsername("vikram");
		vikram.setPassword("password");
		
		ParseUser ashish = new ParseUser();
		ashish.setUsername("ashish");
		ashish.setPassword("password");
		
		ParseUser sandeep = new ParseUser();
		sandeep.setUsername("sandeep");
		sandeep.setPassword("password");
		
		ParseUser vinod = new ParseUser();
		vinod.setUsername("vinod");
		vinod.setPassword("password");
		
		friends.add(nitin);
		friends.add(vinod);
		friends.add(vikram);
		friends.add(ashish);
		friends.add(sandeep);
		
		try {
			
			for (ParseUser parseUser : friends) {
				parseUser.signUp();
				ConnectUser newUser = new ConnectUser();
				newUser.setUserId(parseUser.getObjectId());
				newUser.setFirstName("John");
				newUser.setLastName("Smith");
				newUser.save();
				newUser.addFriend(me, new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e != null)
							Log.e(TAG, "Error saving relations with me", e);
						
					}
				});
				
				me.addFriend(newUser, new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if(e == null){
							Log.d(TAG, "User created successfully");
						}
						else {
							Log.e(TAG, "Error creating user", e);
						}
						
					}
					
					
				});
			}
			
			//ParseUser.saveAll(friends);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public final static void getFriends(ConnectUser user, FindCallback<ParseObject> callback){
		user.getFriends(callback);
	}
	
	
	public final static boolean activityExists(final ConnectUser user, Class<ConnectActivity> activity) throws ParseException{
		ParseQuery<ConnectActivity> userQuery = ParseQuery.getQuery(activity);
		userQuery.whereEqualTo("user", user);
		userQuery.whereEqualTo("expired", false);
		
		List<ConnectActivity> aList = userQuery.find();
		if(aList.size() > 0)
			return true;
		else
			return false;
	}
	
	public final static void saveActivity(final ConnectUser user, final ConnectActivity activity, final SaveCallback callback){
		getFriends(user, new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> fs, ParseException e) {
				List<ConnectUser> friends = new ArrayList<ConnectUser>();
				if(e == null){
					Log.d(TAG, "Got friends list size " + fs.size());
					System.err.println("Got friends list size " + fs.size());
					for (ParseObject f : fs) {
						friends.add((ConnectUser) f);
					}
					activity.setFor(friends, callback);
					
				}else{
					Log.e(TAG, "Error fetching friends list", e);
				}
				
			}
			
		});
		
	}
	
	
	
	public final static void getActivityList(ConnectUser user, final FindCallback<ConnectActivity> callback, boolean self, String activityType){
		ParseQuery<ConnectActivity> query = null;
		if(activityType.equals(Constants.COFFEE)){
			query = ParseQuery.getQuery("CoffeeActivity");
		}
		else if (activityType.equals(Constants.LUNCH)){
			query = ParseQuery.getQuery("LunchActivity");
		}
		else if (activityType.equals(Constants.RIDE)){
			query = ParseQuery.getQuery("RideActivity");
		}
		else if (activityType.equals(Constants.DINNER)){
			query = ParseQuery.getQuery("DinnerActivity");
		}
		
		else 
			return;
		
		
			if (!self)
				query.whereEqualTo("for", user);
			else
				query.whereEqualTo("user", user);
			
			query.findInBackground(callback);
		  
		
	}
}
