package ua.runningbet.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.runningbet.models.Role;
import ua.runningbet.models.User;
import ua.runningbet.repositpries.RoleRepository;
import ua.runningbet.repositpries.UserRepository;

@Controller
public class AdminController {
	private int page = 0;
	private final int PAGE_SIZE = 15;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping(value = "/admin")
	public String statisticsPage(Model model) {
		List<User> users = userRepository.findAll();
		int registratedUsers = users.size();
		model.addAttribute("registratedUsers", registratedUsers);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "admin";
	}

	@GetMapping(value = "/admin/users")
	public String usersPage(Model model) {
		Pageable pageNumber = PageRequest.of(page, PAGE_SIZE);
		Page<User> pageabledUsers = userRepository.findAll(pageNumber);
		model.addAttribute("users", pageabledUsers);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "users";
	}

	@GetMapping(value = "/admin/users/sort/blocked")
	public String sortBlocked(Model model) {
		Sort blockedStart = new Sort(Sort.Direction.ASC, "blocked");
		List<User> users = userRepository.findByBlocked("true", blockedStart);
		model.addAttribute("users", users);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "users";
	}

	@GetMapping(value = "/admin/users/sort/unblocked")
	public String sortUnlocked(Model model) {
		Sort blockedStart = new Sort(Sort.Direction.ASC, "blocked");
		List<User> users = userRepository.findByBlocked("false", blockedStart);
		model.addAttribute("users", users);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "users";
	}

	@GetMapping(value = "/admin/users/find")
	public String findUser(String userLogin, Model model) {
		User user = userRepository.findOneByLogin(userLogin);
		model.addAttribute("users", user);
		model.addAttribute("header", "fragments/header");
		model.addAttribute("buttons", "fragments/adminButtons");
		return "users";
	}

	@GetMapping("/admin/users/next")
	public String nextPage() {
		page++;
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/prev")
	public String prevPage() {
		if (page > 0) {
			page--;
		}
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/user/lock")
	public String lockUser(@ModelAttribute("userLogin") String userLogin) {
		User user = userRepository.findOneByLogin(userLogin);
		user.setBlocked("true");
		userRepository.save(user);
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/user/unlock")
	public String unlockUser(@ModelAttribute("userLogin") String userLogin) {
		User user = userRepository.findOneByLogin(userLogin);
		user.setBlocked("false");
		userRepository.save(user);
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/user/role/up")
	public String addRoleAdmin(String userLogin, Model model) {
		User user = userRepository.findOneByLogin(userLogin);
		List<Role> roles = new ArrayList<>();
		Role role = roleRepository.findOneByRole("admin");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		return "redirect:/admin/users";
	}

	@PostMapping("/admin/user/role/down")
	public String addRoleUser(String userLogin, Model model) {
		User user = userRepository.findOneByLogin(userLogin);
		List<Role> roles = new ArrayList<>();
		Role role = roleRepository.findOneByRole("user");
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		return "redirect:/admin/users";
	}
}
