package at.tugraz.ist.knowledgebases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

//https://www.itu.dk/research/cla/externals/clib/bike2.cp
public class PCKB implements KB{

	Model modelKB = new Model("PCConfigurationProblem");
	int numberOfVariables = 45;
	IntVar[] vars = new IntVar[numberOfVariables];
	//List<List<Integer>> varValues = new ArrayList<List<Integer>>();
	
	int [][] domains = new int [numberOfVariables][];
	
	
	public PCKB(){
		defineVariableValues();
    	defineVariables();
    	defineConstraints();
	}
	
	
	public void defineVariableValues() {
		int i=0;
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{0, 1, 2, 3, 4};
		domains[i++]=new int[]{200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400};
		domains[i++]=new int[]{200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{32, 64, 128, 256, 512, 1024, 2048, 4096};
		
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{512, 1024, 2048, 4096, 8192, 16384, 32768};
		
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{512, 1024, 2048, 4096, 8192, 16384, 32768};
		
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32, 64, 65, 67, 68, 100, 128, 256, 512, 1024, 2000, 2048, 3000
				, 4096, 5000, 6000, 7000, 8192, 9000, 10000, 16384};
		
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32, 64, 65, 67, 68, 100, 128, 256, 512, 1024, 2000, 2048, 3000
				, 4096, 5000, 6000, 7000, 8192, 9000, 10000, 16384};
		
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32, 64, 65, 67, 68, 100, 128, 256, 512, 1024, 2000, 2048, 3000
				, 4096, 5000, 6000, 7000, 8192, 9000, 10000, 16384};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32, 64, 65, 67, 68, 100, 128, 256, 512, 1024, 2000, 2048, 3000
				, 4096, 5000, 6000, 7000, 8192, 9000, 10000, 16384};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6};
		domains[i++]=new int[]{0, 1, 2, 3};
		domains[i++]=new int[]{200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400};
		domains[i++]=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 32, 64, 65, 67, 68, 100, 128, 256, 512, 1024, 2000, 2048, 3000
				, 4096, 5000, 6000, 7000, 8192, 9000, 10000, 16384};
		
		domains[i++]=new int[]{512, 1024};
		domains[i++]=new int[]{512, 1024};
		domains[i++]=new int[]{512, 1024};
		domains[i++]=new int[]{512, 1024};
		
	}
	
	public void defineVariables (){
		int i =0;
		vars[0] = this.modelKB.intVar("mb_idD", domains[i++]);
        vars[1] = this.modelKB.intVar("mb_cpuslotD", domains[i++]);
        vars[2] = this.modelKB.intVar("mb_controllertypeD", domains[i++]);
        vars[3] = this.modelKB.intVar("mb_SOME_slotD0", domains[i++]);
        vars[4] = this.modelKB.intVar("mb_SOME_slotD1", domains[i++]);
        vars[5] = this.modelKB.intVar("mb_SOME_slotD2", domains[i++]);
        vars[6] = this.modelKB.intVar("mb_SOME_slotD3", domains[i++]);
        vars[7] = this.modelKB.intVar("mb_SOME_slotD4", domains[i++]);
        vars[8] = this.modelKB.intVar("mb_SOME_slotD5", domains[i++]);
        vars[9] = this.modelKB.intVar("mb_SOME_slotD6", domains[i++]);
		vars[10] = this.modelKB.intVar("mb_SOME_slotD7", domains[i++]);
		vars[11] = this.modelKB.intVar("mb_controllercountD", domains[i++]);
		vars[12] = this.modelKB.intVar("mb_mincpufreqD", domains[i++]);
		vars[13] = this.modelKB.intVar("mb_maxcpufreqD", domains[i++]);
		vars[14] = this.modelKB.intVar("mb_ramslotD", domains[i++]);
		vars[15] = this.modelKB.intVar("mb_ramcapacityD",domains[i++]);

		vars[16] = this.modelKB.intVar("pro_idD", domains[i++]);
		vars[17] = this.modelKB.intVar("pro_cpuslotD",domains[i++]);
		vars[18] = this.modelKB.intVar("pro_freqD", domains[i++]);
		vars[19] = this.modelKB.intVar("hd1_idD", domains[i++]);
		vars[20] = this.modelKB.intVar("hd1_busD", domains[i++]);
		vars[21] = this.modelKB.intVar("hd1_capacityD", domains[i++]);

		vars[22] = this.modelKB.intVar("hd2_idD", domains[i++]);
		vars[23] = this.modelKB.intVar("hd2_busD",domains[i++]);
		vars[24] = this.modelKB.intVar("hd2_capacityD", domains[i++]);

		vars[25] = this.modelKB.intVar("ram1_idD", domains[i++]);
		vars[26] = this.modelKB.intVar("ram1_slotD", domains[i++]);
		vars[27] = this.modelKB.intVar("ram1_capacityD", domains[i++]);

		vars[28] = this.modelKB.intVar("ram2_idD", domains[i++]);
		vars[29] = this.modelKB.intVar("ram2_slotD", domains[i++]);
		vars[30] = this.modelKB.intVar("ram2_capacityD", domains[i++]);

		vars[31] = this.modelKB.intVar("ram3_idD",domains[i++]);
		vars[32] = this.modelKB.intVar("ram3_slotD",domains[i++]);
		vars[33] = this.modelKB.intVar("ram3_capacityD", domains[i++]);
		vars[34] = this.modelKB.intVar("ram4_idD", domains[i++]);
		vars[35] = this.modelKB.intVar("ram4_slotD", domains[i++]);
		vars[36] = this.modelKB.intVar("ram4_capacityD", domains[i++]);
		vars[37] = this.modelKB.intVar("graphics_idD",domains[i++]);
		vars[38] = this.modelKB.intVar("graphics_slotD", domains[i++]);
		vars[39] = this.modelKB.intVar("pc_clockD", domains[i++]);
		vars[40] = this.modelKB.intVar("pc_memoryD", domains[i++]);

		vars[41] = this.modelKB.intVar("ram1_presentD",domains[i++]);
		vars[42] = this.modelKB.intVar("ram2_presentD", domains[i++]);
		vars[43] = this.modelKB.intVar("ram3_presentD", domains[i++]);
		vars[44] = this.modelKB.intVar("ram4_presentD", domains[i++]);    
	}
	
	
	
	public void defineConstraints() {
		List<Constraint> kbReqList = new ArrayList<Constraint>();
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 6));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 400));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1600));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 512));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 3));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 5));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 400));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1600));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 512));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 3));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 8));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 200));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1000));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 8));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 200));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1000));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));

		kbReqList.add(this.modelKB.arithm(vars[0],"=", 10));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 200));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 800));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 10));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 200));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 800));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 400));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 2000));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 3));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 9));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 400));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1000));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 512));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 400));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1600));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 3));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 7));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 400));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 1400));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 1800));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 2200));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 1800));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 2200));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		kbReqList.add(this.modelKB.arithm(vars[0],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[1],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[2],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[11],"=", 4));
		kbReqList.add(this.modelKB.arithm(vars[12],"=", 1800));
		kbReqList.add(this.modelKB.arithm(vars[13],"=", 2400));
		kbReqList.add(this.modelKB.arithm(vars[14],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[15],"=", 256));
		kbReqList.add(this.modelKB.arithm(vars[3],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[4],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[5],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[6],"=", 1));
		kbReqList.add(this.modelKB.arithm(vars[7],"=", 2));
		kbReqList.add(this.modelKB.arithm(vars[8],"=", 3));
		kbReqList.add(this.modelKB.arithm(vars[9],"=", 0));
		kbReqList.add(this.modelKB.arithm(vars[10],"=", 0));
		
		List<Constraint> configurationList = new ArrayList<>();
		for(int counter = 0; counter <= kbReqList.size()-16; counter = counter +16) {
			configurationList.add(this.modelKB.and(kbReqList.get(0 + counter), kbReqList.get(1 + counter), kbReqList.get(2 + counter), 
					kbReqList.get(3 + counter), kbReqList.get(4 + counter), kbReqList.get(5 + counter), kbReqList.get(6 + counter), kbReqList.get(7 + counter), 
					kbReqList.get(8 + counter), kbReqList.get(9 + counter), kbReqList.get(10 + counter), kbReqList.get(11 + counter), kbReqList.get(12 + counter),
					kbReqList.get(13 + counter), kbReqList.get(14 + counter), kbReqList.get(15 + counter)));
		}
		
		Constraint result = this.modelKB.or(configurationList.get(0), configurationList.get(1), configurationList.get(2), configurationList.get(3), configurationList.get(4),
				configurationList.get(5), configurationList.get(6), configurationList.get(7), configurationList.get(8), configurationList.get(9), configurationList.get(10),
				configurationList.get(11), configurationList.get(12));
		
		this.modelKB.post(result);
		
		
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 6), this.modelKB.arithm(vars[17],"=", 1), this.modelKB.arithm(vars[18],"=", 600)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 0), this.modelKB.arithm(vars[17],"=", 3), this.modelKB.arithm(vars[18],"=", 2400)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 1), this.modelKB.arithm(vars[17],"=", 3), this.modelKB.arithm(vars[18],"=", 1800)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 7), this.modelKB.arithm(vars[17],"=", 1), this.modelKB.arithm(vars[18],"=", 200)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 3), this.modelKB.arithm(vars[17],"=", 0), this.modelKB.arithm(vars[18],"=", 1200)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 4), this.modelKB.arithm(vars[17],"=", 0), this.modelKB.arithm(vars[18],"=", 1000)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 2), this.modelKB.arithm(vars[17],"=", 0), this.modelKB.arithm(vars[18],"=", 2000)),
				this.modelKB.and(this.modelKB.arithm(vars[16],"=", 5), this.modelKB.arithm(vars[17],"=", 0), this.modelKB.arithm(vars[18],"=", 800))
				));
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 0), this.modelKB.arithm(vars[20],"=", 0), this.modelKB.arithm(vars[21],"=", 512)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 0), this.modelKB.arithm(vars[20],"=", 1), this.modelKB.arithm(vars[21],"=", 512)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 5), this.modelKB.arithm(vars[20],"=", 0), this.modelKB.arithm(vars[21],"=", 2048)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 2), this.modelKB.arithm(vars[20],"=", 0), this.modelKB.arithm(vars[21],"=", 16384)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 3), this.modelKB.arithm(vars[20],"=", 0), this.modelKB.arithm(vars[21],"=", 8192)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 1), this.modelKB.arithm(vars[20],"=", 1), this.modelKB.arithm(vars[21],"=", 32768)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 6), this.modelKB.arithm(vars[20],"=", 1), this.modelKB.arithm(vars[21],"=", 1024)),
				this.modelKB.and(this.modelKB.arithm(vars[19],"=", 4), this.modelKB.arithm(vars[20],"=", 0), this.modelKB.arithm(vars[21],"=", 4096))
				));
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 0), this.modelKB.arithm(vars[23],"=", 0), this.modelKB.arithm(vars[24],"=", 512)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 0), this.modelKB.arithm(vars[23],"=", 1), this.modelKB.arithm(vars[24],"=", 512)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 5), this.modelKB.arithm(vars[23],"=", 0), this.modelKB.arithm(vars[24],"=", 2048)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 2), this.modelKB.arithm(vars[23],"=", 0), this.modelKB.arithm(vars[24],"=", 16384)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 3), this.modelKB.arithm(vars[23],"=", 0), this.modelKB.arithm(vars[24],"=", 8192)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 1), this.modelKB.arithm(vars[23],"=", 1), this.modelKB.arithm(vars[24],"=", 32768)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 6), this.modelKB.arithm(vars[23],"=", 1), this.modelKB.arithm(vars[24],"=", 1024)),
				this.modelKB.and(this.modelKB.arithm(vars[22],"=", 4), this.modelKB.arithm(vars[23],"=", 0), this.modelKB.arithm(vars[24],"=", 4096))
				));
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 0), this.modelKB.arithm(vars[26],"=", 0), this.modelKB.arithm(vars[27],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 0), this.modelKB.arithm(vars[26],"=", 2), this.modelKB.arithm(vars[27],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 6), this.modelKB.arithm(vars[26],"=", 0), this.modelKB.arithm(vars[27],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 2), this.modelKB.arithm(vars[26],"=", 2), this.modelKB.arithm(vars[27],"=", 4)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 5), this.modelKB.arithm(vars[26],"=", 2), this.modelKB.arithm(vars[27],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 4), this.modelKB.arithm(vars[26],"=", 2), this.modelKB.arithm(vars[27],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 3), this.modelKB.arithm(vars[26],"=", 2), this.modelKB.arithm(vars[27],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[25],"=", 1), this.modelKB.arithm(vars[26],"=", 2), this.modelKB.arithm(vars[27],"=", 8))
				));
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 0), this.modelKB.arithm(vars[29],"=", 0), this.modelKB.arithm(vars[30],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 0), this.modelKB.arithm(vars[29],"=", 2), this.modelKB.arithm(vars[30],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 6), this.modelKB.arithm(vars[29],"=", 0), this.modelKB.arithm(vars[30],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 2), this.modelKB.arithm(vars[29],"=", 2), this.modelKB.arithm(vars[30],"=", 4)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 5), this.modelKB.arithm(vars[29],"=", 2), this.modelKB.arithm(vars[30],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 4), this.modelKB.arithm(vars[29],"=", 2), this.modelKB.arithm(vars[30],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 3), this.modelKB.arithm(vars[29],"=", 2), this.modelKB.arithm(vars[30],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[28],"=", 1), this.modelKB.arithm(vars[29],"=", 2), this.modelKB.arithm(vars[30],"=", 8))
				));
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 0), this.modelKB.arithm(vars[32],"=", 0), this.modelKB.arithm(vars[33],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 0), this.modelKB.arithm(vars[32],"=", 2), this.modelKB.arithm(vars[33],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 6), this.modelKB.arithm(vars[32],"=", 0), this.modelKB.arithm(vars[33],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 2), this.modelKB.arithm(vars[32],"=", 2), this.modelKB.arithm(vars[33],"=", 4)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 5), this.modelKB.arithm(vars[32],"=", 2), this.modelKB.arithm(vars[33],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 4), this.modelKB.arithm(vars[32],"=", 2), this.modelKB.arithm(vars[33],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 3), this.modelKB.arithm(vars[32],"=", 2), this.modelKB.arithm(vars[33],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[31],"=", 1), this.modelKB.arithm(vars[32],"=", 2), this.modelKB.arithm(vars[33],"=", 8))
				));
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 0), this.modelKB.arithm(vars[35],"=", 0), this.modelKB.arithm(vars[36],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 0), this.modelKB.arithm(vars[35],"=", 2), this.modelKB.arithm(vars[36],"=", 0)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 6), this.modelKB.arithm(vars[35],"=", 0), this.modelKB.arithm(vars[36],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 2), this.modelKB.arithm(vars[35],"=", 2), this.modelKB.arithm(vars[36],"=", 4)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 5), this.modelKB.arithm(vars[35],"=", 2), this.modelKB.arithm(vars[36],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 4), this.modelKB.arithm(vars[35],"=", 2), this.modelKB.arithm(vars[36],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 3), this.modelKB.arithm(vars[35],"=", 2), this.modelKB.arithm(vars[36],"=", 2)),
				this.modelKB.and(this.modelKB.arithm(vars[34],"=", 1), this.modelKB.arithm(vars[35],"=", 2), this.modelKB.arithm(vars[36],"=", 8))
				));
		
		this.modelKB.post(this.modelKB.or(
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 0), this.modelKB.arithm(vars[38],"=", 3)),
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 3), this.modelKB.arithm(vars[38],"=", 3)),
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 6), this.modelKB.arithm(vars[38],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 2), this.modelKB.arithm(vars[38],"=", 3)),
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 5), this.modelKB.arithm(vars[38],"=", 1)),
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 4), this.modelKB.arithm(vars[38],"=", 3)),
				this.modelKB.and(this.modelKB.arithm(vars[37],"=", 1), this.modelKB.arithm(vars[38],"=", 3))
				));
		
		this.modelKB.post(this.modelKB.and(this.modelKB.arithm(vars[1],"=", vars[17]), this.modelKB.arithm(vars[18],">=", vars[12]), this.modelKB.arithm(vars[18],"<=", vars[13])));
		this.modelKB.post(this.modelKB.arithm(vars[2],"=", vars[20]));
		this.modelKB.post(this.modelKB.arithm(vars[2],"=", vars[23]));
		
		this.modelKB.post(this.modelKB.arithm(vars[14],"=", vars[26]));
		this.modelKB.post(this.modelKB.arithm(vars[14],"=", vars[29]));
		this.modelKB.post(this.modelKB.arithm(vars[14],"=", vars[32]));
		this.modelKB.post(this.modelKB.arithm(vars[14],"=", vars[35]));
		
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[41],"=", 512), this.modelKB.arithm(vars[25],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[41],"=", 1024), this.modelKB.arithm(vars[25],"<", 1)));
		
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[42],"=", 512), this.modelKB.arithm(vars[28],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[42],"=", 1024), this.modelKB.arithm(vars[28],"<", 1)));
		
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[43],"=", 512), this.modelKB.arithm(vars[31],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[43],"=", 1024), this.modelKB.arithm(vars[31],"<", 1)));
		
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[44],"=", 512), this.modelKB.arithm(vars[34],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[44],"=", 1024), this.modelKB.arithm(vars[34],"<", 1)));
		
		IntVar [] varsToBeAdded = new IntVar[4];
		IntVar sum = this.modelKB.intVar("sum", 0);   
		varsToBeAdded[0] = vars[41];
		varsToBeAdded[1] = vars[42];
		varsToBeAdded[2] = vars[43];
		varsToBeAdded[3] = vars[44];
		this.modelKB.sum(varsToBeAdded ,"=",sum);
		this.modelKB.post(this.modelKB.arithm(vars[15], ">=", sum));
		
		this.modelKB.post(this.modelKB.or(this.modelKB.not(this.modelKB.arithm(vars[28],">", 0)), this.modelKB.arithm(vars[25],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.not(this.modelKB.arithm(vars[31],">", 0)), this.modelKB.arithm(vars[25],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.not(this.modelKB.arithm(vars[31],">", 0)), this.modelKB.arithm(vars[28],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.not(this.modelKB.arithm(vars[34],">", 0)), this.modelKB.arithm(vars[25],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.not(this.modelKB.arithm(vars[34],">", 0)), this.modelKB.arithm(vars[28],">", 0)));
		this.modelKB.post(this.modelKB.or(this.modelKB.not(this.modelKB.arithm(vars[34],">", 0)), this.modelKB.arithm(vars[31],">", 0)));
		
		this.modelKB.post(this.modelKB.or(this.modelKB.arithm(vars[3],"=", vars[38]), this.modelKB.arithm(vars[4],"=", vars[38]), 
				this.modelKB.arithm(vars[5],"=", vars[38]), this.modelKB.arithm(vars[6],"=", vars[38]), 
				this.modelKB.arithm(vars[7],"=", vars[38]), this.modelKB.arithm(vars[8],"=", vars[38]), 
				this.modelKB.arithm(vars[9],"=", vars[38]), this.modelKB.arithm(vars[10],"=", vars[38])));
		
		this.modelKB.post(this.modelKB.arithm(vars[19], ">", 0));
		this.modelKB.post(this.modelKB.arithm(vars[25], ">", 0));
		this.modelKB.post(this.modelKB.arithm(vars[39], ">", vars[18]));
		
		IntVar [] varsToBeAdded2 = new IntVar[4];
		IntVar sum2 = this.modelKB.intVar("sum2", 0);   
		varsToBeAdded2[0] = vars[27];
		varsToBeAdded2[1] = vars[30];
		varsToBeAdded2[2] = vars[33];
		varsToBeAdded2[3] = vars[36];
		this.modelKB.sum(varsToBeAdded2 ,"=",sum2);
		this.modelKB.post(this.modelKB.arithm(vars[40], ">=", sum2));
		
		//Constraint[] cKB = this.modelKB.getCstrs(); 
		//return cKB;
	}

	@Override
	public Model getModelKB() {
		// TODO Auto-generated method stub
		return modelKB;
	}

	@Override
	public void setModelKB(Model m) {
		// TODO Auto-generated method stub
		modelKB = m;
	}

	@Override
	public int getNumberOfVariables() {
		// TODO Auto-generated method stub
		return numberOfVariables;
	}

	@Override
	public void setNumberOfVariables(int n) {
		// TODO Auto-generated method stub
		numberOfVariables = n;
	}

	@Override
	public IntVar[] getVars() {
		// TODO Auto-generated method stub
		return vars;
	}

	@Override
	public void setVars(IntVar[] v) {
		// TODO Auto-generated method stub
		vars=v;
	}

	public static List<Constraint> arrayToList(Constraint[] cstrs)
	 {
		 List<Constraint> l = new ArrayList<Constraint>(); 
		 for (int i=0; i<cstrs.length; i++)
			 l.add(cstrs[i]);
		 return l;
	 }


	@Override
	public int[][] getDomains() {
		// TODO Auto-generated method stub
		return domains;
	}

}
