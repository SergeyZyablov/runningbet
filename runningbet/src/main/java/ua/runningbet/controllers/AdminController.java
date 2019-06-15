package ua.runningbet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.runningbet.models.User;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class AdminController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String statisticsPage(Model model) {
		List<User> users = userRepository.findAll();
		int registratedUsers = users.size();
		model.addAttribute("registratedUsers", registratedUsers);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "admin";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String usersPage(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "users";
	}
}
