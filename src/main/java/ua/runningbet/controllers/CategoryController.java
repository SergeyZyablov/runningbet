package ua.runningbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Category;
import ua.runningbet.repositpries.CategoryRepository;

@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping(value = "/admin/category")
	public String categoryPage(Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/category";
	}

	@PostMapping(value = "/admin/category/add")
	public String categoryAddPage(@ModelAttribute("Category") Category category, Model model) {
		categoryRepository.save(category);
		return "redirect:/admin/category";
	}

	@PostMapping(value = "/category/remove")
	public String categoryRemove(String id, Model model) {
		Category category = categoryRepository.getOne(Integer.valueOf(id));
		categoryRepository.delete(category);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/category";
	}
}
