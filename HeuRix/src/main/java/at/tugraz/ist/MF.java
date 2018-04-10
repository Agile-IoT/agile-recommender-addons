package at.tugraz.ist;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.Rating;

import scala.Tuple2;

import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

public class MF {
	

	public static List<RecommendedItem> SVD(DataModel dataModel,int numFeatures, int numIterations, int userID, int numberRecommendedItems){
		try {
			long start = System.nanoTime();
			Factorizer factorizer_svd;
		
			factorizer_svd = new SVDPlusPlusFactorizer(dataModel,numFeatures,numIterations);
		
			Recommender recommender = new SVDRecommender(dataModel,factorizer_svd);
			List<RecommendedItem> topItems = recommender.recommend(userID,numberRecommendedItems);
			long end  = System.nanoTime();
			System.out.println("SVD Time: "+ (end-start));
			//System.out.println("SVD: "+topItems.toString());
			
			return topItems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<RecommendedItem> ALS(DataModel dataModel,int numFeatures, int numIterations,double lambda, int userID, int numberRecommendedItems){
		try {
			long start = System.nanoTime();
			Factorizer factorizer_als;
		
			factorizer_als = new ALSWRFactorizer(dataModel,numFeatures,lambda,numIterations);
		
			Recommender recommender = new SVDRecommender(dataModel,factorizer_als);
			List<RecommendedItem> topItems = recommender.recommend(userID,numberRecommendedItems);
			long end  = System.nanoTime();
			
			System.out.println("ALS Time: "+ (end-start));
			// System.out.println("ALS: "+topItems.toString());
			
			return topItems;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
