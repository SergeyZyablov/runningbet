package ua.runningbet.valodators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.runningbet.models.Event;
import ua.runningbet.repositpries.EventRepository;
@Component
public class EventValidator implements Validator {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Event event = (Event) target;
		if (eventRepository.existsByName(event.getName())) {
			errors.rejectValue("name", "", "Событие с таким именем уже существует");
		}
	}

}
