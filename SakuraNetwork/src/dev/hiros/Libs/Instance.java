package dev.hiros.Libs;

import java.util.HashMap;
import java.util.Map;

import dev.hiros.GameMethods.GameType;
import dev.hiros.Minigames.Engine.MinigameManager;
import dev.hiros.Minigames.FFA.FFAManager;
import dev.hiros.Types.SakuraManager;

public class Instance {
	public static Map<String, SakuraManager> instances = new HashMap<String, SakuraManager>();
	
	public static void loadInstances() {
		instances.put("FFA", new FFAManager());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends MinigameManager> T getManager(GameType type) {
		if(type == GameType.FFA) {
			return (T) instances.get("FFA");
		}
		else {
			return null;
		}
	}
}