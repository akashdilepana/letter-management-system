package com.lms.letter_management_system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.letter_management_system.model.User;
import com.lms.letter_management_system.repository.UserRepo;
import com.lms.letter_management_system.repository.UserTypeRepo;

@Service
public class LoginService {

    @Autowired
    UserRepo serv;
    @Autowired
    UserTypeRepo ut;

    public User checkLogin(String username) {
        return serv.findByUsername(username).orElse(null);
    }

    public String getDashboard(Integer userTypeId) {
        return ut.findById(userTypeId).get().getDashboard();
    }
}
