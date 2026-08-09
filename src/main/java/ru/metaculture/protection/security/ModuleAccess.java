package ru.metaculture.protection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ru.metaculture.profile.Role;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ModuleAccess {
   Role minimumRole() default Role.DEFAULT;

   Role[] roles() default {};

   String[] usernames() default {};

   int[] uids() default {};
}
