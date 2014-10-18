package com.nitinag.connect.model;

import com.nitinag.connect.utils.Constants;
import com.parse.ParseClassName;

@ParseClassName("DinnerActivity")
public class DinnerActivity extends ConnectActivity{

	@Override
	public String getType() {
		return Constants.DINNER;
	}


}
