package at.tugraz.ist.configurator.csh.clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.weka.WekaClusterer;
import weka.clusterers.XMeans;
import weka.clusterers.EM;
import weka.clusterers.CLOPE;
import weka.clusterers.FarthestFirst;
import weka.clusterers.FilteredClusterer;
import weka.clusterers.sIB;


public class Clusterer {
	

	public int[][] cluster(int clusteringType, String modelsName, int numberOfvars, int numberOfClusters){
		
		try {
	        /* Load a dataset */
	        Dataset data;
			
			data = FileHandler.loadDataset(new File("Files/inputs/"+modelsName+".data"), numberOfvars, ",");
			Dataset[] clusters = null;
			
	        switch(clusteringType){
		        case 0:
		        	KMeans km = new KMeans(numberOfClusters);
		        	clusters = km.cluster(data);
		        	break;
		        case 1:
		        	XMeans xm = new XMeans();
		        	WekaClusterer jmlxm = new WekaClusterer(xm);
		        	clusters = jmlxm.cluster(data);
		        	break;
		        case 2:
		        	EM em = new EM();
		        	WekaClusterer jmlem = new WekaClusterer(em);
		        	clusters = jmlem.cluster(data);
		        	break;
		        case 3:
		        	CLOPE clope = new CLOPE();
		        	WekaClusterer jmlclope = new WekaClusterer(clope);
		        	clusters = jmlclope.cluster(data);
		        	break;
		        case 4:
		        	FarthestFirst ff = new FarthestFirst();
		        	WekaClusterer jmlff = new WekaClusterer(ff);
		        	clusters = jmlff.cluster(data);
		        	break;
		        case 5:
		        	FilteredClusterer fc = new FilteredClusterer();
		        	WekaClusterer jmlfc = new WekaClusterer(fc);
		        	clusters = jmlfc.cluster(data);
		        	break;
//		        case 6:
//		        	sIB sib = new sIB();
//		        	WekaClusterer jmlsib = new WekaClusterer(sib);
//		        	clusters = jmlsib.cluster(data);
//		        	break;
		        default:
		        	KMeans km2 = new KMeans(numberOfClusters);
		        	clusters = km2.cluster(data);
		        	break;
	        }
			numberOfClusters = clusters.length;
	       
	        for(int i=0;i<clusters.length;i++){
	        	
	        	boolean dir = new File("Files/outputs/"+modelsName).mkdir();
	        	File file = new File("Files/outputs/"+modelsName+"/Cluster"+i+".txt");

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

		
		return getClusters(modelsName, numberOfClusters);
	}

	public int[][] getClusters(String modelsName,int numberOfClusters){
		 
		 // "kmeans2/seda/outputs/"+modelsName+"/Cluster"+i+".txt"
		 int [][] clusters = new int [numberOfClusters][];
		 
		 for (int i=0;i<numberOfClusters;i++){
			 List<Integer> indexes = new ArrayList<Integer>();
			 
			 try {
				 BufferedReader br = new BufferedReader(new FileReader("Files/outputs/"+modelsName+"/Cluster"+i+".txt"));
			     StringBuilder sb = new StringBuilder();
			     
			     String line = br.readLine();
			     
			     //System.out.println(line);
			     while (line != null) {
			         sb.append(line);
			         sb.append(System.lineSeparator());
			         int val = Integer.valueOf(line.split("\t")[0]);
			         indexes.add(val);
			         
			         // read next string
			         line = br.readLine();
			     }
			     clusters[i]= new int[indexes.size()];
			     for(int m=0;m<indexes.size();m++){
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
