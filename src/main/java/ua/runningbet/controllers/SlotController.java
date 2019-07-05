package ua.runningbet.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.HorseRepository;
import ua.runningbet.repositpries.SlotRepository;
import ua.runningbet.valodators.SlotValidator;

@Controller
public class SlotController {
	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private HorseRepository hourceRepository;
	@Autowired
	private SlotValidator slotValidator;

	@PostMapping(value = "/slot/add")
	public String addSlot(String eventId, @ModelAttribute @Valid Slot slot, BindingResult bindingResult, String hource,
			Model model, Errors errors) {
		Event event = eventRepository.findOneById(Integer.valueOf(eventId));
		slot.setEvent(event);
		slot.setHorse(hourceRepository.findOneByName(hource));
		slotValidator.validate(slot, errors);
		if (bindingResult.hasErrors()) {
			model.addAttribute("event", event);
			model.addAttribute("slots", event.getSlots());
			model.addAttribute("hources", hourceRepository.findAll());
			model.addAttribute("header", "fragments/header");
			model.addAttribute("buttons", "fragments/adminButtons");
			return "redirect:/event?eventId=" + event.getId();
		}
		List<Slot> slots = event.getSlots();
		for (int i = 0; i < slots.size(); i++) {
			if (slots.get(i).getHorse().getId() == hourceRepository.findOneByName(hource).getId()) {
				return "redirect:/event?eventId=" + event.getId();
			}
		}

		slotRepository.save(slot);
		model.addAttribute("event", event);
		model.addAttribute("slots", event.getSlots());
		model.addAttribute("hources", hourceRepository.findAll());
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "redirect:/event?eventId=" + event.getId();
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
		return "redirect:/event?eventId=" + event.getId();
	}

}
