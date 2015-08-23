package dev.hiros.Config.Configs;

import dev.hiros.Config.BaseConfigFile;
import dev.hiros.Config.Config;

public class Config_Hub_Entities extends BaseConfigFile {
	public Config_Hub_Entities() {
		this.setFilePath("/Hub/hub_entities.yml");
	}
	@Override
	public void setupDefaultConfig() {
		Config config = Config.get().getFile("/Hub/hub_entities.yml");
		config.getConfig().set("setup", false);
		config.saveConfig();
	}
}
