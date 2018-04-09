package at.tugraz.ist.configurator.csh.choco;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMax;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

public class TestValueOrderingEffects {
	
	public static void main(String [] args){
		
		Model model1= new Model("model1");
		IntVar a1 = model1.intVar("a1", 0, 20, false);
		IntVar b1 = model1.intVar("b1", 0, 20, false);
		IntVar c1 = model1.intVar("c1", 0, 20, false);
		model1.arithm(a1, "+", b1, "=", c1).post();
		System.out.println("solveWithoutHeuristics");
		solveWithoutHeuristics(model1);
		
		
//		
//		Model model2= new Model("model2");
//		IntVar a2 = model2.intVar("a2", 0, 20, false);
//		IntVar b2 = model2.intVar("b2", 0, 20, false);
//		IntVar c2 = model2.intVar("c2", 0, 20, false);
//		model2.arithm(a2, "+", b2, "=", c2).post();
//		System.out.println("solveWithVariableOrderingHeuristics");
//		solveWithDefaultHeuristics(model2,null,null,1);
//		
//		
		Model model3= new Model("model3");
		IntVar a3 = model3.intVar("a3", 0, 20, false);
		IntVar b3 = model3.intVar("b3", 0, 20, false);
		IntVar c3 = model3.intVar("c3", 0, 20, false);
		model3.arithm(a3, "+", b3, "=", c3).post();
		System.out.println("solveWithValueOrderingHeuristics");
		solveWithDefaultHeuristics(model3,null,null,2);
		
	}
	
	public static void solveWithoutHeuristics(Model chocoModel){
		
		  Solver solver = chocoModel.getSolver();
 	 
		
		  	 int count =0;
			 while(solver.solve()){
				 if (count<1)
					 System.out.println("FIRST SOLUTION: "+chocoModel);
				 //System.out.println(((IntVar) chocoModel.getVar(0)).getValue());
				 //solver.printStatistics();
				 count++;
			 }
			 //solver.printStatistics();
			 //System.out.println(((IntVar) chocoModel.getVar(0)).getValue());
		 
	
	}
	
	public static void solveWithDefaultHeuristics(Model chocoModel, int [] variableOrder, CSHValueOrder valueOrder, int type){
		
		long time = 0;
	
			 Solver solver = chocoModel.getSolver();
			 
			 // setHeuristics
			 solver = setHeuristicToSolver(chocoModel, solver, variableOrder,valueOrder,type);
			
			 
			 int count =0;
			 while(solver.solve()){
				 if (count<1)
					 System.out.println("FIRST SOLUTION: "+chocoModel);
				 //System.out.println(((IntVar) chocoModel.getVar(0)).getValue());
				 //solver.printStatistics();
				 count++;
			 }
			 //solver.printStatistics();
			 //System.out.println(((IntVar) chocoModel.getVar(0)).getValue());
		 
		 
	}
	
	private static IntVar[] getIntVars(Model model){
		 Variable[] vars = model.getVars();
		 IntVar[] intvars = new IntVar[vars.length];
		 
		 for(int v=0;v<vars.length;v++){
			 intvars[v]= (IntVar)vars[v];
		 }
		 return intvars;
		
	}
	
	private static Solver setHeuristicToSolver(Model model, Solver solver, int[] varOrder, CSHValueOrder valueOrder,int type){
		 
		 IntVar[] intvars = getIntVars(model);
		 VariableSelector varSelector = null;
		 IntValueSelector valueSelector=valueOrder;
		
		 varSelector =(VariableSelector<IntVar>) variables -> {
				for(int i =0;i<model.getVars().length;i++){
			        return intvars[i];
			    }
			    return null;
	     };
	     
	     // SET DEFAULT IF NULL
	     if(type==2 && valueOrder==null){
	    	 valueSelector = new IntDomainMax();
	    	 varSelector = new InputOrder<>(model);
	     }
	     if(type==1 && varOrder==null){
	    	 varSelector = new Smallest();
	    	 valueSelector = new IntDomainMin();
	     }
	     
		 solver.setSearch(intVarSearch(
                
				 varSelector,
                // selects the smallest domain value (lower bound)
				 
				 valueSelector,
				 //new IntDomainMin(),
                //new IntDomainMax(),
               
                // variables to branch on
                intvars
		));
		    
		return solver;
	 }
	
}
