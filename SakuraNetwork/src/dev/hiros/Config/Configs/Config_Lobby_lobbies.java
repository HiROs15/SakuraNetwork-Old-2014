package dev.hiros.Config.Configs;

import dev.hiros.Config.BaseConfigFile;
import dev.hiros.Config.Config;

public class Config_Lobby_lobbies extends BaseConfigFile {
	public Config_Lobby_lobbies() {
		this.setFilePath("/Lobby/lobbies.yml");
	}
	
	@Override
	public void setupDefaultConfig() {
		Config config = Config.get().getFile("/Lobby/lobbies.yml");
		config.getConfig().set("setup", false);
		config.saveConfig();
	}
}
