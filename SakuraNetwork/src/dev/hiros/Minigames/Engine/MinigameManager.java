package dev.hiros.Minigames.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.hiros.Config.Config;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Minigames.FFA.FFAArena;
import dev.hiros.Types.SakuraManager;

public abstract class MinigameManager extends SakuraManager {
	/* *HOW TO USE*
	 * 
	 * ----- Constructor -----
	 * setArenaClass()
	 * setGameType()
	 * setFile()
	 * setMinPlayers()
	 * setMaxPlayers()
	 * loadMaps()
	 */
	private GameType gameType;
	private ArrayList<Minigame> arenas = new ArrayList<Minigame>();
	private ArrayList<String> maps = new ArrayList<String>();
	private Minigame recentCreatedArena;
	private String file;
	private int minPlayers;
	private int maxPlayers;
	private int gameTime;
	private MinigameVoteManager voteManager;
	private HashMap<ItemStack, Integer> lobbyInv = new HashMap<ItemStack, Integer>();
	
	public MinigameManager() {
		this.startup();
	}
	
	public void setVoteManager(MinigameVoteManager voteManager) {
		this.voteManager = voteManager;
	}
	
	public void addLobbyItem(ItemStack item, Integer slot) {
		lobbyInv.put(item, slot);
	}
	
	public HashMap<ItemStack, Integer> getLobbyInv() {
		return this.lobbyInv;
	}
	
	public void setGameItem(int gameTime) {
		this.gameTime = gameTime;
	}
	
	public int getGameTime() {
		return this.gameTime;
	}
	
	public MinigameVoteManager getVoteManager() {
		return this.voteManager;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return this.file;
	}
	
	public Minigame getRecentArena() {
		return this.recentCreatedArena;
	}
	
	public void setMinPlayers(int i) {
		this.minPlayers = i;
	}
	
	public void setMaxPlayers(int i) {
		this.maxPlayers = i;
	}
	
	public int getMinPlayers() {
		return this.minPlayers;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public void setGameType(GameType type) {
		this.gameType = type;
	}
	
	public GameType getGameType() {
		return this.gameType;
	}
	
	public ArrayList<Minigame> getArenas() {
		return this.arenas;
	}
	
	public void addArena(Minigame arena) {
		arenas.add(arena);
	}
	
	public Minigame getArena(int id) {
		for(Minigame m : this.arenas) {
			if(m.getId() == id) {
				return m;
			}
		}
		return null;
	}
	
	public void removeArena(Minigame arena) {
		arenas.remove(arena);
	}
	
	public void run() {
		System.out.println("Wow it work!");
	}
	
	public void loadMaps() {
		Config config = Config.get().getFile(this.file);
		for(String key : config.getConfig().getConfigurationSection("maps").getKeys(false)) {
			maps.add(key);
		}
	}
	
	public ArrayList<String> getMaps() {
		return this.maps;
	}
	
	public ArrayList<ItemStack> getVoteMaps() {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		for(String map : maps) {
			ItemStack i = new ItemStack(Material.IRON_BLOCK, 1);
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName(ChatColor.BOLD+""+ChatColor.BOLD+""+map);
			List<String> lore = meta.getLore();
			lore.add(ChatColor.GRAY+"Click on this map to cast a vote!");
			meta.setLore(lore);
			i.setItemMeta(meta);
			items.add(i);
		}
		
		return items;
	}
	
	public void setupArena(String map) {
		
		ArrayList<String> avaliableArenas = new ArrayList<String>();
		
		Config config = Config.get().getFile(this.file);
		
		for(String key : config.getConfig().getConfigurationSection("maps."+map+"").getKeys(false)) {
			avaliableArenas.add(key);
		}
		
		int random = (int) Math.ceil(Math.random()*avaliableArenas.size());
		
		int hugeRandom = (int) Math.floor(Math.random()*9999999);
		
		if(this.gameType == GameType.FFA) {
			FFAArena arena = new FFAArena(map, random, hugeRandom, this.file);
			this.arenas.add(arena);
			this.recentCreatedArena = arena;
		}
	}
	
	public Minigame getPlayerArena(Player player) {
		for(Minigame m : this.arenas) {
			if(m.getPlayer(player) != null) {
				return m;
			}
		}
		return null;
	}
	
	public abstract void startup();
}
