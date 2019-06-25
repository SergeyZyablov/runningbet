package ua.runningbet.valodators;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;

@Component
public class SlotValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Slot.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

}
