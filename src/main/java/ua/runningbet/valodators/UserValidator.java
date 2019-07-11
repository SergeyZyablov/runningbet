package ua.runningbet.valodators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ua.runningbet.models.User;
import ua.runningbet.repositpries.UserRepository;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void editionValidation(User newUser, User oldUser, Errors errors) {

		if (newUser.getName().isEmpty()) {
			errors.rejectValue("name", "", "Поле не заполнено");
		}
		if (newUser.getSurname().isEmpty()) {
			errors.rejectValue("surname", "", "Поле не заполнено");
		}
		if (newUser.getEmail().isEmpty()) {
			errors.rejectValue("email", "", "Поле не заполнено");
		}
		if (newUser.getBirthday().isEmpty()) {
			errors.rejectValue("birthday", "", "Поле не заполнено");
		}
		if (newUser.getLogin().isEmpty()) {
			errors.rejectValue("login", "", "Поле не заполнено");
		}
		if (newUser.getPassword().isEmpty()) {
			errors.rejectValue("password", "", "Поле не заполнено");
		}
		if (newUser.getPassword().length() < 6) {
			errors.rejectValue("password", "", "Пароль должен быть больше 6 знаков");
		}
		if (newUser.getLogin().length() < 6) {
			errors.rejectValue("login", "", "Логин должен быть больше 6 знаков");
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(newUser.getBirthday());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			GregorianCalendar birthDay = new GregorianCalendar();
			birthDay.setTime(date);
			GregorianCalendar checkDay = new GregorianCalendar();
			int years = checkDay.get(GregorianCalendar.YEAR) - birthDay.get(GregorianCalendar.YEAR);
			int checkMonth = checkDay.get(GregorianCalendar.MONTH);
			int birthMonth = birthDay.get(GregorianCalendar.MONTH);
			if (checkMonth < birthMonth) {
				years--;
			} else if (checkMonth == birthMonth
					&& checkDay.get(GregorianCalendar.DAY_OF_MONTH) < birthDay.get(GregorianCalendar.DAY_OF_MONTH)) {
				years--;
			}
			if (years < 18) {
				errors.rejectValue("birthday", "", "Пользователь должен быть старше 18 лет");

			}
		}

		if (!newUser.getLogin().equals(oldUser.getLogin())) {
			if (userRepository.existsByLogin(oldUser.getLogin())) {
				errors.rejectValue("login", "", "Пользователь с таким логином уже существует");
			}
		}
		if (!newUser.getEmail().equals(oldUser.getEmail())) {
			if (userRepository.existsByEmail(oldUser.getEmail())) {
				errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
			}
		}
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if (userRepository.existsByLogin(user.getLogin())) {
			errors.rejectValue("login", "", "Пользователь с таким логином уже существует");
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(user.getBirthday());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			GregorianCalendar birthDay = new GregorianCalendar();
			birthDay.setTime(date);
			GregorianCalendar checkDay = new GregorianCalendar();
			int years = checkDay.get(GregorianCalendar.YEAR) - birthDay.get(GregorianCalendar.YEAR);
			int checkMonth = checkDay.get(GregorianCalendar.MONTH);
			int birthMonth = birthDay.get(GregorianCalendar.MONTH);
			if (checkMonth < birthMonth) {
				years--;
			} else if (checkMonth == birthMonth
					&& checkDay.get(GregorianCalendar.DAY_OF_MONTH) < birthDay.get(GregorianCalendar.DAY_OF_MONTH)) {
				years--;
			}
			if (years < 18) {
				errors.rejectValue("birthday", "", "Пользователь должен быть старше 18 лет");

			}
		}

	}

}
