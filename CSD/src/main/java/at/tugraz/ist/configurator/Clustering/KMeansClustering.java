package at.tugraz.ist.configurator.Clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.weka.WekaClusterer;
import weka.clusterers.EM;

public class KMeansClustering {
	
	
public static void applyKMeans(String inputFile, int numberofVars, int numberOfClusters, String outputFolder){
		 	try {
		        /* Load a dataset */
		        Dataset data;
				
				data = FileHandler.loadDataset(new File(inputFile), numberofVars, ",");
				
		        /*
		         * Create a new instance of the KMeans algorithm, with no options
		         * specified. By default this will generate 4 clusters.
		         */
		        Clusterer km = new KMeans(numberOfClusters);
		        /*
		         * Cluster the data, it will be returned as an array of data sets, with
		         * each dataset representing a cluster
		         */
		        Dataset[] clusters = km.cluster(data);
		        //System.out.println("Cluster count: " + clusters.length);
		        
		        
		        
		        //EM em = new EM();
	        	//WekaClusterer jmlem = new WekaClusterer(em);
	        	//Dataset[] clusters = jmlem.cluster(data);
	        	//System.out.println("***NEW: Cluster count: " + clusters.length);
	        	
		        for(int i=0;i<clusters.length;i++){
		        	
		        	boolean dir = new File(outputFolder).mkdir();
		        	File file = new File(outputFolder+"/Cluster"+i+".txt");

					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}

		        	FileHandler.exportDataset(clusters[i],file);
		        }
		 	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

	
	public static int[][] getClusters(int numberOfCluster, String outputFolder){
			 
			int [][] clusters = new int [numberOfCluster][];
			 
			 for (int i=0;i<numberOfCluster;i++){
				 List<Integer> indexes = new ArrayList<Integer>();
				 
				 try {
					 BufferedReader br = new BufferedReader(new FileReader(outputFolder+"/Cluster"+i+".txt"));
				     StringBuilder sb = new StringBuilder();
				     
				     String line = br.readLine();
		
				     int count = 0;
				     while (line != null) {
				         sb.append(line);
				         sb.append(System.lineSeparator());
				         int val = Integer.valueOf(line.split("\t")[0]);
				         indexes.add(val);
				         
				         // read next string
				         line = br.readLine();
				         count++;
				     }
				     
				     // do not take weights
				     int numberOfVars= indexes.size();
				     clusters[i]= new int[numberOfVars];
				     
				     for(int m=0;m<numberOfVars;m++){
				    	 clusters[i][m]=indexes.get(m);
				     }
				     String everything = sb.toString();
				     br.close();
				 }
				 catch(Exception e){
					 int z =0;
				 }
			 }
			 return clusters;
		 }
	
	
}



