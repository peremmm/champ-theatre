package com.ninjaTurtles.champtheatre.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class EmailMessageBean {
    private Long employeeId;
    private String to;
    private String subject;
    private String message;
}
