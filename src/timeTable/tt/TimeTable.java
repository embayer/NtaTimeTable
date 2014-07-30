package timeTable.tt;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.content.SharedPreferences;

public class TimeTable {

	// ArrayListen um alle Eintraege zu speichen
	ArrayList<Entry> listRegulaer;
	ArrayList<Entry> listUnterrichtsausfall;
	ArrayList<Entry> listAenderung;
	ArrayList<Entry> listKlausur;
	ArrayList<Entry> listVeranstaltung;	
	
	String begin = "BEGIN:VEVENT";			// Start eines Blocks
	String end = "END:VEVENT";				// Ende eines Blocks
	String vend = "END:VCALENDAR";			// Ende eines Dokuments
	
	// Praefixe der Eintraege
	String description = "DESCRIPTION:";	
	String dtstart = "DTSTART:";				// "DTSTART:"
	String dtend = "DTEND:";					// "DTEND:"
	String location = "LOCATION:";			// existiert nicht immer
	String organizer = "ORGANIZER:";
	String summary = "SUMMARY:";
	
	String dtstartDate = "DTSTART;VALUE=DATE:";	// nur Date
	String dtendDate = "DTEND;VALUE=DATE:";		// nur Date
	String dtstartB = "DTSTART";				// ohne Doppelpunkt als Border 
	String dtendB = "DTEND";					// ohne Doppelpunkt als Border
	
	// Einstellungen fuer Ausbildung, Semester
	String prefAusbildung;
	String prefSemester;
	SharedPreferences settings;
	
	// gleichbleibender Teil jeder url (http://hvp.fh-isny.de/ical/ical_sp.php?ut=<unterrichtstyp>&ab=<ausbildung>&sm=<semester>)
	String urlBase = "http://hvp.fh-isny.de/ical/ical_sp.php?ut="; 

	// urls
	String urlRegulaer;
	String urlUnterrichtsausfall;
	String urlAenderung;
	String urlKlausur;
	String urlVeranstaltung;
	
	// ics-Rohdaten
	String rawRegulaer;
	String rawUnterrichtsausfall;
	String rawAenderung;
	String rawKlausur;
	String rawVeranstaltung;
	
	int REGULAER = 1;
	int AUSFALL = 2;
	int AENDERUNG = 3;
	int KLAUSUR = 4;
	int VERANSTALTUNG = 5;
	
	boolean empty;
	
	public TimeTable(String _prefAusbildung, String _prefSemester) {
		// Ausbildung und Semester aus Einstellungen lesen
		prefAusbildung = _prefAusbildung;		
		prefSemester = _prefSemester;			
		
		// urls fuer a,b,c,d,t bauen
		urlRegulaer = urlBase+"a&ab="+prefAusbildung+"&sm="+prefSemester;				// a = Regulaer
		urlUnterrichtsausfall = urlBase+"b&ab="+prefAusbildung+"&sm="+prefSemester;		// b = Unterrichtsausfall
		urlAenderung = urlBase+"c&ab="+prefAusbildung+"&sm="+prefSemester;				// c = Aenderung
		urlKlausur = urlBase+"d&ab="+prefAusbildung+"&sm="+prefSemester;				// d = Klausur
		urlVeranstaltung = urlBase+"t&ab="+prefAusbildung+"&sm="+prefSemester;			// t = Veranstaltung
		
		// ics-Rohdaten abholen
		rawRegulaer = getRawData(urlRegulaer);
		rawUnterrichtsausfall = getRawData(urlUnterrichtsausfall);
		rawAenderung = getRawData(urlAenderung);
		rawKlausur = getRawData(urlKlausur);
		rawVeranstaltung = getRawData(urlVeranstaltung);
		
		// Eintraege aus den ics-Rohdaten lesen
		listRegulaer = getEntries(rawRegulaer, REGULAER);
		listKlausur = getEntries(rawKlausur, KLAUSUR);
		
		if(listRegulaer.isEmpty() && listKlausur.isEmpty()) 
		{
			listRegulaer = null;
			empty = true;  // abbrechen wenn leer
			return;
		}

		
		listUnterrichtsausfall = getEntries(rawUnterrichtsausfall, AUSFALL);
		listAenderung = getEntries(rawAenderung, AENDERUNG);
		listVeranstaltung = getEntries(rawVeranstaltung, VERANSTALTUNG);
		
		// ArrayListen fuer die Typen von Rohdaten
		listRegulaer.addAll(listUnterrichtsausfall);
		listRegulaer.addAll(listAenderung);
		listRegulaer.addAll(listKlausur);
		listRegulaer.addAll(listVeranstaltung);
		
		// alle Listen in listRegulaer speichern und sortieren 
		//Collections.sort(listRegulaer); 
		
	}
	
