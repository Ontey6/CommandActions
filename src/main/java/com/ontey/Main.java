package com.ontey;

import com.ontey.files.Config;
import com.ontey.files.Tab;
import com.ontey.listener.TabListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   
   public static Main instance;
   
   public static final String version = "1.0";
   
   @Override
   public void onEnable() {
      instance = this;
      load();
   }
   
   private void load() {
      Config.load();
      Tab.load();
      loadEvents();
   }
   
   private void loadEvents() {
      getServer().getPluginManager().registerEvents(new TabListener(), this);
   }
   
   public static void disablePlugin() {
      instance.getServer().getPluginManager().disablePlugin(instance);
   }
}