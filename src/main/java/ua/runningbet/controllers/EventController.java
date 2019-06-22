package ua.runningbet.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

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
import ua.runningbet.repositpries.HorseRepository;
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
	private HorseRepository hourceRepository;

	@GetMapping(value = "/admin/event")
	public String eventPage(Model model) {
		java.util.Date date = new java.util.Date();
		Date dateSQL = new Date(date.getTime());
		List<Event> events = eventRepository.findAll();
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDate().toString().equalsIgnoreCase(dateSQL.toString())) {
				events.get(i).setStatus(statusRepository.findByName("LIVE"));
			} else if (events.get(i).getStartDate().after(dateSQL)) {
				events.get(i).setStatus(statusRepository.findByName("FUTURE"));
			} else if (events.get(i).getStartDate().before(dateSQL)) {
				events.get(i).setStatus(statusRepository.findByName("FINISHED"));
			}
			eventRepository.save(events.get(i));
		}
		model.addAttribute("events", events);
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
		java.util.Date parsed = format.parse(date);
		Date dateSQL = new java.sql.Date(parsed.getTime());
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

	@PostMapping(value = "/event/remove")
	public String removeEvent(String id, Model model) {
		Event event = eventRepository.findOneById(Integer.valueOf(id));
		eventRepository.delete(event);
		model.addAttribute("events", eventRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/event";
	}
}
