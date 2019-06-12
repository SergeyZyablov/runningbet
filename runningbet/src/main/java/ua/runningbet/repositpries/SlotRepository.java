package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Slot;

public interface SlotRepository extends JpaRepository<Slot, Integer> {

}
