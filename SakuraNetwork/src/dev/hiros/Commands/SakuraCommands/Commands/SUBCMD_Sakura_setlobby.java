package dev.hiros.Commands.SakuraCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.SubCommand;
import dev.hiros.Config.Config;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Variables.Chat;

public class SUBCMD_Sakura_setlobby extends SubCommand {
	public SUBCMD_Sakura_setlobby() {
		this.setCommandString("setlobby");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(Chat.CommandHelpHeader);
		player.sendMessage("/sakura setlobby <Lobby #>");
		player.sendMessage(ChatColor.GRAY+"This will setup different waiting lobbies for games!");
		player.sendMessage(ChatColor.RED+"Rank Required: "+ChatColor.RESET+"Dev");
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		if(args.length <= 2) {
			player.sendMessage(Chat.CommandErrorTag+"Need Help? Use /sakura setlobby ?");
			return;
		}
		
		Config config = Config.get().getFile("/Lobby/lobbies.yml");
		config.getConfig().set("setup", true);
		config.saveConfig();
		
		ConfigLocation.get().saveLocation("/Lobby/lobbies.yml", "lobbies."+args[2]+".spawn", player);
		
		player.sendMessage(Chat.CommandSuccessTag+"You have successfully created a lobby with the ID: "+args[2]+".");
	}
}
