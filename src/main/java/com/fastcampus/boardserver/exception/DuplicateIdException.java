package com.fastcampus.boardserver.exception;

public class DuplicateIdException extends RuntimeException {
    //Throwble을 상속받지 않고 RuntimeException을 상속을 받아야 런타임시 예외를 던질 수 있음.
    public DuplicateIdException(String msg) {
        super(msg);
    }
}
