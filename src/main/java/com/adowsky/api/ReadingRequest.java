package com.adowsky.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReadingRequest {
    private Date date;
    private Progression progression;

    public enum Progression {
        START, END
    }
}
