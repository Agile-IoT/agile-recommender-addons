package at.tugraz.ist.configurator.csh.fileoperations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.variables.IntVar;

public class Writers {
	

	 public void writeToFile(IntVar[][] vars, String modelsName){
		 
		    List<String> lines = new ArrayList<String>();
		    String str = "";
		    int val = -1000000;
		    int size = -1;
		    
		    for (int i=0;i<vars.length;i++){
		    	str = "";
		   		for(int j=0;j<vars[0].length;j++){
		   			size = vars[i][j].getDomainSize();
		   			if(size==1)
		   				val = vars[i][j].getValue();
		   			else
		   				val = -1000000;
		   			str += val+",";
		   		}
		   		str += i+"\n";
		   		lines.add(str);
		    }
			try {
				String basePath = new File("").getAbsolutePath();
				File file = new File("Files\\inputs\\"+modelsName+".data");

				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsolutePath());
				BufferedWriter bw = new BufferedWriter(fw);
				
				for (int i=0;i<lines.size();i++){
					 bw.append(lines.get(i));
				 }
				bw.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		 
		
	 }



}
