package com.nitinag.connect.utils;

import com.nitinag.connect.model.ConnectActivity;
import com.todddavies.components.progressbar.ProgressWheel;

import android.os.AsyncTask;

public class UpdateActivityTask extends AsyncTask<ConnectActivity, Void, Integer[]>{
	
	private ProgressWheel[] wheels;

	public UpdateActivityTask(ProgressWheel[] wheels) {
		this.wheels = wheels;
	}
	
	@Override
	protected Integer[] doInBackground(ConnectActivity... activities) {
		
		
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	protected void onPostExecute(Integer[] result) {
		
	}

	

}
