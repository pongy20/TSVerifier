package de.ts3connect.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class tsMySQL {
	
	private static String table_name = mysql.table_name;
	
	public static boolean didUserExist(UUID uuid) {
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM " + table_name + " WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean checkUserNameIsActual(String username, UUID uuid) {
		if (didUserExist(uuid)) {
			PreparedStatement ps;
			try {
				ps = mysql.getConnection().prepareStatement("SELECT username FROM " + table_name + " WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					return rs.getString("username").equals(username);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * Only use this when the username isn't the actual from the table
	 * For Example when somebody changed his nickname
	 */
	public static void updateUsername(String username, UUID uuid) {
		if (didUserExist(uuid)) {
			try {
				PreparedStatement ps = mysql.getConnection().prepareStatement("UPDATE " + table_name + " SET username = ? WHERE UUID = ?");
				ps.setString(1, username);
				ps.setString(2, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void insertUser(String username, UUID uuid, String TSID, int groupID) {
		if(!didUserExist(uuid)) {
			try {
				PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO " + table_name + " (username, UUID, TSID, GID) VALUES (?,?,?,?)");
				ps.setString(1, username);
				ps.setString(2, uuid.toString());
				ps.setString(3, TSID);
				ps.setInt(4, groupID);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	public static void deleteUser(UUID uuid) {
		if (didUserExist(uuid)) {
			try {
				PreparedStatement ps = mysql.getConnection().prepareStatement("DELETE FROM " + table_name + " WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static String getParameter(String parameter, UUID uuid) {
		if (didUserExist(uuid)) {
			try {
				PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM " + table_name + " WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					return rs.getString(parameter);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
