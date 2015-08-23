package dev.hiros.Hub;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dev.hiros.Libs.ConfigLocation;

public class Hub {
	private Location loc;
	private List<Player> players;
	private String id;
	private int maxPlayers = 250;
	
	public Hub(int id) {
		this.id = ""+id;
		this.loc = ConfigLocation.get().getLocation("/Hub/hubs.yml", "hubs."+id+".spawn");
		this.players = new ArrayList<Player>();
	}
	
	public boolean containsPlayer(Player player) {
		for(Player p : this.players) {
			if(p.getName() == player.getName()) {
				return true;
			}
		}
		return false;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public int getCurrentPlayers() {
		return this.players.size();
	}
	
	public Location getLocation() {
		return this.loc;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public int getId() {
		return Integer.parseInt(this.id);
	}
	
	public void join(Player player) {
		this.players.add(player);
	}
	
	public void leave(Player player) {
		this.players.remove(player);
	}
}
