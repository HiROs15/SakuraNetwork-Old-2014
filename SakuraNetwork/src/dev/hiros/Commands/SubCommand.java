package dev.hiros.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.PlayerManager.Rank;

public abstract class SubCommand {
	private String commandString;
	private int authlevel;
	
	public abstract void ExecuteClass(Player player, String[] args);
	
	/*
	 * - Command Header -
	 * Command Use
	 * (Gray) Description
	 * (Red) Rank Required
	 */
	
	public abstract void HelpClass(Player player);
	
	public void CommandExecute(Player player, String[] args) {
		if(Rank.get().getPlayerRank(player) < getAuthLevel()) {
			player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"NO PERMISSION "+ChatColor.RESET+""+ChatColor.GRAY+"You do not have access to this command.");
			return;
		}
		
		if(args.length > 2 && (args[2].equals("?") || args[2].equalsIgnoreCase("help"))) {
			HelpClass(player);
			return;
		}
		
		ExecuteClass(player, args);
	}
	
	public void setCommandString(String s) {
		commandString = s;
	}
	
	public void setAuthLevel(int auth) {
		authlevel = auth;
	}
	
	public String getCommandString() {
		return commandString;
	}
	
	public int getAuthLevel() {
		return authlevel;
	}
}
