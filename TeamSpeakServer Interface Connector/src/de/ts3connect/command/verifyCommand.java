package de.ts3connect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import de.ts3connect.mysql.tsMySQL;
import de.ts3connect.teamspeakserver.TSServerStats;
import net.md_5.bungee.api.ChatColor;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class verifyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args[0].equalsIgnoreCase("verify")) {
				if (p.hasPermission("ts3.verify")) {
					if (args.length == 2) {
						if (TSServerStats.api.getClientByUId(args[1]) != null) {
							PermissionUser user = PermissionsEx.getUser(p);
							Client c = TSServerStats.api.getClientByUId(args[1]);
							int GID = TSServerStats.getTSGroupID(user);
							if (GID != -1) {
								TSServerStats.api.addClientToServerGroup(GID, c.getDatabaseId());
								tsMySQL.insertUser(p.getName(), p.getUniqueId(), args[1], GID);
								p.sendMessage(ChatColor.GREEN + "TeamSpeak Servergroup have been set!");
							} else {
								p.sendMessage(ChatColor.RED + "Internal Error!");
							}
						} else {
							p.sendMessage(ChatColor.RED + "We don't know anything about that Teamspeak Identity.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "/verify <ts identity>");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You don't have enough permssions to do that!");
				}
			} else if (args[0].equalsIgnoreCase("unverify")) {
				if (p.hasPermission("ts3.unverify")) {
					if (tsMySQL.didUserExist(p.getUniqueId())) {
						Client c = TSServerStats.api.getClientByUId(tsMySQL.getParameter("TSID", p.getUniqueId()));
						tsMySQL.deleteUser(p.getUniqueId());
						PermissionUser user = PermissionsEx.getUser(p);
						TSServerStats.api.removeClientFromServerGroup(TSServerStats.getTSGroupID(user), c.getDatabaseId());
						p.sendMessage(ChatColor.RED + "You are no longer verified!");
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + " /ts <verify/unverify> <tsid>");
			}
		} else {
			sender.sendMessage(ChatColor.RED  + "You must be a Player to perform this command!");
		}
		
		return true;
	}

}
