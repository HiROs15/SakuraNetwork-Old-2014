package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Managers;
import dev.hiros.Commands.SubCommand;
import dev.hiros.Config.Config;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Variables.Chat;

public class SUBCMD_Hub_spawnentity extends SubCommand {
	public SUBCMD_Hub_spawnentity() {
		this.setCommandString("spawnentity");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(Chat.CommandHelpHeader);
		player.sendMessage("/hub spawnentity <Type>");
		player.sendMessage(ChatColor.GRAY+"Spawns an interactive hub enitity!");
		player.sendMessage(ChatColor.RED+"Rank Required:"+ChatColor.RESET+" Dev");
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		String type = args[2];
		
		ConfigLocation.get().saveLocation("/Hub/hub_entities.yml", "entities."+Managers.hubEntityManager.getCurrentEntities()+"", player);
		Config config = Config.get().getFile("/Hub/hub_entities.yml");
		config.getConfig().set("setup", true);
		config.getConfig().set("entities."+Managers.hubEntityManager.getCurrentEntities()+".type", type);
		config.saveConfig();
			
		//Spawn in the entity
		Managers.hubEntityManager.spawnEntity(player.getLocation(), type);
			
		player.sendMessage(Chat.CommandSuccessTag+"You have successfuly created a sales merchant "+type+".");
	}
}
