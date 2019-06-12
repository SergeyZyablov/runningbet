package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
