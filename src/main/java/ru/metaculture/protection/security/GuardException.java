package ru.metaculture.protection;

public final class GuardException extends RuntimeException {
   public GuardException() {
      super((String)null);
   }

   public GuardException(String string) {
      super(string != null && !string.isBlank() ? string : null);
   }
}
