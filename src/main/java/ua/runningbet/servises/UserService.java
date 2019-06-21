package ua.runningbet.servises;

import java.util.Optional;

import ua.runningbet.models.User;

public interface UserService {
	void save(User user);

	Optional<User> findByLogin(String login);
}