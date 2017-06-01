package de.ts3connect.fileconfig;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.ts3connect.teamspeakserver.TSServerStats;

public class TsServerConfig {
	private static File getConfigFile() {
        return new File("plugins/TS3ServerInterface", "tsserver.yml");
    }

    private static YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public static void setConfig() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true); 
        
        cfg.addDefault("host", "playlegend.eu");
        cfg.addDefault("queryPort", 10011);
        cfg.addDefault("enableCommunicationsLogging", false);
        cfg.addDefault("commandTimeout", 4000);
        try {
            cfg.save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readConfig() {
        YamlConfiguration cfg = getConfiguration();	
        
        //TODO: CommunicationsLogging
        
        TSServerStats.config.setHost(cfg.getString("host"));
        TSServerStats.config.setQueryPort(cfg.getInt("queryPort"));
        TSServerStats.config.setCommandTimeout(cfg.getInt("commandTimeout"));
    }
}
