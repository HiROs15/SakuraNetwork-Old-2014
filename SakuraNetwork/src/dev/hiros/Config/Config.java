package dev.hiros.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.hiros.SakuraNetwork;

public class Config {
	private static Config inst;
	public static Config get() {
		inst = new Config();
		return inst;
	}
	
	private FileConfiguration config = null;
	private File file = null;
	private String filename = null;
	
	public Config getFile(String name) {
		filename = name;
		return inst;
	}
	
	public void reloadConfig() {
		if(file == null) {
			file = new File(SakuraNetwork.plugin.getDataFolder()+"/"+filename);
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig() {
		if(config == null) {
			reloadConfig();
		}
		return config;
	}
	
	public void saveConfig() {
		if(file == null || config == null) {
			reloadConfig();
		}
		try {
			config.save(file);
		} catch(Exception e) {}
	}
}