	/**
	 * Laedt anhand der uebergebenen URL die Seite und gibt den Inhalt als java.lang.String zurück
	 * @param websiteUrl: Url zur Webseite, welchen den Content enthält
	 * @return In: Inhalt der Webseite als java.lang.String
	 */
	private String getRawData(final String websiteUrl) {
		HttpURLConnection urlConnection = null;
		
		try
		{
			final URL url = new URL(websiteUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			final InputStream result = new BufferedInputStream(urlConnection.getInputStream());
			
			return readStream(result);
		}
		catch (MalformedURLException e)
		{
//@			Log.e("MY_TAG", e.getMessage());
		}
		catch (IOException e)
		{
//@			Log.e("MY_TAG", e.getMessage());
		}
		finally
		{
			if( null != urlConnection ) {
				urlConnection.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * Hier wird ein beliebiger InputStream in einen String umgewandelt. Aufruf in getRawData()
	 * @param in InputStream, welcher die Daten zum Umwandeln enthält
	 * @return Die Daten aus dem InputStream als java.lang.String
	 * @throws IOException Exception, welchem beim Umwandeln auftreten kann
	 */
	private String readStream(final InputStream in) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(in), 4096);
		final StringBuilder result = new StringBuilder();
		
		String line;
		while ( (line = reader.readLine()) != null ) {
			result.append(line);
		}
		reader.close();
		
		return result.toString();
	}
	
	/**
	 * Liest aus den uebergebenen ics-Rohdaten die Eintraege aus und gibt sie als ArrayList zurueck
	 * @param ics-Rohdaten als java.lang.String
	 * @return Eintraege als ArrayList<Entry>
	 */
	private ArrayList<Entry> getEntries(String rawData, int entryType) {
		
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		
		// Strings um Eintragsdaten zwischenzuspeichern
		String descriptionData;
		String dtstartData;
		String dtendData;
		String locationData = "";
		String summaryData;

		if ( rawData.contains(begin) ) {	// Entries vorhanden?
			
			// linker und rechter Trenner der Eintragsdaten
			int leftBorder = 0;
			int rightBorder = 0;
			
			boolean done = false;
			while (done == false) {
				// description
				leftBorder = rawData.indexOf(description, rightBorder) +description.length();
				rightBorder = rawData.indexOf(dtstartB, leftBorder);
				descriptionData = rawData.substring(leftBorder, rightBorder);
				System.out.println(descriptionData);

				
				if( !(descriptionData.equals("Feiertag") || descriptionData.equals("Freie Tage")) ) {			// Variante "DTSTART:"
					// dtstart
					leftBorder = rawData.indexOf(dtstart, rightBorder) +dtstart.length();
					rightBorder = rawData.indexOf(dtendB, leftBorder);
					dtstartData = rawData.substring(leftBorder, rightBorder);
					System.out.println(dtstartData);
					
					/*	berechnet ausgehend von description den Rest (schneller aber schlechter Stil)
					dtstartData = rawData.substring(rightBorder +8, rightBorder +24);
					dtendData = rawData.substring(rightBorder +45, rightBorder +60);		
					leftBorder = rightBorder +69;
					rightBorder = rawData.indexOf(organizer, leftBorder);
					locationData = rawData.substring(rightBorder, leftBorder);
					*/
					
					// dtend
					leftBorder = rawData.indexOf(dtend, rightBorder) +dtend.length();
					rightBorder = rawData.indexOf(location, leftBorder);
					dtendData  = rawData.substring(leftBorder, rightBorder);
					
					// location
					leftBorder = rawData.indexOf(location, rightBorder) +location.length();
					rightBorder = rawData.indexOf(organizer, leftBorder);
					locationData = rawData.substring(leftBorder, rightBorder);
					
				}else {	// Variante "DTSTART;VALUE=DATE:"
					// dtstart
					leftBorder = rawData.indexOf(dtstartDate, rightBorder) +dtstartDate.length();
					rightBorder = rawData.indexOf(dtendB, leftBorder);
					dtstartData = rawData.substring(leftBorder, rightBorder);
					System.out.println(dtstartData);
					
					/*	berechnet ausgehend von description den Rest (schneller aber schlechter Stil)
					dtstartData = rawData.substring(rightBorder +dtstartB.length(), rightBorder +dtstartB.length() +8);	
					dtendData = rawData.substring(rightBorder +dtstartB.length() +8, rightBorder +dtstartB.length() +8 +dtendB.length());							
					*/
					
					// dtend
					leftBorder = rawData.indexOf(dtendDate, rightBorder) +dtendDate.length();
					rightBorder = rawData.indexOf(organizer, leftBorder);
					dtendData  = rawData.substring(leftBorder, rightBorder);
					System.out.println(dtendData);
					
					// location nicht vorhanden
				}  
				
				//summary
				leftBorder =rawData.indexOf(summary, rightBorder) +summary.length();
				rightBorder = rawData.indexOf(end, leftBorder);
				summaryData = rawData.substring(leftBorder, rightBorder);
				System.out.println(summaryData);
				
				// Entry speichern
				entryList.add(new Entry(description, dtstartData, dtendData, locationData, summaryData, entryType));
				
				// letzter Entry wenn Dokumentende erreicht
				if(rightBorder + end.length() + vend.length() == rawData.length()) {
					done = true;
					break;
				}
				
			}
		}

		return entryList;
	}
	
	/**
	 * find the index of BEGIN:VEVENT and END:VEVENT substrings
	 * @param _rawData
	 * @return ArrayList containing all Start and Stop indices of the events
	 */
	private ArrayList<Integer> getIndices(String _rawData) {
		ArrayList<Integer> indexList = new ArrayList<Integer>(15);
		
		// Ermitteln des Start- und Endindexes der Eintragsbloecke
		int hit = 0;							// Index eines Start- oder Endpunkts
		while( hit != -1 ) {					// Index == -1 wenn String nicht gefunden werden kann
			hit = _rawData.indexOf(begin, hit);	// Suche nach Startpunk (begin), ab Index (letzter hit)
			indexList.add(hit);			
			
			if( hit == -1 ) {					// Abbruch da es keinen Endpunkt ohne Startpunkt geben kann
				break;
			}
			
			hit = _rawData.indexOf(end, hit);	// Suche nach Endpunkt
			indexList.add(hit);	
		}
		indexList.subList(indexList.size()-1, indexList.size()).clear();			// Um einen eintrag kuerzen, da letzter immer -1 ist
		
		return indexList;
	}
	
}




