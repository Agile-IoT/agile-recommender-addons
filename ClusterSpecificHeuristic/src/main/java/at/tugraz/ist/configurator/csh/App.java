package at.tugraz.ist.configurator.csh;

import java.util.List;

import org.chocosolver.solver.Model;

import at.tugraz.ist.configurator.csh.choco.CSHValueOrder;
import at.tugraz.ist.configurator.csh.choco.MapColoringCSP;
import at.tugraz.ist.configurator.csh.choco.MyChocoSolver;
import at.tugraz.ist.configurator.csh.choco.RandomCSPs;
import at.tugraz.ist.configurator.csh.clustering.Clusterer;
import at.tugraz.ist.configurator.csh.fileoperations.Writers;
import at.tugraz.ist.configurator.csh.learning.valueOrdering.ValueOrdering;
import at.tugraz.ist.configurator.csh.learning.variableOrdering.Learning;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    int cspmultiplier= 2;
    int numberOfProblems = 6;
    
    for (int n_csps=1;n_csps<cspmultiplier;n_csps++){
      for (int p=0;p<numberOfProblems;p++){
    	System.out.println( "number of csps: "+100*n_csps);
    	System.out.println( "p= "+p );
    	
        //System.out.println( "STEP-1: Get Problems" );
        int numberOfCSPs = 100*n_csps;
        int numberOfVars = 100;
        int maxDomain = 10;
        int max_numberOfCons = 5;
        //RandomCSPs testCSPs = new RandomCSPs();
        MapColoringCSP testCSPs = new MapColoringCSP();
        testCSPs.createTestCSPs(numberOfCSPs, numberOfVars, maxDomain, max_numberOfCons);
        Writers writers = new Writers();
        writers.writeToFile(testCSPs.getVars(), "TestCSP");
        
        // STEP-1 - DONE
        //System.out.println( "Test CSPs are created as; numberOfCSPs: "+ numberOfCSPs+" numberOfVars: "+ numberOfVars+" maxDomain: "+ maxDomain);
        //System.out.println( "DONE" );
        
        testCSPs.createOneTestCSP();
        
        // When comparison of cluster number needed, increase this
        int maxNumberOfClustersMultiplier=2;
        int maxNumberOfClusterType=1;
        
        boolean testTypesFlag= true;
     
      for (int clc=1;clc<maxNumberOfClustersMultiplier;clc++){
    	for (int ct=0;ct<maxNumberOfClusterType;ct++){
    	
    	//System.out.println( "clusterer type: "+ct);
        // STEP-2 
    	//System.out.println( "STEP-2: Cluster Problems" );
        Clusterer myclusterer = new Clusterer();
        int numberOfClusters = clc*5*n_csps;
        if(testTypesFlag)
        	numberOfClusters = 4;
        System.out.println( "number of clusters= "+numberOfClusters );
        
        int [][] clusters = null;
        		
        if (!testTypesFlag && clc==maxNumberOfClustersMultiplier-1 && maxNumberOfClustersMultiplier>2){
        	numberOfClusters = numberOfCSPs;
        	clusters = new int [numberOfClusters][1];
        	for(int w=0;w<numberOfClusters;w++)
        		clusters[w][0]=w;
        }
        else	
        	clusters = myclusterer.cluster(ct,"TestCSP",numberOfVars,numberOfClusters);
        
        // update the number of cluster based on the result set
        numberOfClusters = clusters.length;
        
        // CALCULATE MEANS OF THE CLUSTERS
        int [][] meanValues = new int [numberOfClusters][numberOfVars];
        for(int c=0;c<numberOfClusters;c++){
        	 for(int v=0;v<numberOfVars;v++){
        		int sum_of_v = 0;
             	for(int e=0;e<clusters[c].length;e++){
             		// get Vth value of the CSP in the cluster
             		sum_of_v += testCSPs.getVars()[clusters[c][e]][v].getValue();
             	}
             	int avg_of_v = 0;
             	
             	if (clusters[c].length!=0)
             	 avg_of_v = sum_of_v/clusters[c].length;
             	meanValues[c][v] = avg_of_v;
             }
        }
        System.out.println( "Number of Clusters: "+numberOfClusters);
        //System.out.println( "DONE" );
        
        
        // STEP-3 
        //System.out.println( "STEP-3: Learn Heuristics For Each Cluster" );
        // LEARN VARIABLE ORDERING
        int sizeOfPopulation = 10;
        Learning learn = new Learning();
        int[][] varOrderHeuristics = learn.learnHeuristicsForClusters(numberOfVars, maxDomain, numberOfClusters, sizeOfPopulation, testCSPs, clusters);
        // System.out.println( "LEARN VARIABLE ORDERING: DONE" );
        
        // LEARN VALUE ORDERING
        int sizeOfPopulation2 = 10;
        ValueOrdering valueOrdering = new ValueOrdering();
        CSHValueOrder [] valOrderHeuristics = valueOrdering.learnHeuristicsForClusters(numberOfVars, maxDomain, numberOfClusters, sizeOfPopulation2, testCSPs, clusters, varOrderHeuristics);
        //System.out.println( "LEARN VALUE ORDERING: DONE" );
        
        
        // STEP-5 
        //System.out.println( "STEP-5: Find Closest Cluster to the New Problem" );
        // USE EUCLIDEAN DISTANCE FORMULA
        double [] distances = new double [numberOfClusters];
        for(int c=0;c<numberOfClusters;c++){
        	 int sum_of_sqr_subtract = 0;
        	 for(int v=0;v<numberOfVars;v++){
        		 int subtract_v = testCSPs.getVars_newCSP()[v].getValue() - meanValues[c][v];
        		 int sqr_subtract_v = subtract_v*subtract_v;
        		 sum_of_sqr_subtract += sqr_subtract_v;
             }
        	 distances[c] = Math.sqrt(sum_of_sqr_subtract);
        }
        
        double min = distances[0];
        int cl = 0;
        for(int k=1;k<numberOfClusters-1;k++){
        	if(min>distances[k]){
        		min = distances[k];
        		cl= k;
        	}
        }
        //System.out.println("Closest cluster is: "+cl);
        //System.out.println( "DONE" );
        
        
        // STEP-6 
        //System.out.println( "STEP-6: Solve without any Heuristics" );
        MyChocoSolver mysolver = new MyChocoSolver();
        long time2 = mysolver.solveWithoutHeuristics(-1,testCSPs);
        System.out.println(time2);
        //System.out.println( "DONE" );
        
        
        // STEP-7
        System.out.println( "STEP-7: Solve with builtin Heuristics" );
        MyChocoSolver mysolver2 = new MyChocoSolver();
        long []times = mysolver2.solveWithBuiltinHeuristics(-1,testCSPs);
        //for(int t =0;t<times.length;t++)
        	//System.out.println(times[t]);
        //System.out.println( "DONE" );
        
        
        // STEP-8 
        System.out.println( "STEP-8: Solve with variable ordering Heuristic" );
        MyChocoSolver mysolver3 = new MyChocoSolver();
        long time = mysolver3.solveWithHeuristic(-1,testCSPs, varOrderHeuristics[cl],null,false);
        System.out.println(time);
        //System.out.println( "DONE" );
        
        // STEP-9 
        System.out.println( "STEP-9: Solve with value ordering Heuristic" );
        MyChocoSolver mysolver5 = new MyChocoSolver();
        long time5 = mysolver5.solveWithHeuristic(-1,testCSPs, null, valOrderHeuristics[cl],false);
        //System.out.println(time5);
        //System.out.println( "DONE" );
        
        
        // STEP-10 
        System.out.println( "STEP-10: Solve with variable and value ordering Heuristic" );
        MyChocoSolver mysolver4 = new MyChocoSolver();
        long time22 = mysolver4.solveWithHeuristic(-1,testCSPs, varOrderHeuristics[cl],valOrderHeuristics[cl],false);
        //System.out.println(time22);
        System.out.println( "DONE" );
        
        
      } // number of clusters
      } // type of clusterings
      } // problems 
   }// different numbers of csps
  }

}
