package com.ontey.listener;

import com.ontey.files.Tab;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Collection;

public class TabListener implements Listener {
   
   private Collection<String> commands;
   
   @EventHandler
   public void onTabComplete(PlayerCommandSendEvent event) {
      commands = event.getCommands();
      
      if(Tab.removeNamespacedCommands())
         commands.removeIf(cmd -> cmd.contains(":"));
      
      handleTabRemoval();
   }
   
   private void handleTabRemoval() {
      switch(Tab.getType()) {
         case BLACKLIST -> commands.removeAll(Tab.getTab());
         case WHITELIST -> {
            commands.clear();
            commands.addAll(Tab.getTab());
         }
      }
   }
}
