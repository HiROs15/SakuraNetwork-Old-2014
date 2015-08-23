package dev.hiros.PlayerManager;

import org.bukkit.entity.Player;

public class SakuraPlayer {
	private Player player;
	
	public SakuraPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
}
