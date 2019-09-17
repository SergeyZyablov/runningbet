package ua.runningbet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsControlle {

	@GetMapping("/about")
	public String getAboutUs() {
		return "aboutUs";
	}
}
