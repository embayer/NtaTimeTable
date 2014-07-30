package timeTable.tt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

public class DisplayTableActivity extends Activity  {

	int clicks = 0;
	Date date;	
	WebView web;
	TimeTable tt;
	Button back;
	Button fwd;
	ProgressDialog pd;
	
	 public void onCreate(Bundle savedInstanceState) {		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.table); 
	        

	        if(date == null)
	        	date = new Date();
	        

				
				back = (Button) findViewById(R.id.backbutton);
				fwd = (Button) findViewById(R.id.forwardbutton);	        
		        web = (WebView) findViewById(R.id.vertWebView);
		        
		        back.setEnabled(false);			        
 
	        	pd = new ProgressDialog(DisplayTableActivity.this);
		        pd.setMessage("Lade..");
		        pd.show();    		        
		        Thread t = new Thread(new Runnable() {
					
					public void run() 
					{					

						tt = new TimeTable(Prefs.getAusbildungPref(getApplicationContext()), 
									Prefs.getSemesterPref(getApplicationContext())); 
						
						

				        
						if(!tt.empty) 
				        {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
				        	while(!writeFile(tt.listRegulaer))
				        	{
				        		calendar.add(Calendar.DATE, 1);
				        		date = calendar.getTime();
				        	}
				        	runOnUiThread(new Runnable() {
				        	     public void run() 
				        	     {
									back.setEnabled(false);	
									web.loadUrl("file://"+getFileStreamPath(getFilename(date)).getPath());
				        	    }
				        	});	
								        
				        }
						else
						{
							runOnUiThread(new Runnable() {
							     public void run() 
							     {
							    	fwd.setEnabled(false);
									web.loadUrl("file://"+getFileStreamPath("err.html").getPath());

							    }
							});
						}
						pd.dismiss();
					}
				});
		        t.start(); 
			
	 }
	 


	 
	 	 /**
	  *  Optionmenu erstellen
	  */
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.menu, menu);
	     return true;
	 }
 
	 /**
	  * Dateinamen anhand des Datums erstellen. Bsp (812506.html -> ausbildung=8, Semester=1, Datum=25.06)
	  * @param date 
	  * @return Dateiname
	  */
	public String getFilename(Date date)
	{
		return Prefs.getAusbildungPref(getApplicationContext()) + 
				Prefs.getSemesterPref(getApplicationContext()) +
				date.getDate() + (date.getMonth()+1) +".html";
	}
	 
	 
	 private boolean writeFile( ArrayList<Entry> list) 
	 {
		 
		 if(!getFileStreamPath(getFilename(date)).exists())
		 {
			 String doc=null;
			 FileOutputStream fos;
			 OutputStreamWriter osw;
			 generateHtmlDay html = new generateHtmlDay(list);
			 
				 try {
					 doc = html.buildDocumentString(date);	
					 if(doc != null) {
						 fos = openFileOutput(getFilename(date), Context.MODE_PRIVATE);
						 osw = new OutputStreamWriter(fos);
						 osw.write(doc);
						 osw.flush();
						 osw.close();
						 return true;
					 }

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			 return false;
		 }
		 return true;
	 }
	 
	 
	 /**
	  *  Auf Optionmenuclick reagieren
	  */
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	     
	         case R.id.neuwahl:     Intent intent = new Intent(DisplayTableActivity.this, MenuActivity.class);
	        						DisplayTableActivity.this.startActivity(intent);
									this.finish();
							
	         break;
	         
	     }
	     return true;
	 }
	 

	 
	 /**
	  * Auf Back(Hardware)-Button abfragen 
	  */
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK)) {     	 

	       	
	       	 
	   	         AlertDialog.Builder back = new AlertDialog.Builder(this);
	   	         back.setTitle("Beenden?");
	   	         back.setMessage("Möchten Sie das Programm wirklich beenden?");
	   	          
	   	         back.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
	   	
	   	             public void onClick(DialogInterface dialog, int whichButton) {
	   	            	 finish();
	   	              
	   	             }
	   	
	   	         });
	   	
	   	         back.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
	   	
	   	             public void onClick(DialogInterface dialog, int whichButton) {
	   	             }
	   	
	   	         });
	   	
	   	         back.show();
	        }
	        return false; 
	    }


		
	 
	    public void ButtonClicked(View view) {
	        
        	int id = view.getId();
        	
        	if(id == R.id.backbutton) {
        		
        		back.setEnabled(false);
	        	 Calendar c = Calendar.getInstance();
	        	 c.setTime(date);
	        	 c.add(Calendar.DATE, -1);
	             File f = getFileStreamPath(getFilename(c.getTime()));
	             int i = 0;
	             while(!f.exists() && clicks != 0)
	             {
	            	 c.add(Calendar.DATE, -1);
	            	 f = getFileStreamPath(getFilename(c.getTime()));	
	            	 i++;
	             }	    
	             
	             web.loadUrl("file://"+getFileStreamPath(getFilename(c.getTime())).getPath());
            	 date = c.getTime();
	             clicks--;
	             
	             if(clicks == 0)
	            	 back.setEnabled(false);
	             else
	            	 back.setEnabled(true);
	             fwd.setEnabled(true);
        	}

        	if(id == R.id.forwardbutton) 
        	{
        		 back.setEnabled(true);
        		 fwd.setEnabled(false);
	          	 Calendar c = Calendar.getInstance();
	        	 c.setTime(date);
	        	 c.add(Calendar.DATE, 1);
	             File f = getFileStreamPath(getFilename(c.getTime()));
	             date = c.getTime();
	             if(f.exists())
	             {	            	 
	            	 //
	             }	          		
	             else 
	             {
	            	 
	            	 int i = 0;
	            	 
	            	 while(!writeFile(tt.listRegulaer))
	            	 {
	            		 if(i==5)
	            		 {
	            			 c.add(Calendar.DATE, -6);
	            			 date = c.getTime();
	            			 fwd.setEnabled(false);
	            		 	 return;
	            		 }
	            		 c.add(Calendar.DATE, 1);
	            		 date = c.getTime();
	            		 i++;
	            	 }
	            	 
	             }
	             web.loadUrl("file://"+getFileStreamPath(getFilename(c.getTime())).getPath());	             
	             if(++clicks == 8)
	            	 fwd.setEnabled(false);
	             else
	            	 fwd.setEnabled(true);
        	}
	    }     
	
}




