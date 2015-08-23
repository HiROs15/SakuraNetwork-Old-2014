package dev.hiros.Config;

import java.io.File;
import java.util.ArrayList;

import dev.hiros.SakuraNetwork;
import dev.hiros.Config.Configs.Config_FFA;
import dev.hiros.Config.Configs.Config_Hub_Entities;
import dev.hiros.Config.Configs.Config_Hubs;
import dev.hiros.Config.Configs.Config_Lobby_lobbies;
import dev.hiros.Config.Configs.Config_Server;

public class ConfigManager {
	public static ConfigManager get() {
		return new ConfigManager();
	}
	
	private ArrayList<BaseConfigFile> configs = new ArrayList<BaseConfigFile>();
	
	public ConfigManager() {
		//Add Configs Here
		configs.add(new Config_Server());
		configs.add(new Config_Hubs());
		configs.add(new Config_Hub_Entities());
		configs.add(new Config_Lobby_lobbies());
		configs.add(new Config_FFA());
	}
	
	public void setupConfigs() {
		for(BaseConfigFile config : configs) {
			File f = new File(SakuraNetwork.plugin.getDataFolder()+""+config.getFilePath());
			if(!f.exists()) {
				try {
					f.createNewFile();
				}catch(Exception e) {}
				//Add the Defaults
				config.setupDefaultConfig();
				SakuraNetwork.plugin.getLogger().info("Server has created the initial config files.");
			}
		}
	}
}
