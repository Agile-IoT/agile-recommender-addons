package at.tugraz.ist.configurator.FastDiag;


import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.constraints.Constraint;

import at.tugraz.ist.configurator.ChocoExtensions.CSP;
import at.tugraz.ist.configurator.ChocoExtensions.ChocoDuplications;
import at.tugraz.ist.configurator.ChocoExtensions.Constraint_Extension;
import at.tugraz.ist.configurator.ChocoExtensions.Constraints_Singleton;


public class FastDiag {
	
//	Algorithm 1 − FastDiag
	
	
//	1 func FastDiag(C ⊆ AC, AC = {c1..ct}) : ∆
//	2 if isEmpty(C) or inconsistent(AC − C) return null
//	3 else return F D(null, C, AC)
	
	
//	4 func FD(D, C = {c1..cq}, AC) : diagnosis ∆
//	5 if D != null and consistent(AC) return ∅;
//	6 if singleton(C) return C;
//	7 k = q/2;
//	8 C1 = {c1..ck}; C2 = {ck+1..cq};
//	9 D1 = F D(C1, C2, AC − C1);
//	10 D2 = F D(D1, C1, AC − D1);
//	11 return(D1 ∪ D2);

	private static CSP rootCSP;
	
	public static List<Constraint> computeDiagnose(CSP userModel, CSP productTable){
			
		rootCSP = productTable;
		
		if(userModel.constraints_user.isEmpty())
			return null;
		
		if(isConsistent(userModel))
			return null;
		
		List<Constraint> emptyList = new ArrayList<Constraint>();
		List<Constraint> diagnosis= fd(emptyList,userModel.constraints_user,userModel);
		//System.out.println("Diagnosis: "+ diagnosis);
		return diagnosis;
	}
	
	
//	4 func FD(D, C = {c1..cq}, AC) : diagnosis ∆
//	5 if D != ∅ and consistent(AC) return ∅;
//	6 if singleton(C) return C;
//	7 k = q/2;
//	8 C1 = {c1..ck}; C2 = {ck+1..cq};
//	9 D1 = F D(C1, C2, AC − C1);
//	10 D2 = F D(D1, C1, AC − D1);
	private static List<Constraint> fd(List<Constraint>D, List<Constraint> C, CSP AC){
		
		List<Constraint> finalDiagnosis = new ArrayList<Constraint>();
		
		if(!D.isEmpty() && isConsistent(AC))
			return new ArrayList<Constraint>();
		
		if(C.size()==1)
			return C;
			
		int k = C.size()/2;
		
		// System.out.println("C: "+ C);
		
		List<Constraint> C1 = new ArrayList<Constraint>();
		C1.addAll(C.subList(0, k));
		List<Constraint> C2 = new ArrayList<Constraint>();
		C2.addAll(C.subList(k, C.size()));

//		System.out.println("C1: "+ C1);
//		System.out.println("C2: "+ C2);
//		System.out.println("-------------------------------------");
//		System.out.println();
	
		CSP AC_C1 = subtractConstraints(AC,AC.constraints_user,C1); 
		//System.out.println("AC_C1: "+ AC_C1.constraints_user);
		
		//System.out.println("FD_D1, \nD:"+ C1 +"\nC:"+ C2 +"\nAC:"+ AC_C1.constraints_user );
	    List<Constraint> D1 = fd(C1, C2, AC_C1);  
	    //System.out.println("D1: "+ D1);
	    //System.out.println("-------------------------------------");
	    //System.out.println();
		
		
		CSP AC_D1 = subtractConstraints(AC,AC.constraints_user,D1); 
		//System.out.println("AC_D1: "+ AC_D1.constraints_user);
		
		//System.out.println("FD_D2, \nD:"+ D1 +"\nC:"+ C1 +"\nAC:"+ AC_D1.constraints_user );
		List<Constraint> D2 = fd(D1, C1, AC_D1); 
		//System.out.println("D2: "+ D2);
		
		//System.out.println("-------------------------------------");
		//System.out.println();
		
		// ADD D1
		boolean isFound=false;
		for(int s=0;s<D1.size();s++){
			isFound = false;
			for(int l=0;l<finalDiagnosis.size();l++){
				if(finalDiagnosis.get(l).getName().equals(D1.get(s).getName())){
					isFound = true;
					break;
				}
			}
			if(!isFound){
				finalDiagnosis.add(D1.get(s));
			}
				
		}
		
		// ADD D2
	    isFound=false;
		for(int s=0;s<D2.size();s++){
			isFound = false;
			for(int l=0;l<finalDiagnosis.size();l++){
				if(finalDiagnosis.get(l).getName().equals(D2.get(s).getName())){
					isFound = true;
					break;
				}
			}
			if(!isFound){
				finalDiagnosis.add(D2.get(s));
			}
				
		}
		
		
		return finalDiagnosis;
	} 
	
	public static boolean isConsistent(CSP userModel){
		boolean isSolvable = false;
		userModel.chocoModel.getSolver().reset();
		isSolvable = userModel.chocoModel.getSolver().solve();
		return isSolvable;
	}
	
	
	public static CSP subtractConstraints(CSP model,List<Constraint> L1, List<Constraint> L2){
		
		if(L1.size()==0 || L2.size()==0)
			return model;
			
		int [] varArray = new int[model.numberOfVariables];
		for (int a=0;a<varArray.length;a++)
			varArray[a] = -1;
		
		List<Constraint> L1_copy = new ArrayList<Constraint>();
		//L1_copy.addAll(L1);
		List<Integer> indexesToRemove = new ArrayList<Integer>();
		
		for(int i=0;i<L2.size();i++){
			for(int j=0;j<L1.size();j++){
				if(L1.get(j).getName().equals(L2.get(i).getName())){
					indexesToRemove.add(j);
				}
			}
		}
		for(int i=0;i<L1.size();i++){
			boolean isRemoved = false;
			for(int r=0;r<indexesToRemove.size();r++){
				if(indexesToRemove.get(r)==i)
					isRemoved = true;
			}
			if(!isRemoved)
				L1_copy.add(L1.get(i));
		}
		
		
		for (int k=0;k<L1_copy.size();k++){
				int constrID = Integer.valueOf(L1_copy.get(k).getName());
				Constraint_Extension c = Constraints_Singleton.getInstance().getConstraintList_extension__UserRequirements().get(constrID);
				int varID = c.getVar_1_ID();
				int value = c.getValue_1();
				varArray[varID-1] = value;
		}
		
		// CSP (boolean type, int[][]productTable, CSP originalCSP, int[] variables, int userID, int prodID)
		// create test CSP, type =1
		CSP subCSP = new CSP(1,null,model,varArray,-1,model.selectedProductID,null);
		return subCSP;
	}

} 
