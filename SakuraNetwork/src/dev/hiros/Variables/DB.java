package dev.hiros.Variables;

import dev.hiros.SakuraNetwork;
import dev.hiros.Libs.MySQL;

public class DB {
	public static String server = "mysql4.worldplanethosting.com";
	public static String username = "alphaks_admin";
	public static String password = "fatcheese123";
	public static String database = "alphaks_sakuranetwork";
	
	public static MySQL db = new MySQL(SakuraNetwork.plugin, "mysql4.worldplanethosting.com", "3306", "alphaks_sakuranetwork", "alphaks_admin", "fatcheese123");
}
