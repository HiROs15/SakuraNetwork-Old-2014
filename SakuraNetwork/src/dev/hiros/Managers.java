package dev.hiros;

import dev.hiros.Hub.HubManager;
import dev.hiros.Hub.Entities.HubEntityManager;
import dev.hiros.Lobby.LobbyManager;
import dev.hiros.MySQL.SakuraDB;

public class Managers {
	public static HubManager hubManager = new HubManager();
	public static HubEntityManager hubEntityManager = new HubEntityManager();
	public static LobbyManager lobbyManager = new LobbyManager();
	
	public static SakuraDB sakuraDB = new SakuraDB();
}
