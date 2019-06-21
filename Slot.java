package ua.runningbet.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(schema = "runningbet", name = "slot")
@Data
public class Slot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	private Float coefficient;
	private Float bet;
	
	@ManyToOne
	private Horse horse;
	
	@ManyToMany(mappedBy="slots")
	private List<User> users;
	
	@ManyToOne
	private Event event;
}
