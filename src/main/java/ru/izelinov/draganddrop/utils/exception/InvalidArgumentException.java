package ru.izelinov.draganddrop.utils.exception;

public class InvalidArgumentException extends GeneralApplicationException {

  public InvalidArgumentException(String message) {
    super(message);
  }

  public InvalidArgumentException(String message, Throwable cause) {
    super(message, cause);
  }
}
