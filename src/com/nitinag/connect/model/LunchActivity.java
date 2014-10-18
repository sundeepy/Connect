package com.nitinag.connect.model;

import com.nitinag.connect.utils.Constants;
import com.parse.ParseClassName;

@ParseClassName("LunchActivity")
public class LunchActivity extends ConnectActivity{

	@Override
	public String getType() {
		return Constants.LUNCH;
	}


}
