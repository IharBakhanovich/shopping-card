package com.bakhanovich.interviews.shoppingcart.exception;

/**
 * Exception: Thrown if a method-argument is not valid.
 *
 * @author Ihar Bakhanovich.
 */
public class MethodArgumentNotValidException extends AppException{
    /**
     * Constructs a new MethodArgumentNotValidException.
     *
     * @param errorCode    is the code of an error.
     * @param errorMessage is the message of an error.
     */
    public MethodArgumentNotValidException(String errorCode, Object errorMessage) {
        super(errorCode, errorMessage);
    }
}
