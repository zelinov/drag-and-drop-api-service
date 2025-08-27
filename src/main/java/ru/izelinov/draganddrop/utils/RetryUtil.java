package ru.izelinov.draganddrop.utils;

import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class RetryUtil {

  public static <T, E extends Exception> T retry(
      Supplier<T> supplier,
      int maxAttempts,
      long delayMillis,
      Class<E> retryOn
  ) {
    Exception last = null;

    for (var attempt = 1; attempt <= maxAttempts; attempt++) {
      try {
        return supplier.get();
      } catch (Exception e) {
        if (!retryOn.isInstance(e) || attempt == maxAttempts) {
          break;
        }

        last = e;

        try {
          Thread.sleep(delayMillis);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
      }
    }

    throw new RuntimeException(last);
  }

  public static <E extends Exception> void retry(
      Runnable runnable,
      int maxAttempts,
      long delayMillis,
      Class<E> retryOn
  ) {
    retry(
        runnable,
        runnable,
        maxAttempts,
        delayMillis,
        retryOn
    );
  }

  public static <E extends Exception> void retry(
      Runnable runnable,
      Runnable onRetry,
      int maxAttempts,
      long delayMillis,
      Class<E> retryOn
  ) {
    Exception last = null;

    for (var attempt = 1; attempt <= maxAttempts; attempt++) {
      try {
        if (isNull(last)) {
          runnable.run();
        } else {
          onRetry.run();
        }

        return;
      } catch (Exception e) {
        if (!retryOn.isInstance(e) || attempt == maxAttempts) {
          break;
        }

        last = e;

        try {
          Thread.sleep(delayMillis);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
      }
    }

    throw new RuntimeException(last);
  }
}
