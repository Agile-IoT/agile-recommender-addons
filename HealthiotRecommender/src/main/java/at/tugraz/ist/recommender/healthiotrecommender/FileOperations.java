package at.tugraz.ist.recommender.healthiotrecommender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;

public class FileOperations {
	
    public static void appendNewLineToFile(String fileName, String line) 
	{
		 try {
			    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
			     writer.append(line);
			     writer.append('\n');
			     writer.close();
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void cleanFile(String fileName){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		     writer.write("");
		     writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public static void removeLastLine (String fileName){
		  try{
	             RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
	             long length = raf.length();
	             System.out.println("File Length="+raf.length());
	             raf.setLength(length - 9);
	             System.out.println("File Length="+raf.length());
	             raf.close();
	       }catch(Exception ex){
	             ex.printStackTrace();
	       }
	}
	

}
