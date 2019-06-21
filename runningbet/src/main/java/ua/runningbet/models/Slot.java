package ua.runningbet.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	@ManyToMany
	private List<User> users;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "event_slot", joinColumns = {
			@JoinColumn(name = "bet_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "slot_id", nullable = false, updatable = false) })
	private List<Event> events;
}
