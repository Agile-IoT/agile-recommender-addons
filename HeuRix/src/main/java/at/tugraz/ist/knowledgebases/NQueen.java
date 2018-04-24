package at.tugraz.ist.knowledgebases;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class NQueen {

    public void modelAndSolve(){
        int n = 8;
        Model model = new Model(n + "-queens problem");
        IntVar[] vars = new IntVar[n];
        
        for(int q = 0; q < n; q++){
            vars[q] = model.intVar("Q_"+q, 1, n);
        }
        
        model.arithm(vars[0], "=",1).post();
        //model.arithm(vars[1], "=",5).post();
        
        
        for(int i  = 0; i < n-1; i++){
            for(int j = i + 1; j < n; j++){
                model.arithm(vars[i], "!=",vars[j]).post();
                model.arithm(vars[i], "!=", vars[j], "-", j - i).post();
                model.arithm(vars[i], "!=", vars[j], "+", j - i).post();
            }
        }
        long start = System.nanoTime();
        Solution solution = model.getSolver().findSolution();
        long end = System.nanoTime();
        if(solution != null){
        	System.out.println(end-start);
            System.out.println(solution.toString());
        }
    }

    public static void main(String[] args) {
        new NQueen().modelAndSolve();
    }

}