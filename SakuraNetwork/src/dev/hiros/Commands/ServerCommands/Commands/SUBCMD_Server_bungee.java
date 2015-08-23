package dev.hiros.Commands.ServerCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import dev.hiros.Managers;
import dev.hiros.Commands.SubCommand;

public class SUBCMD_Server_bungee extends SubCommand {
	public SUBCMD_Server_bungee() {
		this.setCommandString("bungee");
		this.setAuthLevel(4);
	}
	
	@Override
	public void HelpClass(Player player) {
		
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		Location loc = Bukkit.getWorld(args[2]).getSpawnLocation();
		player.teleport(loc);
		
		Managers.hubManager.leaveHub(player);
	}
}
