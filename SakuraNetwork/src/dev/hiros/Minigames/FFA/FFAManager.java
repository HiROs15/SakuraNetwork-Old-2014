package dev.hiros.Minigames.FFA;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import dev.hiros.SakuraNetwork;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.Item;
import dev.hiros.Minigames.Engine.MinigameManager;

public class FFAManager extends MinigameManager {
	public FFAManager() {
		this.setGameType(GameType.FFA);
		this.setFile("/FFA/ffa.yml");
		this.loadMaps();
		this.setMinPlayers(2);
		this.setMaxPlayers(20);
		this.setVoteManager(new FFAVoteManager("/FFA/ffa.yml"));
		
		this.addLobbyItem(Item.get().createItem(new ItemStack(Material.WATCH, 1), ChatColor.GREEN+"Map Voting "+ChatColor.GRAY+"(Right Click)"), 0);
	}
	
	@Override
	public void startup() {
		Bukkit.getServer().getPluginManager().registerEvents(new FFAEvents(), SakuraNetwork.plugin);
	}
}
