package com.controller;

import com.model.Resident;
import com.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ResidentController {

	@Autowired
	private ResidentService residentService;

	@GetMapping("/resident")
	public String showLogin(@RequestParam(value = "message", required = false) String message, Model model) {
		if (message != null) {
			model.addAttribute("message", message);
		}
		return "resident/login";
	}

	@PostMapping("/resident/login")
	public String doLogin(@RequestParam String username, @RequestParam String password, Model model,
			HttpSession session) {

		Optional<Resident> auth = residentService.authenticate(username, password);
		if (auth.isPresent()) {
			Resident resident = auth.get();
			session.setAttribute("resident", resident);
			model.addAttribute("message", "Login successful");
			
			return "redirect:/resident/complaint/list";
		}

		if (!residentService.existsByUsername(username)) {

			return "redirect:/resident/signup?username=" + username;
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "resident/login";
		}
	}

	@GetMapping("/resident/signup")
	public String showSignup(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "error", required = false) String error, Model model) {
		if (username != null)
			model.addAttribute("username", username);
		if (error != null)
			model.addAttribute("error", error);
		return "resident/signup";
	}

	@PostMapping("/resident/signup")
	public String doSignup(@RequestParam String username, @RequestParam String password,
			@RequestParam String confirmPassword, Model model) {

		if (username == null || username.isBlank()) {
			model.addAttribute("error", "Username is required");
			return "resident/signup";
		}

		if (residentService.existsByUsername(username)) {
			model.addAttribute("error", "Username already taken");
			model.addAttribute("username", username);
			return "resident/signup";
		}

		if (!password.equals(confirmPassword)) {
			model.addAttribute("error", "Passwords do not match");
			model.addAttribute("username", username);
			return "resident/signup";
		}

		residentService.register(username, password);

		return "redirect:/resident?message=signup_success";
	}

}
