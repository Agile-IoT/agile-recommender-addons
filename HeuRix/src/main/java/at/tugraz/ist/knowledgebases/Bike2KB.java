package at.tugraz.ist.knowledgebases;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

//https://www.itu.dk/research/cla/externals/clib/bike2.cp
public class Bike2KB implements KB{

	Model modelKB = new Model("BikeConfigurationProblem");
	int numberOfVariables = 34;
	IntVar[] vars = new IntVar[numberOfVariables];
	
	public Bike2KB(){
		// 34 variables
    	defineVariables();
    	// 31 constraints
    	defineConstraints();
	}
	
	public void defineVariables (){
    
        vars[0] = this.modelKB.intVar("person_gender", 0, 2);
        vars[1] = this.modelKB.intVar("person_height", 0, 5);
        vars[2] = this.modelKB.intVar("person_biketype", 0, 4);
        vars[3] = this.modelKB.intVar("frame_sku", 0, 37);
        
        vars[4] = this.modelKB.intVar("frame_color", 0, 14);
        vars[5] = this.modelKB.intVar("frame_biketype", 0, 4);
        vars[6] = this.modelKB.intVar("frame_size", 0, 14);
        vars[7] = this.modelKB.intVar("frame_gender", 0, 2);
        vars[8] = this.modelKB.intVar("tires_sku", 0, 16);
        
        vars[9] = this.modelKB.intVar("tires_height", 0, 3);
        vars[10] = this.modelKB.intVar("tires_width", 0, 5);
        vars[11] = this.modelKB.intVar("tires_profile", 0, 11);
        vars[12] = this.modelKB.intVar("rims_sku", 0, 12);
        vars[13] = this.modelKB.intVar("rims_height", 0, 3);
		
        vars[14] = this.modelKB.intVar("rims_width", 0, 5);
        vars[15] = this.modelKB.intVar("gear_sku", 0, 15);
        vars[16] = this.modelKB.intVar("gear_gears", 0, 10);
        vars[17] = this.modelKB.intVar("gear_biketype", 0, 4);
        vars[18] = this.modelKB.intVar("pedals_sku", 0, 9);
        
        vars[19] = this.modelKB.intVar("pedals_pedaltype", 0, 3);
        vars[20] = this.modelKB.intVar("shoes_sku", 0, 5);
        vars[21] = this.modelKB.intVar("shoes_pedaltype", 0, 3);
        
        vars[22] = this.modelKB.intVar("frame_internal", 0, 2);
        vars[23] = this.modelKB.intVar("extra_Carrier", 0, 2);
        vars[24] = this.modelKB.intVar("extra_Mudguard", 0, 2);
        vars[25] = this.modelKB.intVar("extra_Lock", 0, 2);
        vars[26] = this.modelKB.intVar("extra_Pump", 0, 2);
        vars[27] = this.modelKB.intVar("extra_Bottle", 0, 2);
        vars[28] = this.modelKB.intVar("extra_Basket", 0, 2);
        vars[29] = this.modelKB.intVar("extra_Cateye", 0, 2);
        vars[30] = this.modelKB.intVar("extra_Sidereflex", 0, 2);
        vars[31] = this.modelKB.intVar("extra_Frontreflex", 0, 2);
        vars[32] = this.modelKB.intVar("extra_Propstand", 0, 2);
        vars[33] = this.modelKB.intVar("gear_internal", 0, 2);
        
       
	}
	public void defineConstraints() {
// 31 CONSTRAINTS		
////R1:
////(((((((((((((((((((((((((((((((((((((((
//		
//
////((((((frame_sku == 1) && (frame_biketype == 2)) && ((frame_size >= 8) && (frame_size <= 11))) && (frame_gender == 2)) && frame_internal)
//			
//	Constraint c1 = this.modelKB.arithm(vars[3],"=",1); // (frame_sku == 1)
//	Constraint c2 = this.modelKB.arithm(vars[5],"=",2); // (frame_biketype == 2)
//	Constraint c3 = this.modelKB.arithm(vars[6],">",7); // (frame_size >= 8
//	Constraint c4 = this.modelKB.arithm(vars[6],"<",12); // (frame_size <= 11)
//	Constraint c5 = this.modelKB.arithm(vars[7],"=",2); // (frame_gender == 2)
//	Constraint c6 = this.modelKB.arithm(vars[22],"=",1); // frame_internal true
//	
//	Constraint c_agg1 = this.modelKB.and(c1,c2,c3,c4,c5,c6);
//	// && -> c_agg3
//	
//	//((((frame_color == 1)|| (frame_color == 11)) || (frame_color == 5)) || (frame_color == 3))) 
//	Constraint c7 = this.modelKB.arithm(vars[4],"=",1); // (frame_color == 1)
//	Constraint c8 = this.modelKB.arithm(vars[4],"=",1); // frame_color
//	Constraint c9 = this.modelKB.arithm(vars[4],"=",5); // frame_color
//	Constraint c10 = this.modelKB.arithm(vars[4],"=",3); // frame_color
//	Constraint c_agg2 = this.modelKB.or(c_agg1,c7,c8,c9,c10);
//
//	Constraint c_agg3 = this.modelKB.and(c_agg1,c_agg2);
//	
//	
////|| -> R1.1 c_agg6
//	
//	
//// (((( ((frame_sku == 4) && (frame_biketype == 1)) && (((frame_size == 8) || (frame_size == 10)) || (frame_size == 11)) ) && (frame_gender == 2)) && frame_internal) && (frame_color == 8))) 
//	Constraint c11 = this.modelKB.arithm(vars[3],"=",4); // frame_sku 
//	Constraint c12 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//	
//	Constraint c13 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
//	Constraint c14 = this.modelKB.arithm(vars[6],"=",10); // frame_size
//	Constraint c15 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//	Constraint c_agg4 = this.modelKB.or(c13,c14,c15);
//	
//	Constraint c16 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
//	Constraint c17 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//	Constraint c18 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
//	
//	Constraint c_agg5 = this.modelKB.and(c11,c12,c_agg4,c16,c17,c18);
//	
//Constraint c_agg6 = this.modelKB.or(c_agg3,c_agg5);
//	
//
////|| --> R1.2  c_agg10
//// ((((((frame_sku == 3) && (frame_biketype == 1)) && (((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && ((frame_color == 12) ||
//// (frame_color == 8)))) 
//
//Constraint c19 = this.modelKB.arithm(vars[3],"=",3); // frame_sku 
//Constraint c20 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint c21 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
//Constraint c22 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint c23 = this.modelKB.arithm(vars[6],"=",13); // frame_size
//Constraint c_agg7 = this.modelKB.or(c21,c22,c23);
//
//Constraint c24 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
//Constraint c25 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c26 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
//Constraint c27 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
//Constraint c_agg8 = this.modelKB.or(c26,c27);
//
//Constraint c_agg9 = this.modelKB.and(c19,c20,c_agg7,c24,c25,c_agg8);
//
//Constraint c_agg10 = this.modelKB.or(c_agg6,c_agg9);
//
////|| -> R1.3 c_agg14
//
//// ((((((frame_sku == 6) && (frame_biketype == 1)) && (((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((frame_color == 12) ||
//// (frame_color == 11)))) 
//
//Constraint c28 = this.modelKB.arithm(vars[3],"=",6); // frame_sku 
//Constraint c29 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint c30 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
//Constraint c31 = this.modelKB.arithm(vars[6],"=",10); // frame_size
//Constraint c32 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint c_agg11 = this.modelKB.or(c30,c31,c32);
//
//Constraint c33 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
//Constraint c34 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c35 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
//Constraint c36 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//Constraint c_agg12 = this.modelKB.or(c35,c36);
//
//Constraint c_agg13 = this.modelKB.and(c28,c29,c_agg11,c33,c34,c_agg12);
//
//Constraint c_agg14 = this.modelKB.or(c_agg10,c_agg13);
//
//
//// || -> R1.4 c_agg18
//// ((((((frame_sku == 5) && (frame_biketype == 1)) && (((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
//// (frame_color == 10)) ||
//// (frame_color == 11))))
//
//Constraint c37 = this.modelKB.arithm(vars[3],"=",5); // frame_sku 
//Constraint c38 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint c39 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
//Constraint c40 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint c41 = this.modelKB.arithm(vars[6],"=",13); // frame_size
//Constraint c_agg15 = this.modelKB.or(c39,c40,c41);
//
//Constraint c42 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
//Constraint c43 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c44 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
//Constraint c45 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
//Constraint c46 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//Constraint c_agg16 = this.modelKB.or(c44,c45,c46);
//
//Constraint c_agg17 = this.modelKB.and(c37,c38,c_agg15,c42,c43,c_agg16);
//
//Constraint c_agg18 = this.modelKB.or(c_agg14,c_agg17);
//
//
////|| -> R1.5 c_agg22
//
//// ((((((frame_sku == 7) && (frame_biketype == 1)) && (((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 12) ||
//// (frame_color == 6)) ||
//// (frame_color == 10)) ||
//// (frame_color == 11))))
//
//Constraint c47 = this.modelKB.arithm(vars[3],"=",7); // frame_sku 
//Constraint c48 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint c49 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
//Constraint c50 = this.modelKB.arithm(vars[6],"=",10); // frame_size
//Constraint c51 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint c_agg19 = this.modelKB.or(c49,c50,c51);
//
//Constraint c52 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
//Constraint c53 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c54 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
//Constraint c55 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
//Constraint c56 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//Constraint c_agg20 = this.modelKB.or(c54,c55,c56);
//
//Constraint c_agg21 = this.modelKB.and(c47,c48,c_agg19,c52,c53,c_agg20);
//
//Constraint c_agg22 = this.modelKB.or(c_agg18,c_agg21);
//
//
//// || -> R1.6 c_agg26
//
//// ((((((frame_sku == 2) && (frame_biketype == 1)) && (((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && ((((frame_color == 12) ||
//// (frame_color == 6)) ||
//// (frame_color == 3)) ||
//// (frame_color == 1)))) 
//
//Constraint c57 = this.modelKB.arithm(vars[3],"=",2); // frame_sku 
//Constraint c58 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint c59 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
//Constraint c60 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint c61 = this.modelKB.arithm(vars[6],"=",13); // frame_size
//Constraint c_agg23 = this.modelKB.or(c49,c50,c51);
//
//Constraint c62 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
//Constraint c63 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c64 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
//Constraint c65 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
//Constraint c66 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
//Constraint c67 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
//Constraint c_agg24 = this.modelKB.or(c64,c65,c66,c67);
//
//Constraint c_agg25 = this.modelKB.and(c57,c58,c_agg23,c62,c63,c_agg24);
//
//Constraint c_agg26 = this.modelKB.or(c_agg22,c_agg25);
//
////|| -> R1.7 c_agg29
//// ((((((frame_sku == 8) && (frame_biketype == 2)) && ((frame_size >= 8) && (frame_size <= 11))) && (frame_gender == 2)) && frame_internal) && (frame_color == 11)))
//Constraint c68 = this.modelKB.arithm(vars[3],"=",8); // frame_sku 
//Constraint c69 = this.modelKB.arithm(vars[5],"=",2); // frame_biketype
//
//Constraint c70 = this.modelKB.arithm(vars[6],">",7); // frame_size 
//Constraint c71 = this.modelKB.arithm(vars[6],"<",12); // frame_size
//Constraint c_agg27 = this.modelKB.or(c70,c71);
//
//Constraint c72 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
//Constraint c73 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c74 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//
//Constraint c_agg28 = this.modelKB.and(c68,c69,c_agg27,c72,c73,c74);
//
//Constraint c_agg29 = this.modelKB.or(c_agg26,c_agg28);
//
//
////|| -> R1.8 c_agg33
//
//// ((((((frame_sku == 10) && (frame_biketype == 1)) && (((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 10) ||
//// (frame_color == 8)) ||
//// (frame_color == 11)) ||
//// (frame_color == 1)))) 
//Constraint c75 = this.modelKB.arithm(vars[3],"=",10); // frame_sku 
//Constraint c76 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint c77 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
//Constraint c78 = this.modelKB.arithm(vars[6],"=",10); // frame_size
//Constraint c79 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint c_agg30 = this.modelKB.or(c77,c78,c79);
//
//Constraint c80 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
//Constraint c81 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint c82 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
//Constraint c83 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//Constraint c84 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
//Constraint c_agg31 = this.modelKB.or(c82,c83,c84);
//
//Constraint c_agg32 = this.modelKB.and(c75,c76,c_agg30,c80,c81,c_agg31);
//
//Constraint c_agg33 = this.modelKB.or(c_agg29,c_agg32);
//
//
////|| ->  R1.9 -> r1_aggr9
//// ((((((frame_sku == 9) && (frame_biketype == 1)) && (((((frame_size == 5) ||
//// (frame_size == 7)) ||
//// (frame_size == 8)) ||
//// (frame_size == 10)) ||
//// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (((((frame_color == 3) ||
//// (frame_color == 4)) ||
//// (frame_color == 8)) ||
//// (frame_color == 10)) ||
//// (frame_color == 11)))) 
//Constraint r1_9_1 = this.modelKB.arithm(vars[3],"=",9); // frame_sku 
//Constraint r1_9_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint r1_9_3 = this.modelKB.arithm(vars[6],"=",5); // frame_size 
//Constraint r1_9_4 = this.modelKB.arithm(vars[6],"=",7); // frame_size
//Constraint r1_9_5 = this.modelKB.arithm(vars[6],"=",8); // frame_size
//Constraint r1_9_6 = this.modelKB.arithm(vars[6],"=",10); // frame_size
//Constraint r1_9_7 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint r1_9_aggr1 = this.modelKB.or(r1_9_3,r1_9_4,r1_9_5,r1_9_6,r1_9_7);
//
//Constraint r1_9_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
//Constraint r1_9_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint r1_9_10 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
//Constraint r1_9_11 = this.modelKB.arithm(vars[4],"=",4); // frame_color 
//Constraint r1_9_12 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
//Constraint r1_9_13 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
//Constraint r1_9_14 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//Constraint r1_9_aggr2 = this.modelKB.or(r1_9_10,r1_9_11,r1_9_12,r1_9_13,r1_9_14);
//
//Constraint r1_9_aggr3 = this.modelKB.and(r1_9_1,r1_9_2,r1_9_aggr1,r1_9_8,r1_9_9,r1_9_aggr2);
//
//Constraint r1_aggr9 = this.modelKB.or(c_agg33,r1_9_aggr3);
//
//
//
//// || -> R1.10 r1_aggr10
//
//// ((((((frame_sku == 9) && (frame_biketype == 1)) && (((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 8) ||
//// (frame_color == 3)) ||
//// (frame_color == 1)))) 
//Constraint r1_10_1 = this.modelKB.arithm(vars[3],"=",9); // frame_sku 
//Constraint r1_10_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
//
//Constraint r1_10_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
//Constraint r1_10_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
//Constraint r1_10_5 = this.modelKB.arithm(vars[6],"=",13); // frame_size
//Constraint r1_10_aggr1 = this.modelKB.or(r1_10_3,r1_10_4,r1_10_5);
//
//Constraint r1_10_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
//Constraint r1_10_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint r1_10_10 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
//Constraint r1_10_11 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
//Constraint r1_10_12 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
//Constraint r1_10_aggr2 = this.modelKB.or(r1_10_10,r1_10_11,r1_10_12);
//
//Constraint r1_10_aggr10 = this.modelKB.and(r1_10_1,r1_10_2,r1_10_aggr1,r1_10_8,r1_10_9,r1_10_aggr2);
//
//Constraint r1_aggr10 = this.modelKB.or(r1_aggr9,r1_10_aggr10);
//
//
//
//// || -> R1.11 r1_aggr11
//// ((((((frame_sku == 12) && (frame_biketype == 3)) && ((((frame_size == 3) ||
//// (frame_size == 5)) ||
//// (frame_size == 7)) ||
//// (frame_size == 8))) && (frame_gender == 2)) && frame_internal) && (((frame_color == 12) ||
//// (frame_color == 11)) ||
//// (frame_color == 1)))) 
//
//Constraint r1_11_1 = this.modelKB.arithm(vars[3],"=",12); // frame_sku 
//Constraint r1_11_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype
//
//Constraint r1_11_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
//Constraint r1_11_4 = this.modelKB.arithm(vars[6],"=",5); // frame_size
//Constraint r1_11_5 = this.modelKB.arithm(vars[6],"=",8); // frame_size
//Constraint r1_11_aggr1 = this.modelKB.or(r1_11_3,r1_11_4,r1_11_5);
//
//Constraint r1_11_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
//Constraint r1_11_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
//
//Constraint r1_11_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
//Constraint r1_11_11 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
//Constraint r1_11_12 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
//Constraint r1_11_aggr2 = this.modelKB.or(r1_11_10,r1_11_11,r1_11_12);
//
//Constraint r1_11_aggr10 = this.modelKB.and(r1_10_1,r1_10_2,r1_10_aggr1,r1_10_8,r1_10_9,r1_10_aggr2);
//
//Constraint r1_aggr11 = this.modelKB.or(r1_aggr9,r1_10_aggr10);
//
//
//
////|| -> R1.12
//// ((((((frame_sku == 11) && (frame_biketype == 3)) && (((((frame_size == 3) ||
//// (frame_size == 5)) ||
//// (frame_size == 7)) ||
//// (frame_size == 8)) ||
//// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && ((((frame_color == 12) ||
//// (frame_color == 4)) ||
//// (frame_color == 3)) ||
//// (frame_color == 1)))) 
//
//
////|| -> R1.13
//// ((((((frame_sku == 13) && (frame_biketype == 3)) && (((((frame_size == 3) ||
//// (frame_size == 5)) ||
//// (frame_size == 7)) ||
//// (frame_size == 8)) ||
//// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && ((frame_color == 12) ||
//// (frame_color == 3)))) 
//
//
////|| -> R1.14
//// ((((((frame_sku == 15) && (frame_biketype == 1)) && (((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (((frame_color == 10) ||
//// (frame_color == 12)) ||
//// (frame_color == 11)))) 
//
//
////|| -> R1.15
//// ((((((frame_sku == 14) && (frame_biketype == 1)) && (((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
//// (frame_color == 6)) ||
//// (frame_color == 10)))) 
//
//
////|| -> R1.16
//// ((((((frame_sku == 17) && (frame_biketype == 1)) && (((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((frame_color == 12) ||
//// (frame_color == 3)))) 
//
//
////|| -> R1.17
//// ((((((frame_sku == 16) && (frame_biketype == 1)) && (((((frame_size == 7) ||
//// (frame_size == 8)) ||
//// (frame_size == 10)) ||
//// (frame_size == 11)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((((frame_color == 12) ||
//// (frame_color == 11)) ||
//// (frame_color == 6)) ||
//// (frame_color == 10)) ||
//// (frame_color == 3)))) 
//
//
////|| -> R1.18
//// ((((((frame_sku == 18) && (frame_biketype == 3)) && (((((frame_size == 3) ||
//// (frame_size == 5)) ||
//// (frame_size == 7)) ||
//// (frame_size == 8)) ||
//// (frame_size == 10))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 11))) 
//
//
////|| -> R1.19
//// ((((((frame_sku == 19) && (frame_biketype == 4)) && ((((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 12)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 6) ||
//// (frame_color == 9)))) 
//
//
////|| -> R1.20
//// ((((((frame_sku == 20) && (frame_biketype == 4)) && ((((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 12)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 11) ||
//// (frame_color == 9)))) 
//
//
//
////|| -> R1.21
//// ((((((frame_sku == 21) && (frame_biketype == 4)) && ((((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 12)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 6) ||
//// (frame_color == 8)))) 
//
//
////|| -> R1.22
//// ((((((frame_sku == 22) && (frame_biketype == 3)) && (((((frame_size == 3) ||
//// (frame_size == 5)) ||
//// (frame_size == 7)) ||
//// (frame_size == 8)) ||
//// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
//// (frame_color == 6)) ||
//// (frame_color == 10)))) 
//
//
////|| -> R1.23
//// ((((((frame_sku == 24) && (frame_biketype == 4)) && ((((frame_size == 7) ||
//// (frame_size == 8)) ||
//// (frame_size == 9)) ||
//// (frame_size == 10))) && (frame_gender == 2)) && !(frame_internal)) && (frame_color == 12))) 
//
//
//
////|| -> R1.24
//// ((((((frame_sku == 23) && (frame_biketype == 4)) && ((((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 12)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && (((frame_color == 12) ||
//// (frame_color == 3)) ||
//// (frame_color == 10)))) 
//
//
//
////|| -> R1.25
//// ((((((frame_sku == 25) && (frame_biketype == 4)) && ((((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 12)) ||
//// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 8))) 
//
//
//
////|| -> R1.26
//// ((((((frame_sku == 26) && (frame_biketype == 1)) && (((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (((((((((frame_color == 1) ||
//// (frame_color == 7)) ||
//// (frame_color == 14)) ||
//// (frame_color == 3)) ||
//// (frame_color == 13)) ||
//// (frame_color == 11)) ||
//// (frame_color == 6)) ||
//// (frame_color == 12)) ||
//// (frame_color == 8)))) 
//
//
//
////|| -> R1.27
//// ((((((frame_sku == 27) && (frame_biketype == 4)) && (((((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11)) ||
//// (frame_size == 13)) ||
//// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 1))) 
//
//
////|| -> R1.28
//// ((((((frame_sku == 28) && (frame_biketype == 4)) && (((((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11)) ||
//// (frame_size == 13)) ||
//// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 2))) 
//
//
////|| -> R1.29
//// ((((((frame_sku == 29) && (frame_biketype == 4)) && (((((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11)) ||
//// (frame_size == 13)) ||
//// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 1) ||
//// (frame_color == 13)))) 
//
//
////|| -> R1.30
//// ((((((frame_sku == 30) && (frame_biketype == 3)) && ((((frame_size == 3) ||
//// (frame_size == 5)) ||
//// (frame_size == 7)) ||
//// (frame_size == 9))) && ((frame_gender == 1) ||
//// (frame_gender == 2))) && frame_internal) && (frame_color == 12))) 
//
//
//
////|| -> R1.31
//// ((((((frame_sku == 31) && ((frame_biketype == 1) ||
//// (frame_biketype == 3))) && (((frame_size == 8) ||
//// (frame_size == 9)) ||
//// (frame_size == 10))) && (frame_gender == 2)) && frame_internal) && (frame_color == 12))) 
//
//
////|| -> R1.32
//// ((((((frame_sku == 31) && ((frame_biketype == 1) ||
//// (frame_biketype == 3))) && (((frame_size == 9) ||
//// (frame_size == 10)) ||
//// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (frame_color == 12))) 
//
//
////|| -> R1.33
//// ((((((frame_sku == 32) && ((frame_biketype == 1) ||
//// (frame_biketype == 3))) && ((((frame_size == 8) ||
//// (frame_size == 9)) ||
//// (frame_size == 10)) ||
//// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (frame_color == 12))) 
//
//
////|| -> R1.34
//// ((((((frame_sku == 32) && ((frame_biketype == 1) ||
//// (frame_biketype == 3))) && ((frame_size == 8) ||
//// (frame_size == 9))) && (frame_gender == 2)) && frame_internal) && (frame_color == 12))) 
//
//
////|| -> R1.35
//// ((((((frame_sku == 33) && (frame_biketype == 1)) && (((frame_size == 10) ||
//// (frame_size == 11)) ||
//// (frame_size == 12))) && ((frame_gender == 1) ||
//// (frame_gender == 2))) && frame_internal) && ((frame_color == 12) ||
//// (frame_color == 8)))) 
//
//
////|| -> R1.36
//// (((((frame_sku == 34) && (frame_biketype == 3)) && (((((frame_size == 8) ||
//// (frame_size == 10)) ||
//// (frame_size == 11)) && (frame_gender == 1)) ||
//// (((frame_size == 8) ||
//// (frame_size == 10)) && (frame_gender == 2)))) && !(frame_internal)) && (frame_color == 12))) 
//
//
////|| -> R1.37
//// ((((((frame_sku == 35) && (frame_biketype == 3)) && (((frame_size == 4) ||
//// (frame_size == 6)) ||
//// (frame_size == 8))) && (frame_gender == 1)) && frame_internal) && (frame_color == 6))) 
//
//
//// || -> R1.38
//// ((((((frame_sku == 36) && (frame_biketype == 3)) && ((frame_size >= 1) && (frame_size <= 11))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 11))) 
//
//
////|| -> R1.39
//// ((((((frame_sku == 37) && (frame_biketype == 3)) && ((frame_size >= 1) && (frame_size <= 11))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 12)));
//		
		
		
//		R2:
//		(((frame_biketype == 1) ||
//		(frame_biketype == 2)) >> frame_internal);
		this.modelKB.ifThen(
 			   this.modelKB.arithm(vars[5],"=",1),
 			   this.modelKB.arithm(vars[22],"=",1)
	    );
		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[5],"=",2),
	 			   this.modelKB.arithm(vars[22],"=",1)
		);
			
		
