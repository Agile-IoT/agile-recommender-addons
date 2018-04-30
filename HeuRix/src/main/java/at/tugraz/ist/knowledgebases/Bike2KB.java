package at.tugraz.ist.knowledgebases;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

//https://www.itu.dk/research/cla/externals/clib/bike2.cp
public class Bike2KB implements KB{

	Model modelKB = new Model("BikeConfigurationProblem");
	int numberOfVariables = 34;
	IntVar[] vars = new IntVar[numberOfVariables];
	int [][] domains = null;
	
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
        
        for (int i=0;i<numberOfVariables;i++){
			domains[i]= new int[vars[i].getDomainSize()];
			for(int j=0;j<vars[i].getDomainSize();j++){
				domains[i][j]=j;
			}
		}
	}
	public void defineConstraints() {
// 31 CONSTRAINTS		
////R1: DONE
////(((((((((((((((((((((((((((((((((((((((
//		
//
////((((((frame_sku == 1) && (frame_biketype == 2)) && ((frame_size >= 8) && (frame_size <= 11))) && (frame_gender == 2)) && frame_internal)
			
	Constraint c1 = this.modelKB.arithm(vars[3],"=",1); // (frame_sku == 1)
	Constraint c2 = this.modelKB.arithm(vars[5],"=",2); // (frame_biketype == 2)
	Constraint c3 = this.modelKB.arithm(vars[6],">",7); // (frame_size >= 8
	Constraint c4 = this.modelKB.arithm(vars[6],"<",12); // (frame_size <= 11)
	Constraint c5 = this.modelKB.arithm(vars[7],"=",2); // (frame_gender == 2)
	Constraint c6 = this.modelKB.arithm(vars[22],"=",1); // frame_internal true
	
	Constraint c_agg1 = this.modelKB.and(c1,c2,c3,c4,c5,c6);
	// && -> c_agg3
	
	//((((frame_color == 1)|| (frame_color == 11)) || (frame_color == 5)) || (frame_color == 3))) 
	Constraint c7 = this.modelKB.arithm(vars[4],"=",1); // (frame_color == 1)
	Constraint c8 = this.modelKB.arithm(vars[4],"=",1); // frame_color
	Constraint c9 = this.modelKB.arithm(vars[4],"=",5); // frame_color
	Constraint c10 = this.modelKB.arithm(vars[4],"=",3); // frame_color
	Constraint c_agg2 = this.modelKB.or(c_agg1,c7,c8,c9,c10);

	Constraint c_agg3 = this.modelKB.and(c_agg1,c_agg2);
	
	
//|| -> R1.1 c_agg6
	
	
// (((( ((frame_sku == 4) && (frame_biketype == 1)) && (((frame_size == 8) || (frame_size == 10)) || (frame_size == 11)) ) && (frame_gender == 2)) && frame_internal) && (frame_color == 8))) 
	Constraint c11 = this.modelKB.arithm(vars[3],"=",4); // frame_sku 
	Constraint c12 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
	
	Constraint c13 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
	Constraint c14 = this.modelKB.arithm(vars[6],"=",10); // frame_size
	Constraint c15 = this.modelKB.arithm(vars[6],"=",11); // frame_size
	Constraint c_agg4 = this.modelKB.or(c13,c14,c15);
	
	Constraint c16 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
	Constraint c17 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 
	Constraint c18 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
	
	Constraint c_agg5 = this.modelKB.and(c11,c12,c_agg4,c16,c17,c18);
	
Constraint c_agg6 = this.modelKB.or(c_agg3,c_agg5);
	

//|| --> R1.2  c_agg10
// ((((((frame_sku == 3) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 8)))) 

Constraint c19 = this.modelKB.arithm(vars[3],"=",3); // frame_sku 
Constraint c20 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint c21 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint c22 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint c23 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint c_agg7 = this.modelKB.or(c21,c22,c23);

Constraint c24 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint c25 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c26 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint c27 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
Constraint c_agg8 = this.modelKB.or(c26,c27);

Constraint c_agg9 = this.modelKB.and(c19,c20,c_agg7,c24,c25,c_agg8);

Constraint c_agg10 = this.modelKB.or(c_agg6,c_agg9);

//|| -> R1.3 c_agg14

// ((((((frame_sku == 6) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 11)))) 

Constraint c28 = this.modelKB.arithm(vars[3],"=",6); // frame_sku 
Constraint c29 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint c30 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint c31 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint c32 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint c_agg11 = this.modelKB.or(c30,c31,c32);

Constraint c33 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint c34 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c35 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint c36 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint c_agg12 = this.modelKB.or(c35,c36);

Constraint c_agg13 = this.modelKB.and(c28,c29,c_agg11,c33,c34,c_agg12);

Constraint c_agg14 = this.modelKB.or(c_agg10,c_agg13);


// || -> R1.4 c_agg18
// ((((((frame_sku == 5) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 10)) ||
// (frame_color == 11))))

Constraint c37 = this.modelKB.arithm(vars[3],"=",5); // frame_sku 
Constraint c38 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint c39 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint c40 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint c41 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint c_agg15 = this.modelKB.or(c39,c40,c41);

Constraint c42 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint c43 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c44 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint c45 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint c46 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint c_agg16 = this.modelKB.or(c44,c45,c46);

Constraint c_agg17 = this.modelKB.and(c37,c38,c_agg15,c42,c43,c_agg16);

Constraint c_agg18 = this.modelKB.or(c_agg14,c_agg17);


//|| -> R1.5 c_agg22

// ((((((frame_sku == 7) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 10)) ||
// (frame_color == 11))))

Constraint c47 = this.modelKB.arithm(vars[3],"=",7); // frame_sku 
Constraint c48 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint c49 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint c50 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint c51 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint c_agg19 = this.modelKB.or(c49,c50,c51);

Constraint c52 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint c53 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c54 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint c55 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint c56 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint c_agg20 = this.modelKB.or(c54,c55,c56);

Constraint c_agg21 = this.modelKB.and(c47,c48,c_agg19,c52,c53,c_agg20);

Constraint c_agg22 = this.modelKB.or(c_agg18,c_agg21);


// || -> R1.6 c_agg26

// ((((((frame_sku == 2) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && ((((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 3)) ||
// (frame_color == 1)))) 

Constraint c57 = this.modelKB.arithm(vars[3],"=",2); // frame_sku 
Constraint c58 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint c59 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint c60 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint c61 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint c_agg23 = this.modelKB.or(c59,c60,c61);

Constraint c62 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint c63 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c64 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint c65 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint c66 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint c67 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint c_agg24 = this.modelKB.or(c64,c65,c66,c67);

Constraint c_agg25 = this.modelKB.and(c57,c58,c_agg23,c62,c63,c_agg24);

Constraint c_agg26 = this.modelKB.or(c_agg22,c_agg25);

//|| -> R1.7 c_agg29
// ((((((frame_sku == 8) && (frame_biketype == 2)) && ((frame_size >= 8) && (frame_size <= 11))) && (frame_gender == 2)) && frame_internal) && (frame_color == 11)))
Constraint c68 = this.modelKB.arithm(vars[3],"=",8); // frame_sku 
Constraint c69 = this.modelKB.arithm(vars[5],"=",2); // frame_biketype

Constraint c70 = this.modelKB.arithm(vars[6],">",7); // frame_size 
Constraint c71 = this.modelKB.arithm(vars[6],"<",12); // frame_size
Constraint c_agg27 = this.modelKB.or(c70,c71);

Constraint c72 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint c73 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c74 = this.modelKB.arithm(vars[4],"=",11); // frame_color 

Constraint c_agg28 = this.modelKB.and(c68,c69,c_agg27,c72,c73,c74);

Constraint c_agg29 = this.modelKB.or(c_agg26,c_agg28);


//|| -> R1.8 c_agg33

// ((((((frame_sku == 10) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 10) ||
// (frame_color == 8)) ||
// (frame_color == 11)) ||
// (frame_color == 1)))) 
Constraint c75 = this.modelKB.arithm(vars[3],"=",10); // frame_sku 
Constraint c76 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint c77 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint c78 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint c79 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint c_agg30 = this.modelKB.or(c77,c78,c79);

Constraint c80 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint c81 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint c82 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint c83 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint c84 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint c_agg31 = this.modelKB.or(c82,c83,c84);

Constraint c_agg32 = this.modelKB.and(c75,c76,c_agg30,c80,c81,c_agg31);

Constraint c_agg33 = this.modelKB.or(c_agg29,c_agg32);


//|| ->  R1.9 -> r1_aggr9
// ((((((frame_sku == 9) && (frame_biketype == 1)) && (((((frame_size == 5) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (((((frame_color == 3) ||
// (frame_color == 4)) ||
// (frame_color == 8)) ||
// (frame_color == 10)) ||
// (frame_color == 11)))) 
Constraint r1_9_1 = this.modelKB.arithm(vars[3],"=",9); // frame_sku 
Constraint r1_9_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_9_3 = this.modelKB.arithm(vars[6],"=",5); // frame_size 
Constraint r1_9_4 = this.modelKB.arithm(vars[6],"=",7); // frame_size
Constraint r1_9_5 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_9_6 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_9_7 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_9_aggr1 = this.modelKB.or(r1_9_3,r1_9_4,r1_9_5,r1_9_6,r1_9_7);

Constraint r1_9_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_9_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_9_10 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_9_11 = this.modelKB.arithm(vars[4],"=",4); // frame_color 
Constraint r1_9_12 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
Constraint r1_9_13 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint r1_9_14 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_9_aggr2 = this.modelKB.or(r1_9_10,r1_9_11,r1_9_12,r1_9_13,r1_9_14);

Constraint r1_9_aggr3 = this.modelKB.and(r1_9_1,r1_9_2,r1_9_aggr1,r1_9_8,r1_9_9,r1_9_aggr2);

Constraint r1_aggr9 = this.modelKB.or(c_agg33,r1_9_aggr3);



// || -> R1.10 r1_aggr10

// ((((((frame_sku == 9) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 8) ||
// (frame_color == 3)) ||
// (frame_color == 1)))) 
Constraint r1_10_1 = this.modelKB.arithm(vars[3],"=",9); // frame_sku 
Constraint r1_10_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_10_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_10_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_10_5 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_10_aggr1 = this.modelKB.or(r1_10_3,r1_10_4,r1_10_5);

Constraint r1_10_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_10_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_10_10 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
Constraint r1_10_11 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_10_12 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint r1_10_aggr2 = this.modelKB.or(r1_10_10,r1_10_11,r1_10_12);

Constraint r1_10_aggr10 = this.modelKB.and(r1_10_1,r1_10_2,r1_10_aggr1,r1_10_8,r1_10_9,r1_10_aggr2);

Constraint r1_aggr10 = this.modelKB.or(r1_aggr9,r1_10_aggr10);



// || -> R1.11 r1_aggr11
// ((((((frame_sku == 12) && (frame_biketype == 3)) && ((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8))) && (frame_gender == 2)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 11)) ||
// (frame_color == 1)))) 

Constraint r1_11_1 = this.modelKB.arithm(vars[3],"=",12); // frame_sku 
Constraint r1_11_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_11_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
Constraint r1_11_4 = this.modelKB.arithm(vars[6],"=",5); // frame_size
Constraint r1_11_5 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_11_aggr1 = this.modelKB.or(r1_11_3,r1_11_4,r1_11_5);

Constraint r1_11_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_11_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_11_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_11_11 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_11_12 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint r1_11_aggr2 = this.modelKB.or(r1_11_10,r1_11_11,r1_11_12);

Constraint r1_11_aggr10 = this.modelKB.and(r1_11_1,r1_11_2,r1_11_aggr1,r1_11_8,r1_11_9,r1_11_aggr2);

Constraint r1_aggr11 = this.modelKB.or(r1_aggr10,r1_11_aggr10);



//|| -> R1.12
// ((((((frame_sku == 11) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && ((((frame_color == 12) ||
// (frame_color == 4)) ||
// (frame_color == 3)) ||
// (frame_color == 1)))) 

Constraint r1_12_1 = this.modelKB.arithm(vars[3],"=",11); // frame_sku 
Constraint r1_12_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_12_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
Constraint r1_12_4 = this.modelKB.arithm(vars[6],"=",5); // frame_size
Constraint r1_12_5 = this.modelKB.arithm(vars[6],"=",7); // frame_size
Constraint r1_12_6 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_12_7 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_12_aggr1 = this.modelKB.or(r1_12_3,r1_12_4,r1_12_5,r1_12_6,r1_12_7);

Constraint r1_12_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_12_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_12_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_12_11 = this.modelKB.arithm(vars[4],"=",4); // frame_color 
Constraint r1_12_12 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_12_13 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint r1_12_aggr2 = this.modelKB.or(r1_12_10,r1_12_11,r1_12_12,r1_12_13);

Constraint r1_12_aggr10 = this.modelKB.and(r1_12_1,r1_12_2,r1_12_aggr1,r1_12_8,r1_12_9,r1_12_aggr2);

Constraint r1_aggr12 = this.modelKB.or(r1_aggr11,r1_12_aggr10);


//|| -> R1.13
// ((((((frame_sku == 13) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 3)))) 

Constraint r1_13_1 = this.modelKB.arithm(vars[3],"=",13); // frame_sku 
Constraint r1_13_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_13_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
Constraint r1_13_4 = this.modelKB.arithm(vars[6],"=",5); // frame_size
Constraint r1_13_5 = this.modelKB.arithm(vars[6],"=",7); // frame_size
Constraint r1_13_6 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_13_7 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_13_aggr1 = this.modelKB.or(r1_13_3,r1_13_4,r1_13_5,r1_13_6,r1_13_7);

Constraint r1_13_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_13_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_13_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_13_11 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_13_aggr2 = this.modelKB.or(r1_13_10,r1_13_11);

Constraint r1_13_aggr10 = this.modelKB.and(r1_13_1,r1_13_2,r1_13_aggr1,r1_13_8,r1_13_9,r1_13_aggr2);

Constraint r1_aggr13 = this.modelKB.or(r1_aggr12,r1_13_aggr10);




//|| -> R1.14
// ((((((frame_sku == 15) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (((frame_color == 10) ||
// (frame_color == 12)) ||
// (frame_color == 11)))) 

Constraint r1_14_1 = this.modelKB.arithm(vars[3],"=",15); // frame_sku 
Constraint r1_14_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_14_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_14_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_14_5 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_14_aggr1 = this.modelKB.or(r1_14_3,r1_14_4,r1_14_5);

Constraint r1_14_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_14_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_14_10 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint r1_14_11 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_14_12 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_14_aggr2 = this.modelKB.or(r1_14_10,r1_14_11,r1_14_12);

Constraint r1_14_aggr10 = this.modelKB.and(r1_14_1,r1_14_2,r1_14_aggr1,r1_14_8,r1_14_9,r1_14_aggr2);

Constraint r1_aggr14 = this.modelKB.or(r1_aggr13,r1_14_aggr10);


//|| -> R1.15
// ((((((frame_sku == 14) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 10)))) 
Constraint r1_15_1 = this.modelKB.arithm(vars[3],"=",14); // frame_sku 
Constraint r1_15_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_15_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_15_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_15_5 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_15_aggr1 = this.modelKB.or(r1_15_3,r1_15_4,r1_15_5);

Constraint r1_15_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_15_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_15_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_15_11 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint r1_15_12 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint r1_15_aggr2 = this.modelKB.or(r1_15_10,r1_15_11,r1_15_12);

Constraint r1_15_aggr10 = this.modelKB.and(r1_15_1,r1_15_2,r1_15_aggr1,r1_15_8,r1_15_9,r1_15_aggr2);

Constraint r1_aggr15 = this.modelKB.or(r1_aggr14,r1_15_aggr10);

//|| -> R1.16
// ((((((frame_sku == 17) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 3)))) 

Constraint r1_16_1 = this.modelKB.arithm(vars[3],"=",17); // frame_sku 
Constraint r1_16_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_16_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_16_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_16_5 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_16_aggr1 = this.modelKB.or(r1_16_3,r1_16_4,r1_16_5);

Constraint r1_16_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_16_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_16_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_16_11 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_16_aggr2 = this.modelKB.or(r1_16_10,r1_16_11);

Constraint r1_16_aggr10 = this.modelKB.and(r1_16_1,r1_16_2,r1_16_aggr1,r1_16_8,r1_16_9,r1_16_aggr2);

Constraint r1_aggr16 = this.modelKB.or(r1_aggr15,r1_16_aggr10);


//|| -> R1.17
// ((((((frame_sku == 16) && (frame_biketype == 1)) && (((((frame_size == 7) ||
// (frame_size == 8)) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((((frame_color == 12) ||
// (frame_color == 11)) ||
// (frame_color == 6)) ||
// (frame_color == 10)) ||
// (frame_color == 3)))) 
Constraint r1_17_1 = this.modelKB.arithm(vars[3],"=",16); // frame_sku 
Constraint r1_17_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_17_3 = this.modelKB.arithm(vars[6],"=",7); // frame_size 
Constraint r1_17_4 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_17_5 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_17_6 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_17_7 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_17_aggr1 = this.modelKB.or(r1_17_3,r1_17_4,r1_17_5,r1_17_6,r1_17_7);

Constraint r1_17_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_17_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_17_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_17_11 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_17_12 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint r1_17_13 = this.modelKB.arithm(vars[4],"=",10); // frame_color 
Constraint r1_17_14 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_17_aggr2 = this.modelKB.or(r1_17_10,r1_17_11,r1_17_12,r1_17_13,r1_17_14);

Constraint r1_17_aggr10 = this.modelKB.and(r1_17_1,r1_17_2,r1_17_aggr1,r1_17_8,r1_17_9,r1_17_aggr2);

Constraint r1_aggr17 = this.modelKB.or(r1_aggr16,r1_17_aggr10);




//|| -> R1.18
// ((((((frame_sku == 18) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 11))) 


Constraint r1_18_1 = this.modelKB.arithm(vars[3],"=",18); // frame_sku 
Constraint r1_18_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_18_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
Constraint r1_18_4 = this.modelKB.arithm(vars[6],"=",5); // frame_size
Constraint r1_18_5 = this.modelKB.arithm(vars[6],"=",7); // frame_size
Constraint r1_18_6 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_18_7 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_18_aggr1 = this.modelKB.or(r1_18_3,r1_18_4,r1_18_5,r1_18_6,r1_18_7);

Constraint r1_18_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_18_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_18_10 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_18_aggr2 = this.modelKB.or(r1_18_10);

Constraint r1_18_aggr10 = this.modelKB.and(r1_18_1,r1_18_2,r1_18_aggr1,r1_18_8,r1_18_9,r1_18_aggr2);

Constraint r1_aggr18 = this.modelKB.or(r1_aggr17,r1_18_aggr10);




//|| -> R1.19
// ((((((frame_sku == 19) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 6) ||
// (frame_color == 9)))) 

Constraint r1_19_1 = this.modelKB.arithm(vars[3],"=",19); // frame_sku 
Constraint r1_19_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_19_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_19_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_19_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_19_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_19_aggr1 = this.modelKB.or(r1_19_3,r1_19_4,r1_19_5,r1_19_6);

Constraint r1_19_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_19_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_19_10 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint r1_19_11 = this.modelKB.arithm(vars[4],"=",9); // frame_color 
Constraint r1_19_aggr2 = this.modelKB.or(r1_19_10,r1_19_11);

Constraint r1_19_aggr10 = this.modelKB.and(r1_19_1,r1_19_2,r1_19_aggr1,r1_19_8,r1_19_9,r1_19_aggr2);

Constraint r1_aggr19 = this.modelKB.or(r1_aggr18,r1_19_aggr10);


//|| -> R1.20
// ((((((frame_sku == 20) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 11) ||
// (frame_color == 9)))) 

Constraint r1_20_1 = this.modelKB.arithm(vars[3],"=",20); // frame_sku 
Constraint r1_20_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_20_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_20_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_20_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_20_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_20_aggr1 = this.modelKB.or(r1_20_3,r1_20_4,r1_20_5,r1_20_6);

Constraint r1_20_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_20_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_20_10 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_20_11 = this.modelKB.arithm(vars[4],"=",9); // frame_color
Constraint r1_20_aggr2 = this.modelKB.or(r1_20_10,r1_20_11);

Constraint r1_20_aggr10 = this.modelKB.and(r1_20_1,r1_20_2,r1_20_aggr1,r1_20_8,r1_20_9,r1_20_aggr2);

Constraint r1_aggr20 = this.modelKB.or(r1_aggr19,r1_20_aggr10);

//|| -> R1.21
// ((((((frame_sku == 21) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 6) ||
// (frame_color == 8)))) 


Constraint r1_21_1 = this.modelKB.arithm(vars[3],"=",21); // frame_sku 
Constraint r1_21_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_21_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_21_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_21_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_21_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_21_aggr1 = this.modelKB.or(r1_21_3,r1_21_4,r1_21_5,r1_21_6);

Constraint r1_21_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_21_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_21_10 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint r1_21_11 = this.modelKB.arithm(vars[4],"=",8); // frame_color
Constraint r1_21_aggr2 = this.modelKB.or(r1_21_10,r1_21_11);

Constraint r1_21_aggr10 = this.modelKB.and(r1_21_1,r1_21_2,r1_21_aggr1,r1_21_8,r1_21_9,r1_21_aggr2);

Constraint r1_aggr21 = this.modelKB.or(r1_aggr20,r1_21_aggr10);


//|| -> R1.22
// ((((((frame_sku == 22) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 10)))) 


Constraint r1_22_1 = this.modelKB.arithm(vars[3],"=",21); // frame_sku 
Constraint r1_22_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_22_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_22_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_22_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_22_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_22_aggr1 = this.modelKB.or(r1_22_3,r1_22_4,r1_22_5,r1_22_6);

Constraint r1_22_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_22_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_22_10 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint r1_22_11 = this.modelKB.arithm(vars[4],"=",8); // frame_color
Constraint r1_22_aggr2 = this.modelKB.or(r1_22_10,r1_22_11);

Constraint r1_22_aggr10 = this.modelKB.and(r1_22_1,r1_22_2,r1_22_aggr1,r1_22_8,r1_22_9,r1_22_aggr2);

Constraint r1_aggr22 = this.modelKB.or(r1_aggr21,r1_22_aggr10);


//|| -> R1.23
// ((((((frame_sku == 24) && (frame_biketype == 4)) && ((((frame_size == 7) ||
// (frame_size == 8)) ||
// (frame_size == 9)) ||
// (frame_size == 10))) && (frame_gender == 2)) && !(frame_internal)) && (frame_color == 12))) 


Constraint r1_23_1 = this.modelKB.arithm(vars[3],"=",24); // frame_sku 
Constraint r1_23_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_23_3 = this.modelKB.arithm(vars[6],"=",7); // frame_size 
Constraint r1_23_4 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_23_5 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_23_6 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_23_aggr1 = this.modelKB.or(r1_23_3,r1_23_4,r1_23_5,r1_23_6);

Constraint r1_23_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_23_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_23_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_23_aggr2 = this.modelKB.or(r1_23_10);

Constraint r1_23_aggr10 = this.modelKB.and(r1_23_1,r1_23_2,r1_23_aggr1,r1_23_8,r1_23_9,r1_23_aggr2);

Constraint r1_aggr23 = this.modelKB.or(r1_aggr22,r1_23_aggr10);


//|| -> R1.24
// ((((((frame_sku == 23) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && (((frame_color == 12) ||
// (frame_color == 3)) ||
// (frame_color == 10)))) 



Constraint r1_24_1 = this.modelKB.arithm(vars[3],"=",23); // frame_sku 
Constraint r1_24_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_24_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_24_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_24_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_24_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_24_aggr1 = this.modelKB.or(r1_24_3,r1_24_4,r1_24_5,r1_24_6);

Constraint r1_24_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_24_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_24_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_24_11 = this.modelKB.arithm(vars[4],"=",3); // frame_color
Constraint r1_24_12 = this.modelKB.arithm(vars[4],"=",10); // frame_color
Constraint r1_24_aggr2 = this.modelKB.or(r1_24_10,r1_24_11,r1_24_12);

Constraint r1_24_aggr10 = this.modelKB.and(r1_24_1,r1_24_2,r1_24_aggr1,r1_24_8,r1_24_9,r1_24_aggr2);

Constraint r1_aggr24 = this.modelKB.or(r1_aggr23,r1_24_aggr10);


//|| -> R1.25
// ((((((frame_sku == 25) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 8))) 

Constraint r1_25_1 = this.modelKB.arithm(vars[3],"=",25); // frame_sku 
Constraint r1_25_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_25_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_25_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_25_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_25_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_25_aggr1 = this.modelKB.or(r1_25_3,r1_25_4,r1_25_5,r1_25_6);

Constraint r1_25_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_25_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_25_10 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
Constraint r1_25_aggr2 = this.modelKB.or(r1_25_10);

Constraint r1_25_aggr10 = this.modelKB.and(r1_25_1,r1_25_2,r1_25_aggr1,r1_25_8,r1_25_9,r1_25_aggr2);

Constraint r1_aggr25 = this.modelKB.or(r1_aggr24,r1_25_aggr10);

//|| -> R1.26
// ((((((frame_sku == 26) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (((((((((frame_color == 1) ||
// (frame_color == 7)) ||
// (frame_color == 14)) ||
// (frame_color == 3)) ||
// (frame_color == 13)) ||
// (frame_color == 11)) ||
// (frame_color == 6)) ||
// (frame_color == 12)) ||
// (frame_color == 8)))) 

Constraint r1_26_1 = this.modelKB.arithm(vars[3],"=",26); // frame_sku 
Constraint r1_26_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_26_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_26_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_26_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_26_aggr1 = this.modelKB.or(r1_26_3,r1_26_4,r1_26_5);

Constraint r1_26_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_26_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_26_10 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint r1_26_11 = this.modelKB.arithm(vars[4],"=",7); // frame_color 
Constraint r1_26_12 = this.modelKB.arithm(vars[4],"=",14); // frame_color 
Constraint r1_26_13 = this.modelKB.arithm(vars[4],"=",3); // frame_color 
Constraint r1_26_14 = this.modelKB.arithm(vars[4],"=",13); // frame_color 
Constraint r1_26_15 = this.modelKB.arithm(vars[4],"=",11); // frame_color 
Constraint r1_26_16 = this.modelKB.arithm(vars[4],"=",6); // frame_color 
Constraint r1_26_17 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_26_18 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
Constraint r1_26_aggr2 = this.modelKB.or(r1_26_10,r1_26_11,r1_26_12,r1_26_13,r1_26_14,r1_26_15,r1_26_16,r1_26_17,r1_26_18);

Constraint r1_26_aggr10 = this.modelKB.and(r1_26_1,r1_26_2,r1_26_aggr1,r1_26_8,r1_26_9,r1_26_aggr2);

Constraint r1_aggr26 = this.modelKB.or(r1_aggr25,r1_26_aggr10);



//|| -> R1.27
// ((((((frame_sku == 27) && (frame_biketype == 4)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13)) ||
// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 1))) 

Constraint r1_27_1 = this.modelKB.arithm(vars[3],"=",27); // frame_sku 
Constraint r1_27_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_27_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_27_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_27_5 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_27_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_27_7 = this.modelKB.arithm(vars[6],"=",14); // frame_size
Constraint r1_27_aggr1 = this.modelKB.or(r1_27_3,r1_27_4,r1_27_5,r1_27_6,r1_27_7);

Constraint r1_27_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_27_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_27_10 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint r1_27_aggr2 = this.modelKB.or(r1_27_10);

Constraint r1_27_aggr10 = this.modelKB.and(r1_27_1,r1_27_2,r1_27_aggr1,r1_27_8,r1_27_9,r1_27_aggr2);

Constraint r1_aggr27 = this.modelKB.or(r1_aggr26,r1_27_aggr10);

//|| -> R1.28
// ((((((frame_sku == 28) && (frame_biketype == 4)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13)) ||
// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 2))) 
Constraint r1_28_1 = this.modelKB.arithm(vars[3],"=",28); // frame_sku 
Constraint r1_28_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_28_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_28_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_28_5 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_28_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_28_7 = this.modelKB.arithm(vars[6],"=",14); // frame_size
Constraint r1_28_aggr1 = this.modelKB.or(r1_28_3,r1_28_4,r1_28_5,r1_28_6,r1_28_7);

Constraint r1_28_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_28_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_28_10 = this.modelKB.arithm(vars[4],"=",2); // frame_color 
Constraint r1_28_aggr2 = this.modelKB.or(r1_28_10);

Constraint r1_28_aggr10 = this.modelKB.and(r1_28_1,r1_28_2,r1_28_aggr1,r1_28_8,r1_28_9,r1_28_aggr2);

Constraint r1_aggr28 = this.modelKB.or(r1_aggr27,r1_28_aggr10);

//|| -> R1.29
// ((((((frame_sku == 29) && (frame_biketype == 4)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13)) ||
// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 1) ||
// (frame_color == 13)))) 

Constraint r1_29_1 = this.modelKB.arithm(vars[3],"=",29); // frame_sku 
Constraint r1_29_2 = this.modelKB.arithm(vars[5],"=",4); // frame_biketype

Constraint r1_29_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_29_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_29_5 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_29_6 = this.modelKB.arithm(vars[6],"=",13); // frame_size
Constraint r1_29_7 = this.modelKB.arithm(vars[6],"=",14); // frame_size
Constraint r1_29_aggr1 = this.modelKB.or(r1_29_3,r1_29_4,r1_29_5,r1_29_6,r1_29_7);

Constraint r1_29_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_29_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_29_10 = this.modelKB.arithm(vars[4],"=",1); // frame_color 
Constraint r1_29_11 = this.modelKB.arithm(vars[4],"=",13); // frame_color 
Constraint r1_29_aggr2 = this.modelKB.or(r1_29_10,r1_29_11);

Constraint r1_29_aggr10 = this.modelKB.and(r1_29_1,r1_29_2,r1_29_aggr1,r1_29_8,r1_29_9,r1_29_aggr2);

Constraint r1_aggr29 = this.modelKB.or(r1_aggr28,r1_29_aggr10);


//|| -> R1.30
// ((((((frame_sku == 30) && (frame_biketype == 3)) && ((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 9))) && ((frame_gender == 1) ||
// (frame_gender == 2))) && frame_internal) && (frame_color == 12))) 


Constraint r1_30_1 = this.modelKB.arithm(vars[3],"=",30); // frame_sku 
Constraint r1_30_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_30_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
Constraint r1_30_4 = this.modelKB.arithm(vars[6],"=",5); // frame_size
Constraint r1_30_5 = this.modelKB.arithm(vars[6],"=",7); // frame_size
Constraint r1_30_6 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_30_aggr1 = this.modelKB.or(r1_30_3,r1_30_4,r1_30_5,r1_30_6);

Constraint r1_30_7 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_30_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_30_aggr3 = this.modelKB.or(r1_30_7,r1_30_8);

Constraint r1_30_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_30_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_30_aggr2 = this.modelKB.or(r1_30_10);

Constraint r1_30_aggr10 = this.modelKB.and(r1_30_1,r1_30_2,r1_30_aggr1,r1_30_aggr3,r1_30_9,r1_30_aggr2);

Constraint r1_aggr30 = this.modelKB.or(r1_aggr29,r1_30_aggr10);




//|| -> R1.31
// ((((((frame_sku == 31) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && (((frame_size == 8) ||
// (frame_size == 9)) ||
// (frame_size == 10))) && (frame_gender == 2)) && frame_internal) && (frame_color == 12))) 

Constraint r1_31_1 = this.modelKB.arithm(vars[3],"=",31); // frame_sku 
Constraint r1_31_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r1_31_21 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype
Constraint r1_31_aggr_type = this.modelKB.or(r1_31_2,r1_31_21);

Constraint r1_31_4 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_31_5 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_31_6 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_31_aggr1 = this.modelKB.or(r1_31_4,r1_31_5,r1_31_6);

Constraint r1_31_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_31_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_31_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_31_aggr2 = this.modelKB.or(r1_31_10);

Constraint r1_31_aggr10 = this.modelKB.and(r1_31_1,r1_31_aggr_type,r1_31_aggr1,r1_31_8,r1_31_9,r1_31_aggr2);

Constraint r1_aggr31 = this.modelKB.or(r1_aggr30,r1_31_aggr10);



//|| -> R1.32
// ((((((frame_sku == 31) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && (((frame_size == 9) ||
// (frame_size == 10)) ||
// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (frame_color == 12))) 


Constraint r1_32_1 = this.modelKB.arithm(vars[3],"=",31); // frame_sku 

Constraint r1_32_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r1_32_21 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype
Constraint r1_32_aggr_type = this.modelKB.or(r1_32_2,r1_32_21);


Constraint r1_32_3 = this.modelKB.arithm(vars[6],"=",3); // frame_size 
Constraint r1_32_4 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_32_5 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_32_6 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_32_aggr1 = this.modelKB.or(r1_32_3,r1_32_4,r1_32_5,r1_32_6);

Constraint r1_32_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_32_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_32_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_32_aggr2 = this.modelKB.or(r1_32_10);

Constraint r1_32_aggr10 = this.modelKB.and(r1_32_1,r1_32_aggr_type,r1_32_aggr1,r1_32_8,r1_32_9,r1_32_aggr2);

Constraint r1_aggr32 = this.modelKB.or(r1_aggr31,r1_32_aggr10);




//|| -> R1.33
// ((((((frame_sku == 32) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && ((((frame_size == 8) ||
// (frame_size == 9)) ||
// (frame_size == 10)) ||
// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (frame_color == 12))) 



Constraint r1_33_1 = this.modelKB.arithm(vars[3],"=",32); // frame_sku 

Constraint r1_33_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r1_33_21 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype
Constraint r1_33_aggr_type = this.modelKB.or(r1_33_2,r1_33_21);


Constraint r1_33_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_33_4 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_33_5 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_33_6 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_33_aggr1 = this.modelKB.or(r1_33_3,r1_33_4,r1_33_5,r1_33_6);

Constraint r1_33_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_33_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_33_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_33_aggr2 = this.modelKB.or(r1_33_10);

Constraint r1_33_aggr10 = this.modelKB.and(r1_33_1,r1_33_aggr_type,r1_33_aggr1,r1_33_8,r1_33_9,r1_33_aggr2);

Constraint r1_aggr33 = this.modelKB.or(r1_aggr32,r1_33_aggr10);


//|| -> R1.34
// ((((((frame_sku == 32) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) 
//&& ((frame_size == 8) ||
// (frame_size == 9))) && (frame_gender == 2)) && frame_internal) && (frame_color == 12))) 



Constraint r1_34_1 = this.modelKB.arithm(vars[3],"=",32); // frame_sku 

Constraint r1_34_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r1_34_21 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype
Constraint r1_34_aggr_type = this.modelKB.or(r1_34_2,r1_34_21);


Constraint r1_34_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_34_4 = this.modelKB.arithm(vars[6],"=",9); // frame_size
Constraint r1_34_aggr1 = this.modelKB.or(r1_34_3,r1_34_4);

Constraint r1_34_8 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_34_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_34_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_34_aggr2 = this.modelKB.or(r1_34_10);

Constraint r1_34_aggr10 = this.modelKB.and(r1_34_1,r1_34_aggr_type,r1_34_aggr1,r1_34_8,r1_34_9,r1_34_aggr2);

Constraint r1_aggr34 = this.modelKB.or(r1_aggr33,r1_34_aggr10);

//|| -> R1.35
// ((((((frame_sku == 33) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12))) && ((frame_gender == 1) ||
// (frame_gender == 2))) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 8)))) 


Constraint r1_35_1 = this.modelKB.arithm(vars[3],"=",33); // frame_sku 

Constraint r1_35_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype

Constraint r1_35_3 = this.modelKB.arithm(vars[6],"=",10); // frame_size 
Constraint r1_35_4 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_35_5 = this.modelKB.arithm(vars[6],"=",12); // frame_size
Constraint r1_35_aggr1 = this.modelKB.or(r1_35_3,r1_35_4,r1_35_5);

Constraint r1_35_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 
Constraint r1_35_81 = this.modelKB.arithm(vars[5],"=",2); // frame_biketype
Constraint r1_35_aggr_gender = this.modelKB.or(r1_35_8,r1_35_81);

Constraint r1_35_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_35_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color 
Constraint r1_35_11 = this.modelKB.arithm(vars[4],"=",8); // frame_color 
Constraint r1_35_aggr2 = this.modelKB.or(r1_35_10,r1_35_11);

Constraint r1_35_aggr10 = this.modelKB.and(r1_35_1,r1_35_2,r1_35_aggr1,r1_35_aggr_gender,r1_35_9,r1_35_aggr2);

Constraint r1_aggr35 = this.modelKB.or(r1_aggr34,r1_35_aggr10);



//|| -> R1.36
// (((((frame_sku == 34) && (frame_biketype == 3)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) && (frame_gender == 1)) ||
// (((frame_size == 8) ||
// (frame_size == 10)) && (frame_gender == 2)))) && !(frame_internal)) && (frame_color == 12))) 



Constraint r1_36_1 = this.modelKB.arithm(vars[3],"=",34); // frame_sku 

Constraint r1_36_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

/////
Constraint r1_36_3 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_36_4 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_36_5 = this.modelKB.arithm(vars[6],"=",11); // frame_size
Constraint r1_36_aggr1 = this.modelKB.or(r1_36_3,r1_36_4,r1_36_5);

Constraint r1_36_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 

Constraint r1_36_aggr1_1 = this.modelKB.and(r1_36_aggr1,r1_36_8);
/////

Constraint r1_36_31 = this.modelKB.arithm(vars[6],"=",8); // frame_size 
Constraint r1_36_41 = this.modelKB.arithm(vars[6],"=",10); // frame_size
Constraint r1_36_aggr11 = this.modelKB.or(r1_36_31,r1_36_41);

Constraint r1_36_81 = this.modelKB.arithm(vars[7],"=",2); // frame_gender 
Constraint r1_36_aggr1_2 = this.modelKB.and(r1_36_aggr11,r1_36_81);
//////
Constraint r1_36_aggr3= this.modelKB.or(r1_36_aggr1_1,r1_36_aggr1_2);


Constraint r1_36_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_36_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color
Constraint r1_36_aggr2 = this.modelKB.or(r1_36_10);

Constraint r1_36_aggr10 = this.modelKB.and(r1_36_1,r1_36_2,r1_36_aggr3,r1_36_9,r1_36_aggr2);

Constraint r1_aggr36 = this.modelKB.or(r1_aggr35,r1_36_aggr10);



//|| -> R1.37
// ((((((frame_sku == 35) && (frame_biketype == 3)) && (((frame_size == 4) ||
// (frame_size == 6)) ||
// (frame_size == 8))) && (frame_gender == 1)) && frame_internal) && (frame_color == 6))) 



Constraint r1_37_1 = this.modelKB.arithm(vars[3],"=",35); // frame_sku 

Constraint r1_37_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_37_3 = this.modelKB.arithm(vars[6],"=",4); // frame_size 
Constraint r1_37_4 = this.modelKB.arithm(vars[6],"=",6); // frame_size
Constraint r1_37_5 = this.modelKB.arithm(vars[6],"=",8); // frame_size
Constraint r1_37_aggr1 = this.modelKB.or(r1_37_3,r1_37_4,r1_37_5);

Constraint r1_37_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 


Constraint r1_37_9 = this.modelKB.arithm(vars[22],"=",1); // frame_internal 

Constraint r1_37_10 = this.modelKB.arithm(vars[4],"=",6); // frame_color
Constraint r1_37_aggr2 = this.modelKB.or(r1_37_10);

Constraint r1_37_aggr10 = this.modelKB.and(r1_37_1,r1_37_2,r1_37_aggr1,r1_37_8,r1_37_9,r1_37_aggr2);

Constraint r1_aggr37 = this.modelKB.or(r1_aggr36,r1_37_aggr10);



// || -> R1.38
// ((((((frame_sku == 36) && (frame_biketype == 3)) && ((frame_size >= 1) && (frame_size <= 11))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 11))) 

Constraint r1_38_1 = this.modelKB.arithm(vars[3],"=",36); // frame_sku 

Constraint r1_38_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_38_3 = this.modelKB.arithm(vars[6],">",0); // frame_size 
Constraint r1_38_4 = this.modelKB.arithm(vars[6],"<",12); // frame_size
Constraint r1_38_aggr1 = this.modelKB.and(r1_38_3,r1_38_4);

Constraint r1_38_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 


Constraint r1_38_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_38_10 = this.modelKB.arithm(vars[4],"=",11); // frame_color
Constraint r1_38_aggr2 = this.modelKB.or(r1_38_10);

Constraint r1_38_aggr10 = this.modelKB.and(r1_38_1,r1_38_2,r1_38_aggr1,r1_38_8,r1_38_9,r1_38_aggr2);

Constraint r1_aggr38 = this.modelKB.or(r1_aggr37,r1_38_aggr10);



//|| -> R1.39
// ((((((frame_sku == 37) && (frame_biketype == 3)) && ((frame_size >= 1) && (frame_size <= 11))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 12)));


Constraint r1_39_1 = this.modelKB.arithm(vars[3],"=",37); // frame_sku 

Constraint r1_39_2 = this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r1_39_3 = this.modelKB.arithm(vars[6],">",0); // frame_size 
Constraint r1_39_4 = this.modelKB.arithm(vars[6],"<",12); // frame_size
Constraint r1_39_aggr1 = this.modelKB.and(r1_39_3,r1_39_4);

Constraint r1_39_8 = this.modelKB.arithm(vars[7],"=",1); // frame_gender 


Constraint r1_39_9 = this.modelKB.arithm(vars[22],"=",0); // frame_internal 

Constraint r1_39_10 = this.modelKB.arithm(vars[4],"=",12); // frame_color
Constraint r1_39_aggr2 = this.modelKB.or(r1_39_10);

Constraint r1_39_aggr10 = this.modelKB.and(r1_39_1,r1_39_2,r1_39_aggr1,r1_39_8,r1_39_9,r1_39_aggr2);

Constraint r1_aggr39 = this.modelKB.or(r1_aggr38,r1_39_aggr10);

this.modelKB.post(r1_aggr39);
		
		
//		R2:DONE
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
			
		
//R3:DONE
//((frame_biketype == 4) >> !(frame_internal));

		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[5],"=",4),
	 			   this.modelKB.arithm(vars[22],"=",0)
		    );
