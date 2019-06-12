package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
