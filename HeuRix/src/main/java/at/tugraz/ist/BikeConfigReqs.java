package at.tugraz.ist;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.chocosolver.solver.variables.IntVar;

import at.tugraz.ist.fileOperations.WriteToFile;

public class BikeConfigReqs {

    int [][] reqs;
    int numberOfvariables= 34;
    double requirementRate = 0.1;
    BikeConfig [] bikeConfigProblems; 
    BikeConfig [] bikeConfigProblems_copies; 
    
    public BikeConfigReqs(){}
    
	public int[][] generateDataset (int numberofReqs, int solnSize, String inputFile, String outputFolder, boolean istype2)
	{
		bikeConfigProblems = new BikeConfig[numberofReqs];
		bikeConfigProblems_copies = new BikeConfig[numberofReqs];
		reqs = new int[numberofReqs][34];
		int problemIndex = solnSize;
		
		
		for (int i=0;i<numberofReqs;i++){
			bikeConfigProblems[i] = new BikeConfig();
			bikeConfigProblems_copies[i] = new BikeConfig();
			
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
			reqs[i] = generateREQSforAProblem(i,istype2);
			
			
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

	public int [] generateREQSforAProblem (int index, boolean isType2){
		
		int [] reqs = new int[numberOfvariables];
		List<String> lines = new ArrayList<String>();
		int itemIndex = 0;
		// Generate random user requirements
		for (int t=0;t<numberOfvariables;t++){
			
			int size = bikeConfigProblems[index].domainSizes[t];
			int random = -1;
			
			// DECIDE TO INITIATE THIS VAR OR NOT
			if(Math.random()<requirementRate){
				random = (int) (Math.random()*size); // for ex: random=5
				bikeConfigProblems[index].bikeModel.arithm(bikeConfigProblems[index].vars[t],"=",random).post();
				bikeConfigProblems_copies[index].bikeModel.arithm(bikeConfigProblems_copies[index].vars[t],"=",random).post();
			
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
			
		}
		//return lines;		
		return reqs;		
	}
	
	
}