//R4:DONE
//(extra_Carrier >> extra_Mudguard);

		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[23],"=",1),
	 			   this.modelKB.arithm(vars[24],"=",1)
		    );
//R5:DONE
//(extra_Pump && extra_Bottle);
		
		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[26],"=",1),
	 			   this.modelKB.arithm(vars[27],"=",1)
		    );
		
//R6:DONE
//(((((((((((((((((((tires_sku == 16) && (tires_profile == 2)) && (tires_height == 2)) && (tires_width == 2)) ||
		Constraint r6_1= this.modelKB.arithm(vars[8],"=",16); // tires_sku
		Constraint r6_2= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_3= this.modelKB.arithm(vars[9],"=",2); // tires_height
		Constraint r6_4= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_and1 = this.modelKB.and(r6_1,r6_2,r6_3,r6_4);
		
// ((((tires_sku == 4) && ((tires_profile == 5) ||
		// (tires_profile == 9))) && (tires_height == 3)) && ((tires_width == 1) ||
		// (tires_width == 3)))) ||
		Constraint r6_12= this.modelKB.arithm(vars[8],"=",4); // tires_sku
		
		Constraint r6_22= this.modelKB.arithm(vars[11],"=",5); // tires_profile
		Constraint r6_221= this.modelKB.arithm(vars[11],"=",3); // tires_profile
		Constraint r6_22or = this.modelKB.or(r6_22,r6_221);
		
		Constraint r6_32= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_42= this.modelKB.arithm(vars[10],"=",1); // tires_width
		Constraint r6_421= this.modelKB.arithm(vars[10],"=",3); // tires_width
		Constraint r6_42or = this.modelKB.or(r6_42,r6_421);
		
		Constraint r6_and2 = this.modelKB.and(r6_12,r6_22or,r6_32,r6_42or);


