package de.ts3connect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.ts3connect.command.verifyCommand;
import de.ts3connect.events.PlayerEnterServer;
import de.ts3connect.fileconfig.TsServerConfig;
import de.ts3connect.fileconfig.mySQLConfig;
import de.ts3connect.mysql.mysql;
import de.ts3connect.teamspeakserver.TSServerStats;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
		mySQLConfig.setConfig();
		mySQLConfig.readConfig();
		
		mysql.connect();
		
		TsServerConfig.setConfig();
		TSServerStats.registerServer();
		
		registerEvents();
		registerCommands();
		
	}
	@Override
	public void onDisable() {
		
		mysql.close();
		
	}
	
	private void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new PlayerEnterServer(), this);
	}
	private void registerCommands() {
		this.getCommand("teamspeak").setExecutor(new verifyCommand());
	}
	
}
