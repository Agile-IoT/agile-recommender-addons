package test;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;

import algorithms.FlexDiag;

public class TestChoco {
	public static void main (String [] args){
		
		Solver solver = new Solver("my first problem");
		
		IntVar x = VariableFactory.bounded("X", 0, 3, solver);
		IntVar y = VariableFactory.bounded("Y", 0, 5, solver);
		
		Constraint c1 = IntConstraintFactory.arithm(x, "=", 5);
		Constraint c2 = IntConstraintFactory.arithm(y, "=", 5);
				
				
		solver.post(c1);
		solver.post(c2);
		
		
		Constraint [] diag = FlexDiag.diagnose(solver, 2, 1);
		
		System.out.println("Diagnosis length: "+diag.length);
	}
	
	
	private static Solver cloneSolver (Solver solver){
		Solver newsolver = new Solver();
		Constraint [] c = solver.getCstrs();
		IntVar [] v = solver.retrieveIntVars();
	
		// COPY INTVARs
		for(int i=0;i<v.length;i++){
			VariableFactory.bounded(v[i].getName(), v[i].getLB(), v[i].getUB(), newsolver);
		}
		
		// COPY CONSTRAINTS
		for(int i=0;i<c.length;i++){
			newsolver.post(c[i]);	
		}
		
		
		return newsolver;
	}

}
