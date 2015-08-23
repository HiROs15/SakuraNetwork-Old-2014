package dev.hiros.Libs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import dev.hiros.Config.Config;

public class ConfigLocation {
	public static ConfigLocation get() {
		return new ConfigLocation();
	}
	
	public Location getLocation(String file, String path) {
		Config config = Config.get().getFile(file);
		Location loc = new Location(
				Bukkit.getServer().getWorld(config.getConfig().getString(path+".world")),
				config.getConfig().getDouble(path+".x"),
				config.getConfig().getDouble(path+".y"),
				config.getConfig().getDouble(path+".z"),
				(float) config.getConfig().getDouble(path+".yaw"),
				(float) config.getConfig().getDouble(path+".pitch")
				);
		return loc;
	}
	
	public void saveLocation(String file, String path, Player player) {
		Config config = Config.get().getFile(file);
		Location loc = player.getLocation();
		
		config.getConfig().set(path+".world", loc.getWorld().getName());
		config.getConfig().set(path+".x", loc.getX());
		config.getConfig().set(path+".y", loc.getY());
		config.getConfig().set(path+".z", loc.getZ());
		config.getConfig().set(path+".yaw", loc.getYaw());
		config.getConfig().set(path+".pitch", loc.getPitch());
		config.saveConfig();
	}
}
