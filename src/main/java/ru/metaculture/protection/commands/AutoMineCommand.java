package ru.metaculture.protection;

import java.util.List;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class AutoMineCommand extends Command {
   public AutoMineCommand() {
      super("automine", "Управление AutoMine", ".automine save");
      this.addCompletionProvider("save", List::of);
   }

   @Compile
   @Override
   public void execute(String[] strings) {}

   static {
      Loader.initialize();
   }
}
