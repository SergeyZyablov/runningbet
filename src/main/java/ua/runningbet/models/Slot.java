package ua.runningbet.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(schema = "runningbet", name = "slot")
@Data
public class Slot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@NotNull
	private Float coefficient;
	private Float bet;
	private Integer position;
	private String status;

	@ManyToOne
	private Horse horse;

	@ManyToMany(mappedBy = "slots",fetch = FetchType.EAGER)
	private List<User> users;

	@ManyToOne
	private Event event;
}
