package ru.izelinov.draganddrop.utils.identity;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.UUID;
import ru.izelinov.draganddrop.utils.exception.InvalidArgumentException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class GlobalId extends AbstractId<UUID> {

  public GlobalId() {
    super(UUID.randomUUID());
  }

  public GlobalId(Object identity) throws InvalidArgumentException {
    super(parse(identity));
  }

  public static boolean canBeId(Object identity) {
    try {
      return nonNull(parse(identity));
    } catch (RuntimeException ignore) {
      return false;
    }
  }

  public byte[] toBinary() {
    final var byteBuffer = ByteBuffer.wrap(new byte[16]);

    byteBuffer.putLong(identity.getMostSignificantBits());
    byteBuffer.putLong(identity.getLeastSignificantBits());

    return byteBuffer.array();
  }

  public UUID getIdentity() {
    return identity;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    return nonNull(object) &&
        object instanceof GlobalId id &&
        identity.equals(id.identity);
  }

  protected static UUID parse(Object identity) throws InvalidArgumentException {
    if (isNull(identity)) {
      throw new InvalidArgumentException("UUID id must not be null.");
    } else if (identity instanceof UUID id) {
      return id;
    } else if (identity instanceof GlobalId id) {
      return id.getIdentity();
    }

    try {
      if (identity instanceof byte[] id) {
        return byteArrayToUUID(id);
      }

      return UUID.fromString(identity.toString().trim());
    } catch (BufferUnderflowException | IllegalArgumentException e) {
      throw new InvalidArgumentException("Invalid UUID id: " + identity + ".", e);
    }
  }

  protected static UUID byteArrayToUUID(byte[] identity) throws BufferUnderflowException {
    final var byteBuffer = ByteBuffer.wrap(identity);
    final long high = byteBuffer.getLong();
    final long low = byteBuffer.getLong();

    return new UUID(high, low);
  }
}
