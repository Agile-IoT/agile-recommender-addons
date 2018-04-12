package at.tugraz.ist;

import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.variables.IntVar;

public class SedasValueOrdering implements IntValueSelector {
	
	    int [][] valueSelections =null;
	    int counter = 0;
	    int varID = -1;
	    
	    public SedasValueOrdering(int[][] heuristic){
	    	valueSelections = heuristic;
	    }
	    
	 	@Override
	    public int selectValue(IntVar var) {
	 		int returnvalue= 0;
	 		if(varID!=var.getId()){
	 			counter = 0;
	 			varID = var.getId();
	 		}
	 		returnvalue = valueSelections[var.getId()-1][counter];
	 		counter++;
	 			
	        return returnvalue;
	    }
}
