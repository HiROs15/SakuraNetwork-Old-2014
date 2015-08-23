package dev.hiros.Commands.FFACommands.Commands;

import org.bukkit.entity.Player;

import dev.hiros.Commands.SubCommand;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Variables.Chat;

public class SUBCMD_ffa_setspawn extends SubCommand {
	public SUBCMD_ffa_setspawn() {
		this.setCommandString("setspawn");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(Chat.CommandHelpHeader);
		player.sendMessage("/ffa setspawn <Map Name> <Arena #> <Spawn #>");
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		ConfigLocation.get().saveLocation("/FFA/ffa.yml", "maps."+args[2]+"."+args[3]+".spawns."+args[4]+"", player);
		
		player.sendMessage(Chat.CommandSuccessTag+"You have set a spawn in Arena #: "+args[3]+" with the spawn #: "+args[4]+".");
	}
}
