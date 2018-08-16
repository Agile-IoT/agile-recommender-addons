package test;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.constraints.LogicalConstraintFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

import algorithms.FlexDiag;

public class TestFlexDiag {

	 public static void main(String[] args) throws Exception {
		 Solver solver = new Solver("test");
		 IntVar v1 = VariableFactory.bounded("v1", 1, 12, solver);
		 IntVar v2 = VariableFactory.bounded("v2", 1, 12, solver);
		 IntVar v3 = VariableFactory.bounded("v3", 1, 12, solver);

		 Constraint c1 = IntConstraintFactory.arithm(v1,"=",20);
		 Constraint c3 = IntConstraintFactory.arithm(v3,"=",5);
		 Constraint c4 = IntConstraintFactory.arithm(v2,"<=",12);
		 //Constraint c = LogicalConstraintFactory.and(c1,c2,c3);
		 
		 solver.post(c3); 
		 solver.post(c1); 
		 solver.post(c4); 
		 
		 Solver solver2 = FlexDiag.cloneSolver(solver);
		 
		 Constraint[] diag = FlexDiag.diagnose(solver, 3, 1);
		 System.out.println(diag.length);
		 
		 Constraint[] diag2 = FlexDiag.diagnose(solver2, 3, 1);
		 System.out.println(diag2.length);
		 
	 }
}
