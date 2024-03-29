package ua.runningbet.repositpries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Slot;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

	Slot findOneById(Integer valueOf);

	List<Slot> findByHorseName(String name);
}
