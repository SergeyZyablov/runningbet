package ua.runningbet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Category;
import ua.runningbet.models.User;
import ua.runningbet.repositpries.CategoryRepository;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class AdminController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/admin")
	public String statisticsPage(Model model) {
		List<User> users = userRepository.findAll();
		int registratedUsers = users.size();
		model.addAttribute("registratedUsers", registratedUsers);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "admin";
	}

	@GetMapping(value = "/admin/users")
	public String usersPage(Model model) {
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "users";
	}
}
