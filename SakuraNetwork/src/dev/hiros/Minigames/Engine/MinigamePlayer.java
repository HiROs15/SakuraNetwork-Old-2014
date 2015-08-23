package dev.hiros.Minigames.Engine;

import org.bukkit.entity.Player;

public class MinigamePlayer {
	private Player player;
	
	public MinigamePlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}
