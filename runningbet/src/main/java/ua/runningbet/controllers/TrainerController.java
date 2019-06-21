package ua.runningbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Trainer;
import ua.runningbet.repositpries.TrainerRepository;

@Controller
public class TrainerController {
	@Autowired
	private TrainerRepository trainerRepository;

	@GetMapping(value = "/admin/trainers")
	public String categoryPage(Model model) {
		model.addAttribute("trainers", trainerRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "trainer";
	}

	@PostMapping(value = "/admin/trainers/add")
	public String categoryAddPage(@ModelAttribute("Category") Trainer trainer, Model model) {
		trainerRepository.save(trainer);
		return "redirect:/admin/trainers";
	}
}
