package dev.hiros.Server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import dev.hiros.Config.Config;
import dev.hiros.Libs.Color;

public class ServerEvents implements Listener {
	@EventHandler
	public void onPlayerPingServer(ServerListPingEvent event) {
		Config config = Config.get().getFile("settings.yml");
		int slots = config.getConfig().getInt("slots");
		String motd = Color.format(config.getConfig().getString("motd"));
		
		event.setMaxPlayers(slots);
		event.setMotd(motd);
	}
}
