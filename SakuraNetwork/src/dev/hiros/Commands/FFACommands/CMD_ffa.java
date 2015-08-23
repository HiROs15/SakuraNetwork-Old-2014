package dev.hiros.Commands.FFACommands;

import org.bukkit.entity.Player;

import dev.hiros.Commands.BaseCommand;

public class CMD_ffa extends BaseCommand {
	public CMD_ffa() {
		this.setCommandString("ffa");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
	}
}
