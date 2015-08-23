package dev.hiros.Hub;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.hiros.Config.Config;
import dev.hiros.PlayerManager.Rank;
import dev.hiros.PlayerManager.SakuraRank;
import dev.hiros.PlayerManager.Economy.PlayerData;
import dev.hiros.Variables.Chat;

public class HubManager {
	private ScoreboardManager scoreboardManager;
	private Scoreboard scoreboard;
	private Objective objective;
	private Objective buffer;
	String header = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Sakura Network";
	
	public ArrayList<Hub> hubs;
	
	public HubManager() {
		hubs = new ArrayList<Hub>();
	}
	
	public void loadHubs() {
		Config config = Config.get().getFile("/Hub/hubs.yml");
		if(config.getConfig().getBoolean("setup") == false) {
			return;
		}
		
		for(String id : config.getConfig().getConfigurationSection("hubs").getKeys(false)) {
			Hub hub = new Hub(Integer.parseInt(id));
			hubs.add(hub);
		}
	}
	
	public Hub getHub(int id) {
		for(Hub hub : hubs) {
			if(hub.getId() == id) {
				return hub;
			}
		}
		return null;
	}
	
	public Hub getPlayerHub(Player player) {
		for(Hub hub : hubs) {
			for(Player p : hub.getPlayers()) {
				if(p.getName().equals(player.getName())) {
					return hub;
				}
			}
		}
		return null;
	}
	
	public void joinHub(Player player) {
		if(Config.get().getFile("/Hub/hubs.yml").getConfig().getBoolean("setup") == false) {
			return;
		}
		
		if(this.getPlayerHub(player) != null) {
			return;
		}
		
		int randomHub = (int) Math.ceil(Math.random()*hubs.size());
		
		//Configure the player settings
		
		if(this.getHub(randomHub).getCurrentPlayers() == 0) {
			resetEntities();
		}
		
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		this.getHub(randomHub).join(player);
		
		player.teleport(this.getHub(randomHub).getLocation());
		
		updateVisiblePlayers(player);
		setupPlayerInventory(player);
		resetScoreboard();
		player.setScoreboard(scoreboard);
		
		this.updatePlayerTags(player);
		
		for(Player p : getPlayerHub(player).getPlayers()) {
			updateBoard(p);
		}
	}
	
	private void resetEntities() {
		// ONline if needed!
	}
	
