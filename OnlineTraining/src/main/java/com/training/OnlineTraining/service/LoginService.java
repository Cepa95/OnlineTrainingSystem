package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.UserDto;
import com.training.OnlineTraining.model.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;


public interface LoginService {
	String processLogin(String username, HttpSession session, Model model, Role role);
}
