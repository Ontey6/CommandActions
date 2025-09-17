package com.ontey;

import com.ontey.files.Config;
import com.ontey.files.TabFile;
import com.ontey.listener.TabListener;
import com.ontey.updater.Updater;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
   
   public static Main instance;
   
   public static final String version = "1.0";
   
   public static PluginManager pm;
   
   @Override
   public void onEnable() {
      instance = this;
      pm = getServer().getPluginManager();
      
      load();
      Updater.checkForUpdates();
   }
   
   private void load() {
      Config.load();
      TabFile.load();
      loadEvents();
   }
   
   private void loadEvents() {
      if(Config.isListenerActive("tab"))
         pm.registerEvents(new TabListener(), this);
   }
   
   public static void disablePlugin() {
      instance.getServer().getPluginManager().disablePlugin(instance);
   }
}