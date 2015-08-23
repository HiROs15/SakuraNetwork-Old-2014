package dev.hiros.PlayerManager.Showcase;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.connorlinfoot.titleapi.TitleAPI;

import dev.hiros.Managers;
import dev.hiros.SakuraNetwork;
import dev.hiros.Hub.Hub;

public class Showcase {
	public static Showcase get() {
		return new Showcase();
	}
	
	private double angle = 0;
	private double radius = 10;
	private double height = 20;
	private float look = 180;
	private boolean toggledPlayers = false;
	private int frame = 0;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public void addPlayer(Player player) {
		players.add(player);
		this.startShowcase(player);
	}
	
	private void startShowcase(final Player player) {
		final Location loc = player.getLocation();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(player.getGameMode() != GameMode.SPECTATOR) {
					player.setGameMode(GameMode.SPECTATOR);
				}
				if(toggledPlayers == false) {
					//Handle pre showcase things!
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						p.hidePlayer(player);
						player.hidePlayer(p);
					}
					toggledPlayers = true;
					startTitle(player);
				}
				Location nl = new Location(
						loc.getWorld(),
						loc.getX()+Math.sin(angle)*radius,
						loc.getY()+height,
						loc.getZ()+Math.cos(angle)*radius,
						(float) -look,
						15
						);
				player.teleport(nl);
				angle += 0.01;
				look += 0.58;
				
				if(angle >= 6) {
					this.cancel();
					loadIntoHub(player);
				}
				if(look > 180) {
					look = -180;
				}
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 1L);
	}
	
	private void startTitle(final Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(frame == 0) {
					TitleAPI.sendTitle(player, 20, 80, 0, ChatColor.GOLD+"Welcome to "+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Sakura Network", ChatColor.YELLOW+"Were some of the best minigames around are found!");
				}
				if(frame == 4) {
					TitleAPI.sendTitle(player, 0, 480, 0, ChatColor.GOLD+"Welcome to "+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Sakura Network", ChatColor.RED+"Please wait while our servers generate your profile.");
				}
				if(frame == 28) {
					TitleAPI.sendTitle(player, 0, 40, 0, ChatColor.GOLD+"Welcome to "+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Sakura Network", ChatColor.GRAY+"Profile has been created. Receiving server data.");
				}
				if(frame == 29) {
					this.cancel();
				}
				frame++;
			}
		}.runTaskTimer(SakuraNetwork.plugin, 0L, 20L);
	}
	
	private void loadIntoHub(Player player) {
		Hub hub = Managers.hubManager.getPlayerHub(player);
		player.teleport(hub.getLocation());
		player.setGameMode(GameMode.ADVENTURE);
		player.setHealth(20);
		player.setFoodLevel(20);
		Managers.hubManager.updateVisiblePlayers(player);
		Managers.hubManager.updateBoard(player);
		
		player.sendMessage(ChatColor.GOLD+"Welcome to the "+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Sakura Network "+ChatColor.RESET+""+ChatColor.GRAY+"| "+ChatColor.YELLOW+"Developed by the Sakura Dev Team!");
	}
}
