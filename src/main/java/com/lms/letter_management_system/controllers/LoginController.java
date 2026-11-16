package com.lms.letter_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lms.letter_management_system.model.User;
import com.lms.letter_management_system.service.LoginService;
import com.lms.letter_management_system.service.LogoutService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public String checkLogin(@RequestParam String username, HttpSession session) {
        User user = loginService.checkLogin(username);
//        System.out.println(user);
        if (user == null) {
            return "error";
        } else {

            session.setAttribute("uid", user.getId());
            session.setAttribute("username", username);
            session.setAttribute("name", user.getFull_name());
            session.setAttribute("user_type", user.getUserType());

            return "ok";

        }

    }

}
