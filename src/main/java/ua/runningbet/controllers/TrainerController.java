package ua.runningbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Trainer;
import ua.runningbet.repositpries.TrainerRepository;
import ua.runningbet.valodators.TrainerValidator;

@Controller
public class TrainerController {
	@Autowired
	private TrainerRepository trainerRepository;
	@Autowired
	private TrainerValidator trainerValidator;

	@GetMapping(value = "/admin/trainers")
	public String categoryPage(Model model) {
		model.addAttribute("trainer", new Trainer());
		model.addAttribute("trainers", trainerRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "trainer";
	}

	@PostMapping(value = "/admin/trainers/add")
	public String categoryAddPage(@ModelAttribute @Validated Trainer trainer, BindingResult bindingResult, Model model,
			Errors errors) {
		trainerValidator.validate(trainer, errors);
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/trainers";
		}
		trainerRepository.save(trainer);
		return "redirect:/admin/trainers";
	}

	@PostMapping(value = "/trainers/remove")
	public String trainerRemove(String id, Model model) {
		Trainer trainer = trainerRepository.getOne(Integer.valueOf(id));
		trainerRepository.delete(trainer);
		model.addAttribute("trainers", trainerRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "redirect:/admin/trainers";
	}

}
