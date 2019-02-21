package at.tugraz.ist.recommender.healthiotrecommender;

import java.util.Date;
import java.util.Hashtable;

public class User {
	
	 public User(){
		
	 }
	
     String ID;
     int index;
	 int gender;
	 int age;

	 Hashtable<String, Steps> stepsRecords = new Hashtable<String,Steps>();
	 Hashtable<String, Sleep> sleepRecords = new Hashtable<String,Sleep>();
	 
	 Sleep avgSleeps;
	 Steps avgSteps;
	 
	 
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the stepsRecords
	 */
	public Hashtable<String, Steps> getStepsRecords() {
		return stepsRecords;
	}
	/**
	 * @param stepsRecords the stepsRecords to set
	 */
	public void setStepsRecords(Hashtable<String, Steps> stepsRecords) {
		this.stepsRecords = stepsRecords;
	}
	/**
	 * @return the sleepRecords
	 */
	public Hashtable<String, Sleep> getSleepRecords() {
		return sleepRecords;
	}
	/**
	 * @param sleepRecords the sleepRecords to set
	 */
	public void setSleepRecords(Hashtable<String, Sleep> sleepRecords) {
		this.sleepRecords = sleepRecords;
	}
	/**
	 * @return the avgSleeps
	 */
	public Sleep getAvgSleeps() {
		return avgSleeps;
	}
	/**
	 * @param avgSleeps the avgSleeps to set
	 */
	public void setAvgSleeps(Sleep avgSleeps) {
		this.avgSleeps = avgSleeps;
	}
	/**
	 * @return the avgSteps
	 */
	public Steps getAvgSteps() {
		return avgSteps;
	}
	/**
	 * @param avgSteps the avgSteps to set
	 */
	public void setAvgSteps(Steps avgSteps) {
		this.avgSteps = avgSteps;
	}

	
}
