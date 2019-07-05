package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Horse;

public interface HorseRepository extends JpaRepository<Horse, Integer> {
	public Horse findOneByName(String name);

	public boolean existsByName(String name);

	public Horse findOneById(Integer valueOf);
}
