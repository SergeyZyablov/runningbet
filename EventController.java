package ua.runningbet.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Category;
import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;
import ua.runningbet.repositpries.CategoryRepository;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.SlotRepository;
import ua.runningbet.repositpries.StatusRepository;

@Controller
public class EventController {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private HorseRepository hourceRepository;

	@GetMapping(value = "/admin/event")
	public String eventPage(Model model) {
		model.addAttribute("events", eventRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/event";
	}

	@GetMapping(value = "/admin/event/add")
	public String eventAddPage(Model model) {
		model.addAttribute("category", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventAdd";
	}

	@PostMapping(value = "/admin/event/add")
	public String eventAddPage(@ModelAttribute("categorName") String categoryName, @ModelAttribute("event") Event event,
			@ModelAttribute("date") String date, Model model) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date parsed = format.parse(date);
		java.sql.Date dateSQL = new java.sql.Date(parsed.getTime());
		Category category = categoryRepository.findByName(categoryName);
		event.setStartDate(dateSQL);
		event.setCategory(category);
		event.setStatus(statusRepository.findByName("FUTURE"));
		eventRepository.save(event);
		return "redirect:/admin/event";
	}

	@GetMapping(value = "/event")
	public String oneEventPage(String id, Model model) {
		Event event = eventRepository.findOneById(Integer.valueOf(id));
		model.addAttribute("event", event);
		model.addAttribute("slots", event.getSlots());
		model.addAttribute("hources", hourceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventPage";
	}
}
