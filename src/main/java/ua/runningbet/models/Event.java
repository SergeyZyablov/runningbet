package ua.runningbet.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(schema = "runningbet", name = "event")
@Data
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@NotEmpty(message = "Поле не заполнено")
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date endDate;

	@OneToMany(mappedBy = "event")
	private List<Slot> slots;

	@ManyToOne
	private Category category;

	@ManyToOne
	private Status status;
}
