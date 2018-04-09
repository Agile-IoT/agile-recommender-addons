package at.tugraz.ist.configurator.csh.choco;

import java.util.Random;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class MapColoringCSP implements TestCSP{
	
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
	
	public MapColoringCSP(){
		super();
	}
	
	public Model createCons(Model model){
		int index = 0;
		Constraint [] cons = new Constraint[numberOfVars*4];
		
		// EACH ROW HAS 10 ITEMS
		for (int m=0;m<numberOfVars/10;m++){
			for (int n=0;n<10;n++){
				int id = m*10+n;
				int testVar;
				
				// c1: n!=0 -> check left
				if(n!=0){
					cons[index] = model.arithm((IntVar) model.getVar(id), "!=", ((IntVar) model.getVar(id-1)).getValue());
					cons[index].post();
					index++;
				}
				
				// c2: n!=numberOfVars -> check right
				if(n!=9){
					cons[index] = model.arithm((IntVar) model.getVar(id), "!=", ((IntVar) model.getVar(id+1)).getValue());
					cons[index].post();
					index++;
				}
				
				// c3: m!=0 -> check up
				if(m!=0){
					cons[index] = model.arithm((IntVar) model.getVar(id), "!=", ((IntVar) model.getVar(id-10)).getValue());
					cons[index].post();
					index++;
				}
				
				// c4: m!=numberOfVars -> check down
				if(m!=(numberOfVars/10)-1){
					cons[index] = model.arithm((IntVar) model.getVar(id), "!=", ((IntVar) model.getVar(id+10)).getValue());
					cons[index].post();
					index++;
				}
				
			}
			
		}
		
		numberOfCons = index;
		
		return model;
	}
	
	public Model getNewModel(int index) {
		vars_newCSP = new IntVar[numberOfVars];
		Model newModel = new Model(Integer.toString(nextModelID));
		//System.out.println(newModel.getName());
		nextModelID++;
		
		for(int n=0;n<10;n++){
			for(int m=0;m<numberOfVars/10;m++){
				int id = 10*n+m;
				IntVar var = null;
				vars_newCSP[id] = newModel.intVar("v"+id, 0, maxDomain);
			}
		}
		
		// constraints are always same based on the variables
		newModel = createCons(newModel);
		
		return newModel;
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
			
			chocoModel[p] = getNewModel(p);
			vars[p] = vars_newCSP;
			
			for(int i=0;i<max_numberOfCons;i++){
				 int m = rand.nextInt(numberOfVars/10); // ROW
				 int n = rand.nextInt(10); // COL
				 int id = m*10+n;
				 int color = rand.nextInt(maxDomain);
				 //chocoModel[p].arithm((IntVar) chocoModel[p].getVar(id), "=", color).post();
				 vars[p][i] =  chocoModel[p].intVar("v"+id, rand.nextInt(maxDomain));
			}
			
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
