package at.tugraz.ist.knowledgebases;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

public class CameraKB implements KB{
	
	Model modelKB = new Model("CameraConfigurationProblem");
	int numberOfVariables = 10;
	IntVar[] vars = new IntVar[numberOfVariables];
	int [][] domains = new int [numberOfVariables][];
	
	public CameraKB(){
		// 10 variables
    	defineVariables();
    	// 31 constraints
    	defineConstraints();
	}
	
	public void defineVariables (){
		
		domains[0] = new int[]{61, 102, 123, 142, 162, 241, 242, 208, 209, 243, 363};
		domains[1] = new int[]{18, 25, 27, 30, 32};
		domains[2] = new int[]{0, 1};
		domains[3] = new int[]{0, 1};
		domains[4] = new int[]{0, 1};
		domains[5] = new int[]{0, 1};
		domains[6] = new int[]{0, 1, 2, 3, 4, 5};
		domains[7] = new int[]{20, 30, 35, 50, 58, 78};
		domains[8] = new int[]{445, 455, 460, 470, 475, 505, 530, 535, 560, 675, 700, 765, 840, 850, 860, 980, 1405};
		domains[9] = new int[]{189, 399, 400, 469, 479, 499, 579, 581, 609, 659, 669, 749, 1129, 1649, 2149, 2329, 2749, 3229, 5219};
    
        vars[0] = this.modelKB.intVar("Resolution", domains[0]);
        vars[1] = this.modelKB.intVar("Display", domains[1] );
        vars[2] = this.modelKB.intVar("Touchscreen",  domains[2]);
        vars[3] = this.modelKB.intVar("WiFi", domains[3] );
        vars[4] = this.modelKB.intVar("NFC",  domains[4]);
        vars[5] = this.modelKB.intVar("GPS",  domains[5]);
        vars[6] = this.modelKB.intVar("Video", domains[6] );
        vars[7] = this.modelKB.intVar("Zoom",  domains[7]);
        vars[8] = this.modelKB.intVar("Weight", domains[8] );
        vars[9] = this.modelKB.intVar("Price",  domains[9]);
       
	}
	public void defineConstraints() {

 //		208	32	1	1	0	1	0	30	1405	5219

		Constraint c1 = this.modelKB.arithm(vars[0],"=",208);
		Constraint c2 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c3 = this.modelKB.arithm(vars[2],"=",1); 
		Constraint c4 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c5 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c6 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c7 = this.modelKB.arithm(vars[6],"=",0); 
		Constraint c8 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c9 = this.modelKB.arithm(vars[8],"=",1405); 
		Constraint c10 = this.modelKB.arithm(vars[9],"=",5219); 
	
		Constraint c_agg1 = this.modelKB.and(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10);
		
//		61	25	0	1	0	0	4	30	475	659
		Constraint c11 = this.modelKB.arithm(vars[0],"=",61);
		Constraint c12 = this.modelKB.arithm(vars[1],"=",25); 
		Constraint c13 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c14 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c15 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c16 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c17 = this.modelKB.arithm(vars[6],"=",4); 
		Constraint c18 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c19 = this.modelKB.arithm(vars[8],"=",475); 
		Constraint c110 = this.modelKB.arithm(vars[9],"=",659); 
	
		Constraint c_agg11 = this.modelKB.and(c11,c12,c13,c14,c15,c16,c17,c18,c19,c110);
		
//		61	18	0	0	0	0	4	20	700	189
		Constraint c21 = this.modelKB.arithm(vars[0],"=",61);
		Constraint c22 = this.modelKB.arithm(vars[1],"=",18); 
		Constraint c23 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c24 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c25 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c26 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c27 = this.modelKB.arithm(vars[6],"=",4); 
		Constraint c28 = this.modelKB.arithm(vars[7],"=",20); 
		Constraint c29 = this.modelKB.arithm(vars[8],"=",700); 
		Constraint c210 = this.modelKB.arithm(vars[9],"=",189); 
	
		Constraint c_agg21 = this.modelKB.and(c21,c22,c23,c24,c25,c26,c27,c28,c29,c210);
		
//		209	32	1	1	1	0	0	58	860	2329
		Constraint c31 = this.modelKB.arithm(vars[0],"=",209);
		Constraint c32 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c33 = this.modelKB.arithm(vars[2],"=",1); 
		Constraint c34 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c35 = this.modelKB.arithm(vars[4],"=",1); 
		Constraint c36 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c37 = this.modelKB.arithm(vars[6],"=",0); 
		Constraint c38 = this.modelKB.arithm(vars[7],"=",58); 
		Constraint c39 = this.modelKB.arithm(vars[8],"=",860); 
		Constraint c310 = this.modelKB.arithm(vars[9],"=",2329); 
	
		Constraint c_agg31 = this.modelKB.and(c31,c32,c33,c34,c35,c36,c37,c38,c39,c310);
		
//		243	32	0	0	0	0	2	35	850	1649
		Constraint c41 = this.modelKB.arithm(vars[0],"=",243);
		Constraint c42 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c43 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c44 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c45 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c46 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c47 = this.modelKB.arithm(vars[6],"=",2); 
		Constraint c48 = this.modelKB.arithm(vars[7],"=",35); 
		Constraint c49 = this.modelKB.arithm(vars[8],"=",850); 
		Constraint c410 = this.modelKB.arithm(vars[9],"=",1649); 
	
		Constraint c_agg41 = this.modelKB.and(c41,c42,c43,c44,c45,c46,c47,c48,c49,c410);
//		243	32	0	1	0	0	3	35	840	2149
		Constraint c51 = this.modelKB.arithm(vars[0],"=",243);
		Constraint c52 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c53 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c54 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c55 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c56 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c57 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c58 = this.modelKB.arithm(vars[7],"=",35); 
		Constraint c59 = this.modelKB.arithm(vars[8],"=",840); 
		Constraint c510 = this.modelKB.arithm(vars[9],"=",2149); 
	
		Constraint c_agg51 = this.modelKB.and(c51,c52,c53,c54,c55,c56,c57,c58,c59,c510);
		
//		363	32	0	0	0	0	3	50	980	3229
		Constraint c61 = this.modelKB.arithm(vars[0],"=",363);
		Constraint c62 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c63 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c64 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c65 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c66 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c67 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c68 = this.modelKB.arithm(vars[7],"=",50); 
		Constraint c69 = this.modelKB.arithm(vars[8],"=",980); 
		Constraint c610 = this.modelKB.arithm(vars[9],"=",3229); 
	
		Constraint c_agg61 = this.modelKB.and(c61,c62,c63,c64,c65,c66,c67,c68,c69,c610);
		
//		102	30	0	0	0	0	4	30	535	400
		Constraint c71 = this.modelKB.arithm(vars[0],"=",102);
		Constraint c72 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c73 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c74 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c75 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c76 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c77 = this.modelKB.arithm(vars[6],"=",4); 
		Constraint c78 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c79 = this.modelKB.arithm(vars[8],"=",535); 
		Constraint c710 = this.modelKB.arithm(vars[9],"=",400); 
	
		Constraint c_agg71 = this.modelKB.and(c71,c72,c73,c74,c75,c76,c77,c78,c79,c710);
		
//		142	30	0	0	0	0	1	30	455	469
		Constraint c81 = this.modelKB.arithm(vars[0],"=",142);
		Constraint c82 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c83 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c84 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c85 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c86 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c87 = this.modelKB.arithm(vars[6],"=",1); 
		Constraint c88 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c89 = this.modelKB.arithm(vars[8],"=",455); 
		Constraint c810 = this.modelKB.arithm(vars[9],"=",469); 
	
		Constraint c_agg81 = this.modelKB.and(c81,c82,c83,c84,c85,c86,c87,c88,c89,c810);
		
//		242	30	0	0	0	1	2	30	455	581
		Constraint c91 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c92 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c93 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c94 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c95 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c96 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c97 = this.modelKB.arithm(vars[6],"=",2); 
		Constraint c98 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c99 = this.modelKB.arithm(vars[8],"=",455); 
		Constraint c910 = this.modelKB.arithm(vars[9],"=",581); 
	
		Constraint c_agg91 = this.modelKB.and(c91,c92,c93,c94,c95,c96,c97,c98,c99,c910);
		



		
		
//		242	30	0	0	0	0	3	58	460	399
		Constraint c01 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c02 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c03 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c04 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c05 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c06 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c07 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c08 = this.modelKB.arithm(vars[7],"=",58); 
		Constraint c09 = this.modelKB.arithm(vars[8],"=",460); 
		Constraint c010 = this.modelKB.arithm(vars[9],"=",399); 
	
		Constraint c_agg01 = this.modelKB.and(c01,c02,c03,c04,c05,c06,c07,c08,c09,c010);
		
//		242	30	0	0	0	0	3	30	445	499
		Constraint c011 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c120 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c130 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c140 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c150 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c160 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c170 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c180 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c190 = this.modelKB.arithm(vars[8],"=",445); 
		Constraint c1100 = this.modelKB.arithm(vars[9],"=",499); 
	
		Constraint c_agg110 = this.modelKB.and(c011,c120,c130,c140,c150,c160,c170,c180,c190,c1100);
		
//		123	27	0	0	0	1	5	30	560	579
		Constraint c021 = this.modelKB.arithm(vars[0],"=",123);
		Constraint c220 = this.modelKB.arithm(vars[1],"=",27); 
		Constraint c230 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c240 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c250 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c260 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c270 = this.modelKB.arithm(vars[6],"=",5); 
		Constraint c280 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c290 = this.modelKB.arithm(vars[8],"=",560); 
		Constraint c2100 = this.modelKB.arithm(vars[9],"=",579); 
	
		Constraint c_agg210 = this.modelKB.and(c021,c220,c230,c240,c250,c260,c270,c280,c290,c2100);
		

//		162	30	0	0	0	1	2	30	560	469
		Constraint c031 = this.modelKB.arithm(vars[0],"=",162);
		Constraint c320 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c330 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c340 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c350 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c360 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c370 = this.modelKB.arithm(vars[6],"=",2); 
		Constraint c380 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c390 = this.modelKB.arithm(vars[8],"=",560); 
		Constraint c3100 = this.modelKB.arithm(vars[9],"=",469); 
	
		Constraint c_agg310 = this.modelKB.and(c031,c320,c330,c340,c350,c360,c370,c380,c390,c3100);
		

//		241	30	0	1	0	1	2	58	505	479
		Constraint c041 = this.modelKB.arithm(vars[0],"=",241);
		Constraint c420 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c430 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c440 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c450 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c460 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c470 = this.modelKB.arithm(vars[6],"=",2); 
		Constraint c480 = this.modelKB.arithm(vars[7],"=",58); 
		Constraint c490 = this.modelKB.arithm(vars[8],"=",505); 
		Constraint c4100 = this.modelKB.arithm(vars[9],"=",479); 
	
		Constraint c_agg410 = this.modelKB.and(c041,c420,c430,c440,c450,c460,c470,c480,c490,c4100);
		
		

//		242	32	0	1	0	1	3	58	530	609
		Constraint c051 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c520 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c530 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c540 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c550 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c560 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c570 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c580 = this.modelKB.arithm(vars[7],"=",58); 
		Constraint c590 = this.modelKB.arithm(vars[8],"=",530); 
		Constraint c5100 = this.modelKB.arithm(vars[9],"=",609); 
	
		Constraint c_agg510 = this.modelKB.and(c051,c520,c530,c540,c550,c560,c570,c580,c590,c5100);
		

//		242	32	1	1	0	1	3	58	470	749
		Constraint c061 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c620 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c630 = this.modelKB.arithm(vars[2],"=",1); 
		Constraint c640 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c650 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c660 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c670 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c680 = this.modelKB.arithm(vars[7],"=",58); 
		Constraint c690 = this.modelKB.arithm(vars[8],"=",470); 
		Constraint c6100 = this.modelKB.arithm(vars[9],"=",749); 
	
		Constraint c_agg610 = this.modelKB.and(c061,c620,c630,c640,c650,c660,c670,c680,c690,c6100);
		

//		241	32	1	1	0	1	2	58	675	669
		Constraint c071 = this.modelKB.arithm(vars[0],"=",241);
		Constraint c720 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c730 = this.modelKB.arithm(vars[2],"=",1); 
		Constraint c740 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c750 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c760 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c770 = this.modelKB.arithm(vars[6],"=",2); 
		Constraint c780 = this.modelKB.arithm(vars[7],"=",58); 
		Constraint c790 = this.modelKB.arithm(vars[8],"=",675); 
		Constraint c7100 = this.modelKB.arithm(vars[9],"=",669); 
	
		Constraint c_agg710 = this.modelKB.and(c071,c720,c730,c740,c750,c760,c770,c780,c790,c7100);
		

//		242	32	0	1	1	0	3	78	765	1129
		Constraint c081 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c820 = this.modelKB.arithm(vars[1],"=",32); 
		Constraint c830 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c840 = this.modelKB.arithm(vars[3],"=",1); 
		Constraint c850 = this.modelKB.arithm(vars[4],"=",1); 
		Constraint c860 = this.modelKB.arithm(vars[5],"=",0); 
		Constraint c870 = this.modelKB.arithm(vars[6],"=",3); 
		Constraint c880 = this.modelKB.arithm(vars[7],"=",78); 
		Constraint c890 = this.modelKB.arithm(vars[8],"=",765); 
		Constraint c8100 = this.modelKB.arithm(vars[9],"=",1129); 
	
		Constraint c_agg810 = this.modelKB.and(c081,c820,c830,c840,c850,c860,c870,c880,c890,c8100);
		

//		162	32	0	0	0	0	4	50	765	2749
		Constraint c091 = this.modelKB.arithm(vars[0],"=",242);
		Constraint c920 = this.modelKB.arithm(vars[1],"=",30); 
		Constraint c930 = this.modelKB.arithm(vars[2],"=",0); 
		Constraint c940 = this.modelKB.arithm(vars[3],"=",0); 
		Constraint c950 = this.modelKB.arithm(vars[4],"=",0); 
		Constraint c960 = this.modelKB.arithm(vars[5],"=",1); 
		Constraint c970 = this.modelKB.arithm(vars[6],"=",2); 
		Constraint c980 = this.modelKB.arithm(vars[7],"=",30); 
		Constraint c990 = this.modelKB.arithm(vars[8],"=",455); 
		Constraint c9100 = this.modelKB.arithm(vars[9],"=",581); 
	
		Constraint c_agg910 = this.modelKB.and(c091,c920,c930,c940,c950,c960,c970,c980,c990,c9100);

	
		Constraint c_aggAll = this.modelKB.or(c_agg1,c_agg11,c_agg21,c_agg31,c_agg41,c_agg51,c_agg61,c_agg71,c_agg81,c_agg91,
											 c_agg01,c_agg110,c_agg210,c_agg310,c_agg410,c_agg510,c_agg610,c_agg710,c_agg810,c_agg910);
	
		this.modelKB.post(c_aggAll);
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

	@Override
	public int[][] getDomains() {
		// TODO Auto-generated method stub
		return domains;
	}
	

}
