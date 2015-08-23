package dev.hiros.Config;

public abstract class BaseConfigFile {
	public String filePath = null;
	
	public void setFilePath(String path) {
		filePath = path;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public abstract void setupDefaultConfig();
}
