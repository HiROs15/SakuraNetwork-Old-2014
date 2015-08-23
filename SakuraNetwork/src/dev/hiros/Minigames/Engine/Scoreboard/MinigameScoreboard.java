package dev.hiros.Minigames.Engine.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class MinigameScoreboard {
	private ScoreboardManager manager;
	private Scoreboard scoreboard;
	private Objective objective;
	private Objective objectivebuffer;
	private String header;
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public MinigameScoreboard(String header) {
		this.header = header;
		
		manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		objective = scoreboard.registerNewObjective("test", "dummy");
		objectivebuffer = scoreboard.registerNewObjective("test", "dummy");
		
		objective.setDisplayName(this.header);
		objectivebuffer.setDisplayName(this.header);
	}
	
	public void resetScoreboard() {
		manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		objective = scoreboard.registerNewObjective("test", "dummy");
		objectivebuffer = scoreboard.registerNewObjective("test", "dummy");
	}
	
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
	
	public void addLine(String text, int score) {
		objective.getScore(text).setScore(score);
		objectivebuffer.getScore(text).setScore(score);
	}
	
	public void resetObjective() {
		objectivebuffer.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective = scoreboard.registerNewObjective("test", "dummy");
	}
	
	public void resetBuffer() {
		objectivebuffer = objective;
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
}
