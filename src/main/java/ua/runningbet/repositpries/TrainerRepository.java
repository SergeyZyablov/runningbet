package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
	public Trainer findByName(String name);

}
