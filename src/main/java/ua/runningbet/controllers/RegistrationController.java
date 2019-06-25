package ua.runningbet.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Role;
import ua.runningbet.models.User;
import ua.runningbet.repositpries.RoleRepository;
import ua.runningbet.repositpries.UserRepository;
import ua.runningbet.valodators.UserValidator;

@Controller
public class RegistrationController {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserValidator userValidator;

	@GetMapping(value = "/registration")
	public String getRegistrationPage(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping(value = "/registration")
	private String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, Errors errors) {
		userValidator.validate(user, errors);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		Role userRole = roleRepository.findOneByRole("user");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return "redirect:/login";
	}
}