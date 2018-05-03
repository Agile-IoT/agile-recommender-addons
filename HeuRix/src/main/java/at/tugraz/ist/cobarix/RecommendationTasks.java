package at.tugraz.ist.cobarix;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.chocosolver.solver.variables.IntVar;

import at.tugraz.ist.fileOperations.ReadFile;
import at.tugraz.ist.fileOperations.WriteToFile;
import at.tugraz.ist.knowledgebases.Bike2KB;
import at.tugraz.ist.knowledgebases.CameraKB;
import at.tugraz.ist.knowledgebases.PCKB;

public class RecommendationTasks {

	int numberofReqs;
    int [][] reqs;
  
    int numberOfvariables;
    double requirementRate = 0.1;
    Knowledgebase [][] recomTasks; 
    Knowledgebase [][] recomTasks_copies; 
    
    int numberOfVariablesInAReq = 1;
    
    public RecommendationTasks(int numberOfvariables, int n){
    	this.numberOfvariables =  numberOfvariables;
    	this.numberOfVariablesInAReq = n;
    }
    
    // CameraKB
    public int[][] generateDataset_fromFile(int numberOfVars, int [][]histTransactions, int numberofReqs,int numberOfComparedHeuristics,int numberOfVarOrdHeuristics){
    	boolean isTestAccuracy = true;
    	
    	this.numberofReqs = numberofReqs;
		recomTasks = new Knowledgebase[numberOfVarOrdHeuristics][numberofReqs];
		
		
		recomTasks_copies = new Knowledgebase[numberOfComparedHeuristics][numberofReqs];
		reqs = new int[numberofReqs][numberOfVars];
		
		
		for (int i=0;i<numberofReqs;i++){
			
			for(int j=0;j<numberOfVars;j++){
				reqs[i][j] = -1;
			}
			
			Random rnd = new Random();
			Set randoms = new HashSet<>();
			for(int j=0;j<numberOfVariablesInAReq;j++){
				int random = rnd.nextInt(numberOfVars);
				
				if (randoms.isEmpty())
					randoms.add(random);
				
				else{
					if(randoms.contains(random)){
						j--;
						continue;
					}
					else
						randoms.add(random);
				}
				
				if(random == numberOfVars)
					random = random-1;
				reqs[i][random] = histTransactions[i][random];
					
				//int random = (int) (Math.random()*numberOfVars);
				
			}
			
			for(int j=0;j<numberOfVarOrdHeuristics;j++){
				recomTasks[j][i] = new Knowledgebase(new CameraKB(),isTestAccuracy);
			
				for(int t=0;t<numberOfvariables;t++){
					if(reqs[i][t]!=-1)
						recomTasks[j][i].kb.getModelKB().arithm((IntVar)recomTasks[j][i].kb.getModelKB().getVar(t),"=",reqs[i][t]).post();
					//System.out.println("Problem-"+i+", Var :"+t+"= "+reqs[i][t]);
				}
				
			}
			
			for(int j=0;j<numberOfComparedHeuristics;j++){
				recomTasks_copies[j][i] = new Knowledgebase(new CameraKB(),isTestAccuracy);
				for(int t=0;t<numberOfvariables;t++){
					if(reqs[i][t]!=-1)
						recomTasks_copies[j][i].kb.getModelKB().arithm((IntVar)recomTasks_copies[j][i].kb.getModelKB().getVar(t),"=",reqs[i][t]).post();
				}
			}
		} 
		
		return reqs;
    }
    
    
    // Bike2KB or PCKB
	public int[][] generateDataset (int numberOfVars, int numberofReqs, int solnSize, String inputFile, String outputFolder, boolean istype2,int numberOfComparedHeuristics,int numberOfVarOrdHeuristics, boolean isBike, boolean isTestAccuracy)
	{
		this.numberofReqs = numberofReqs;
		recomTasks = new Knowledgebase[numberOfVarOrdHeuristics][numberofReqs];
		
		recomTasks_copies = new Knowledgebase[numberOfComparedHeuristics][numberofReqs];
		reqs = new int[numberofReqs][numberOfVariablesInAReq];
		int problemIndex = solnSize;
		
		
		for (int i=0;i<numberofReqs;i++){
			
			for(int j=0;j<numberOfVarOrdHeuristics;j++){
				if(isBike)
					recomTasks[j][i] = new Knowledgebase(new Bike2KB(),isTestAccuracy);
				else
					recomTasks[j][i] = new Knowledgebase(new PCKB(),isTestAccuracy);
			}
			for(int j=0;j<numberOfComparedHeuristics;j++){
				if(isBike)
					recomTasks_copies[j][i] = new Knowledgebase(new Bike2KB(),isTestAccuracy);
				else
					recomTasks_copies[j][i] = new Knowledgebase(new PCKB(),isTestAccuracy);
			}
			
//			// FILE OPERATIONS
//			File problemFile = new File(outputFolder+"/Problem_"+i);
//			String line ;
//			if (problemFile.exists()) 
//				problemFile.delete();
//			
//				try {
//					problemFile.createNewFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			
//			// COPY SOLUTIONS TO THE FILE
//			try {
//				FileUtils.copyFile(new File(inputFile), problemFile);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			
//			// OPEN FILE TO WRITE
//			FileWriter fw = null;
//			try {
//				fw = new FileWriter(problemFile.getAbsolutePath(), true);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			BufferedWriter bw = new BufferedWriter(fw);
		
			
			// GENERATE A USERS REQ
			//List<String> lines = generateREQSforAProblem(i,istype2);
			reqs[i] = generateREQSforAProblem(i,istype2,numberOfComparedHeuristics,numberOfVarOrdHeuristics);
			
			
//			// FILE OPERATIONS
//			for(int j=0;j<lines.size();j++)			
//				try { 
//					if(j==0)
//						bw.write("");
//					bw.write(problemIndex+","+lines.get(j)); // ADD USER ID: i
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			
//			// CLOSE FILE 
//			try {
//				bw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		}
		return reqs;
		
	}

	public int [] generateREQSforAProblem (int index, boolean isType2, int numberOfComparedHeuristics,int numberOfVarOrdHeuristics){
		
		int [] reqs = new int[numberOfvariables];
		List<String> lines = new ArrayList<String>();
		int itemIndex = 0;
		int count =0;
		// Generate random user requirements
		for (int t=0;t<numberOfvariables;t++){
			
			int size = recomTasks[0][index].domainSizes[t];
			int random = -1;
			
			// DECIDE TO INITIATE THIS VAR OR NOT
			if(Math.random()<requirementRate && count<numberOfVariablesInAReq){
				count++;
				random = (int) (Math.random()*size); // for ex: random=5
				if(random == size)
					random = size-1;
				//recomTasks[index].modelKB.arithm(recomTasks[index].vars[t],"=",random).post();
				
				for(int j=0;j<numberOfVarOrdHeuristics;j++){
					recomTasks[j][index].kb.getModelKB().arithm((IntVar)recomTasks[j][index].kb.getModelKB().getVar(t),"=",random).post();
				}
				
				for(int j=0;j<numberOfComparedHeuristics;j++){
					recomTasks_copies[j][index].kb.getModelKB().arithm((IntVar)recomTasks_copies[j][index].kb.getModelKB().getVar(t),"=",random).post();
				}
				
				// ENCODED SOLUTIONS
				if(!isType2){
					for (int d=0;d<size;d++){ // for ex: size is 14
						String value = "0.0";
						if(random==d) // for ex : random=5 and d=5
							value = "1.0";
						String s= itemIndex+","+value+"\n";
						lines.add(s);
						itemIndex++;
					}
				}
				// REAL SOLUTIONS
				else{
					String s= itemIndex+","+random+"\n";
					lines.add(s);
					itemIndex++;
				}
			}
			else{
				// ENCODED SOLUTIONS
				if(!isType2)
					itemIndex += size;
				// REAL SOLUTIONS
				else
					itemIndex ++;
			}
			reqs[t] = random;
		}
		//return lines;		
		return reqs;		
	}
	
	public double getAccuracy(int [][] recommendations, int index, Knowledgebase kb){
		double accuracy = 0;
		
		//for (int h=0;h<numberOfVarOrdHeuristics;h++){
			for(int j=0;j<numberofReqs;j++){
				int result =1;
				
				for(int i=0;i<numberOfvariables;i++){
					if(kb.purchases[j][i] != recommendations[j][i]){
						result=0;
						break;
					}
				}
				accuracy += result;
			}
			accuracy =  (double)(accuracy/numberofReqs);
		//}
		
		return accuracy;
	}

	public double getAccuracy(int [] recommendedIDs,Knowledgebase kb){
		double accuracy = 0;
		
		for(int j=0;j<numberofReqs;j++){
			int result =1;
			
			if(kb.purchaseIDs[j] != recommendedIDs[j]){
					result=0;
					break;
				}
			
			accuracy += result;
		}
		
		return (double)(accuracy/numberofReqs);
	}

}