	public void updateVisiblePlayers(Player player) {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(getPlayerHub(p) == null) {
				p.hidePlayer(player);
				player.hidePlayer(p);
			} else {
				if(getPlayerHub(player).getId() == getPlayerHub(p).getId()) {
					p.showPlayer(player);
					player.showPlayer(p);
				} else {
					p.hidePlayer(player);
					player.hidePlayer(p);
				}
			}
		}
	}
	
	//Scoreboard MEthods Start
	public void resetScoreboard() {
		this.scoreboardManager = Bukkit.getScoreboardManager();
		scoreboard = scoreboardManager.getNewScoreboard();
		objective = scoreboard.registerNewObjective("test", "dummy");
		buffer = scoreboard.registerNewObjective("buffer", "dummy");
		
		objective.setDisplayName(header);
		buffer.setDisplayName(header);
	}
	
	public void addLine(String text, int score) {
		objective.getScore(text).setScore(score);
		buffer.getScore(text).setScore(score);
	}
	
	public void resetObjective() {
		buffer.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.unregister();
		objective = scoreboard.registerNewObjective("test", "dummy");
		objective.setDisplayName(header);
	}
	
	public void resetBuffer() {
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		buffer.unregister();
		buffer = scoreboard.registerNewObjective("buffer", "dummy");
		buffer.setDisplayName(header);
	}
	//Scoreboard Methods END
	
	public void updatePlayerTags(Player player) {
		SakuraRank rank = Rank.get().getRankFromId(Rank.get().getPlayerRank(player));
		String name = player.getName();
		String prefix = "";
		
		switch(rank) {
		case BASIC:
			prefix = "";
			break;
		case VIP:
			prefix = ChatColor.AQUA+""+ChatColor.BOLD+"VIP "+ChatColor.RESET+"";
			break;
		case MOD:
			prefix = ChatColor.BLUE+""+ChatColor.BOLD+"MOD "+ChatColor.RESET+"";
			break;
		case ADMIN:
			prefix = ChatColor.RED+""+ChatColor.BOLD+"ADMIN "+ChatColor.RESET+"";
			break;
		case BUILDTEAM:
			prefix = ChatColor.GREEN+""+ChatColor.BOLD+"BUILDTEAM "+ChatColor.RESET+"";
			break;
		case DEV:
			prefix = ChatColor.DARK_AQUA+""+ChatColor.BOLD+"DEV "+ChatColor.RESET+"";
			break;
		case OVERLORD:
			prefix = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"OVERLORD "+ChatColor.RESET+"";
			break;
		}
		
		player.setPlayerListName(prefix+""+name);
	}
	
	public ArrayList<Hub> getHubs() {
		return this.hubs;
	}
	
	public void updateBoard(Player player) {
		resetObjective();
		addLine("      ", 0);
		addLine(ChatColor.YELLOW+""+ChatColor.BOLD+"Online Staff", 1);
		
		addLine(" ",2);
		
		addLine(this.getPlayerHub(player).getCurrentPlayers()+"/"+this.getPlayerHub(player).getMaxPlayers()+" Players Online", 3);
		addLine(ChatColor.GREEN+""+ChatColor.BOLD+"Players", 4);
		
		addLine("  ", 5);
		
		addLine(PlayerData.get().getCoins(player)+" Coins", 6);
		addLine(ChatColor.GOLD+""+ChatColor.BOLD+"Coins", 7);
		resetBuffer();
	}
	
	public void switchHubs(Player player, int id)
	  {
	    Hub hub = getHub(id);
	    Hub chub = getPlayerHub(player);
	    if (hub.getCurrentPlayers() >= hub.getMaxPlayers())
	    {
	      player.sendMessage(Chat.CommandErrorTag + "That hub server is full.");
	      return;
	    }
	    if (getPlayerHub(player) == null) {
	      return;
	    }
	    
	    if(this.getHub(id).getCurrentPlayers() == 0) {
	    	this.resetEntities();
	    }
	    
	    chub.leave(player);
	    hub.join(player);
	    
	    player.teleport(hub.getLocation());
	    
	    player.getInventory().clear();
	    player.setGameMode(GameMode.ADVENTURE);
	    player.setFoodLevel(20);
	    player.setHealth(20.0D);
	    
	    updatePlayerTags(player);
	    
	    updateVisiblePlayers(player);
	    
	    setupPlayerInventory(player);
	    
	    resetScoreboard();
	    for(Player p : getPlayerHub(player).getPlayers()) {
			updateBoard(p);
		}
	  }
	
	public void setupPlayerInventory(Player player) {
	    player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
	    player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
	    player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
	    player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
	    
		player.getInventory().setItem(0, createItem(Material.COMPASS, 1, ChatColor.GREEN+"Quick Warp "+ChatColor.GRAY+"(Right Click)"));
		
		player.getInventory().setItem(4, createItem(Material.WATCH, 1, ChatColor.GREEN+"Toggle Players"+ChatColor.GRAY+" (Right Click)"));
		
		player.getInventory().setItem(8, createItem(Material.NETHER_STAR, 1, ChatColor.GREEN+"Hub Selector "+ChatColor.GRAY+"(Right Click)"));
	}
	
	private ItemStack createItem(Material mat, int num, String name) {
		ItemStack item = new ItemStack(mat, num);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>());
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public void leaveHub(Player player) {
		if(this.getPlayerHub(player) == null) {
			return;
		}
		
		this.getPlayerHub(player).leave(player);
	}
}
