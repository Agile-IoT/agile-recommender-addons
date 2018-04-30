package at.tugraz.ist.knowledgebases;

import org.springframework.beans.factory.annotation.Autowired;

public class TestKB {
	

	@Autowired
	public static void main (String [] args){
		Bike2KB bikes = new Bike2KB();
		int n=0;
		while(bikes.modelKB.getSolver().solve()){
			n++;
			System.out.println("Solution #"+n);
			for(int i=0;i<10;i++)
				System.out.println(bikes.modelKB.getVar(i));
			//System.out.println(bikes.modelKB.getVar(0));
		}
		
	}

}
