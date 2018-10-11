package at.tugraz.ist.recommender.healthiotrecommender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Testing {

	
	 public static void main( String[] args )
	    {
		 
		 SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s1 = "2013-09-29 12:00:00";
		 String s2 = "2013-09-29 18:00:00";
		
		 try {
			 Date d1 = inFormat.parse(s1);
			 Date d2 = inFormat.parse(s2);
			
			System.out.println("d1:"+d1.getTime());
			System.out.println("d2:"+d2.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
		 
	    }
}
