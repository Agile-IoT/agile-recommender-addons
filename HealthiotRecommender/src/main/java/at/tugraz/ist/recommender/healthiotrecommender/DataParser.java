package at.tugraz.ist.recommender.healthiotrecommender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class DataParser 
{
    public static int parseFile(String filename)
    {
        System.out.println( "Hello World!" );
        SimpleDateFormat onlyDateFormat = new SimpleDateFormat("yyyyMMdd");
        
        //String filename = System.getProperty("user.dir")+"\\UserActivities";
        FileOperations.cleanFile(filename);
        
        
        List<User> userList = new ArrayList<User>();
        userList = CSVReader.readAllUsers();
       
        for(int i=0;i<userList.size();i++){
        	 int index = userList.get(i).index;
        	 Iterator<String> keySetIterator = userList.get(i).sleepRecords.keySet().iterator(); 
        	 int genderCode= userList.get(i).gender;
        	 
        	 // FEMALE=1, OTHER=3, MALE=5
        	 if(genderCode==2)
        		 genderCode=5;
        	 
        	 String usersgender = index+","+1+","+genderCode+".0";
			 
    		 FileOperations.appendNewLineToFile(filename, usersgender);
    		 
        	 //int daycount=0;
        	 
        	 while(keySetIterator.hasNext()){ 
        		 // READ ONE DAY STATISTICS OF THE USER
        		 String today_str = keySetIterator.next();
        		 //String date = String.valueOf(dateTime.getYear())+String.valueOf(dateTime.getMonth())+String.valueOf(dateTime.getDate())+","+String.valueOf(dateTime.getDay());
        		 String tomorrow =null;
        		 try {
						//Date today = inFormat.parse(today_str);
						//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Calendar c = Calendar.getInstance();
						c.setTime(onlyDateFormat.parse(today_str.split(",")[0]));
						c.add(Calendar.DATE, 1);  // number of days to add
						tomorrow = onlyDateFormat.format(c.getTime());  // dt is now the new date
	        		  
        		 } catch (ParseException e) {
        			 // TODO Auto-generated catch block
						e.printStackTrace();
        		 }  
        		 
        		 
        		 // weekdays: MONDAY:0, ......SUNDAY:6
        		 String weekday = today_str.split(",")[1];
        		
        		 int dayint= Integer.valueOf(weekday);
        		 if(dayint==6)
        			 tomorrow += ",0";
        		 else
        			 tomorrow += ","+(dayint+1);
        			 
        		 // NO RECORD FOR THE NIGHT SLEEP (after 00:00 of the night)
        		 if(tomorrow==null || userList.get(i).sleepRecords.get(tomorrow)==null)
        			 continue;
        		 
        		 // IF NO STEPS DATA FOR THIS DATA, CONTINUE WITH THE NEXT RECORD
        		 if (userList.get(i).stepsRecords.get(today_str)==null)
        			 continue;
        		 

//        		 // CHANGE SUNDAY=0 to SUNDAY=7
//        		 if(Integer.valueOf(weekday)==0)
//        			 weekday = String.valueOf(Integer.valueOf(weekday)+7);
//        		 
        		 
        		 // READ SLEEP RECORDS OF THIS DAY
        		 int sleep1_Avg=0;
        		 int sleep2_Avg=0;
        		 int sleep3_Avg=0;
        		 int sleept_Avg=0;
        		
        		 // SLEEP1 from tomorrow 00:00-12:00
        		 if(userList.get(i).sleepRecords.get(tomorrow).sleep1count!=0)
        			 sleep1_Avg = userList.get(i).sleepRecords.get(tomorrow).sleep1/userList.get(i).sleepRecords.get(tomorrow).sleep1count;
        		 
        		 // SLEEP2 and SLEEP3 from today 12:00-18:00 and 18:00-24:00
        		 if(userList.get(i).sleepRecords.get(today_str).sleep2count!=0)
        			 sleep2_Avg = userList.get(i).sleepRecords.get(today_str).sleep2/userList.get(i).sleepRecords.get(today_str).sleep2count;
        		 if(userList.get(i).sleepRecords.get(today_str).sleep3count!=0)
        			 sleep3_Avg = userList.get(i).sleepRecords.get(today_str).sleep3/userList.get(i).sleepRecords.get(today_str).sleep3count;
        		 
//        		 // SLEEP TOTAL
//        		 if(userList.get(i).sleepRecords.get(today_str).sleeptotalcount!=0)
//        			 sleept_Avg = userList.get(i).sleepRecords.get(today_str).sleeptotal/userList.get(i).sleepRecords.get(today_str).sleeptotalcount;
//        		 
        		 int sleep1_c= userList.get(i).sleepRecords.get(today_str).sleep1count;
        		 int sleep2_c= userList.get(i).sleepRecords.get(today_str).sleep2count;
        		 int sleep3_c= userList.get(i).sleepRecords.get(today_str).sleep3count;
        		 //int sleept_c= userList.get(i).sleepRecords.get(today_str).sleeptotalcount;
        		 //int sleept_c= (sleep1_c+sleep1_c+sleep1_c);
        		 
        		 
        	     sleept_Avg = (sleep1_Avg+sleep2_Avg+sleep3_Avg)/3;
        		 
        		 int sleepCode_total = 1;
        		 if(sleept_Avg<=-40)
        			 sleepCode_total=5;
        		 else if(sleept_Avg<=-30)
        			 sleepCode_total=4;
        		 else if(sleept_Avg<=-20)
        			 sleepCode_total=3;
        		 else if(sleept_Avg<=-10)
        			 sleepCode_total=2;
        		 
        		 
        		 int sleepCode_1 = 1;
        		 if(sleep2_Avg<=-40)
        			 sleepCode_1=5;
        		 else if(sleep2_Avg<=-30)
        			 sleepCode_1=4;
        		 else if(sleep2_Avg<=-20)
        			 sleepCode_1=3;
        		 else if(sleep2_Avg<=-10)
        			 sleepCode_1=2;
        		 
        		 
        		 int sleepCode_2 = 1;
        		 if(sleep3_Avg<=-40)
        			 sleepCode_2=5;
        		 else if(sleep3_Avg<=-30)
        			 sleepCode_2=4;
        		 else if(sleep3_Avg<=-20)
        			 sleepCode_2=3;
        		 else if(sleep3_Avg<=-10)
        			 sleepCode_2=2;
        		 
        		 
        		 int sleepCode_3 = 1;
        		 if(sleept_Avg<=-40)
        			 sleepCode_3=5;
        		 else if(sleept_Avg<=-30)
        			 sleepCode_3=4;
        		 else if(sleept_Avg<=-20)
        			 sleepCode_3=3;
        		 else if(sleept_Avg<=-10)
        			 sleepCode_3=2;
        		 
        		 
        		 String sleepQualityTrend= String.valueOf(sleepCode_1)+String.valueOf(sleepCode_2)+String.valueOf(sleepCode_3);
        		 
        		 String sleepDurationTrend = String.valueOf(sleep1_c/12).substring(0, 1)+String.valueOf(sleep2_c/12).substring(0, 1)+String.valueOf(sleep3_c/12).substring(0, 1);
        		 String totalsleepDuration = String.valueOf((sleep1_c+sleep2_c+sleep3_c) / 12).substring(0, 1); // 1 -> 1 hour, 2 -> 2 hours ...
        		 
        		 // System.out.println(index+","+key+","+sleep1_Avg+","+sleep2_Avg+","+sleep3_Avg+","+sleept_Avg);
        		 //daycount++;
        		 
        		 // READ STEPS RECORDS OF THE SAME DAY
        		 
        		 int steps1= userList.get(i).stepsRecords.get(today_str).steps1;
        		 int steps2= userList.get(i).stepsRecords.get(today_str).steps2;
        		 int steps3= userList.get(i).stepsRecords.get(today_str).steps3;
        		 int stepst= userList.get(i).stepsRecords.get(today_str).stepstotal;
        		 
        		 int steps1_c= userList.get(i).stepsRecords.get(today_str).steps1count;
        		 int steps2_c= userList.get(i).stepsRecords.get(today_str).steps2count;
        		 int steps3_c= userList.get(i).stepsRecords.get(today_str).steps3count;
        		 int stepst_c= userList.get(i).stepsRecords.get(today_str).stepstotalcount;
        		 
        		 
        		 
        		 String itemID = "";
        		 String stepsNumberTrend= String.valueOf(steps1 / 2000).substring(0, 1)+String.valueOf(steps2 / 2000).substring(0, 1)+String.valueOf(steps3 / 2000).substring(0, 1);
        		 String totalstepsNumber = String.valueOf(stepst / 2000).substring(0, 1);
        		 
        		 String stepsDurationTrend = String.valueOf(steps1_c/12).substring(0, 1)+String.valueOf(steps2_c/12).substring(0, 1)+String.valueOf(steps3_c/12).substring(0, 1);
        		 String totalstepsDuration = String.valueOf(stepst_c/12).substring(0, 1); // 1 -> 1 hour, 2 -> 2 hours ...
        		 
        		 //itemID = weekday+"_"+stepsNumberTrend+"_"+stepsDurationTrend+"_"+totalDuration;
        		 //itemID = stepsNumberTrend+"_"+totalstepsNumber+"_"+stepsDurationTrend+"_"+totalstepsDuration+"_"+sleepDurationTrend+"_"+totalsleepDuration;
        		 itemID = 1+stepsNumberTrend+totalstepsNumber+stepsDurationTrend+totalstepsDuration+sleepDurationTrend+totalsleepDuration;
        		 String line = index+","+itemID+","+sleepCode_total+".0";
        				 
        		 FileOperations.appendNewLineToFile(filename, line);
        		 
        		 System.out.println(index+","+itemID+","+sleepCode_total);
        	 }

        }
        return userList.size();
    }
}
