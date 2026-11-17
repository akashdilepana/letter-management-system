package com.lms.letter_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterCounts {
    private long allLetters;
    private long repliedLetters;
    private long notRepliedLetters;
}
