package at.tugraz.ist.configurator.CSD;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.tugraz.ist.configurator.ChocoExtensions.CSP;
import at.tugraz.ist.configurator.ChocoExtensions.ChocoDuplications;
import at.tugraz.ist.configurator.ChocoExtensions.Constraint_Extension;
import at.tugraz.ist.configurator.ChocoExtensions.Constraints_Singleton;
import at.tugraz.ist.configurator.ChocoExtensions.FileToChocoModel;
import at.tugraz.ist.configurator.Clustering.KMeansClustering;
import at.tugraz.ist.configurator.Learning.LearningHeuristics;
import at.tugraz.ist.configurator.fileOperations.NormalizeFileForClustering;
import at.tugraz.ist.configurator.fileOperations.WriteToFile;


public class TestCSD {
	
	 static String modelsName = "CSD";
	 static FileWriter writer = null;
	 static int numberOfVariables;
	 static int numberOfClusters ;
	 static int numberOfUsers ;
	 static int [][] clusters ;
	 static int diagnosisType;
	 static int m_cardinality;
	 static List<int[]> variableOrders ;
	 static int numberOfWeights;
	 static float avgPresicion;
	 static float avgTime;
	 static float avgCardinality;
	 static float avgConsChecks;
	 
	 static String userConstraintsFile= "files\\inputs\\CSD_Model.data";
	 static String userConstraintsFileNormalized= "files\\inputs\\CSD_Model.data_normalized";
	 static String productTableFile= "files\\inputs\\CSD_ProductPortfolio.data";
	 static String outputFolder= "files\\outputs\\CSD_Model\\";
	 static String outputFile ;
	 static String outputFile_AvgPresicion;
	
	
	 public static void main(String []args){
		 
		int test_index = Integer.valueOf(args[0]); // 1
		numberOfWeights = Integer.valueOf(args[1]); //3;
		numberOfVariables = Integer.valueOf(args[2]); //10;
		numberOfClusters = Integer.valueOf(args[3]); //4; 
		diagnosisType = Integer.valueOf(args[4]); //0;
		m_cardinality = Integer.valueOf(args[5]); //0;
		
		outputFile=  outputFolder + "\\Type="+diagnosisType+" Clusters="+numberOfClusters+" testIndex:"+test_index+".data";
		outputFile_AvgPresicion = outputFolder + "\\AveragePrecisions.data";
		// numberOfClusters = Integer.valueOf(args[0]);
		// diagnosisType = Integer.valueOf(args[1]);
		
		System.out.println("####################################");
		System.out.println("test_index: "+ test_index);
		System.out.println("numberOfVariables: "+ numberOfVariables);
		System.out.println("numberOfWeights: "+ numberOfWeights);
		System.out.println("NumberOfClusters: "+ numberOfClusters);
		System.out.println("Diagnosis Type: "+ diagnosisType);
		System.out.println("####################################");
		
		switch(diagnosisType){
			case 0:
				LearningHeuristics.targetValueOfFitness = 1;
				LearningHeuristics.type = 0; // DONE 
				diagnoseByFastDiag_BestPrecision_withCSH();
				//diagnoseBy_DiagRect_Strategy(3);
				break;
			case 1:
				LearningHeuristics.targetValueOfFitness = 0;
				LearningHeuristics.type = 1; // DONE 
				diagnoseByFastDiag_Fastest_withCSH();
				//diagnoseBy_DiagRect_Strategy(3);
				break;
			case 2:
				diagnoseByFastDiag(); // DONE 
				break;
			case 3:
				LearningHeuristics.targetValueOfFitness = 1;
				LearningHeuristics.type = 3; 
				diagnoseByGeneticAlgorithm_BestPrecision();
				break;
			case 4:
				LearningHeuristics.targetValueOfFitness = 0;
				LearningHeuristics.type = 4;
				diagnoseByGeneticAlgorithm_Fastest();
				break;
				
			case 5:
				LearningHeuristics.targetValueOfFitness = -1;
				LearningHeuristics.type = 5;
				diagnoseByFastDiag_Fastest_withRandom();
				break;
				
		    /// added for IJCAI
			case 6: // precision
				LearningHeuristics.targetValueOfFitness = 1;
				LearningHeuristics.type = 6; // DONE 
				diagnoseByFlexDiag_withHeuristics(m_cardinality);
				break;
				
			case 7: // time
				LearningHeuristics.targetValueOfFitness = -1;
				LearningHeuristics.type = 7; // DONE 
				diagnoseByFlexDiag_withHeuristics(m_cardinality);
				break;
				
			case 8: // card
				LearningHeuristics.targetValueOfFitness = -1;
				LearningHeuristics.type = 8; // DONE 
				diagnoseByFlexDiag_withHeuristics(m_cardinality);
				break;
			case 9: // mix
				LearningHeuristics.targetValueOfFitness = 200;
				LearningHeuristics.type = 9; // DONE 
				diagnoseByFlexDiag_withHeuristics(m_cardinality);
				break;
				
			case 10: // flexdiag
				LearningHeuristics.targetValueOfFitness = 0;
				LearningHeuristics.type = 10; // DONE 
				diagnoseByFlexDiag(m_cardinality);
				break;
				
				
			default:
				LearningHeuristics.type = 2; // DONE 
				diagnoseByFastDiag();
				break;
			}
			
			List<String> line = new ArrayList<String>();
			avgTime = 0 - avgTime;
			line.add(diagnosisType+"\t\t"+m_cardinality+"\t\t"+avgPresicion+"\t\t"+avgTime+"\t\t"+avgCardinality+"\t\t"+avgConsChecks);
			WriteToFile.writeFile(outputFile_AvgPresicion, line, true, true,true);
			System.out.println("####################################");
	  }
	
	
	 // LOAD INPUTS
	 public static void step1and2(){
		 
		// STEP-1 : generate the original problem with products table
				 Constraints_Singleton.getInstance().setOriginalCSP(FileToChocoModel.createOriginalCSP(productTableFile));
				 numberOfVariables = Constraints_Singleton.getInstance().getIntVarList_extension__UserRequirements().size()-1;
				 
				 // print products
				 int numberOfProductConstraints = Constraints_Singleton.getInstance().getOriginalCSP().numberOfProducts;
				 int constrIndex = 0;
				 
				 System.out.println("####################################");
				 System.out.println("PRODUCT TABLE");
				 System.out.println("Number of Products: "+numberOfProductConstraints);
				 System.out.println("Number of Variables: "+numberOfVariables);
				 
				 for(int i=0;i<numberOfProductConstraints;i++){
					 System.out.print("PRODUCT ID-: "+i+" => ");
					 for(int m=0;m<numberOfVariables;m++){
						 int constID = Integer.valueOf(Constraints_Singleton.getInstance().getOriginalCSP().constraints_products.get(constrIndex).getName());
						 Constraint_Extension c = Constraints_Singleton.getInstance().getConstraintList_extension__UserRequirements().get(constID);
						 System.out.print(" ("+c.getVar_1_ID()+" = "+c.getValue_1()+" )");
						 constrIndex++;
					 }
					 System.out.println();
				 }
				 System.out.println("####################################");
				
				 // STEP-1 is DONE
				 
				 
				 // STEP-2 : get user_constraints as models based on the original problem
				 Constraints_Singleton.getInstance().setCSPs_tobe_Clustered(FileToChocoModel.createUserModels(Constraints_Singleton.getInstance().getOriginalCSP(),userConstraintsFile));
				 numberOfUsers = Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().size();
				 // STEP-2 is DONE
				 
		 
	 }
	 
