package ua.runningbet.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Horse;
import ua.runningbet.models.Jockey;
import ua.runningbet.models.Trainer;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.JockeyRepository;
import ua.runningbet.repositpries.TrainerRepository;
import ua.runningbet.valodators.HourceValidator;

@Controller
public class HourceController {
	@Autowired
	private HorseRepository horceRepository;
	@Autowired
	private TrainerRepository trainerRepository;
	@Autowired
	private JockeyRepository jockeyRepository;
	@Autowired
	private HourceValidator hourceValidator;

	@GetMapping(value = "/admin/hources")
	public String hourcesPage(Model model) {
		model.addAttribute("hources", horceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "hources";
	}

	@GetMapping(value = "/admin/hources/add")
	public String hourcesAddPage(Model model) {
		model.addAttribute("hource", new Horse());
		model.addAttribute("trainers", trainerRepository.findAll());
		model.addAttribute("jockeys", jockeyRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "hourcesAdd";
	}

	@PostMapping(value = "/admin/hources/add")
	public String hourcesAdd(@ModelAttribute @Valid Horse hource, BindingResult bindingResult, Errors errors,
			String jockeyName, String trainerName, Model model) {

		hourceValidator.validate(hource, errors);
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/hources/add";
		}
		Jockey jockey = jockeyRepository.findByName(jockeyName);
		Trainer trainer = trainerRepository.findByName(trainerName);
		hource.setJockey(jockey);
		hource.setTrainer(trainer);
		horceRepository.save(hource);
		return "redirect:/admin/hources";
	}

	@PostMapping(value = "/hource/remove")
	public String hourceRemove(String id, Model model) {
		Horse hource = horceRepository.getOne(Integer.valueOf(id));
		horceRepository.delete(hource);
		model.addAttribute("hources", horceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "hources";
	}
}
