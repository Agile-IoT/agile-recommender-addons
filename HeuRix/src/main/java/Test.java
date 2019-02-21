import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Test {

	
	public static void main(String []args){
		File fout = new File("out.txt");
		FileOutputStream fos;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
				
		for (int i = 0; i < 150; i++) {
			// vertices
			int [] values = new int [100];
			
			for(int j=0;j<10;j++){
				// pick a vertex
				int vertex = (int)(Math.random()* 100);
				
				// pick a color
				int color = (int)(Math.random()* 10)+1;
				
				values[vertex]=color;
			}
			// userID
			String line = String.valueOf(i);
			// constraints, if 0 not selected, else selected
			for(int v=0;v<values.length;v++){
				line+=","+values[v];
			}	
			
			
			try {
				bw.write(line);
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	 
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


