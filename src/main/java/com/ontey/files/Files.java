package com.ontey.files;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Files {
   
   @NotNull
   public static List<String> getList(ConfigurationSection config, String path) {
      List<?> list = config.getList(path);
      if(list == null)
         return new ArrayList<>(0);
      List<String> out = new ArrayList<>();
      
      for(Object obj : list)
         out.add(obj == null ? "" : obj.toString());
      return out;
   }
   
   @NotNull
   public static List<String> getField(ConfigurationSection config, String path) {
      if(config.isList(path))
         return getList(config, path);
      String singleton = config.getString(path);
      return List.of(singleton == null ? "" : singleton);
   }
}
