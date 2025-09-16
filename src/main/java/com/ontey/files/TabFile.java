package com.ontey.files;

import static com.ontey.Main.instance;
import com.ontey.log.Log;
import com.ontey.tab.TabGroup;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Set;

public class TabFile {
   
   public static File file;
   
   public static YamlConfiguration config;
   
   private TabFile() { }
   
   public static void load() {
      file = new File(instance.getDataFolder(), "tab.yml");
      
      if(!file.exists())
         instance.saveResource("tab.yml", false);
      
      config = new YamlConfiguration();
      config.options().parseComments(true);
      
      try {
         config.load(file);
      } catch(Exception e) {
         Log.error(
           "+-+-+-+-+-+-+-+-+-+-+-+-Command Actions-+-+-+-+-+-+-+-+-+-+-+-+-+",
           "  Couldn't load the tab file.",
           "  Look at the stack-trace below, so you can identify the error.",
           "  There is probably a syntax error in the yml.",
           "  Fix the error, then restart the server and it will work again.",
           "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+"
         );
         //noinspection CallToPrintStackTrace
         e.printStackTrace();
      }
   }
   
   public static List<TabGroup> getGroups() {
      ConfigurationSection section = config.getConfigurationSection("groups");
      if(section == null)
         return new ArrayList<>(0);
      Set<String> groups = section.getKeys(false);
      List<TabGroup> out = new ArrayList<>();
      
      for(String group : groups) {
         ConfigurationSection sec = section.getConfigurationSection(group);
         if(sec == null)
            continue;
         String name = sec.getString("name", group);
         TabType type = typeOf(section.getString("type", "blacklist"));
         List<String> list = Files.getField(sec, "list");
         boolean removeNamespaced = sec.getBoolean("remove-namespaced", false);
         List<String> removedNamespaces = Files.getField(sec, "removed-namespaces");
         String permission = sec.getString("permission", "commandactions.tab.<group>");
         boolean isDefault = sec.getBoolean("default", false);
         
         TabGroup grp = TabGroup.builder()
           .name(name)
           .type(type)
           .list(list)
           .removeNamespaced(removeNamespaced)
           .removedNamespaces(removedNamespaces)
           .permission(permission)
           .isDefault(isDefault)
           .build();
         
         out.add(grp);
      }
      
      return out;
   }
   
   private static TabType typeOf(@NotNull String str) {
      return str.equalsIgnoreCase("whitelist") ? TabType.WHITELIST : TabType.BLACKLIST;
   }
   
   public enum TabType { WHITELIST, BLACKLIST }
}
