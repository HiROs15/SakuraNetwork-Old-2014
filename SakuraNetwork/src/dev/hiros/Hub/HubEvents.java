package dev.hiros.Hub;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import dev.hiros.Managers;
import dev.hiros.Hub.Menus.LobbyMenu;
import dev.hiros.Hub.Menus.QuickWarpMenu;
import dev.hiros.PlayerManager.Rank;

public class HubEvents implements Listener {
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		
		if(Managers.hubManager.getPlayerHub(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerStarve(FoodLevelChangeEvent event) {
		Player player = (Player) event.getEntity();
		
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		
		if(Managers.hubManager.getPlayerHub(player) != null) {
			event.setCancelled(true);
			player.setFoodLevel(20);
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(Managers.hubManager.getPlayerHub(player) != null) {
			if(Rank.get().getPlayerRank(player) > 3 && player.getGameMode() == GameMode.CREATIVE) {
				event.setCancelled(false);
				return;
			}
			else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractBlock(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.hubManager.getPlayerHub(player) == null) {
			return;
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.TRAP_DOOR || event.getClickedBlock().getType() == Material.CHEST) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.hubManager.getPlayerHub(player) == null) {
			return;
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem().getType() == Material.NETHER_STAR) {
				new LobbyMenu(player).openMenu(player);
			}
			if(event.getItem().getType() == Material.COMPASS) {
				new QuickWarpMenu().openMenu(player);
			}
		}
	}
	
	@EventHandler
	public void rightClickEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity ent = event.getRightClicked();
		
		if(!(ent instanceof Villager)) {
			return;
		}
		
		if(Managers.hubManager.getPlayerHub(player) != null) {
			if(ent.getType() == EntityType.VILLAGER) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onHubRaining(WeatherChangeEvent event) {
		if(event.getWorld().getName().equals("world")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropId(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.hubManager.getPlayerHub(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(Managers.hubManager.getPlayerHub(player) != null) {
			if(Rank.get().getPlayerRank(player) > 3 && player.getGameMode() == GameMode.CREATIVE) {
				return;
			} else {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		Entity ent = event.getEntity();
		if(ent.getWorld().getName().equals("world") && ent.getType() == EntityType.SKELETON) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.hubManager.getPlayerHub(player) != null) {
			String message = Rank.get().getPlayerRankName(player)+""+ChatColor.YELLOW+""+player.getName()+" "+ChatColor.WHITE+""+event.getMessage();
			for(Player p : Managers.hubManager.getPlayerHub(player).getPlayers()) {
				p.sendMessage(message);
			}
			event.setCancelled(true);
		}
	}
}
