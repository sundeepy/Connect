package com.nitinag.connect.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.nitinag.connect.model.Assoc;
import com.nitinag.connect.model.CoffeeActivity;
import com.nitinag.connect.model.ConnectActivity;
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

	public final static void addActivity(ConnectActivity c, SaveCallback callback){
		c.saveInBackground(callback);
	}
	
	
	public final static void addMockFriends(){

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
			}
			
			ParseUser.saveAll(friends);
			addFriends(friends);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public final static void addFriends(List<ParseUser> friends){
		ParseUser user = ParseUser.getCurrentUser();
		ParseRelation<ParseObject> relation = user.getRelation("friends");
		for (ParseUser friend : friends) {
			relation.add(friend);
		}
		SaveCallback callback = new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				 if (e != null) {
				        Log.e("Update Friends List", e.toString(), e);
				      } else {
				        Log.d("Update Friends List", "Successfully Updated");
				 }
				
			}
		};
		user.saveInBackground(callback );
	}
	
	public final static void getFriends(FindCallback<ParseObject> callback){
		ParseUser user = ParseUser.getCurrentUser();
		ParseRelation<ParseObject> relation = user.getRelation("friends");
		if(callback == null) {
			relation.getQuery().findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> friends, ParseException e) {
			      if (e != null) {
			        Log.e("Friends List", e.toString(), e);
			      } else {
			        Log.d("Friends List", "Number of friends " + friends.size());
			      }
			    }
			});
		}
		else relation.getQuery().findInBackground(callback);
	}
	
	
	public final static void saveAssoc(String userId, List<String> friendIds, String activityId, String activityType, SaveCallback callback) throws ParseException{
		List<Assoc> newAssocs = new ArrayList<Assoc>();
		for (String friendId : friendIds) {
			Assoc newAssoc = new Assoc();
			newAssoc.setUserId(userId);
			newAssoc.setFriendId(friendId);
			newAssoc.setActivityId(activityId);
			newAssoc.setActivityType(activityType);
			newAssocs.add(newAssoc);
		}
		ParseObject.saveAllInBackground(newAssocs, callback);
	}
	
	
	public final static void saveActivity(final ParseUser user, final ConnectActivity activity, final SaveCallback callback){
		
		SaveCallback activitySaveCallback = new SaveCallback() {
			
			@Override
			public void done(ParseException pe) {
				if(pe == null){
					final String userId = user.getObjectId();
					final String activityId = activity.getObjectId();
					final List<String> friendIds = new ArrayList<String>();
					FindCallback<ParseObject> friendCallback = new FindCallback<ParseObject>() {
						
						@Override
						public void done(List<ParseObject> friends, ParseException e) {
							if(e != null){
								Log.e("Unable to retrieve Friends List", e.toString(), e);
							}
							else{
								for (ParseObject friend : friends) {
									friendIds.add(friend.getObjectId());
								}
								try {
									saveAssoc(userId, friendIds, activityId, activity.getType(), null);
								} catch (ParseException pe) {
									Log.d(TAG, "Unable to save association of users and activity", pe);
								}
								callback.done(e);
								
							}
							
						}
					};
				
					getFriends(friendCallback);
			    }
				else {
					Log.e(TAG, "Error saving activity", pe);
				}
		}
	};
		
		addActivity(activity, activitySaveCallback);
		
		
	}
	
	
	
	
	public final static void getActivityList(List<Assoc> assocs, FindCallback<ParseObject> callback, String activityType){
		ParseQuery<ParseObject> query = null;
		
		if(activityType.equals("Coffee"))
			query = ParseQuery.getQuery("CoffeeActivity");
		
		List<String> activityIds = new ArrayList<String>();
		
		for (Assoc assoc : assocs) {
			activityIds.add(assoc.getActivityId());
		}
		
		query.whereContainedIn("activityId", activityIds);
		query.findInBackground(callback);
		
		
		
	}
	
	public final static void getActivityList(ParseUser user, final FindCallback<ParseObject> callback, final String activityType){
		final List<Assoc> assocs = new ArrayList<Assoc>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Assoc");
		query.whereEqualTo("friendId", user.getObjectId());
		query.whereEqualTo("activityType", activityType);
		
		query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> aList, ParseException e) {
			    	
			        if (e == null) {
			            Log.d("Assocs List", "Retrieved " + aList.size());
			            for (ParseObject parseObject : aList) {
							Assoc assoc = (Assoc) parseObject;
							assocs.add(assoc);
							
						}
			            getActivityList(assocs, callback, activityType);
			        } else {
			            Log.d("Assocs List", "Error: " + e.getMessage());
			        }
			    }
			});
		
		
		
		
		
	}
	

}
