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
    
	public void generateDataset (int numberofReqs, int solnSize, String inputFile, String outputFolder)
	{
		bikeConfigProblems = new BikeConfig[numberofReqs];
		bikeConfigProblems_copies = new BikeConfig[numberofReqs];
		reqs = new int[numberofReqs][34];
		int problemIndex = solnSize;
		
		// WRITE DATASET
		for (int i=0;i<numberofReqs;i++){
			bikeConfigProblems[i] = new BikeConfig();
			bikeConfigProblems_copies[i] = new BikeConfig();
			
			File problemFile = new File(outputFolder+"/Problem_"+i);
			String line ;
			if (!problemFile.exists()) {
				try {
					problemFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// COPY SOLUTIONS TO THE FILE
			try {
				FileUtils.copyFile(new File(inputFile), problemFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			// OPEN FILE TO WRITE
			FileWriter fw = null;
			try {
				fw = new FileWriter(problemFile.getAbsolutePath(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedWriter bw = new BufferedWriter(fw);
		
			
			// ADD USER REQS TO THE FILE
			List<String> lines = generateREQSforAProblem(i);
			
			for(int j=0;j<lines.size();j++)			
				try { 
					bw.write(problemIndex+","+lines.get(j)); // ADD USER ID: i
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			// CLOSE FILE 
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public List<String> generateREQSforAProblem (int index){
		
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
			
				for (int d=0;d<size;d++){ // for ex: size is 14
					String value = "0.0";
					if(random==d) // for ex : random=5 and d=5
						value = "1.0";
					String s= itemIndex+","+value+"\n";
					lines.add(s);
					itemIndex++;
				}
			}
			else
				itemIndex += size;
		}
		return lines;		
	}
	

	
}
