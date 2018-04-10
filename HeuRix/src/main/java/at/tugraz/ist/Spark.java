package at.tugraz.ist;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import scala.Tuple2;

public class Spark {
	
	public static String inputFile = "Files/BikeConfigDataset/Problem_1";
	
	public static void main (String [] args){
		
		//SparkConf sparkConf = new SparkConf().setAppName("JavaALS");
		SparkConf sparkConf = new SparkConf().setAppName("JavaALS").setMaster("local[2]").set("spark.executor.memory","1g");
	    int rank = 1;
	    int iterations = 2;
	    String outputDir = "Files/BikeConfigDataset/Spark/";
	    int blocks = -1;
	    
	    JavaSparkContext sc = new JavaSparkContext(sparkConf);
	    JavaRDD<String> lines = sc.textFile(inputFile);

	    JavaRDD<Rating> ratings = lines.map(new ParseRating());

	    long start = System.nanoTime();
	    MatrixFactorizationModel model = ALS.train(ratings.rdd(), rank, iterations, 0.01, blocks);

	    model.userFeatures().toJavaRDD().map(new FeaturesToString()).saveAsTextFile(
	        outputDir + "/userFeatures");
	    model.productFeatures().toJavaRDD().map(new FeaturesToString()).saveAsTextFile(
	        outputDir + "/productFeatures");
	    System.out.println("Final user/product features written to " + outputDir);
	    long end = System.nanoTime();
	    sc.stop();
	    System.out.println("Spark ALS: "+(end-start));
	}
	
	static class ParseRating implements Function<String, Rating> {
	    private static final Pattern COMMA = Pattern.compile(",");

	    @Override
	    public Rating call(String line) {
	      String[] tok = COMMA.split(line);
	      int x = Integer.parseInt(tok[0]);
	      int y = Integer.parseInt(tok[1]);
	      double rating = Double.parseDouble(tok[2]);
	      return new Rating(x, y, rating);
	    }
 }
 
 static class FeaturesToString implements Function<Tuple2<Object, double[]>, String> {
	    @Override
	    public String call(Tuple2<Object, double[]> element) {
	      return element._1() + "," + Arrays.toString(element._2());
	    }
}

}
