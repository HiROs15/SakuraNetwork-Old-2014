package dev.hiros.Lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.hiros.Managers;
import dev.hiros.SakuraNetwork;
import dev.hiros.GameMethods.GameConverts;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Libs.Instance;
import dev.hiros.Minigames.Engine.Minigame;
import dev.hiros.Minigames.Engine.MinigameManager;
import dev.hiros.Minigames.FFA.FFAManager;
import dev.hiros.Minigames.FFA.FFAPlayer;
import dev.hiros.Variables.Chat;

public class Lobby {
	private Location loc;
	private GameType game;
	private int id;
	private ArrayList<Player> players = new ArrayList<Player>();
	private LobbyState state;
	private int minPlayers;
	private int maxPlayers;
	private int defaultId;
	private String map = "Use the clock to vote!";
	private boolean voteActive = true;
	
	private ScoreboardManager scoreManager;
	private Scoreboard scoreboard;
	private Objective objective;
	
	private int countdown = 60;
	private boolean countdownStarted = false;
	
	public Lobby(int id) {
		this.id = id;
		this.defaultId = id;
		this.state = LobbyState.OPEN;
		
		this.loc = ConfigLocation.get().getLocation("/Lobby/lobbies.yml", "lobbies."+id+".spawn");
		
		resetLobbyBoard();
	}
	
	public void loadDataFromMinigame() {
		this.setMaxPlayers(((MinigameManager)Instance.getManager(this.game)).getMaxPlayers());
		this.setMinPlayers(((MinigameManager)Instance.getManager(this.game)).getMinPlayers());
	}
	
	public void setState(LobbyState state) {
		this.state = state;
	}
	
	public String getMap() {
		return this.map;
	}
	
