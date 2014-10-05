package com.nitinag.connect.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("ConnectActivity")
public abstract class ConnectActivity extends ParseObject{

	public String getUserId() {
		return getString("userId");
	}

	public void setUserId(String value) {
		put("userId", value);
	}

	public ParseGeoPoint getLocation() {
		return getParseGeoPoint("location");
	}

	public void setLocation(ParseGeoPoint value) {
		put("location", value);
	}
	
	public boolean isExpired(){
		return getBoolean("expired");
	}
	
	public void expired(){
		put("expired", true);
	}
	
	public abstract String getType();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
