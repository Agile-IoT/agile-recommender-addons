package at.tugraz.ist.configurator.csh.learning.variableOrdering;

import java.util.Arrays;

import at.tugraz.ist.configurator.csh.choco.MyChocoSolver;
import at.tugraz.ist.configurator.csh.choco.RandomCSPs;
import at.tugraz.ist.configurator.csh.choco.TestCSP;


public class Learning {

	
	 public int[] geneToOrder(byte[]gene, int numberOfvars){
		 // input : 010 01 1 -> v0:2, v1:1, v2:3
		 // output: 102 -> order or variables
		 
		 int [] result = new int[numberOfvars];
		 int index=0;
		 int order=0;
		 boolean [] orders = new boolean[numberOfvars];
		 Arrays.fill(orders, Boolean.FALSE);
		 
		 for(int i=0;i<numberOfvars;i++){
			 // 5, 4 ,3, 2, 1
			 int readNumberOfBytes = numberOfvars-i;
			
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
	 
	 public void testPopulationOverCluster(Population myPop, int clusterIndex, TestCSP csps, int [][] clusters){
		  long totalRunningTimeForCluster = 0;
		  int indivdiaulIndex = 0;
		  int sizeOfPopulation = myPop.size();
		  
		
		  for(int i=0;i<sizeOfPopulation;i++){
			  indivdiaulIndex = i;
			  int [] variableOrder = myPop.getIndividual(i).getGenes();
			
			  for (int md=0;md<clusters[clusterIndex].length;md++){
					 int modelIndex = clusters[clusterIndex][md];
					 MyChocoSolver mysolver = new MyChocoSolver();
					 long time = mysolver.solveWithHeuristic(modelIndex, csps, variableOrder,null,true);
					 totalRunningTimeForCluster += time;
			  }
			  
			  // bu gen icin bu clusterda hesaplanan ortalama running time
			  if (clusters[clusterIndex].length>0){
				  long avg = totalRunningTimeForCluster / (clusters[clusterIndex].length);
				  myPop.getIndividual(i).setFitness(avg);
			  }
		 }
	 }

	 public int[][] learnHeuristicsForClusters(int numberOfvars, int maxDomainSize, int numberOfclusters, int sizeOfPopulation, TestCSP csps, int [][]clusters){
		 
		 int sizeOfGene = numberOfvars;
		 int[][] ordersOfVariables = new int[numberOfclusters][numberOfvars];
		
		 // FIND VARIABLE AND VALUE ORDERING FOR EACH CLUSTER
		 // CLUSTER
		 for (int cl=0;cl<numberOfclusters;cl++){
			 
			 Population myPop = new Population(sizeOfPopulation,sizeOfGene,true,cl);
			 
			 testPopulationOverCluster(myPop,cl,csps,clusters);
			  
			 int generationCount = 0; 
			 long startTime = System.nanoTime();
			 long currentTime = System.nanoTime();
			 
			 long fitness = 1000000;
			 
			 while(fitness > 20000 && generationCount<10){ 
			
			   // System.out.println("gen-"+generationCount+": "+myPop.getFittest().getFitness() );
			 
			   generationCount++; 
			
			   Algorithm alg = new Algorithm();
			   myPop = alg.evolvePopulation(myPop,cl,maxDomainSize); 
			   
			   testPopulationOverCluster(myPop,cl,csps,clusters);
			 
			   fitness =myPop.getFittest().getFitness();
			
			 } 
			 
			 int [] varOrder = myPop.getFittest().getGenes();
			
			 ordersOfVariables[cl] = varOrder;
		 }
		 
		 return ordersOfVariables;
		 
	 }
	 
	 
	 
}
