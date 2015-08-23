package dev.hiros.Commands.FFACommands.Commands;

import org.bukkit.entity.Player;

import dev.hiros.Commands.SubCommand;
import dev.hiros.Config.Config;
import dev.hiros.Variables.Chat;

public class SUBCMD_ffa_setmap extends SubCommand {
	public SUBCMD_ffa_setmap() {
		this.setCommandString("setmap");
		this.setAuthLevel(5);
	}
	
	@Override
	public void HelpClass(Player player) {
		player.sendMessage(Chat.CommandHelpHeader);
		player.sendMessage("/ffa setmap <Map name> <#>");
	}
	
	@Override
	public void ExecuteClass(Player player, String[] args) {
		String map = args[2];
		int id = Integer.parseInt(args[3]);
		
		Config config = Config.get().getFile("/FFA/ffa.yml");
		config.getConfig().set("maps."+map+"."+id+".setup", true);
		config.saveConfig();
		
		player.sendMessage(Chat.CommandSuccessTag+"You have added the map "+map+ "with the id "+id+".");
	}
}
