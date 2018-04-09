package at.tugraz.ist.configurator.csh.choco;

import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.variables.IntVar;

public class CSHValueOrder implements IntValueSelector {
	
	    int [] valueSelections =null;
	    int counter = 0;
	    
	    public CSHValueOrder(int[] heuristic){
	    	valueSelections = heuristic;
	    }
	    
	 	@Override
	    public int selectValue(IntVar var) {
	 		int returnvalue= 0;
	 		int lowerbound= var.getLB();
	        int upperbound= var.getUB();
	        
	 		try{
	 			if(counter>9)
	 				counter=0;
	 			
	 			int selectedPercentageToStartSearch = lowerbound + ((upperbound-lowerbound)/10)*valueSelections[counter];
	 			
	 			returnvalue = selectedPercentageToStartSearch;
	 			counter++;
	 			
	 		}catch(Exception e){
	 			returnvalue = lowerbound;
	 		}
	        
	        return upperbound;
	    }
}
