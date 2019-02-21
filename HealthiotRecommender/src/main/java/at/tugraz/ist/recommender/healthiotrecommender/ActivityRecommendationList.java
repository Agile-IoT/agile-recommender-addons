package at.tugraz.ist.recommender.healthiotrecommender;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecommendationList {
	
	public ActivityRecommendationList(){}
	
	String flag = "green";
	
	String intro1 = "Recent scientific works show that Insomnia (a sleep disorder) can be solved with a personal physical acticity plan.";
	String intro2 = "Therefore, according to your profile, for a high quality sleep, we recommend you several walking and sleeping plans as below:";
	
	List<ActivityRecommendation> activityRecommendation_list = new ArrayList<ActivityRecommendation>();
	
	/**
	 * @return the intro1
	 */
	public String getIntro1() {
		return intro1;
	}
	/**
	 * @param intro1 the intro1 to set
	 */
	public void setIntro1(String intro1) {
		this.intro1 = intro1;
	}
	/**
	 * @return the intro2
	 */
	public String getIntro2() {
		return intro2;
	}
	/**
	 * @param intro2 the intro2 to set
	 */
	public void setIntro2(String intro2) {
		this.intro2 = intro2;
	}
	/**
	 * @return the activityRecommendation_list
	 */
	public List<ActivityRecommendation> getActivityRecommendation_list() {
		return activityRecommendation_list;
	}
	/**
	 * @param activityRecommendation_list the activityRecommendation_list to set
	 */
	public void setActivityRecommendation_list(List<ActivityRecommendation> activityRecommendation_list) {
		this.activityRecommendation_list = activityRecommendation_list;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}
