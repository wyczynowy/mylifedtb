package com.electroline.myapp;

import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
//@SessionAttributes(value = {"myBean"})
public class HomeController {
	
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
		
		return "home";
	}
	
	@RequestMapping(value = "/login")
	public String login(@RequestParam Map<String, String> params) {
		return "login";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(@RequestParam Map<String, String> params) {
		return "login";
	}
	
	@RequestMapping(value = "/message")
	public ResponseEntity<String> message(@RequestParam Map<String, String> params) throws Exception {
		String message = "{ \"name\":\"John\", \"age\":30, \"occupation\":null }";
		System.out.println("id=" + params.get("id") + " name = " +params.get("name") + ", surname = " +params.get("surname"));
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
