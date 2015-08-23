package dev.hiros.Minigames.FFA;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.hiros.Managers;
import dev.hiros.SakuraNetwork;
import dev.hiros.GameMethods.GameType;
import dev.hiros.Libs.Instance;
import dev.hiros.Minigames.Engine.MinigamePlayer;
import dev.hiros.Minigames.Engine.MinigameState;
import dev.hiros.Minigames.FFA.Menus.KitSelector;
import dev.hiros.PlayerManager.Rank;

public class FFAEvents implements Listener {
	//Lobby Listener
	@EventHandler
	public void onPlayerInteract1(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(Managers.lobbyManager.getPlayerLobby(player) != null) {
			if(Managers.lobbyManager.getPlayerLobby(player).getGame() == GameType.FFA) {
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(player.getItemInHand().getType() == Material.WATCH) {
						((FFAManager)Instance.getManager(GameType.FFA)).getVoteManager().showMenu(player);
					}
				}
			}
		}
	}
	//Lobby Listener End
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		if(event.getWorld().getName().equals("ffa")) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(event.getPlayer()) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerHunger(FoodLevelChangeEvent event) {
		Player player = (Player) event.getEntity();
		
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerMoveInPregame(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player).getState() == MinigameState.PREGAME) {
				FFAManager manager = (FFAManager) Instance.getManager(GameType.FFA);
				Location spawn = ((FFAPlayer)manager.getPlayerArena(player).getPlayer(player)).getSpawn();
				if(player.getLocation().getX() != spawn.getX() || player.getLocation().getZ() != spawn.getZ()) {
					player.teleport(new Location(spawn.getWorld(), spawn.getX(), spawn.getY(), spawn.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			((FFAArena)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player)).playerQuit(player);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(player.getItemInHand().getType() == Material.WATCH) {
					new KitSelector().openMenu(player);
					event.setCancelled(true);
				}
			}
			
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(event.getClickedBlock().getType() == Material.TRAP_DOOR || event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.FURNACE) {
					event.setCancelled(true);
				}
				if(event.getClickedBlock().getType() == Material.WOODEN_DOOR) {
					event.getClickedBlock().setType(Material.AIR);
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		Player player = event.getPlayer();
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityKillEachOther(PlayerDeathEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		Player killer = (Player) event.getEntity().getKiller();
		
		if(killer == null && ((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			((FFAArena)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player)).playerDeath(player);
			event.setDroppedExp(0);
			event.getDrops().clear();
			event.setDeathMessage("");
		}
		
		else if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null && ((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(killer) != null) {
			((FFAArena)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player)).killHandler(player, killer);
			event.setDeathMessage("");
			event.setDroppedExp(0);
			event.getDrops().clear();
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			new BukkitRunnable() {
			@Override
			public void run() {
			FFAArena arena = (FFAArena)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player);
			
			arena.respawnPlayer(player);
			arena.updateTabList();
			((FFAPlayer)arena.getPlayer(player)).setKit(((FFAPlayer)arena.getPlayer(player)).getKit());
			}
			}.runTaskLater(SakuraNetwork.plugin, 5L);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			event.setCancelled(true);
			FFAArena arena = ((FFAArena)((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player));
			
			for(MinigamePlayer p : arena.getPlayers()) {
				p.getPlayer().sendMessage(Rank.get().getPlayerRankName(p.getPlayer())+""+ChatColor.YELLOW+""+p.getPlayer().getName()+" "+ChatColor.WHITE+""+event.getMessage());
			}
		}
	}
	
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		
		if(((FFAManager)Instance.getManager(GameType.FFA)).getPlayerArena(player) != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		if(event.getLocation().getWorld().getName().equalsIgnoreCase("ffa")) {
			event.setCancelled(true);
		}
	}
}
