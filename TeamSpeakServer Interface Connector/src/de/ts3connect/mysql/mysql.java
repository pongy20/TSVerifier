package de.ts3connect.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class mysql {
	public static String table_name = "teamspeak_verification";
	
	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;
	private static Connection con;
	
	public static void connect() {
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				Bukkit.getConsoleSender().sendMessage("[MySQL] connected to database!");
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	public static void close() {
		if (isConnected()) {
			try {
				con.close();
				Bukkit.getConsoleSender().sendMessage("[MySQL] disconnected from database!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	public static Connection getConnection() {
		return con;
	}
	public static void createTable() {
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table_name + " (username VARCHAR(255) NOT NULL, UUID VARCHAR(255) NOT NULL PRIMARY_KEY, TSID VARCHAR(255) NOT NULL, GID INT(11) NOT NULL)");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
