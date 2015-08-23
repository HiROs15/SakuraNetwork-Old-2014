package dev.hiros.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.hiros.Managers;
import dev.hiros.SakuraNetwork;
import dev.hiros.Hub.Entities.HubEntity;

public class PlayerManagerEvents implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Managers.hubManager.joinHub(event.getPlayer());
		PlayerManager.get().addPlayer(event.getPlayer());
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for(HubEntity ent : Managers.hubEntityManager.getEntities()) {
					ent.getHologram().spawn();
				}
			}
		}.runTaskLater(SakuraNetwork.plugin, 40L);
		event.setJoinMessage("");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayerManager.get().removePlayer(event.getPlayer());
		
		//Remove player from hub!
		if(Managers.hubManager.getPlayerHub(event.getPlayer()) != null) {
			Managers.hubManager.getPlayerHub(event.getPlayer()).leave(event.getPlayer());
		}
		
		event.setQuitMessage("");
	}
}
