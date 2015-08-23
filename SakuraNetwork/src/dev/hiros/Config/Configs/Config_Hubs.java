package dev.hiros.Config.Configs;

import dev.hiros.Config.BaseConfigFile;
import dev.hiros.Config.Config;

public class Config_Hubs extends BaseConfigFile {
	public Config_Hubs() {
		this.setFilePath("/Hub/hubs.yml");
	}
	
	@Override
	public void setupDefaultConfig() {
		Config config = Config.get().getFile("/Hub/hubs.yml");
		config.getConfig().set("setup", false);
		config.saveConfig();
	}
}
