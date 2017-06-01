package de.ts3connect.fileconfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.core.jmx.Server;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;

import de.ts3connect.teamspeakserver.TSServerStats;

public class TsGroupsConfig {
	
	public static ArrayList<ServerGroup> getGroups() {
		ArrayList<ServerGroup> groups = new ArrayList<>();
		for (ServerGroup group : TSServerStats.api.getServerGroups()) {
			groups.add(group);
		}
		return groups;
	}
	private static File getConfigFile() {
        return new File("plugins/TS3ServerInterface", "tsservergroups.yml");
    }

    private static YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public static void setConfig() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true); 
       
        for (ServerGroup group : getGroups()) {
        	cfg.addDefault(group.getName(), group.getId());
        }
        try {
            cfg.save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readConfig() {
        YamlConfiguration cfg = getConfiguration();	
        
        //TODO: CommunicationsLogging
        
        
        @SuppressWarnings("unchecked")
		ArrayList<String> groups = (ArrayList<String>) cfg.get("groups");
        for (String s : groups) {
        	
        }
    }
	
}
