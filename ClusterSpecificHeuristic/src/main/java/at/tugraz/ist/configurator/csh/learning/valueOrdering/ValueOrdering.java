package at.tugraz.ist.configurator.csh.learning.valueOrdering;

import java.util.Arrays;

import at.tugraz.ist.configurator.csh.choco.CSHValueOrder;
import at.tugraz.ist.configurator.csh.choco.MyChocoSolver;
import at.tugraz.ist.configurator.csh.choco.RandomCSPs;
import at.tugraz.ist.configurator.csh.choco.TestCSP;


public class ValueOrdering {

	
	 public int[] geneToOrder(byte[]gene, int numberOfVariables){
		 // input : 010 01 1 -> v0:2, v1:1, v2:3
		 // output: 102 -> order or variables
		 
		 int [] result = new int[numberOfVariables];
		 int index=0;
		 int order=0;
		 boolean [] orders = new boolean[numberOfVariables];
		 Arrays.fill(orders, Boolean.FALSE);
		 
		 for(int i=0;i<numberOfVariables;i++){
			 // 5, 4 ,3, 2, 1
			 int readNumberOfBytes = numberOfVariables-i;
			
			 for(int j=0;j<readNumberOfBytes;j++){
				 if(gene[index+j]==1){
					 order = j;
					 break;
				 }
			 }
			 while(orders[order]!=false){
				 order += 1;
			 }
			 orders[order] = true;
			 // i :1 ->(v1), order:0 -> (first var) 
			 result[order] = i;
			 index += readNumberOfBytes;
		 }
		 return result;
	 }
	 	 
	 public void testPopulationOverCluster(Population myPop, int clusterIndex, TestCSP csps, int [][] clusters,  int[][] varOrderHeuristics){
		  long totalRunningTimeForCluster = 0;
		  int indivdiaulIndex = 0;
		  int sizeOfPopulation = myPop.size();
		  
		
		  for(int i=0;i<sizeOfPopulation;i++){
			  indivdiaulIndex = i;
			  
			  int[] intValueOrder = myPop.getIndividual(i).getGenes();
			  CSHValueOrder valueOrder = new CSHValueOrder(intValueOrder);
			
			  for (int md=0;md<clusters[clusterIndex].length;md++){
					 int modelIndex = clusters[clusterIndex][md];
					 MyChocoSolver mysolver = new MyChocoSolver();
					 long time = mysolver.solveWithHeuristic(modelIndex, csps, varOrderHeuristics[clusterIndex], valueOrder,true);
					 totalRunningTimeForCluster += time;
			  }
			  
			  // bu gen icin bu clusterda hesaplanan ortalama running time
			  if (clusters[clusterIndex].length>0){
				  long avg = totalRunningTimeForCluster / (clusters[clusterIndex].length);
				  myPop.getIndividual(i).setFitness(avg);
			  }
			  
		 }
	 }

	 public CSHValueOrder[] learnHeuristicsForClusters(int numberOfvars, int maxDomainSize, int numberOfclusters, int sizeOfPopulation, TestCSP csps, int [][]clusters,  int[][] varOrderHeuristics){
		 
		 int sizeOfGene = numberOfvars;
		 // int[][] ordersOfVariables = new int[numberOfclusters][numberOfvars];
		 CSHValueOrder[] valueOrders = new CSHValueOrder[numberOfclusters];
		
		 // FIND VARIABLE AND VALUE ORDERING FOR EACH CLUSTER
		 // CLUSTER
		 for (int cl=0;cl<numberOfclusters;cl++){
			 
			 Population myPop = new Population(sizeOfPopulation,sizeOfGene,true,cl);
			 
			 testPopulationOverCluster(myPop,cl,csps,clusters,varOrderHeuristics);
			  
			 int generationCount = 0; 
			 long startTime = System.nanoTime();
			 long currentTime = System.nanoTime();
			 
			 long fitness = 1000000;
			 
			 while(fitness > 20000 && generationCount<10){ 
			
			   // System.out.println("gen-"+generationCount+": "+myPop.getFittest().getFitness() );
			 
			   generationCount++; 
			
			   Algorithm alg = new Algorithm();
			   myPop = alg.evolvePopulation(myPop,cl,maxDomainSize); 
			   
			   testPopulationOverCluster(myPop,cl,csps,clusters,varOrderHeuristics);
			 
			   fitness =myPop.getFittest().getFitness();
			
			 } 
			 
			 int [] varOrder = myPop.getFittest().getGenes();
			
			 valueOrders[cl] = new CSHValueOrder(varOrder);
		 }
		 
		 return valueOrders;
		 
	 }
	 
	 
}
