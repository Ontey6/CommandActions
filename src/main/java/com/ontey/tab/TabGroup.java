package com.ontey.tab;

import com.ontey.files.TabFile.TabType;
import lombok.Builder;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

@Builder
public class TabGroup {
   
   public String name;
   
   public TabType type;
   
   public List<String> list;
   
   public boolean removeNamespaced;
   
   public List<String> removedNamespaces;
   
   public boolean isDefault;
   
   private String permission;
   
   public String getPermission() {
      return permission.replace("<group>", name);
   }
   
   public boolean isPermittedTo(Player player) {
      if(isDefault)
         return !player.hasPermission(getPermission());
      return player.hasPermission(getPermission());
   }
   
   public void apply(Collection<String> tab) {
      if(removeNamespaced)
         tab.removeIf(cmd -> cmd.contains(":"));
      for(String namespace : removedNamespaces)
         tab.removeIf(cmd -> cmd.startsWith(namespace + ":"));
      
      applyList(tab);
   }
   
   private void applyList(Collection<String> tab) {
      switch(type) {
         case BLACKLIST -> tab.removeAll(list);
         case WHITELIST -> {
            tab.clear();
            tab.addAll(list);
         }
      }
   }
}
