package dev.hiros.Minigames.FFA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.connorlinfoot.titleapi.TitleAPI;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import de.inventivegames.hologram.view.ViewHandler;
import dev.hiros.Managers;
import dev.hiros.SakuraNetwork;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.Instance;
import dev.hiros.Libs.Particles.ParticleEffect;
import dev.hiros.Lobby.Lobby;
import dev.hiros.Minigames.Engine.Minigame;
import dev.hiros.Minigames.Engine.MinigamePlayer;
import dev.hiros.Minigames.Engine.MinigameState;
import dev.hiros.PlayerManager.Rank;
import dev.hiros.PlayerManager.Economy.PlayerData;
import dev.hiros.Variables.Chat;
import dev.hiros.Variables.EconMultipliers;

public class FFAArena extends Minigame {
	private int countdown = 15;
	private int gameTime = 300;
	private int postTime = 15;
	private ArrayList<FFAPlayer> orderPlayers = new ArrayList<FFAPlayer>();
	private FFAArena arena = this;
	private HashMap<String, Hologram> powerUps = new HashMap<String, Hologram>();
	
	public FFAArena(String map, int cloneId, int id, String file) {
		super(map, cloneId, id, file);
		this.loadSpawns();
	}
	
	//Score board methods
	
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board = manager.getNewScoreboard();
	private Objective objective = board.registerNewObjective("pregame", "dummy");
	private Objective buffer = board.registerNewObjective("buffer", "dummy");
	
	private void resetScoreboard() {
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		objective = board.registerNewObjective("pregame", "dummy");
		objective.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Free for All");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	private void addLine(String text, int score) {
		objective.getScore(text).setScore(score);
		buffer.getScore(text).setScore(score);
	}
	
	private void resetObjective() {
		buffer.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Free for All");
		buffer.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.unregister();
		objective = board.registerNewObjective("ffa", "dummy");
	}
	
	private void resetBuffer() {
		objective.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Free for All");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		buffer.unregister();
		buffer = board.registerNewObjective("buffer", "dummy");
	}
	//End Scoreboard methods
	
	private void setupBoard() {
		this.resetScoreboard();
	}
	
