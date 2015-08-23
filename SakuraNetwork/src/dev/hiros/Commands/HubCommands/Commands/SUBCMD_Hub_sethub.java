package dev.hiros.Commands.HubCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.SubCommand;
import dev.hiros.Config.Config;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Variables.Chat;

public class SUBCMD_Hub_sethub extends SubCommand {
	public SUBCMD_Hub_sethub() {
		this.setCommandString("sethub");
		this.setAuthLevel(5);
	}
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(Chat.CommandHelpHeader);
		player.sendMessage("/hub sethub <#>");
		player.sendMessage(ChatColor.GRAY+"This will set the hub spawn location.");
		player.sendMessage(ChatColor.RED+"Rank Required: "+ChatColor.RESET+"Developer");
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		if(args.length == 2) {
			player.sendMessage(Chat.CommandErrorTag+"For help use /hub sethub ?.");
			return;
		}
		
		Config config = Config.get().getFile("/Hub/hubs.yml");
		config.getConfig().set("setup", true);
		config.saveConfig();
		
		ConfigLocation.get().saveLocation("/Hub/hubs.yml", "hubs."+args[2]+".spawn", player);
		player.sendMessage(Chat.CommandSuccessTag+"You have successfuly created hub ID: "+args[2]+".");
	}
}
