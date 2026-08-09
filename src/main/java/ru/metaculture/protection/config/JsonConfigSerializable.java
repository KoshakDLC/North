package ru.metaculture.protection;

import com.google.gson.JsonObject;

public interface JsonConfigSerializable {
   JsonObject resolve();

   void invoke(JsonObject jsonObject);
}
