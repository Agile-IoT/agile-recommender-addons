package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.chocosolver.parser.ParserListener;
import org.chocosolver.parser.flatzinc.BaseFlatzincListener;
import org.chocosolver.parser.flatzinc.Flatzinc;
import org.chocosolver.parser.flatzinc.FznSettings;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.constraints.LogicalConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;


public class TestFZN {
	
	 
	
	 public static void main(String[] args) throws Exception {
		 
		 // 1- DEFINE INPUT FILE
		// File initialFile = new File("C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/ChocoZN/test0_0.czn");
		 String initialFile = new String("C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/ChocoZN/test0_0.czn");
		 args = new String [1];
		 args[0] =initialFile;
		 //InputStream targetStream = new FileInputStream(initialFile);
		 
		 
		 // CHOCO3.3 PARSER:
//		 System.out.println("Problem #"+i+", DataFile#"+j);
//		 File initialFile = new File("C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/ChocoZN/test"+i+"_"+j+".czn");
//		 InputStream targetStream = new FileInputStream(initialFile);
//		 
//		 // 3- GENERATE PAST TRANSACTIONS 
//		 
//		 String outputFile = "C:/Users/spolater/Desktop/AGILE/STS_WORKSPACE/LCR/FILES/PastReqs/past"+i+"_"+j+".czn"; 
//		 int numberOfVariables=0;
//			 
//			 // CREATE SOLVER AND LOAD INPUT FILE
//			 targetStream = new FileInputStream(initialFile);
//			 fzn[p] = new Flatzinc();
//			 fzn[p].createSolver();	
//			 Solver solver = fzn[p].getSolver();
//			 //fzn[p].parse(solver, targetStream);
//			 fzn[p].parse(targetStream);
		 
		 // 2- CREATE SOLVER AND LOAD INPUT FILE
	
		 Flatzinc fzn = new Flatzinc();
         fzn.addListener(new BaseFlatzincListener(fzn));

         fzn.parseParameters(args);
         fzn.defineSettings(new FznSettings());
         fzn.createSolver();
         fzn.parseInputFile();
         fzn.configureSearch();
         //fzn.solve();
		
         //fzn.parse(solver, targetStream);
		//fzn.parseInputFile();
		 Solver solver = fzn.getSolver();
		 // GET VARIABLES
		 IntVar [] intVars = solver.retrieveIntVars();
		 BoolVar[] boolVars = solver.retrieveBoolVars();
		 
		 // GENERATE AND LOAD 3 USER REQs
		 for (int i=0;i<5;i++){
			 int lb = intVars[i].getLB();
			 int ub = intVars[i].getUB();
			 Constraint a = IntConstraintFactory.arithm(intVars[i],"=",lb);
			 Constraint b = IntConstraintFactory.arithm(intVars[i],"=",ub);
			 Constraint c = LogicalConstraintFactory.or(a,b);
			 solver.post(c); 
		 }
		 
		 // 3- SET HEURISTICS
		 // fzn.configureSearch();
		 // solver.set(IntStrategyFactory.maxDom_Split(all)); 
		 
		 
		 // 4- SOLVE
		 System.out.println("The First Solution: ");
		 System.out.println("----------");		 
		 fzn.solve();
		 
		 //prettyOut(fzn, solver,true);
		 
		 
		 System.out.print("THE END");
	 }
	 
//	 public static void prettyOut(Flatzinc fzn, Solver solver, boolean onlyOne) {
//
//		 if (solver.isFeasible() == ESat.TRUE) {
//			 int num_solutions = 1;
//			 do {
//			        //System.out.print(solver);
//				    System.out.print("Solution #"+num_solutions);
//				    fzn.solve();
//			        num_solutions++;
//			  } while (solver.nextSolution() == Boolean.TRUE && !onlyOne);
//		 }
//		 
//		    
//      }


}
