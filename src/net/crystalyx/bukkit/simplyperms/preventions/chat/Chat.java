package net.crystalyx.bukkit.simplyperms.preventions.chat;

import net.crystalyx.bukkit.simplyperms.SimplyPlugin;
import net.crystalyx.bukkit.simplyperms.SimplyPrevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;

public class Chat extends SimplyPrevents {

	public Chat(SimplyPlugin plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void chat(PlayerChatEvent event) {
		if (!event.getMessage().startsWith("u00a74u00a75u00a73u00a74")) {
			prevent(event, event.getPlayer(), "chat");
		}
	}

}
