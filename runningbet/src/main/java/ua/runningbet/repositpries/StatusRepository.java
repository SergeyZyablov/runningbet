package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
