package com.adowsky.api;

import lombok.Getter;

@Getter
public class BorrowRequest {
    private boolean outside;
    private Type type;

    public enum Type {
        RETURN, BORROW
    }
}
