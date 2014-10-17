package com.nitinag.connect.utils;

import android.util.Log;

import com.nitinag.connect.model.AnywallPost;
import com.nitinag.connect.model.CoffeeActivity;
import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.model.ConnectUser;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ActivityUtilsTest extends android.app.Application{

	private static final String PARSE_APPLICATION_ID = "nh7qch34SMCs00ySmhBEuZWczFnpfbDp0WYomSY6";

	private static final String PARSE_CLIENT_KEY = "ED4eq5TDpyOVQjBWJQ6SX2t6pxA2lH4W9SSptOMD";
	
	/**
	 * @param args
	 */
	
	public ActivityUtilsTest() {
		registerModelsAndInitParse();
	}
	
	private void registerModelsAndInitParse(){
		  ParseObject.registerSubclass(AnywallPost.class);
		  ParseObject.registerSubclass(ConnectActivity.class);
		  ParseObject.registerSubclass(CoffeeActivity.class);
		  
		  Parse.initialize(this, PARSE_APPLICATION_ID,
			        PARSE_CLIENT_KEY);
		  
	  }
	
	public void addCoffeeActivity(ConnectUser user){

		CoffeeActivity coffee = new CoffeeActivity();
		coffee.setUser(user);
		
		
		SaveCallback callback = new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				Log.e("ActivityUtils", e.toString(), e);
				
			}
		};
		ActivityUtil.addActivity(coffee, callback);
	}
	
	public static void main(String[] args) {
		
	}

}
