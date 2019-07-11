package ua.runningbet.repositpries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
	public Event findOneById(int id);

	public boolean existsByName(String name);

	public Event findOneByStatusName(String string);

	public boolean existsByStatusName(String string);

	public List<Event> findByCategoryName(String category);

	public List<Event> findByStatusName(String status);

	public Event findOneByName(String name);

}
