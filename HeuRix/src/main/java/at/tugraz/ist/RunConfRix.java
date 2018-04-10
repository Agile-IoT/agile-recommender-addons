package at.tugraz.ist;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;

public class RunConfRix {

	public static void main (String [] args){
		
		// STEP-1: generate solutions (sample solutions of BikeConfig+user reqs)
		BikeConfig bikeProblem = new BikeConfig();
		int solnSize= 5;
		String solutionsFile = "Files/BikeConfigSolutions/Solutions_"+solnSize;
		bikeProblem.generateSampleSolutions(solnSize, solutionsFile);
		System.out.println("STEP-1 is completed.");
		
		// STEP-2: generate dataset with problems
		BikeConfigReqs datasetGenerator= new BikeConfigReqs();
		int numberOfProblems= 2;
		String outputDirectory =  "Files/BikeConfigDataset";
		datasetGenerator.generateDataset(numberOfProblems,solnSize,solutionsFile, outputDirectory);
		System.out.println("STEP-2 is completed.");
		
		// STEP-3: apply MF-SVD to each problem+dataset
		DataModel dataModel;
		int numFeatures =3; // mxk,kxn -> k value
		int numIterations =2;
		int userID=solnSize;
		double lambda = 2;
		int numberRecommendedItems=bikeProblem.numberOfVariables; // 34 vars 
		List<RecommendedItem> recommendedItems = null;
		
		for (int i=0;i<numberOfProblems;i++){
			String problemFile = outputDirectory+"/Problem_"+i;
			try {
				dataModel = new FileDataModel(new File(problemFile));  // outputFolder+"/Problem_"+i
				//recommendedItems = MF.SVD(dataModel,numFeatures,numIterations,userID,numberRecommendedItems);
				//recommendedItems = MF.ALS(dataModel, numFeatures, numIterations, lambda, userID, numberRecommendedItems);
				Spark.inputFile=problemFile;
				Spark.main(null);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		System.out.println("STEP-3 is completed.");
		
		// STEP-4: convert recommendations into value ordering heuristics for each problem
		for (int p=0;p<numberOfProblems;p++){
			for(int i=0;i<numberRecommendedItems;i++){
				int id = (int) recommendedItems.get(i).getItemID();
				int variable = bikeProblem.hashmapIDs.get(id);
				int lowBoundary = bikeProblem.lowBoundaries[variable];
				int value = (int)id - lowBoundary;
				int size = datasetGenerator.bikeConfigProblems[p].domainSizes[variable];
				//datasetGenerator.bikeConfigProblems[p].valueOrdering[variable] = new int[size];
				datasetGenerator.bikeConfigProblems[p].valueOrdering[variable][0] = value;
				datasetGenerator.bikeConfigProblems[p].valueOrdering[variable][value] = 0;
				datasetGenerator.bikeConfigProblems[p].setHeuristics();
			}
			
		}
		System.out.println("STEP-4 is completed.");
		
		
		
		// STEP-5: solve problems using this heuristics
		//for (int p=0;p<numberOfProblems;p++){
//			long start=System.nanoTime();
//			datasetGenerator.bikeConfigProblems[0].bikeModel.getSolver().solve();
//			long end=System.nanoTime();
//			System.out.println(end-start);
		//}
		
		
		for (int p=0;p<numberOfProblems;p++){
			long start=System.nanoTime();
			datasetGenerator.bikeConfigProblems_copies[0].bikeModel.getSolver().solve();
			long end=System.nanoTime();
			System.out.println(end-start);
		}
		
		
		System.out.println("STEP-5 is completed.");
		
		System.out.println("All steps are completed.");
		
	}
	
	
}
