package at.tugraz.ist.configurator.csh.choco;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMax;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMedian;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMiddle;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandom;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandomBound;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.ActivityBased;
import org.chocosolver.solver.search.strategy.selectors.variables.AntiFirstFail;
import org.chocosolver.solver.search.strategy.selectors.variables.Cyclic;
import org.chocosolver.solver.search.strategy.selectors.variables.DomOverWDeg;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;
import org.chocosolver.solver.search.strategy.selectors.variables.GeneralizedMinDomVarSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.ImpactBased;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.search.strategy.selectors.variables.Largest;
import org.chocosolver.solver.search.strategy.selectors.variables.MaxRegret;
import org.chocosolver.solver.search.strategy.selectors.variables.Occurrence;
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.iterators.IntVarValueIterator;

public class MyChocoSolver {

	private Model cloneModel(int indexOfModel, TestCSP cspList){
		Model cloneModel = cspList.getNewModel(indexOfModel);
		return cloneModel ;
	}
	public long solveWithoutHeuristics(int indexOfModel, TestCSP cspList){
		
		 // UserModel userModel = CSH.modelsOfTheSameProblem.get(modelIndex);
		 Model chocoModel= cloneModel(indexOfModel,cspList);
		 Solver solver = chocoModel.getSolver();
  	 
		 long time = 0;
		 long start = System.nanoTime();
		 boolean isSolved= solver.solve();
		 long end = System.nanoTime();
		 time = end-start;
		 
//		 solver.printStatistics();
//		 while(solver.solve()){
//			 System.out.println(((IntVar) chocoModel.getVar(0)).getValue());
//		 }
	
		 return time;
	}
		 
	
	
	public long solveWithHeuristic(int indexOfModel, TestCSP cspList, int [] variableOrder, CSHValueOrder valueOrder,boolean flagLearning){
		
		long time = 0;
		// for (int i=0;i<10;i++){
			 // UserModel userModel = CSH.modelsOfTheSameProblem.get(modelIndex);
			 
			 // setHeuristics
			 if(!flagLearning && variableOrder==null){
				 System.out.println("variableOrder is NULL");
				 for(int varHeur=0;varHeur<13;varHeur++){
					 Model chocoModel= cloneModel(indexOfModel,cspList);
					 Solver solver = chocoModel.getSolver();
					 
					 solver = setHeuristicToSolver(chocoModel, solver, variableOrder,valueOrder,varHeur,0,flagLearning);
					 long start = System.nanoTime();
					 boolean isSolved= solver.solve();
					 long end = System.nanoTime();
					 time = end-start;
					 System.out.println(time);
				 }
			 }
			 else if(!flagLearning && valueOrder==null){
				 System.out.println("valueOrder is NULL");
				 for(int valHeur=0;valHeur<6;valHeur++){
					 Model chocoModel= cloneModel(indexOfModel,cspList);
					 Solver solver = chocoModel.getSolver();
					 solver = setHeuristicToSolver(chocoModel, solver, variableOrder,valueOrder,0,valHeur,flagLearning);
					 long start = System.nanoTime();
					 boolean isSolved= solver.solve();
					 long end = System.nanoTime();
					 time = end-start;
					 System.out.println(time);
				 }
			 }
			 else{
				 if(!flagLearning)
					 System.out.println("variableOrder and valueOrder is not NULL");
				 
				 Model chocoModel= cloneModel(indexOfModel,cspList);
				 Solver solver = chocoModel.getSolver();
				 solver = setHeuristicToSolver(chocoModel, solver, variableOrder,valueOrder,0,0,flagLearning);
				 long start = System.nanoTime();
				 boolean isSolved= solver.solve();
				 long end = System.nanoTime();
				 time = end-start;
				 
				 if(!flagLearning)
					 System.out.println(time);
			 }
			
			 
//			 solver.printStatistics();
//			 while(solver.solve()){
//				 System.out.println(((IntVar) chocoModel.getVar(0)).getValue());
//			 }
		//} 
		
		//System.out.println(time/10);
		//return time/10;
		return time;
	   
	}
	
