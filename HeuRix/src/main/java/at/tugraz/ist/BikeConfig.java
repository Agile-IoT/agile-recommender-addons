package at.tugraz.ist;

import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandom;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

// https://www.itu.dk/research/cla/externals/clib/bike2.cp

public class BikeConfig {
	public Model bikeModel = new Model("Bike Configuration Problem");
	int numberOfVariables = 34;
	int [] domainSizes= new int[numberOfVariables];
	HashMap<Integer,Integer> hashmapIDs = new HashMap<Integer,Integer>();  
	int[] lowBoundaries = new int[numberOfVariables];  
	public IntVar[] vars = new IntVar[numberOfVariables];
	int [][] valueOrdering = new int [numberOfVariables][];
	
	
	BikeConfig(){
		// 34 variables
    	defineVariables();
    	// 31 constraints
    	defineConstraints();
	}
	
	public void defineVariables (){
    
        vars[0] = bikeModel.intVar("person_gender", 0, 2);
        vars[1] = bikeModel.intVar("person_height", 0, 5);
        vars[2] = bikeModel.intVar("person_biketype", 0, 4);
        vars[3] = bikeModel.intVar("frame_sku", 0, 37);
        
        vars[4] = bikeModel.intVar("frame_color", 0, 14);
        vars[5] = bikeModel.intVar("frame_biketype", 0, 4);
        vars[6] = bikeModel.intVar("frame_size", 0, 14);
        vars[7] = bikeModel.intVar("frame_gender", 0, 2);
        vars[8] = bikeModel.intVar("tires_sku", 0, 16);
        
        vars[9] = bikeModel.intVar("tires_height", 0, 3);
        vars[10] = bikeModel.intVar("tires_width", 0, 5);
        vars[11] = bikeModel.intVar("tires_profile", 0, 11);
        vars[12] = bikeModel.intVar("rims_sku", 0, 12);
        vars[13] = bikeModel.intVar("rims_height", 0, 3);
		
        vars[14] = bikeModel.intVar("rims_width", 0, 5);
        vars[15] = bikeModel.intVar("gear_sku", 0, 15);
        vars[16] = bikeModel.intVar("gear_gears", 0, 10);
        vars[17] = bikeModel.intVar("gear_biketype", 0, 4);
        vars[18] = bikeModel.intVar("pedals_sku", 0, 9);
        
        vars[19] = bikeModel.intVar("pedals_pedaltype", 0, 3);
        vars[20] = bikeModel.intVar("shoes_sku", 0, 5);
        vars[21] = bikeModel.intVar("shoes_pedaltype", 0, 3);
        
        vars[22] = bikeModel.intVar("frame_internal", 0, 2);
        vars[23] = bikeModel.intVar("extra_Carrier", 0, 2);
        vars[24] = bikeModel.intVar("extra_Mudguard", 0, 2);
        vars[25] = bikeModel.intVar("extra_Lock", 0, 2);
        vars[26] = bikeModel.intVar("extra_Pump", 0, 2);
        vars[27] = bikeModel.intVar("extra_Bottle", 0, 2);
        vars[28] = bikeModel.intVar("extra_Basket", 0, 2);
        vars[29] = bikeModel.intVar("extra_Cateye", 0, 2);
        vars[30] = bikeModel.intVar("extra_Sidereflex", 0, 2);
        vars[31] = bikeModel.intVar("extra_Frontreflex", 0, 2);
        vars[32] = bikeModel.intVar("extra_Propstand", 0, 2);
        vars[33] = bikeModel.intVar("gear_internal", 0, 2);
        
        int index = 0;
        for(int i=0;i<numberOfVariables;i++){
        	domainSizes[i] =vars[i].getDomainSize();
        	valueOrdering[i]= new int[domainSizes[i]] ;
        	for(int j=0;j<domainSizes[i];j++){
        		if(j==0)
        			lowBoundaries[i]=index;
        		hashmapIDs.put(index,i);  
        		index++;
        		valueOrdering[i][j]=j;
        	}
        }

	}
	public void defineConstraints() {
// 31 CONSTRAINTS		

//R1:
//(((((((((((((((((((((((((((((((((((((((((((((frame_sku == 1) && (frame_biketype == 2)) && ((frame_size >= 8) && (frame_size <= 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 1) ||
// (frame_color == 11)) ||
// (frame_color == 5)) ||
// (frame_color == 3))) ||
// ((((((frame_sku == 4) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (frame_color == 8))) ||
// ((((((frame_sku == 3) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 8)))) ||
// ((((((frame_sku == 6) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 11)))) ||
// ((((((frame_sku == 5) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 10)) ||
// (frame_color == 11)))) ||
// ((((((frame_sku == 7) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 10)) ||
// (frame_color == 11)))) ||
// ((((((frame_sku == 2) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && ((((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 3)) ||
// (frame_color == 1)))) ||
// ((((((frame_sku == 8) && (frame_biketype == 2)) && ((frame_size >= 8) && (frame_size <= 11))) && (frame_gender == 2)) && frame_internal) && (frame_color == 11))) ||
// ((((((frame_sku == 10) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((((frame_color == 10) ||
// (frame_color == 8)) ||
// (frame_color == 11)) ||
// (frame_color == 1)))) ||
// ((((((frame_sku == 9) && (frame_biketype == 1)) && (((((frame_size == 5) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (((((frame_color == 3) ||
// (frame_color == 4)) ||
// (frame_color == 8)) ||
// (frame_color == 10)) ||
// (frame_color == 11)))) ||
// ((((((frame_sku == 9) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 8) ||
// (frame_color == 3)) ||
// (frame_color == 1)))) ||
// ((((((frame_sku == 12) && (frame_biketype == 3)) && ((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8))) && (frame_gender == 2)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 11)) ||
// (frame_color == 1)))) ||
// ((((((frame_sku == 11) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && ((((frame_color == 12) ||
// (frame_color == 4)) ||
// (frame_color == 3)) ||
// (frame_color == 1)))) ||
// ((((((frame_sku == 13) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 3)))) ||
// ((((((frame_sku == 15) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && (((frame_color == 10) ||
// (frame_color == 12)) ||
// (frame_color == 11)))) ||
// ((((((frame_sku == 14) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 10)))) ||
// ((((((frame_sku == 17) && (frame_biketype == 1)) && (((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11))) && (frame_gender == 2)) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 3)))) ||
// ((((((frame_sku == 16) && (frame_biketype == 1)) && (((((frame_size == 7) ||
// (frame_size == 8)) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13))) && (frame_gender == 1)) && frame_internal) && (((((frame_color == 12) ||
// (frame_color == 11)) ||
// (frame_color == 6)) ||
// (frame_color == 10)) ||
// (frame_color == 3)))) ||
// ((((((frame_sku == 18) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 11))) ||
// ((((((frame_sku == 19) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 6) ||
// (frame_color == 9)))) ||
// ((((((frame_sku == 20) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 11) ||
// (frame_color == 9)))) ||
// ((((((frame_sku == 21) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 6) ||
// (frame_color == 8)))) ||
// ((((((frame_sku == 22) && (frame_biketype == 3)) && (((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 8)) ||
// (frame_size == 10))) && (frame_gender == 1)) && frame_internal) && (((frame_color == 12) ||
// (frame_color == 6)) ||
// (frame_color == 10)))) ||
// ((((((frame_sku == 24) && (frame_biketype == 4)) && ((((frame_size == 7) ||
// (frame_size == 8)) ||
// (frame_size == 9)) ||
// (frame_size == 10))) && (frame_gender == 2)) && !(frame_internal)) && (frame_color == 12))) ||
// ((((((frame_sku == 23) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && (((frame_color == 12) ||
// (frame_color == 3)) ||
// (frame_color == 10)))) ||
// ((((((frame_sku == 25) && (frame_biketype == 4)) && ((((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12)) ||
// (frame_size == 13))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 8))) ||
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
// (frame_color == 8)))) ||
// ((((((frame_sku == 27) && (frame_biketype == 4)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13)) ||
// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 1))) ||
// ((((((frame_sku == 28) && (frame_biketype == 4)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13)) ||
// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 2))) ||
// ((((((frame_sku == 29) && (frame_biketype == 4)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) ||
// (frame_size == 13)) ||
// (frame_size == 14))) && (frame_gender == 1)) && !(frame_internal)) && ((frame_color == 1) ||
// (frame_color == 13)))) ||
// ((((((frame_sku == 30) && (frame_biketype == 3)) && ((((frame_size == 3) ||
// (frame_size == 5)) ||
// (frame_size == 7)) ||
// (frame_size == 9))) && ((frame_gender == 1) ||
// (frame_gender == 2))) && frame_internal) && (frame_color == 12))) ||
// ((((((frame_sku == 31) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && (((frame_size == 8) ||
// (frame_size == 9)) ||
// (frame_size == 10))) && (frame_gender == 2)) && frame_internal) && (frame_color == 12))) ||
// ((((((frame_sku == 31) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && (((frame_size == 9) ||
// (frame_size == 10)) ||
// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (frame_color == 12))) ||
// ((((((frame_sku == 32) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && ((((frame_size == 8) ||
// (frame_size == 9)) ||
// (frame_size == 10)) ||
// (frame_size == 12))) && (frame_gender == 1)) && frame_internal) && (frame_color == 12))) ||
// ((((((frame_sku == 32) && ((frame_biketype == 1) ||
// (frame_biketype == 3))) && ((frame_size == 8) ||
// (frame_size == 9))) && (frame_gender == 2)) && frame_internal) && (frame_color == 12))) ||
// ((((((frame_sku == 33) && (frame_biketype == 1)) && (((frame_size == 10) ||
// (frame_size == 11)) ||
// (frame_size == 12))) && ((frame_gender == 1) ||
// (frame_gender == 2))) && frame_internal) && ((frame_color == 12) ||
// (frame_color == 8)))) ||
// (((((frame_sku == 34) && (frame_biketype == 3)) && (((((frame_size == 8) ||
// (frame_size == 10)) ||
// (frame_size == 11)) && (frame_gender == 1)) ||
// (((frame_size == 8) ||
// (frame_size == 10)) && (frame_gender == 2)))) && !(frame_internal)) && (frame_color == 12))) ||
// ((((((frame_sku == 35) && (frame_biketype == 3)) && (((frame_size == 4) ||
// (frame_size == 6)) ||
// (frame_size == 8))) && (frame_gender == 1)) && frame_internal) && (frame_color == 6))) ||
// ((((((frame_sku == 36) && (frame_biketype == 3)) && ((frame_size >= 1) && (frame_size <= 11))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 11))) ||
// ((((((frame_sku == 37) && (frame_biketype == 3)) && ((frame_size >= 1) && (frame_size <= 11))) && (frame_gender == 1)) && !(frame_internal)) && (frame_color == 12)));
		
		
		
//		R2:
//		(((frame_biketype == 1) ||
//		(frame_biketype == 2)) >> frame_internal);
		bikeModel.ifThen(
 			   bikeModel.arithm(vars[5],"=",1),
 			   bikeModel.arithm(vars[22],"=",1)
	    );
		bikeModel.ifThen(
	 			   bikeModel.arithm(vars[5],"=",2),
	 			   bikeModel.arithm(vars[22],"=",1)
		);
			
		
//R3:
//((frame_biketype == 4) >> !(frame_internal));

		bikeModel.ifThen(
	 			   bikeModel.arithm(vars[5],"=",4),
	 			   bikeModel.arithm(vars[22],"=",0)
		    );
//R4:
//(extra_Carrier >> extra_Mudguard);

		bikeModel.ifThen(
	 			   bikeModel.arithm(vars[23],"=",1),
	 			   bikeModel.arithm(vars[24],"=",1)
		    );
//R5:
//(extra_Pump && extra_Bottle);
		
		bikeModel.ifThen(
	 			   bikeModel.arithm(vars[26],"=",1),
	 			   bikeModel.arithm(vars[27],"=",1)
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
		bikeModel.ifThen(
	 			   bikeModel.arithm(vars[22],"=",0),
	 			   bikeModel.arithm(vars[32],"=",0)
		    );
//R14:
//(rims_height == tires_height);
		bikeModel.arithm(vars[13],"=",vars[9]).post();
//R15:
//(rims_width == tires_width);
		bikeModel.arithm(vars[14],"=",vars[10]).post();
//R16:
//(frame_internal == gear_internal);
		bikeModel.arithm(vars[22],"=",vars[33]).post();
//R17:
//(pedals_pedaltype == shoes_pedaltype);
		bikeModel.arithm(vars[19],"=",vars[21]).post();
//R18:
//(frame_biketype == gear_biketype);
		bikeModel.arithm(vars[5],"=",vars[17]).post();
//R19:
//(person_gender == frame_gender);
		bikeModel.arithm(vars[0],"=",vars[7]).post();
//R20:
//(person_biketype == frame_biketype);
		bikeModel.arithm(vars[2],"=",vars[5]).post();
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
		
		
		// ADDITIONAL CONSTRAINTS
		for(int i=0;i<numberOfVariables;i++){
			int value = vars[i].getDomainSize();
			int minvalue = vars[i].getLB();
			int maxvalue = vars[i].getUB();
			
			int avg = (maxvalue-minvalue)/2;
			if (i<numberOfVariables-1)
				bikeModel.ifThen(
			 			   bikeModel.arithm(vars[i],">",avg),
			 			   bikeModel.arithm(vars[i+1],"!=",vars[i])
				);
			if (i<numberOfVariables-2)
				bikeModel.ifThen(
			 			   bikeModel.arithm(vars[i],">",avg),
			 			   bikeModel.arithm(vars[i+2],"!=",vars[i])
				);
			
			
			
			
			if (i>1)
				bikeModel.ifThen(
			 			   bikeModel.arithm(vars[i],"<",avg),
			 			   bikeModel.arithm(vars[i-1],"!=",vars[i])
				);
			if (i>2)
			bikeModel.ifThen(
			 			   bikeModel.arithm(vars[i],"<",avg),
			 			   bikeModel.arithm(vars[i-2],"!=",vars[i])
			);
			
			
		}
		
	
	}
	
	public int [][] generateSampleSolutions(int number, String outputFile, boolean istype2){
		
		 Solver solver = bikeModel.getSolver();
		 int counter = 0;
		 int [][] solutions = new int [number][numberOfVariables];
		 
		VariableSelector varSelector =  new InputOrder<>(bikeModel);
		IntValueSelector valueSelector = new IntDomainRandom(1);
		 do{
			 solver.setSearch(intVarSearch(
		                
					 varSelector,
		               
					 valueSelector,
		               
					vars
				));
			 
			 solver.solve();
			 solutions[counter] = new int [numberOfVariables];
			 for(int i=0;i<numberOfVariables;i++)
				 solutions[counter][i]=((IntVar)(bikeModel.getVar(i))).getValue();
			 
			 writeSolutionToFile(outputFile,solutions[counter],counter,istype2);
			 counter++;
		 }while(counter < number);
		 return solutions;
	}
	
	
	private void writeSolutionToFile (String outputFile, int []values, int index, boolean istype2){
		
		int itemIndex = 0;
		List<String> lines = new ArrayList<String>();
		for (int t=0;t<numberOfVariables;t++){
			int size = domainSizes[t];
			if(!istype2){
				for (int d=0;d<size;d++){ // for ex: size is 14
					String value = "0.0";
					if(values[t]==d) // for ex : random=5 and d=5
						value = "1.0";
					String s= itemIndex+","+value+"\n";
					lines.add(s);
					itemIndex++;
				}
			}
			else{
				String s= itemIndex+","+values[t]+"\n";
				lines.add(s);
				itemIndex++;
			}
		}
		writeToFile(outputFile,lines,index);
	}
	
	public void writeToFile(String outputFile,List<String> lines, int index){
		// WRITE DATASET
		File file = new File(outputFile);
		String line ;
		if (!file.exists()) 
			try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		
					
		// OPEN FILE TO WRITE
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsolutePath(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		
					
		// ADD USER REQS TO THE FILE
		for(int j=0;j<lines.size();j++)			
			try { 
				bw.write(index+","+lines.get(j)); // ADD USER ID: i
			} catch (IOException e) {
							e.printStackTrace();
			}
					
			// CLOSE FILE 
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setHeuristics (){
		
		SedasValueOrdering valueOrder = new SedasValueOrdering(valueOrdering);
		VariableSelector varSelector =  new InputOrder<>(bikeModel);
		IntValueSelector valueSelector = valueOrder;
		
	     
		bikeModel.getSolver().setSearch(intVarSearch(
                
				varSelector,
                // selects the smallest domain value (lower bound)
				 
				valueSelector,
               
                // variables to branch on
				vars
		));
		
	}

}
