package ua.runningbet.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Category;
import ua.runningbet.models.Event;
import ua.runningbet.repositpries.CategoryRepository;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.StatusRepository;

@Controller
public class EventController {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StatusRepository statusRepository;

	@GetMapping(value = "/admin/event")
	public String eventPage(Model model) {
		model.addAttribute("events", eventRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/event";
	}

	@GetMapping(value = "/event/add")
	public String eventAddPage(Model model) {
		model.addAttribute("category", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventAdd";
	}

	@PostMapping(value = "/event/add")
	public String eventAddPage(@ModelAttribute("categorName") String categoryName, @ModelAttribute("event") Event event,
			Model model) {
		Category category = categoryRepository.findByName(categoryName);
		event.setCategory(category);
		event.setStatus(statusRepository.findByName("FUTURE"));
		eventRepository.save(event);
		return "redirect:/admin/event";
	}
}
