package com.nitinag.connect.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Assoc")
public class Assoc extends ParseObject{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getUserId() {
		return getString("userId");
	}

	public void setUserId(String userId) {
		put("userId", userId);
	}

	public String getActivityId() {
		return getString("activityId");
	}

	public void setActivityId(String activityId) {
		put("activityId", activityId);
	}

	public String getFriendId() {
		return getString("friendId");
	}

	public void setFriendId(String friendId) {
		put("friendId", friendId);
	}

	public String getActivityType() {
		return getString("activityType");
	}

	public void setActivityType(String activityType) {
		put("activityType", activityType);
	}

}
