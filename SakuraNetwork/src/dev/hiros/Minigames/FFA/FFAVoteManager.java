package dev.hiros.Minigames.FFA;

import org.bukkit.entity.Player;

import dev.hiros.Managers;
import dev.hiros.Lobby.Lobby;
import dev.hiros.Lobby.LobbyState;
import dev.hiros.Minigames.Engine.MinigameVoteManager;
import dev.hiros.Variables.Chat;

public class FFAVoteManager extends MinigameVoteManager {
	public FFAVoteManager(String file) {
		super(file);
		this.setupMapVoting();
	}
	
	public void showMenu(Player player) {
		Lobby lobby = Managers.lobbyManager.getPlayerLobby(player);
		if(lobby.getState() == LobbyState.OPEN) {
			player.sendMessage(Chat.CommandErrorTag+"Voting is not open at this time. Please wait untill more players join.");
			return;
		}
		if(lobby.getVoteActive() == false) {
			player.sendMessage(Chat.CommandErrorTag+"The map has already been selected. Voting is closed.");
			return;
		}
		
		new FFAVoteMenu().openMenu(player);
	}
}
