package at.tugraz.ist.configurator.csh.choco;

import java.util.Random;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class RandomCSPs implements TestCSP{
	
	private IntVar[][] vars = null;
	private Model[] chocoModel = null;
	//private Constraint [] cons= null;
	
	private IntVar[] vars_newCSP = null;
	private Model chocoModel__newCSP = null;
	private Constraint[] cons_newCSP= null;
	
	private int nextModelID=0;
	private int numberOfVars = 0;
	private int max_numberOfCons = 0;
	private int numberOfCons = 0;
	
	private int maxDomain=0;
	
	public RandomCSPs(){
		super();
	}
	
	
	public Model createCons(Model model){
		int index = 0;
		Constraint [] cons = new Constraint[max_numberOfCons];
		
		for(int v=0;v<numberOfVars;v++){
				if(((IntVar) model.getVar(v)).getDomainSize()>1){
					cons[index] = model.arithm((IntVar) model.getVar(v), ">", v);
					cons[index].post();
					index++;
					if(index==max_numberOfCons)
						break;
				}
		}
		
		numberOfCons = index;
		
		return model;
	}
	
	
	public void createTestCSPs(int numberOfCSPs, int numberOfVars, int maxDomain, int max_numberOfCons){
		
		this.maxDomain = maxDomain;
		nextModelID = numberOfCSPs;
		this.numberOfVars = numberOfVars;
		this.max_numberOfCons = max_numberOfCons;
		
		vars = new IntVar[numberOfCSPs][numberOfVars];
		chocoModel = new Model[numberOfCSPs];
		
		Random rand = new Random();
		
		for(int p=0;p<numberOfCSPs;p++){
			
			chocoModel[p] = new Model("TestCSP_"+p);
			
			for(int i=0;i<numberOfVars;i++){
				
				if(rand.nextBoolean()) {
					 // set a value for this variable
					 vars[p][i] =  chocoModel[p].intVar("v"+i, rand.nextInt(maxDomain));
					
				 } 
				 else{
					 // do not set a value for this variable
					 vars[p][i] =  chocoModel[p].intVar("v"+i, 0, maxDomain);
					 
				 }
			}
			chocoModel[p] = createCons(chocoModel[p]);
		}
		
	}

	
    public void createOneTestCSP(){
		
		
    	chocoModel__newCSP = new Model("NewCSP");
    	vars_newCSP = new IntVar [numberOfVars];
		Random rand = new Random();
		
			for(int i=0;i<numberOfVars;i++){
				
				if(rand.nextBoolean()) {
					 // set a value for this variable
					vars_newCSP[i] =  chocoModel__newCSP.intVar("v"+i, rand.nextInt(this.maxDomain));
					
				 } 
				 else{
					 // do not set a value for this variable
					vars_newCSP[i] =  chocoModel__newCSP.intVar("v"+i, 0, this.maxDomain);
					 
				 }
			}
			
		chocoModel__newCSP = createCons(chocoModel__newCSP);
			
			
	}
	
	
	public IntVar[][] getVars() {
		// TODO Auto-generated method stub
		return vars;
	}

	public Model[] getModels() {
		// TODO Auto-generated method stub
		return chocoModel;
	}

	
	public void setVars(IntVar[][] variables) {
		// TODO Auto-generated method stub
		vars = variables;
	}

	public void setModels(Model[] models) {
		// TODO Auto-generated method stub
		chocoModel = models;
	}

	
	public Model getNewModel(int index) {
		
		Model newModel = new Model(Integer.toString(nextModelID));
		//System.out.println(newModel.getName());
		nextModelID++;
		
		for(int i=0;i<numberOfVars;i++){
			IntVar var = null;
			// index=-1 is the new CSP
			if(index==-1)
				var = vars_newCSP[i];
			else 
				var = vars[index][i];
			
			if(var.getDomainSize()==1)
				newModel.intVar("v"+i, var.getValue());
			else
				newModel.intVar("v"+i, 0, maxDomain);
		}
		
		// constraints are always same based on the variables
		newModel = createCons(newModel);
		
		return newModel;
	}

	public IntVar[] getVars_newCSP() {
		return vars_newCSP;
	}

	public void setVars_newCSP(IntVar[] vars_newCSP) {
		this.vars_newCSP = vars_newCSP;
	}

	public Model getChocoModel__newCSP() {
		return chocoModel__newCSP;
	}

	public void setChocoModel__newCSP(Model chocoModel__newCSP) {
		this.chocoModel__newCSP = chocoModel__newCSP;
	}

	public Constraint[] getCons_newCSP() {
		return cons_newCSP;
	}

	public void setCons_newCSP(Constraint[] cons_newCSP) {
		this.cons_newCSP = cons_newCSP;
	}

}
