package at.tugraz.ist.recommender.healthiotrecommender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	//String filename = "C:\\Users\\spolater\\Desktop\\AGILE\\STS_WORKSPACE\\HealthiotRecommender\\UserActivities";
	String filename = "/home/agile/Files/UserActivities";
	int numberofusers=0;
	int numberofitems=0;
	
	boolean isInitiated=false;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
		
		if(!isInitiated)
    		initate();
    	
			
		model.addAttribute("users", numberofusers);
		model.addAttribute("items", numberofitems);
		
		return "home";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public User test() {
		User u = new User();
		u.setAge(30);
		u.setGender(3);
		u.setID("12345");
		Sleep s = new Sleep();
		s.sleep1=40;
		s.sleep2=40;
		s.sleep3=40;
		s.sleep1count=5;
		s.sleep2count=5;
		s.sleep3count=5;
		
		Steps t = new Steps();
		t.steps1=100;
		t.steps2=100;
		t.steps3=100;
		t.stepstotal=300;
		t.steps1count=5;
		t.steps2count=5;
		t.steps3count=5;
		t.stepstotalcount=15;
		
		String date = "20190117,1";
		
		Hashtable<String, Steps> stepsRecords = new Hashtable<String,Steps>();
		Hashtable<String, Sleep> sleepRecords = new Hashtable<String,Sleep>();
		stepsRecords.put(date, t);
		sleepRecords.put(date, s);
		
		u.setAvgSleeps(s);
		u.setAvgSteps(t);
		u.setAge(30);
		u.setSleepRecords(sleepRecords);
		u.setStepsRecords(stepsRecords);
		
		return u;
	}
	
    @RequestMapping(value = "/getActivityRecommendation", method = RequestMethod.POST)
   	public @ResponseBody ActivityRecommendationList getActivityRecommendation(@RequestBody User newuser) {
       	
    	
    	
    	List<ActivityRecommendation> recommendations = Recommender.applyCollaborativeFiltering(newuser,filename,numberofusers);
    	
    	ActivityRecommendationList returnModel = new ActivityRecommendationList();
   		returnModel.setActivityRecommendation_list(recommendations);
   		return returnModel;
   	}
	
    public void initate(){
    	
    	numberofusers = DataParser.parseFile(filename);
    	numberofitems = Recommender.getNumberOfItems(filename);
    	isInitiated=true;
    }
	
}
