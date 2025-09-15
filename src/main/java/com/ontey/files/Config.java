package com.ontey.files;

import static com.ontey.Main.instance;

import com.ontey.log.Log;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.HashMap;

public class Config {
   
   public static File file;
   
   public static YamlConfiguration config;
   
   private Config() { }
   
   public static void load() {
      file = new File(instance.getDataFolder(), "config.yml");
      
      if(!file.exists())
         instance.saveResource("config.yml", false);
      
      config = new YamlConfiguration();
      config.options().parseComments(true);
      
      try {
         config.load(file);
      } catch(Exception e) {
         Log.error(
           "+-+-+-+-+-+-+-+-+-+-+-+-Command Actions-+-+-+-+-+-+-+-+-+-+-+-+-+",
           "  Couldn't load the config file.",
           "  Look at the stack-trace below, so you can identify the error.",
           "  There is probably a syntax error in the yml.",
           "  Fix the error, then restart the server and it will work again.",
           "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"
         );
         //noinspection CallToPrintStackTrace
         e.printStackTrace();
      }
      
      loadConstants();
   }
   
   public static void save() {
      try {
         config.save(file);
      } catch(Exception e) {
         Log.error(
           "+-+-+-+-+-+-+-+-+-+-+-+-Command Actions-+-+-+-+-+-+-+-+-+-+-+-+-+",
           "  Couldn't save the config file.",
           "  If the file doesn't exist anymore, restart the server",
           "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"
         );
         //noinspection CallToPrintStackTrace
         e.printStackTrace();
      }
   }
   
   public static void set(String path, Object value) {
      config.set(path, value);
      save();
   }
   
   @Contract("_,!null->!null")
   public static <T> T getOrDefault(String path, T fallback) {
      if(config.get(path) == null)
         return fallback;
      try {
         // noinspection unchecked
         return (T) config.getObject(path, fallback.getClass());
      } catch(ClassCastException e) {
         return fallback;
      }
   }
   
   public static boolean isListenerActive(String name) {
      String path = "listeners." + name;
      return config.getBoolean(path, false);
   }
   
   private static void loadConstants() {
      LOG_PREFIX = getOrDefault("format.plugin-prefix", "[CmdActions]");
      setActiveListeners();
   }
   
    private static void setActiveListeners() {
      ACTIVE_LISTENERS.put("tab", isListenerActive("tab"));
   }
   
   public static String LOG_PREFIX;
   public static HashMap<String, Boolean> ACTIVE_LISTENERS = new HashMap<>();
}
