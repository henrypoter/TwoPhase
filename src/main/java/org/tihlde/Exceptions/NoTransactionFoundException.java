package org.tihlde.Exceptions;

/**
 * Created by kanin on 04.05.14.
 */
public class NoTransactionFoundException extends Exception {
    public NoTransactionFoundException() {};

    public NoTransactionFoundException(String message) {
        super(message);
    }
}
