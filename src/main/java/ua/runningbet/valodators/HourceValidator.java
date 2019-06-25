package ua.runningbet.valodators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.runningbet.models.Horse;
import ua.runningbet.repositpries.HorseRepository;
@Component
public class HourceValidator implements Validator {
	@Autowired
	private HorseRepository hpurceRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Horse.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Horse hource = (Horse) target;
		if (hpurceRepository.existsByName(hource.getName())) {
			errors.rejectValue("name", "", "Лошадь с таким именем уже существует");
		}

	}

}
