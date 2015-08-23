package dev.hiros.Config.Configs;

import dev.hiros.Config.BaseConfigFile;
import dev.hiros.Config.Config;

public class Config_Server extends BaseConfigFile {
	public Config_Server() {
		this.setFilePath("/settings.yml");
	}
	
	@Override
	public void setupDefaultConfig() {
		Config config = Config.get().getFile("settings.yml");
		config.getConfig().set("slots", 2500);
		config.getConfig().set("motd", "None");
		config.getConfig().set("starting_coins", 20);
		config.getConfig().set("starting_rank","basic");
		config.saveConfig();
	}
}
