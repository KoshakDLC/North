package ru.metaculture.protection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HudElementInfo {
   String resolve();

   String resolve2();
}
