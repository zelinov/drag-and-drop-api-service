package ru.izelinov.draganddrop.utils.exception;

public class InvalidOperationException extends GeneralApplicationException {

  public InvalidOperationException(String message) {
    super(message);
  }

  public InvalidOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
