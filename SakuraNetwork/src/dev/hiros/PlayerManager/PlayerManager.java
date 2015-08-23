package dev.hiros.PlayerManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import dev.hiros.Managers;
import dev.hiros.PlayerManager.Showcase.Showcase;

public class PlayerManager {
	public static PlayerManager get() {
		return new PlayerManager();
	}
	
	private ArrayList<SakuraPlayer> players = new ArrayList<SakuraPlayer>();
	
	public void addPlayer(Player player) {
		this.setupPlayerData(player);
	}
	
	public SakuraPlayer getPlayer(Player player) {
		for(SakuraPlayer p : players) {
			if(p.getPlayer().getName() == player.getName()) {
				return p;
			}
		}
		return null;
	}
	
	public boolean containsPlayer(Player player) {
		if(getPlayer(player) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void removePlayer(Player player) {
	}
	
	private void setupPlayerData(Player player) {
		String name = player.getUniqueId().toString();
		try {
			Connection c = Managers.sakuraDB.db.openConnection();
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("SELECT username FROM members WHERE username='"+name+"';");
			
			if(!rs.next()) {
				Showcase.get().addPlayer(player);
				
				st = c.createStatement();
				st.executeUpdate("INSERT INTO members (username) VALUES ('"+name+"');");
			} else {
				Managers.hubManager.joinHub(player);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
