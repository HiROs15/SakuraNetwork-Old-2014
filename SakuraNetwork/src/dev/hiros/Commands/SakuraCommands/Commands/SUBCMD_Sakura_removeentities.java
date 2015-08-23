package dev.hiros.Commands.SakuraCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import dev.hiros.Commands.SubCommand;

public class SUBCMD_Sakura_removeentities extends SubCommand {
	public SUBCMD_Sakura_removeentities() {
		this.setCommandString("removeentities");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
		
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		for(Entity ent : Bukkit.getWorld(player.getLocation().getWorld().getName()).getEntities()) {
			if(!(ent instanceof Player)) {
				ent.remove();
			}
		}
	}
}
