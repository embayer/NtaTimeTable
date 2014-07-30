package timeTable.tt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

// TODO ungerade/gerade semester wählen
// table activity wenn defaultwerte bei preferences


public class MenuActivity extends Activity {
    /** Called when the activity is first created. */   
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Button Button4;
    private Button Button5;
    String BIOTA = "1";
    String CTA = "2";
    String PHYTA = "3";
    String AIK = "4";
    String PTA = "5";
    String VORSEMESTER = "8";
    String BACH_CHEM = "21";
    String BACH_PHCHEM = "22";
    String BACH_INFO = "23";
    String BACH_PHYS = "25";
    boolean isBkChosen=false;
    boolean isSummer=false;
    Animation fadeIn = null;
    Animation fadeOut = null;
    
	Intent intent;
	

    
    int action=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);   

        
        intent = new Intent(MenuActivity.this, DisplayTableActivity.class);
        
    	fadeIn = new AlphaAnimation(0, 1);
    	fadeIn.setDuration(400);
    	fadeIn.setStartOffset(400);
    	fadeIn.setFillAfter(true);
    	        
        Button1 = (Button)findViewById(R.id.button1);        
        Button2 = (Button)findViewById(R.id.button2);        
        Button3 = (Button)findViewById(R.id.button3);
        Button4 = (Button)findViewById(R.id.button4);
        Button5 = (Button)findViewById(R.id.button5);   
        
        
    }
    


    public void sequenceOne() {
    	
    	Button1.setText("Berufskolleg");
    	Button2.setText("Hochschule");
    	Button3.setText("Vorsemester");   
    	
    	Button1.startAnimation(fadeIn);
    	Button2.startAnimation(fadeIn);
    	Button3.startAnimation(fadeIn);
    	
    	Button1.setEnabled(true);
    	Button2.setEnabled(true);
    	Button3.setEnabled(true);
    	Button4.setEnabled(false);
    	Button5.setEnabled(false);
    	Button4.setVisibility(View.INVISIBLE);
    	Button4.clearAnimation();
    	Button5.setVisibility(View.INVISIBLE);
    	Button5.clearAnimation();
    }
    
    public void sequenceTwoBK() {
    	Button1.setText("AIK");
    	Button2.setText("BioTA");
    	Button3.setText("CTA");
    	Button4.setText("PhyTA");
    	Button5.setText("PTA");
    	
    	Button1.setEnabled(true);
    	Button2.setEnabled(true);
    	Button3.setEnabled(true);
    	Button4.setEnabled(true);
    	Button5.setEnabled(true);
    	
    	Button1.startAnimation(fadeIn);
    	Button2.startAnimation(fadeIn);
    	Button3.startAnimation(fadeIn);
    	Button4.startAnimation(fadeIn);
    	Button5.startAnimation(fadeIn);
    	
    }
    
    public void sequenceTwoHS() {
    	Button1.setText("Chemie");
    	Button2.setText("Informatik");
    	Button3.setText("Pharm. Chemie");
    	Button4.setText("Physik");
    	
    	Button1.setEnabled(true);
    	Button2.setEnabled(true);
    	Button3.setEnabled(true);
    	Button4.setEnabled(true);
    	Button5.setEnabled(false);    	
    	Button5.setVisibility(View.INVISIBLE);
    	Button5.clearAnimation();
    	
    	Button1.startAnimation(fadeIn);
    	Button2.startAnimation(fadeIn);
    	Button3.startAnimation(fadeIn);
    	Button4.startAnimation(fadeIn);    	
    }
    
    public void sequenceThreeBK() {
    	Button1.setText("1. Semester");  		// ############## Semester ???
    	Button2.setText("2. Semester");
    	Button3.setText("3. Semester");  		
    	Button4.setText("4. Semester");    	
    	
    	
    	Button1.setEnabled(true);
    	Button2.setEnabled(true);
    	Button3.setEnabled(true);
    	Button4.setEnabled(true);
    	Button5.setEnabled(false);


    	Button5.setVisibility(View.INVISIBLE);
    	//Button3.clearAnimation();
    	//Button4.clearAnimation();
    	Button5.clearAnimation();
    	
    	Button1.startAnimation(fadeIn);
    	Button2.startAnimation(fadeIn);    	
    	Button3.startAnimation(fadeIn);
    	Button4.startAnimation(fadeIn);
    }
    
    public void sequenceThreeHS() {
    	Button1.setText("Sommersemester");
    	Button2.setText("Wintersemester");
    	
    	Button1.setEnabled(true);
    	Button2.setEnabled(true);
    	Button3.setEnabled(false);
    	Button4.setEnabled(false);
    	Button5.setEnabled(false);  
    	    	
    	Button3.setVisibility(View.INVISIBLE); 
    	Button4.setVisibility(View.INVISIBLE);
    	Button5.setVisibility(View.INVISIBLE); 
    	Button3.clearAnimation();
    	Button4.clearAnimation();
    	Button5.clearAnimation();
    	
    	Button1.startAnimation(fadeIn);
    	Button2.startAnimation(fadeIn);
    }
    
    public void sequenceFourHS() {
    	if(isSummer){
	    	Button1.setText("2. Semester");                 
	    	Button2.setText("4. Semester");					
	    	Button3.setText("6. Semester");    		
    	} 
    	else {
	    	Button1.setText("1. Semester");                 
	    	Button2.setText("3. Semester");					
	    	Button3.setText("5. Semester"); 
    	}

    	
    	Button1.setEnabled(true);
    	Button2.setEnabled(true);
    	Button3.setEnabled(true);
    	Button4.setEnabled(false);
    	Button5.setEnabled(false);
    	Button4.setVisibility(View.INVISIBLE);
    	Button5.setVisibility(View.INVISIBLE); 
    	Button4.clearAnimation();
    	Button5.clearAnimation();
    	
    	Button1.startAnimation(fadeIn);
    	Button2.startAnimation(fadeIn);
    	Button3.startAnimation(fadeIn);
    	
    }

    public void ButtonClicked(View view) {
      
        	int id = view.getId();
        	
        	// Aktion/Animation für 1 Button
			if(id == R.id.button1) {
				
				
				if(action==0) {
					isBkChosen = true;
					sequenceTwoBK();				
				}
				if(action==1) {			
					if(isBkChosen) {
						Prefs.setAusbildungPref(getApplicationContext(), AIK);
						sequenceThreeBK();
					}
					else {
						Prefs.setAusbildungPref(getApplicationContext(),  BACH_CHEM);
						sequenceThreeHS();
					}
					
				}
				if(action==2) {
					if(isBkChosen) {
						Prefs.setSemesterPref(getApplicationContext(),  "1");
						MenuActivity.this.startActivity(intent);
						this.finish();
					}
					else {
						isSummer = true;
						sequenceFourHS();
					}					

				}
				
				if(action==3) {
					if(isSummer) 
						Prefs.setSemesterPref(getApplicationContext(),  "2");
					else
						Prefs.setSemesterPref(getApplicationContext(),  "1");   //winter ungerade HS
					
					
					MenuActivity.this.startActivity(intent);
					this.finish();
				}
				
				
				action++;
			}
  
        
			// Aktion/Animation für 2 Button
			if(id == R.id.button2) {
				if(action==0) {	
					isBkChosen = false;			
					sequenceTwoHS();				
				}				
				if(action==1) {				
					if(isBkChosen) {
						Prefs.setAusbildungPref(getApplicationContext(),  BIOTA); 
						sequenceThreeBK();
					}
					else {
						Prefs.setAusbildungPref(getApplicationContext(),  BACH_INFO); 						
						sequenceThreeHS();
					}
					
				}
				if(action==2){
					if(isBkChosen) {
						Prefs.setSemesterPref(getApplicationContext(),  "2");
						MenuActivity.this.startActivity(intent);
						this.finish();
					}
					else {
						isSummer = false;
						sequenceFourHS();
					}					
				}
				
				if(action==3) {
					if(isSummer) 
						Prefs.setSemesterPref(getApplicationContext(),  "4");
					else
						Prefs.setSemesterPref(getApplicationContext(),  "3");   //winter ungerade HS
					
					MenuActivity.this.startActivity(intent);
					this.finish();
				}
				
				action++;
			}
			
			if(id == R.id.button3) {
				if(action==0) {
					Prefs.setAusbildungPref(getApplicationContext(),  VORSEMESTER);		
					Prefs.setSemesterPref(getApplicationContext(), "1");
					MenuActivity.this.startActivity(intent);
					this.finish();
				}
				if(action==1) {
					if(isBkChosen) {
						Prefs.setAusbildungPref(getApplicationContext(),  CTA);
						sequenceThreeBK();						
					}
					else {
						Prefs.setAusbildungPref(getApplicationContext(),  BACH_PHCHEM);
						sequenceThreeHS();
					}				
					
				}
				if(action==2)
				{
						if(isBkChosen)
						{
							Prefs.setSemesterPref(getApplicationContext(), "3");
							MenuActivity.this.startActivity(intent);
							this.finish();
						}
				}
					if(action==3) 
					{				
						
						if(isSummer) 
							Prefs.setSemesterPref(getApplicationContext(),  "6");
						else
							Prefs.setSemesterPref(getApplicationContext(),  "5");   //winter ungerade HS							
						

						
						MenuActivity.this.startActivity(intent);
						this.finish();
					}
				
				action++;
			}
        	
			if(id == R.id.button4) {
				if(action==2)
				{
					if(isBkChosen)
						Prefs.setSemesterPref(getApplicationContext(), "4");
						MenuActivity.this.startActivity(intent);
						this.finish();
				}
				else
				{
					if(isBkChosen) {
						Prefs.setAusbildungPref(getApplicationContext(),  PHYTA);
						sequenceThreeBK();
					}
					else {
						Prefs.setAusbildungPref(getApplicationContext(),  BACH_PHYS);
						sequenceThreeHS();
					}
				}

				
				action++;
			}
			
			if(id == R.id.button5) {
				Prefs.setAusbildungPref(getApplicationContext(),  PTA);
				
				sequenceThreeBK();
				action++;
			}
      
    }   
    
