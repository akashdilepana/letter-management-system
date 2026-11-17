package com.lms.letter_management_system.dto;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LetterDetails {
    private Integer id;
    private String sno;
    private Date receivedDate;
    private Date documentDate;
    private String letterMyNo;
    private String addressTo;
    private String sentBy;
    private String subject;
    private List<String> actionTakenBy;
    private boolean replyRequired;
    private String replyLetterMyNo;
    private Date replyDate;
    private String status;
    private String letterFileUrl;
    private String replyFileUrl;
}
