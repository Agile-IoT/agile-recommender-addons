package at.tugraz.ist.algorithms;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import java.text.DecimalFormat;

public class TestMF {
	
	
	public static void main (String [] args){

	    String solutionsFile = "Files/ConfigSolutions/TestMatrix";
		
	    DataModel dataModel;
	    DecimalFormat df2 = new DecimalFormat("0.#");
		
		
		try {
			File file_soln = new File(solutionsFile);
			dataModel = new FileDataModel(file_soln);
			MatrixFactorization.SVD(dataModel,100,1000,1,6);
			double [][] p=MatrixFactorization.UF;
			double [][] q=MatrixFactorization.IF;
			
			double [][] denseMatrix;
			denseMatrix = MatrixFactorization.multiplyByMatrix(p, q);
			for(int i=0;i<denseMatrix.length;i++){
				System.out.print("\n");
				for(int j=0;j<denseMatrix[0].length;j++)
					System.out.print(df2.format(denseMatrix[i][j])+"&");
			}
			
			double [] lisa = {0,0,1,0,0,0,0,1};
			double [][] normalized_denseMatrix = new double[denseMatrix.length][];
			double [][]minmax = {{-0.3,1.9},{-0.2,1.9},{-0.3,1.9},{-0.4,2.7},{-0.3,2.2}};
			
			for(int d=0;d<denseMatrix.length;d++){
				normalized_denseMatrix[d] = MinMaxNormalization.normalizeTo01(denseMatrix[d],minmax[d]);
				double dist =MatrixFactorization.euclidean_distance(lisa,normalized_denseMatrix[d]);
				System.out.println(dist); 
			}
			
			double [][] similars = new double [][]{normalized_denseMatrix[1],normalized_denseMatrix[3],normalized_denseMatrix[4]};
			double [] aggr = KNN.aggregateAvgDouble(similars);
			
			System.out.println("Similars"); 
			for(int i=0;i<similars.length;i++){
				System.out.print("\n");
				for(int j=0;j<similars[0].length;j++)
					System.out.print(df2.format(similars[i][j])+"&");
			}
			
			System.out.println("aggr"); 
			for(int j=0;j<aggr.length;j++)
					System.out.print(df2.format(aggr[j])+"&");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // outputFolder+"/Problem_"+i
		
		
	} 
	    
}