	 // CLUSTER AND LEARN
	 public static void step3and4(){
		 
		 // STEP-3 : apply clustering
		
		 NormalizeFileForClustering.normalize(userConstraintsFile, productTableFile, numberOfVariables,numberOfWeights);
		 
		 //int numberOfVariablesForClustering = numberOfVariables+3;
		 int numberOfVariablesForClustering = numberOfVariables;
		 KMeansClustering.applyKMeans(userConstraintsFileNormalized,numberOfVariablesForClustering,numberOfClusters,outputFolder);
		 clusters = KMeansClustering.getClusters(numberOfClusters,outputFolder);
		 
		 
		 // print clustered user models
		 System.out.println("####################################");
		 System.out.println("USER MODELS");
		 System.out.println("Number of Users: "+numberOfUsers);
		 System.out.println("Number of Clusters: "+numberOfClusters);
		 
		 for(int i=0;i<clusters.length;i++){
			 
			 System.out.println("Cluster"+i+" size= "+clusters[i].length+" :");
			 // get Model
			 for(int v=0;v<clusters[i].length;v++){
				 int modelIndex = clusters[i][v];
				 System.out.print("ID of User Model: "+modelIndex+" => ");
				 for(int m=0;m<numberOfVariables;m++){
					 System.out.print(" "+Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(modelIndex).constraints_user.get(m));
				 }
				 System.out.println();
			 }
			 System.out.println();
		 }
		 System.out.println("####################################");
		 // STEP-3 is DONE
		 
 
		
		 
		 // STEP-4 : learn heuristics
		 variableOrders = LearningHeuristics.learnHeuristicsForClusters(numberOfVariables,clusters,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered());
		 for(int i=0;i<variableOrders.size();i++){
			 System.out.println("VARIABLE ORDER FOR CLUSTER-"+i+":");
			 for(int v=0;v<numberOfVariables;v++){
				 System.out.print(" "+variableOrders.get(i)[v]);
			 }
			 System.out.println();
		 }
		// STEP-4 is DONE
		 
	 }
	
	 
	 
