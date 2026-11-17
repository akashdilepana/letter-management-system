package com.lms.letter_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DesignationDataTable {
    
    public Integer id;
    public String name;
    public String modBy;
    public String modOn;
    public String status;
    
}
