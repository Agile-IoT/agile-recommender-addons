package at.tugraz.ist.recommender.healthiotrecommender;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class Recommender {
	
	
	private static int numberofitems= 0;
	
	public static int getNumberOfItems(String inputFilename){
		try {
			// load the data from the file with format "userID,itemID,value"
			DataModel model = new FileDataModel(new File(inputFilename));
			numberofitems = model.getNumItems();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberofitems;
	}
	
	public static List<ActivityRecommendation> applyCollaborativeFiltering(User user, String inputFilename,int lastUserID){
	//(int genderCode, int agecode, String inputFilename,int lastUserID){
		
		int genderCode = Integer.valueOf(user.getGender());
    	if(genderCode==2)
    		genderCode=5;
    	else if(genderCode==2)
    		genderCode=3;
    	
    	int ageCode = Integer.valueOf(user.getAge());
    	if(ageCode<20)
    		ageCode=1;
    	else if(ageCode<40)
    		ageCode=2;
    	else if(ageCode<60)
    		ageCode=3;
    	else if(ageCode<80)
    		ageCode=4;
    	else 
    		ageCode=5;
    	
		int userIndex = lastUserID+1;
		String usersgender = userIndex+","+1+","+genderCode+".0";
		
		
		// ADD NEW USER TO THE FILE
		// 1- GENDER
	    FileOperations.appendNewLineToFile(inputFilename, usersgender);
	    // 2- AGE
	    if(ageCode>0){
			String usersage = userIndex+","+2+","+ageCode+".0";
			FileOperations.appendNewLineToFile(inputFilename, usersage);
	    }
	    // 3- LAST DAY 
	    ///////////
	    Set<String> keyset = user.stepsRecords.keySet();
	    String[] key = keyset.toArray(new String[0]);
	    //String [] key= Arrays.
	    
	    int steps1_code = user.stepsRecords.get(key[0]).steps1/2000;
		int steps2_code = user.stepsRecords.get(key[0]).steps2/2000;
		int steps3_code = user.stepsRecords.get(key[0]).steps3/2000;
		int stepst_code = user.stepsRecords.get(key[0]).stepstotal/2000;
		 
		 if(steps1_code>9)
			 steps1_code=9;
		 if(steps2_code>9)
			 steps2_code=9;
		 if(steps3_code>9)
			 steps3_code=9;
		 if(stepst_code>9)
			 stepst_code=9;
		 
		String stepsNumberTrend= String.valueOf(steps1_code)+String.valueOf(steps2_code)+String.valueOf(steps3_code);
		String totalstepsNumber = String.valueOf(stepst_code);
		
		
		 int steps1_c= user.stepsRecords.get(key[0]).steps1count;
		 int steps2_c= user.stepsRecords.get(key[0]).steps2count;
		 int steps3_c= user.stepsRecords.get(key[0]).steps3count;
		 int stepst_c= user.stepsRecords.get(key[0]).stepstotalcount;
		 
		 /////// STEPS DURATION ///////
		 int steps1_d= steps1_c/12;
		 int steps2_d= steps2_c/12;
		 int steps3_d= steps3_c/12;
		 int stepst_d= stepst_c/12;
		 
		 // IF 10,11 or 12 hours -> write 9 hours
		 if(stepst_d>9)
			 stepst_d=9;
		 if(steps1_d>9)
			 steps1_d=9;
		 if(steps2_d>9)
			 steps2_d=9;
		 if(steps3_d>9)
			 steps3_d=9;
		 
		 String stepsDurationTrend = String.valueOf(steps1_d)+String.valueOf(steps2_d)+String.valueOf(steps3_d);
		 String totalstepsDuration = String.valueOf(stepst_d); // 1 -> 1 hour, 2 -> 2 hours ...
	     ////////////////////////////////////
		 
		 
		 /// SLEEP /////
		 
		 int sleepHours1 = (user.sleepRecords.get(key[0]).sleep1count) / 12;
		 int sleepHours2 = (user.sleepRecords.get(key[0]).sleep2count) / 12;
		 int sleepHours3 = (user.sleepRecords.get(key[0]).sleep3count) / 12;
		 int totalSleepHours = sleepHours1 +sleepHours2+sleepHours3;
		 
		 int sleep1_c= user.sleepRecords.get(key[0]).sleep1count;
		 int sleep2_c= user.sleepRecords.get(key[0]).sleep2count;
		 int sleep3_c= user.sleepRecords.get(key[0]).sleep3count;
		 int sleept_c= (sleep1_c+sleep2_c+sleep3_c);
		 
		 
		 // SLEEP TOTAL
		 int sleep_Total = user.sleepRecords.get(key[0]).sleep1 + user.sleepRecords.get(key[0]).sleep2 + user.sleepRecords.get(key[0]).sleep3;
		
	    
		 // IF 10,11 or 12 hours -> write 9 hours
		 if(totalSleepHours>9)
			 totalSleepHours=9;
		 if(sleepHours1>9)
			 sleepHours1=9;
		 if(sleepHours2>9)
			 sleepHours2=9;
		 if(sleepHours3>9)
			 sleepHours3=9;
		 
		 int sleept_Avg=0;
		 if(sleept_c>0)
			 sleept_Avg = sleep_Total/sleept_c;
		
		 int sleepCode_total = 1;
		 if(sleept_Avg<=-33)
			 sleepCode_total=5;
		 
		 else if(sleept_Avg<=-30)
			 sleepCode_total=4;
		 
		 else if(sleept_Avg<=-27)
			 sleepCode_total=3;
		 
		
		 String totalsleepDuration = String.valueOf(totalSleepHours); // 1 -> 1 hour, 2 -> 2 hours ...
		 
		 String sleepDurationTrend = String.valueOf(sleepHours1)+String.valueOf(sleepHours2)+String.valueOf(sleepHours3);
		 
		 ////////////////
		
	    String itemID = 1+stepsNumberTrend+totalstepsNumber+stepsDurationTrend+totalstepsDuration+sleepDurationTrend+totalsleepDuration;
		String lastDay = userIndex+","+itemID+","+sleepCode_total+".0";		 
		FileOperations.appendNewLineToFile(inputFilename, lastDay);
		
	    
	    // 4- AVG
         ///////////
	    int steps1_code_avg = user.avgSteps.steps1/2000;
		int steps2_code_avg = user.avgSteps.steps2/2000;
		int steps3_code_avg = user.avgSteps.steps3/2000;
		int stepst_code_avg = user.avgSteps.stepstotal/2000;
		 
		 if(steps1_code_avg>9)
			 steps1_code_avg=9;
		 if(steps2_code_avg>9)
			 steps2_code_avg=9;
		 if(steps3_code_avg>9)
			 steps3_code_avg=9;
		 if(stepst_code_avg>9)
			 stepst_code_avg=9;
		 
		String stepsNumberTrend_avg= String.valueOf(steps1_code)+String.valueOf(steps2_code)+String.valueOf(steps3_code);
		String totalstepsNumber_avg = String.valueOf(stepst_code);
		
		
		 int steps1_c_avg= user.avgSteps.steps1count;
		 int steps2_c_avg= user.avgSteps.steps2count;
		 int steps3_c_avg= user.avgSteps.steps3count;
		 int stepst_c_avg= user.avgSteps.stepstotalcount;
		 
		 /////// STEPS DURATION ///////
		 int steps1_d_avg= steps1_c_avg/12;
		 int steps2_d_avg= steps2_c_avg/12;
		 int steps3_d_avg= steps3_c_avg/12;
		 int stepst_d_avg= stepst_c_avg/12;
		 
		 // IF 10,11 or 12 hours -> write 9 hours
		 if(stepst_d_avg>9)
			 stepst_d_avg=9;
		 if(steps1_d_avg>9)
			 steps1_d_avg=9;
		 if(steps2_d_avg>9)
			 steps2_d_avg=9;
		 if(steps3_d_avg>9)
			 steps3_d_avg=9;
		 
		 String stepsDurationTrend_avg = String.valueOf(steps1_d_avg)+String.valueOf(steps2_d_avg)+String.valueOf(steps3_d_avg);
		 String totalstepsDuration_avg = String.valueOf(stepst_d_avg); // 1 -> 1 hour, 2 -> 2 hours ...
	     ////////////////////////////////////
		 
		 
		 /// SLEEP /////
		 
		 int sleepHours1_avg = (user.avgSleeps.sleep1count) / 12;
		 int sleepHours2_avg = (user.avgSleeps.sleep2count) / 12;
		 int sleepHours3_avg = (user.avgSleeps.sleep3count) / 12;
		 int totalSleepHours_avg = sleepHours1_avg +sleepHours2_avg+sleepHours3_avg;
		 
		 int sleep1_c_avg= user.avgSleeps.sleep1count;
		 int sleep2_c_avg= user.avgSleeps.sleep2count;
		 int sleep3_c_avg= user.avgSleeps.sleep3count;
		 int sleept_c_avg= (sleep1_c_avg+sleep2_c_avg+sleep3_c_avg);
		 
		 
		 // SLEEP TOTAL
		 int sleep_Total_avg = user.avgSleeps.sleep1 + user.avgSleeps.sleep2 + user.avgSleeps.sleep3;
		
	    
		 // IF 10,11 or 12 hours -> write 9 hours
		 if(totalSleepHours_avg>9)
			 totalSleepHours_avg=9;
		 if(sleepHours1_avg>9)
			 sleepHours1_avg=9;
		 if(sleepHours2_avg>9)
			 sleepHours2_avg=9;
		 if(sleepHours3_avg>9)
			 sleepHours3_avg=9;
		 
		 int sleept_Avg_avg=0;
		 if(sleept_c_avg>0)
			 sleept_Avg_avg = sleep_Total_avg/sleept_c_avg;
		
		 int sleepCode_total_avg = 1;
		 if(sleept_Avg_avg<=-33)
			 sleepCode_total_avg=5;
		 
		 else if(sleept_Avg_avg<=-30)
			 sleepCode_total_avg=4;
		 
		 else if(sleept_Avg_avg<=-27)
			 sleepCode_total_avg=3;
		 
		
		 String totalsleepDuration_avg = String.valueOf(totalSleepHours_avg); // 1 -> 1 hour, 2 -> 2 hours ...
		 
		 String sleepDurationTrend_avg = String.valueOf(sleepHours1_avg)+String.valueOf(sleepHours2_avg)+String.valueOf(sleepHours3_avg);
		 
		 ////////////////
		
	    String itemID_avg = 1+stepsNumberTrend_avg+totalstepsNumber_avg+stepsDurationTrend_avg+totalstepsDuration_avg+sleepDurationTrend_avg+totalsleepDuration_avg;
		String avgData = userIndex+","+itemID_avg+","+sleepCode_total_avg+".0";		 
		FileOperations.appendNewLineToFile(inputFilename, avgData);
	    
	    
		List<RecommendedItem> recommendations=null;
		List<ActivityRecommendation> items= new ArrayList<ActivityRecommendation>();
		 
		try {
			// load the data from the file with format "userID,itemID,value"
			DataModel model = new FileDataModel(new File(inputFilename));
			numberofitems = model.getNumItems();
			
			//  compute the correlation coefficient between their interactions
			//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
			
			// we'll use all that have a similarity greater than 0.1
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			//UserNeighborhood neighborhood = new NearestNUserNeighborhood(1, similarity, model);
			
			// all the pieces to create our recommender
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			
			//  get 1 items recommended for the user with userID 
			recommendations = recommender.recommend(userIndex, 5);
			
			for (RecommendedItem recommendation : recommendations) {
				System.out.println(recommendation);
				String recommendedItemID = String.valueOf(recommendation.getItemID());
				
				items.add(parseItemID(recommendedItemID));
				//String item = getItem(recommendation.getItemID(),inputFilename);
				//System.out.println(item);
			}
			
			// DELETE NEW USER FROM THE FILE
		    FileOperations.removeLastLine(inputFilename);
		    if(ageCode>0){
		    	FileOperations.removeLastLine(inputFilename);
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	public static ActivityRecommendation parseItemID(String id){
		// 1 111 1 111 1 111 1
		
		ActivityRecommendation recom = new ActivityRecommendation();
		
		String parsedItem = id.substring(1, id.length()-1);
		String stepsNumberTrend_total = parsedItem.substring(0, 4); // x2000
		
		String stepsDurationTrend_total = parsedItem.substring(4, 8); //x1 hour
		
		String sleepDurationTrend_total = parsedItem.substring(8); // x1 hour
		
		recom.steps1 = "take "+ (Integer.valueOf(stepsNumberTrend_total.substring(0, 1))+1)*2000+" steps in " + (Integer.valueOf(stepsDurationTrend_total.substring(0, 1))+1)+" hours before noon";
		recom.steps2 = "take "+ (Integer.valueOf(stepsNumberTrend_total.substring(1, 2))+1)*2000+" steps in " + (Integer.valueOf(stepsDurationTrend_total.substring(1, 2))+1)+" hours in the afternoon";
		recom.steps3 = "take "+ (Integer.valueOf(stepsNumberTrend_total.substring(2, 3))+1)*2000+" steps in " + (Integer.valueOf(stepsDurationTrend_total.substring(2, 3))+1)+" hours in the evening";
		//recom.stepstotal = "take "+ (Integer.valueOf(stepsNumberTrend_total.substring(3, 4))+1)*2000+" steps in " + (Integer.valueOf(stepsDurationTrend_total.substring(2, 3))+1)+" hours in total today";
		
		recom.sleep1 = "sleep "+ (Integer.valueOf(sleepDurationTrend_total.substring(0, 1)))+" hours between 00:00-12:00";
		recom.sleep2 = "sleep "+ (Integer.valueOf(sleepDurationTrend_total.substring(1, 2)))+" hours between 12:00-18:00";
		recom.sleep3 = "sleep "+ (Integer.valueOf(sleepDurationTrend_total.substring(2, 3)))+" hours between 18:00-24:00";
		//recom.sleeptotal = "sleep "+ (Integer.valueOf(sleepDurationTrend_total.substring(3, 4))+1)+" hours in total today";
		
	
		return recom;
	}
	
//	public static String getItem(long itemNumber,String filename){
//
//		String item =null;
//		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
//			item = lines.skip(itemNumber).findFirst().get();
//		} catch(Exception ex){
//			// TODO
//		}
//		
//		return item;
//	}

	
}
