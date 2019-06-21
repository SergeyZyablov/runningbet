package ua.runningbet.repositpries;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	List<Role> findAllById(Set<Long> singleton);

}
