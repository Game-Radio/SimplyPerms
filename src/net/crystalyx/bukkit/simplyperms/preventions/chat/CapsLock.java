package net.crystalyx.bukkit.simplyperms.preventions.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

public class CapsLock extends SimplyPrevents {

	public CapsLock(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void capslock(PlayerChatEvent event) {
		String message = event.getMessage();
		for (Player player : super.plugin.getServer().getOnlinePlayers()) {
			message.replace(player.getName(), "");
		}
		String nocaps = message.replaceAll("[A-Z]*", "");
		if (message.length() > 5
				&& message.length() - nocaps.length() > message.length() / 2) {
			prevent(event, event.getPlayer(), "capslock,chat");
		}
	}

}
