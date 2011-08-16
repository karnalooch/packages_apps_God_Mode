package com.t3hh4xx0r.addons.omgb;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.net.Uri;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.widget.Toast;

import com.t3hh4xx0r.addons.utils.Constants;
import com.t3hh4xx0r.addons.utils.Downloads;

import com.t3hh4xx0r.R;

public class OnOMGBPreferenceClickListener implements OnPreferenceClickListener {
	
	private final String TAG = "OnOMGBPreferenceClickListener";

	private boolean DBG = (false || Constants.FULL_DBG);
	OMGBObject mOMGB;
	int mPosition;
	Context mContext;
	private String externalStorageDir = "/mnt/sdcard/t3hh4xx0r/downloads";
	private String DOWNLOAD_DIR = externalStorageDir+ "/";
	public static String DATE = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(new Date());

	public OnOMGBPreferenceClickListener(OMGBObject o, int position, Context context) {	
		mOMGB = o;
		mPosition = position;
		mContext = context;
	}
	
	@Override
	public boolean onPreferenceClick(Preference v) {
		
 		Log.d(TAG, v.getSummary().toString()  );
 		Log.d(TAG, v.getTitle().toString()  );

 		File check =  new File(externalStorageDir+ "/" + mOMGB.getZipName());

 		if(!check.exists()){	
 			DownloadManager dman = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
 		
 		    File f = new File(externalStorageDir);
 		    if(!f.exists()){
 			    f.mkdirs();
 			    Log.i(TAG, "File diretory does not exist, creating it");
 		    }
 		    f = null;
 		    f = new File(externalStorageDir+ "/" + mOMGB.getZipName());
 		
 	    	Uri down = Uri.parse(mOMGB.getURL());	
 		    DownloadManager.Request req = new DownloadManager.Request(down);
 		    req.setShowRunningNotification(true);
 		    req.setVisibleInDownloadsUi(false);
 		    req.setDestinationUri(Uri.fromFile(f));
 		
 		    dman.enqueue(req);
 		}
 		else{
 		    check = null;
 			Log.d(TAG, "About to choose flash options");
 	 		FlashAlertBox(mContext.getString(R.string.choose_flash_options), Boolean.parseBoolean(mOMGB.getInstallable()), mOMGB.getZipName());
 		}
		return false;
	}
	
	protected void FlashAlertBox(String title, final boolean Installable, final String OUTPUT_NAME) {
	    final CharSequence[] items = {mContext.getString(R.string.backup_rom), mContext.getString(R.string.wipe_data),
		mContext.getString(R.string.wipe_cache), mContext.getString(R.string.google_app_installation)}; // Should turn the into calls to R.String.~~~
        final boolean checked[] = new boolean[]{false, false, true, false};

        new AlertDialog.Builder(mContext)
	        .setTitle(title)
	        .setCancelable(true)
            .setMultiChoiceItems(items, checked, new OnMultiChoiceClickListener() {
			    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    File gapps =  new File(Constants.DOWNLOAD_DIR + Constants.GOOGLE_APPS);
				    if (checked[3] == true && !gapps.exists()) {
					    Toast.makeText(mContext, mContext.getString(R.string.gapps_not_downloaded), Toast.LENGTH_SHORT).show();
					checked[3] = false;
				    }
				    log( "Item number " + which + " and is set to: " + Boolean.toString(isChecked));
				}
			})
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	        	 Thread FlashThread = new Thread() {
	            		@Override
	            	    public void	run(){
	            			File f = new File (DOWNLOAD_DIR + OUTPUT_NAME);
	            			if(f.exists()) {
	            				log( "User approved flashing, begining flash. Installable = " + String.valueOf(Installable));
		  				  		Log.i(TAG, "File location is: "+ f.toString());
		  						if (Installable) {
		  						   Downloads.installPackage(OUTPUT_NAME, mContext );
		  						} else {
		  							log("About to flash package");
		  							Downloads.flashPackage(OUTPUT_NAME, checked[0], checked[1], checked[2], checked[3]);
		  						    
		  						}
	            			} 
	            	  }
	            };
	            FlashThread.run();
	        }
	    })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton){
	        	// Do nothing
	        	log("User did not approve flashing.");
	        	log( "Backup: "+ checked[0]+ " WipeData: "+ checked[1] + " WipeCache: "+ checked[2] +" InstallGoogle: "+ checked[3]);
	        }
	    })  
	    .show();
	}
    
    private void log(String message) {	   
		   if(DBG)Log.d(TAG, message);	   
	}
}  