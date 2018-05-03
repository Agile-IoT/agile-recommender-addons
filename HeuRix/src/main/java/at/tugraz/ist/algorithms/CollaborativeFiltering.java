package at.tugraz.ist.algorithms;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.CosineSimilarity;

import at.tugraz.ist.fileOperations.CopyFile;

public class CollaborativeFiltering {

		static String userProfilesFile = "Files/ConfigSolutions/CameraUserRatings";
		//List<RecommendedItem> recommendations=null;
		static int [][] domainValues;
	
		public static int applyCollaborativeFiltering(int [] reqs, int domains [][], int methodID){
			
			int id = 0;
			
			domainValues = new int[domains.length][];
			for (int i=0;i<domains.length;i++){
				domainValues[i] = new int[domains[i].length];
				for (int j=0;j<domains[i].length;j++)
					domainValues[i][j] = domains[i][j];
			}
			
			int userID = newProblemFile(reqs);
			
			try {
				// load the data from the file with format "userID,itemID,value"
				DataModel model = new FileDataModel(new File(userProfilesFile));
				
				UserSimilarity similarity;
				
				switch(methodID){
					case 0:
						similarity = new EuclideanDistanceSimilarity(model);
						break;				
					case 1:
						similarity = new PearsonCorrelationSimilarity(model);
						break;		
					case 2:
						similarity = new UncenteredCosineSimilarity(model);
						break;		
					case 3:
						similarity = new TanimotoCoefficientSimilarity(model);
						break;		
					default:
						similarity = new EuclideanDistanceSimilarity(model);
						break;		
						
				}
				//  compute the correlation coefficient between their interactions
				//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				// UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
				
				
				// we'll use all that have a similarity greater than 0.1
				
			
				//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(1, similarity, model);
				
				// all the pieces to create our recommender
				UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
				
				//  get 10 items recommended for the user with userID 
				// recommendations = recommender.recommend(userID, 92);
				
				
				// check items from 72 to 91 
				float max=0;
				
				for(int i=0;i<20;i++){
					float preference = recommender.estimatePreference(userID,i);
					if(preference>max){
						max = preference;
						id = i;
					}
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return id;
		}
		
		private static int newProblemFile(int []reqs){
			
			String newUserFile =userProfilesFile+"_"+Math.random();
			CopyFile.CopyFileToAnother(userProfilesFile, newUserFile);
			
			int itemIndex=0;
			for(int i=0;i<reqs.length;i++){
				
				if(reqs[i]!=-1){
					int size = domainValues[i].length;
					for(int d=0;d<size;d++){
						if(reqs[i] == domainValues[i][d])
							appendToBottomOfFile(20+","+itemIndex+",1.0",newUserFile);
						else
							appendToBottomOfFile(20+","+itemIndex+",0.0",newUserFile);
						itemIndex++;
					}
				}
				else{
					int size = domainValues[i].length;
					itemIndex += size;
				}
			}
				
				return 20;
		}
		
		
		public static int appendToBottomOfFile(String line,String filename) {
			
			line = "\n"+line;
		    int lineNumber=0;
			try {
				
				Files.write(Paths.get(filename), line.getBytes(), StandardOpenOption.APPEND);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lineNumber-1;
			
		}
		
//		public int getLastUserID(){
//			int userID = 0;
//			
//			try {
//				//BufferedReader reader = new BufferedReader(new FileReader(userProfilesFile));
//				String lastLine="1,1";
//				Scanner sc=new Scanner(new File(userProfilesFile));
//			    while(sc.hasNextLine()){  //checking for the presence of next Line
//			        	lastLine =sc.nextLine();  //reading and storing all lines
//			    }
//			    sc.close();  //close the scanner
//			    userID = Integer.valueOf(lastLine.split(",")[0]);
//					
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//				
//				return userID;
//			}
			
}


