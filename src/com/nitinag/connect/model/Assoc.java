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

	public ConnectUser getFrom(){
		return (ConnectUser) get("from");
	}
	
	public void setFrom(ConnectUser user){
		put("from", user);
	}

	public ConnectUser getFor(){
		return (ConnectUser) get("for");
	}
	
	public void setFor(ConnectUser user){
		put("for", user);
	}
	
	public ConnectActivity getActivity() {
		return (ConnectActivity) get("activity");
	}

	public void setActivity(ConnectActivity activity) {
		put("activity", activity);
	}

}
