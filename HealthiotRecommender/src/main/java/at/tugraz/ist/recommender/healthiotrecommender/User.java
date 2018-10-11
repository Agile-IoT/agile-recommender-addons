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

	 public Hashtable<String, Steps> stepsRecords = new Hashtable<String,Steps>();
	 public Hashtable<String, Sleep> sleepRecords = new Hashtable<String,Sleep>();
	 
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

	
}
