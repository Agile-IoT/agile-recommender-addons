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
import org.chocosolver.solver.variables.IntVar;
import org.springframework.beans.factory.annotation.Autowired;

import at.tugraz.ist.algorithms.MatrixFactorization;
import at.tugraz.ist.algorithms.MinMaxNormalization;
import at.tugraz.ist.algorithms.CollaborativeFiltering;
import at.tugraz.ist.algorithms.KNN;
import at.tugraz.ist.knowledgebases.Bike2KB;
import at.tugraz.ist.knowledgebases.CameraKB;
import at.tugraz.ist.knowledgebases.PCKB;

public class RunConfRix {

	@Autowired
	public static void main (String [] args){
		
		// *********************************************************************
		// ****************       SET TEST PARAMETERS      *********************
		// *********************************************************************
		
		// istype2 is not applying bitmap technique. so each var is stated with its value
		boolean istype2 = false;
		boolean isTestAccuracy = true;
		boolean isBikeKB = false;
		int r=1;
		VariableSelector [] varheuristics = new VariableSelector [13];
		
		
		
		// *********************************************************************
		// ****************       OFFLINE PHASE            *********************
		// *********************************************************************
		
		// *********************************************************************
		// STEP-1: GENERATE SOLUTIONS (sample solutions of BikeConfig+user reqs)
		// *********************************************************************
		Knowledgebase knowledgebase;
		if(isTestAccuracy)
			 knowledgebase = new Knowledgebase(new CameraKB(),isTestAccuracy);
		else{
			if(isBikeKB)
				 knowledgebase = new Knowledgebase(new Bike2KB(),isTestAccuracy);
			else
				 knowledgebase = new Knowledgebase(new PCKB(),isTestAccuracy);
		}
		int solnSize= 20;
		String solutionsFile = "Files/ConfigSolutions/Solutions_"+System.currentTimeMillis();
		String cameraFile = "Files/CameraDataset/CameraUserReqs.data";
		
		File file_soln = new File(solutionsFile);
		if (file_soln.exists()) 
			file_soln.delete();
		int [][] histTransactions;
		if(isTestAccuracy)
			histTransactions= knowledgebase.readHistoricalTransactions(cameraFile,solutionsFile);
		
		else
			histTransactions = knowledgebase.generateHistoricalTransactions(solnSize, solutionsFile,istype2);
		
		int numberOfVariables = histTransactions[0].length;
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
		int numberRecommendedItems=knowledgebase.numberOfVariables;
		double [][] p = null;
		double [][] q = null;
		
		
		try {
				dataModel = new FileDataModel(new File(solutionsFile));  // outputFolder+"/Problem_"+i
				MatrixFactorization.SVD(dataModel,numFeatures,numIterations,userID,numberRecommendedItems);
				p=MatrixFactorization.UF;
				q=MatrixFactorization.IF;
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		
		System.out.println("STEP-2 is completed.");
				

		
		// *********************************************************************
		// STEP-3: GENERATE/READ NEW PROBLEMS 
		// *********************************************************************
		RecommendationTasks recommendationTasks= new RecommendationTasks(knowledgebase.numberOfVariables,r);
		int numberOfProblems= 2;
		int numberOfComparedHeuristics = 78; 
		String outputDirectory =  "Files/ConfigDataset";
		int [][] reqs;
		 
		 // recommendationTasks.recomTasks[h][i].
		if(isTestAccuracy)
			reqs = recommendationTasks.generateDataset_fromFile(numberOfVariables,histTransactions, numberOfProblems, numberOfComparedHeuristics,varheuristics.length);
		else
			reqs = recommendationTasks.generateDataset(numberOfVariables,numberOfProblems,solnSize,solutionsFile, outputDirectory,istype2, numberOfComparedHeuristics,varheuristics.length,isBikeKB,isTestAccuracy);
		
		System.out.println("STEP-3 is completed.");
		

		// *********************************************************************
		// ****************       ONLINE   PHASE            ********************
		// *********************************************************************
		
		
		// *********************************************************************
		// STEP-4: FIND SIMILAR PROBLEM FOR EACH PROBLEM (knn, jackard dist., aggregate)
		// *********************************************************************
		// TODO: implement knn & jaccard dist. & aggregation
		
		long start = System.nanoTime();
		
		// find similar using euclidean distance
		int [] similarSolutionForEachProblem = new int [numberOfProblems];
		for(int i=0;i<numberOfProblems;i++){
			double minDist = 0;
			for (int s=0;s<histTransactions.length;s++){
				// NORMALIZE
				double [] reqs_normalized= MinMaxNormalization.normalize(reqs[i], knowledgebase.varsNotInModel);
				double [] soln_normalized= MinMaxNormalization.normalize(histTransactions[s], knowledgebase.varsNotInModel);
				//double dist = MF.euclidean_distance(reqs[i],solutions[s]);
				// DISTANCE
				double dist = MatrixFactorization.euclidean_distance(reqs_normalized,soln_normalized);
				if(s!=0 && dist<minDist){
					minDist = dist;
					similarSolutionForEachProblem[i] = s;
				}
			}
		}
		long end_step4 = System.nanoTime();
		System.out.println("STEP-4 is completed. Finding similar in the online phase, time spent: "+(end_step4-start));
		
		// *********************************************************************
		// STEP-5: FIND PREDICTIONS FOR EACH PROBLEM (array multiplication)
		// *********************************************************************
				// TODO: multiply aggregated UF with IF 
		
		double [][] predictions  = new double [numberOfProblems][];
		for(int i=0;i<numberOfProblems;i++){
			double [][] similarUsersFeatures = new double [1][];
			similarUsersFeatures[0] = p[similarSolutionForEachProblem[i]];
			double [][] predictionsForUser ;
			predictionsForUser = MatrixFactorization.multiplyByMatrix(similarUsersFeatures, q);
			predictions[i] = predictionsForUser[0];
		}
		
		long end_step5 =System.nanoTime();
		System.out.println("STEP-5 is completed. Matrix multiplication in the online phase, time spent:  "+(end_step5-end_step4));
		
		
		// *********************************************************************
		// STEP-6: CONVERT PREDICTIONS TO VALUE ORDERINGS
		// *********************************************************************
		for (int i=0;i<numberOfProblems;i++){
			int index = 0;
			for(int v=0;v<knowledgebase.numberOfVariables;v++){
				HashMap<Double,Integer> valuesOfv = new HashMap<Double,Integer>();    
				for(int d=0;d<knowledgebase.domainSizes[v];d++){
					valuesOfv.put(predictions[i][index],d);
					index++;
				}
				List<Double> mapKeys = new ArrayList<>(valuesOfv.keySet());
			    Collections.sort(mapKeys);
			    
			    for(int d=0;d<knowledgebase.domainSizes[v];d++){
			    	int val_index = valuesOfv.get(mapKeys.get(d));
			    	for (int h=0;h<varheuristics.length;h++){
			    		recommendationTasks.recomTasks[h][i].valueOrdering[v][d] = val_index; 
			    	}
			    }
			    
			}
		}
		long end =System.nanoTime();
		System.out.println("STEP-6 is completed. Converting into value ordering in the online phase, time spent:  "+(end-end_step5));
		
		// *********************************************************************
		// STEP-7: SOLVE PROBLEMS USING BUILT-IN VALUE ORDERINGS
		// *********************************************************************
		

		// WITHOUT USING COBARIX 
		// eliminate the first problems solving time
		recommendationTasks.recomTasks_copies[0][0].kb.getModelKB().getSolver().solve();
		
		// Compared Value Ordering Heuristics
		IntValueSelector valueorderingheuristics[]= new IntValueSelector[6];
		
		 valueorderingheuristics[0] = new IntDomainMax();
		 valueorderingheuristics[1] = new IntDomainMin();
		 valueorderingheuristics[2] = new IntDomainMedian();
		 valueorderingheuristics[3] = new IntDomainMiddle(true);
		 valueorderingheuristics[4] = new IntDomainRandom(1);
		 valueorderingheuristics[5] = new IntDomainRandomBound(1);
		 

	
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
						case 2: recommendationTasks.recomTasks_copies[index][i].kb.getModelKB().getSolver().setSearch(new ActivityBased(recommendationTasks.recomTasks_copies[index][i].kb.getVars()));break;
						case 3: varheuristics[3] =  new FirstFail(recommendationTasks.recomTasks_copies[index][i].kb.getModelKB()); break;
						case 4: varheuristics[4] =  new AntiFirstFail(recommendationTasks.recomTasks_copies[index][i].kb.getModelKB()); break;
						case 8: varheuristics[8] =  new InputOrder(recommendationTasks.recomTasks_copies[index][i].kb.getModelKB()); break;
						case 9: recommendationTasks.recomTasks_copies[index][i].kb.getModelKB().getSolver().setSearch( new DomOverWDeg(recommendationTasks.recomTasks_copies[index][i].kb.getVars(), (long) 0.01, valueorderingheuristics[h])); break;
						case 10: recommendationTasks.recomTasks_copies[index][i].kb.getModelKB().getSolver().setSearch( new ImpactBased(recommendationTasks.recomTasks_copies[index][i].kb.getVars(), false)); break;
						default: recommendationTasks.recomTasks_copies[index][i].seValOrdHeuristics(varheuristics[j],valueorderingheuristics[h]); break;
							
					}
					
					recommendationTasks.recomTasks_copies[index][i].kb.getModelKB().getSolver().solve();
					
				}
				index++;
				