	public long[] solveWithBuiltinHeuristics(int indexOfModel, TestCSP cspList){
		
		 IntValueSelector [] valueorderingheuristics = new IntValueSelector[6];
		
		 valueorderingheuristics[0] = new IntDomainMax();
		 valueorderingheuristics[1] = new IntDomainMin();
		 valueorderingheuristics[2] = new IntDomainMedian();
		 valueorderingheuristics[3] = new IntDomainMiddle(true);
		 valueorderingheuristics[4] = new IntDomainRandom(1);
		 valueorderingheuristics[5] = new IntDomainRandomBound(1);
	    
	     
		
		 VariableSelector [] heuristics = new VariableSelector [13];
		 long times [] = new long [13];
		 heuristics[0] = new Smallest();
		 heuristics[1] = new Largest();
		 heuristics[2] = null; //new ActivityBased<>();
		 heuristics[3] = null; //new FirstFail(null);
		 heuristics[4] = null; //new AntiFirstFail(null);
		 heuristics[5] = new Cyclic<>();
		 heuristics[6] = new MaxRegret();
		 heuristics[7] = new Occurrence<>();
		 heuristics[8] = null; // new InputOrder<>(null);
		 heuristics[9] = null; //new DomOverWeg();
		 heuristics[10] = null; //new ImpactBased();
		 heuristics[11] = new GeneralizedMinDomVarSelector();
		 heuristics[12] = new org.chocosolver.solver.search.strategy.selectors.variables.Random<>((long)0.011);
		 
		 for (int j=0;j<6;j++){
			 System.out.println("valueordering: "+j);
			 for (int i=0;i<13;i++){
				  Model chocoModel= cloneModel(indexOfModel,cspList);
				 Solver solver = chocoModel.getSolver();
				 
				 if(i==2)
					 solver.setSearch( new ActivityBased(getIntVars(chocoModel)));
				 else if(i==3){
					 solver.setSearch(intVarSearch(
			                 
							 new FirstFail(chocoModel),
			                 
							 valueorderingheuristics[j],
			                
			                 // variables to branch on
			                 getIntVars(chocoModel)
						));
				 }
			     else if(i==4){
			    	 solver.setSearch(intVarSearch(
			    			 
			    			 new AntiFirstFail(chocoModel),
			    			 
			    			 valueorderingheuristics[j],
			                
			                 // variables to branch on
			                 getIntVars(chocoModel)
						));
			     }
			     else if(i==8)
					 solver.setSearch(intVarSearch(
			    			 
			    			 new InputOrder<>(chocoModel),
			    			 
			    			 valueorderingheuristics[j],
			                
			                 // variables to branch on
			                 getIntVars(chocoModel)
						));
				 
				 else if(i==9)
					 solver.setSearch( new DomOverWDeg(getIntVars(chocoModel), (long) 0.01, new IntDomainMin()));
				 else if (i==10)
				 	 solver.setSearch(new ImpactBased(getIntVars(chocoModel), false));	
				 else{
					 solver.setSearch(intVarSearch(
		                 
						 heuristics[0],
		                 
						 valueorderingheuristics[j],
		                
		                 // variables to branch on
		                 getIntVars(chocoModel)
					));
					 
					 	
				}
				 long start = System.nanoTime();
				 boolean isSolved= solver.solve();
				 long end = System.nanoTime();
				 times[i] = end-start;
				 System.out.println(times[i]);
			
		 }
			 
		 }
		return times;
	}
		 
