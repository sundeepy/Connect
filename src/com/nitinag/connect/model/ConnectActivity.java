package com.nitinag.connect.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;

@ParseClassName("ConnectActivity")
public abstract class ConnectActivity extends ParseObject{
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
	

	public ConnectUser getUser() {
		return (ConnectUser) get("user");
	}
	
	@Override
	public int hashCode() {
		return getObjectId().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		return getObjectId().equals(o);
	}
	
	public void setFor(List<ConnectUser> users, SaveCallback callback){
		ParseRelation<ParseObject> relation = getRelation("for");
		for (ConnectUser connectUser : users) {
			relation.add(connectUser);
		}
		saveInBackground(callback);
	}

	public void setUser(ConnectUser value) {
		put("user", value);
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
	
	public void expired(boolean e){
		put("expired", e);
	}
	
	public String getMessage(){
		return getString("message");
	}
	
	public void setMessage(String message){
		put("message", message);
	}
	
	public abstract String getType();
	public static ParseQuery<ConnectActivity> getQuery(){
		ParseQuery<ConnectActivity> query = ParseQuery.getQuery(ConnectActivity.class);
		return query;
	}
	

	public String getRelativeTimeAgo() {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(getCreatedAt().toString()).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	public Date getExpiration(){
		return getDate("expiration");
	}
	
	public void setExpiration(Date d){
		put("expiration", d);
	}
	
	public Date getDefaultExpiration(){
		return new Date(getCreatedAt().getTime() + (30 * ONE_MINUTE_IN_MILLIS));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
