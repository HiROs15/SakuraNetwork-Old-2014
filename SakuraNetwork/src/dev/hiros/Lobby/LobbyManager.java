package dev.hiros.Lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.hiros.Managers;
import dev.hiros.Config.Config;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.ConfigLocation;
import dev.hiros.Libs.Instance;
import dev.hiros.Minigames.FFA.FFAManager;
import dev.hiros.PlayerManager.Rank;
import dev.hiros.Variables.Chat;

public class LobbyManager {
	private ArrayList<Lobby> defaultLobbies = new ArrayList<Lobby>();
	private ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
	
	public void loadLobbies() {
		Config config = Config.get().getFile("/Lobby/lobbies.yml");
		
		if(config.getConfig().getBoolean("setup") == false) {
			return;
		}
		
		for(String key : config.getConfig().getConfigurationSection("lobbies").getKeys(false)) {
			defaultLobbies.add(new Lobby(Integer.parseInt(key)));
		}
	}
	
	public void setupLobby(GameType game, Player starterPlayer) {
		int random = (int) Math.ceil(Math.random()*defaultLobbies.size());
		int hugeRandom = (int) Math.ceil(Math.random()*9999);
		
		Lobby lobby = new Lobby(random);
		
		lobby.setGame(game);
		lobby.setId(hugeRandom);
		lobby.getPlayerCountData();
		lobby.loadDataFromMinigame();
		
		lobbies.add(lobby);
		
		joinLobby(starterPlayer, random, hugeRandom);
	}
	
	private void setupLobbyInventory(Player player) {
		player.getInventory().clear();
		
		if(this.getPlayerLobby(player).getGame() == GameType.FFA) {
			HashMap<ItemStack, Integer> tempInv = ((FFAManager)Instance.getManager(GameType.FFA)).getLobbyInv();
			
			for(ItemStack i : tempInv.keySet()) {
				player.getInventory().setItem(tempInv.get(i), i);
			}
		}
		
		player.getInventory().setItem(8, createItem(new ItemStack(Material.REDSTONE_TORCH_ON, 1), ChatColor.YELLOW+"Leave Lobby "+ChatColor.GRAY+"(Right Click)"));
	}
	
	private ItemStack createItem(ItemStack item, String name) {
		ItemStack i = item;
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(name);
		i.setItemMeta(meta);
		return i;
	}
	
	public void joinLobby(Player player, int defaultId, int id) {
		if(Managers.hubManager.getPlayerHub(player) != null) {
			Managers.hubManager.leaveHub(player);
		}
		
		Location loc = ConfigLocation.get().getLocation("/Lobby/lobbies.yml", "lobbies."+defaultId+".spawn");
		
		player.getInventory().clear();
		
		player.teleport(loc);
		
		player.setHealth(20);
		player.setFoodLevel(20);
		
		this.getLobby(id).join(player);
		
		this.setupLobbyInventory(player);
		
		for(Player p : this.getLobby(id).getPlayers()) {
			p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"SERVER "+ChatColor.RESET+""+ChatColor.GRAY+""+player.getName()+" has joined the lobby.");
		}
		
		this.getLobby(id).updateVisiblePlayers(player);
		
		this.getLobby(id).updateGameState();
		player.setGameMode(GameMode.ADVENTURE);
		
		updateTabList(player);
		
	}
	
	public void leaveLobby(Player player) {
		if(getPlayerLobby(player) == null) {
			return;
		}
		
		Lobby lobby = getPlayerLobby(player);
		lobby.leave(player);
		lobby.updateGameState();
		
		for(Player p : lobby.getPlayers()) {
			p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"SERVER "+ChatColor.GRAY+""+player.getName()+" has left the lobby.");
		}
	}
	
	public Lobby getLobby(int id) {
		for(Lobby l : this.lobbies) {
			if(l.getId() == id) {
				return l;
			}
		}
		return null;
	}
	
	private void updateTabList(Player player) {
		player.setPlayerListName(Rank.get().getPlayerRankName(player)+""+player.getName());
	}
	
	public void getOpenLobby(GameType game, Player player) {
		for(Lobby l : this.lobbies) {
			if(l.getGame() == game && (l.getState() == LobbyState.OPEN || l.getState() == LobbyState.PREGAME) && l.getCurrentPlayers() < l.getMaxPlayers()) {
				joinLobby(player, l.getDefaultId(), l.getId());
				return;
			}
		}
		setupLobby(game, player);
		return;
	}
	
	public Lobby getPlayerLobby(Player player) {
		for(Lobby l : this.lobbies) {
			if(l.containsPlayer(player)) {
				return l;
			}
		}
		return null;
	}
	
	public void closeLobby(int id) {
		this.lobbies.remove(getLobby(id));
	}
	
	public void openVoteMenu(Player player) {
		Lobby lobby = this.getPlayerLobby(player);
		if(lobby.getState() == LobbyState.OPEN) {
			player.sendMessage(Chat.CommandErrorTag+"Voting is not open at this time. Please wait untill more players join.");
			return;
		}
		if(lobby.getVoteActive() == false) {
			player.sendMessage(Chat.CommandErrorTag+"The map has already been selected. Voting is closed.");
			return;
		}
		if(lobby.getGame() == GameType.FFA) {
			((FFAManager)Instance.getManager(lobby.getGame())).getVoteManager().showMenu(player);
		}
	}
}
