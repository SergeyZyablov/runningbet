package ua.runningbet.valodators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.runningbet.models.Trainer;
import ua.runningbet.repositpries.TrainerRepository;

@Component
public class TrainerValidator implements Validator {
	@Autowired
	private TrainerRepository trainerRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Trainer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Trainer trainer = (Trainer) target;
		if (trainerRepository.existsByName(trainer.getName())) {
			errors.rejectValue("name", "", "Тренер с таким именем уже существует.");
		}
	}

}
