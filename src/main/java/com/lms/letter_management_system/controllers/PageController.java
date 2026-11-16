package com.lms.letter_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lms.letter_management_system.service.LogoutService;
import com.lms.letter_management_system.service.PageService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller 
public class PageController {
   @GetMapping({"/index", "/rda/index"})
    public String index(HttpSession session) {
            return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Autowired
    PageService ser;

    @GetMapping("/pages")
    @ResponseBody
    public String getPages(HttpSession session) {
//        return ser.getPagesForUser(2 + "");
        Integer uid = (Integer) session.getAttribute("uid");
        return ser.getPagesForUser(uid.toString());
    }

    @Autowired
    LogoutService logout;
    
    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpSession session, HttpServletResponse resp) {
        logout.logout(session, resp);

    }

    @GetMapping("/users")
    public String users() {
        return "user";
    }

    @GetMapping("/userType")
    public String userType() {
        return "userType";
    }

    @GetMapping("/attendance")
    public String attendance() {
        return "attendance";
    }

    @GetMapping("/salarySlip")
    public String sallarySlip() {
//        Integer uid = (Integer) session.getAttribute("uid");
//        sal.getEmpSalary(uid);
        return "salary";
    }

    @GetMapping("/salarySheet")
    public String sallarySheet() {
        return "salarySheet";
    }

    @GetMapping("/enter_salary")
    public String enterSalary() {
        return "enterSalary";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/leave")
    public String leave() {
        return "leave";
    }
}
