package at.tugraz.ist;

import java.io.File;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class TestBikeConfig {

	
	public static void main(String[] args) {
		BikeConfig bike = new BikeConfig();
		bike.generateSampleSolutions(1,null);
	}	
}