	private IntVar[] getIntVars(Model model){
		 Variable[] vars = model.getVars();
		 IntVar[] intvars = new IntVar[vars.length];
		 
		 for(int v=0;v<vars.length;v++){
			 intvars[v]= (IntVar)vars[v];
		 }
		 return intvars;
		
	}
	private Solver setHeuristicToSolver(Model model, Solver solver, int[] varOrder, CSHValueOrder valueOrder, int varHeur, int ValHeur, boolean flagLearning){
		 
		 IntVar[] intvars = getIntVars(model);
		 VariableSelector varSelector = null;
		 IntValueSelector valueSelector = valueOrder;
		
		 varSelector =(VariableSelector<IntVar>) variables -> {
				for(int i =0;i<model.getVars().length;i++){
			        return intvars[varOrder[i]];
			    }
			    return null;
	     };
	     
	     // SET DEFAULT IF NULL
	     if(!flagLearning && valueOrder==null){
	    	 IntValueSelector [] valueorderingheuristics = new IntValueSelector[6];
				
	 		 valueorderingheuristics[0] = new IntDomainMax();
	 		 valueorderingheuristics[1] = new IntDomainMin();
	 		 valueorderingheuristics[2] = new IntDomainMedian();
	 		 valueorderingheuristics[3] = new IntDomainMiddle(true);
	 		 valueorderingheuristics[4] = new IntDomainRandom(1);
	 		 valueorderingheuristics[5] = new IntDomainRandomBound(1);
	 	    
	    	 solver.setSearch(intVarSearch(
	                     
	    				 varSelector,
	                     // selects the smallest domain value (lower bound)
	    				 
	    				 valueorderingheuristics[ValHeur],
	    				 //new IntDomainMin(),
	                     //new IntDomainMax(),
	                    
	                     // variables to branch on
	                     intvars
	    		));
	    		
	     }

	     
	     else if(!flagLearning && varOrder==null){
	    	 
			 VariableSelector [] heuristics = new VariableSelector [13];
			 long times [] = new long [13];
			 heuristics[0] = new Smallest();
			 heuristics[1] = new Largest();
			 heuristics[2] = null; // new ActivityBased<>();
			 heuristics[3] = null; // new FirstFail(null);
			 heuristics[4] = null; // new AntiFirstFail(null);
			 heuristics[5] = new Cyclic<>();
			 heuristics[6] = new MaxRegret();
			 heuristics[7] = new Occurrence<>();
			 heuristics[8] = null; // new InputOrder<>(null);
			 heuristics[9] = null; // new DomOverWeg();
			 heuristics[10] = null; // new ImpactBased();
			 heuristics[11] = new GeneralizedMinDomVarSelector();
			 heuristics[12] = new org.chocosolver.solver.search.strategy.selectors.variables.Random<>((long)0.011);
		
				 if(varHeur==2)
					 solver.setSearch( new ActivityBased(getIntVars(model)));
				 
				 else if(varHeur==3){
					 solver.setSearch(intVarSearch(
			                 
							 new FirstFail(model),
			                 
							 valueSelector,
			                
			                 // variables to branch on
			                 getIntVars(model)
						));
				 }
				 
			     else if(varHeur==4){
			    	 solver.setSearch(intVarSearch(
			    			 
			    			 new AntiFirstFail(model),
			    			 
			    			 valueSelector,
			                
			                 // variables to branch on
			                 getIntVars(model)
						));
			     }
			     else if(varHeur==8)
					 solver.setSearch(intVarSearch(
			    			 
			    			 new InputOrder<>(model),
			    			 
			    			 valueSelector,
			                
			                 // variables to branch on
			                 getIntVars(model)
						));
				 
				 else if(varHeur==9)
					 solver.setSearch( new DomOverWDeg(getIntVars(model), (long) 0.01, new IntDomainMin()));
				 
				 else if (varHeur==10)
				 	 solver.setSearch(new ImpactBased(getIntVars(model), false));
				 
				 else{
					 solver.setSearch(intVarSearch(
		                 
						 heuristics[varHeur],
		                 
						 valueSelector,
		                
		                 // variables to branch on
		                 getIntVars(model)
					));
				 }
				 
	     }	
	     
	  
	     else{
	    	 if(valueSelector==null)
	    		 valueSelector = new IntDomainMin();
	    	 if(varOrder==null)
	    		 varSelector = new Smallest();
	    	 
			 solver.setSearch(intVarSearch(
	                 
					 varSelector,
	                 // selects the smallest domain value (lower bound)
					 
					 valueSelector,
					 //new IntDomainMin(),
	                 //new IntDomainMax(),
	                
	                 // variables to branch on
	                 intvars
			));
	     }
		
		 
		 
		return solver;
	 }
	
}
