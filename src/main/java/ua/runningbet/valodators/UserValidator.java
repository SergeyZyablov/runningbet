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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
