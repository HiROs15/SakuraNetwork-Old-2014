package dev.hiros.Commands.HubCommands;

import org.bukkit.entity.Player;

import dev.hiros.Commands.BaseCommand;

public class CMD_Hub extends BaseCommand {
	public CMD_Hub() {
		this.setCommandString("hub");
		this.setAuthLevel(0);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage("This will add you back into the hub!");
	}
	
}
