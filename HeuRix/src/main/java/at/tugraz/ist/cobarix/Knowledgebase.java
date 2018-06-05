package at.tugraz.ist.cobarix;


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
import org.chocosolver.solver.search.strategy.Search;
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
	// kb.getModelKB() ;
	IntVar[] varsNotInModel;
	
	int [] domainSizes;
	HashMap<Integer,Integer> hashmapIDs = new HashMap<Integer,Integer>();  
	int[] lowBoundaries ;  
	int [][] valueOrdering ;
	int [] variableOrdering ;
	
	int [][] purchases;
	int [] purchaseIDs;
	
	Knowledgebase(KB kb, boolean isTestAccuracy){
		 this.kb = kb;
		 numberOfVariables= kb.getNumberOfVariables(); 
		 
		 //kb.getModelKB() = kb.getkb.getModelKB()();
		 varsNotInModel = kb.getVars();
		 domainSizes= new int[numberOfVariables];
		 lowBoundaries = new int[numberOfVariables]; 
		 valueOrdering = new int [numberOfVariables][];
		 variableOrdering= new int [numberOfVariables];
		
		  int index = 0;
	        for(int i=0;i<numberOfVariables;i++){
	        	domainSizes[i] =kb.getVars()[i].getDomainSize();
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
	     if(!isTestAccuracy)
			for(int i=0;i<numberOfVariables;i++){
				int value = kb.getVars()[i].getDomainSize();
				int minvalue = kb.getVars()[i].getLB();
				int maxvalue = kb.getVars()[i].getUB();
				
				int avg = (maxvalue-minvalue)/2;
				if (i<numberOfVariables-1)
					kb.getModelKB().ifThen(
							kb.getModelKB().arithm(kb.getVars()[i],">",avg),
							kb.getModelKB().arithm(kb.getVars()[i+1],"!=",kb.getVars()[i])
					);
				if (i<numberOfVariables-2)
					kb.getModelKB().ifThen(
							kb.getModelKB().arithm(kb.getVars()[i],">",avg),
							kb.getModelKB().arithm(kb.getVars()[i+2],"!=",kb.getVars()[i])
					);
				if (i<numberOfVariables-3)
					kb.getModelKB().ifThen(
							kb.getModelKB().arithm(kb.getVars()[i],">",avg),
							kb.getModelKB().arithm(kb.getVars()[i+3],"!=",kb.getVars()[i])
					);
				
				
				
				if (i>1)
					kb.getModelKB().ifThen(
							kb.getModelKB().arithm(kb.getVars()[i],"<",avg),
							kb.getModelKB().arithm(kb.getVars()[i-1],"!=",kb.getVars()[i])
					);
				if (i>2)
					kb.getModelKB().ifThen(
							kb.getModelKB().arithm(kb.getVars()[i],"<",avg),
							kb.getModelKB().arithm(kb.getVars()[i-2],"!=",kb.getVars()[i])
				);
				
				if (i>3)
					kb.getModelKB().ifThen(
							kb.getModelKB().arithm(kb.getVars()[i],"<",avg),
							kb.getModelKB().arithm(kb.getVars()[i-3],"!=",kb.getVars()[i])
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
			purchaseIDs[i]= Integer.valueOf(parsed[parsed.length-1]);
			writeSolutionToFile(outputFile,purchases[i],i,false);
		}
		
		 
		return purchases;
		
	}
	
	public int [][] generateHistoricalTransactions(int number, String outputFile, boolean istype2){
		
		 Solver solver = kb.getModelKB().getSolver();
		 int counter = 0;
		 int [][] solutions = new int [number][numberOfVariables];
		 
		 VariableSelector varselector =  new InputOrder<>(kb.getModelKB());
		 IntValueSelector valueSelector = new IntDomainRandom(1);
		 do{
			 solver.setSearch(Search.intVarSearch(
		                
					 varselector,
		               
					 valueSelector,
		               
					 kb.getVars()
				));
			 
			 solver.solve();
			 solutions[counter] = new int [numberOfVariables];
			 for(int i=0;i<numberOfVariables;i++)
				 solutions[counter][i]=((IntVar)(kb.getModelKB().getVar(i))).getValue();
			 
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
					if(values[t]==kb.getDomains()[t][d]) // for ex : random=5 and d=5
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
	
	public void setCOBARIXHeuristics_onlyValue (VariableSelector variableSelector){
		
		SedasValueOrdering myValueOrder = new SedasValueOrdering(valueOrdering, kb.getDomains());
		//VariableSelector kb.getVars()elector =  new InputOrder<>(kb.getModelKB());
		
	     
		kb.getModelKB().getSolver().setSearch(Search.intVarSearch(
						variableSelector,
						myValueOrder,
						kb.getVars()	
		));

	}
	
	public void setCOBARIXHeuristics_onlyVar (IntValueSelector valueSelector){
		
		VariableSelector  varSelector =(VariableSelector<IntVar>) variables -> {
			for(int i =0;i<kb.getVars().length;i++){
		        return kb.getVars()[variableOrdering[i]];
		    }
		    return null;
		};
	     
		kb.getModelKB().getSolver().setSearch(Search.intVarSearch(
						varSelector,
						valueSelector,
						kb.getVars()	
		));

	}
	
	public void setCOBARIXHeuristics_both (){
		
		SedasValueOrdering myValueOrder = new SedasValueOrdering(valueOrdering, kb.getDomains());
		//VariableSelector kb.getVars()elector =  new InputOrder<>(kb.getModelKB());
		VariableSelector  varSelector =(VariableSelector<IntVar>) variables -> {
			for(int i =0;i<kb.getVars().length;i++){
		        return kb.getVars()[variableOrdering[i]];
		    }
		    return null;
		};
	     
		kb.getModelKB().getSolver().setSearch(Search.intVarSearch(
						varSelector,
						myValueOrder,
						kb.getVars()	
		));

	}
	
	public SedasValueOrdering getCOBARIXHeuristics (){
		
		SedasValueOrdering myValueOrder = new SedasValueOrdering(valueOrdering, kb.getDomains());
		//VariableSelector kb.getVars()elector =  new InputOrder<>(kb.getModelKB());
		
	    return myValueOrder;
	}
	
	public void setComparedHeuristics (VariableSelector variableSelector, IntValueSelector valueOrder){
		
		//VariableSelector kb.getVars()elector =  new InputOrder<>(kb.getModelKB());
		IntValueSelector valueSelector = valueOrder;
		
	     
		kb.getModelKB().getSolver().setSearch(Search.intVarSearch(
                
				variableSelector,
                // selects the smallest domain value (lower bound)
				 
				valueSelector,
               
                // variables to branch on
				kb.getVars()
		));
		
	}
	
}