// ((((tires_sku == 14) && (((tires_profile == 2) ||
// (tires_profile == 5)) ||
// (tires_profile == 7))) && (tires_height == 3)) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
		
		Constraint r6_13= this.modelKB.arithm(vars[8],"=",14); // tires_sku
		
		Constraint r6_23= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_231= this.modelKB.arithm(vars[11],"=",5); // tires_profile
		Constraint r6_232= this.modelKB.arithm(vars[11],"=",7); // tires_profile
		Constraint r6_23or = this.modelKB.or(r6_23,r6_231,r6_232);
		
		Constraint r6_33= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_43= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_431= this.modelKB.arithm(vars[10],"=",3); // tires_width
		Constraint r6_43or = this.modelKB.or(r6_43,r6_431);
		
		Constraint r6_and3 = this.modelKB.and(r6_13,r6_23or,r6_33,r6_43or);
		
		
// ((((tires_sku == 13) && (((tires_profile == 1) ||
// (tires_profile == 4)) ||
// (tires_profile == 6))) && (tires_height == 3)) && ((tires_width == 1) ||
// (tires_width == 2)))) ||
		
		Constraint r6_14= this.modelKB.arithm(vars[8],"=",13); // tires_sku
		
		Constraint r6_24= this.modelKB.arithm(vars[11],"=",1); // tires_profile
		Constraint r6_241= this.modelKB.arithm(vars[11],"=",4); // tires_profile
		Constraint r6_242= this.modelKB.arithm(vars[11],"=",6); // tires_profile
		Constraint r6_24or = this.modelKB.or(r6_24,r6_241,r6_242);
		
		Constraint r6_34= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_44= this.modelKB.arithm(vars[10],"=",1); // tires_width
		Constraint r6_441= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_44or = this.modelKB.or(r6_44,r6_441);
		
		Constraint r6_and4 = this.modelKB.and(r6_14,r6_24or,r6_34,r6_44or);
		
		
