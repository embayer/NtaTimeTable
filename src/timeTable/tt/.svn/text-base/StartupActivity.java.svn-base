package timeTable.tt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class StartupActivity extends Activity {

	 public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
        String ausbildung = Prefs.getAusbildungPref(getApplicationContext());
        
        cleanup();
        
        copyFile("default.css");
        copyFile("err.html");
        
        Intent intent;
        
    	if(!isOnline())
    	{
   	         AlertDialog.Builder back = new AlertDialog.Builder(this);
   	         back.setTitle("Keine Internetverbindung");
   	         back.setMessage("Das Programm wird beendet.");
   	          
   	         back.setPositiveButton("ok", new DialogInterface.OnClickListener() {
   	
   	             public void onClick(DialogInterface dialog, int whichButton) {
   	            	 finish();
   	             
   	             }
   	
   	         }); 
   	         
   	         back.show();
    	}
    	else
    	{
	        if(ausbildung.equals("0")) {
	        	intent = new Intent(StartupActivity.this, MenuActivity.class);
	        	StartupActivity.this.startActivity(intent);
	        }
	        else {
	        		 intent = new Intent(StartupActivity.this, DisplayTableActivity.class);
	        		 StartupActivity.this.startActivity(intent);       	
	        }
	        this.finish();	    		
    	}


	 }
	 
	 /**
	  * Prüft ob eine Internetverbindung besteht
	  * @return boolean
	  */
	 public boolean isOnline() {
		    ConnectivityManager cm =
		        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
		}
	 
	 
	 void cleanup()
	 {
		 try
		 {
			 File dir = getFilesDir();
			 if (dir.isDirectory()) {
			         String[] children = dir.list();
			         for (int i = 0; i < children.length; i++) {
			             new File(dir, children[i]).delete();
			         }
			 }
		 }
		 catch (Exception e) 
		 {
			 
		 }
	 }
	 
	 /**
	  * Kopiert eine Datei aus dem Assets-Ordner in den internen Speicher der für die App vorgesehen ist
	  * \/Data/Data/Appname/files/
	  * @param filename Ein Dateiname aus /Assets/
	  */
	    private void copyFile(String filename) {
	        AssetManager assetManager = this.getAssets();  
	        InputStream in;
	        File file = getFileStreamPath(filename);
	        
	        if(!file.exists()) {
				try {
					in = assetManager.open(filename); 
					FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
		             
			        byte[] buffer = new byte[1024];
			        int read;
			        while ((read = in.read(buffer)) != -1) {
			            fos.write(buffer, 0, read);
			        }        
			        
			        fos.close();
			        fos = null;
			        in.close();
			        in = null;
					} 
				catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	    }
}