//R3:
//((frame_biketype == 4) >> !(frame_internal));

		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[5],"=",4),
	 			   this.modelKB.arithm(vars[22],"=",0)
		    );
//R4:
//(extra_Carrier >> extra_Mudguard);

		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[23],"=",1),
	 			   this.modelKB.arithm(vars[24],"=",1)
		    );
//R5:
//(extra_Pump && extra_Bottle);
		
		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[26],"=",1),
	 			   this.modelKB.arithm(vars[27],"=",1)
		    );
		
//R6:
//(((((((((((((((((((tires_sku == 16) && (tires_profile == 2)) && (tires_height == 2)) && (tires_width == 2)) ||
// ((((tires_sku == 4) && ((tires_profile == 5) ||
// (tires_profile == 9))) && (tires_height == 3)) && ((tires_width == 1) ||
// (tires_width == 3)))) ||
// ((((tires_sku == 14) && (((tires_profile == 2) ||
// (tires_profile == 5)) ||
// (tires_profile == 7))) && (tires_height == 3)) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
// ((((tires_sku == 13) && (((tires_profile == 1) ||
// (tires_profile == 4)) ||
// (tires_profile == 6))) && (tires_height == 3)) && ((tires_width == 1) ||
// (tires_width == 2)))) ||
// ((((tires_sku == 7) && ((tires_profile == 10) ||
// (tires_profile == 11))) && (tires_height == 3)) && ((tires_width == 4) ||
// (tires_width == 5)))) ||
// ((((tires_sku == 2) && ((tires_profile == 2) ||
// (tires_profile == 3))) && (tires_height == 3)) && (tires_width == 2))) ||
// ((((tires_sku == 15) && (tires_profile == 4)) && (tires_height == 3)) && (tires_width == 1))) ||
// ((((tires_sku == 5) && (tires_profile == 3)) && (tires_height == 3)) && (tires_width == 2))) ||
// ((((tires_sku == 1) && ((tires_profile == 2) ||
// (tires_profile == 4))) && (tires_height == 3)) && (tires_width == 3))) ||
// ((((tires_sku == 3) && (tires_profile == 2)) && (tires_height == 2)) && ((tires_width == 3) ||
// (tires_width == 4)))) ||
// ((((tires_sku == 8) && (tires_profile == 2)) && (tires_height == 2)) && ((tires_width == 3) ||
// (tires_width == 2)))) ||
// ((((tires_sku == 9) && ((tires_profile == 2) ||
// (tires_profile == 4))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
// ((((tires_sku == 10) && ((tires_profile == 4) ||
// (tires_profile == 6))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
// ((((tires_sku == 12) && (((((tires_profile == 4) ||
// (tires_profile == 5)) ||
// (tires_profile == 6)) ||
// (tires_profile == 7)) ||
// (tires_profile == 8))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
// ((((tires_sku == 11) && ((tires_profile == 2) ||
// (tires_profile == 1))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 1) ||
// (tires_width == 3)))) ||
// ((((tires_sku == 6) && (tires_profile == 10)) && (tires_height == 1)) && ((tires_width == 3) ||
// (tires_width == 4))));
//
//R7:
//(
//(
//(
//(((((((((((rims_sku == 9) && (rims_height == 3)) && (rims_width == 1)) ||
// (((rims_sku == 11) && (rims_height == 3)) && (rims_width == 2))) ||
// (((rims_sku == 7) && (rims_height == 3)) && (rims_width == 3))) ||
// (((rims_sku == 10) && (rims_height == 3)) && (rims_width == 2))) ||
// (((rims_sku == 12) && (rims_height == 2)) && (rims_width == 4))) ||
// (((rims_sku == 5) && (rims_height == 2)) && (rims_width == 3))) ||
// (((rims_sku == 6) && (rims_height == 1)) && (rims_width == 4))) ||
// (((rims_sku == 2) && (rims_height == 2)) && (rims_width == 2))) ||
// (((rims_sku == 4) && (rims_height == 2)) && (rims_width == 1))
//
// ) ||
// (((rims_sku == 3) && (rims_height == 2)) && (rims_width == 1))
// )
//  ||
// (((rims_sku == 1) && (rims_height == 2)) && (rims_width == 2))
// ) ||
// (((rims_sku == 8) && (rims_height == 1)) && (rims_width == 3))
// 
// );
//
//
//R8:
//((((((((((((
//
//((((((gear_sku == 5) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 7)) ||
// ((((gear_sku == 15) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 6))) ||
// ((((gear_sku == 13) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 6))) ||
// ((((gear_sku == 12) && (gear_biketype == 3)) && !(gear_internal)) && (gear_gears == 8))) ||
// ((((gear_sku == 1) && (gear_biketype == 3)) && !(gear_internal)) && (gear_gears == 9))) ||
// ((((gear_sku == 10) && (((gear_biketype == 1) ||
// (gear_biketype == 3)) ||
// (gear_biketype == 2))) && (gear_internal == 1)) && (((gear_gears == 2) ||
// (gear_gears == 3)) ||
// (gear_gears == 5)))) ||
//
//
// ((((gear_sku == 11) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 6))) ||
// ((((gear_sku == 7) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 7))) ||
// ((((gear_sku == 2) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 6))) ||
// ((((gear_sku == 3) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 7))) ||
// ((((gear_sku == 4) && (gear_biketype == 4)) && !(gear_internal)) && (gear_gears == 7))) ||
// ((((gear_sku == 9) && (gear_biketype == 3)) && !(gear_internal)) && (gear_gears == 10))) ||
// ((((gear_sku == 8) && (gear_biketype == 3)) && !(gear_internal)) && (gear_gears == 9))) ||
// ((((gear_sku == 14) && ((gear_biketype == 1) ||
// (gear_biketype == 2))) && (gear_internal == 1)) && ((gear_gears == 2) ||
// (gear_gears == 4)))) ||
//
// (((gear_sku == 6) && (gear_internal == 1)) && (gear_gears == 1)));
//
//((gear_biketype == 4) >> (gear_internal == 0));
//
//R9:
//((((((((((pedals_sku == 5) && (pedals_pedaltype == 2)) ||
// ((pedals_sku == 4) && (pedals_pedaltype == 2))) ||
// ((pedals_sku == 9) && (pedals_pedaltype == 3))) ||
// ((pedals_sku == 8) && (pedals_pedaltype == 3))) ||
// ((pedals_sku == 3) && (pedals_pedaltype == 2))) ||
// ((pedals_sku == 2) && (pedals_pedaltype == 2))) ||
// ((pedals_sku == 7) && (pedals_pedaltype == 1))) ||
// ((pedals_sku == 1) && (pedals_pedaltype == 1))) ||
// ((pedals_sku == 6) && (pedals_pedaltype == 1)));
//
//R10:
//((((((shoes_sku == 5) && (shoes_pedaltype == 2)) ||
// ((shoes_sku == 4) && (shoes_pedaltype == 3))) ||
// ((shoes_sku == 3) && (shoes_pedaltype == 2))) ||
// ((shoes_sku == 2) && (shoes_pedaltype == 3))) ||
// (shoes_sku == 1));
//
//R11:
//(((rims_height == 1) >> ((frame_size >= 3) && (frame_size <= 10))) &&
//((rims_height == 2) >> ((frame_size >= 5) && (frame_size <= 14))) &&
//((rims_height == 3) >> ((frame_size >= 7) && (frame_size <= 14))) &&
//((!((rims_height == 1)) &&
//!((rims_height == 2)) &&
//!((rims_height == 3))) >> 1));
//
//R12:
//(((frame_biketype == 4) >> ((((tires_profile >= 1) && (tires_profile <= 7)) && !(extra_Mudguard)) && !(extra_Basket))) &&
//((frame_biketype == 3) >> (((tires_profile >= 8) && !(extra_Carrier)) && !(extra_Basket))) &&
//((frame_biketype == 1) >> ((tires_profile >= 4) && (tires_profile <= 10))) &&
//((frame_biketype == 2) >> (((((tires_profile >= 6) && (tires_profile <= 10)) && extra_Carrier) && extra_Propstand) && extra_Basket)) &&
//((!((frame_biketype == 4)) &&
//!((frame_biketype == 3)) &&
//!((frame_biketype == 1)) &&
//!((frame_biketype == 2))) >> 1));
//
		