// ((((tires_sku == 7) && ((tires_profile == 10) ||
// (tires_profile == 11))) && (tires_height == 3)) && ((tires_width == 4) ||
// (tires_width == 5)))) ||
		
		
		Constraint r6_15= this.modelKB.arithm(vars[8],"=",7); // tires_sku
		
		Constraint r6_25= this.modelKB.arithm(vars[11],"=",10); // tires_profile
		Constraint r6_251= this.modelKB.arithm(vars[11],"=",11); // tires_profile
		Constraint r6_25or = this.modelKB.or(r6_25,r6_251);
		
		Constraint r6_35= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_45= this.modelKB.arithm(vars[10],"=",4); // tires_width
		Constraint r6_451= this.modelKB.arithm(vars[10],"=",5); // tires_width
		Constraint r6_45or = this.modelKB.or(r6_45,r6_451);
		
		Constraint r6_and5 = this.modelKB.and(r6_15,r6_25or,r6_35,r6_45or);
		
// ((((tires_sku == 2) && ((tires_profile == 2) ||
// (tires_profile == 3))) && (tires_height == 3)) && (tires_width == 2))) ||
		
		
		Constraint r6_16= this.modelKB.arithm(vars[8],"=",2); // tires_sku
		
		Constraint r6_26= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_261= this.modelKB.arithm(vars[11],"=",3); // tires_profile
		Constraint r6_26or = this.modelKB.or(r6_26,r6_261);
		
		Constraint r6_36= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_46= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_and6 = this.modelKB.and(r6_16,r6_26or,r6_36,r6_46);
		
