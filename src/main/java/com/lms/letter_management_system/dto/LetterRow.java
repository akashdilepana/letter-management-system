package com.lms.letter_management_system.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LetterRow {
    private Integer id;
    private String sno;
    private Date date;
    private String addressTo;
    private String sentBy;
    private String subject;
    private String letterMyNo;
    private String reply;
    private String replyStatus;
}
