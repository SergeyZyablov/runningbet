package ua.runningbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Jockey;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.JockeyRepository;

@Controller
public class JockeyController {
	@Autowired
	private JockeyRepository jockeyRepository;
	@Autowired
	private HorseRepository hourceRepository;

	@GetMapping(value = "/admin/jockey")
	public String categoryPage(Model model) {
		model.addAttribute("hources", hourceRepository.findAll());
		model.addAttribute("jockeys", jockeyRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "jockey";
	}

	@PostMapping(value = "/admin/jockey/add")
	public String categoryAddPage(@ModelAttribute("Category") Jockey jockey, Model model) {
		jockeyRepository.save(jockey);
		return "redirect:/admin/jockey";
	}
}