// ((((tires_sku == 15) && (tires_profile == 4)) && (tires_height == 3)) && (tires_width == 1))) ||
		Constraint r6_17= this.modelKB.arithm(vars[8],"=",15); // tires_sku
		
		Constraint r6_27= this.modelKB.arithm(vars[11],"=",4); // tires_profile
		Constraint r6_37= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_47= this.modelKB.arithm(vars[10],"=",1); // tires_width
		Constraint r6_and7 = this.modelKB.and(r6_17,r6_27,r6_37,r6_47);
		
		
// ((((tires_sku == 5) && (tires_profile == 3)) && (tires_height == 3)) && (tires_width == 2))) ||
		
		Constraint r6_18= this.modelKB.arithm(vars[8],"=",5); // tires_sku
		
		Constraint r6_28= this.modelKB.arithm(vars[11],"=",3); // tires_profile
		Constraint r6_38= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_48= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_and8 = this.modelKB.and(r6_18,r6_28,r6_38,r6_48);
		
		
// ((((tires_sku == 1) && ((tires_profile == 2) ||
// (tires_profile == 4))) && (tires_height == 3)) && (tires_width == 3))) ||

		Constraint r6_19= this.modelKB.arithm(vars[8],"=",1); // tires_sku
		
		Constraint r6_29= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_291= this.modelKB.arithm(vars[11],"=",4); // tires_profile
		Constraint r6_29or = this.modelKB.or(r6_29,r6_291);
		
		Constraint r6_39= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_49= this.modelKB.arithm(vars[10],"=",3); // tires_width
		Constraint r6_and9 = this.modelKB.and(r6_19,r6_29or,r6_39,r6_49);
		
// ((((tires_sku == 3) && (tires_profile == 2)) && (tires_height == 2)) && ((tires_width == 3) ||
// (tires_width == 4)))) ||
		
	    Constraint r6_110= this.modelKB.arithm(vars[8],"=",3); // tires_sku
		
		Constraint r6_210= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_2101= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_210or = this.modelKB.or(r6_210,r6_2101);
		
		Constraint r6_310= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_410= this.modelKB.arithm(vars[10],"=",4); // tires_width
		Constraint r6_and10 = this.modelKB.and(r6_110,r6_210or,r6_310,r6_410);
		
		
// ((((tires_sku == 8) && (tires_profile == 2)) && (tires_height == 2)) && ((tires_width == 3) ||
// (tires_width == 2)))) ||
		

	    Constraint r6_111= this.modelKB.arithm(vars[8],"=",8); // tires_sku
		
		Constraint r6_211= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		
		Constraint r6_311= this.modelKB.arithm(vars[9],"=",2); // tires_height
		
		Constraint r6_411= this.modelKB.arithm(vars[10],"=",3); // tires_width
		Constraint r6_4112= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_411or = this.modelKB.or(r6_411,r6_4112);
		
		Constraint r6_and11 = this.modelKB.and(r6_111,r6_211,r6_311,r6_411or);
		
// ((((tires_sku == 9) && ((tires_profile == 2) ||
// (tires_profile == 4))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 2) ||
// (tires_width == 3)))) ||

	    Constraint r6_112= this.modelKB.arithm(vars[8],"=",9); // tires_sku
		
		Constraint r6_212= this.modelKB.arithm(vars[11],"=",2); // tires_profile
		Constraint r6_2122= this.modelKB.arithm(vars[11],"=",4); // tires_profile
		Constraint r6_212or = this.modelKB.or(r6_212,r6_2122);
		
		Constraint r6_312= this.modelKB.arithm(vars[9],"=",3); // tires_height
		
		Constraint r6_412= this.modelKB.arithm(vars[10],"=",2); // tires_width
		Constraint r6_4122= this.modelKB.arithm(vars[10],"=",3); // tires_width
		Constraint r6_412or = this.modelKB.or(r6_412,r6_4122);
		
		Constraint r6_and12 = this.modelKB.and(r6_112,r6_212or,r6_312,r6_412or);
		
		
		
// ((((tires_sku == 10) && ((tires_profile == 4) ||
// (tires_profile == 6))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
		    Constraint r6_113= this.modelKB.arithm(vars[8],"=",10); // tires_sku
			
			Constraint r6_213= this.modelKB.arithm(vars[11],"=",4); // tires_profile
			Constraint r6_2132= this.modelKB.arithm(vars[11],"=",5); // tires_profile
			Constraint r6_213or = this.modelKB.or(r6_213,r6_2132);
			
			Constraint r6_313= this.modelKB.arithm(vars[9],"=",2); // tires_height
			Constraint r6_3132= this.modelKB.arithm(vars[9],"=",3); // tires_height
			Constraint r6_313or = this.modelKB.or(r6_313,r6_3132);
			
			Constraint r6_413= this.modelKB.arithm(vars[10],"=",2); // tires_width
			Constraint r6_4132= this.modelKB.arithm(vars[10],"=",3); // tires_width
			Constraint r6_413or = this.modelKB.or(r6_413,r6_4132);
			
			Constraint r6_and13 = this.modelKB.and(r6_113,r6_213or,r6_313or,r6_413or);
		
		
// ((((tires_sku == 12) && (((((tires_profile == 4) ||
// (tires_profile == 5)) ||
// (tires_profile == 6)) ||
// (tires_profile == 7)) ||
// (tires_profile == 8))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 2) ||
// (tires_width == 3)))) ||
			
			 	Constraint r6_114= this.modelKB.arithm(vars[8],"=",12); // tires_sku
				
				Constraint r6_214= this.modelKB.arithm(vars[11],"=",4); // tires_profile
				Constraint r6_2142= this.modelKB.arithm(vars[11],"=",5); // tires_profile
				Constraint r6_2143= this.modelKB.arithm(vars[11],"=",6); // tires_profile
				Constraint r6_2144= this.modelKB.arithm(vars[11],"=",7); // tires_profile
				Constraint r6_2145= this.modelKB.arithm(vars[11],"=",8); // tires_profile
				Constraint r6_214or = this.modelKB.or(r6_214,r6_2142,r6_2143,r6_2144,r6_2145);
				
				Constraint r6_314= this.modelKB.arithm(vars[9],"=",2); // tires_height
				Constraint r6_3142= this.modelKB.arithm(vars[9],"=",3); // tires_height
				Constraint r6_314or = this.modelKB.or(r6_313,r6_3132);
				
				Constraint r6_414= this.modelKB.arithm(vars[10],"=",2); // tires_width
				Constraint r6_4142= this.modelKB.arithm(vars[10],"=",3); // tires_width
				Constraint r6_414or = this.modelKB.or(r6_413,r6_4132);
				
				Constraint r6_and14 = this.modelKB.and(r6_114,r6_214or,r6_314or,r6_414or);
		
