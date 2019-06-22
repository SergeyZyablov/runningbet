package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Jockey;

public interface JockeyRepository extends JpaRepository<Jockey, Integer> {
	public Jockey findByName(String name);

	public Jockey findOneByid(Integer id);
}
