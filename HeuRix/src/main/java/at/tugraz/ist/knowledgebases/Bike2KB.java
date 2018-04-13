package at.tugraz.ist.knowledgebases;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

//https://www.itu.dk/research/cla/externals/clib/bike2.cp
public class Bike2KB implements KB{

	Model modelKB = new Model("BikeConfigurationProblem");
	int numberOfVariables = 34;
	IntVar[] vars = new IntVar[numberOfVariables];
	
	public void Bike2KB(){
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