	// LearningHeuristics.type = 5;
	 public static void diagnoseByFastDiag_Fastest_withRandom(){
		 // LOAD INPUTS
		 step1and2();
		 
		 // SKIP CLUSTER AND LEARN
		 // step3and4();
		 
		 
		 float totalRndPresicion=0;
		 float totalRndavgTime=0;
		 
		 // take averages of the random ordering outputs
		 for (int i=0;i<10;i++){
			 
			 List<Integer> orderingInputs = new ArrayList<Integer>(10);
			 for(int m=0;m<10;m++){
				 orderingInputs.add(m, m);
			 }
			 
			 
			 int[] randomVariableOrder = new int [10];
			 
			 for(int v=0;v<10;v++){
				 Random randomizer = new Random();
				 randomVariableOrder[v] = orderingInputs.get(randomizer.nextInt(orderingInputs.size()));
				 for(int r=0;r<orderingInputs.size();r++){
					 if(randomVariableOrder[v] == orderingInputs.get(r))
						 orderingInputs.remove(r);
				 }
			 }
			 
			 // STEP-5 : apply FastDiag
			 List<String> lines =  new ArrayList<String>();
			 for(int u=0;u<numberOfUsers;u++){
						 	CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(u),null,0,0,null);
						 	float [] resp = LearningHeuristics.diagnoseCSP_FastDiag(model, randomVariableOrder);
						 	lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
							avgPresicion += resp[0];
							avgTime += resp[1];
			 }
			 WriteToFile.writeFile(outputFile,lines, true, true,false);
		     // STEP-5 is DONE
					 
			 avgPresicion = avgPresicion / (float)numberOfUsers;
			 totalRndPresicion += avgPresicion;
			 avgTime = avgTime / (float)numberOfUsers;
			 totalRndavgTime += avgTime;
			
		 }
		 
