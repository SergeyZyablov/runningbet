package ua.runningbet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.SlotRepository;

@Controller
public class SlotController {
	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private HorseRepository hourceRepository;

	@PostMapping(value = "/slot/add")
	public String addSlot(String eventId, Slot slot, String hource, Model model) {
		Event event = eventRepository.findOneById(Integer.valueOf(eventId));
		slot.setEvent(event);
		slot.setHorse(hourceRepository.findOneByName(hource));
		slotRepository.save(slot);
		model.addAttribute("event", event);
		model.addAttribute("slots", event.getSlots());
		model.addAttribute("hources", hourceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventPage";
	}

	@PostMapping(value = "/slot/remove")
	public String removeSlot(String id, String eventId, Model model) {
		Event event = eventRepository.findOneById(Integer.valueOf(eventId));
		slotRepository.deleteById(Integer.valueOf(id));
		model.addAttribute("event", event);
		model.addAttribute("slots", event.getSlots());
		model.addAttribute("hources", hourceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "eventPage";
	}

}
