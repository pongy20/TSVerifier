package de.ts3connect.fileconfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        return new File("plugins/TS3ServerInterface", "groups.yml");
    }

    private static YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public static void setConfig() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true); 
       
        List<String> groups = new ArrayList<>();
        cfg.set("groups", groups);
        try {
            cfg.save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
	public static void readConfig() {
    	YamlConfiguration cfg = getConfiguration();
    	
    	List<String> groups = new ArrayList<>();
    	
    	if (cfg.get("groups") != null) {
    		groups = (List<String>) cfg.get("groups");
    	}
    	for (String s : groups) {
    		String[] parts = s.split(":");
    		String name = parts[0];
    		int id = Integer.parseInt(parts[1]);
    		
    		for (ServerGroup group : TSServerStats.api.getServerGroups()) {
    			if (group.getId() == id) {
    				TSServerStats.servergroupsHash.put(name, group);
    			}
    		}
    	}
    }
	
}
