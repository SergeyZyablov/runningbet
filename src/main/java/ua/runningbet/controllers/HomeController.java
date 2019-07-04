package ua.runningbet.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.runningbet.models.User;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.StatusRepository;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		if (user.getId() != null) {
			if (user.getBlocked().equals("true")) {
				return "redirect:/blocked";
			}
		}
		model.addAttribute("events", eventRepository.findAll());
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/blocked")
	public String blockedPage(Model model) {
		return "blocked";
	}

}
