package dev.hiros.Commands;

import java.util.ArrayList;

import dev.hiros.Commands.FFACommands.CMD_ffa;
import dev.hiros.Commands.FFACommands.Commands.SUBCMD_ffa_setmap;
import dev.hiros.Commands.FFACommands.Commands.SUBCMD_ffa_setspawn;
import dev.hiros.Commands.HubCommands.CMD_Hub;
import dev.hiros.Commands.HubCommands.Commands.SUBCMD_Hub_sethub;
import dev.hiros.Commands.HubCommands.Commands.SUBCMD_Hub_spawnentity;
import dev.hiros.Commands.SakuraCommands.CMD_Sakura;
import dev.hiros.Commands.SakuraCommands.Commands.SUBCMD_Sakura_removeentities;
import dev.hiros.Commands.SakuraCommands.Commands.SUBCMD_Sakura_setlobby;
import dev.hiros.Commands.SakuraCommands.Commands.SUBCMD_Sakura_setrank;
import dev.hiros.Commands.ServerCommands.CMD_Server;
import dev.hiros.Commands.ServerCommands.Commands.SUBCMD_Server_bungee;

public class CommandManager {
	public static CommandManager get() {
		return new CommandManager();
	}
	
	private ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();
	
	public CommandManager() {
		commands.add(new CMD_Sakura().addSubCommand(new SUBCMD_Sakura_setrank()).addSubCommand(new SUBCMD_Sakura_setlobby()).addSubCommand(new SUBCMD_Sakura_removeentities()));
		commands.add(new CMD_Hub().addSubCommand(new SUBCMD_Hub_sethub()).addSubCommand(new SUBCMD_Hub_spawnentity()));
		commands.add(new CMD_ffa().addSubCommand(new SUBCMD_ffa_setmap()).addSubCommand(new SUBCMD_ffa_setspawn()));
		commands.add(new CMD_Server().addSubCommand(new SUBCMD_Server_bungee()));
	}
	
	public BaseCommand getCommand(String string) {
		for(BaseCommand cmd : commands) {
			if(cmd.getCommandString().equalsIgnoreCase(string)) {
				return cmd;
			}
		}
		return null;
	}
}
