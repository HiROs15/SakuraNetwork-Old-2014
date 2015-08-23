package dev.hiros;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.hiros.Commands.CommandEvents;
import dev.hiros.Config.ConfigManager;
import dev.hiros.Hub.HubEvents;
import dev.hiros.Libs.Instance;
import dev.hiros.Libs.Entities.Entities;
import dev.hiros.Lobby.LobbyEvents;
import dev.hiros.PlayerManager.PlayerManagerEvents;
import dev.hiros.Server.ServerEvents;

public class SakuraNetwork extends JavaPlugin {
	public static SakuraNetwork plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		//Give the enable command
		this.getServer().getLogger().info("§2-------------------------");
		this.getServer().getLogger().info("§2|§6Sakura Network Enabled§2|");
		this.getServer().getLogger().info("§2-------------------------");
		//End enable command
		
		setupConfigs();
		
		registerEvents();
		
		loadHubs();
		
		//Register Entities
		Entities.registerEntities();
		
		// Load Hub entities
		Managers.hubEntityManager.loadEntities();
		
		//Load lobbies
		Managers.lobbyManager.loadLobbies();
		
		//Minigame Loaders
		this.loadMinigameData();
		
		//Load Minigame Instances
		Instance.loadInstances();
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.kickPlayer(ChatColor.RED+""+ChatColor.BOLD+"Server Restart"+ChatColor.RESET+"\n Sorry for the trouble but your current server was forced to reset!\n"+ChatColor.GREEN+"Please reconnect right away as this only takes a few seconds!");
		}
		//Remove Hub Entities
		Managers.hubEntityManager.removeEntities();
	}
	
	public void setupConfigs() {
		ConfigManager.get().setupConfigs();
	}
	
	public void registerEvents() {
		//Player Manager Events
		this.getServer().getPluginManager().registerEvents(new PlayerManagerEvents(), this);
		
		//Player Command Events
		this.getServer().getPluginManager().registerEvents(new CommandEvents(), this);
		
		//Server Events
		this.getServer().getPluginManager().registerEvents(new ServerEvents(), this);
		
		//Hub Events
		this.getServer().getPluginManager().registerEvents(new HubEvents(), this);
		
		//Lobby Events
		this.getServer().getPluginManager().registerEvents(new LobbyEvents(), this);
		
	}
	
	public void loadHubs() {
		Bukkit.getLogger().info("Access: "+Managers.hubManager.hubs.size());
		Managers.hubManager.loadHubs();
	}
	
	private void loadMinigameData() {
		//Load data for the FFA Minigame
		
	}
}
