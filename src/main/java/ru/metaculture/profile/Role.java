package ru.metaculture.profile;

public enum Role {
   DEFAULT(0),
   USER(1),
   MEDIA(2),
   SUPPORT(3),
   MODERATOR(4),
   ADMIN(5),
   OWNER(6);

   private final int level;

   private Role(int j) {
      this.level = j;
   }

   public int getLevel() {
      return this.level;
   }

   public boolean isAtLeast(Role role) {
      return role == null || this.level >= role.level;
   }
}
