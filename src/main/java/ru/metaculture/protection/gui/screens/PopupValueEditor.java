package ru.metaculture.protection;

import org.wild.module.api.Module;

@FunctionalInterface
public interface PopupValueEditor {
   void openForSetting(Module module, Setting setting, double d, double e, Object object);
}
