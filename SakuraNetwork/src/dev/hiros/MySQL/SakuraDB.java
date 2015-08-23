package dev.hiros.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dev.hiros.SakuraNetwork;
import dev.hiros.Libs.MySQL;

public class SakuraDB {
	public MySQL db = new MySQL(SakuraNetwork.plugin, "mysql4.worldplanethosting.com", "3306", "alphaks_sakuranetwork", "alphaks_admin", "fatcheese123");
	public Connection c = null;
	public SakuraDB() {
		try {
			c = db.openConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet query(String query) {
		Statement st;
		try {
			st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void update(String query) {
		Statement st;
		try {
			st = c.createStatement();
			st.executeUpdate(query);
		} catch(Exception e) {}
	}
}
