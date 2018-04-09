package at.tugraz.ist.configurator.fileOperations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {
	
	 static FileWriter writer = null;
	 
	 public static FileWriter writeFile(String fileDir, List<String> line,boolean first, boolean last,boolean append){
		 try {
			 File file = new File(fileDir);
				// if file doesnt exists, then create it
			 if (!file.exists()) {
					file.createNewFile();
					
			 }
			 // append mode
			 if(writer==null || first)
				 writer = new FileWriter(file.getPath(),append);
			 
			 CSVUtils.writeLine(writer,line);

			 if(last){
				 writer.flush();
				 writer.close();
			 }
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	 }
}
