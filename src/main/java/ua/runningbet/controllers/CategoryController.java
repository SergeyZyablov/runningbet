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

import ua.runningbet.models.Category;
import ua.runningbet.repositpries.CategoryRepository;
import ua.runningbet.valodators.CategoryValidator;

@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryValidator categoryValidator;

	@GetMapping(value = "/admin/category")
	public String categoryPage(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/category";
	}

	@PostMapping(value = "/admin/category/add")
	public String categoryAddPage(@ModelAttribute @Valid Category category, BindingResult bindingResult, Errors errors,
			Model model) {
		categoryValidator.validate(category, errors);
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/category";
		} else {
			categoryRepository.save(category);
			return "redirect:/admin/category";
		}

	}

	@PostMapping(value = "/category/remove")
	public String categoryRemove(String id, Model model) {
		Category category = categoryRepository.getOne(Integer.valueOf(id));
		categoryRepository.delete(category);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "redirect:/admin/category";
	}
}
