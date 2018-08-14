package algorithms;

import org.chocosolver.solver.variables.IntVar;

public class MinMaxNormalization {

	
	public static double [] normalize(int [] arrayToNormalize, IntVar[] vars){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<arrayToNormalize.length;i++){
			double min = vars[i].getLB();
			double max = vars[i].getUB();
			normalized[i] = (double)((arrayToNormalize[i]-min)/(max-min));
			if(arrayToNormalize[i]==-1)
				normalized[i]=-1;
		}
		
		return normalized;
	}
	
	public static double [] normalizeTo0_1(int [] arrayToNormalize, double[][] minmax){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<arrayToNormalize.length;i++){
			double min = minmax[i][0];
			double max = minmax[i][1];
			normalized[i] = (double)((arrayToNormalize[i]-min)/(max-min));
			if(arrayToNormalize[i]==-999)
				normalized[i]=-999;
		}
		
		return normalized;
	}
	public static double [] normalizeTo1_5(double [] arrayToNormalize, double[] minmax){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<arrayToNormalize.length;i++){
			double min = minmax[0];
			double max = minmax[1];
			normalized[i] = ((double)((arrayToNormalize[i]-min)/(max-min)))*5;
			if(arrayToNormalize[i]==-1)
				normalized[i]=-1;
		}
		
		return normalized;
	}
}
