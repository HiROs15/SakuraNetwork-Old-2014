package dev.hiros.Commands.ServerCommands;

import org.bukkit.entity.Player;

import dev.hiros.Commands.BaseCommand;

public class CMD_Server extends BaseCommand {
	public CMD_Server() {
		this.setCommandString("server");
		this.setAuthLevel(4);
	}
	
	@Override
	public void HelpClass(Player player) {
		
	}
}