	public void updateVisiblePlayers(Player player) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(this.getPlayer(p) != null) {
				p.showPlayer(player);
				player.showPlayer(p);
			}
			else {
				player.hidePlayer(p);
				p.hidePlayer(player);
			}
		}
	}
	
	private void updateBoard(FFAPlayer player) {
		if(getState() == MinigameState.PREGAME) {
			this.resetObjective();
			this.addLine("Use your clock to select a kit!", 0);
			this.addLine(ChatColor.BLUE+""+ChatColor.BOLD+"Quick Message", 1);
			
			this.addLine(" ", 2);
			
			this.addLine(this.getCurrentPlayers()+"/"+((FFAManager)Instance.getManager(GameType.FFA)).getMaxPlayers()+" Players", 3);
			this.addLine(ChatColor.GREEN+""+ChatColor.BOLD+"Online Players", 4);
			
			this.addLine("  ", 5);
			
			this.addLine(this.countdown+" Seconds", 6);
			this.addLine(ChatColor.RED+""+ChatColor.BOLD+"Starting In", 7);
			
			this.addLine("   ", 8);
			
			this.addLine("You are playing on "+this.getMap(), 9);
			this.addLine(ChatColor.AQUA+""+ChatColor.BOLD+"Current Map", 10);
			
			this.addLine("    ", 11);
			
			this.addLine(player.getKit(), 12);
			this.addLine(ChatColor.BLUE+""+ChatColor.BOLD+"Your Kit", 13);
			this.resetBuffer();
		}
		
		else if(getState() == MinigameState.STARTED) {
			int minutes = (this.gameTime % 3600) / 60;
			int seconds = this.gameTime % 60;
			String timeString = String.format("%02d:%02d", minutes, seconds);
			
			this.resetObjective();
			
			String first = (orderPlayers.size() >= 1)?orderPlayers.get(0).getPlayer().getName():"";
			String second = (orderPlayers.size() >= 2)?orderPlayers.get(1).getPlayer().getName():"";
			String third = (orderPlayers.size() >= 3)?orderPlayers.get(2).getPlayer().getName():"";
			
			this.addLine("3rd "+ChatColor.GRAY+""+third , 0);
			this.addLine("2nd "+ChatColor.GRAY+""+second, 2);
			this.addLine("1st "+ChatColor.GRAY+""+first, 3);
			this.addLine(ChatColor.BLUE+""+ChatColor.BOLD+"Leaderboard", 4);
			
			this.addLine(" ", 5);
			
			this.addLine(this.getCurrentPlayers()+"/"+((FFAManager)Instance.getManager(GameType.FFA)).getMaxPlayers()+" Players Online", 6);
			this.addLine(ChatColor.GREEN+""+ChatColor.BOLD+"Players", 7);
			
			this.addLine("  ", 8);
			
			this.addLine("Server-"+this.getMap()+"-"+this.getId(), 9);
			this.addLine(ChatColor.YELLOW+""+ChatColor.BOLD+"Server", 10);
			
			this.addLine("   ", 11);
			
			this.addLine(""+timeString, 12);
			this.addLine(ChatColor.RED+""+ChatColor.BOLD+"Game Time", 13);
			this.resetBuffer();
		}
	}
	
	private void updateGame() {
		if(getState() == MinigameState.PREGAME) {
			
		}
	}
	
	private void calculateEconomy(Player player) {
		String first = (orderPlayers.size() >= 1)?orderPlayers.get(0).getPlayer().getName():"";
		String second = (orderPlayers.size() >= 2)?orderPlayers.get(1).getPlayer().getName():"";
		String third = (orderPlayers.size() >= 3)?orderPlayers.get(2).getPlayer().getName():"";
		
		int coins = 0;
		ArrayList<String> msgs = new ArrayList<String>();
		
		if(player.getName().equals(first)) {
			coins += 25;
			msgs.add(ChatColor.GREEN+"1st Place - " +ChatColor.GRAY+"+25 Coins");
		}
		else if(player.getName().equals(second)) {
			coins += 15;
			msgs.add(ChatColor.GREEN+"2nd Place - "+ChatColor.GRAY+"+15 Coins");
		}
		else if(player.getName().equals(third)) {
			coins += 10;
			msgs.add(ChatColor.GREEN+"3rd Place - "+ChatColor.GRAY+"+10 Coins");
		}
		else {
			coins += 5;
			msgs.add(ChatColor.GREEN+"Participation - "+ChatColor.GRAY+"+5 Coins");
		}
		
		int kills = ((FFAPlayer)getPlayer(player)).getKills();
		
		coins += kills*5;
		msgs.add(ChatColor.GREEN+"Kills Bonus - "+ChatColor.GRAY+""+kills*5+" Coins");
		
		if(Rank.get().getPlayerRank(player) > 0) {
			coins = coins*2;
			msgs.add(ChatColor.GOLD+"Rank Bonus - "+ChatColor.GRAY+"X2 Coins");
		}
		
		if(EconMultipliers.coinM != 1) {
			coins = coins*EconMultipliers.coinM;
			msgs.add(ChatColor.GOLD+"Bonus Event - "+ChatColor.GRAY+"X"+EconMultipliers.coinM+" Coins");
		}
		
		PlayerData.get().addCoins(player, coins);
		
		player.sendMessage(Chat.MinigameEconResultHeader);
		
		for(String m : msgs) {
			player.sendMessage(m);
		}
		player.sendMessage(" ");
		player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Total Coins Received - "+ChatColor.RESET+""+ChatColor.WHITE+""+ChatColor.BOLD+""+coins+" Coins");
	}
	
	public void killHandler(Player player, Player killer) {
		((FFAPlayer)getPlayer(player)).addDeath();
		((FFAPlayer)getPlayer(killer)).addKill();
		
		for(MinigamePlayer p : getPlayers()) {
			p.getPlayer().sendMessage(ChatColor.BLUE+""+killer.getName()+" "+ChatColor.YELLOW+"Killed "+ChatColor.RED+""+player.getName());
		}
		
		this.respawnPlayer(player);
		
		this.updateTabList();
	}
	
	public void playerDeath(Player player) {
		((FFAPlayer)getPlayer(player)).addDeath();
		
		for(MinigamePlayer p : getPlayers()) {
			p.getPlayer().sendMessage(ChatColor.RED+""+player.getName() +" "+ChatColor.YELLOW+"has been killed!");
		}
		
		this.respawnPlayer(player);
		
		this.updateTabList();
	}
	
	public void updateTabList() {
		for(MinigamePlayer p : getPlayers()) {
			FFAPlayer ffaPlayer = (FFAPlayer) p;
			ffaPlayer.getPlayer().setPlayerListName(ChatColor.GRAY+"[ "+ChatColor.AQUA+""+ffaPlayer.getKills()+" "+ChatColor.GRAY+"- "+ChatColor.RED+""+ffaPlayer.getDeaths()+" "+ChatColor.GRAY+"]" +ChatColor.RESET+""+Rank.get().getPlayerRankName(ffaPlayer.getPlayer())+""+ffaPlayer.getPlayer().getName());
		}
	}
	
	public void respawnPlayer(Player player) {
		int random = (int) Math.floor(Math.random()*this.getSpawns().size());
		player.teleport(this.getSpawn(random).add(0,0.25,0));
	}
	
	private void joinMethod(Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				player.setGameMode(GameMode.ADVENTURE);
			}
		}.runTaskLater(SakuraNetwork.plugin, 10L);
		
		setupInventory(player);
		
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setExp(0L);
	}
	
	private void setupInventory(Player player) {
		player.getInventory().clear();
		
		player.getInventory().setItem(0, createItem(new ItemStack(Material.WATCH, 1), ChatColor.BLUE+"Select your Kit", ChatColor.GRAY+"Right click to use this item."));
	}
	
	private ItemStack createItem(ItemStack item, String name, String... lore) {
		ItemStack i = item;
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		
		List<String> l = Arrays.asList(lore);
		
		meta.setLore(l);
		i.setItemMeta(meta);
		
		return i;
	}
	
	public void playerQuit(Player player) {
		this.leave(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player).getPlayer(player));
		
		for(MinigamePlayer p : this.getPlayers()) {
			p.getPlayer().sendMessage(ChatColor.RED+"Leave> "+ChatColor.GRAY+""+player.getName());
		}
		
		if(this.getCurrentPlayers() <= 1) {
			this.cancelGame();
			for(MinigamePlayer p : this.getPlayers()) {
				p.getPlayer().sendMessage(Chat.CommandErrorTag+"Sorry but your game server was forced to close!");
			}
		}
	}
	
	private void cancelGame() {
		countdown = 0;
		gameTime = 0;
		postTime = 0;
		
		for(MinigamePlayer p : this.getPlayers()) {
			this.leave(p);
			Managers.hubManager.joinHub(p.getPlayer());
		}
		
		for(String s : powerUps.keySet()) {
			powerUps.get(s).despawn();
		}
		
		((FFAManager)Instance.getManager(GameType.FFA)).removeArena(this);
	}
	
	private void autoSelectKits() {
		for(MinigamePlayer p : this.getPlayers()) {
			FFAPlayer ffaplayer = (FFAPlayer) p;
			
			if(ffaplayer.getKit().equals("None Selected")) {
				ffaplayer.setKit("Basic");
			}
		}
	}
	
	private void spawnPowerup(String type) {
		int random = (int) Math.floor(Math.random()*((FFAManager)Instance.getManager(GameType.FFA)).getMaxPlayers());
		Location loc = this.getSpawn(random);
		if(type.equals("1")) {
			Hologram h = HologramAPI.createHologram(loc.add(0,1.5,0), ChatColor.AQUA+""+ChatColor.BOLD+"Sword Powerup " +ChatColor.RESET+""+ChatColor.GREEN+"(Touch to Activate)");
			h.addViewHandler(new ViewHandler() {
				@Override
				public String onView(Hologram h, Player player, String str) {
					if(getPlayer(player) == null) {
						return "";
					} else {
						return str;
					}
				}
			});
			h.spawn();
			this.powerUps.put("1", h);
			for(MinigamePlayer p : getPlayers()) {
				p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.SHEEP_SHEAR, 1, 1);
				p.getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"! ATTENTION ! "+ChatColor.RESET+""+ChatColor.AQUA+"A Powerup has spawned somewhere on the map!");
			}
		}
	}
	
	private void fastGameThread() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(gameTime == 0) {
					this.cancel();
				} else {
					for(String s : powerUps.keySet()) {
						ParticleEffect.FLAME.display((float)0.2, (float)0.2, (float)0.2, (float)0.1, 10, powerUps.get(s).getLocation(), getPlayerPlayers());
						ParticleEffect.REDSTONE.display((float)0.2, (float)0.2, (float)0.2, 2, 10, powerUps.get(s).getLocation(), getPlayerPlayers());
					}
				}
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 5L);
	}
	
	private void endGame() {
		countdown = 0;
		gameTime = 0;
		
		String first = (orderPlayers.size() >= 1)?orderPlayers.get(0).getPlayer().getName():"None";
		String second = (orderPlayers.size() >= 2)?orderPlayers.get(1).getPlayer().getName():"None";
		String third = (orderPlayers.size() >= 3)?orderPlayers.get(2).getPlayer().getName():"None";
		
		for(MinigamePlayer p : getPlayers()) {
			TitleAPI.sendTitle(p.getPlayer(), 10, 280, 10, ChatColor.GOLD+""+ChatColor.BOLD+""+first+" "+ChatColor.RESET+""+ChatColor.AQUA+"was 1st place!", ""+ChatColor.YELLOW+"2nd Place - "+ChatColor.WHITE+""+second+"          "+ChatColor.BLUE+"3rd Place - "+ChatColor.WHITE+""+third);
		}
		
		for(String s : powerUps.keySet()) {
			powerUps.get(s).despawn();
		}
	}
	
	@Override
	public void join(MinigamePlayer player) {
		this.getPlayers().add(player);
		joinMethod(player.getPlayer());
	}
	
	@Override
	public void leave(MinigamePlayer player) {
		this.getPlayers().remove(player);
	}
	
	@Override
	public void startGame() {
		this.setState(MinigameState.PREGAME);
		FFAManager manager = ((FFAManager)Instance.getManager(GameType.FFA));
		FFAArena arena = (FFAArena) manager.getArena(this.getId());
		arena.resetScoreboard();
		
		this.setupBoard();
		
		for(MinigamePlayer p : this.getPlayers()) {
			p.getPlayer().setScoreboard(this.board);
			orderPlayers.add((FFAPlayer) p);
		}
		
		this.preThread();
	}
	
	@Override
	public void preThread() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(countdown == 0) {
					for(MinigamePlayer p : getPlayers()) {
						p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
					}
					setState(MinigameState.STARTED);
					this.cancel();
					
					autoSelectKits();
					
					gameThread();
					
					fastGameThread();
					
					resetScoreboard();
					for(MinigamePlayer p : getPlayers()) {
						updateBoard((FFAPlayer) p);
						p.getPlayer().setScoreboard(board);
					}
					
					updateTabList();
				}
				else if(countdown <= 5) {
					countdown -= 1;
					for(MinigamePlayer p : getPlayers()) {
						p.getPlayer().playNote(p.getPlayer().getLocation(), Instrument.PIANO, Note.natural(1, Tone.A));
						updateBoard((FFAPlayer) p);
					}
				}
				else {
					countdown -= 1;
					
					//Update the Scoreboard
					for(MinigamePlayer p : getPlayers()) {
						updateBoard((FFAPlayer) p);
					}
				}
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 20L);
	}
	
	@Override
	public void gameThread() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(getState() == MinigameState.STARTED) {
					if(gameTime == 0) {
						this.cancel();
						
						endGame();
						
						postThread();
						
						setState(MinigameState.STOPPED);
						
						resetScoreboard();
						for(MinigamePlayer p : getPlayers()) {
							updateBoard((FFAPlayer) p);
							p.getPlayer().setScoreboard(board);
							p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
						}
					}
					else if(gameTime <= 11) {
						gameTime -= 1;
						for(MinigamePlayer p : getPlayers()) {
							p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.CLICK, 1, 1);
							TitleAPI.sendTitle(p.getPlayer(), 1, 18, 1, ChatColor.RED+""+gameTime, "");
							updateBoard((FFAPlayer)p);
						}
						Collections.sort(orderPlayers, new FFAPlayerSorter());
					}
					else {
						if(gameTime == 150) {
							spawnPowerup("1");
						}
						gameTime -= 1;
						
						for(MinigamePlayer p : getPlayers()) {
							updateBoard((FFAPlayer) p);
						}
						updateGame();
						Collections.sort(orderPlayers, new FFAPlayerSorter());
					}
				}
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 20L);
	}
	
	@Override
	public void postThread() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(postTime == 0) {
					this.cancel();
					
					Managers.lobbyManager.setupLobby(GameType.FFA, getPlayers().get(0).getPlayer());
					Player host = getPlayers().get(0).getPlayer();			
					calculateEconomy(host);
					leave(getPlayers().get(0));
					
					for(MinigamePlayer p : getPlayers()) {
						Lobby l = Managers.lobbyManager.getPlayerLobby(host);
						Managers.lobbyManager.joinLobby(p.getPlayer(), l.getDefaultId(), l.getId());
						calculateEconomy(p.getPlayer());
					}
					
					((FFAManager)Instance.getManager(GameType.FFA)).removeArena(arena);
				}
				else {
					postTime -= 1;
				}
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 20L);
	}
}
