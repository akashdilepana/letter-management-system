package com.lms.letter_management_system.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DivisionLvlDesDataTable {
    private Integer id;
    private String name;
    // The table query returns the modifier's username, not the numeric user ID.
    private String modBy;
    private LocalDateTime modOn;
    private String status;
}
