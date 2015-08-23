package dev.hiros.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.PlayerManager.Rank;

public abstract class BaseCommand {
	private ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
	private String commandString;
	private int authlevel;
	
	public abstract void HelpClass(Player player);
	
	public BaseCommand setCommandString(String s) {
		this.commandString = s;
		return this;
	}
	
	public String getCommandString() {
		return this.commandString;
	}
	
	public void setAuthLevel(int auth) {
		authlevel = auth;
	}
	
	public int getAuthLevel() {
		return authlevel;
	}
	
	public BaseCommand addSubCommand(SubCommand subcmd) {
		subCommands.add(subcmd);
		return this;
	}
	
	public void CommandExecute(Player player, String[] args) {
		if(Rank.get().getPlayerRank(player) < getAuthLevel()) {
			player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"NO PERMISSION "+ChatColor.RESET+""+ChatColor.GRAY+"You do not have access to this command.");
			return;
		}
		if(args.length == 1) {
			// Display Help Message
			HelpClass(player);
			return;
		}
		for(SubCommand cmd : subCommands) {
			if(cmd.getCommandString().equalsIgnoreCase(args[1])) {
				cmd.CommandExecute(player, args);
				return;
			}
		}
		player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"ERROR "+ChatColor.RESET+""+ChatColor.GRAY+"Could not find that command.");
		return;
	}
}
