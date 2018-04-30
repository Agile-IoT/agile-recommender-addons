package at.tugraz.ist.algorithms;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MinMaxNormalization {

	
	public static double [] normalize(int [] arrayToNormalize, IntVar[] vars){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<vars.length;i++){
			double min = vars[i].getLB();
			double max = vars[i].getUB();
			normalized[i] = (double)((arrayToNormalize[i]-min)/(max-min));
		}
		
		return normalized;
	}
}
