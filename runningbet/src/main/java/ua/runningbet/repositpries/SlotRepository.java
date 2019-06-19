package ua.runningbet.repositpries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;

public interface SlotRepository extends JpaRepository<Slot, Integer> {
	public List<Slot> findByEvents(Event event);
}
