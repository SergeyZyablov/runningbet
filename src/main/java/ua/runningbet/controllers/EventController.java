package ua.runningbet.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

import ua.runningbet.models.Category;
import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;
import ua.runningbet.repositpries.CategoryRepository;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.StatusRepository;
import ua.runningbet.valodators.EventValidator;

@Controller
public class EventController {
	private int page = 0;
	private final int PAGE_SIZE = 9;

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private HorseRepository hourceRepository;
	@Autowired
	private EventValidator eventValidator;

	@GetMapping(value = "/admin/event")
	public String eventPage(Model model) {
		Pageable pageNumber = PageRequest.of(page, PAGE_SIZE);
		Page<Event> pageabledEvents = eventRepository.findAll(pageNumber);
		model.addAttribute("events", pageabledEvents);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/event";
	}

	@GetMapping("/admin/event/next")
	public String nextPage() {
		page++;
		return "redirect:/admin/event";
	}

	@GetMapping("/admin/event/prev")
	public String prevPage() {
		if (page > 0) {
			page--;
		}
		return "redirect:/admin/event";
	}

	@GetMapping(value = "/admin/event/add")
	public String eventAddPage(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("date", new String());
		model.addAttribute("category", categoryRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventAdd";
	}

	@PostMapping(value = "/admin/event/add")
	public String eventAddPage(@ModelAttribute("categorName") String categoryName,
			@Valid @ModelAttribute("event") Event event, BindingResult bindingResult,
			@ModelAttribute("date") String date, Model model, Errors errors) throws ParseException {
		eventValidator.validate(event, errors);
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/event/add";
		}
		if (date.isEmpty()) {
			return "redirect:/admin/event/add";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		java.util.Date parsed = format.parse(date);
		Category category = categoryRepository.findByName(categoryName);
		event.setStartDate(parsed);
		event.setCategory(category);
		event.setStatus(statusRepository.findByName("FUTURE"));
		eventRepository.save(event);
		return "redirect:/admin/event";
	}

	@GetMapping(value = "/event")
	public String oneEventPage(String eventId, Model model) {
		Event event = eventRepository.findOneById(Integer.valueOf(eventId));

		List<Slot> slots = event.getSlots();
		for (int j = 0; j < slots.size() * 2; j++) {
			for (int i = 0; i < slots.size(); i++) {
				if (slots.get(i).getBet() != null) {
					slots.remove(slots.get(i));
				}
			}
		}

		model.addAttribute("slotsSize", event.getSlots().size());
		model.addAttribute("slot", new Slot());
		model.addAttribute("event", event);
		model.addAttribute("slots", slots);
		model.addAttribute("hources", hourceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventPage";
	}

	@PostMapping(value = "/event/remove")
	public String removeEvent(String id, Model model) {
		Event event = eventRepository.findOneById(Integer.valueOf(id));
		if (event.getSlots().isEmpty()) {
			eventRepository.delete(event);
		}
		model.addAttribute("events", eventRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "/event";
	}
}