// ((((tires_sku == 11) && ((tires_profile == 2) ||
// (tires_profile == 1))) && ((tires_height == 2) ||
// (tires_height == 3))) && ((tires_width == 1) ||
// (tires_width == 3)))) ||
				
				 Constraint r6_115= this.modelKB.arithm(vars[8],"=",11); // tires_sku
					
					Constraint r6_215= this.modelKB.arithm(vars[11],"=",2); // tires_profile
					Constraint r6_2152= this.modelKB.arithm(vars[11],"=",1); // tires_profile
					Constraint r6_215or = this.modelKB.or(r6_215,r6_2152);
					
					Constraint r6_315= this.modelKB.arithm(vars[9],"=",2); // tires_height
					Constraint r6_3152= this.modelKB.arithm(vars[9],"=",3); // tires_height
					Constraint r6_315or = this.modelKB.or(r6_315,r6_3152);
					
					Constraint r6_415= this.modelKB.arithm(vars[10],"=",1); // tires_width
					Constraint r6_4152= this.modelKB.arithm(vars[10],"=",3); // tires_width
					Constraint r6_415or = this.modelKB.or(r6_415,r6_4152);
					
					Constraint r6_and15 = this.modelKB.and(r6_115,r6_215or,r6_315or,r6_415or);
		
		
// ((((tires_sku == 6) && (tires_profile == 10)) && (tires_height == 1)) && ((tires_width == 3) ||
// (tires_width == 4))));
//
					 Constraint r6_116= this.modelKB.arithm(vars[8],"=",6); // tires_sku
						
						Constraint r6_216= this.modelKB.arithm(vars[11],"=",10); // tires_profile
						
						Constraint r6_316= this.modelKB.arithm(vars[9],"=",1); // tires_height
						Constraint r6_416= this.modelKB.arithm(vars[10],"=",3); // tires_width
						Constraint r6_and16 = this.modelKB.and(r6_116,r6_216,r6_316,r6_416);
			
	
		
		Constraint r6_or= this.modelKB.or(r6_and1,r6_and2,r6_and3,r6_and4,r6_and5,r6_and6,r6_and7,r6_and8,r6_and9,r6_and10,r6_and11,r6_and12,r6_and13,r6_and14,r6_and15,r6_and16);
		
		this.modelKB.post(r6_or);
		
		
//R7: DONE
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
		
		Constraint r7_1= this.modelKB.arithm(vars[12],"=",9); // rims_sku
		Constraint r7_2= this.modelKB.arithm(vars[13],"=",3); // rims_height
		Constraint r7_3= this.modelKB.arithm(vars[14],"=",1); // rims_width
		Constraint r7_and1 = this.modelKB.and(r7_1,r7_2,r7_3);
		
		
		Constraint r7_12= this.modelKB.arithm(vars[12],"=",11); // rims_sku
		Constraint r7_22= this.modelKB.arithm(vars[13],"=",3); // rims_height
		Constraint r7_32= this.modelKB.arithm(vars[14],"=",2); // rims_width
		Constraint r7_and2 = this.modelKB.and(r7_12,r7_22,r7_32);
		
		Constraint r7_13= this.modelKB.arithm(vars[12],"=",7); // rims_sku
		Constraint r7_23= this.modelKB.arithm(vars[13],"=",3); // rims_height
		Constraint r7_33= this.modelKB.arithm(vars[14],"=",3); // rims_width
		Constraint r7_and3 = this.modelKB.and(r7_13,r7_23,r7_33);
		
		Constraint r7_14= this.modelKB.arithm(vars[12],"=",10); // rims_sku
		Constraint r7_24= this.modelKB.arithm(vars[13],"=",3); // rims_height
		Constraint r7_34= this.modelKB.arithm(vars[14],"=",2); // rims_width
		Constraint r7_and4 = this.modelKB.and(r7_14,r7_24,r7_34);
		
		Constraint r7_15= this.modelKB.arithm(vars[12],"=",12); // rims_sku
		Constraint r7_25= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r7_35= this.modelKB.arithm(vars[14],"=",4); // rims_width
		Constraint r7_and5 = this.modelKB.and(r7_15,r7_25,r7_35);
		
		Constraint r7_16= this.modelKB.arithm(vars[12],"=",5); // rims_sku
		Constraint r7_26= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r7_36= this.modelKB.arithm(vars[14],"=",3); // rims_width
		Constraint r7_and6 = this.modelKB.and(r7_16,r7_26,r7_36);
		
		Constraint r7_17= this.modelKB.arithm(vars[12],"=",6); // rims_sku
		Constraint r7_27= this.modelKB.arithm(vars[13],"=",1); // rims_height
		Constraint r7_37= this.modelKB.arithm(vars[14],"=",4); // rims_width
		Constraint r7_and7 = this.modelKB.and(r7_17,r7_27,r7_37);
		
		Constraint r7_18= this.modelKB.arithm(vars[12],"=",2); // rims_sku
		Constraint r7_28= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r7_38= this.modelKB.arithm(vars[14],"=",2); // rims_width
		Constraint r7_and8 = this.modelKB.and(r7_18,r7_28,r7_38);
		
		Constraint r7_19= this.modelKB.arithm(vars[12],"=",4); // rims_sku
		Constraint r7_29= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r7_39= this.modelKB.arithm(vars[14],"=",1); // rims_width
		Constraint r7_and9 = this.modelKB.and(r7_19,r7_29,r7_39);
	
		Constraint r7_110= this.modelKB.arithm(vars[12],"=",3); // rims_sku
		Constraint r7_210= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r7_310= this.modelKB.arithm(vars[14],"=",1); // rims_width
		Constraint r7_and10 = this.modelKB.and(r7_110,r7_210,r7_310);
		
		Constraint r7_111= this.modelKB.arithm(vars[12],"=",1); // rims_sku
		Constraint r7_211= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r7_311= this.modelKB.arithm(vars[14],"=",2); // rims_width
		Constraint r7_and11 = this.modelKB.and(r7_111,r7_211,r7_311);
		
		Constraint r7_112= this.modelKB.arithm(vars[12],"=",8); // rims_sku
		Constraint r7_212= this.modelKB.arithm(vars[13],"=",1); // rims_height
		Constraint r7_312= this.modelKB.arithm(vars[14],"=",3); // rims_width
		Constraint r7_and12 = this.modelKB.and(r7_112,r7_212,r7_312);
		
		
		Constraint r7_or= this.modelKB.or(r7_and1,r7_and2,r7_and3,r7_and4,r7_and5,r7_and6,r7_and7,r7_and8,r7_and9,r7_and10,r7_and11,r7_and12);
		
		this.modelKB.post(r7_or);
		
		
//R8: DONE
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
// (gear_gears == 5)))) 
//
//||
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
// (gear_gears == 4)))) 
//
//||
//
// (((gear_sku == 6) && (gear_internal == 1)) && (gear_gears == 1)));
	
		Constraint r8_1= this.modelKB.arithm(vars[15],"=",5); // gear_sku
		Constraint r8_2= this.modelKB.arithm(vars[17],"=",5); // gear_biketype
		Constraint r8_3= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_4= this.modelKB.arithm(vars[16],"=",7); // gear_gears
		Constraint r8_and1 = this.modelKB.and(r8_1,r8_2,r8_3,r8_4);
		
		Constraint r8_12= this.modelKB.arithm(vars[15],"=",15); // gear_sku
		Constraint r8_22= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_32= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_42= this.modelKB.arithm(vars[16],"=",6); // gear_gears
		Constraint r8_and2 = this.modelKB.and(r8_12,r8_22,r8_32,r8_42);
		
		Constraint r8_13= this.modelKB.arithm(vars[15],"=",13); // gear_sku
		Constraint r8_23= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_33= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_43= this.modelKB.arithm(vars[16],"=",6); // gear_gears
		Constraint r8_and3 = this.modelKB.and(r8_13,r8_23,r8_33,r8_43);
		
		Constraint r8_14= this.modelKB.arithm(vars[15],"=",12); // gear_sku
		Constraint r8_24= this.modelKB.arithm(vars[17],"=",3); // gear_biketype
		Constraint r8_34= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_44= this.modelKB.arithm(vars[16],"=",8); // gear_gears
		Constraint r8_and4 = this.modelKB.and(r8_14,r8_24,r8_34,r8_44);

		Constraint r8_15= this.modelKB.arithm(vars[15],"=",1); // gear_sku
		Constraint r8_25= this.modelKB.arithm(vars[17],"=",3); // gear_biketype
		Constraint r8_35= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_45= this.modelKB.arithm(vars[16],"=",9); // gear_gears
		Constraint r8_and5 = this.modelKB.and(r8_15,r8_25,r8_35,r8_45);
	
		
		
		Constraint r8_16= this.modelKB.arithm(vars[15],"=",10); // gear_sku
		
		Constraint r8_26= this.modelKB.arithm(vars[17],"=",1); // gear_biketype
		Constraint r8_262= this.modelKB.arithm(vars[17],"=",2); // gear_biketype
		Constraint r8_263= this.modelKB.arithm(vars[17],"=",3); // gear_biketype
		Constraint r8_and61 = this.modelKB.and(r8_26,r8_262,r8_263);
		
		Constraint r8_36= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		
		Constraint r8_46= this.modelKB.arithm(vars[16],"=",2); // gear_gears
		Constraint r8_461= this.modelKB.arithm(vars[16],"=",3); // gear_gears
		Constraint r8_462= this.modelKB.arithm(vars[16],"=",5); // gear_gears
		Constraint r8_and62 = this.modelKB.and(r8_46,r8_461,r8_462);
		
		Constraint r8_and6 = this.modelKB.and(r8_16,r8_and61,r8_36,r8_and62);
		
		
		Constraint r8_17= this.modelKB.arithm(vars[15],"=",11); // gear_sku
		Constraint r8_27= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_37= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_47= this.modelKB.arithm(vars[16],"=",6); // gear_gears
		Constraint r8_and7 = this.modelKB.and(r8_17,r8_27,r8_37,r8_47);
		
		
		Constraint r8_18= this.modelKB.arithm(vars[15],"=",7); // gear_sku
		Constraint r8_28= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_38= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_48= this.modelKB.arithm(vars[16],"=",7); // gear_gears
		Constraint r8_and8 = this.modelKB.and(r8_18,r8_28,r8_38,r8_48);
		
		
		Constraint r8_19= this.modelKB.arithm(vars[15],"=",2); // gear_sku
		Constraint r8_29= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_39= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_49= this.modelKB.arithm(vars[16],"=",6); // gear_gears
		Constraint r8_and9 = this.modelKB.and(r8_19,r8_29,r8_39,r8_49);
		
		Constraint r8_110= this.modelKB.arithm(vars[15],"=",3); // gear_sku
		Constraint r8_210= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_310= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_410= this.modelKB.arithm(vars[16],"=",7); // gear_gears
		Constraint r8_and10 = this.modelKB.and(r8_110,r8_210,r8_310,r8_410);
		
		
		Constraint r8_111= this.modelKB.arithm(vars[15],"=",4); // gear_sku
		Constraint r8_211= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		Constraint r8_311= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_411= this.modelKB.arithm(vars[16],"=",7); // gear_gears
		Constraint r8_and11 = this.modelKB.and(r8_111,r8_211,r8_311,r8_411);
		
		Constraint r8_112= this.modelKB.arithm(vars[15],"=",9); // gear_sku
		Constraint r8_212= this.modelKB.arithm(vars[17],"=",3); // gear_biketype
		Constraint r8_312= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_412= this.modelKB.arithm(vars[16],"=",10); // gear_gears
		Constraint r8_and12 = this.modelKB.and(r8_112,r8_212,r8_312,r8_412);
		
		Constraint r8_113= this.modelKB.arithm(vars[15],"=",8); // gear_sku
		Constraint r8_213= this.modelKB.arithm(vars[17],"=",3); // gear_biketype
		Constraint r8_313= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		Constraint r8_413= this.modelKB.arithm(vars[16],"=",9); // gear_gears
		Constraint r8_and13 = this.modelKB.and(r8_113,r8_213,r8_313,r8_413);
		
	
		
		Constraint r8_114= this.modelKB.arithm(vars[15],"=",14); // gear_sku
		Constraint r8_214= this.modelKB.arithm(vars[17],"=",1); // gear_biketype
		Constraint r8_2141= this.modelKB.arithm(vars[17],"=",2); // gear_biketype
		Constraint r8_214_or = this.modelKB.or(r8_214,r8_2141);
		
		Constraint r8_314= this.modelKB.arithm(vars[33],"=",1); // gear_internal
		Constraint r8_414= this.modelKB.arithm(vars[16],"=",2); // gear_gears
		Constraint r8_4141= this.modelKB.arithm(vars[16],"=",4); // gear_gears
		Constraint r8_414_or = this.modelKB.or(r8_414,r8_4141);
		
		
		Constraint r8_and14 = this.modelKB.and(r8_114,r8_214_or,r8_314,r8_414_or);
		
		// (((gear_sku == 6) && (gear_internal == 1)) && (gear_gears == 1)));
		
		Constraint r8_115= this.modelKB.arithm(vars[15],"=",6); // gear_sku
		Constraint r8_315= this.modelKB.arithm(vars[33],"=",1); // gear_internal
		Constraint r8_415= this.modelKB.arithm(vars[16],"=",1); // gear_gears
		Constraint r8_and15 = this.modelKB.and(r8_115,r8_315,r8_415);
		
		Constraint r8_or= this.modelKB.or(r8_and1,r8_and2,r8_and3,r8_and4,r8_and5,r8_and6,r8_and7,r8_and8,r8_and9,r8_and10,r8_and11,r8_and12,r8_and13,r8_and14,r8_and15);
		
		this.modelKB.post(r8_or);
		
		
