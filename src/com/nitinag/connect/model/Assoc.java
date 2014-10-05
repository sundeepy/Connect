package com.nitinag.connect.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Assoc")
public class Assoc extends ParseObject{

	private String userId;
	private String activityId;
	private String friendId;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

}
