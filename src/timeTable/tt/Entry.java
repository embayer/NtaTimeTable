package timeTable.tt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


// Klasse, als Speicher fuer Eintraege
public class Entry implements Comparable<Entry> {
	String description;
	String dtstart;
	String dtend;
	String location;
	String summary;
	String rowspan;
	int rowindex;
	int entryType; // für einfärbung
	boolean collision;
	
	
	SimpleDateFormat dateTime = new SimpleDateFormat("yyyyMMdd HHmmss");					// => besser?
	
	Date start;
	Date end;
	
	public Entry() {
		
	}
	
	public Entry(String _description, String _dtstart, String _dtend, String _location, String _summary, int _entryType) {
		description = _description;
		dtstart = _dtstart;
		dtend = _dtend;
		location = _location;
		summary = _summary;	
		entryType = _entryType;
		collision = false;
		

		start = dateFormatter(dtstart);
		end = dateFormatter(dtend);
		rowspan = Integer.toString((((end.getHours()*60) + end.getMinutes()) - ((start.getHours()*60)+ start.getMinutes())) / 15) ;
		rowindex = ( (start.getHours()*60 + start.getMinutes()) - 420 ) / 15;  // (Startuhrzeit - 07.00) / 15 ergibt zeilenindex 
		// TODO  ganztägige einträge? wie sehen die aus? startzeit=endzeit?
		// 		 
	}

	
	private Date dateFormatter(String dateAndTime) {
		
		Date d = null;
		StringBuffer sb = new StringBuffer(dateAndTime);
		
		// Datum in der Form: yyyyMMddTHHmmss => T durch Leerzeichen ersetzen
		if( dateAndTime.length() == 15 ) {

			sb.replace(8,9, " ");
			try {
				d = dateTime.parse(sb.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		// Datum in der Form: yyyyMMdd => default Wert 7:15:00 anhaengen
		else {

			try {
				d = dateTime.parse(dateAndTime+" 071500");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return d;
	}
	
	public int compareTo(Entry e) {
		// TODO Auto-generated method stub
		return start.compareTo(e.start);
	}
}