package com.ontey.listener;

import com.ontey.files.TabFile;
import com.ontey.tab.TabGroup;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Collection;
import java.util.List;

public class TabListener implements Listener {
   
   @EventHandler
   public void onTabComplete(PlayerCommandSendEvent event) {
      apply(event.getPlayer(), event.getCommands());
   }
   
   private void apply(Player player, Collection<String> tab) {
      if(player.hasPermission("commandactions.tab-all") && !player.isOp())
         return;
      
      List<TabGroup> groups = TabFile.getGroups();
      
      groups.removeIf(group -> !group.isPermittedTo(player));
      
      for(TabGroup group : groups)
         group.apply(tab);
   }
}