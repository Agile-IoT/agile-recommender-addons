package utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class WriteToFile {
	
	static boolean flag = true;
	
	public static void writePastReqToFile (String outputFile, double []values, int numberOfVariables, int uid){

		List<String> lines = new ArrayList<String>();
		for (int d=0;d<numberOfVariables;d++){ // for ex: size is 14
					if(values[d]!=-999) {
						String s= d+","+values[d]+"\n";
						lines.add(s);
					}
		}
		
		writeToFile(outputFile,lines,uid);
		flag= false;
	}
	
	public static void writeDiagnosisToFile (String outputFile, Constraint []diag, IntVar[] intvars, int numberOfVariables, int uid){
		
		List<String> lines = new ArrayList<String>();
		String s= "";
		
		int []indexes = new int[diag.length];
		
		for(int i=0;i<diag.length;i++){
			String constraint = diag[i].toString();
			
			for (int j=0;j<intvars.length;j++){
				int diagIndex = j+numberOfVariables;
				String intvarStr= intvars[j].getName();
				if(constraint.contains(intvarStr)){
					s= diagIndex+","+1+"\n";
					lines.add(s);
				}
			}
			
//			String [] parsed = constraint.split("_");
//			String [] parsed2= parsed[1].split("=");
//			String [] parsed3= parsed2[1].split("]");
//			indexes[i] = Integer.valueOf(parsed2[0].replaceAll(" ", ""))-1;
//			int diagIndex = indexes[i]+numberOfVariables;
//			//s= diagIndex+","+parsed3[0].replaceAll(" ", "")+"\n";
//			s= diagIndex+","+1+"\n";
//			lines.add(s);
		}
		
		for (int d=0;d<numberOfVariables;d++){ 
			int diagIndex = d+numberOfVariables;
			boolean flag= false;
			
			for(int j=0;j<indexes.length;j++){
				if(d==indexes[j])
					flag=true;
			}
			if(!flag){
				s= diagIndex+","+0+"\n";
				lines.add(s);
			}
		}
		
		writeToFile(outputFile,lines,uid);
	}
	
	public static void writeToFile(String outputFile,List<String> lines, int uid){
		// WRITE DATASET
		File file = new File(outputFile);
		String line ;
		if (!file.exists() || flag) 
			try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		
					
		// OPEN FILE TO WRITE
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsolutePath(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		
					
		// ADD USER REQS TO THE FILE
		for(int j=0;j<lines.size();j++)			
			try { 
				bw.write(uid+","+lines.get(j)); // ADD USER ID: i
			} catch (IOException e) {
							e.printStackTrace();
			}
					
			// CLOSE FILE 
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
//	
//	 public static void writeALineToAFile(String line, String filename){
//		 
//			try {
//			
//				File file = new File(filename);
//
//				if (!file.exists()) {
//					file.createNewFile();
//				}
//
//				FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				
//				bw.write(line);
//				
//				bw.close();
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		 
//		
//	 }

	 
}
