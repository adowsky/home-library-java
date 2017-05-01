package com.adowsky.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Reading {
    private Long bookId;
    private String readerUsername;
    private Date startDate;
    private Date endDate;
}
