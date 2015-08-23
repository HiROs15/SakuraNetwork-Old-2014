package dev.hiros.Commands.SakuraCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Commands.BaseCommand;

public class CMD_Sakura extends BaseCommand {
	public CMD_Sakura() {
		this.setCommandString("sakura");
		this.setAuthLevel(3);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(ChatColor.YELLOW+"--------------- "+ChatColor.GOLD+""+ChatColor.BOLD+"Command Help "+ChatColor.RESET+""+ChatColor.YELLOW+"---------------");
		player.sendMessage("/sakura setrank"+ChatColor.RED+" Change a players rank.");
	}
}
