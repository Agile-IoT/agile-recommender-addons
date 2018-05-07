package at.tugraz.ist.knowledgebases;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Solution;
import org.springframework.beans.factory.annotation.Autowired;

public class TestKB {


//	@Autowired
//	public static void main (String [] args){
//		CameraKB cameras = new CameraKB();
//		int n=0;
//		// recomTasks[j][i].kb.getModelKB().arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
//		
//		//cameras.getModelKB().arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
//		//.arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
//		while(cameras.modelKB.getSolver().solve()){
//			n++;
//			//System.out.println("Solution #"+n);
//			//for(int i=0;i<10;i++)
//				//System.out.println(cameras.modelKB.getVar(i));
//			//System.out.println(bikes.modelKB.getVar(0));
//		}
//		System.out.println("Solutions: "+n);
//		
//	}
	
	@Autowired
	public static void main (String [] args){
		Bike2KB kb = new Bike2KB();
		
//		double multip=1;
//		for (int i=0;i<kb.domains.length;i++){
//			multip *= kb.domains[i].length;
//			System.out.println("multip*"+kb.domains[i].length+" :"+multip);
//		}
//		
		int n=0;
		// recomTasks[j][i].kb.getModelKB().arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
		
		//cameras.getModelKB().arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
		//.arithm(recomTasks[j][i].vars[t],"=",reqs[i][t]).post();
		List<Solution> solutions = new ArrayList<>();
		
	     while (kb.modelKB.getSolver().solve()){
	          solutions.add(new Solution(kb.modelKB).record());
	     }


//		while(kb.modelKB.getSolver().solve()){
//			n++;
//			//System.out.println("Solution #"+n);
//			//for(int i=0;i<10;i++)
//				//System.out.println(cameras.modelKB.getVar(i));
//			//System.out.println(bikes.modelKB.getVar(0));
//		}
		System.out.println("Solutions: "+solutions.size());
		
	}

}
