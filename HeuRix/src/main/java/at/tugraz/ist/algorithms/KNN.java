package at.tugraz.ist.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import at.tugraz.ist.cobarix.Knowledgebase;
import at.tugraz.ist.cobarix.RecommendationTasks;
import at.tugraz.ist.knowledgebases.KB;

public class KNN {
	
	public static int[] getKNN(int n, int []req, int [][]users, KB knowledgebase){
		
		int [] reqs_normalized= MinMaxNormalization.normalize(req, knowledgebase.getVars());
		HashMap<Double, Integer> hmap = new HashMap<Double, Integer>();
		
		for (int i=0; i<users.length;i++){
			int [] user_normalized= MinMaxNormalization.normalize(users[i], knowledgebase.getVars());
			
			// DISTANCE
			double dist = MatrixFactorization.euclidean_distance(reqs_normalized,user_normalized);
			
			int minDist = 0;
			if(i!=0 && dist<minDist){
				//minDist = dist;
				hmap.put(dist, i);
			}
		}
		
		int[] similarIndexes = new int[n];
		Map<Double, Integer> map = new TreeMap<Double, Integer>(hmap); 
        //System.out.println("After Sorting:");
        Set set2 = map.entrySet();
        Iterator iterator2 = set2.iterator();
        int count =0;
        while(count<n) {
        	similarIndexes[n]= (int) ((Map.Entry)iterator2.next()).getValue();
        	count++;
        }
		
		return similarIndexes;			
	}

	public static int[] aggregateAvg (int[][] similars){
		int size = similars[0].length;
		int[] aggr = new int [size];
		for(int i=0;i<similars.length;i++){
			for(int j=0;j<size;j++){
				aggr[i] = similars[i][j];
			}
			aggr[i] = aggr[i] / size;
		}
		
		return aggr;
	}
	
	public static int aggregateProductID (int[] similarIndexes, RecommendationTasks recom){
		int size = similarIndexes.length; 
		HashMap<Integer,Integer> products = new HashMap<Integer,Integer>();
		
		for(int i=0;i<similarIndexes.length;i++){
			if(products.get(similarIndexes[i])==null)
				products.put(similarIndexes[i], 1);
			else
				products.put(similarIndexes[i], products.get(similarIndexes[i])+1);
		}
		
		List<Integer> mapValues = new ArrayList<>(products.values());
		Collections.sort(mapValues);
		Iterator<Integer> valueIt = mapValues.iterator();
		Integer val = valueIt.next();
		 
		return products.get(val);
	}
	
	
	
	
}
