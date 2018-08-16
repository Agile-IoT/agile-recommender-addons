import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.chocosolver.parser.flatzinc.BaseFlatzincListener;
import org.chocosolver.parser.flatzinc.Flatzinc;
import org.chocosolver.parser.flatzinc.FznSettings;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.constraints.LogicalConstraintFactory;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.ESat;

import algorithms.FlexDiag;
import algorithms.MatrixFactorization;
import algorithms.MinMaxNormalization;
import utils.WriteToFile;

public class LCR {
	
	 public static void main(String[] args) throws Exception {
			 
		 // 1- DEFINE INPUT FOLDER 
		 int numberOfProblems= 1; //20
		 int numberOfDataFiles= 1; // 2
		 int numberOfReqs=10;
		 int m = 1;
		 int numberOfPastTransactions = 4;
		 int pastReqs[][] = new int[numberOfPastTransactions][];
		 Constraint diagnoses[][] = new Constraint[numberOfPastTransactions-1][];
		 Flatzinc [] fzn= new Flatzinc [numberOfPastTransactions];
		 //Flatzinc [] testFzns= new Flatzinc [2];
		 
		 String resultsFile = "C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/Results";
		 
		 
		 // 2- DEFINE PROBLEMS
		 for (int i=0;i<numberOfProblems;i++){
			 System.out.println("Problem #"+i);
			 for (int j=0;j<numberOfDataFiles;j++){
				 System.out.println("Problem #"+i+", DataFile#"+j);
				 //File initialFile = new File("C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/ChocoZN/test"+i+"_"+j+".czn");
				 String initialFile = new String("C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/ChocoZN/test"+i+"_"+j+".czn");
				 // InputStream targetStream = new FileInputStream(initialFile);
				 
				 args = new String [1];
				 args[0] =initialFile;
				 
				 // 3- GENERATE PAST TRANSACTIONS 
				 
				 String outputFile = "C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/PastReqs/past"+i+"_"+j+".czn"; 
				 int numberOfVariables=0;
				 for (int p=0;p<numberOfPastTransactions;p++){
					 
					 // CREATE SOLVER AND LOAD INPUT FILE
					 //targetStream = new FileInputStream(initialFile);
					 fzn[p] = new Flatzinc();
					 fzn[p].addListener(new BaseFlatzincListener(fzn[p]));

					 fzn[p].parseParameters(args);
					 fzn[p].defineSettings(new FznSettings());
					 fzn[p].createSolver();
					 fzn[p].parseInputFile();
					 //fzn[p].configureSearch();
					 Solver solver = fzn[p].getSolver();
					
					 // GET VARIABLES FROM INPUT FILE
					 //Variable [] vars = solver.getVars();
					 // vars[0].
					 IntVar [] intVars = solver.retrieveIntVars();
					 numberOfVariables = intVars.length;
					 //numberOfReqs = numberOfVariables/2;
					 double [][] minmax = new double [numberOfVariables][2];
					 for(int n=0;n<numberOfVariables;n++){
						 minmax[n][0]= intVars[n].getLB();
						 minmax[n][1]= intVars[n].getUB();
					 }
					
					 
					 pastReqs[p] = new int [numberOfVariables]; 
					 Arrays.fill(pastReqs[p], -999);
					 
					 //System.out.println("generated req indexes:");
					 int count =0;
					 // GENERATE 5 RANDOM REQS TO CREATE INCONSISTENCY
					 Random rnd = new Random();
					 
					 while(true){
						 Constraint []constraints = new Constraint[numberOfReqs];
						 // add 5 constraints
						 for (int k=0;k<numberOfReqs;k++){
							 //System.out.println("k:"+k);
							 // pick a variable
							 int index= 0;
							 do{
								 index = rnd.nextInt(numberOfVariables);
								 //String realIndex = intVars[index].getName();
								 //String [] parsed = realIndex.split("_");
								 //index = Integer.valueOf(parsed[1]); // assign real var ID
							 }while(pastReqs[p][index]!=-999);
							
							 int lb = intVars[index].getLB();
							 int ub = intVars[index].getUB();
							 
							 if(lb==ub){
								 k--;
							 }
								 
							 // pick a value
							 else{
								 int value = (int)(Math.random() * (ub-lb));
								 // CHANGEME
								 //int val = lb+value;
								 int val = value+lb;
								 if((val)==-999)
									 System.out.print("BIG PROBLEM");
								
								 pastReqs[p][index]=val;
								 if(k<numberOfReqs/3) // k<3
									 constraints[k] = IntConstraintFactory.arithm(intVars[index],"=",val);
								 else if(k<2*numberOfReqs/3) // k<6
									 constraints[k] = IntConstraintFactory.arithm(intVars[index],"<",ub);
								 else // k>6
									 constraints[k] = IntConstraintFactory.arithm(intVars[index],">",lb);
								 
								 //System.out.println(index);
								 count++;
								 solver.post(constraints[k]); 
							 }
					     }
						 //fzn[p].solve(); 
						 
						 //boolean test3 = solver.findSolution();
						 //Solver solver3 = FlexDiag.cloneSolver(solver);
						 //boolean test4 = solver3.findSolution();
						 
						 if(solver.findSolution()){
							 for (int k=0;k<numberOfReqs;k++)
								 solver.unpost(constraints[k]);
							 solver= FlexDiag.cloneSolver(solver);
							 Arrays.fill(pastReqs[p], -999);
						 }
						 else{ // INCONSISTENCY FOUND
							 double [] normalized = MinMaxNormalization.normalizeTo0_1(pastReqs[p], minmax);
							 WriteToFile.writePastReqToFile(outputFile, normalized, numberOfVariables, p);
							 break;
						 }

					 }
					 if (count<numberOfReqs)
						 System.out.print("BIG PROBLEM");
					 
					 System.out.println("Generated pastReq-"+p);
					 
					 // DIAGNOSE
					 if(p!=numberOfPastTransactions-1){
						  diagnoses[p] = FlexDiag.diagnose(fzn[p].getSolver(),numberOfReqs,1);
						  System.out.println("diagnosis lenght: "+diagnoses[p].length);
						  WriteToFile.writeDiagnosisToFile(outputFile, diagnoses[p],intVars, numberOfVariables, p);
					 }
					 
					 System.out.println("Diagnosed pastReq-"+p);
				 }
				 
				
				
				
				// 4- MATRIX FACTORIZATION
				DataModel dataModel = new FileDataModel(new File(outputFile)); 
				int numFeatures =3; // mxk, kxn -> k value
				int numIterations =2;
				
				long startTime = System.nanoTime();
				MatrixFactorization.SVD(dataModel,numFeatures,numIterations,numberOfPastTransactions-1,numberOfVariables*numberOfPastTransactions*2);
				long endTime = System.nanoTime();
				double [][] p=MatrixFactorization.UF;
				double [][] q=MatrixFactorization.IF;
				
				double [][] pqt = MatrixFactorization.multiplyByMatrix(p, q); // mxn 4x50
				 
				System.out.println("end of MF");
				
				
				// 5- RETRIEVE  LCR 
				int startIndex = pqt[numberOfPastTransactions-1].length - numberOfVariables;
				double [] estimatedDiagnosis = Arrays.copyOfRange(pqt[numberOfPastTransactions-1], startIndex, pqt[numberOfPastTransactions-1].length);
				HashMap<Double,Integer> estimatedDiagnosis_hashmap = new HashMap<Double,Integer>(numberOfVariables);  
				
				for(int v=0;v<numberOfVariables;v++){
					double value =estimatedDiagnosis[v];
					estimatedDiagnosis_hashmap.put(value,v);
				}
				List<Double> estimatedDiagnosis_keys = new ArrayList<>(estimatedDiagnosis_hashmap.keySet());
			    Collections.sort(estimatedDiagnosis_keys);
			    // CHANGEME
			    //Collections.reverse(estimatedDiagnosis_keys);
			    System.out.println("end of 5- RETRIEVE  LCR ");
			    
				
				// 6- REORDER CONSTRAINTS
			    double [][] pastReqs_sorted = new double[numberOfVariables][2];
			    //double [] pastReqs_sorted = new double[numberOfVariables];
			    //int index=0;
			    int count =0;
			    //System.out.println("sorted indexes:");
				for(int r=0;r<numberOfVariables;r++){
					try{
					int var_index = estimatedDiagnosis_hashmap.get(estimatedDiagnosis_keys.get(r));
					// remove the checked one
					//estimatedDiagnosis_hashmap.remove(estimatedDiagnosis_keys.get(r));
					
					if(pastReqs[numberOfPastTransactions-1][var_index]!=-999){
						//System.out.println(var_index);
						pastReqs_sorted[r][0] = var_index;
						pastReqs_sorted[r][1] = pastReqs[numberOfPastTransactions-1][var_index];
						count++;
					}
					else{
						pastReqs_sorted[r][0] = -1;
						pastReqs_sorted[r][1] = -999;
					}
					}catch(Exception e)
					{
						System.out.println(e);
					}
						
				}
				if(count<numberOfReqs)
					System.out.println("ERROR");
				
				System.out.println("end of ordering");
				
				// 7- DIAGNOSE with LCR
				long startTime_lcr = System.nanoTime();
				Solver solver_lcr= fzn[numberOfPastTransactions-1].getSolver();
				Solver solver_fd = FlexDiag.cloneSolver(solver_lcr);
				Solver solver_fd2 = FlexDiag.cloneSolver(solver_lcr);
				
				Constraint [] diagnosis_lcr = FlexDiag.diagnose(solver_lcr,pastReqs_sorted,numberOfReqs,m);
				long endTime_lcr = System.nanoTime();
				long runtime_lcr = (endTime - startTime) + (endTime_lcr-startTime_lcr); 
				//long runtime_lcr = (endTime_lcr-startTime_lcr); 
				double runtime_lcr_double= (double)(runtime_lcr) / 1000000000;
				System.out.println("end of diagnosis-1");
				
				
				
				// 8- DIAGNOSE without LCR
//				startTime = System.nanoTime();
//				
//				targetStream = new FileInputStream(initialFile);
//				Flatzinc fzn_fd = new Flatzinc();
//				fzn_fd.createSolver();	
//				Solver solver_fd = fzn_fd.getSolver();
//				fzn_fd.parse(solver_fd, targetStream);
//				
//				 IntVar [] intVars_fd = solver_fd.retrieveIntVars();
//				 numberOfVariables = intVars_fd.length;
//				 
//				 for(int g=0;g<numberOfVariables;g++)
//				 {
//					 if(pastReqs[numberOfPastTransactions-1][g]!=-999){
//						 Constraint c= IntConstraintFactory.arithm(intVars_fd[g],"=",pastReqs[numberOfPastTransactions-1][g]);
//						 solver_fd.post(c); 
//					 } 
//				 }
				 
				Constraint [] diagnosis_fd = FlexDiag.diagnose(solver_fd,numberOfReqs,m);
				endTime = System.nanoTime();
				long runtime_fd = endTime - startTime; 
				double runtime_fd_double= (double)(runtime_fd) / 1000000000;
				System.out.println("end of diagnosis-2");
				
				
				// 9- DIAGNOSE with FASTDIAG
//				targetStream = new FileInputStream(initialFile);
//				Flatzinc fzn_fd2 = new Flatzinc();
//				fzn_fd2.createSolver();	
//				Solver solver_fd2 = fzn_fd2.getSolver();
//				fzn_fd2.parse(solver_fd2, targetStream);
//				
//				 IntVar [] intVars_fd2 = solver_fd2.retrieveIntVars();
//				 numberOfVariables = intVars_fd2.length;
//				 
//				 for(int g=0;g<numberOfVariables;g++)
//				 {
//					 if(pastReqs[numberOfPastTransactions-1][g]!=-999){
//						 Constraint c= IntConstraintFactory.arithm(intVars_fd2[g],"=",pastReqs[numberOfPastTransactions-1][g]);
//						 solver_fd2.post(c); 
//					 } 
//				 }
				 
				Constraint [] diagnosis_fd2 = FlexDiag.diagnose(solver_fd2,numberOfReqs,1);
				
				double minimality_lcr = (double)((double)(diagnosis_fd2.length))/ ((double)(diagnosis_lcr.length));
			    if(minimality_lcr<1)
			    	System.out.println("PROBLEM");
				double minimality_fd = (double)((double)(diagnosis_fd2.length))/ ((double)(diagnosis_fd.length));
			    double combined_lcr= minimality_lcr / runtime_lcr_double;
			    double combined_fd= minimality_fd / runtime_fd_double;
			    double improvementRatio = (combined_lcr-combined_fd)/ combined_fd;
			    improvementRatio = improvementRatio*100;
			    System.out.println("end of diagnosis-3");		
						
				// 10- PRINT RESULTS
			    NumberFormat formatter = new DecimalFormat("#0.000");     
			    
				String result = i+"#"+j+"#"+m+"#"+formatter.format(runtime_lcr_double)+"#"+formatter.format(minimality_lcr)+"#"+formatter.format(combined_lcr)+"###"+formatter.format(runtime_fd_double)+"#"+formatter.format(minimality_fd)+"#"+formatter.format(combined_fd)+"#"+formatter.format(improvementRatio)+"\n";
				System.out.println(result);
				// WriteToFile.writeALineToAFile(result, resultsFile);
				try {
				    Files.write(Paths.get(resultsFile), result.getBytes(), StandardOpenOption.APPEND);
				}catch (Exception e) {
				   
				}
			 }
		 }
		 
		 
		
	 
	 }
	 
	 
	 

}
