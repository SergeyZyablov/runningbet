package ua.runningbet.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.runningbet.models.Event;
import ua.runningbet.models.User;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.StatusRepository;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		if (user.getBlocked().equals("true")) {
			return "redirect:/blocked";
		}

		java.util.Date date = new java.util.Date();
		Date dateSQL = new Date(date.getTime());
		List<Event> events = eventRepository.findAll();
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getStartDate().toString().equalsIgnoreCase(dateSQL.toString())) {
				events.get(i).setStatus(statusRepository.findByName("LIVE"));
			} else if (events.get(i).getStartDate().after(dateSQL)) {
				events.get(i).setStatus(statusRepository.findByName("FUTURE"));
			}
			/*
			 * else if (events.get(i).getStartDate().before(dateSQL)) {
			 * events.get(i).setStatus(statusRepository.findByName("FINISHED")); }
			 */
			eventRepository.save(events.get(i));
		}
		/*
		 * List<Event> newEvents = new ArrayList<>(); List<Event> liveEvent = new
		 * ArrayList<>(); List<Event> pastEvent = new ArrayList<>(); for (int i = 0; i <
		 * events.size(); i++) { if (events.get(i).getStartDate().before(date)) {
		 * pastEvent.add(events.get(i)); } } for (int i = 0; i < events.size(); i++) {
		 * if (events.get(i).getStartDate().after(date)) { newEvents.add(events.get(i));
		 * } } for (int i = 0; i < events.size(); i++) { if
		 * (events.get(i).getStartDate().equals(date)) { liveEvent.add(events.get(i)); }
		 * }
		 */
		model.addAttribute("events", events);
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/blocked")
	public String blockedPage(Model model) {
		return "blocked";
	}

}
