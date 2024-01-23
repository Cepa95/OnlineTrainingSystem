package com.training.OnlineTraining.service;

import com.training.OnlineTraining.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<UserDto> getAllUsers();
    List<UserDto> filterUsersByRole(String role);
    void deleteUser(UUID userId);

}