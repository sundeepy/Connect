package com.nitinag.connect;

import android.content.Context;
import android.content.SharedPreferences;

import com.nitinag.connect.model.AnywallPost;
import com.nitinag.connect.model.Assoc;
import com.nitinag.connect.model.CoffeeActivity;
import com.nitinag.connect.model.ConnectActivity;
import com.nitinag.connect.model.ConnectUser;
import com.nitinag.connect.model.DinnerActivity;
import com.nitinag.connect.model.LunchActivity;
import com.nitinag.connect.model.RideActivity;
import com.parse.Parse;
import com.parse.ParseObject;

public class Application extends android.app.Application {
  // Debugging switch
  public static final boolean APPDEBUG = false;

  // Debugging tag for the application
  public static final String APPTAG = "Connect";

  // Used to pass location from MainActivity to PostActivity
  public static final String INTENT_EXTRA_LOCATION = "location";

  // Key for saving the search distance preference
  private static final String KEY_SEARCH_DISTANCE = "searchDistance";

  private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;

private static final String PARSE_APPLICATION_ID = "nh7qch34SMCs00ySmhBEuZWczFnpfbDp0WYomSY6";

private static final String PARSE_CLIENT_KEY = "ED4eq5TDpyOVQjBWJQ6SX2t6pxA2lH4W9SSptOMD";

  private static SharedPreferences preferences;

  private static ConfigHelper configHelper;

  public Application() {
  }

  private void registerModels(){
	  ParseObject.registerSubclass(ConnectUser.class);
	  ParseObject.registerSubclass(AnywallPost.class);
	  ParseObject.registerSubclass(ConnectActivity.class);
	  ParseObject.registerSubclass(CoffeeActivity.class);
	  ParseObject.registerSubclass(LunchActivity.class);
	  ParseObject.registerSubclass(RideActivity.class);
	  ParseObject.registerSubclass(DinnerActivity.class);
	  ParseObject.registerSubclass(Assoc.class);
	  
  }
  
  @Override
  public void onCreate() {
    super.onCreate();

    registerModels();
    
    Parse.initialize(this, PARSE_APPLICATION_ID,
        PARSE_CLIENT_KEY);

    preferences = getSharedPreferences("com.nitinag.connect", Context.MODE_PRIVATE);

    configHelper = new ConfigHelper();
    configHelper.fetchConfigIfNeeded();
  }

  public static float getSearchDistance() {
    return preferences.getFloat(KEY_SEARCH_DISTANCE, DEFAULT_SEARCH_DISTANCE);
  }

  public static ConfigHelper getConfigHelper() {
    return configHelper;
  }

  public static void setSearchDistance(float value) {
    preferences.edit().putFloat(KEY_SEARCH_DISTANCE, value).commit();
  }

}
