package de.ts3connect.teamspeakserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;

import de.ts3connect.fileconfig.TsServerConfig;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;

public class TSServerStats {

	public static TS3Config config;
	public static TS3Query query;
	public static TS3Api api;
	public static List<ServerGroup> servergroups = new ArrayList<>();
	public static HashMap<String, ServerGroup> servergroupsHash = new HashMap<>();
	
	public static void registerServer() {
		TsServerConfig.readConfig();
		
		servergroups = api.getServerGroups();
		query = new TS3Query(config);
		api = query.getApi();
	}
	@SuppressWarnings("deprecation")
	public static int getTSGroupID(PermissionUser p) {
		if (p.getGroups()[0] != null) {
			PermissionGroup pexgroup = p.getGroups()[0];
			for (ServerGroup group : servergroups) {
				if (pexgroup.getName() == group.getName()) {
					return group.getId();
				}
			}
		}
		return -1;
	}
	
}
