package dev.hiros.Config.Configs;

import dev.hiros.Config.BaseConfigFile;
import dev.hiros.Config.Config;

public class Config_FFA extends BaseConfigFile {
	public Config_FFA() {
		this.setFilePath("/FFA/ffa.yml");
	}
	
	@Override
	public void setupDefaultConfig() {
		Config config = Config.get().getFile("/FFA/ffa.yml");
		config.getConfig().set("setup", false);
		config.saveConfig();
	}
}
