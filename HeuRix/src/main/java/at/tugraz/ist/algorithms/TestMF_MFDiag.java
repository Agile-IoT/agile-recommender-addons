package at.tugraz.ist.algorithms;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import java.text.DecimalFormat;

public class TestMF_MFDiag {
	
	
	public static void main (String [] args){

	    String solutionsFile = "Files/ConfigSolutions/TestMatrix2";
		
	    DataModel dataModel;
	    DecimalFormat df2 = new DecimalFormat("0.#");
		
		
		try {
			File file_soln = new File(solutionsFile);
			dataModel = new FileDataModel(file_soln);
			MatrixFactorization.SVD(dataModel,50,1000,5,120);
			double [][] p=MatrixFactorization.UF;
			double [][] q=MatrixFactorization.IF;
			
			System.out.println("P"); 
			for(int i=0;i<p.length;i++){
				System.out.print("\n");
				for(int j=0;j<p[0].length;j++)
					System.out.print(df2.format(p[i][j])+"&");
			}
			
			System.out.println("Q"); 
			for(int i=0;i<q.length;i++){
				System.out.print("\n");
				for(int j=0;j<q[0].length;j++)
					System.out.print(df2.format(q[i][j])+"&");
			}
			
//			double [][]UFlisa = {{0.1,0.3,0.3,-0.4,-0.1,0.5}};
//			
//			System.out.println("MF_Lisa"); 
//			double [][] denseLisa = MatrixFactorization.multiplyByMatrix(UFlisa, q);
//			
//			for(int i=0;i<denseLisa.length;i++){
//				System.out.print("\n");
//				for(int j=0;j<denseLisa[0].length;j++)
//					System.out.print(df2.format(denseLisa[i][j])+"&");
//			}
//			
			
			System.out.println("Dense"); 
			double [][] denseMatrix;
			denseMatrix = MatrixFactorization.multiplyByMatrix(p, q);
			for(int i=0;i<denseMatrix.length;i++){
				System.out.print("\n");
				for(int j=0;j<denseMatrix[0].length;j++)
					System.out.print(df2.format(denseMatrix[i][j])+"&");
			}
			
			double [] lisa = {0,0,1,0,0,-1,-1,-1,-1,-1,0,1};
			double [][] normalized_denseMatrix = new double[denseMatrix.length][];
			double [][]minmax = {{-0.3,1.9},{-0.2,1.9},{-0.3,1.9},{-0.4,2.7},{-0.3,2.2}};
			
			System.out.println("Distances"); 
			for(int d=0;d<denseMatrix.length;d++){
				//normalized_denseMatrix[d] = MinMaxNormalization.normalizeTo01(denseMatrix[d],minmax[d]);
				double dist =MatrixFactorization.euclidean_distance(lisa,denseMatrix[d]);
				System.out.println(dist); 
			}
			
			System.out.println("Norm Distances"); 
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
