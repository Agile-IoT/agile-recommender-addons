package at.tugraz.ist.cobarix;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMax;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMedian;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMiddle;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandom;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandomBound;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.*;
import org.springframework.beans.factory.annotation.Autowired;

public class RunConfRix {

	@Autowired
	public static void main (String [] args){
		
		
		
		// istype2 is not applying bitmap technique. so each var is stated with its value
		boolean istype2 = false;
		
		// *********************************************************************
		// ****************       OFFLINE PHASE            *********************
		// *********************************************************************
		
		// *********************************************************************
		// STEP-1: GENERATE SOLUTIONS (sample solutions of BikeConfig+user reqs)
		// *********************************************************************
		Knowledgebase bikeProblem = new Knowledgebase();
		int solnSize= 20;
		String solutionsFile = "Files/BikeConfigSolutions/Solutions_"+solnSize;
		File file_soln = new File(solutionsFile);
		if (file_soln.exists()) 
			file_soln.delete();
		int [][] solutions = bikeProblem.generateSampleSolutions(solnSize, solutionsFile,istype2);
		System.out.println("STEP-1 is completed.");
		
		
		// *********************************************************************
		// STEP-2: GENERATE P and Q from SOLUTIONS
		// *********************************************************************
		// TODO: store UF and IF in objects
		DataModel dataModel;
		int numFeatures =3; // mxk, kxn -> k value
		int numIterations =2;
		int userID=solnSize;
		double lambda = 2;
		int numberRecommendedItems=bikeProblem.numberOfVariables; // 34 vars 
		//List<RecommendedItem> recommendedItems = null;
		double [][] p = null;
		double [][] q = null;
		
		
		try {
				dataModel = new FileDataModel(new File(solutionsFile));  // outputFolder+"/Problem_"+i
				MF.SVD(dataModel,numFeatures,numIterations,userID,numberRecommendedItems);
				p=MF.UF;
				q=MF.IF;
				//recommendedItems = MF.ALS(dataModel, numFeatures, numIterations, lambda, userID, numberRecommendedItems);
				//Spark.inputFile=problemFile;
				//Spark.main(null);
				//numberRecommendedItems = recommendedItems.size();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		
		System.out.println("STEP-2 is completed.");
				

		
		// *********************************************************************
		// STEP-3: GENERATE NEW PROBLEMS 
		// *********************************************************************
		HistoricalTransactions datasetGenerator= new HistoricalTransactions();
		int numberOfProblems= 3;
		int numberOfComparedHeuristics = 78; 
		String outputDirectory =  "Files/BikeConfigDataset";
		int [][] reqs = datasetGenerator.generateDataset(numberOfProblems,solnSize,solutionsFile, outputDirectory,istype2, numberOfComparedHeuristics);
		System.out.println("STEP-3 is completed.");
		

		// *********************************************************************
		// ****************       ONLINE   PHASE            ********************
		// *********************************************************************
		
		
		// *********************************************************************
		// STEP-4: FIND SIMILAR PROBLEM FOR EACH PROBLEM (knn, jackard dist., aggregate)
		// *********************************************************************
		// TODO: implement knn & jaccard dist. & aggregation
		
		long start =System.nanoTime();
		
		// find similar using euclidean distance
		int [] similarSolutionForEachProblem = new int [numberOfProblems];
		for(int i=0;i<numberOfProblems;i++){
			double minDist = 0;
			for (int s=0;s<solutions.length;s++){
				double dist = MF.euclidean_distance(reqs[i],solutions[s]);
				if(s!=0 && dist<minDist){
					minDist = dist;
					similarSolutionForEachProblem[i] = s;
				}
			}
		}
		long end_step4 =System.nanoTime();
		System.out.println("STEP-4 is completed: "+(end_step4-start));
		
		// *********************************************************************
		// STEP-5: FIND PREDICTIONS FOR EACH PROBLEM (array multiplication)
		// *********************************************************************
				// TODO: multiply aggregated UF with IF 
		
		double [][] predictions  = new double [numberOfProblems][];
		for(int i=0;i<numberOfProblems;i++){
			double [][] similarUsersFeatures = new double [1][];
			similarUsersFeatures[0] = p[similarSolutionForEachProblem[i]];
			double [][] predictionsForUser ;
			predictionsForUser = MF.multiplyByMatrix(similarUsersFeatures, q);
			predictions[i] = predictionsForUser[0];
		}
		
		long end_step5 =System.nanoTime();
		System.out.println("STEP-5 is completed: "+(end_step5-end_step4));
		
		
		// *********************************************************************
		// STEP-6: CONVERT PREDICTIONS TO VALUE ORDERINGS
		// *********************************************************************
		// TODO : update this part
		for (int i=0;i<numberOfProblems;i++){
			int index = 0;
			for(int v=0;v<bikeProblem.numberOfVariables;v++){
				HashMap<Double,Integer> valuesOfv = new HashMap<Double,Integer>();    
				for(int d=0;d<bikeProblem.domainSizes[v];d++){
					valuesOfv.put(predictions[i][index],d);
					index++;
				}
				
				List<Double> mapKeys = new ArrayList<>(valuesOfv.keySet());
			    Collections.sort(mapKeys);
			    
			    for(int d=0;d<bikeProblem.domainSizes[v];d++){
			    	int val_index = valuesOfv.get(mapKeys.get(d));
			    	datasetGenerator.bikeConfigProblems[i].valueOrdering[v][d] = val_index; 
			    }
			   		    
//				int variable = bikeProblem.hashmapIDs.get(x);
//				int lowBoundary = bikeProblem.lowBoundaries[variable];
//				int value = x - lowBoundary;
//				
//				int size = datasetGenerator.bikeConfigProblems[i].domainSizes[variable];
//				//datasetGenerator.bikeConfigProblems[p].valueOrdering[variable] = new int[size];
//				datasetGenerator.bikeConfigProblems[i].valueOrdering[variable][0] = value;
//				datasetGenerator.bikeConfigProblems[i].valueOrdering[variable][value] = 0;
			    
			}
		}
		long end =System.nanoTime();
		System.out.println("STEP-6 is completed: "+(end-end_step5));
		
		// *********************************************************************
		// STEP-7: SOLVE PROBLEMS USING BUILT-IN VALUE ORDERINGS
		// *********************************************************************
		

		// WITHOUT USING COBARIX 
		// eliminate the first problems solving time
		datasetGenerator.bikeConfigProblems_copies[0][0].modelKB.getSolver().solve();
		
		// Compared Value Ordering Heuristics
		IntValueSelector valueorderingheuristics[]= new IntValueSelector[6];
		
		 valueorderingheuristics[0] = new IntDomainMax();
		 valueorderingheuristics[1] = new IntDomainMin();
		 valueorderingheuristics[2] = new IntDomainMedian();
		 valueorderingheuristics[3] = new IntDomainMiddle(true);
		 valueorderingheuristics[4] = new IntDomainRandom(1);
		 valueorderingheuristics[5] = new IntDomainRandomBound(1);
		 

		VariableSelector [] varheuristics = new VariableSelector [13];
		 varheuristics[0] = new Smallest();
		 varheuristics[1] = new Largest();
		 varheuristics[2] = null; //new ActivityBased<>();
		 varheuristics[3] = null; //new FirstFail(null);
		 varheuristics[4] = null; //new AntiFirstFail(null);
		 varheuristics[5] = new Cyclic<>();
		 varheuristics[6] = new MaxRegret();
		 varheuristics[7] = new Occurrence<>();
		 varheuristics[8] = null; // new InputOrder<>(null);
		 varheuristics[9] = null; //new DomOverWeg();
		 varheuristics[10] = null; //new ImpactBased();
		 varheuristics[11] = new GeneralizedMinDomVarSelector();
		 varheuristics[12] = new org.chocosolver.solver.search.strategy.selectors.variables.Random<>((long)0.011);
		
		int index=0;
		for (int j=0;j<varheuristics.length;j++){
			
			for(int h=0;h<valueorderingheuristics.length;h++){
				long start2=System.nanoTime();
				for (int i=1;i<numberOfProblems;i++){
					//if(j!=numberOfComparedHeuristics-1)
					switch(j){
						case 2: datasetGenerator.bikeConfigProblems_copies[index][i].modelKB.getSolver().setSearch(new ActivityBased(datasetGenerator.bikeConfigProblems_copies[index][i].vars));break;
						case 3: varheuristics[3] =  new FirstFail(datasetGenerator.bikeConfigProblems_copies[index][i].modelKB); break;
						case 4: varheuristics[4] =  new AntiFirstFail(datasetGenerator.bikeConfigProblems_copies[index][i].modelKB); break;
						case 8: varheuristics[8] =  new InputOrder(datasetGenerator.bikeConfigProblems_copies[index][i].modelKB); break;
						case 9: datasetGenerator.bikeConfigProblems_copies[index][i].modelKB.getSolver().setSearch( new DomOverWDeg(datasetGenerator.bikeConfigProblems_copies[index][i].vars, (long) 0.01, valueorderingheuristics[h])); break;
						case 10: datasetGenerator.bikeConfigProblems_copies[index][i].modelKB.getSolver().setSearch( new ImpactBased(datasetGenerator.bikeConfigProblems_copies[index][i].vars, false)); break;
						default: datasetGenerator.bikeConfigProblems_copies[index][i].seValOrdHeuristics(varheuristics[j],valueorderingheuristics[h]); break;
							
					}
					
					datasetGenerator.bikeConfigProblems_copies[index][i].modelKB.getSolver().solve();
					
				}
				index++;
				long end2=System.nanoTime();
				
				long avgtime2 = ((end2-start2))/(numberOfProblems-1); 
				System.out.println("WITH HEURISTIC#"+j+"-"+h+": "+ avgtime2);
			}
		}
		
		System.out.println("STEP-7 is completed.");
		

		// *********************************************************************
		// STEP-8: SOLVE PROBLEMS USING COBARIX
		// *********************************************************************
		
		for (int j=0;j<varheuristics.length;j++){
			// online spent time
			long start3=System.nanoTime();
			for (int i=0;i<numberOfProblems;i++){
				datasetGenerator.bikeConfigProblems[i].setCOBARIXHeuristics(varheuristics[j]);
				datasetGenerator.bikeConfigProblems[i].modelKB.getSolver().solve();
			}
			long end3=System.nanoTime();
			
			long avgtime3 = ((end-start)+(end3-start3))/(numberOfProblems); 
			System.out.println("WITH COBARIX: "+ (avgtime3));
		}
		
		System.out.println("STEP-8 is completed.");
		
		
		System.out.println("All steps are completed.");
		
	}
	
	
	
	
}
