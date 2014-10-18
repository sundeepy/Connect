package com.nitinag.connect.model;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

@ParseClassName("ConnectUser")
public class ConnectUser extends ParseObject{

	public String getUserId(){
		return getString("userId");
	}
	
	public void setUserId(String userId){
		put("userId", userId);
	}
	
	public String getFirstName(){
		return getString("fname");
	}
	
	public String getLastName(){
		return getString("lname");
	}
	
	public void setFirstName(String fname){
		put("fname", fname);
	}
	
	public void setLastName(String lname){
		put("lname", lname);
	}
	
	public void addFriend(ConnectUser friend, SaveCallback callback){
		ParseRelation<ConnectUser> relation = getRelation("friends");
		relation.add(friend);
		saveInBackground(callback);
	}
	
	public void setFriends(List<ParseObject> friends, SaveCallback callback){
		ParseRelation<ParseObject> relation = getRelation("friends");
		for (ParseObject friend : friends) {
			relation.add(friend);
		}
		saveInBackground(callback);
	}
	
	public void getFriends(FindCallback<ParseObject> callback){
		ParseRelation<ParseObject> relation = getRelation("friends");
		ParseQuery<ParseObject> query = relation.getQuery();
		query.findInBackground(callback);
	}
	
	 public ParseFile getPhotoFile() {
	        return getParseFile("photo");
	 }
	 
	 public void setPhotoFile(ParseFile file) {
	        put("photo", file);
	 }
	

}
