package at.tugraz.ist.recommender.healthiotrecommender;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
	
	public static List<ActivityRecommendation> applyCollaborativeFiltering(int genderCode, int agecode, String inputFilename,int lastUserID){
		 
		int userIndex = lastUserID+1;
		String usersgender = userIndex+","+1+","+genderCode+".0";
		
		
		// ADD NEW USER TO THE FILE
	    FileOperations.appendNewLineToFile(inputFilename, usersgender);
	    if(agecode>0){
			String usersage = userIndex+","+2+","+agecode+".0";
			FileOperations.appendNewLineToFile(inputFilename, usersage);
	    }
		 
		
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
				String itemID = String.valueOf(recommendation.getItemID());
				
				items.add(parseItemID(itemID));
				//String item = getItem(recommendation.getItemID(),inputFilename);
				//System.out.println(item);
			}
			
			// DELETE NEW USER FROM THE FILE
		    FileOperations.removeLastLine(inputFilename);
		    if(agecode>0){
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
