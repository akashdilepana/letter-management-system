package com.lms.letter_management_system.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterChartData {
    private List<String> labels;
    private List<Long> allLetters;
    private List<Long> repliedLetters;
    private List<Long> notRepliedLetters;
}
