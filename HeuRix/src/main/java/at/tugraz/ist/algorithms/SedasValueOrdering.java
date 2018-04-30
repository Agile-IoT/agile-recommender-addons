package at.tugraz.ist.algorithms;

import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.variables.IntVar;

public class SedasValueOrdering implements IntValueSelector {
	
	    int [][] valueIDs =null;
	    int [][] domainValues =null;
	    int [] counters;
	    //int counter = 0;
	    //int varID = -1;
	    
	    public SedasValueOrdering(int[][] heuristic, int [][] values){
	    	valueIDs = heuristic;
	    	domainValues = values;
	    	counters = new int[values.length];
	    }
	    
	 	@Override
	    public int selectValue(IntVar var) {
	 		int valueID= 0;
	 		int varID = var.getId()-1;
	 		
//	 		if(varID!=var.getId()){
//	 			counter = 0;
//	 			varID = var.getId();
//	 		}
	 		
	 		if(counters[varID] +1 >= domainValues[varID].length)
	 			counters[varID] = 0;
	 		
	 		valueID = valueIDs[varID][counters[varID]];
	 		int returnvalue = domainValues[varID][valueID];
	 				
	 				
	 		//System.out.println("varID: "+var.getId()+", domainSize:"+var.getDomainSize()+", counter: "+counter+", returnvalue: "+returnvalue);
	 		counters[varID]++;
	 			
	        return returnvalue;
	    }
}
