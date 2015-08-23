package dev.hiros.PlayerManager.Economy;

import java.sql.ResultSet;

import org.bukkit.entity.Player;

import dev.hiros.Managers;

public class PlayerData {
	public static PlayerData get() {
		return new PlayerData();
	}
	
	public int getCoins(Player player) {
		try {
			ResultSet rs = Managers.sakuraDB.query("SELECT * FROM members WHERE username='"+player.getUniqueId().toString()+"';");
			
			if(rs.next()) {
				return rs.getInt("coins");
			}
			else {
				return 0;
			}
		} catch(Exception e) {}
		return 0;
	}
	
	public void addCoins(Player player, int coins) {
		int total = 0;
		try {
			ResultSet rs = Managers.sakuraDB.query("SELECT * FROM members WHERE username='"+player.getUniqueId().toString()+"';");
			if(rs.next()) {
				total = rs.getInt("coins");
			} else {
				total = 0;
			}
			
			total = total+coins;
			
			Managers.sakuraDB.update("UPDATE members SET coins='"+total+"' WHERE username='"+player.getUniqueId().toString()+"';");
		}catch(Exception e) {}
	}
}
