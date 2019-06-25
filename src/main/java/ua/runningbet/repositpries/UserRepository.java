package ua.runningbet.repositpries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByLogin(String login);

	User findOneByLogin(String login);

	boolean existsByLogin(String login);

	boolean existsByEmail(String email);

}
