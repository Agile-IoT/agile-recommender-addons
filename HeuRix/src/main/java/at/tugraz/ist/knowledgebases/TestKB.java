package at.tugraz.ist.knowledgebases;

import org.springframework.beans.factory.annotation.Autowired;

public class TestKB {
	

//	@Autowired
//	public static void main (String [] args){
//		Bike2KB bikes = new Bike2KB();
//		int n=0;
//		while(bikes.modelKB.getSolver().solve()){
//			n++;
//			System.out.println("Solution #"+n);
//			for(int i=0;i<10;i++)
//				System.out.println(bikes.modelKB.getVar(i));
//			//System.out.println(bikes.modelKB.getVar(0));
//		}
//		
//	}
	

	@Autowired
	public static void main (String [] args){
		CameraKB cameras = new CameraKB();
		int n=0;
		// recomTasks[j][i].kb.getModelKB().arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
		
		//cameras.getModelKB().arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
		//.arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
		while(cameras.modelKB.getSolver().solve()){
			n++;
			System.out.println("Solution #"+n);
			for(int i=0;i<10;i++)
				System.out.println(cameras.modelKB.getVar(i));
			//System.out.println(bikes.modelKB.getVar(0));
		}
		
	}

}
