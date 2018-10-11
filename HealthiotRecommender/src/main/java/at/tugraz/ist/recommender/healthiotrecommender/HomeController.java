package at.tugraz.ist.recommender.healthiotrecommender;

import java.text.DateFormat;
import java.util.Date;
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

	String filename = System.getProperty("user.dir")+"\\UserActivities";
	int numberofusers=0;
	
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
    	
			
		model.addAttribute("message", numberofusers);
		
		return "home";
	}
	
    @RequestMapping(value = "/getActivityRecommendation", method = RequestMethod.POST)
   	public @ResponseBody ActivityRecommendation getActivityRecommendation(@RequestBody User newuser) {
       	
    	int genderCode = Integer.valueOf(newuser.getGender());
    	if(genderCode==2)
    		genderCode=5;
   		
    	ActivityRecommendation recommendation = Recommender.applyCollaborativeFiltering(genderCode,filename,numberofusers);
    	
   		return recommendation;
   	}
	
    public void initate(){
    	
    	numberofusers = DataParser.parseFile(filename);
    	isInitiated=true;
    }
	
}
