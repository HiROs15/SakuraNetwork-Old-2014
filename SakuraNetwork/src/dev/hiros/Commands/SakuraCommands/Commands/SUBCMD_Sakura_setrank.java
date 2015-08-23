package dev.hiros.Commands.SakuraCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Managers;
import dev.hiros.SakuraNetwork;
import dev.hiros.Commands.SubCommand;
import dev.hiros.Variables.Chat;

public class SUBCMD_Sakura_setrank extends SubCommand {
	public SUBCMD_Sakura_setrank() {
		this.setCommandString("setrank");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(Chat.CommandHelpHeader);
		player.sendMessage("/sakura setrank <Player> <Auth Level>");
		player.sendMessage(ChatColor.GRAY+"This command will allow you to set the rank level of a player.");
		player.sendMessage(ChatColor.RED+"Rank Required: "+ChatColor.RESET+"Developer");
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		if(args.length != 4) {
			player.sendMessage(Chat.CommandErrorTag+"For help use /sakura setrank ?");
			return;
		}
		Player target = SakuraNetwork.plugin.getServer().getPlayer(args[2]);
		if(target == null) {
			player.sendMessage(Chat.CommandErrorTag+"Could not find that player. Be sure they're online.");
			return;
		}
		int rank = Integer.parseInt(args[3]);
		
		Managers.sakuraDB.update("UPDATE members SET rank='"+rank+"' WHERE username='"+target.getUniqueId()+"';");
		
		player.sendMessage(Chat.CommandSuccessTag+"You have set "+target.getName()+"'s rank level to "+rank+".");
	}
}
