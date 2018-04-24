package at.tugraz.ist.algorithms;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MinMaxNormalization {

	
	public static int [] normalize(int [] arrayToNormalize, IntVar[] vars){
		
		int [] normalized = new int[arrayToNormalize.length];
		
		for(int i=0;i<vars.length;i++){
			int min = vars[i].getLB();
			int max = vars[i].getUB();
			normalized[i] = (arrayToNormalize[i]-min)/(max-min);
		}
		
		return normalized;
	}
}
