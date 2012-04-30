package net.crystalyx.bukkit.simplyperms.preventions;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Command extends SimplyPrevents {

	public Command(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void command(PlayerCommandPreprocessEvent event) {
		String message = event.getMessage();
		if (message.startsWith("/")) {
			message = message.substring(1).trim();
			int spaceIndex = message.indexOf(' ');
			if (spaceIndex >= 0) {
				message = message.substring(0, spaceIndex);
			}
			if (message.length() > 0) {
				prevent(event, event.getPlayer(), "command." + message);
			}
		}
	}

}
