package dev.hiros.PlayerManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.hiros.Managers;

public class Rank {
	public static Rank get() {
		return new Rank();
	}
	
	public int getIdFromRank(SakuraRank rank) {
		int ret = 0;
		switch(rank) {
		case BASIC:
			ret = 0;
			break;
		case VIP:
			ret = 1;
			break;
		case MOD:
			ret = 2;
			break;
		case ADMIN:
			ret = 3;
			break;
		case BUILDTEAM:
			ret = 4;
			break;
		case DEV:
			ret = 5;
			break;
		case OVERLORD:
			ret = 6;
			break;
		}
		return ret;
	}
	
	public SakuraRank getRankFromId(int id) {
		SakuraRank rank = null;
		
		switch(id) {
		case 0:
			rank = SakuraRank.BASIC;
			break;
		case 1:
			rank = SakuraRank.VIP;
			break;
		case 2:
			rank = SakuraRank.MOD;
			break;
		case 3:
			rank = SakuraRank.ADMIN;
			break;
		case 4:
			rank = SakuraRank.BUILDTEAM;
			break;
		case 5:
			rank = SakuraRank.DEV;
			break;
		case 6:
			rank = SakuraRank.OVERLORD;
			break;
		}
		return rank;
	}
	
	public SakuraRank getRankFromString(String rank) {
		SakuraRank ret = null;
		switch(rank) {
		case "basic":
			ret = SakuraRank.BASIC;
			break;
		case "vip":
			ret = SakuraRank.VIP;
			break;
		case "mod":
			ret = SakuraRank.MOD;
			break;
		case "admin":
			ret = SakuraRank.ADMIN;
			break;
		case "buildteam":
			ret = SakuraRank.BUILDTEAM;
			break;
		case "dev":
			ret = SakuraRank.DEV;
			break;
		case "overlord":
			ret = SakuraRank.OVERLORD;
			break;
		}
		return ret;
	}
	
	public String getStringFromRank(SakuraRank rank) {
		String ret = null;
		switch(rank) {
		case BASIC:
			ret = "basic";
			break;
		case VIP:
			ret = "vip";
			break;
		case MOD:
			ret = "mod";
			break;
		case BUILDTEAM:
			ret = "buildteam";
			break;
		case ADMIN:
			ret = "admin";
			break;
		case DEV:
			ret = "dev";
			break;
		case OVERLORD:
			ret = "overlord";
			break;
		}
		return ret;
	}
	
	public int getPlayerRank(Player player) {
		ResultSet rs = Managers.sakuraDB.query("SELECT * FROM members WHERE username='"+player.getUniqueId().toString()+"';");
		try {
			if(rs.next()) {
				return rs.getInt("rank");
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getPlayerRankName(Player player) {
		int rank = this.getPlayerRank(player);
		String ret = "";
		switch(rank) {
		case 0:
			ret = "";
			break;
		case 1:
			ret = ChatColor.AQUA+""+ChatColor.BOLD+"VIP "+ChatColor.RESET+"";
			break;
		case 2:
			ret = ChatColor.BLUE+""+ChatColor.BOLD+"MOD "+ChatColor.RESET+"";
			break;
		case 3:
			ret = ChatColor.RED+""+ChatColor.BOLD+"ADMIN "+ChatColor.RESET+"";
			break;
		case 4:
			ret = ChatColor.GREEN+""+ChatColor.BOLD+"BUILDTEAM "+ChatColor.RESET+"";
			break;
		case 5:
			ret = ChatColor.DARK_AQUA+""+ChatColor.BOLD+"DEV "+ChatColor.RESET+"";
			break;
		case 6:
			ret = ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"OVERLORD "+ChatColor.RESET+"";
			break;
		}
		return ret;
	}
}
