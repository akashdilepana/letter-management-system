package com.lms.letter_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetPagesDTO {

    private Integer id;
    private String dashboard;
    private String name;
//    private String dashboard_name;
    private String allPage;
}
