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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(schema = "runningbet", name = "user")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@NotEmpty(message = "Поле не заполнено")
	private String name;
	@NotEmpty(message = "Поле не заполнено")
	private String surname;
	@NotEmpty(message = "Поле не заполнено")
	@Email(message = "Почта указана не корректно")
	private String email;
	@NotEmpty(message = "Поле не заполнено")
	private String birthday;
	@NotEmpty(message = "Поле не заполнено")
	@Size(min = 6, max = 50, message = "Логин должен быть больше 6 знаков")
	private String login;
	@NotEmpty(message = "Поле не заполнено")
	@Size(min = 6, max = 50, message = "Пароль должен быть больше 6 знаков")
	private String password;
	private String blocked;
	private Float mony;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private List<Role> roles;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_has_slot", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "slot_id", nullable = false, updatable = false) })
	private List<Slot> slots;
}
