package com.nitinag.connect.activity;

import com.nitinag.connect.utils.PopulateView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class ActivityDialogFragment extends DialogFragment {
	
		private String activityType;

		public ActivityDialogFragment(String activityType) {
			this.activityType = activityType;
		}
	    
	    /* The activity that creates an instance of this dialog fragment must
	     * implement this interface in order to receive event callbacks.
	     * Each method passes the DialogFragment in case the host needs to query it. */
	    public interface ActivityDialogListener {
	        public void onDialogPositiveClick(DialogFragment dialog, String activityType);
	        public void onDialogNegativeClick(DialogFragment dialog, String activityType);
	    }
	    
	    // Use this instance of the interface to deliver action events
	    ActivityDialogListener mListener;
		private EditText activityMessage;
		private PopulateView mPopulate;
	    
	    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        // Verify that the host activity implements the callback interface
	        try {
	            // Instantiate the NoticeDialogListener so we can send events to the host
	            mListener = (ActivityDialogListener) activity;
	            
	            //Instantiate the PopulateView so we can send events to the host
	            mPopulate = (PopulateView) activity;
	        } catch (ClassCastException e) {
	            // The activity doesn't implement the interface, throw exception
	            throw new ClassCastException(activity.toString()
	                    + " must implement ActivityDialogListener");
	        }
	   }
	    
	    
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Get the layout inflater
	        LayoutInflater inflater = getActivity().getLayoutInflater();

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        /*
	        View v = inflater.inflate(R.layout.create_activity_dialog, null);
	        ImageView ivActivity = (ImageView) v.findViewById(R.id.ivActivity);
	        ivActivity.setImageDrawable(getResources().getDrawable(R.drawable.ic_coffee));
	        setActivityMessage((EditText)v.findViewById(R.id.activity_message));
	        */
	        View v = mPopulate.populate(inflater, activityType);
	        setActivityMessage((EditText)v.findViewById(R.id.activity_message));
	        builder.setView(v)
	        // Add action buttons
	               .setPositiveButton(R.string.create_activity, new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int id) {
	                       if(mListener != null)
	                    	   mListener.onDialogPositiveClick(ActivityDialogFragment.this, activityType);
	                   }
	               })
	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       ActivityDialogFragment.this.getDialog().cancel();
	                   }
	               });      
	        return builder.create();
	    
	    }


		public EditText getActivityMessage() {
			return activityMessage;
		}


		public void setActivityMessage(EditText activityMessage) {
			this.activityMessage = activityMessage;
		}
	    
	
}