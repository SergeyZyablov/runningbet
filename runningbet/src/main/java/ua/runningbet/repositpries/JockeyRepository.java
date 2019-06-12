package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Jockey;

public interface JockeyRepository extends JpaRepository<Jockey, Integer> {

}
