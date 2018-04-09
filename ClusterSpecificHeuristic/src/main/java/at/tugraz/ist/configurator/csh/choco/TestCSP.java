package at.tugraz.ist.configurator.csh.choco;

import org.chocosolver.solver.Model;

public interface TestCSP {
	
	public void createTestCSPs(int numberOfCSPs, int numberOfVars, int maxDomain, int max_numberOfCons);
	public void createOneTestCSP();
	public Model getNewModel(int index);

}
