package at.tugraz.ist.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorization;
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

public class MatrixFactorization {
	
	public static double[][] IF;
	public static double[][] UF;
	
	public static void SVD(DataModel dataModel,int numFeatures, int numIterations, int userID, int numberRecommendedItems){
		try {
			long start = System.nanoTime();
			Factorizer factorizer_svd;
		
			factorizer_svd = new SVDPlusPlusFactorizer(dataModel,numFeatures,numIterations);
			
			Factorization facts = factorizer_svd.factorize();
			IF = facts.allItemFeatures();
			UF = facts.allUserFeatures();
		
//			Recommender recommender = new SVDRecommender(dataModel,factorizer_svd);
//			List<RecommendedItem> topItems = recommender.recommend(userID,numberRecommendedItems);
			long end  = System.nanoTime();
			System.out.println("SVD Time: "+ (end-start));
//			//System.out.println("SVD: "+topItems.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
		
		m2 = transposeMatrix(m2);
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {         // rows from m1
            for(int j = 0; j < mRColLength; j++) {     // columns from m2
                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }
	
	 public static double[][] transposeMatrix(double [][] m){
	        double[][] temp = new double[m[0].length][m.length];
	        for (int i = 0; i < m.length; i++)
	            for (int j = 0; j < m[0].length; j++)
	                temp[j][i] = m[i][j];
	        return temp;
	    }
	
	public static double euclidean_distance(int[] a, int[] b) {
        double diff_square_sum = 0.0;
        for (int i = 0; i < a.length; i++) {
        	if(a[i]!=-1)
        		diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(diff_square_sum);
    }
	
//	public static List<RecommendedItem> ALS(DataModel dataModel,int numFeatures, int numIterations,double lambda, int userID, int numberRecommendedItems){
//		try {
//			long start = System.nanoTime();
//			Factorizer factorizer_als;
//		
//			factorizer_als = new ALSWRFactorizer(dataModel,numFeatures,lambda,numIterations);
//		
//			Recommender recommender = new SVDRecommender(dataModel,factorizer_als);
//			List<RecommendedItem> topItems = recommender.recommend(userID,numberRecommendedItems);
//			long end  = System.nanoTime();
//			
//			System.out.println("ALS Time: "+ (end-start));
//			// System.out.println("ALS: "+topItems.toString());
//			
//			return topItems;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	

}
