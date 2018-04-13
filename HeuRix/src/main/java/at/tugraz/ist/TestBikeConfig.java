package at.tugraz.ist;

import java.io.File;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class TestBikeConfig {

	
	public static void main(String[] args) {
		CP bike = new CP();
		bike.generateSampleSolutions(1,null,false);
	}	
}
