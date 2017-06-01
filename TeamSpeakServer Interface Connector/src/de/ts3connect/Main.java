package de.ts3connect;

import org.bukkit.plugin.java.JavaPlugin;

import de.ts3connect.fileconfig.TsServerConfig;
import de.ts3connect.teamspeakserver.TSServerStats;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
		TsServerConfig.setConfig();
		TSServerStats.registerServer();
		
	}
	@Override
	public void onDisable() {
		
		
		
	}
	
}
