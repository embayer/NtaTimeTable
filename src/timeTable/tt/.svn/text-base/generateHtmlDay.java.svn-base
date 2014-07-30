package timeTable.tt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class generateHtmlDay {
	ArrayList<Entry> list;			
	//Date d;
	List<Entry> preparedList;																
	int cols;																		// Spaltenzahl fuer jeden Tag, gleicher Index wie preparedLists
	
	public generateHtmlDay(ArrayList<Entry> _list) 
	{
		list = _list;
	}
	
	/**
	 * Holt die Subliste für das angegebene Date day
	 * @param day Datum für gewünschte Liste
	 * @return Die Subliste für übergebenes Datum
	 */
	private List<Entry> getListforDay(Date day)
	{
		List<Entry> listForDay = new ArrayList<Entry>();      
		
		ListIterator<Entry> iterator = list.listIterator();
		Entry entry;
		
		while(iterator.hasNext())
		{
			entry = iterator.next();
			if(entry.start.getDate() == day.getDate() && entry.start.getMonth() == day.getMonth() )  
			{
				listForDay.add(entry);
			}							
		}      
		
		Collections.sort(listForDay);
		
		return listForDay;
	}
	
	
	/**
	 * Nimmt den übergebenen String und wandelt Umlaute in das HTML-Äquivalent
	 * @param s
	 * @return
	 */
	String formatString(String s)
	{
		s = s.replace("\\", "");
		s = s.replace("&", "&amp");
		s = s.replace("ä", "&auml;");
		s = s.replace("ü", "&uuml;");
		s = s.replace("ö", "&ouml;");
		s = s.replace("Ä", "&Auml;");
		s = s.replace("Ü", "&Uuml;");
		s = s.replace("Ö", "&Ouml;");
		s = s.replace("ß", "&szlig;");		
		
		return s;
	}

	
	// TODO 
	/**
	 * Schreibt die Spalten für eine Tabellenzeile
	 * @param Aktueller Zeilenindex
	 * @return Gesamter Zeilenstring
	 */
	private void writeRows(StringBuilder sb)
	{				
		
		ListIterator<Entry> iterator = preparedList.listIterator();
		Entry entry = preparedList.get(0); // erstes element holen
		Entry tmp;
		int[] lastRowSpan = new int[cols]; // Rowspan überwachen. nach mehrzeiligem element warten bis rowspan abgelaufen ist, dann erst neues
		int hrs,min;
		int hrIndex=7;
		
		
		// 68 tabellenzeilen ohne Header
		for(int i = 0; i<68; i++)
		{
			sb.append("<tr>");
			// bei row%4=0 Zeitspalte schreiben mit rowspan=4
			if(i%4==0)
			{						
				sb.append("<td colspan=\"1\" rowspan=\"4\" align=\"center\" valign=\"top\" width=\"40\" class=\"timeborder2\">"+ 
						(hrIndex>9?hrIndex:("0"+hrIndex)) + ":00");
				hrIndex++;
				sb.append("</td>");
			}
			sb.append("<td bgcolor=\"#a1a5a9\" width=\"1\" height=\"15\"></td>");
			for(int n = 0; n < cols; n++)  // Spalten eines Tages
			{
				if(lastRowSpan[n] == 0) 
				{
					if( entry != null && entry.rowindex == i)
					{						
						sb.append("<td rowspan=\"" + (Integer.parseInt(entry.rowspan)>0?entry.rowspan:3) + "\"");
						
						if(entry.rowspan == "0")
							entry.rowspan = "3";
						
						if(entry.collision)
						{
							sb.append(" colspan=\"1\" align=\"left\" valign=\"top\" class=\"eventbg2_" + entry.entryType + "\">");
							lastRowSpan[n]=Integer.parseInt(entry.rowspan);							
						}
						else
						{
							sb.append(" colspan=\"" + cols + "\" align=\"left\" valign=\"top\" class=\"eventbg2_" + entry.entryType + "\">");
							for(int k = 0; k < cols; k++)
							{
								lastRowSpan[k]=Integer.parseInt(entry.rowspan);
							}								
						}							
						
						sb.append("<div class=\"eventfont\"><div class=\"eventbg_" + entry.entryType + "\"><b>");
						
						hrs = entry.start.getHours();
						min = entry.start.getMinutes();
						
						sb.append((hrs>9?hrs:("0"+hrs))+":");
						sb.append(min>9?min:("0"+min));
						
						sb.append("</b></div><div class=\"padd\">");
						
						if(entry.entryType == 2)
							sb.append("Ausfall: ");
						if(entry.entryType == 3)
							sb.append("&Auml;nderung: ");
						if(entry.entryType == 4)
							sb.append("Klausur: ");
						
						sb.append(formatString(entry.summary)); 
						
						if(entry.entryType == 5)
						{
							if(!(entry.location.length() == 0))
								sb.append(", Ort: " + formatString(entry.location));
						}
						
						sb.append("</div></div></td>");						
						
						if(iterator.hasNext()){
							tmp = entry;
							entry = iterator.next();
							while(tmp.equals(entry) && iterator.hasNext())
								entry = iterator.next();
						}

						else
							entry = null;
						
						lastRowSpan[n]--;
					}
					else
					{
						sb.append("<td colspan=\"1\" class=\"weekborder2\">&nbsp;</td>");
					}
					
				}
				else
				{
					lastRowSpan[n]--;					
				}
			}

			
			sb.append("</tr>");				
		}

	}
	
	/**
	 * Anzahl der spalten pro tag (überlappung in zeit)
	 * @param LinkedList, welche ArrayListen fuer jeden Tag enthaelt
	 * @return Array, welches fuer jeden Tag die benoetigte Spaltenanzahl angibt
	 */
	private int numColPerDay(List<Entry> _preparedList) 
	{
		//int columns = 1;
		Entry temp = null;		
		int j, k;			// Entry j, Entry k
		int s;				// Gesamtzahl der Entries eines Tages
		int c = 1;			// Counter (wie oft wird ein Element ueberschnitten)
		int h = 1;			// Counter (wieviele Ueberschneidungen) 
		s = _preparedList.size();
		
		// jeden entry (j) mit allen anderen entries (k) in list vergleichen
		for(j=0; j<s-1; j++) {
			
			// nicht die Gesamtzahl der Ueberschneidungen relevant sondern nur die eines Entry mit allen anderen
		//	if(h == 2) columns = 2;			// 2 Spalten...
		//	else if(h == 3) columns = 3;	// ... oder 3 noetig?
			
			c=1;							// counter ruecksetzen (nur combos relevant!)
			
			for(k=j+1; k<s; k++) {
				
				// gleiche Startzeit j&k
				if( (_preparedList.get(j).start.equals(_preparedList.get(k).start)) 
						// gleiche Endzeit j&k
						|| (_preparedList.get(j).end.equals(_preparedList.get(k).end))
						// Startzeit j nach Startzeit k und Endzeit j vor Endzeit k
						|| ( (_preparedList.get(j).start.after(_preparedList.get(k).start)) && (_preparedList.get(j).end.before(_preparedList.get(k).end)) )
						// Startzeit j vor Startzeit k und Endzeit j nach Endzeit k
						|| ( (_preparedList.get(j).start.before(_preparedList.get(k).start)) && (_preparedList.get(j).end.after(_preparedList.get(k).end)) )
						// Startzeit  innerhalb eines Entries
						|| ( (_preparedList.get(j).start.after(_preparedList.get(k).start)) && (_preparedList.get(j).start.before(_preparedList.get(k).end)) )
						// Endzeit innerhalb eines Entries
						|| ( (_preparedList.get(j).end.after(_preparedList.get(k).start)) && (_preparedList.get(j).end.before(_preparedList.get(k).end)) )
						) {
					if(k>1)
						temp = _preparedList.get(k-1);
					if(temp != null && temp.end.before(_preparedList.get(k).start))
						c--;
					
					c++;				// Ueberschneidungen zaehlen
					if(c > h) h=c;		// hoeher als bisheriges Maximum?
					
					_preparedList.get(j).collision = true;
					_preparedList.get(k).collision = true;
				}
			}
		}
		
		return h;

	}
	
	

	
	String getDayString(Date day) {
		 String date=null;
	    Calendar c = Calendar.getInstance();
	    c.setTime(day);
	    int tmp = c.get(Calendar.DAY_OF_WEEK); 
		 
	    switch (tmp) {
	        case 1: date = "Mo, ";     // Sonntag zeigt montag an
	        break;
	    	case 2: date = "Mo, ";
	    	break;
	    	case 3: date = "Di, ";
	    	break;
	    	case 4: date = "Mi, ";
	    	break;
	    	case 5: date = "Do, ";
	    	break;
	    	case 6: date = "Fr, ";
	    	break;
	    	case 7: date = "Sa, ";   
	    }
	
	    return date + c.get(Calendar.DAY_OF_MONTH) +"."+ (c.get(Calendar.MONTH)+1);

	 }
	
	
	/**
	 * Generiert die Überschriften für Alle Spalten und gibt sie als String zurück
	 * @return Die gesamte Überschriftenzeile beginnend und schließend mit tr
	 */
	private void colHeaders(Date day, StringBuilder sb) 
	{		
		sb.append("<tr><td colspan=\"1\" width=\"40\" class=\"rowOff\"> </td>");
		sb.append("<td width=\"1\"></td>");
		
		sb.append("<td colspan=\"");
		sb.append(cols);
		sb.append("\"align=\"center\" class=\"rowOff\">");
		sb.append(getDayString(day));
		sb.append("</td>");
		
		
		sb.append("</tr>");
	}
	
	
	/**
	 * Generiert die gesamte Html-Datei für angegebenen Tag und gibt den String zurück
	 * @param startDate Datum für das die Datei generiert werden soll
	 * @return Gesamter Html-Content als String
	 */
	public String buildDocumentString(Date startDate) 
	{		
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);	
				
		if(c.get(Calendar.DAY_OF_WEEK )==1)
			return null;		// Sonntag überspringen
		
		preparedList = getListforDay(c.getTime());	
		if(preparedList == null || preparedList.isEmpty())
		{
			return null;
		}
		
		
		cols = numColPerDay(preparedList);
		
		StringBuilder sb = new StringBuilder();
		
		// static head content
		sb.append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"./default.css\" />" +
				"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"calborder\"><tr><td>" +
						"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tfixed\">");
				
		
		//header
		colHeaders(startDate, sb); // Format? Mo, xx.xx	?	
		
		//rows
		writeRows(sb);

		
		

		
		sb.append("</table></td></tr></table></html>");
		
		return sb.toString();
	}
}