//R13:
//(!(frame_internal) >> !(extra_Propstand));
		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[22],"=",0),
	 			   this.modelKB.arithm(vars[32],"=",0)
		    );
//R14:
//(rims_height == tires_height);
		this.modelKB.arithm(vars[13],"=",vars[9]).post();
//R15:
//(rims_width == tires_width);
		this.modelKB.arithm(vars[14],"=",vars[10]).post();
//R16:
//(frame_internal == gear_internal);
		this.modelKB.arithm(vars[22],"=",vars[33]).post();
//R17:
//(pedals_pedaltype == shoes_pedaltype);
		this.modelKB.arithm(vars[19],"=",vars[21]).post();
//R18:
//(frame_biketype == gear_biketype);
		this.modelKB.arithm(vars[5],"=",vars[17]).post();
//R19:
//(person_gender == frame_gender);
		this.modelKB.arithm(vars[0],"=",vars[7]).post();
//R20:
//(person_biketype == frame_biketype);
		this.modelKB.arithm(vars[2],"=",vars[5]).post();
//R21:
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 1)) >> ((frame_size >= 5) && (frame_size <= 8)));
//R22:
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 2)) >> ((frame_size >= 7) && (frame_size <= 10)));
///R23:
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 3)) >> ((frame_size >= 9) && (frame_size <= 12)));
//R24:
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 4)) >> ((frame_size >= 11) && (frame_size <= 14)));
//R25:
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 5)) >> ((frame_size >= 13) && (frame_size <= 14)));
//R26:
//(((frame_biketype == 3) && (person_height == 1)) >> ((frame_size >= 3) && (frame_size <= 6)));
//R27:
//(((frame_biketype == 3) && (person_height == 2)) >> ((frame_size >= 4) && (frame_size <= 7)));
//R28:
//(((frame_biketype == 3) && (person_height == 3)) >> ((frame_size >= 5) && (frame_size <= 8)));
//R29:
//(((frame_biketype == 3) && ((person_height == 4) ||
// (person_height == 5))) >> ((frame_size >= 6) && (frame_size <= 10)));
//
//R30:
//((((((tires_sku == 8) ||
// (tires_sku == 9)) ||
// (tires_sku == 12)) ||
// (tires_sku == 10)) ||
// (tires_sku == 11)) >> !(extra_Sidereflex));
//
//R31:
//(((frame_biketype == 4) >> (pedals_pedaltype != 1)) &&
//((frame_biketype == 3) >> (pedals_pedaltype != 1)) &&
//((frame_biketype == 1) >> (pedals_pedaltype == 1)) &&
//((frame_biketype == 2) >> (pedals_pedaltype == 1)) &&
//((!((frame_biketype == 4)) &&
//!((frame_biketype == 3)) &&
//!((frame_biketype == 1)) &&
//!((frame_biketype == 2))) >> 1));
		
		
	
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
	

}
