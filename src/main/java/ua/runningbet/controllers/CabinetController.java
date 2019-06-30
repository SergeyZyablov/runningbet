package ua.runningbet.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.User;
import ua.runningbet.repositpries.SlotRepository;
import ua.runningbet.repositpries.UserRepository;
import ua.runningbet.valodators.UserValidator;

@Controller
public class CabinetController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SlotRepository slotRepositor;
	@Autowired
	private UserValidator userValidator;

	@GetMapping(value = "/cabinet")
	public String getCabinetPage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		model.addAttribute("logenedUser", user);
		return "cabinet";
	}

	@GetMapping(value = "/cabinet/editing")
	public String getEditinPage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		model.addAttribute("user", new User());
		model.addAttribute("logenedUser", user);
		return "editUser";
	}

	@PostMapping(value = "/cabinet/editing")
	public String editUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, Errors errors) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loginedUser = userRepository.findByLogin(auth.getName()).orElse(new User());
		loginedUser.setName(user.getName());
		loginedUser.setSurname(user.getSurname());
		loginedUser.setLogin(user.getLogin());
		loginedUser.setEmail(user.getEmail());
		loginedUser.setBirthday(user.getBirthday());
		userValidator.validate(loginedUser, errors);
		if (bindingResult.hasErrors()) {
			return "redirect:/cabinet/editing";
		}
		return "/cabinet";
	}

}
