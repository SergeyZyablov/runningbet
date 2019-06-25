package ua.runningbet.valodators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.runningbet.models.Jockey;
import ua.runningbet.repositpries.JockeyRepository;

@Component
public class JockeyValidator implements Validator {
	@Autowired
	private JockeyRepository jockeyRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Jockey.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Jockey jockey = (Jockey) target;
		if (jockeyRepository.existsByName(jockey.getName())) {
			errors.rejectValue("name", "", "Жокей с таким именем уже существует");
		}
	}

}
