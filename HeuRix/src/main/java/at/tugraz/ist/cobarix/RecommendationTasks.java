package at.tugraz.ist.cobarix;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.chocosolver.solver.variables.IntVar;

import at.tugraz.ist.fileOperations.ReadFile;
import at.tugraz.ist.fileOperations.WriteToFile;
import at.tugraz.ist.knowledgebases.Bike2KB;
import at.tugraz.ist.knowledgebases.CameraKB;

public class RecommendationTasks {

	int numberofReqs;
    int [][] reqs;
  
    int numberOfvariables;
    double requirementRate = 0.1;
    Knowledgebase [] recomTasks; 
    Knowledgebase [][] recomTasks_copies; 
    
    public RecommendationTasks(int numberOfvariables){
    	this.numberOfvariables =  numberOfvariables;
    }
    
    public int[][] generateDataset_fromFile(int numberOfVars, int [][]histTransactions, int numberofReqs,int numberOfComparedHeuristics){
    	this.numberofReqs = numberofReqs;
		recomTasks = new Knowledgebase[numberofReqs];
		
		
		recomTasks_copies = new Knowledgebase[numberOfComparedHeuristics][numberofReqs];
		reqs = new int[numberofReqs][numberOfVars];
		
		for (int i=0;i<numberofReqs;i++){
			recomTasks[i] = new Knowledgebase(new CameraKB());
			
			for(int j=0;j<numberOfComparedHeuristics;j++){
				recomTasks_copies[j][i] = new Knowledgebase(new CameraKB());
			}
			
			for(int j=0;j<3;j++){
				reqs[i][j] = histTransactions[i][j];
			}
		} 
		
		return reqs;
    }
    
    
	public int[][] generateDataset (int numberOfVars, int numberofReqs, int solnSize, String inputFile, String outputFolder, boolean istype2,int numberOfComparedHeuristics)
	{
		this.numberofReqs = numberofReqs;
		recomTasks = new Knowledgebase[numberofReqs];
		
		recomTasks_copies = new Knowledgebase[numberOfComparedHeuristics][numberofReqs];
		reqs = new int[numberofReqs][numberOfVars];
		int problemIndex = solnSize;
		
		
		for (int i=0;i<numberofReqs;i++){
			recomTasks[i] = new Knowledgebase(new Bike2KB());
			for(int j=0;j<numberOfComparedHeuristics;j++){
				recomTasks_copies[j][i] = new Knowledgebase(new Bike2KB());
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
			reqs[i] = generateREQSforAProblem(i,istype2,numberOfComparedHeuristics);
			
			
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

	public int [] generateREQSforAProblem (int index, boolean isType2, int numberOfComparedHeuristics){
		
		int [] reqs = new int[numberOfvariables];
		List<String> lines = new ArrayList<String>();
		int itemIndex = 0;
		// Generate random user requirements
		for (int t=0;t<numberOfvariables;t++){
			
			int size = recomTasks[index].domainSizes[t];
			int random = -1;
			
			// DECIDE TO INITIATE THIS VAR OR NOT
			if(Math.random()<requirementRate){
				random = (int) (Math.random()*size); // for ex: random=5
				recomTasks[index].modelKB.arithm(recomTasks[index].vars[t],"=",random).post();
				
			
				for(int j=0;j<numberOfComparedHeuristics;j++){
					recomTasks_copies[j][index].modelKB.arithm(recomTasks_copies[j][index].vars[t],"=",random).post();
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
	
	public float getAccuracy(int [][] recommendations){
		float accuracy = 0;
		
		for(int j=0;j<numberofReqs;j++){
			int result =1;
			
			for(int i=0;i<numberOfvariables;i++){
				if(recomTasks[0].purchases[j][i] != recommendations[j][i]){
					result=0;
					break;
				}
			}
			accuracy += result;
		}
		
		return accuracy/numberofReqs;
	}

	public float getAccuracy(int [] recommendedIDs){
		float accuracy = 0;
		
		for(int j=0;j<numberofReqs;j++){
			int result =1;
			
			if(recomTasks[0].purchaseIDs[j] != recommendedIDs[j]){
					result=0;
					break;
				}
			
			accuracy += result;
		}
		
		return accuracy/numberofReqs;
	}

}
