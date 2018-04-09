package at.tugraz.ist.fileOperations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {
	
	
	 public static void writeALineToAFile(String line, String filename){
		 
			try {
			
				File file = new File(filename);

				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(line);
				
				bw.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		
	 }

	 
}
