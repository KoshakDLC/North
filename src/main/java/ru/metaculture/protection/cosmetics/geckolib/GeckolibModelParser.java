package ru.metaculture.protection.cosmetics.geckolib;

import ru.metaculture.protection.cosmetics.geo.GeoModel;
import ru.metaculture.protection.cosmetics.geo.GeoModelParser;
import ru.metaculture.protection.cosmetics.model.CosmeticModel;

public final class GeckolibModelParser {
   public GeoModel parseModel(CosmeticModel cosmetic) {
      if (cosmetic == null || cosmetic.getRawModelJson() == null) {
         return null;
      }

      return GeoModelParser.parse(cosmetic.getRawModelJson());
   }
}
