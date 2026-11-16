package com.lms.letter_management_system.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    public void logout(HttpSession session, HttpServletResponse resp) {
        try {
            session.removeAttribute("uid");
            resp.sendRedirect("login");
        } catch (Exception e) {
        }

    }
}
