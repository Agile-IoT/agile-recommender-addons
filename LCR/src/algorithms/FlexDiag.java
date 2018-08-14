package algorithms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bouncycastle.util.Arrays;
import org.chocosolver.parser.flatzinc.Flatzinc;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.ESat;

public class FlexDiag {
	
	
	public static Constraint [] diagnose(Solver solver,int size, int m) {
		
		IntVar [] intVars = solver.retrieveIntVars();
		 

		Constraint [] allconstraints = solver.getCstrs();
		List<Constraint> constraints = new ArrayList<Constraint>();
		
		for(int i=0;i<size;i++)
			constraints.add(allconstraints[allconstraints.length-size+i]);
		
		List<Constraint> diagList= FlexDiag(constraints, solver, m);
		Constraint [] diag = new Constraint [diagList.size()];
		for(int i=0;i<diagList.size();i++)
			diag[i] =diagList.get(i);
		return diag;
		
	}
	
	public static Constraint [] diagnose(Solver solver, double [][]sorted_reqs, int size, int m) {
		
		IntVar [] intVars = solver.retrieveIntVars();

		Constraint [] allconstraints = solver.getCstrs();
		List<Constraint> constraints = new ArrayList<Constraint>();
		
		List<Integer> indexes = new ArrayList<Integer>();
		
		for(int j=0;j<sorted_reqs.length;j++){
			if(sorted_reqs[j][0]!=-1)
				indexes.add((int) sorted_reqs[j][0]);
		}
		
		// add first order constraints
		for(int i=0;i<indexes.size();i++){
			
			boolean flag=false;
			int constIndex1 = -1;
			try{
			String intVarName = "["+intVars[indexes.get(i)].getName()+" =";
			
			for(int j=0;j<size;j++){
			    constIndex1 = allconstraints.length-size+j;
				String constraint = allconstraints[constIndex1].toString();
				
				if(constraint.contains(intVarName)){
					flag=true;
					break;
				}
			}
			}catch(Exception ex){
				System.out.println(ex);
			}
			if(flag)
				constraints.add(allconstraints[constIndex1]);
			
		}
		
		// add the rest 
		for(int i=0;i<size;i++){
			boolean flag=false;
			int constIndex1 = -1;
			String intVarName = "";
			try{
				intVarName = intVars[indexes.get(i)].getName();
			}catch(Exception e){
				System.out.print(e);
			}
			
			for(int j=0;j<indexes.size();j++){
				try{
				constIndex1 = allconstraints.length-size+j;
				String constraint = allconstraints[constIndex1].toString();
				
				if(constraint.contains(intVarName)){
					flag=true;
					break;
				}
				}catch(Exception ex)
				{
					System.out.print("PROBLEM");
				}
			}
			if(!flag)
				constraints.add(allconstraints[allconstraints.length-size+i]);
		}
			
	
		List<Constraint> diagList= FlexDiag(constraints, solver, m);
		Constraint [] diag = new Constraint [diagList.size()];
		for(int i=0;i<diagList.size();i++)
			diag[i] =diagList.get(i);
		return diag;
	}
	
	
//	Algorithm 1 − FlexDiag
	
	
//	1 func FlexDiag(C ⊆ AC, AC = {c1..ct,m ) : ∆
//	2 if isEmpty(C) or inconsistent(AC − C) return null
//	3 else return F D(null, C, AC)
// --------------------------------------------
//	4 func FD(D, C = {c1..cq}, AC, m) : diagnosis ∆
//	5 if D != null and consistent(AC) return ∅;
//	6 if size(C)=m return C;
//	7 k = q/2;
//	8 C1 = {c1..ck}; C2 = {ck+1..cq};
//	9 D1 = F D(C1, C2, AC − C1);
//	10 D2 = F D(D1, C1, AC − D1);
//	11 return(D1 ∪ D2);
	private static List<Constraint> FlexDiag(List<Constraint> constraints, Solver solver, int m){
		Set<Constraint> diagnosis = null;
		if(constraints==null||constraints.size()==0)
			return null;
		else
			return FD(diagnosis,constraints, solver, m);
		
	}
	
	private static List<Constraint> FD(Set<Constraint> diag, List<Constraint> constraints,Solver solver, int m){
		//solver.getEnvironment().worldPush();
		//solver.getSearchLoop().restoreRootNode();
		
		if(diag!=null && solver.findSolution()){
			//System.out.println("Solved with constraints "+solver.getNbCstrs());
			return new ArrayList<Constraint>(diag);
		}
		if(diag==null)
			diag = new HashSet<Constraint>();
			
		if(constraints.size()==m)
			return constraints;
		
		int k = constraints.size()/2;
		
		//Constraint []c1 = new Constraint [k];
		//Constraint []c2 = new Constraint [constraints.size()-k];
		List<Constraint> c1 = new ArrayList<Constraint>();
		List<Constraint> c2 = new ArrayList<Constraint>();
		
		Constraint []c =solver.getCstrs();
		Variable[] vars = solver.getVars();
		
		
		//s1.getEnvironment().worldPop();
		Solver s1 = cloneSolver(solver);
		Solver s2 = cloneSolver(solver);
	
		// ADD CONSTRAINTS
		for(int i=0;i<constraints.size();i++){
			
			if(i<k){
				c1.add(constraints.get(i));
				s1.unpost(constraints.get(i));	
			}
			else{
				c2.add(constraints.get(i));
			}
		}
		
		
		Set<Constraint> c1set = new HashSet<Constraint>(c1);
		List<Constraint> d1 = FD(c1set,c2,s1,m);
		
		for(int i=0;i<d1.size();i++)
			s2.unpost(d1.get(i));	
		
		
		Set<Constraint> d1set = new HashSet<Constraint>(d1);
		List<Constraint> d2 = FD(d1set,c1,s2,m);
		
		List<Constraint> diagnosis = new ArrayList<Constraint>();
		diagnosis.addAll(d1);
		diagnosis.addAll(d2);
		
		Set set = new HashSet(diagnosis);
		List list = new ArrayList(set);
		return list;
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
