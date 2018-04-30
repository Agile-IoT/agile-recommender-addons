package at.tugraz.ist.cobarix;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandom;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.search.strategy.selectors.variables.Largest;
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

import at.tugraz.ist.algorithms.SedasValueOrdering;
import at.tugraz.ist.fileOperations.ReadFile;
import at.tugraz.ist.knowledgebases.Bike2KB;
import at.tugraz.ist.knowledgebases.KB;

// https://www.itu.dk/research/cla/externals/clib/bike2.cp

public class Knowledgebase {
	
	
	KB kb;
	int numberOfVariables;
	Model modelKB ;
	IntVar[] vars ;
	
	int [] domainSizes;
	HashMap<Integer,Integer> hashmapIDs = new HashMap<Integer,Integer>();  
	int[] lowBoundaries ;  
	int [][] valueOrdering ;
	
	int [][] purchases;
	int [] purchaseIDs;
	
	Knowledgebase(KB kb){
		 this.kb = kb;
		 numberOfVariables= kb.getNumberOfVariables(); 
		 
		 modelKB = kb.getModelKB();
		 vars = kb.getVars();
		 domainSizes= new int[numberOfVariables];
		 lowBoundaries = new int[numberOfVariables]; 
		 valueOrdering = new int [numberOfVariables][];
		 
		
		  int index = 0;
	        for(int i=0;i<numberOfVariables;i++){
	        	domainSizes[i] =vars[i].getDomainSize();
	        	valueOrdering[i]= new int[domainSizes[i]] ;
	        	for(int j=0;j<domainSizes[i];j++){
	        		if(j==0)
	        			lowBoundaries[i]=index;
	        		hashmapIDs.put(index,i);  
	        		index++;
	        		valueOrdering[i][j]=j;
	        	}
	        }
	             
	     // ADDITIONAL CONSTRAINTS
			for(int i=0;i<numberOfVariables;i++){
				int value = vars[i].getDomainSize();
				int minvalue = vars[i].getLB();
				int maxvalue = vars[i].getUB();
				
				int avg = (maxvalue-minvalue)/2;
				if (i<numberOfVariables-1)
					modelKB.ifThen(
							modelKB.arithm(vars[i],">",avg),
							modelKB.arithm(vars[i+1],"!=",vars[i])
					);
				if (i<numberOfVariables-2)
					modelKB.ifThen(
							modelKB.arithm(vars[i],">",avg),
							modelKB.arithm(vars[i+2],"!=",vars[i])
					);
				if (i<numberOfVariables-3)
					modelKB.ifThen(
							modelKB.arithm(vars[i],">",avg),
							modelKB.arithm(vars[i+3],"!=",vars[i])
					);
				
				
				
				if (i>1)
					modelKB.ifThen(
							modelKB.arithm(vars[i],"<",avg),
							modelKB.arithm(vars[i-1],"!=",vars[i])
					);
				if (i>2)
					modelKB.ifThen(
							modelKB.arithm(vars[i],"<",avg),
							modelKB.arithm(vars[i-2],"!=",vars[i])
				);
				
				if (i>3)
					modelKB.ifThen(
							modelKB.arithm(vars[i],"<",avg),
							modelKB.arithm(vars[i-3],"!=",vars[i])
				);
				
				
			}
			

	}
	

	public int [][] readHistoricalTransactions(String inputFile, String outputFile){
		
		List<String> lines = ReadFile.readFile(inputFile);
		purchases = new int [lines.size()][numberOfVariables];
		purchaseIDs = new int [lines.size()];
		
		for(int i=0;i<lines.size();i++){
			String [] parsed = lines.get(i).split(",");
			for(int j=0;j<numberOfVariables;j++){
				purchases[i][j]= Integer.valueOf(parsed[j]);
			}
			purchaseIDs[i]= Integer.valueOf(parsed[parsed.length-2]);
			writeSolutionToFile(outputFile,purchases[i],i,false);
		}
		
		 
		return purchases;
		
	}
	
	public int [][] generateHistoricalTransactions(int number, String outputFile, boolean istype2){
		
		 Solver solver = modelKB.getSolver();
		 int counter = 0;
		 int [][] solutions = new int [number][numberOfVariables];
		 
		 VariableSelector varSelector =  new InputOrder<>(modelKB);
		 IntValueSelector valueSelector = new IntDomainRandom(1);
		 do{
			 solver.setSearch(intVarSearch(
		                
					 varSelector,
		               
					 valueSelector,
		               
					 vars
				));
			 
			 solver.solve();
			 solutions[counter] = new int [numberOfVariables];
			 for(int i=0;i<numberOfVariables;i++)
				 solutions[counter][i]=((IntVar)(modelKB.getVar(i))).getValue();
			 
			 writeSolutionToFile(outputFile,solutions[counter],counter,istype2);
			 counter++;
		 }while(counter < number);
		 return solutions;
	}

	
	private void writeSolutionToFile (String outputFile, int []values, int index, boolean istype2){
		
		int itemIndex = 0;
		List<String> lines = new ArrayList<String>();
		for (int t=0;t<numberOfVariables;t++){
			int size = domainSizes[t];
			if(!istype2){
				for (int d=0;d<size;d++){ // for ex: size is 14
					String value = "0.0";
					if(values[t]==d) // for ex : random=5 and d=5
						value = "1.0";
					String s= itemIndex+","+value+"\n";
					lines.add(s);
					itemIndex++;
				}
			}
			else{
				String s= itemIndex+","+values[t]+"\n";
				lines.add(s);
				itemIndex++;
			}
		}
		writeToFile(outputFile,lines,index);
	}
	
	public void writeToFile(String outputFile,List<String> lines, int index){
		// WRITE DATASET
		File file = new File(outputFile);
		String line ;
		if (!file.exists()) 
			try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		
					
		// OPEN FILE TO WRITE
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsolutePath(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		
					
		// ADD USER REQS TO THE FILE
		for(int j=0;j<lines.size();j++)			
			try { 
				bw.write(index+","+lines.get(j)); // ADD USER ID: i
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
	
	public void setCOBARIXHeuristics (VariableSelector variableSelector){
		
		SedasValueOrdering valueOrder = new SedasValueOrdering(valueOrdering);
		//VariableSelector varSelector =  new InputOrder<>(modelKB);
		IntValueSelector valueSelector = valueOrder;
		
	     
		modelKB.getSolver().setSearch(intVarSearch(
                
				variableSelector,
                // selects the smallest domain value (lower bound)
				 
				valueSelector,
               
                // variables to branch on
				vars
		));
		
	}
	
	public void seValOrdHeuristics (VariableSelector variableSelector, IntValueSelector valueOrder){
		
		//VariableSelector varSelector =  new InputOrder<>(modelKB);
		IntValueSelector valueSelector = valueOrder;
		
	     
		modelKB.getSolver().setSearch(intVarSearch(
                
				variableSelector,
                // selects the smallest domain value (lower bound)
				 
				valueSelector,
               
                // variables to branch on
				vars
		));
		
	}
	
}
