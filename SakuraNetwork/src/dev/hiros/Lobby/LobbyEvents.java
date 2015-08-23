package dev.hiros.Lobby;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import dev.hiros.Managers;
import dev.hiros.PlayerManager.Rank;

public class LobbyEvents implements Listener {
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			if(player.getLocation().getY() < 10) {
				player.teleport(Managers.lobbyManager.getPlayerLobby(player).getLocation());
				player.setHealth(20);
				event.setCancelled(true);
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerStave(FoodLevelChangeEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			Managers.lobbyManager.leaveLobby(player);
		}
	}
	
	@EventHandler
	public void onMobSpaw(CreatureSpawnEvent event) {
		World world = event.getLocation().getWorld();
		
		if(world.getName().equals("lobbies")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onRain(WeatherChangeEvent event) {
		World world = event.getWorld();
		
		if(world.getName().equals("lobbies")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerSelectItem(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(player.getItemInHand().getType() == Material.REDSTONE_TORCH_ON) {
					Managers.lobbyManager.leaveLobby(player);
					Managers.hubManager.joinHub(player);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			String message = Rank.get().getPlayerRankName(player) + "" +ChatColor.YELLOW+""+player.getName() + " "+ChatColor.WHITE+""+event.getMessage();
			
			for(Player p : Managers.lobbyManager.getPlayerLobby(player).getPlayers()) {
				p.sendMessage(message);
			}
			event.setCancelled(true);
		}
	}
}
