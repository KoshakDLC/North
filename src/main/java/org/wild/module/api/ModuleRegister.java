package org.wild.module.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import ru.metaculture.protection.Category;
import ru.metaculture.protection.ModuleRiskLevel;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleRegister {
   String name();

   String description();

   Category category();

   ModuleRiskLevel[] riskLevels() default {};
}
