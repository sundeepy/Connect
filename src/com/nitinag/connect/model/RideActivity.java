package com.nitinag.connect.model;

import com.nitinag.connect.utils.Constants;
import com.parse.ParseClassName;

@ParseClassName("RideActivity")
public class RideActivity extends ConnectActivity{

	@Override
	public String getType() {
		return Constants.RIDE;
	}

}
