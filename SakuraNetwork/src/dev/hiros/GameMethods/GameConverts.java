package dev.hiros.GameMethods;

public class GameConverts {
	public static GameConverts get() {
		return new GameConverts();
	}
	
	public String getScoreboardName(GameType type) {
		String ret = "";
		
		switch(type) {
		case FFA:
			ret = "Free for All";
			break;
		}
		
		return ret;
	}
}
