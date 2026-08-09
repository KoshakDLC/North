package ru.metaculture.protection;

@FunctionalInterface
public interface ClickGuiAction {
   void execute(ClickGuiState clickGuiState);
}