		 avgPresicion = totalRndPresicion/10;
		 avgTime = totalRndavgTime/10;
		 System.out.println("avgPresicion: "+avgPresicion);
		 System.out.println("avgTime: "+avgTime);
	 }
	 	 
	 // LearningHeuristics.type = 0;
	 public static void diagnoseByFastDiag_BestPrecision_withCSH(){
		 
		 step1and2();
		 
		 step3and4();
		
		 // STEP-5 : apply FastDiag
		 List<String> lines =  new ArrayList<String>();
		 for(int i=0;i<numberOfUsers;i++){
			 	CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
			 	//CSP model = ChocoDuplications.duplicateModel(Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i), String.valueOf(Math.random()));
				float [] resp = LearningHeuristics.diagnoseCSP_FastDiag(model, variableOrders.get(getClusterID(i)));
				lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
				avgPresicion += resp[0];
				avgTime += resp[1];
		 }
		 WriteToFile.writeFile(outputFile,lines, true, true,false);
		 // STEP-5 is DONE
		 
		 avgPresicion = avgPresicion / (float)numberOfUsers;
		 avgTime = avgTime / (float)numberOfUsers;
		 System.out.println("CSD test is completed");
		 
	 }
	 
	 
	 // LearningHeuristics.type = 1;
	 public static void diagnoseByFastDiag_Fastest_withCSH(){
		 
//			
//		 // STEP-1 : generate the original problem with products table
//		 Constraints_Singleton.getInstance().setOriginalCSP(FileToChocoModel.createOriginalCSP(productTableFile));
//		 numberOfVariables = Constraints_Singleton.getInstance().getIntVarList_extension__UserRequirements().size()-1;
//		 
//		 // print clustered user models
//		 int numberOfProductConstraints = Constraints_Singleton.getInstance().getOriginalCSP().numberOfProducts;
//		 int constrIndex = 0;
//		 
//		 System.out.println("####################################");
//		 System.out.println("PRODUCT TABLE");
//		 System.out.println("Number of Products: "+numberOfProductConstraints);
//		 System.out.println("Number of Variables: "+numberOfVariables);
//		 
//		 for(int i=0;i<numberOfProductConstraints;i++){
//			 System.out.print("PRODUCT ID-: "+i+" => ");
//			 for(int m=0;m<numberOfVariables;m++){
//				 int constID = Integer.valueOf(Constraints_Singleton.getInstance().getOriginalCSP().constraints_products.get(constrIndex).getName());
//				 Constraint_Extension c = Constraints_Singleton.getInstance().getConstraintList_extension__UserRequirements().get(constID);
//				 System.out.print(" ("+c.getVar_1_ID()+" = "+c.getValue_1()+" )");
//				 constrIndex++;
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		
//		 // STEP-1 is DONE
//		 
//		 
//		 // STEP-2 : get user_constraints as models based on the original problem
//		 Constraints_Singleton.getInstance().setCSPs_tobe_Clustered(FileToChocoModel.createUserModels(Constraints_Singleton.getInstance().getOriginalCSP(),userConstraintsFile));
//		 numberOfUsers = Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().size();
//		 // STEP-2 is DONE
//		 
//		 
//		 // STEP-3 : apply clustering
//		 KMeansClustering.applyKMeans(userConstraintsFile,numberOfVariables+1,numberOfClusters,outputFolder);
//		 clusters = KMeansClustering.getClusters(numberOfClusters,outputFolder);
//		 
//		 
//		 // print clustered user models
//		 System.out.println("####################################");
//		 System.out.println("USER MODELS");
//		 System.out.println("Number of Users: "+numberOfUsers);
//		 System.out.println("Number of Clusters: "+numberOfClusters);
//		 
//		 for(int i=0;i<clusters.length;i++){
//			 
//			 System.out.println("Cluster"+i+" size= "+clusters[i].length+" :");
//			 // get Model
//			 for(int v=0;v<clusters[i].length;v++){
//				 int modelIndex = clusters[i][v];
//				 System.out.print("ID of User Model: "+modelIndex+" => ");
//				 for(int m=0;m<numberOfVariables;m++){
//					 System.out.print(" "+Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(modelIndex).constraints_user.get(m));
//				 }
//				 System.out.println();
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		 // STEP-3 is DONE
//		 
//		 
//		 // STEP-4 : learn heuristics
//		 variableOrders = LearningHeuristics.learnHeuristicsForClusters(numberOfVariables,clusters,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered());
//		 for(int i=0;i<variableOrders.size();i++){
//			 System.out.println("VARIABLE ORDER FOR CLUSTER-"+i+":");
//			 for(int v=0;v<numberOfVariables;v++){
//				 System.out.print(" "+variableOrders.get(i)[v]);
//			 }
//			 System.out.println();
//		 }
//		// STEP-4 is DONE
		 
		 step1and2();
		 
		 step3and4();
		
		// STEP-5 : apply FastDiag
		List<String> lines =  new ArrayList<String>();
		for(int i=0;i<numberOfUsers;i++){
			CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
			//CSP model = ChocoDuplications.duplicateModel(Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i), String.valueOf(Math.random()));
			float [] resp = LearningHeuristics.diagnoseCSP_FastDiag(model, variableOrders.get(getClusterID(i)));
			lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
			avgPresicion += resp[0];
			avgTime += resp[1];
		 }
		 WriteToFile.writeFile(outputFile,lines, true, true,false);
		 // STEP-5 is DONE
		 
		 avgPresicion = avgPresicion / (float)numberOfUsers;
		 avgTime = avgTime / (float)numberOfUsers;
	 
		 System.out.println("CSD test is completed");
		 
	 }
	
	 // type = 2;
	 public static void diagnoseByFastDiag(){
		 
			
//		 // STEP-1 : generate the original problem with products table
//		 Constraints_Singleton.getInstance().setOriginalCSP(FileToChocoModel.createOriginalCSP(productTableFile));
//		 numberOfVariables = Constraints_Singleton.getInstance().getIntVarList_extension__UserRequirements().size()-1;
//		 
//		 // print clustered user models
//		 int numberOfProductConstraints = Constraints_Singleton.getInstance().getOriginalCSP().numberOfProducts;
//		 int constrIndex = 0;
//		 
//		 System.out.println("####################################");
//		 System.out.println("PRODUCT TABLE");
//		 System.out.println("Number of Products: "+numberOfProductConstraints);
//		 System.out.println("Number of Variables: "+numberOfVariables);
//		 
//		 for(int i=0;i<numberOfProductConstraints;i++){
//			 System.out.print("PRODUCT ID-: "+i+" => ");
//			 for(int m=0;m<numberOfVariables;m++){
//				 int constID = Integer.valueOf(Constraints_Singleton.getInstance().getOriginalCSP().constraints_products.get(constrIndex).getName());
//				 Constraint_Extension c = Constraints_Singleton.getInstance().getConstraintList_extension__UserRequirements().get(constID);
//				 System.out.print(" ("+c.getVar_1_ID()+" = "+c.getValue_1()+" )");
//				 constrIndex++;
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		
//		 // STEP-1 is DONE
//		 
//		 
//		 // STEP-2 : get user_constraints as models based on the original problem
//		 Constraints_Singleton.getInstance().setCSPs_tobe_Clustered(FileToChocoModel.createUserModels(Constraints_Singleton.getInstance().getOriginalCSP(),userConstraintsFile));
//		 numberOfUsers = Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().size();
//		 // STEP-2 is DONE
		 
		 step1and2();
		 
		 
		 // STEP-3 : apply FastDiag
		 List<String> lines =  new ArrayList<String>();
		 for(int i=0;i<numberOfUsers;i++){
			 	// CSP (int type, int[][]productTable, CSP originalCSP, int[] variables, int userID, int prodID)
			 	CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
			 	float [] resp = LearningHeuristics.diagnoseCSP_FastDiag(model, null);
				lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
				avgPresicion += resp[0];
				avgTime += resp[1];
		 }
		 WriteToFile.writeFile(outputFile,lines, true, true,false);
		 // STEP-3 is DONE
		 
		 avgPresicion = avgPresicion / (float)numberOfUsers;
		 avgTime = avgTime / (float)numberOfUsers;
		 
		 System.out.println("CSD test is completed");
		 
	 }
	 
	 // LearningHeuristics.type = 3;
	 public static void diagnoseByGeneticAlgorithm_BestPrecision(){
		 
			
//		 // STEP-1 : generate the original problem with products table
//		 Constraints_Singleton.getInstance().setOriginalCSP(FileToChocoModel.createOriginalCSP(productTableFile));
//		 numberOfVariables = Constraints_Singleton.getInstance().getIntVarList_extension__UserRequirements().size()-1;
//		 
//		 // print clustered user models
//		 int numberOfProductConstraints = Constraints_Singleton.getInstance().getOriginalCSP().numberOfProducts;
//		 int constrIndex = 0;
//		 
//		 System.out.println("####################################");
//		 System.out.println("PRODUCT TABLE");
//		 System.out.println("Number of Products: "+numberOfProductConstraints);
//		 System.out.println("Number of Variables: "+numberOfVariables);
//		 
//		 for(int i=0;i<numberOfProductConstraints;i++){
//			 System.out.print("PRODUCT ID-: "+i+" => ");
//			 for(int m=0;m<numberOfVariables;m++){
//				 int constID = Integer.valueOf(Constraints_Singleton.getInstance().getOriginalCSP().constraints_products.get(constrIndex).getName());
//				 Constraint_Extension c = Constraints_Singleton.getInstance().getConstraintList_extension__UserRequirements().get(constID);
//				 System.out.print(" ("+c.getVar_1_ID()+" = "+c.getValue_1()+" )");
//				 constrIndex++;
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		
//		 // STEP-1 is DONE
//		 
//		 
//		 // STEP-2 : get user_constraints as models based on the original problem
//		 Constraints_Singleton.getInstance().setCSPs_tobe_Clustered(FileToChocoModel.createUserModels(Constraints_Singleton.getInstance().getOriginalCSP(),userConstraintsFile));
//		 numberOfUsers = Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().size();
//		 // STEP-2 is DONE
//		 
//		 
//		 // STEP-3 : apply clustering
//		 KMeansClustering.applyKMeans(userConstraintsFile,numberOfVariables+1,numberOfClusters,outputFolder);
//		 clusters = KMeansClustering.getClusters(numberOfClusters,outputFolder);
//		 
//		 
//		 // print clustered user models
//		 System.out.println("####################################");
//		 System.out.println("USER MODELS");
//		 System.out.println("Number of Users: "+numberOfUsers);
//		 System.out.println("Number of Clusters: "+numberOfClusters);
//		 
//		 for(int i=0;i<clusters.length;i++){
//			 
//			 System.out.println("Cluster"+i+" size= "+clusters[i].length+" :");
//			 // get Model
//			 for(int v=0;v<clusters[i].length;v++){
//				 int modelIndex = clusters[i][v];
//				 System.out.print("ID of User Model: "+modelIndex+" => ");
//				 for(int m=0;m<numberOfVariables;m++){
//					 System.out.print(" "+Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(modelIndex).constraints_user.get(m));
//				 }
//				 System.out.println();
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		 // STEP-3 is DONE
//		 
//		 
//		 // STEP-4 : learn heuristics
//		 variableOrders = LearningHeuristics.learnHeuristicsForClusters(numberOfVariables,clusters,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered());
//		 for(int i=0;i<variableOrders.size();i++){
//			 System.out.println("VARIABLE ORDER FOR CLUSTER-"+i+":");
//			 for(int v=0;v<numberOfVariables;v++){
//				 System.out.print(" "+variableOrders.get(i)[v]);
//			 }
//			 System.out.println();
//		 }
//		// STEP-4 is DONE
		 
		 step1and2();
		 
		 step3and4();
		 
		 
		// STEP-5 : apply CHOCO
		List<String> lines =  new ArrayList<String>();
		for(int i=0;i<numberOfUsers;i++){
			CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
			//CSP model = ChocoDuplications.duplicateModel(Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i), String.valueOf(Math.random()));
			float [] resp = LearningHeuristics.diagnoseCSP_GA4D(model, variableOrders.get(getClusterID(i)));
			lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
			avgPresicion += resp[0];
			avgTime += resp[1];
		 }
		 WriteToFile.writeFile(outputFile,lines, true, true,false);
		 // STEP-5 is DONE
		 
		 avgPresicion = avgPresicion / (float)numberOfUsers;
		 avgTime = avgTime / (float)numberOfUsers;
		System.out.println("CSD test is completed");
		 
	 }
	 
	 // LearningHeuristics.type = 4;
	 public static void diagnoseByGeneticAlgorithm_Fastest(){
		 
//			
//		 // STEP-1 : generate the original problem with products table
//		 Constraints_Singleton.getInstance().setOriginalCSP(FileToChocoModel.createOriginalCSP(productTableFile));
//		 numberOfVariables = Constraints_Singleton.getInstance().getIntVarList_extension__UserRequirements().size()-1;
//		 
//		 // print clustered user models
//		 int numberOfProductConstraints = Constraints_Singleton.getInstance().getOriginalCSP().numberOfProducts;
//		 int constrIndex = 0;
//		 
//		 System.out.println("####################################");
//		 System.out.println("PRODUCT TABLE");
//		 System.out.println("Number of Products: "+numberOfProductConstraints);
//		 System.out.println("Number of Variables: "+numberOfVariables);
//		 
//		 for(int i=0;i<numberOfProductConstraints;i++){
//			 System.out.print("PRODUCT ID-: "+i+" => ");
//			 for(int m=0;m<numberOfVariables;m++){
//				 int constID = Integer.valueOf(Constraints_Singleton.getInstance().getOriginalCSP().constraints_products.get(constrIndex).getName());
//				 Constraint_Extension c = Constraints_Singleton.getInstance().getConstraintList_extension__UserRequirements().get(constID);
//				 System.out.print(" ("+c.getVar_1_ID()+" = "+c.getValue_1()+" )");
//				 constrIndex++;
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		
//		 // STEP-1 is DONE
//		 
//		 
//		 // STEP-2 : get user_constraints as models based on the original problem
//		 Constraints_Singleton.getInstance().setCSPs_tobe_Clustered(FileToChocoModel.createUserModels(Constraints_Singleton.getInstance().getOriginalCSP(),userConstraintsFile));
//		 numberOfUsers = Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().size();
//		 // STEP-2 is DONE
//		 
//		 
//		 // STEP-3 : apply clustering
//		 KMeansClustering.applyKMeans(userConstraintsFile,numberOfVariables+1,numberOfClusters,outputFolder);
//		 clusters = KMeansClustering.getClusters(numberOfClusters,outputFolder);
//		 
//		 
//		 // print clustered user models
//		 System.out.println("####################################");
//		 System.out.println("USER MODELS");
//		 System.out.println("Number of Users: "+numberOfUsers);
//		 System.out.println("Number of Clusters: "+numberOfClusters);
//		 
//		 for(int i=0;i<clusters.length;i++){
//			 
//			 System.out.println("Cluster"+i+" size= "+clusters[i].length+" :");
//			 // get Model
//			 for(int v=0;v<clusters[i].length;v++){
//				 int modelIndex = clusters[i][v];
//				 System.out.print("ID of User Model: "+modelIndex+" => ");
//				 for(int m=0;m<numberOfVariables;m++){
//					 System.out.print(" "+Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(modelIndex).constraints_user.get(m));
//				 }
//				 System.out.println();
//			 }
//			 System.out.println();
//		 }
//		 System.out.println("####################################");
//		 // STEP-3 is DONE
//		 
//		 
//		 // STEP-4 : learn heuristics
//		 variableOrders = LearningHeuristics.learnHeuristicsForClusters(numberOfVariables,clusters,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered());
//		 for(int i=0;i<variableOrders.size();i++){
//			 System.out.println("VARIABLE ORDER FOR CLUSTER-"+i+":");
//			 for(int v=0;v<numberOfVariables;v++){
//				 System.out.print(" "+variableOrders.get(i)[v]);
//			 }
//			 System.out.println();
//		 }
//		// STEP-4 is DONE
		 
		 step1and2();
		 
		 step3and4();
		 
		// STEP-5 : apply CHOCO
		List<String> lines =  new ArrayList<String>();
		for(int i=0;i<numberOfUsers;i++){
			CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
			//CSP model = ChocoDuplications.duplicateModel(Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i), String.valueOf(Math.random()));
			float [] resp = LearningHeuristics.diagnoseCSP_GA4D(model, variableOrders.get(getClusterID(i)));
			lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
			avgPresicion += resp[0];
			avgTime += resp[1];
		 }
		 WriteToFile.writeFile(outputFile,lines, true, true,false);
		 // STEP-5 is DONE
		 
		 avgPresicion = avgPresicion / (float)numberOfUsers;
		 avgTime = avgTime / (float)numberOfUsers;
		 System.out.println("CSD test is completed");
		 
	 }
	 
	 
	 // added for IJCAI
	 // LearningHeuristics.type = 0;
	 public static void diagnoseByFlexDiag(int m){
		     System.out.println("in the diagnoseByFlexDiag");
			 step1and2();
			 
			 //step3and4();
			
			 // STEP-3 : apply FastDiag
			 List<String> lines =  new ArrayList<String>();
			 for(int i=0;i<numberOfUsers;i++){
				 	// CSP (int type, int[][]productTable, CSP originalCSP, int[] variables, int userID, int prodID)
				 	CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
				 	float [] resp = LearningHeuristics.diagnoseCSP_FlexDiag(model, null,m);
					lines.add(i+"\t\t Precision:"+resp[0]+"\t\t Time:"+(0-resp[1])+"\t\t Cardinality:"+resp[2]+"\t\t Checks:"+resp[4]);
					avgPresicion += resp[0];
					avgTime += resp[1];
					avgCardinality += resp[2];
					avgConsChecks += resp[4];
			 }
			 System.out.println(lines);
			 WriteToFile.writeFile(outputFile,lines, true, true,false);
			 // STEP-3 is DONE
			 
			 avgPresicion = avgPresicion / (float)numberOfUsers;
			 avgTime = avgTime / (float)numberOfUsers;
			 avgCardinality = avgCardinality / (float)numberOfUsers;
			 avgConsChecks = avgConsChecks / (float)numberOfUsers;
			 
			 System.out.println("CSD test is completed");
			 
	 }
	 
	 // LearningHeuristics.type = 0;
	 public static void diagnoseByFlexDiag_withHeuristics(int m){
			 
			 step1and2();
			 
			 step3and4();
			
			 // STEP-3 : apply FastDiag
			 List<String> lines =  new ArrayList<String>();
			 for(int i=0;i<numberOfUsers;i++){
				 	// CSP (int type, int[][]productTable, CSP originalCSP, int[] variables, int userID, int prodID)
				 	CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
				 	float [] resp = LearningHeuristics.diagnoseCSP_FlexDiag(model, variableOrders.get(getClusterID(i)),m);
					lines.add(i+"\t\t Precision:"+resp[0]+"\t\t Time:"+(0-resp[1])+"\t\t Cardinality:"+resp[2]+"\t\t Checks:"+resp[4]);
					avgPresicion += resp[0];
					avgTime += resp[1];
					avgCardinality += resp[2];
					avgConsChecks += resp[4];
			 }
			 System.out.println(lines+"\n");
			 WriteToFile.writeFile(outputFile,lines, true, true,false);
			 // STEP-3 is DONE
			 
			 avgPresicion = avgPresicion / (float)numberOfUsers;
			 avgTime = avgTime / (float)numberOfUsers;
			 avgCardinality = avgCardinality / (float)numberOfUsers;
			 avgConsChecks = avgConsChecks / (float)numberOfUsers;
			 
			 System.out.println("CSD test is completed");
			 
		 }
	 
	 
	 public static void diagnoseBy_DiagRect_Strategy(int strategy){
		 
		 step1and2();
		 
		 step3and4();
		 
		 List<String> lines =  new ArrayList<String>();
		 for(int i=0;i<numberOfUsers;i++){
			CSP model = new CSP(2, null,Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i),null,0,0,null);
			//CSP model = ChocoDuplications.duplicateModel(Constraints_Singleton.getInstance().getCSPs_tobe_Clustered().get(i), String.valueOf(Math.random()));
			float [] resp = null;
			
			if(strategy==1)
				resp = LearningHeuristics.diagnoseDiagRect_Strategy1(model, variableOrders.get(getClusterID(i)));
			if(strategy==2)
				resp = LearningHeuristics.diagnoseDiagRect_Strategy2(model, variableOrders.get(getClusterID(i)));
			if(strategy==3)
				resp = LearningHeuristics.diagnoseDiagRect_Strategy3(model, variableOrders.get(getClusterID(i)));
			
			lines.add(i+"\t\t"+(0-resp[1])+"\t\t"+resp[0]);
			avgPresicion += resp[0];
			avgTime += resp[1];
		  }
		  WriteToFile.writeFile(outputFile,lines, true, true,false);
		  // STEP-5 is DONE
		 
		  avgPresicion = avgPresicion / (float)numberOfUsers;
		  avgTime = avgTime / (float)numberOfUsers;
	 
		  System.out.println("CSD test is completed");
	 }
	 
	 
	 public static int getClusterID (int UserID){
		 int clusterIDofUser = -1;
		 for(int c=0;c<clusters.length;c++){
			 for(int m=0; m<clusters[c].length;m++){
				 if(clusters[c][m]==UserID){
					 clusterIDofUser = c;
					 break;
				 } 
			 }
			 if(clusterIDofUser!=-1)
				 break;
		 }
		 return clusterIDofUser;
	 }
	 
}
