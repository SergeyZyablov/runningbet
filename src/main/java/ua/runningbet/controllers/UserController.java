package ua.runningbet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Event;
import ua.runningbet.models.Horse;
import ua.runningbet.models.Slot;
import ua.runningbet.models.User;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private HorseRepository horseRepository;

	@PostMapping(value = "/user/bet/add")
	public String addBet(Slot slot, String eventId, String horseId, Model model) {

		Event event = eventRepository.findOneById(Integer.valueOf(eventId));
		Horse horse = horseRepository.findOneById(Integer.valueOf(horseId));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByLogin(auth.getName()).orElse(new User());

		if (slot.getBet() == null || slot.getBet() == 0) {
			return "redirect:/";
		} else if (slot.getBet() > user.getMony()) {
			return "redirect:/";
		}
		slot.setEvent(event);
		slot.setHorse(horse);
		List<Slot> slots = user.getSlots();
		slots.add(slot);
		userRepository.save(user);
		return "redirect:/";
	}

}
