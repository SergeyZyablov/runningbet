package ua.runningbet.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(schema = "runningbet", name = "horse")
@Data
public class Horse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	@OneToMany
	private List<Slot> slots;
	@ManyToOne
	private Jockey jockey;
	@ManyToOne
	private Trainer trainer;

}
