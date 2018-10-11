package at.tugraz.ist.recommender.healthiotrecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class CSVReader {

    
    public static List<User> readAllUsers(){
    	 List<User> userList = new ArrayList<User>();
    	 //String s = "2013-09-29 18:46:19";
		 SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat onlyDateFormat = new SimpleDateFormat("yyyyMMdd");
		 
    	
    	 String csvFile1 = "C:\\Users\\spolater\\Desktop\\EVOS dataset\\steps\\steps.csv";
    	 List<String[]> parsedLines1 = parseLines (csvFile1);
    	 // id,effective_date_time,steps,units,type_id,user_id,gender_id,year_of_birth
    	 Hashtable<String, User> hashmap1 = new Hashtable<String,User>();
    	 
    	 for(int i=0;i<parsedLines1.size();i++){
    		 User usr;
    		 
    		 if(!hashmap1.isEmpty() && hashmap1.get(parsedLines1.get(i)[5])!=null)
    			 usr = hashmap1.get(parsedLines1.get(i)[5]);
    		 else{
    			 usr = new User(); 
    			 usr.setID(parsedLines1.get(i)[5]);
    			 usr.setGender(Integer.valueOf(parsedLines1.get(i)[6]));
    			 usr.setIndex(hashmap1.size()+1);
    		 }
    		 
    		 int steps = Integer.valueOf(parsedLines1.get(i)[2]);
    		 //usr.setStepstotal(usr.steps.getStepstotal()+steps);
    		 Date dateTime = null;
    		 		 
    		 try {
    			 dateTime = inFormat.parse(parsedLines1.get(i)[1].replaceAll("\"", ""));
    			 
    			 Calendar c = Calendar.getInstance();
				 c.setTime(inFormat.parse(parsedLines1.get(i)[1].replaceAll("\"", "")));
				 c.add(Calendar.DATE, 1);  // number of days to add
				 String onlyDate = onlyDateFormat.format(c.getTime());  // dt is now the new date
					
    			 //String date = String.valueOf(dateTime.getYear())+String.valueOf(dateTime.getMonth())+String.valueOf(dateTime.getDate())+","+String.valueOf(dateTime.getDay());
    	    		
				 String date = onlyDate+","+String.valueOf(dateTime.getDay());
 	    		
				 
    			 if(usr.stepsRecords.get(date)==null)
    				 usr.stepsRecords.put(date,new Steps());
    			 
    			 if(dateTime.getHours()<12){ // 12AM - Noon
    				 usr.stepsRecords.get(date).steps1 += steps;
    				 usr.stepsRecords.get(date).steps1count ++;
    			 }
    			 else if(dateTime.getHours()>18){ // 18 PM 
    				 usr.stepsRecords.get(date).steps3 += steps;
    				 usr.stepsRecords.get(date).steps3count ++;
    			 }
    			 else{
    				 usr.stepsRecords.get(date).steps2 += steps;
    				 usr.stepsRecords.get(date).steps2count ++;
    			 }
    				 
    			 usr.stepsRecords.get(date).stepstotal += steps;
    			 usr.stepsRecords.get(date).stepstotalcount ++;
    			 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		 
    		 
    		 	 
    		 hashmap1.put(parsedLines1.get(i)[5], usr);	 
    	 }
    	 
    	 String csvFile2 = "C:\\Users\\spolater\\Desktop\\EVOS dataset\\sleep\\sleep.csv";
    	 List<String[]> parsedLines2 = parseLines (csvFile2);
    	 // id,effective_date_time,sleep_type,units,type_id,user_id,gender_id,year_of_birth
    	 
    	 for(int i=0;i<parsedLines2.size();i++){
    		 User usr;
    		 
    		 if(!hashmap1.isEmpty() && hashmap1.get(parsedLines2.get(i)[5])!=null)
    			 usr = hashmap1.get(parsedLines2.get(i)[5]);
    		 else{
    			 usr = new User(); 
    			 usr.setID(parsedLines2.get(i)[5]);
    			 usr.setGender(Integer.valueOf(parsedLines1.get(i)[6]));
    			 usr.setIndex(hashmap1.size()+1);
    		 }
    		 int sleepQuality = Integer.valueOf(parsedLines2.get(i)[2]);
    		 Date dateTime = null;
    		 
    		
    		 try {
    			 dateTime = inFormat.parse(parsedLines2.get(i)[1].replaceAll("\"", ""));
    			 //String date = String.valueOf(dateTime.getYear())+String.valueOf(dateTime.getMonth())+String.valueOf(dateTime.getDate())+","+String.valueOf(dateTime.getDay());
    			 
    			 Calendar c = Calendar.getInstance();
				 c.setTime(inFormat.parse(parsedLines2.get(i)[1].replaceAll("\"", "")));
				 c.add(Calendar.DATE, 1);  // number of days to add
				 String onlyDate = onlyDateFormat.format(c.getTime());  // dt is now the new date
					
    			 //String date = String.valueOf(dateTime.getYear())+String.valueOf(dateTime.getMonth())+String.valueOf(dateTime.getDate())+","+String.valueOf(dateTime.getDay());
    	    		
				 String date = onlyDate+","+String.valueOf(dateTime.getDay());
				 
    			 if(usr.sleepRecords.get(date)==null)
    				 usr.sleepRecords.put(date,new Sleep());
    			 
    			 if(dateTime.getHours()<12){ // 12:00 morning
    				 usr.sleepRecords.get(date).sleep1 += sleepQuality;
    				 usr.sleepRecords.get(date).sleep1count ++;
    			 }
    			 else if(dateTime.getHours()>18){ // 18:00 afternoon
    				 usr.sleepRecords.get(date).sleep3 += sleepQuality;
    				 usr.sleepRecords.get(date).sleep3count ++;
    			 }
    			 else{
    				 usr.sleepRecords.get(date).sleep2 += sleepQuality; // night
    				 usr.sleepRecords.get(date).sleep2count ++;
    			 }
    				 
//    			 usr.sleepRecords.get(date).sleeptotal += sleepQuality;
//    			 usr.sleepRecords.get(date).sleeptotalcount ++;
    			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		 
    		 //usr.setSleeptotal(usr.getSleeptotal()+Integer.valueOf(parsedLines2.get(i)[2]));
    		 	 
    		 hashmap1.put(parsedLines2.get(i)[5], usr);
    			 
    	 }
    	 
    	 Iterator<String> keySetIterator = hashmap1.keySet().iterator(); 
    	 while(keySetIterator.hasNext()){ 
    		 String key = keySetIterator.next(); 
    		 userList.add(hashmap1.get(key)); 
    	 }
 
    	 
    	 return userList;
    }
    
    
    public static List<String[]> parseLines(String csvFile) {

        //String csvFile = "C:\\Users\\spolater\\Desktop\\EVOS dataset\\steps\\steps.csv";
        String line = "";
        String cvsSplitBy = ",";

        List<String[]> parsedLines = new ArrayList<String[]>();
        		
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] parsedLine = line.split(cvsSplitBy);
                //System.out.println(country[0]);
                parsedLines.add(parsedLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        parsedLines.remove(parsedLines.get(0));
        return parsedLines;

    }

}

