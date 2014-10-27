package com.nitinag.connect.utils;

import java.util.HashMap;
import java.util.Map;

import android.R;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nitinag.connect.model.ConnectActivity;

public class UpdateActivityThread implements Runnable{
	private static final String TAG = "UpdateActivityThread";
	private Handler handler;
	boolean suspended = false;
	boolean started = false;
	private Map<ConnectActivity, View> progressMap;
	
	public UpdateActivityThread(Handler handler, Map<ConnectActivity, View> pMap) {
		this.handler = handler;
		this.progressMap = pMap;
		
	}
	
	public boolean isStarted(){
		return this.started;
	}

	public void suspend() {
	      suspended = true;
	   }
	public synchronized void resume() {
	      suspended = false;
	       notify();
	   }
	@Override
	public void run() {
		boolean notDone = true;
		started = true;
		while(notDone) {
			final Map<View, String> remainMap = new HashMap<View, String>();
			Log.d(TAG, "Number of activities " + this.progressMap.size());
			for(ConnectActivity c : this.progressMap.keySet()){
				
				long expiration = c.getDefaultExpiration().getTime();
				long current = System.currentTimeMillis();
				if( current < expiration){
					long remaining = expiration - current;
					long hours = remaining / 3600000;
					long mins = remaining / 60000 % 60;
					remainMap.put(this.progressMap.get(c), String.format("%02d hrs %02d mins", hours, mins));
				}
				else{
					c.expired(true);
					remainMap.put(this.progressMap.get(c), "Expired");
				}
			}
			
			
			
			// Update the progress 
            this.handler.post(new Runnable() {
                public void run() {
                	Log.d(TAG, "Number of progress textview " + UpdateActivityThread.this.progressMap.size());
                	for(View v : remainMap.keySet()){
                		
                		TextView tv = (TextView)v;
                		String currentState = remainMap.get(v);
                		if("Expired".equals(currentState))
                			tv.setTextColor(Color.RED);
                		else
                			tv.setTextColor(Color.GREEN);
                		tv.setText(currentState);
                		tv.invalidate();
                	}
                }
            });
            
            try {
				Thread.sleep(500);
				synchronized(this) {
		            while(suspended) {
		               wait();
		            }
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	
}