	public void setMap(String map) {
		this.map = map;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getVoteActive() {
		return this.voteActive;
	}
	
	private void setVoteActive(boolean state) {
		this.voteActive = state;
	}
	
	public Scoreboard getLobbyBoard() {
		return this.scoreboard;
	}
	
	public void setGame(GameType game) {
		this.game = game;
	}
	
	public int getDefaultId() {
		return this.defaultId;
	}
	
	public int getMinPlayers() {
		return this.minPlayers;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	private void setMinPlayers(int i) {
		this.minPlayers = i;
	}
	
	private void setMaxPlayers(int i) {
		this.maxPlayers = i;
	}
	
	public void getPlayerCountData() {
		if(getGame() == GameType.FFA) {
			
		}
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public GameType getGame() {
		return this.game;
	}
	
	public int getId() {
		return this.id;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	public LobbyState getState() {
		return this.state;
	}
	
	public void join(Player player) {
		this.players.add(player);
		updateVisiblePlayers(player);
	}
	
	public void leave(Player player) {
		this.players.remove(player);
	}
	
	public int getCurrentPlayers() {
		return this.players.size();
	}
	
	private String getStringState() {
		String ret = "";
		
		switch(getState()) {
		case OPEN:
			ret = "Waiting for more players.";
			break;
		case PREGAME:
			ret = "Game is getting ready to start.";
			break;
		case CLOSED:
			ret = "";
			break;
		case RESETING:
			ret = "";
			break;
		}
		
		return ret;
	}
	
	public boolean containsPlayer(Player player) {
		for(Player p : this.players) {
			if(p.getName().equals(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public void updateVisiblePlayers(Player player) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(player);
			player.hidePlayer(p);
		}
		
		for(Player p : this.players) {
			p.showPlayer(player);
			player.showPlayer(p);
		}
	}
	
	public void updateGameState() {
		if(getCurrentPlayers() == 0) {
			Managers.lobbyManager.closeLobby(getId());
			
			if(getGame() == GameType.FFA) {
				((FFAManager)Instance.getManager(getGame())).getVoteManager().resetVotes();
			}
			return;
		}
		
		if(getCurrentPlayers() < getMinPlayers()) {
			this.countdown = 60;
			this.countdownStarted = false;
			
			setState(LobbyState.OPEN);
			setupLobbyBoard();
		}
		
		if(this.getCurrentPlayers() >= this.getMinPlayers() && getState() == LobbyState.OPEN) {
			setState(LobbyState.PREGAME);
			setupLobbyBoard();
			startCountdown();
		}
		
		if(getState() == LobbyState.PREGAME && (countdownStarted == true && countdown == 0)) {
			countdownStarted = false;
			countdown = 60;
			
			resetLobbyBoard();
			
			if(getGame() == GameType.FFA) {
				((FFAManager)Instance.getManager(GameType.FFA)).setupArena(this.map);
			}
			
			for(Player p : this.players) {
				p.setScoreboard(scoreboard);
				
				if(getGame() == GameType.FFA) {
					FFAManager ffamanager = (FFAManager) Instance.getManager(this.game);
					Minigame arena = ffamanager.getRecentArena();
					p.teleport(arena.getSpawn(arena.getCurrentPlayers()));
					arena.join(new FFAPlayer(p, arena.getSpawn(arena.getCurrentPlayers())));
				}
			}
			
			if(getGame() == GameType.FFA) {
				((FFAManager)Instance.getManager(GameType.FFA)).getRecentArena().startGame();
			}
			
			Managers.lobbyManager.closeLobby(getId());
		}
	}
	
	private void resetLobbyBoard() {
		this.scoreManager = Bukkit.getScoreboardManager();
		this.scoreboard = scoreManager.getNewScoreboard();
		this.objective = scoreboard.registerNewObjective("test", "dummy");
	}
	
	private void setupLobbyBoard() {
		if(getState() == LobbyState.OPEN) {
			objective.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+""+GameConverts.get().getScoreboardName(this.getGame())+" Minigame");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			addScore(objective, ChatColor.WHITE+""+getMaxPlayers()+" Players", 0);
			addScore(objective, ChatColor.BLUE+""+ChatColor.BOLD+"Max Players",1);
			
			addScore(objective, " ", 2);
			
			addScore(objective, getMinPlayers()+" Players", 3);
			addScore(objective, ChatColor.YELLOW+""+ChatColor.BOLD+"Minimum Players", 4);
			
			addScore(objective, "  ", 5);
			
			addScore(objective, getCurrentPlayers()+" Players", 6);
			addScore(objective, ChatColor.GREEN+""+ChatColor.BOLD+"Current Players",7);
			
			addScore(objective, "   ", 8);
			
			addScore(objective, this.getStringState(), 9);
			addScore(objective, ChatColor.GOLD+""+ChatColor.BOLD+"Status",10);
		}
		
		if(getState() == LobbyState.PREGAME) {
			objective.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+""+GameConverts.get().getScoreboardName(this.getGame())+" Minigame");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			addScore(objective, this.getMap(), 0);
			addScore(objective, ChatColor.YELLOW+""+ChatColor.BOLD+"Current Map", 1);
			
			addScore(objective, " ", 2);
			
			addScore(objective, getCurrentPlayers()+"/"+getMaxPlayers()+" Players Online",3);
			addScore(objective, ChatColor.GREEN+""+ChatColor.BOLD+"Current Players", 4);
			
			addScore(objective, "  ", 5);
			
			addScore(objective, countdown+" Seconds",6);
			addScore(objective, ChatColor.GOLD+""+ChatColor.BOLD+"Starting in", 7);
		}
		
		for(Player p : getPlayers()) {
			p.setScoreboard(this.scoreboard);
		}
	}
	
	private void endVote() {
		if(this.getGame() == GameType.FFA) {
			this.setMap(((FFAManager)Instance.getManager(GameType.FFA)).getVoteManager().getVoteResults());
		}
	}
	
	@SuppressWarnings("unused")
	private void cancelGame() {
		resetLobbyBoard();
		for(Player p : players) {
			leave(p);
			p.setScoreboard(scoreboard);
			
			p.sendMessage(Chat.CommandErrorTag+"We are sorry. Theyre were no more servers running for this game mode!");
			
			Managers.hubManager.joinHub(p);
		}
		
		Managers.lobbyManager.closeLobby(this.getId());
	}
	
	private void startVote() {
		if(this.getGame() == GameType.FFA) {
			((MinigameManager)Instance.getManager(this.game)).getVoteManager().setVotingState(true);
		}
	}
	
	private void addScore(Objective obj, String text, int score) {
		obj.getScore(text).setScore(score);
	}
	
	private void startCountdown() {
		countdownStarted = true;
		this.startVote();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(countdownStarted == false) {
					this.cancel();
				}
				if(countdown == 15) {
					setVoteActive(false);
					endVote();
				}
				if(countdown == 0) {
					updateGameState();
					this.cancel();
				}
				else {
					countdown -= 1;
					resetLobbyBoard();
					setupLobbyBoard();
				}
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 20L);
	}
}
