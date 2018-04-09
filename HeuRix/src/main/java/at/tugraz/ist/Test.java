package at.tugraz.ist;
import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

public class Test {
	
	public static void main(String[] args) {
		DataModel dataModel;
		int numFeatures =3; // mxk,kxn -> k value
		int numIterations =2;
		int userID=4;
		double lambda =0.05 ;
		int numberRecommendedItems=64;
		try {
			dataModel = new FileDataModel(new File("Files/UserProfiles3.data"));
			
			trySVD(dataModel,numFeatures,numIterations,userID,numberRecommendedItems);
			tryALS(dataModel, numFeatures, numIterations, lambda, userID, numberRecommendedItems);	
					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}	
	
	
	public static long trySVD(DataModel dataModel,int numFeatures, int numIterations, int userID, int numberRecommendedItems){
		try {
			long start = System.nanoTime();
			Factorizer factorizer_svd;
		
			factorizer_svd = new SVDPlusPlusFactorizer(dataModel,numFeatures,numIterations);
		
			Recommender recommender = new SVDRecommender(dataModel,factorizer_svd);
			List<RecommendedItem> topItems = recommender.recommend(userID,numberRecommendedItems);
			long end  = System.nanoTime();
			System.out.println(end-start);
	
			System.out.println("SVD: "+topItems.toString());
			
			return (end-start);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (-1);
		}
	}
	
	public static long tryALS(DataModel dataModel,int numFeatures, int numIterations,double lambda, int userID, int numberRecommendedItems){
		try {
			long start = System.nanoTime();
			Factorizer factorizer_als;
		
			factorizer_als = new ALSWRFactorizer(dataModel,numFeatures,lambda,numIterations);
		
			Recommender recommender = new SVDRecommender(dataModel,factorizer_als);
			List<RecommendedItem> topItems = recommender.recommend(userID,numberRecommendedItems);
			long end  = System.nanoTime();
			System.out.println(end-start);
	
			System.out.println("ALS: "+topItems.toString());
			
			return (end-start);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (-1);
		}
	}
	
}
