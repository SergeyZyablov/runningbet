package ua.runningbet.valodators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.runningbet.models.Category;
import ua.runningbet.repositpries.CategoryRepository;

@Component
public class CategoryValidator implements Validator {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Category category = (Category) target;
		if (categoryRepository.existsByName(category.getName())) {
			errors.rejectValue("name", "", "Категория с таким именем существует.");
		}
		if (category.getName().isEmpty()) {
			errors.rejectValue("name", "", "Поле не заполнено");
		}

	}
}
