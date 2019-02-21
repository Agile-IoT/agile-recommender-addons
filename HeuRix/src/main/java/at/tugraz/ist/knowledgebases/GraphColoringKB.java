package at.tugraz.ist.knowledgebases;
import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class GraphColoringKB implements KB{
	
	Model modelKB = new Model("CameraConfigurationProblem");
	int numberOfVariables = 10;
	IntVar[] vars = new IntVar[numberOfVariables];
	int [][] domains = new int [numberOfVariables][];
	
	List<Constraint> constraints = new ArrayList<Constraint>();
	
	public GraphColoringKB(){
		// 100 variables
    	defineVariables();
    	// 180 constraints
    	defineConstraints();
	}
	
	public void defineVariables (){
		
		// 10 colors, 100 vertices
		for (int i=0;i<100;i++){
			domains[i] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			vars[i] = this.modelKB.intVar("vertex-"+i, domains[i]);
		}
       
	}
	public void defineConstraints() {

		
		for (int i=0;i<10;i++){
			for (int j=0;i<10;i++){
				if(j<9){
					// rows
					Constraint c1 = this.modelKB.allDifferent(vars[i*j],vars[i*j+1]);
					c1.post();
					constraints.add(c1);
					
					// columns
					Constraint c2 = this.modelKB.allDifferent(vars[i*j],vars[i*j+10]);
					c2.post();
					constraints.add(c2);
				}
			}
		}
		
	}

	@Override
	public Model getModelKB() {
		// TODO Auto-generated method stub
		return modelKB;
	}

	@Override
	public void setModelKB(Model m) {
		// TODO Auto-generated method stub
		modelKB = m;
	}

	@Override
	public int getNumberOfVariables() {
		// TODO Auto-generated method stub
		return numberOfVariables;
	}

	@Override
	public void setNumberOfVariables(int n) {
		// TODO Auto-generated method stub
		numberOfVariables = n;
	}

	@Override
	public IntVar[] getVars() {
		// TODO Auto-generated method stub
		return vars;
	}

	@Override
	public void setVars(IntVar[] v) {
		// TODO Auto-generated method stub
		vars=v;
	}

	@Override
	public int[][] getDomains() {
		// TODO Auto-generated method stub
		return domains;
	}
	

}

