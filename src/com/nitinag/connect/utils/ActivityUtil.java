package com.nitinag.connect.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.nitinag.connect.model.ConnectActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ActivityUtil { 

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
	
	public final static void getActivityList(ParseUser user, FindCallback<ParseObject> callback){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Assoc");
		query.whereEqualTo("friendId", user.getObjectId());
		if(callback == null){
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> activities, ParseException e) {
			        if (e == null) {
			            Log.d("Activity List", "Retrieved " + activities.size() + " scores");
			        } else {
			            Log.d("Activity List", "Error: " + e.getMessage());
			        }
			    }
			});
		}
		else query.findInBackground(callback);
	}
	

}
