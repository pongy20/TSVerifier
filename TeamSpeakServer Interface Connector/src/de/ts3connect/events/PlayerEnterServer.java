package de.ts3connect.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.ts3connect.mysql.tsMySQL;

public class PlayerEnterServer implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!tsMySQL.checkUserNameIsActual(p.getName(), p.getUniqueId())) {
			tsMySQL.updateUsername(p.getName(), p.getUniqueId());
		}
	}
	
}
