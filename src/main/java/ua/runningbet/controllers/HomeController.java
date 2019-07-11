package ua.runningbet.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import ua.runningbet.repositpries.CategoryRepository;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	private Integer wins;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());
		if (user.getId() != null) {
			if (user.getBlocked().equals("true")) {
				return "redirect:/blocked";
			}
		}
		wins = 0;
		List<User> users = userRepository.findAll();
		users.stream().forEach(e -> wins += e.getWins());
		model.addAttribute("wins", wins);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("events", eventRepository.findAll());
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/home/search/category")
	public String searchByCategory(String category, Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("events", eventRepository.findByCategoryName(category));
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/home/search/status")
	public String searchByStatus(String status, Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("events", eventRepository.findByStatusName(status));
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/home/search/event")
	public String searchByEventName(String name, Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("events", eventRepository.findOneByName(name));
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/home/search/date")
	public String searchByDate(String date, Model model) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed = format.parse(date);
		List<Event> events = eventRepository.findAll();
		List<Event> sortedEvents = events.stream()
				.filter(e -> new Date(convertDate(e.getStartDate())).equals(new Date(convertDate(parsed))))
				.collect(Collectors.toList());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("events", sortedEvents);
		model.addAttribute("header", "fragments/header");
		return "index";
	}

	@GetMapping("/blocked")
	public String blockedPage(Model model) {
		return "blocked";
	}

	private long convertDate(Date date) {
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long time = cal.getTimeInMillis();
		return time;

	}

}