//    Buttonsequence
    
//action=0			1			2		     3
//  	BK  ->	  AIK		  1.Semester
//    			  BioTA	  ->  2.Semester	
//    	   		   CTA		
//    			  PhyTA
//    			   PTA
    
//    			  Chem         SS			1/2
//		FH  ->    Info		-> WS    ->		3/4
//    			  PHChem	   				5/6
//        		  Phys
//    	VS
//   
//    
    
   @Override
   
   /**
    * Auf Back(Hardware)-Button abfragen
    * Menu rückwärts durchlaufen, wenn auf Startseite angelangt -> Programm beenden?
    */
     public boolean onKeyDown(int keyCode, KeyEvent event) {
     if ((keyCode == KeyEvent.KEYCODE_BACK)) {     	 

    	
    	 if(action==0){
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
	    	 if(action==1){
	    		// findViewById(R.id.mainlayout).invalidate();
	    		 sequenceOne();
		    	 

		    	 Button4.setEnabled(false);
		    	 Button5.setEnabled(false);
		    	 
		    	 Button1.setEnabled(true);
		    	 Button2.setEnabled(true);
		    	 Button3.setEnabled(true);
		    	 action--;
	    	 }
	    	 if(action==2){
	    		 if(isBkChosen)
	    			 sequenceTwoBK();
	    		 else
	    			 sequenceTwoHS();
	    		 
	    		 action--;
	    	 }
	    	 if(action==3){
	    		 if(!isBkChosen) {
	    			 sequenceThreeHS();
	    			 action--;
	    		 }
	    	 }
     }
 //----------------------------------------------------------------
     return true;
 }
}