				long end2=System.nanoTime();
				long avgtime2 = ((end2-start2))/(numberOfProblems-1); 
				//System.out.println("WITH HEURISTIC#"+j+"-"+h+": "+ avgtime2);
				if(j!=2 && j!=10 && j!=4 && j!=5)
				System.out.println(avgtime2);
			}
			
		}
		
		System.out.println("STEP-7 is completed.");
		

		// *********************************************************************
		// STEP-8: SOLVE PROBLEMS USING COBARIX
		// *********************************************************************
		System.out.println("STEP-8");
		for (int j=0;j<varheuristics.length;j++){
			// online spent time
			long start3=System.nanoTime();
			for (int i=0;i<numberOfProblems;i++){
				
				 
				switch(j){
					case 2: 
						recommendationTasks.recomTasks[j][i].kb.getModelKB().getSolver().setSearch(new ActivityBased(recommendationTasks.recomTasks[j][i].kb.getVars()));
						break;
					case 3: 
						varheuristics[3] =  new FirstFail(recommendationTasks.recomTasks[j][i].kb.getModelKB()); 
						recommendationTasks.recomTasks[j][i].setCOBARIXHeuristics(varheuristics[j]); 
						break;
					case 4: 
						varheuristics[4] =  new AntiFirstFail(recommendationTasks.recomTasks[j][i].kb.getModelKB()); 
						recommendationTasks.recomTasks[j][i].setCOBARIXHeuristics(varheuristics[j]); 
						break;
					case 8: 
						varheuristics[8] =  new InputOrder(recommendationTasks.recomTasks[j][i].kb.getModelKB()); 
						recommendationTasks.recomTasks[j][i].setCOBARIXHeuristics(varheuristics[j]); 
						break;
					case 9: 
						recommendationTasks.recomTasks[j][i].kb.getModelKB().getSolver().setSearch( new DomOverWDeg(recommendationTasks.recomTasks[j][i].kb.getVars(), (long) 0.01, recommendationTasks.recomTasks[j][i].getCOBARIXHeuristics())); 
						break;
					case 10: 
						recommendationTasks.recomTasks[j][i].kb.getModelKB().getSolver().setSearch( new ImpactBased(recommendationTasks.recomTasks[j][i].kb.getVars(), false)); 
						break;
					default: 
						recommendationTasks.recomTasks[j][i].setCOBARIXHeuristics(varheuristics[j]); 
						break;
				}
				
			   
			    recommendationTasks.recomTasks[j][i].kb.getModelKB().getSolver().solve();
				//System.out.println("Problem-"+i+"cobarix soln, value of Var0: "+((IntVar)(recommendationTasks.recomTasks[j][i].kb.getModelKB().getVar(0))).getValue());
				//System.out.println("Problem-"+i+"cobarix soln, value of Var1: "+((IntVar)(recommendationTasks.recomTasks[j][i].kb.getModelKB().getVar(1))).getValue());
			}
			long end3=System.nanoTime();
			
			long avgtime3 = ((end-start)+(end3-start3))/(numberOfProblems);
			//long avgtime3 = ((end3-start3))/(numberOfProblems);
			//System.out.println("varheuristics ID: "+j+", WITH COBARIX: "+ (avgtime3));
			if(j!=2 && j!=10 && j!=4 && j!=5)
				System.out.println(avgtime3);
		}
		
		System.out.println("STEP-8 is completed.");
		
		
		if(isTestAccuracy){
			
			// *********************************************************************
			// STEP-9: ACCURACY OF COBARIX
			// *********************************************************************
			// isTestAccuracy = true
			int [][] recommendationsCobarix = new int [numberOfProblems][knowledgebase.numberOfVariables];
			double accuracyTotal = 0;
			for (int h=0;h<3;h++){
				int id = 1;
				
				if (h==0)
					id=1;
				if (h==1)
					id=4;
				if (h==2)
					id=6;
				
				for (int i=0;i<numberOfProblems;i++){
					
					for(int j=0;j<knowledgebase.numberOfVariables;j++)
						recommendationsCobarix [i][j] = ((IntVar)(recommendationTasks.recomTasks[h][i].kb.getModelKB().getVar(j))).getValue();
					
					//System.out.println("Problem-"+i+"cobarix soln, value of Var0: "+recommendationsCobarix [i][0]);
					//System.out.println("Problem-"+i+"cobarix soln, value of Var1: "+recommendationsCobarix [i][1]);
				}
				
				double accuracy = recommendationTasks.getAccuracy(recommendationsCobarix,h,knowledgebase);
				System.out.println("varheuristics ID: "+h+", accuracy of COBARIX: "+ accuracy);
				accuracyTotal += accuracy;
			}
			
			System.out.println("avg accuracy of COBARIX: "+ accuracyTotal/3);
			System.out.println("STEP-9 is completed.");
			
			
			// *********************************************************************
			// STEP-10: ACCURACY OF KNN
			// *********************************************************************
			// isTestAccuracy = true
			int [] recommendationsKNN = new int [numberOfProblems];
			for (int i=0;i<numberOfProblems;i++){
				int [] similars = KNN.getKNN(1, recommendationTasks.reqs[i], knowledgebase.purchases, knowledgebase.kb);
				//int recommendedItem = KNN.aggregateProductID(similars, recommendationTasks);
				recommendationsKNN [i] = similars[0];
			}
			
			double accuracy_knn = recommendationTasks.getAccuracy(recommendationsKNN,knowledgebase);
			System.out.println("Accuracy of KNN: "+ accuracy_knn);
			System.out.println("STEP-10 is completed.");
			
			
			
			// *********************************************************************
			// STEP-11: ACCURACY OF Mahout CF Similarities
			// *********************************************************************
			// isTestAccuracy = true
			int [] recommendedIDs = new int [numberOfProblems];
			for (int m=0;m<4;m++){
				for (int i=0;i<numberOfProblems;i++){
					recommendedIDs[i] = CollaborativeFiltering.applyCollaborativeFiltering(recommendationTasks.reqs[i],knowledgebase.kb.getDomains(),m);
				}
				
				double accuracy_mahout = recommendationTasks.getAccuracy(recommendedIDs,knowledgebase);
				System.out.println("Accuracy of Mahout-"+m+": "+ accuracy_mahout);
			
			}
			System.out.println("STEP-11 is completed.");
		
		}
		
		System.out.println("All steps are completed.");
		
	}
	
}
