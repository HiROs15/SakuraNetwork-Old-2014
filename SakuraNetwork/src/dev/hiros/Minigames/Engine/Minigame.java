package dev.hiros.Minigames.Engine;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dev.hiros.Config.Config;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Minigames.Engine.Scoreboard.MinigameScoreboard;

public abstract class Minigame {
	private int id;
	private int cloneId;
	private String map;
	private String file;
	private ArrayList<MinigamePlayer> players = new ArrayList<MinigamePlayer>();
	private ArrayList<Location> spawns = new ArrayList<Location>();
	private MinigameState state;
	private MinigameScoreboard board;
	private GameType game;
	
	public Minigame(String map, int cloneId, int id, String file) {
		this.map = map;
		this.cloneId = cloneId;
		this.id = id;
		this.file = file;
		
		this.state = MinigameState.OPEN;
		
		this.loadSpawns();
	}
	
	public void setGameType(GameType type) {
		this.game = type;
	}
	
	public GameType getGameType() {
		return this.game;
	}
	
	public void setScoreboard(MinigameScoreboard board) {
		this.board = board;
	}
	
	public MinigameScoreboard getBoard() {
		return this.board;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCloneId(int id) {
		this.cloneId = id;
	}
	
	public ArrayList<Location> getSpawns() {
		return this.spawns;
	}
	
	public void setMap(String map) {
		this.map = map;
	}
	
	public MinigameState getState() {
		return this.state;
	}
	
	public void setState(MinigameState state) {
		this.state = state;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public void loadSpawns() {
		Config config = Config.get().getFile(this.file);
		
		for(String key : config.getConfig().getConfigurationSection("maps."+this.map+"."+this.cloneId+".spawns").getKeys(false)) {
			Location loc = ConfigLocation.get().getLocation(this.file, "maps."+this.map+"."+this.cloneId+".spawns."+key);
			this.spawns.add(loc);
		}
	}
	
	public MinigamePlayer getPlayer(Player player) {
		for(MinigamePlayer p : this.players) {
			if(p.getPlayer().getName().equals(player.getName())) {
				return p;
			}
		}
		return null;
	}
	
	public Location getSpawn(int index) {
		System.out.println("Size: "+spawns.size());
		return this.spawns.get(index);
	}
	
	public int getCurrentPlayers() {
		return this.players.size();
	}
	
	public ArrayList<MinigamePlayer> getPlayers() {
		return this.players;
	}
	
	public ArrayList<Player> getPlayerPlayers() {
		ArrayList<Player> pl = new ArrayList<Player>();
		
		for(MinigamePlayer p : getPlayers()) {
			pl.add(p.getPlayer());
		}
		
		return pl;
	}
	
	public abstract void join(MinigamePlayer player);
	
	public abstract void leave(MinigamePlayer player);
	
	public abstract void startGame();
	
	public abstract void preThread();
	
	public abstract void gameThread();
	
	public abstract void postThread();
	
	public int getId() {
		return this.id;
	}
	
	public String getMap() {
		return this.map;
	}
}
