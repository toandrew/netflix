package com.amazon.mas.kiwi.util;

public class ApkInvalidException extends RuntimeException {

    ApkInvalidException() {
    }

    ApkInvalidException(String s) {
        super(s);
    }

    ApkInvalidException(String s, Throwable throwable) {
        super(s, throwable);
    }

    ApkInvalidException(Throwable throwable) {
        super(throwable);
    }
}