//R32: DONE
//((gear_biketype == 4) >> (gear_internal == 0));
//
		Constraint r32_1= this.modelKB.arithm(vars[17],"=",4); // gear_biketype
		
		Constraint r32_2= this.modelKB.arithm(vars[33],"=",0); // gear_internal
		this.modelKB.ifThen(
				r32_1,
				r32_2
			);	
		
//R9: DONE
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
		
		Constraint r9_0= this.modelKB.arithm(vars[18],"=",5); // pedals_sku
		Constraint r9_1= this.modelKB.arithm(vars[18],"=",4); // pedals_sku
		Constraint r9_2= this.modelKB.arithm(vars[18],"=",9); // pedals_sku
		Constraint r9_3= this.modelKB.arithm(vars[18],"=",8); // pedals_sku
		Constraint r9_4= this.modelKB.arithm(vars[18],"=",3); // pedals_sku
		Constraint r9_5= this.modelKB.arithm(vars[18],"=",2); // pedals_sku
		Constraint r9_6= this.modelKB.arithm(vars[18],"=",7); // pedals_sku
		Constraint r9_7= this.modelKB.arithm(vars[18],"=",1); // pedals_sku
		Constraint r9_8= this.modelKB.arithm(vars[18],"=",6); // pedals_sku
		
		Constraint r9_11= this.modelKB.arithm(vars[19],"=",1); // pedals_pedaltype
		Constraint r9_12= this.modelKB.arithm(vars[19],"=",2); // pedals_pedaltype
		Constraint r9_13= this.modelKB.arithm(vars[19],"=",3); // pedals_pedaltype
		 
		
		Constraint r9_and1 = this.modelKB.and(r9_0,r9_12);
		Constraint r9_and2 = this.modelKB.and(r9_1,r9_12);
		Constraint r9_and3 = this.modelKB.and(r9_2,r9_13);
		Constraint r9_and4 = this.modelKB.and(r9_3,r9_13);
		Constraint r9_and5 = this.modelKB.and(r9_4,r9_12);
		Constraint r9_and6 = this.modelKB.and(r9_5,r9_12);
		Constraint r9_and7 = this.modelKB.and(r9_6,r9_11);
		Constraint r9_and8 = this.modelKB.and(r9_7,r9_11);
		Constraint r9_and9 = this.modelKB.and(r9_8,r9_11);

		Constraint r9_or = this.modelKB.or(r9_and1,r9_and2,r9_and3,r9_and4,r9_and5,r9_and6,r9_and7,r9_and8,r9_and9);				
		
		this.modelKB.post(r9_or);
		
		
//R10: DONE
//((((((shoes_sku == 5) && (shoes_pedaltype == 2)) ||
// ((shoes_sku == 4) && (shoes_pedaltype == 3))) ||
// ((shoes_sku == 3) && (shoes_pedaltype == 2))) ||
// ((shoes_sku == 2) && (shoes_pedaltype == 3))) ||
// (shoes_sku == 1));
//
		
		Constraint r10_0= this.modelKB.arithm(vars[18],"=",5); // pedals_sku
		Constraint r10_1= this.modelKB.arithm(vars[18],"=",4); // pedals_sku
		Constraint r10_2= this.modelKB.arithm(vars[18],"=",3); // pedals_sku
		Constraint r10_3= this.modelKB.arithm(vars[18],"=",2); // pedals_sku
		Constraint r10_4= this.modelKB.arithm(vars[18],"=",1); // pedals_sku
		
		Constraint r10_22= this.modelKB.arithm(vars[21],"=",2); // pedals_pedaltype
		Constraint r10_23= this.modelKB.arithm(vars[21],"=",3); // pedals_pedaltype
		 
		
		Constraint r10_and1 = this.modelKB.and(r10_0,r10_22);
		Constraint r10_and2 = this.modelKB.and(r10_1,r10_23);
		Constraint r10_and3 = this.modelKB.and(r10_2,r10_22);
		Constraint r10_and4 = this.modelKB.and(r10_3,r10_23);

		Constraint r10_or = this.modelKB.or(r10_and1,r10_and2,r10_and3,r10_and4,r10_4);				
		
		this.modelKB.post(r10_or);
		
		
		
		
//R11: DONE
//(((rims_height == 1) >> ((frame_size >= 3) && (frame_size <= 10))) &&
//((rims_height == 2) >> ((frame_size >= 5) && (frame_size <= 14))) &&
//((rims_height == 3) >> ((frame_size >= 7) && (frame_size <= 14))) &&
//((!((rims_height == 1)) &&
//!((rims_height == 2)) &&
//!((rims_height == 3))) >> 1));
//
		
	
		
		Constraint r11_1= this.modelKB.arithm(vars[13],"=",1); // rims_height
		Constraint r11_2= this.modelKB.arithm(vars[13],"=",2); // rims_height
		Constraint r11_3= this.modelKB.arithm(vars[13],"=",3); // rims_height

		Constraint r11_4= this.modelKB.arithm(vars[6],">",2); // frame_size
		Constraint r11_5= this.modelKB.arithm(vars[6],"<",11); // frame_size
		Constraint r11_45 = this.modelKB.and(r11_4,r11_5);	
		 
		this.modelKB.ifThen(
				r11_1,
				r11_45
			);

		Constraint r11_6= this.modelKB.arithm(vars[6],">",4); // frame_size
		Constraint r11_7= this.modelKB.arithm(vars[6],"<",15); // frame_size
		Constraint r11_67 = this.modelKB.and(r11_6,r11_7);	
		
		this.modelKB.ifThen(
				r11_2,
				r11_67
			);
		
		Constraint r11_8= this.modelKB.arithm(vars[6],">",6); // frame_size
		Constraint r11_9= this.modelKB.arithm(vars[6],"<",15); // frame_size
		Constraint r11_89 = this.modelKB.and(r11_8,r11_9);	
		
		this.modelKB.ifThen(
				r11_3,
				r11_89
			);
		 
		
		
//R12: DONE
//(((frame_biketype == 4) >> ((((tires_profile >= 1) && (tires_profile <= 7)) && !(extra_Mudguard)) && !(extra_Basket))) &&
//((frame_biketype == 3) >> (((tires_profile >= 8) && !(extra_Carrier)) && !(extra_Basket))) &&
//((frame_biketype == 1) >> ((tires_profile >= 4) && (tires_profile <= 10))) &&
//((frame_biketype == 2) >> (((((tires_profile >= 6) && (tires_profile <= 10)) && extra_Carrier) && extra_Propstand) && extra_Basket)) &&
//((!((frame_biketype == 4)) &&
//!((frame_biketype == 3)) &&
//!((frame_biketype == 1)) &&
//!((frame_biketype == 2))) >> 1));
//
		
		
Constraint r12_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r12_2= this.modelKB.arithm(vars[5],"=",3); // frame_biketype
Constraint r12_3= this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r12_4= this.modelKB.arithm(vars[5],"=",2); // frame_biketype

Constraint r12_5= this.modelKB.arithm(vars[11],">",0); // tires_profile
Constraint r12_6= this.modelKB.arithm(vars[11],"<",8); // tires_profile
Constraint r12_56 = this.modelKB.and(r12_5,r12_6);	

Constraint r12_7= this.modelKB.arithm(vars[24],"=",0); // extra_Mudguard
Constraint r12_8= this.modelKB.arithm(vars[28],"=",0); // extra_Basket

Constraint r12_1_then = this.modelKB.and(r12_56,r12_7,r12_8);	
 
this.modelKB.ifThen(
		r12_1,
		r12_1_then
	);

Constraint r12_51= this.modelKB.arithm(vars[11],">",7); // tires_profile

Constraint r12_71= this.modelKB.arithm(vars[23],"=",0); // extra_Carrier
Constraint r12_81= this.modelKB.arithm(vars[28],"=",0); // extra_Basket

Constraint r12_2_then = this.modelKB.and(r12_51,r12_71,r12_81);	
 
this.modelKB.ifThen(
		r12_2,
		r12_2_then
	);

Constraint r12_52= this.modelKB.arithm(vars[11],">",3); // tires_profile
Constraint r12_62= this.modelKB.arithm(vars[11],"<",11); // tires_profile
Constraint r12_562 = this.modelKB.and(r12_52,r12_62);	

this.modelKB.ifThen(
		r12_3,
		r12_562
	);

Constraint r12_53= this.modelKB.arithm(vars[11],">",5); // tires_profile
Constraint r12_63= this.modelKB.arithm(vars[11],"<",11); // tires_profile
Constraint r12_563 = this.modelKB.and(r12_53,r12_63);	

