package ua.runningbet.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.runningbet.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public Category findByName(String category);
}
