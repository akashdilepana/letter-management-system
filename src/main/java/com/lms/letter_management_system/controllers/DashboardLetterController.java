package com.lms.letter_management_system.controllers;

import com.lms.letter_management_system.dto.LetterChartData;
import com.lms.letter_management_system.dto.LetterCounts;
import com.lms.letter_management_system.service.LetterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardLetterController {

    private final LetterService letterService;

    public DashboardLetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    @GetMapping("/letter-stats")
    public LetterCounts getLetterCounts() {
        return letterService.getLetterCounts();
    }

    @GetMapping("/letter-chart")
    public LetterChartData getLetterChart(@RequestParam(defaultValue = "day") String period) {
        return letterService.getLetterChart(period);
    }
}
