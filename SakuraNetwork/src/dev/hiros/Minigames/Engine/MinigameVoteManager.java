package dev.hiros.Minigames.Engine;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import dev.hiros.Config.Config;
import dev.hiros.Types.SakuraManager;

public abstract class MinigameVoteManager extends SakuraManager {
	
	/*
	 * HOW TO USE
	 * 
	 * ----- Constructor -----
	 * setupMapVoting()
	 * 
	 */
	
	private HashMap<String, Integer> votes = new HashMap<String, Integer>();
	private ArrayList<Player> voters = new ArrayList<Player>();
	private String file;
	private boolean pollsActive = false;
	
	public MinigameVoteManager(String file) {
		this.file = file;
		this.resetVotes();
	}
	
	public void setupMapVoting() {
		Config config = Config.get().getFile(file);
		
		for(String key : config.getConfig().getConfigurationSection("maps").getKeys(false)) {
			votes.put(key, 0);
		}
	}
	
	public void setVotingState(boolean state) {
		this.pollsActive = state;
	}
	
	public boolean getVotingActive() {
		return this.pollsActive;
	}
	
	public HashMap<String, Integer> getVotes() {
		return this.votes;
	}
	
	public boolean vote(String map, Player player) {
		if(this.containsPlayer(player)) {
			return false;
		}
		voters.add(player);
		int num = votes.get(map);
		votes.remove(map);
		votes.put(map, num+1);
		return true;
	}
	
	public void resetVotes() {
		this.votes.clear();
		this.voters.clear();
		this.setupMapVoting();
	}
	
	private boolean containsPlayer(Player player) {
		for(Player p : voters) {
			if(p.getName().equals(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public String getVoteResults() {
		String m = "none";
		int mVotes = 0;
		
		for(String map : votes.keySet()) {
			if(votes.get(map) > mVotes) {
				m = map;
				mVotes = votes.get(map);
			}
		}
		
		if(m.equals("none")) {
			Config config = Config.get().getFile(this.file);
			ArrayList<String> tempMaps = new ArrayList<String>();
			for(String map : config.getConfig().getConfigurationSection("maps").getKeys(false)) {
				tempMaps.add(map);
			}
			
			int random = (int) Math.floor(Math.random()*tempMaps.size());
			return tempMaps.get(random);
		}
		else {
			return m;
		}
	}
	
	public abstract void showMenu(Player player);
}