Constraint r12_73= this.modelKB.arithm(vars[23],"=",1); // extra_Carrier
Constraint r12_83= this.modelKB.arithm(vars[28],"=",1); // extra_Basket
Constraint r12_93= this.modelKB.arithm(vars[32],"=",1); // extra_Propstand

Constraint r12_4_then = this.modelKB.and(r12_563,r12_73,r12_83,r12_93);	
 
this.modelKB.ifThen(
		r12_4,
		r12_4_then
	);

		
//R13:DONE
//(!(frame_internal) >> !(extra_Propstand));
		this.modelKB.ifThen(
	 			   this.modelKB.arithm(vars[22],"=",0),
	 			   this.modelKB.arithm(vars[32],"=",0)
		    );
//R14:DONE
//(rims_height == tires_height);
		this.modelKB.arithm(vars[13],"=",vars[9]).post();
//R15:DONE
//(rims_width == tires_width);
		this.modelKB.arithm(vars[14],"=",vars[10]).post();
//R16:DONE
//(frame_internal == gear_internal);
		this.modelKB.arithm(vars[22],"=",vars[33]).post();
//R17:DONE
//(pedals_pedaltype == shoes_pedaltype);
		this.modelKB.arithm(vars[19],"=",vars[21]).post();
//R18:DONE
//(frame_biketype == gear_biketype);
		this.modelKB.arithm(vars[5],"=",vars[17]).post();
//R19:DONE
//(person_gender == frame_gender);
		this.modelKB.arithm(vars[0],"=",vars[7]).post();
//R20:DONE
//(person_biketype == frame_biketype);
		this.modelKB.arithm(vars[2],"=",vars[5]).post();
		
		
		
//R21:DONE
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 1)) >> ((frame_size >= 5) && (frame_size <= 8)));


Constraint r21_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r21_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r21_aggr_type = this.modelKB.or(r21_1,r21_2);

Constraint r21_5 = this.modelKB.arithm(vars[1],"=",1); // person_height

Constraint r21_aggr_if = this.modelKB.and(r21_aggr_type,r21_5);



Constraint r21_3 = this.modelKB.arithm(vars[6],">",4); // frame_size 
Constraint r21_4 = this.modelKB.arithm(vars[6],"<",9); // frame_size
Constraint r21_aggr_size = this.modelKB.and(r21_3,r21_4);


this.modelKB.ifThen(
		r21_aggr_if,
		r21_aggr_size
 );
		
//R22:DONE
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 2)) >> ((frame_size >= 7) && (frame_size <= 10)));
		
		
Constraint r22_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r22_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r22_aggr_type = this.modelKB.or(r22_1,r22_2);

Constraint r22_5 = this.modelKB.arithm(vars[1],"=",2); // person_height

Constraint r22_aggr_if = this.modelKB.and(r22_aggr_type,r22_5);



Constraint r22_3 = this.modelKB.arithm(vars[6],">",6); // frame_size 
Constraint r22_4 = this.modelKB.arithm(vars[6],"<",11); // frame_size
Constraint r22_aggr_size = this.modelKB.and(r22_3,r22_4);


this.modelKB.ifThen(
		r22_aggr_if,
		r22_aggr_size
 );
		
		
///R23:DONE
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 3)) >> ((frame_size >= 9) && (frame_size <= 12)));

Constraint r23_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r23_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r23_aggr_type = this.modelKB.or(r23_1,r23_2);

Constraint r23_5 = this.modelKB.arithm(vars[1],"=",3); // person_height

Constraint r23_aggr_if = this.modelKB.and(r23_aggr_type,r23_5);



Constraint r23_3 = this.modelKB.arithm(vars[6],">",8); // frame_size 
Constraint r23_4 = this.modelKB.arithm(vars[6],"<",13); // frame_size
Constraint r23_aggr_size = this.modelKB.and(r23_3,r23_4);


this.modelKB.ifThen(
		r23_aggr_if,
		r23_aggr_size
 );
		
		
		
		
//R24:DONE
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 4)) >> ((frame_size >= 11) && (frame_size <= 14)));

Constraint r24_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r24_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r24_aggr_type = this.modelKB.or(r24_1,r24_2);

Constraint r24_5 = this.modelKB.arithm(vars[1],"=",4); // person_height

Constraint r24_aggr_if = this.modelKB.and(r24_aggr_type,r24_5);



Constraint r24_3 = this.modelKB.arithm(vars[6],">",10); // frame_size 
Constraint r24_4 = this.modelKB.arithm(vars[6],"<",15); // frame_size
Constraint r24_aggr_size = this.modelKB.and(r24_3,r24_4);


this.modelKB.ifThen(
		r24_aggr_if,
		r24_aggr_size
 );
		
		
		
		
//R25:DONE
//((((frame_biketype == 4) ||
// (frame_biketype == 1)) && (person_height == 5)) >> ((frame_size >= 13) && (frame_size <= 14)));
		
Constraint r25_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r25_2 = this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r25_aggr_type = this.modelKB.or(r25_1,r25_2);

Constraint r25_5 = this.modelKB.arithm(vars[1],"=",5); // person_height

Constraint r25_aggr_if = this.modelKB.and(r25_aggr_type,r25_5);



Constraint r25_3 = this.modelKB.arithm(vars[6],">",12); // frame_size 
Constraint r25_4 = this.modelKB.arithm(vars[6],"<",15); // frame_size
Constraint r25_aggr_size = this.modelKB.and(r25_3,r25_4);


this.modelKB.ifThen(
		r25_aggr_if,
		r25_aggr_size
 );
		
		
//R26:DONE
//(((frame_biketype == 3) && (person_height == 1)) >> ((frame_size >= 3) && (frame_size <= 6)));
		
Constraint r26_1= this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r26_5 = this.modelKB.arithm(vars[1],"=",1); // person_height

Constraint r26_aggr_if = this.modelKB.and(r26_1,r26_5);



Constraint r26_3 = this.modelKB.arithm(vars[6],">",2); // frame_size 
Constraint r26_4 = this.modelKB.arithm(vars[6],"<",7); // frame_size
Constraint r26_aggr_size = this.modelKB.and(r26_3,r26_4);


this.modelKB.ifThen(
		r26_aggr_if,
		r26_aggr_size
 );
			
		
//R27:DONE
//(((frame_biketype == 3) && (person_height == 2)) >> ((frame_size >= 4) && (frame_size <= 7)));
Constraint r27_1= this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r27_5 = this.modelKB.arithm(vars[1],"=",2); // person_height

Constraint r27_aggr_if = this.modelKB.and(r27_1,r27_5);



Constraint r27_3 = this.modelKB.arithm(vars[6],">",3); // frame_size 
Constraint r27_4 = this.modelKB.arithm(vars[6],"<",8); // frame_size
Constraint r27_aggr_size = this.modelKB.and(r27_3,r27_4);


this.modelKB.ifThen(
		r27_aggr_if,
		r27_aggr_size
 );
		
		
		
//R28:DONE
//(((frame_biketype == 3) && (person_height == 3)) >> ((frame_size >= 5) && (frame_size <= 8)));
		
		
Constraint r28_1= this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r28_5 = this.modelKB.arithm(vars[1],"=",3); // person_height

Constraint r28_aggr_if = this.modelKB.and(r28_1,r28_5);



Constraint r28_3 = this.modelKB.arithm(vars[6],">",4); // frame_size 
Constraint r28_4 = this.modelKB.arithm(vars[6],"<",9); // frame_size
Constraint r28_aggr_size = this.modelKB.and(r28_3,r28_4);


this.modelKB.ifThen(
		r28_aggr_if,
		r28_aggr_size
 );
		
		
		
		
//R29:DONE
//(((frame_biketype == 3) && ((person_height == 4) ||
// (person_height == 5))) >> ((frame_size >= 6) && (frame_size <= 10)));
//
		

Constraint r29_1= this.modelKB.arithm(vars[5],"=",3); // frame_biketype

Constraint r29_5 = this.modelKB.arithm(vars[1],"=",4); // person_height

Constraint r29_aggr_if = this.modelKB.and(r29_1,r29_5);



Constraint r29_3 = this.modelKB.arithm(vars[6],">",5); // frame_size 
Constraint r29_4 = this.modelKB.arithm(vars[6],"<",11); // frame_size
Constraint r29_aggr_size = this.modelKB.and(r29_3,r29_4);


this.modelKB.ifThen(
r29_aggr_if,
r29_aggr_size
);
		
//R30: DONE
//((((((tires_sku == 8) ||
// (tires_sku == 9)) ||
// (tires_sku == 12)) ||
// (tires_sku == 10)) ||
// (tires_sku == 11)) >> !(extra_Sidereflex));
//
		


Constraint r30_1= this.modelKB.arithm(vars[8],"=",8); // tires_sku
Constraint r30_2= this.modelKB.arithm(vars[8],"=",9); // tires_sku
Constraint r30_3= this.modelKB.arithm(vars[8],"=",12); // tires_sku
Constraint r30_4= this.modelKB.arithm(vars[8],"=",10); // tires_sku
Constraint r30_5= this.modelKB.arithm(vars[8],"=",11); // tires_sku

Constraint r30_aggr_if = this.modelKB.or(r30_1,r30_2,r30_3,r30_4,r30_5);



Constraint r30_6 = this.modelKB.arithm(vars[30],"=",0); // extra_Sidereflex 

this.modelKB.ifThen(
r30_aggr_if,
r30_6
);

		
		
//R31:DONE
//(
//((frame_biketype == 4) >> (pedals_pedaltype != 1)) &&
//((frame_biketype == 3) >> (pedals_pedaltype != 1)) &&
//((frame_biketype == 1) >> (pedals_pedaltype == 1)) &&
//((frame_biketype == 2) >> (pedals_pedaltype == 1)) &&
//(
//(!((frame_biketype == 4)) &&
//!((frame_biketype == 3)) &&
//!((frame_biketype == 1)) &&
//!((frame_biketype == 2))) 
//>> 1));
		
		
Constraint r31_1= this.modelKB.arithm(vars[5],"=",4); // frame_biketype
Constraint r31_2= this.modelKB.arithm(vars[5],"=",3); // frame_biketype
Constraint r31_3= this.modelKB.arithm(vars[5],"=",1); // frame_biketype
Constraint r31_4= this.modelKB.arithm(vars[5],"=",2); // frame_biketype

Constraint r31_5= this.modelKB.arithm(vars[19],"!=",1); // pedals_pedaltype
Constraint r31_6= this.modelKB.arithm(vars[19],"!=",1); // pedals_pedaltype
Constraint r31_7= this.modelKB.arithm(vars[19],"=",1); // pedals_pedaltype
Constraint r31_8= this.modelKB.arithm(vars[19],"=",1); // pedals_pedaltype
 

this.modelKB.ifThen(
		r31_1,
		r31_5
	);
this.modelKB.ifThen(
		r31_2,
		r31_6
	);

this.modelKB.ifThen(
		r31_3,
		r31_7
	);
this.modelKB.ifThen(
		r31_4,
		r31_8
	);

		
	
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
