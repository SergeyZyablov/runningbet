package ua.runningbet.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Jockey;
import ua.runningbet.repositpries.JockeyRepository;
import ua.runningbet.valodators.JockeyValidator;

@Controller
public class JockeyController {
	private int page = 0;
	private final int PAGE_SIZE = 15;
	@Autowired
	private JockeyRepository jockeyRepository;
	@Autowired
	private JockeyValidator jockeyValidator;

	@GetMapping(value = "/admin/jockey")
	public String categoryPage(Model model) {
		Pageable pageNumber = PageRequest.of(page, PAGE_SIZE);
		Page<Jockey> pageabledJockey = jockeyRepository.findAll(pageNumber);
		model.addAttribute("jockey", new Jockey());
		model.addAttribute("jockeys", pageabledJockey);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "jockey";
	}
	
	@GetMapping("/admin/jockey/next")
	public String nextPage() {
		page++;
		return "redirect:/admin/jockey";
	}

	@GetMapping("/admin/jockey/prev")
	public String prevPage() {
		if (page > 0) {
			page--;
		}
		return "redirect:/admin/jockey";
	}

	@PostMapping(value = "/admin/jockey/add")
	public String categoryAddPage(@ModelAttribute @Valid Jockey jockey, BindingResult bindingResult, Errors errors,
			Model model) {
		jockeyValidator.validate(jockey, errors);
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/jockey";
		}
		jockeyRepository.save(jockey);
		return "redirect:/admin/jockey";
	}

	@PostMapping(value = "/jockey/remove")
	public String jockyRemove(String id, Model model) {
		Jockey jockey = jockeyRepository.findOneByid(Integer.valueOf(id));
		jockeyRepository.delete(jockey);
		model.addAttribute("jockeys", jockeyRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "redirect:/admin/jockey";
	}
}
