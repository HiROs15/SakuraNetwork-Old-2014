package dev.hiros.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvents implements Listener {
	@EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		
		String preargs = event.getMessage();
		preargs = preargs.replace("/", "");
		String[] args = preargs.split(" ");
		
		if(CommandManager.get().getCommand(args[0]) != null) {
			CommandManager.get().getCommand(args[0]).CommandExecute(player, args);
			event.setCancelled(true);
		} else if(!player.isOp()) {
			player.sendMessage(ChatColor.RED+"This command could not be found in the Sakura Network server.");
			event.setCancelled(true);
		}
	}
}
